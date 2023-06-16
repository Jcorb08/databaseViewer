package databaseViewerApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Service
public class BetService {
    private final BetRepository betRepository;
    private final ClientRepository clientRepository;
    private final GameRepository gameRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public BetService(BetRepository betRepository,
                      ClientRepository clientRepository,
                      GameRepository gameRepository,
                      KafkaTemplate<String, String> kafkaTemplate) {
        this.betRepository = betRepository;
        this.clientRepository = clientRepository;
        this.gameRepository = gameRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void insertUsersFromJsonFile(String filePath) {
        try (JsonReader reader = Json.createReader(new FileReader(filePath))) {
            JsonArray betsArray = reader.readArray();
            for (JsonObject betsJson : betsArray.getValuesAs(JsonObject.class)) {
                // set new bet values
                Bet bet = new Bet();
                bet.setId(betsJson.getInt("id"));
                bet.setDate(new SimpleDateFormat("yyyy-mm-dd").parse(betsJson.getString("date")));
                bet.setNumber_of_bets(betsJson.getInt("numbets"));
                bet.setStake(betsJson.getInt("stake"));
                bet.setReturns(betsJson.getInt("returns"));

                // grab associated client, if not present create new with id specified
                Integer clientId = betsJson.getInt("clientid");
                Optional<Client> currentClient = clientRepository.findById(clientId);
                if (currentClient.isPresent()){
                    bet.setClient_id(currentClient.get());
                } else {
                    Client client = new Client();
                    client.setId(clientId);
                    clientRepository.save(client);
                    bet.setClient_id(client);
                }

                // grab associated game, if not present create new with name specified
                String gameName = betsJson.getString("game");
                Optional<Game> currentGame = gameRepository.findByName(gameName);
                if (currentGame.isPresent()){
                    bet.setGame_id(currentGame.get());
                } else {
                    Game game = new Game();
                    game.setName(gameName);
                    gameRepository.save(game);
                    bet.setGame_id(game);
                }
                betRepository.save(bet);

                // Send Kafka message for each insert
                String message = betsJson.toString();
                kafkaTemplate.send("bet_detail", message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

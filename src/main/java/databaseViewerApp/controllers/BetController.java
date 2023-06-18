package databaseViewerApp.controllers;

import databaseViewerApp.models.Bet;
import databaseViewerApp.models.Game;
import databaseViewerApp.repositories.BetRepository;
import databaseViewerApp.repositories.ClientRepository;
import databaseViewerApp.repositories.GameRepository;
import databaseViewerApp.services.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class BetController {
    private final BetService betService;
    private final BetRepository betRepository;
    private final GameRepository gameRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public BetController(BetService betService, BetRepository betRepository, GameRepository gameRepository, ClientRepository clientRepository) {
        this.betService = betService;
        this.betRepository = betRepository;
        this.gameRepository = gameRepository;
        this.clientRepository = clientRepository;
    }

    @PostMapping("/insertFromJson")
    public @ResponseBody String insertUsersFrom(@RequestParam String filepath){
        betService.insertUsersFromJsonFile(filepath);
        return "Success";
    }

    @GetMapping(path="/json")
    public @ResponseBody Iterable<Bet> getAllUsers() {
        // This returns a JSON or XML with the users
        return betRepository.findAll();
    }

    @GetMapping(path="/")
    public String displayTable(@RequestParam(required = false) String date,
                               @RequestParam(required = false) Integer client_id,
                               @RequestParam(required = false) String game_name,
                               Model model) {
        try {
            model.addAttribute("tableData", performFiltering(date, client_id, game_name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("paramDate",date);
        model.addAttribute("paramClientID",client_id);
        model.addAttribute("paramGameName",game_name);
        return "tablePage";
    }

    private List<Bet> performFiltering(String date, Integer client_id, String game_name) throws ParseException {
        // Implement your custom filtering logic based on the search query
        Boolean game_name_bool = game_name != null && !game_name.isEmpty();
        Boolean date_name_bool = date != null && !date.isEmpty();

        if (date_name_bool && client_id != null && game_name_bool ){
            Optional<Game> g = gameRepository.findByName(game_name);
            if (g.isPresent()) {
                return betRepository.findByDateAndClientAndGame(new SimpleDateFormat("yyyy-MM-dd").parse(date), g.get(), clientRepository.getReferenceById(client_id));
            }
        } else if (date_name_bool && client_id != null){
            return betRepository.findByDateAndClient(new SimpleDateFormat("yyyy-MM-dd").parse(date),clientRepository.getReferenceById(client_id));
        } else if (client_id != null && game_name_bool){
            Optional<Game> g = gameRepository.findByName(game_name);
            if (g.isPresent()) {
                return betRepository.findByGameAndClient(g.get(), clientRepository.getReferenceById(client_id));
            }
        } else if (date_name_bool && game_name_bool){
            Optional<Game> g = gameRepository.findByName(game_name);
            if (g.isPresent()) {
                return betRepository.findByGameAndDate(g.get(), new SimpleDateFormat("yyyy-MM-dd").parse(date));
            }
        } else if (date_name_bool){
            return betRepository.findByDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } else if (game_name_bool) {
            Optional<Game> g = gameRepository.findByName(game_name);
            if (g.isPresent()) {
                return betRepository.findByGame(g.get());
            }
        } else if (client_id != null) {
            return betRepository.findByClient(clientRepository.getReferenceById(client_id));
        }
        return betRepository.findAll();

        // This could involve querying the database or filtering a preloaded data list
        // Return the filtered data as a List<YourEntity>
        // Example:
        // List<YourEntity> filteredData = yourRepository.findBySearchQuery(searchQuery);
    }
}

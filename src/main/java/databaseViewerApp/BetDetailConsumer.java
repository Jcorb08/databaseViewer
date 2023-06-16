package databaseViewerApp;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class BetDetailConsumer {
    @KafkaListener(topics = "bet_detail")
    public void consumeUserMessage(ConsumerRecord<String, String> record) {
        String message = record.value();
        // Parse the message JSON and check if bet amount is more than 1500
        if (betMoreThan(message)) {
            sendNotification();
        }
    }

    private boolean betMoreThan(String message){
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonObject = reader.readObject();
            Integer returns = jsonObject.getInt("returns");
            return returns >= 1500.00;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void sendNotification() {
        // Implement your notification logic here
        System.out.println("returns amount is >= 1500.00");
    }
}

package iss.nus.serverwatson.utils;

import java.io.StringReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Utils {
    
    public static LocalDateTime toLocalDateTime(Integer epochSecond) {
        LocalDateTime dateTime = Instant.ofEpochSecond(epochSecond)
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDateTime();
        return dateTime;
    }

    public static JsonObject toJson(String jsonString) {

        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        
        return json;
    }

}

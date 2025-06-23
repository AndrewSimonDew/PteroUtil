package org.andrexserver.playerUtil.pterodactyl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParseException;
import org.andrexserver.playerUtil.Main;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class PteroAPI {


    public String power(String url,String apiKey, String serverUUID, PowerAction action) {
        if(serverUUID == null) {
            return "§a[§6PteroUtil§a] §cInvalid Server name!";
        }
        StatsResponse stats = parseResources(url, apiKey, serverUUID);
        if(stats == null) {
            return "§a[§6PteroUtil§a] §cInvalid Response from REST API";
        }
        boolean state = stats.attributes.current_state.equalsIgnoreCase("running");
        if(state && action == PowerAction.START) {
            return "§a[§6PteroUtil§a] §cError: Server already running!";
        }
        if(!state && (action == PowerAction.STOP || action == PowerAction.RESTART)) {
            return "§a[§6PteroUtil§a] §cError: Server already shutdown!";
        }


        HttpClient client = HttpClient.newHttpClient();
        String json = "{\"signal\":\""+ action.getActionName() +"\"}";
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url+"/api/client/servers/" + serverUUID + "/power"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.body().length() > 10) {
                Main.instance.logger.warn("Response: " + response.body());
                Main.instance.logger.error("Attempted to " + action.getActionName() + " " + serverUUID + "\nServer with that UUID does not exist!");
                return "§a[§6PteroUtil§a] §cAttempted to " + action.getActionName() + " " + serverUUID + "\nServer with that UUID does not exist!\nCheck config file!";
            }
            return "§a[§6PteroUtil§a] §aSuccessfully sent §6" + action.getActionName() + " §ato §6" + serverUUID;
        } catch (IOException | InterruptedException | URISyntaxException e) {
            return "§a[§6PteroUtil§a] §cAn Error happened: Invalid Request!";
        }
    }




    public StatsResponse parseResources(String url,String apiKey,String serverUUID) throws JsonParseException {
        if(serverUUID == null) {
            return null;
        }
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url+"/api/client/servers/" + serverUUID + "/resources"))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), StatsResponse.class);

        } catch (IOException | InterruptedException | URISyntaxException e) {
            Main.instance.logger.error("Invalid Request!");
            return null;
        }

    }

    public String resolveServerName(String serverName) throws IllegalArgumentException {
        if(!Main.serverList.containsKey(serverName)) {
            return null;
        }
        return (String) Main.serverList.get(serverName);
    }
}


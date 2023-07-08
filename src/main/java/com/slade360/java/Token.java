package com.slade360.java;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Token {
    static String url = "https://accounts.multitenant.slade360.co.ke/oauth2/token/";
    static Dotenv dotenv = Dotenv.configure().load();
    static String clientId = dotenv.get("CLIENT_ID"); // Substitute with your client_id
    static String clientSecret = dotenv.get("CLIENT_SECRET"); // Substitute with your client_secret
    static String username = dotenv.get("EMAIL"); // Your email
    static String password = dotenv.get("PASSWORD"); // Your healthcloud account password

    public static void getToken(TokenCallback callback) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            String urlParameters = "grant_type=password" +
                    "&client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8) +
                    "&client_secret=" + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8) +
                    "&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8) +
                    "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8);

            // Set request headers
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Send POST request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            String responseData =  " ";
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                responseData = response.toString();
                // Process the JSON response data as needed
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(responseData);
                    String accessToken = jsonNode.get("access_token").asText();
                    callback.onTokenReceived(accessToken);  // Invoke the callback
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Request failed with response code: " + responseCode);
            }
        } catch (Exception e) {
           e.printStackTrace();
        }


    }

}

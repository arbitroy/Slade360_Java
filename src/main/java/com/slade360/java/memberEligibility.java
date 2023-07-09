package com.slade360.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class memberEligibility {


    public static String getMemberDets(String memberId, String sladeCode) throws Exception {
        URL url = new URL("https://provider-edi-api.multitenant.slade360.co.ke/v1/beneficiaries/member_eligibility/?member_number=" + memberId + "&payer_slade_code=" + sladeCode);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        Token.getToken(bearerToken -> connection.setRequestProperty("Authorization", "Bearer " + bearerToken));
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("HTTP response code: " + responseCode);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String responseBody = reader.readLine();
        reader.close();
        return responseBody;

    }

}

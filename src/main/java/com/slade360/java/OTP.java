package com.slade360.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OTP {
    public static String  getOtp(String contactId) throws Exception {
        URL url = new URL("https://provider-edi-api.multitenant.slade360.co.ke/v1/beneficiaries/beneficiary_contacts/"+contactId+"/send_otp/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        Token.getToken(bearerToken -> con.setRequestProperty("Authorization", "Bearer " + bearerToken));

        int respCode = con.getResponseCode();
        if (respCode != 200){
            throw new Exception("HTTP response code: "+respCode);
        }
        BufferedReader read = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String responseBody = read.readLine();
        read.close();
        return responseBody;
    }
}

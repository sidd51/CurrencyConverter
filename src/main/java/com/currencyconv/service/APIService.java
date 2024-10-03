package com.currencyconv.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import org.json.JSONObject;

public class APIService {
    public static String fromcurr;
    public static String tocurr;
    public static int amt;

    public static float getResult() throws IOException {
        String lfromcurr = fromcurr.toLowerCase();
        String GET_URL = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/" + lfromcurr + ".json";

        HttpURLConnection httpClient = (HttpURLConnection) new URL(GET_URL).openConnection();
    httpClient.setRequestMethod("GET");

        StringBuffer response = new StringBuffer();
        int responseCode = httpClient.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
        String inputLine;
            while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

            JSONObject obj = new JSONObject(response.toString());
            JSONObject ratesObject = obj.getJSONObject(lfromcurr);
            float exchangerate = ratesObject.getFloat(tocurr.toLowerCase());
            float result= 0;
            result = exchangerate * amt;
            return result;
        } else {
            throw new IOException("GET request failed with response code " + responseCode);
        }
}
}
package main;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;

public class CapitalFinder {

    public static void main(String[] args) {
        // Main loop to handle user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a country name or code (or type 'exit' to quit):");
        String input;
        while (!(input = scanner.nextLine()).equalsIgnoreCase("exit")) {
            System.out.println("Capital: " + getCapitalCity(input));
            System.out.println("\nEnter another country name or code (or type 'exit' to quit):");
        }
        scanner.close();
    }

    private static String getCapitalCity(String countryNameOrCode) {
        String apiUrl = "https://restcountries.com/v3.1/name/" + countryNameOrCode;
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(apiUrl);
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONArray jsonArray = new JSONArray(responseBody);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                return jsonObject.getJSONArray("capital").getString(0);
            } else if (response.getStatusLine().getStatusCode() == 404) {
                return "Country not found. Please try a different name or code.";
            } else {
                return "Error: Unable to process the request.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}

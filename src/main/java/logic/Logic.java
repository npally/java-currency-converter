package logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.math3.util.Precision;

import java.util.*;

public class Logic {
    private String url;
    private Map<String, String> codes;

    public Logic() {
        this.url = "https://api.exchangeratesapi.io/latest?";
        String[] currencyCodes = new String[]{"Canadian Dollar", "CAD", "Hong Kong Dollar", "HKD", "Iceland Krona", "ISK",
                "Philippine Peso", "PHP", "Danish Krone", "DKK", "Forint", "HUF",
                "Czech Koruna", "CZK", "Great British Pound", "GBP", "Romanian Leu", "RON",
                "Swedish Krona", "SEK", "Rupiah", "IDR", "Indian Rupee", "INR", "Brazilian Real", "BRL",
                "Russian Ruble", "RUB", "Kuna", "HRK", "Yen", "JPY", "Thai Baht", "THB", "Swiss Franc", "CHF",
                "Euro", "EUR", "Malaysian Ringgit", "MYR", "Bulgarian Lev", "BGN", "Turkish Lira", "TRY",
                "Yuan Renminbi", "CNY", "Norwegian Krone", "NOK", "New Zealand Dollar", "NZD", "Rand", "ZAR",
                "US Dollar", "USD", "Mexican Peso", "MXN", "Singapore Dollar", "SGD", "Singapore Dollar", "AUD",
                "New Israeli Sheqel", "ILS", "Won", "KRW", "Polish Zloty", "PLN"};

        Map<String, String> codes = new HashMap<String, String>();
        for (int i=0; i < currencyCodes.length; i += 2) {
            codes.put(currencyCodes[i], currencyCodes[i+1]);
        }
        this.codes = codes;
    }
    public Map<String, String> getCodes() {
        return codes;
    }

    public void setCodes(Map<String, String> codes) {
        this.codes = codes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Double> getJsonData(String source) throws UnirestException {
        HttpResponse <JsonNode> response = Unirest.get(source).asJson();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response.getBody().toString());
        String prettyJsonString = gson.toJson(je);
        Map<String, Object> result = new Gson().fromJson(prettyJsonString, Map.class);
        return (Map<String, Double>) result.get("rates");
    }

    public void cleanPrint(Map<String, Double> results) {
        for (String key : results.keySet()) {
            double amount = Precision.round(results.get(key), 4);
            System.out.println(key + ": " + amount);
        }
    }

    public void getAllRates(String base) throws UnirestException {
        String source = getUrl() + "base=" + base;
        Map<String, Double> results;
        results = getJsonData(source);
        cleanPrint(results);
    }

    public void getSpecificRates(String base, String other) throws UnirestException {
        String source = getUrl() + "base=" + base + "&symbols=" + other;
        Map<String, Double> results;
        results = getJsonData(source);
       cleanPrint(results);
    }

    public void getAmount(String base, String other, double amount) throws UnirestException {
        String source = getUrl() + "base=" + base + "&symbols=" + other;
        Map<String, Double> results;
        results = getJsonData(source);
        double rate = results.get(other);
        double newAmount = amount * rate;
        System.out.println(other + ": " + String.format("%.2f", newAmount));
    }

    public void printCodes() {
        for (String key : getCodes().keySet()) {
            System.out.println(key + ": " + getCodes().get(key));
        }
    }

}

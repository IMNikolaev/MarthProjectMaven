package api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import java.io.IOException;


public class CurrencyAPI {

    public Double getCurrencyByName(String currency) {
        String apiKey = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
        OkHttpClient client = new OkHttpClient();
        String targetCurrency = currency.toUpperCase();

        Request request = new Request.Builder()
                .url("https://open.er-api.com/v6/latest/EUR?apikey=" + apiKey)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String jsonResponse = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONObject rates = jsonObject.getJSONObject("rates");
                double exchangeRate = rates.getDouble(targetCurrency);
                return exchangeRate;

            } else {
                System.out.println("Ошибка при получении данных: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
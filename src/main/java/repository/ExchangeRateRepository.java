package repository;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Класс для хранения информации об изменении курса
class ExchangeRateChange {
    private LocalDate date;
    private double rate;

    public ExchangeRateChange(LocalDate date, double rate) {
        this.date = date;
        this.rate = rate;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "Дата " + date +
                " Курс " + rate;
    }
}

public class ExchangeRateRepository {
    // мапа ключ - код валюты, значение - курс к базе евро.
    private Map<String, Double> exchangeRates; // История изменений курсов валют
    private Map<String, List<ExchangeRateChange>> exchangeRatesHistory; //TODO Вдруг смогу допишу чтение и запись
    private File file;

    public ExchangeRateRepository(File file) {
        this.exchangeRates = new HashMap<>();
        this.exchangeRatesHistory = new HashMap<>();
        this.file = file;

        if (file.exists()){
            ratesFormFile();
        }
    }

    private void ratesFormFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null){
                String[] parts = line.split(":");
                if (parts.length ==2){
                    String currency = parts[0];
                    Double rate = Double.parseDouble(parts[1]);
                    exchangeRates.put(currency, rate);
                }
            }
        }
        catch (IOException e){
        }
    }

    private void ratesToFile (){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            for (Map.Entry<String, Double> entry : exchangeRates.entrySet()){
                writer.write(entry.getKey()+ ":"+ entry.getValue());
                writer.newLine();
            }
        }
        catch (IOException e){
        }
    }

    // Инициализация начальных курсов
    // Получение курса валюты.
    public double getRate(String currency) {
        return exchangeRates.getOrDefault(currency, 0.0);
    }

    // Установка курса валюты.
    public void setRate(String currency, double rate) {
        exchangeRates.put(currency, rate);
        trackRateChange(currency, rate); // Отслеживаем изменение курса
        ratesToFile();
    }

    // Удаление курса валюты.
    public void removeRate(String currency) {
        exchangeRates.remove(currency);
        //exchangeRatesHistory.remove(currency); // Удаляем и историю изменений
    }

    // Обновляем курсы валют, переданные в мапе
    public void updateRates(Map<String, Double> newRates) {
        newRates.forEach((currency, rate) -> {
            exchangeRates.put(currency, rate);
            trackRateChange(currency, rate); // Отслеживаем каждое изменение курса
        });
    }

    //  метод для добавления записи об изменении курса в историю
    private void trackRateChange(String currency, double newRate) {
        exchangeRatesHistory.computeIfAbsent(currency, k -> new ArrayList<>())
                .add(new ExchangeRateChange(LocalDate.now(), newRate));
    }

    // Метод для получения истории изменений курса валюты
    public List<ExchangeRateChange> getRateHistory(String currency) {
        return exchangeRatesHistory.getOrDefault(currency, new ArrayList<>());
    }
}
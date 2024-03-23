package service;

import api.CurrencyAPI;
import model.Currency;
import repository.CurrencyRepository;
import repository.ExchangeRateRepository;

import java.util.ArrayList;

public class CurrencyService {
    private CurrencyRepository currencyRepository;
    private CurrencyAPI currencyAPI;
    private ExchangeRateRepository exchangeRateRepository;
    public CurrencyService(CurrencyRepository currencyRepository,
                           CurrencyAPI currencyAPI,
                           ExchangeRateRepository exchangeRateRepository) {
        this.currencyRepository = currencyRepository;
        this.currencyAPI = currencyAPI;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public void setCurrency (String currencyName, String currencyFullName) {
        double currencyRateByEuro = currencyAPI.getCurrencyByName(currencyName);
        Currency currency = new Currency(currencyName,currencyFullName,currencyRateByEuro);
        currencyRepository.addCurrency(currency);
        exchangeRateRepository.setRate(currencyName,currencyRateByEuro);
    }

    public ArrayList<Currency> getAllCurrency() {
        return currencyRepository.getAllCurrency();
    }

    public void setNewCurrencyRate(String currency, double currencyRate) {
        currencyRepository.setNewCurrencyRate(currency,currencyRate);
    }
}

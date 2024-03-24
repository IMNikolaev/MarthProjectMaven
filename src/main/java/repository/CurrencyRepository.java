package repository;

import model.Currency;

import java.util.ArrayList;

public class CurrencyRepository {
    private ArrayList<Currency> currencies = null;

    public CurrencyRepository(){
        this.currencies = new ArrayList<>();
    }

    public void addCurrency(Currency currency) {
        currencies.add(currency);
    }

    public ArrayList<Currency> getAllCurrency() {
        return currencies;
    }

    public void setNewCurrencyRate(String currency, double currencyRate) {
        for (int i = 0; i < currencies.size(); i++) {
            if(currencies.get(i).getCurrencyName().equals(currency)){
                currencies.get(i).setChangeRateByEuro(currencyRate);
            }
        }
    }

    public void removeRate(String currency) {
        for (int i = 0; i < currencies.size(); i++) {
            if (currencies.get(i).getCurrencyName().equals(currency)){
                currencies.remove(i);
            }
        }
    }
}

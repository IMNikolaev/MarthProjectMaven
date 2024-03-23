package model;

public class Currency {
    private String currencyName; //USD
    private String currencyFullName; //Dollar
    private double changeRateByEuro;

    public Currency(String currencyName, String currencyFullName, double changeRateByEuro) {
        this.currencyName = currencyName;
        this.currencyFullName = currencyFullName;
        this.changeRateByEuro = changeRateByEuro;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyFullName() {
        return currencyFullName;
    }

    public void setCurrencyFullName(String currencyFullName) {
        this.currencyFullName = currencyFullName;
    }

    public double getChangeRateByEuro() {
        return changeRateByEuro;
    }

    public void setChangeRateByEuro(double changeRateByEuro) {
        this.changeRateByEuro = changeRateByEuro;
    }

    @Override
    public String toString() {
        return "Валюта " + currencyFullName +
                " Курс " + changeRateByEuro;
    }
}


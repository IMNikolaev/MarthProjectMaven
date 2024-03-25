package model;

import java.util.HashMap;
import java.util.Map;

public class Account {
    private long id;

    public void setAccounts(String currency, Double count) {
        accounts.put(currency, count);
    }

    private Map<String,Double> accounts; //Валюта и Сумма

    public Account(long id) {
        this.id = id;
        this.accounts = new HashMap<>();
        accounts.put("EUR",0.0);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<String, Double> getAccounts() {
        return accounts;
    }

    public void moneyDeposit(String currency, Double count) {
        Double value = accounts.get(currency);
        if (value!=null) {
            accounts.put(currency, count + value);
        }
        else accounts.put(currency, count);
    }
    public boolean moneyWithdraw(String currency, Double count) {
        Double value = accounts.get(currency);
        if (value!=null && value >= count) {
            accounts.put(currency,value - count);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Пользователь ").append(id).append(": ");
        sb.append(", счёт ").append("[");
        for (Map.Entry<String, Double> entry : accounts.entrySet()) {
            sb.append(entry.getKey()).append(" ").append(String.format("%.2f", entry.getValue())).append(". ");
        }
        if (!accounts.isEmpty()) {
            sb.setLength(sb.length() - 2); // Удаление последней точки и пробела
        }
        sb.append("]");
        return sb.toString();
    }
}

package repository;

import model.Account;

import java.util.ArrayList;
import java.util.Map;

public class AccountRepository {
    private ArrayList<Account> accounts = null;

    public AccountRepository(){
        this.accounts= new ArrayList<>();
    }

    public void create (Account account) {
        accounts.add(account);
    }

    public Map<String,Double> getBalance(long userId) {
        for (int i = 0; i < accounts.size(); i++) {
            if(accounts.get(i).getId()==userId){
                return accounts.get(i).getAccounts();
            }
        }
        return null;//СДЕЛАТЬ ОШИБКОЙ!!!
    }

    public boolean cashDeposit(long id, String currency, double count) {
        for (int i = 0; i < accounts.size(); i++) {
            if(accounts.get(i).getId()==id){
                accounts.get(i).moneyDeposit(currency,count);
                return true;
            }
        }
        return false;
    }

    public boolean cashWithdraw(long id, String currency, double count) {
        for (int i = 0; i < accounts.size(); i++) {
            if(accounts.get(i).getId()==id){
                return accounts.get(i).moneyWithdraw(currency,count);
            }
        }
        return false;
    }

    public boolean createCurrencyAccount(long id, String newCurrency) {
        for (int i = 0; i < accounts.size(); i++) {
            if(accounts.get(i).getId()==id){
                if (accounts.get(i).getAccounts().containsKey(newCurrency)){
                    return false;//выкидывать ошибкой)
                }
                else {
                    accounts.get(i).setAccounts(newCurrency,0.0);
                }
            }
        }
        return false;
    }

    public double closeCurrencyAccount(long id, String currency, boolean isReady, double exchangeRate) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getId() == id) {
                Map<String, Double> account = accounts.get(i).getAccounts();
                if (account.containsKey(currency)) {
                    Double value = account.get(currency);
                    if (value == 0.0) {
                        account.remove(currency);
                        return 0;
                    } else if (value>0 && !isReady) {
                        return -1;
                    } else if (value>0 && isReady){
                            return value;
                    }
                } else {
                    account.put(currency, 0.0);
                }
            }
        }
        return -2;
    }

    public boolean transferMoney(long id, String currencyFrom, String currencyTo, double count,double exchangeRate) {
        for (int i = 0; i < accounts.size(); i++) {
            Map<String, Double> account = accounts.get(i).getAccounts();
            if(accounts.get(i).getId()==id){
                if(account.get(currencyFrom) >= count){
                    return true;
                }
            }
            else return false;
        }
        return false;
    }
}

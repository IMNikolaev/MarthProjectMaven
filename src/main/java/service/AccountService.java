package service;

import model.Account;
import model.Operation;
import model.OperationType;
import repository.AccountRepository;
import repository.CurrencyRepository;
import repository.ExchangeRateRepository;
import repository.OperationRepository;

import java.util.ArrayList;
import java.util.Map;

public class AccountService {

    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    private ExchangeRateRepository exchangeRateRepository;
    private CurrencyRepository currencyRepository;
    private int commission;

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }

    public AccountService(AccountRepository accountRepository,
                          OperationRepository operationRepository,
                          ExchangeRateRepository exchangeRateRepository,
                          CurrencyRepository currencyRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.currencyRepository = currencyRepository;
    }

    public Map<String,Double> balance (long userId){ //Вывести баланс по всем счетам
        return accountRepository.getBalance(userId);
    }

    public void createAccount (long id){ //Создать новый банковский аккаунт
        Account account = new Account(id);
        accountRepository.create(account);
    }

    private void addOperation(long userId, String operationCurrency,double operationSum, OperationType type){//Добавить операцию в список операций
        Operation operation = new Operation(userId,operationCurrency,operationSum,type);
        operationRepository.add(operation);
    }

    public boolean cashDeposit(long id, String currency, double count,OperationType type) {//Положить деньги на счет
        boolean indicator = accountRepository.cashDeposit(id,currency,count);
        if (indicator){
            addOperation(id,currency,count,type);
        }
        return indicator;
    }
    public boolean cashWithdraw(long id, String currency, double count, OperationType type) {//Снять деньги со счета
        boolean indicator = accountRepository.cashWithdraw(id,currency,count);
        if (indicator){
            addOperation(id,currency,count,type);
        }
        return indicator;
    }

    public ArrayList<Operation> getListOperations (long id){ //Список ВСЕХ ОПЕРАЦИЙ!
        return operationRepository.getListOperations(id);
    }
    public ArrayList<Operation> getListOperationsByCurrency(String currency) {
        return operationRepository.getListOperationsByCurrency(currency);
    }

    public boolean createCurrencyAccount(long id, String newCurrency) { //Создать счет в валюте
        return accountRepository.createCurrencyAccount(id, newCurrency);
    }


    public int closeCurrencyAccount(long id, String currency, boolean isReady) {//Закрыть счет в валюте
        double exchangeRate = exchangeRateRepository.getRate(currency);
        double result = accountRepository.closeCurrencyAccount(id,currency,isReady,exchangeRate);
        if (result == 0) return 0; //Закрыт
        else if (result == -1) return 1; //Человек не готов закрыть аккаунт так как там деньги)
        else if (result == -2) return -1; //Проблема с закрытием
        else if(result >0){
            cashWithdraw(id,currency, result, OperationType.DEBIT);
            cashDeposit(id,"EUR", result / exchangeRate, OperationType.DEPOSIT);
            accountRepository.closeCurrencyAccount(id,currency,isReady,exchangeRate);
            return 0; //Закрыт
        }
        return -1;//Проблема с закрытием
    }

    public ArrayList<Account> getAllAccounts(){
        return accountRepository.getAccounts();
    }

    public boolean transferMoney(long id, String currencyFrom,String currencyTo,double count){ //Перевести деньги
        double exchangeRateFrom = exchangeRateRepository.getRate(currencyFrom);
        double exchangeRateTo = (1/exchangeRateFrom) * exchangeRateRepository.getRate(currencyTo);
        double commissionBank = (count/100)*commission;
        if (accountRepository.transferMoney(id,currencyFrom,currencyTo,(count-commissionBank) ,exchangeRateTo)){
            cashWithdraw(id,currencyFrom,count,OperationType.TRANSFER);
            cashDeposit(id,currencyTo,(count*exchangeRateTo), OperationType.DEPOSIT);
            cashDeposit(0,currencyFrom,commissionBank,OperationType.DEPOSIT);
            return true;
        }
        return false;
    }


}

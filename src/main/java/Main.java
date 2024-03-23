import UI.MenuStart;
import api.CurrencyAPI;
import model.Operation;
import model.OperationType;
import repository.*;
import service.AccountService;
import service.CurrencyService;
import service.UserService;

import java.time.LocalTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        CurrencyAPI currencyAPI = new CurrencyAPI();
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        AccountRepository accountRepository = new AccountRepository();
        OperationRepository operationRepository = new OperationRepository();
        ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepository();
        CurrencyRepository currencyRepository = new CurrencyRepository();
        CurrencyService currencyService = new CurrencyService(currencyRepository,currencyAPI,exchangeRateRepository);
        AccountService accountService = new AccountService(accountRepository,operationRepository, exchangeRateRepository,currencyRepository);

        userRepository.createAdmin("admin","admin");
        System.out.println(userService.getAllUsers());
        userService.authorize("admin", "admin");
        System.out.println(userService.getAuthorizeUser());
        accountService.createAccount(0);
        System.out.println(accountService.balance(0));
        currencyService.setCurrency("EUR", "EURO");
        currencyService.setCurrency("USD", "DOLLAR");
        currencyService.setCurrency("RUB", "RUBBLE");
        System.out.println(currencyService.getAllCurrency());
        //currencyService.setNewCurrencyRate("USD", 2.1);
        System.out.println(currencyService.getAllCurrency());
        accountService.createAccount(1);
        System.out.println(accountService.balance(1));

        accountService.cashDeposit(1,"EUR", 200.0, OperationType.DEPOSIT);
        System.out.println(accountService.balance(1));


        accountService.cashDeposit(1,"EUR", 100.0, OperationType.DEPOSIT);
        System.out.println(accountService.balance(1));
        accountService.createCurrencyAccount(1,"USD");
        accountService.cashWithdraw(1,"EUR", 50.0, OperationType.CASH);
        System.out.println(accountService.balance(1));
        accountService.cashDeposit(1,"USD", 50.0, OperationType.DEPOSIT);
        System.out.println(accountService.balance(1));
        System.out.println(accountService.balance(1));
        accountService.closeCurrencyAccount(1, "USD", false);
        System.out.println(accountService.balance(1));
        accountService.transferMoney(1,"EUR", "RUB",0);
        accountService.cashDeposit(1,"USD", 50.0, OperationType.CASH);
        accountService.transferMoney(1,"EUR","RUB",1);
        System.out.println(accountService.balance(1));
        accountService.transferMoney(1,"USD","RUB",1);
        System.out.println(accountService.balance(1));
        System.out.println(currencyService.getAllCurrency());
        System.out.println("Ваш список операций");
        ArrayList<Operation> toPrint= (accountService.getListOperations(1));
        for (int i = 0; i < toPrint.size(); i++) {
            System.out.println(toPrint.get(i));
        }
        System.out.println("Ваш список операций в Долларах");
        System.out.println(accountService.getListOperationsByCurrency("USD"));

        loading();
        MenuStart menuStart = new MenuStart(accountService,currencyService , userService);
        menuStart.run();

    }


        public static void clearConsole() {
            // Очищаем консоль с помощью печати символа новой строки много раз
            for (int i = 0; i < 50; ++i) System.out.println();
        }
    public static void loading() {
        for (int i = 0; i < 50; ++i) System.out.println();
        LocalTime nightStop = LocalTime.of(4,0);
        LocalTime morning = LocalTime.of(11,0);
        LocalTime midDay = LocalTime.of(16,0);
        LocalTime evening = LocalTime.of(23,0);
        LocalTime localTime = LocalTime.now();
        if (localTime.isBefore(nightStop)){
            System.out.println("Доброй ночи");
        } else if (localTime.isAfter(nightStop) && localTime.isBefore(morning)) {
            System.out.println("Доброе утро");
        }else if (localTime.isAfter(morning) && localTime.isBefore(midDay)) {
            System.out.println("Добрый день");
        }else if (localTime.isAfter(midDay) && localTime.isBefore(evening)) {
            System.out.println("Добрый вечер");
        }else if (localTime.isAfter(evening)) {
            System.out.println("Доброй ночи");
        }
        for (int i = 0; i < 10; ++i) System.out.println();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


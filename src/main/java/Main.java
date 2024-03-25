import UI.MenuStart;
import api.CurrencyAPI;
import model.Operation;
import model.OperationType;
import repository.*;
import service.AccountService;
import service.CurrencyService;
import service.UserService;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        File file = new File("src/main/java/DB/ExchangeRate.txt");

        CurrencyAPI currencyAPI = new CurrencyAPI();
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        AccountRepository accountRepository = new AccountRepository();
        OperationRepository operationRepository = new OperationRepository();
        ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepository(file);
        CurrencyRepository currencyRepository = new CurrencyRepository();
        CurrencyService currencyService = new CurrencyService(currencyRepository,currencyAPI,exchangeRateRepository);
        AccountService accountService = new AccountService(accountRepository,operationRepository, exchangeRateRepository,currencyRepository);

        userRepository.createAdmin("admin","admin");
        userService.authorize("admin", "admin");
        accountService.createAccount(0);
        currencyService.setCurrency("EUR", "EURO");
        currencyService.setCurrency("USD", "DOLLAR");
        currencyService.setCurrency("RUB", "RUBBLE");

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


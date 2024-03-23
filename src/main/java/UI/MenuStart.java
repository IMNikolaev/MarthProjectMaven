package UI;

import model.Currency;
import model.Role;
import model.User;
import repository.CurrencyRepository;
import service.AccountService;
import service.CurrencyService;
import service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuStart {

    private final Scanner scanner = new Scanner(System.in);
    private final AccountService accountService;
    private final CurrencyService currencyService;
    private final UserService userService;


    String boldTextStart = "\033[1m";
    String boldTextEnd = "\033[0m";
    public static final String RESET_COLOR = "\u001B[0m";
    public static final String COLOR_RED = "\u001B[31m";
    public static final String COLOR_GREEN = "\u001B[32m";
    public static final String COLOR_YELLOW = "\u001B[33m";
    public static final String COLOR_BLUE = "\u001B[34m";


    public MenuStart(AccountService accountService, CurrencyService currencyService, UserService userService) {
        this.accountService = accountService;
        this.currencyService = currencyService;
        this.userService = userService;
    }

    public void run() {
        menuStart();
    }



    public void menuStart() { // MENU - START
        while (true) {
            System.out.println(COLOR_BLUE + "=============ПРИВЕТСТВУЕМ В БАНКЕ = ОПЯТЬ РАБОТАТЬ =============" + RESET_COLOR);
            System.out.println(COLOR_YELLOW + "1" + RESET_COLOR + "->" + boldTextStart + "Авторизация" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "2" + RESET_COLOR + "->" + boldTextStart + "Регистрация" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "3" + RESET_COLOR + "->" + boldTextStart + "Курс валют" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "0" + RESET_COLOR + " ->" + boldTextStart + "ВЫХОД" + boldTextEnd);


            //запрашиваем выбор пользователя
            System.out.println(COLOR_YELLOW + "\nСделайте ваш выбор :" + RESET_COLOR);
            int input = scanner.nextInt();
            scanner.nextLine();

            if (input == 0) {
                System.out.println("Вы покидаете наш банк - Мы будем Скучать!");
                System.exit(0); // завершение работа приложения
            }
            menuStartOpen(input);
        }
    }


    private void menuStartOpen(int input) {
        System.out.println("=========Введите ваши данные для входа в Онлайн банкинг!===========");
        switch (input) {
            case 1:
                System.out.println(COLOR_GREEN + "Введите ваш email:" + RESET_COLOR);
                String email = scanner.nextLine();

                System.out.println(COLOR_GREEN + "Введи Ваш пароль:" + RESET_COLOR);
                String password = scanner.nextLine();
                if (userService.authorize(email,password) !=null){
                    System.out.println(COLOR_GREEN + "Вы успешно вошли в систему!" + RESET_COLOR);
                    if (userService.getAuthorizeUser() != null) {
                        if (userService.getAuthorizeUser().getRole().equals(Role.BLOCKED)){
                            System.out.println(COLOR_RED + "Вы ЗАБЛОКИРОВАННЫ - вы плохой Клиент" + RESET_COLOR);
                            userService.activeUserFalse();
                            run();
                        }
                        System.out.println(userService.getAuthorizeUser());
                        MenuUser menuUser = new MenuUser(accountService,currencyService,userService,userService.getAuthorizeUser().getId());
                        menuUser.menuUserStart();
                    }
                }
                System.out.println(COLOR_RED + "Не верный ввод попробуйте еще раз" + RESET_COLOR);
                menuStart();
//                }

                break;
// РЕГИСТРАЦИЯ В СИСТЕМЕ
            case 2:
                System.out.println(COLOR_GREEN + "Введите ваш email:" + RESET_COLOR);
                String emailNew = scanner.nextLine();

                System.out.println(COLOR_GREEN + "Введи Ваш пароль:" + RESET_COLOR);
                String passwordNew = scanner.nextLine();
                User user = userService.registerUser(emailNew,passwordNew);
                System.out.println(userService.getAllUsers());
                System.out.println(user);
                System.out.println(user.getId());
                accountService.createAccount(user.getId());
                break;

            case 3:
                LocalDate localDate = LocalDate.now();
                System.out.println(COLOR_GREEN + "Курс валют на: "+ localDate + RESET_COLOR);
                printCurrency();
                break;

            default:
                System.out.println(COLOR_RED + "\nНе верный ввод попробуйте еще раз" + RESET_COLOR);
                waitRead();
                break;
        }

    }

    private void printCurrency(){
        ArrayList<Currency> currencies = currencyService.getAllCurrency();
        for (int i = 0; i < currencies.size(); i++) {
            System.out.print(currencies.get(i).getCurrencyName() + " ");
            System.out.println(Math.round(currencies.get(i).getChangeRateByEuro()*100.0)/100.0);
        }
    }


    private void waitRead() {
        System.out.println("\nДля продолжения пожалуйста нажмите " + COLOR_RED + "ENTER ..." + RESET_COLOR);
        scanner.nextLine();
    }

}


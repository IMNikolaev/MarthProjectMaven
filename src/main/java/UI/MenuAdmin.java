package UI;

import model.Currency;
import service.AccountService;
import service.CurrencyService;
import service.UserService;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuAdmin {


    private final Scanner scanner = new Scanner(System.in);

    private AccountService accountService;
    private CurrencyService currencyService;
    private UserService userService;
    private final long id;

    public MenuAdmin(AccountService accountService, CurrencyService currencyService, UserService userService, long id) {
        this.accountService = accountService;
        this.currencyService = currencyService;
        this.userService = userService;
        this.id = id;
    }


    public void run() {
        menuAdminStart();
    }

    // ВЫБОР ЦВЕТА
    String boldTextStart = "\033[1m";
    String boldTextEnd = "\033[0m";
    public static final String RESET_COLOR = "\u001B[0m";
    public static final String COLOR_BLACK = "\u001B[30m";
    public static final String COLOR_RED = "\u001B[31m";
    public static final String COLOR_GREEN = "\u001B[32m";
    public static final String COLOR_YELLOW = "\u001B[33m";
    public static final String COLOR_BLUE = "\u001B[34m";
    public static final String COLOR_PURPLE = "\u001B[35m";
    public static final String COLOR_CYAN = "\u001B[36m";
    public static final String COLOR_WHITE = "\u001B[37m";

    // НАЧАЛО МЕНЮ
    public void menuAdminStart() { // MENU - USER
        System.out.println(COLOR_BLUE + "============= МЕНЮ АДМИНИМТРАТОРА =============" + RESET_COLOR);
        while (true) {

            System.out.println(COLOR_YELLOW + "1" + RESET_COLOR + "->" + boldTextStart + " Изменение курса валюты" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "2" + RESET_COLOR + "->" + boldTextStart + " Добавление валюты" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "3" + RESET_COLOR + "->" + boldTextStart + " Удаление валюты" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "4" + RESET_COLOR + "->" + boldTextStart + " Список доступных для обмена валют" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "5" + RESET_COLOR + "->" + boldTextStart + " Заблокировать пользователя по ID" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "6" + RESET_COLOR + "->" + boldTextStart + " Просмотр истории операций пользователя" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "7" + RESET_COLOR + "->" + boldTextStart + " Просмотр статистики операций по валюте" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "8" + RESET_COLOR + "->" + boldTextStart + " Просмотр истории курсов по валюте" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "9" + RESET_COLOR + "->" + boldTextStart + " Добавить комиссию на операцию" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "0" + RESET_COLOR + "->" + boldTextStart + " Вернутся в user menu" + boldTextEnd);


            //запрашиваем выбор пользователя
            System.out.println(COLOR_YELLOW + "\nСделайте ваш выбор :" + RESET_COLOR);
            int input = scanner.nextInt();
            scanner.nextLine();

            if (input == 0) {
                MenuUser menuUser = new MenuUser(accountService,currencyService,userService,userService.getAuthorizeUser().getId());
                menuUser.menuUserStart();
            }
            menuStartAdminOpen(input);
        }
    }

    private void menuStartAdminOpen(int input) {
        System.out.println("==========================================");
        switch (input) {
            case 1: // Изменение курса валюты
                currencyService.setNewCurrencyRate(selectCurrency(),selectCount());
                waitRead();
                break;
            case 2: // Добавление валюты
                String fullName = scanner.nextLine();
                currencyService.setCurrency(selectCurrency(),fullName);
                waitRead();
                break;
            case 3: // Удаление валюты
                waitRead();
                break;
            case 4: // Список доступных для обмена валют
                System.out.println(currencyService.getAllCurrency());
                waitRead();
                break;
            case 5: // Заблокировать пользователя по ID
                System.out.println(userService.getAllUsers());
                int userNummber = scanner.nextInt();
                userService.blockUser(userNummber);
                System.out.println("Пользователь с ID " + userNummber + " заблокирован");
                // TODO БЛОК МЕТОД
                waitRead();
                break;
            case 6: // Просмотр истории операций пользователя
                System.out.println(userService.getAllUsers());
                int userNum = scanner.nextInt();
                accountService.getListOperations(userNum);
                waitRead();
                break;
            case 7: // Просмотр статистики операций по валюте
                accountService.getListOperationsByCurrency(selectCurrency());
                waitRead();
                break;
            case 8: // Просмотр истории курсов по валюте
                //TODO НАПИСАТЬ!!!
                waitRead();
                break;
            case 9: // Добавить комиссию на операцию
                System.out.println("Выберите процент комиссии");
                int commission  = scanner.nextInt();
                // TODO прикрутить комиссию
                waitRead();
                break;
            default:
                System.out.println(COLOR_RED + "Ваш выбор не корректен, попробуйте еще раз!" + RESET_COLOR);
                waitRead();

        }
    }
    private String selectCurrency (){
        Scanner scanner = new Scanner(System.in);
        System.out.println(COLOR_YELLOW + "Выберите валюту: " + RESET_COLOR);
        ArrayList<Currency> currencies = currencyService.getAllCurrency();
        for (int i = 0; i < currencies.size(); i++) {
            System.out.println(currencies.get(i).getCurrencyName());
        }
        String currency = scanner.nextLine();
        for (int i = 0; i < currencies.size(); i++) {
            if(currencies.get(i).getCurrencyName().equals(currency)){
                return currency;
            }
        }
        System.out.println(COLOR_RED + "Неверный ввод. Пожалуйста, выберите доступную валюту." + RESET_COLOR);
        selectCurrency();
        return null;
    }
    private double selectCount (){
        Scanner scanner = new Scanner(System.in);
        System.out.println(COLOR_YELLOW + "Введите желаемую сумму" + RESET_COLOR);
        return scanner.nextDouble();
    }



    private void waitRead() {
        System.out.println("\nДля продолжения пожалуйста нажмите " + COLOR_RED + "ENTER ..." + RESET_COLOR);
        scanner.nextLine();
    }

}

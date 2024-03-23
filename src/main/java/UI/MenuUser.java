package UI;

import model.Currency;
import model.OperationType;
import model.Role;
import service.AccountService;
import service.CurrencyService;
import service.UserService;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuUser {

    private final Scanner scanner = new Scanner(System.in);
    private  AccountService accountService;
    private CurrencyService currencyService;
    private UserService userService;
    private final long id;


    public MenuUser(AccountService accountService, CurrencyService currencyService, UserService userService, long id) {
        this.accountService = accountService;
        this.currencyService = currencyService;
        this.userService = userService;
        this.id = id;
    }

    public void run() {
        menuUserStart();
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
    public void menuUserStart() { // MENU - USER
        System.out.println(COLOR_BLUE + "=============ВАШ ЛИЧНЫЙ КАБИНЕТ =============" + RESET_COLOR);
        while (true) {

            System.out.println(COLOR_YELLOW + "1" + RESET_COLOR + "->" + boldTextStart + " Просмотр баланса" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "2" + RESET_COLOR + "->" + boldTextStart + " Пополнение счета в выбранной валюте" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "3" + RESET_COLOR + "->" + boldTextStart + " Снятие средств со счета" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "4" + RESET_COLOR + "->" + boldTextStart + " Открытие нового счета в выбранной валюте" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "5" + RESET_COLOR + "->" + boldTextStart + " Обмен валюты (перевод между своими счетами)" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "6" + RESET_COLOR + "->" + boldTextStart + " Просмотр истории операций" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "7" + RESET_COLOR + "->" + boldTextStart + " Закрытие счета в выбранной валюте" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "8" + RESET_COLOR + "->" + boldTextStart + " Просмотр истории курсов по валюте" + boldTextEnd);
            System.out.println(COLOR_YELLOW + "0" + RESET_COLOR + "->" + boldTextStart + " Выйти из аккаунта" + boldTextEnd);


            //запрашиваем выбор пользователя
            System.out.println(COLOR_YELLOW + "\nСделайте ваш выбор :" + RESET_COLOR);
            int input = scanner.nextInt();
            scanner.nextLine();

            if (input == 0) {
                System.out.println("До свидания!");
                userService.activeUserFalse();
                MenuStart menuStart = new MenuStart(accountService, currencyService, userService);
                menuStart.run();
            }
            menuStartUserOpen(input);
        }
    }


    private void menuStartUserOpen(int input) {
        System.out.println("==========================================");
        switch (input) {
            case 1: // Просмотр баланса
                System.out.println(accountService.balance(id));
                waitRead();
                break;
            case 2: // Пополнение счета в выбранной валюте
                accountService.cashDeposit(id,selectCurrency(), selectCount(), OperationType.DEPOSIT);
                waitRead();
                break;
            case 3: //Снятие средств со счета
                accountService.cashWithdraw(id,selectCurrency(),selectCount(),OperationType.CASH);
                waitRead();
                break;
            case 4: //Открытие нового счета в выбранной валюте
                accountService.createCurrencyAccount(id,selectCurrency());
                waitRead();
                break;
            case 5: //Обмен валюты (перевод между своими счетами)
                String firstCurrency = selectCurrency();
                String secondCurrency = selectCurrency();
                accountService.transferMoney(id ,firstCurrency ,secondCurrency ,selectCount());
                waitRead();
                break;
            case 6: //Просмотр истории операций
                System.out.println(accountService.getListOperations(id));
                waitRead();
                break;
            case 7: //Закрытие счета в выбранной валюте
                if (accountService.closeCurrencyAccount(id,selectCurrency(),false) ==1){
                    System.out.println(COLOR_RED + "На выбранном счету есть деньги!" + RESET_COLOR);
                    System.out.println(COLOR_RED + "Закрыть всё равно? (Выберете)" + RESET_COLOR);
                    System.out.println(COLOR_RED + "1. Закрыть (средства будут переведены на Euro счет)" + RESET_COLOR);
                    System.out.println(COLOR_RED + "2. Оставить счет)" + RESET_COLOR);
                    int inpt = scanner.nextInt();
                    switch (inpt){
                        case 1:
                            accountService.closeCurrencyAccount(id,selectCurrency(),true);
                            System.out.println(COLOR_YELLOW + "Аккаунт успешно закрыт" + RESET_COLOR);
                            waitRead();
                            break;
                        case 2:
                            break;
                        default:
                            System.out.println(COLOR_RED + "Ваш выбор не корректен, попробуйте еще раз!" + RESET_COLOR);
                            waitRead();
                    }
                }
                waitRead();
                break;
            case 8: //Просмотр истории курсов по валюте
                waitRead();
                break;
            case 99:
                if (userService.getAuthorizeUser().getRole()== Role.ADMIN) {
                    MenuAdmin menuAdmin = new MenuAdmin(accountService, currencyService, userService, userService.getAuthorizeUser().getId());
                    menuAdmin.menuAdminStart();
                }
            default:
                System.out.println(COLOR_RED + "Ваш выбор не корректен, попробуйте еще раз!" + RESET_COLOR);
                waitRead();

        }
    }

    private void waitRead() {
        System.out.println("\nДля продолжения пожалуйста нажмите " + COLOR_RED + "ENTER ..." + RESET_COLOR);
        scanner.nextLine();
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


}

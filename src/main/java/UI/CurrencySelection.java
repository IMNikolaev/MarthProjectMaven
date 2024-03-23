package UI;

import java.util.Map;
import java.util.Scanner;

public class CurrencySelection {

    private static final String boldTextStart = "\033[1m";
    private static final String boldTextEnd = "\033[0m";
    public static final String RESET_COLOR = "\u001B[0m";
    public static final String COLOR_RED = "\u001B[31m";
    public static final String COLOR_GREEN = "\u001B[32m";
    public static final String COLOR_YELLOW = "\u001B[33m";
    public static final String COLOR_BLUE = "\u001B[34m";

    public static void main(String[] args) {
        Map<Integer, String> currencies = Map.of(
                1, "USD",
                2, "RUR",
                3, "CNY",
                4, "UAH"
        );

        selectCurrency(currencies);
    }

    private static void selectCurrency(Map<Integer, String> currencies) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(boldTextStart + (COLOR_YELLOW + "Выберите валюту: " + RESET_COLOR) + boldTextEnd);
        for (Map.Entry<Integer, String> entry : currencies.entrySet()) {
            System.out.println(COLOR_GREEN + entry.getKey() + ". " + entry.getValue() + RESET_COLOR);
        }
        System.out.println(boldTextStart + (COLOR_BLUE + "Сделайте Ваш выбор -> " + RESET_COLOR) + boldTextEnd);

        int choice = scanner.nextInt();
        String selectedCurrency = currencies.getOrDefault(choice, null);

        if (selectedCurrency != null) {
            System.out.println(boldTextStart + (COLOR_BLUE + "Вы выбрали " + selectedCurrency + RESET_COLOR) + boldTextEnd);
        } else {
            System.out.println(COLOR_RED + "Неверный ввод. Пожалуйста, выберите доступную валюту." + RESET_COLOR);
        }

        scanner.close();
    }
}

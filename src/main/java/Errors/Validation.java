package Errors;

public class Validation {

    class InvalidEmailException extends Exception {
        public InvalidEmailException(String message) {
            super(message);
        }
    }

    class InvalidPasswordException extends Exception {
        public InvalidPasswordException(String message) {
            super(message);
        }
    }

    class InvalidCurrencyException extends Exception {
        public InvalidCurrencyException(String message) {
            super(message);
        }
    }


    public boolean emailValid(String email) throws InvalidEmailException {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]}$";
        if (!email.matches(emailRegex)) {
            throw new InvalidEmailException("Неверный формат Е-мейла");
        }
        return true;
    }

    public boolean passwordValid(String password) throws InvalidPasswordException {
        if (password.length() < 8 || !password.matches(".*\\d.*") || !password.matches(".*[a-zA-Z].*")) {
            throw new InvalidPasswordException("Неверный формат пароля");
        }
        return true;
    }

    public boolean currencyValid(String currency) throws InvalidCurrencyException {
        String[] validCurrencies = {"USD", "EUR", "RUR", "CNY", "UAH"};
        boolean isValid = false;
        for (String validCurrency : validCurrencies) {
            if (validCurrency.equals(currency)) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new InvalidCurrencyException("Неверный формат валюты");
        }
        return true;
    }




    //TODO для тестов
    public static void main(String[] args) {
        Validation validation = new Validation();
        String email = "example@example.@com";
        String password = "1";
        String currency = "UAH";

        try {
            if (validation.emailValid(email)) {
                System.out.println("Электронная адреса валидная");
            }
        } catch (InvalidEmailException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        try {
            if (validation.passwordValid(password)) {
                System.out.println("Пароль валидный");
            }
        } catch (InvalidPasswordException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        try {
            if (validation.currencyValid(currency)) {
                System.out.println("Валюта валидная");
            }
        } catch (InvalidCurrencyException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

}
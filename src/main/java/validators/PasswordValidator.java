package validators;

import exception.PasswordValidateException;

public class PasswordValidator {

    public static boolean validate(String password) throws PasswordValidateException {


        if (password.length() < 8) {
            throw new PasswordValidateException("Password length < 8");
        }

        boolean isLowerCase = false;
        boolean isUpperCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (int i = 0; i < password.length(); i++) {
            char a = password.charAt(i);

            if (Character.isLowerCase(a)) {
                isLowerCase = true;
                continue;
            }

            if (Character.isUpperCase(a)) {
                isUpperCase = true;
                continue;
            }

            if (Character.isDigit(a)) {
                hasDigit = true;
                continue;
            }

            if ("!%$@&*()[]".indexOf(a) >= 0) {
                hasSpecialChar = true;
                continue;

            }


            throw new PasswordValidateException("Invalid character found: " + a);
        }


        if (!isLowerCase) {
            throw new PasswordValidateException("Password should contain at least one lowercase letter.");
        }

        if (!isUpperCase) {
            throw new PasswordValidateException("Password should contain at least one uppercase letter.");
        }

        if (!hasDigit) {
            throw new PasswordValidateException("Password should contain at least one digit.");
        }

        if (!hasSpecialChar) {
            throw new PasswordValidateException("Password should contain at least one special character.");
        }

        return true; // Если все условия выполнены, возвращаем true
    }
}

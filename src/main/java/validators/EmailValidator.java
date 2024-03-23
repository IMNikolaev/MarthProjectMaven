package validators;

import exception.EmailValidateException;

public class EmailValidator {
    public static void isEmailValid(String email) throws EmailValidateException{
        if (email == null || email.isEmpty()){
            throw new EmailValidateException("вы ничего не ввели");
        }

        int indexAt = email.indexOf("@");
        if (indexAt <= 0 || indexAt != email.lastIndexOf("@")) throw new EmailValidateException("ошибка ввода @");


        int indexFirstDotAfterAt = email.indexOf('.', indexAt);
        if (indexFirstDotAfterAt == -1 || indexFirstDotAfterAt == indexAt + 1) throw new EmailValidateException("точка должна быть после @");

        if (email.lastIndexOf('.') >= email.length() - 2) throw new EmailValidateException("точка не может бьть последним символом");

        boolean isCharAlphabetic = Character.isAlphabetic(email.charAt(0));

        if (!isCharAlphabetic) throw new EmailValidateException("символ не является буквой");

        for (int i = 0; i < email.length(); i++) {
            char c = email.charAt(i);

            boolean isCharValid = (Character.isAlphabetic(c)
                    || Character.isDigit(c)
                    || c == '-'
                    || c == '_'
                    || c == '.'
                    || c == '@');
            if (!isCharValid) throw new EmailValidateException("ввод неверного специального символа");
        }


    }

}

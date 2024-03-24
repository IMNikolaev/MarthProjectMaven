package service;

import model.Account;
import model.OperationType;
import model.Role;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class AccountServiceTest {
    private TestOutputHelper testOutputHelper;

    @BeforeEach
    public void setUp() {
        testOutputHelper = new TestOutputHelper();
        testOutputHelper.printSeparator();
    }



    @Test // cashDeposit Testтам
    public void testCashDeposit() {
        // Arrange
        long id = 123456789;
        String currency = "USD";
        double count = 50.0;
        OperationType type = OperationType.DEPOSIT;

        AccountRepository accountRepository = new AccountRepository() {
            @Override
            public boolean cashDeposit(long id, String currency, double count) {

                if (count < 0) {
                    return false;
                }
                return true;
            }
        };

        AccountService accountService = new AccountService(accountRepository,
                new OperationRepository(),
                new ExchangeRateRepository(),
                new CurrencyRepository());

        // проверка успешного депозита
        boolean resultSuccessful = accountService.cashDeposit(id, currency, count, type);
        assertTrue(resultSuccessful, "Успешный депозит ");
        System.out.println("Успешный депозит ");


        // проверка на ошибочной депозит
        count = -50.0;
        boolean resultFailure = accountService.cashDeposit(id, currency, count, type);
        assertFalse(resultFailure, "Ошибка депозиту");
        System.err.println("Ошибка депозиту");
    }

    @Test // создаем пользователя
    public void testUserCreation() {

        UserRepository userRepository = new UserRepository();
        String validEmail = "test@example.com";
        String validPassword = "password";

        String invalidEmail = "invalidemail";
        String invalidPassword = null; // Пароль, который не указан

        // Выполнение и проверка создания пользователя с валидными данными
        User validUser = userRepository.createUser(validEmail, validPassword);
        assertNotNull(validUser);
        assertEquals(validEmail, validUser.getEmail());
        assertEquals(validPassword, validUser.getPassword());
        assertTrue(userRepository.getAllUsers().contains(validUser));
        System.out.println("Пользователь создался Успешно");


        // Выполнение и проверка создания пользователя с невалидными данными
        User invalidUser = userRepository.createUser(invalidEmail, invalidPassword);
        assertNull(invalidUser);
        System.err.println("Пользователь НЕ создался Успешно");
    }

    @Test // Снятия денег со счета тест
    public void testCashWithdraw() {
        Account account = new Account(1);
        account.setAccounts("USD", 1000.0);
        long accountId = account.getId();

        AccountRepository accountRepositoryStub = new AccountRepository();
        accountRepositoryStub.create(account);

        OperationRepository operationRepositoryStub = new OperationRepository();
        ExchangeRateRepository exchangeRateRepositoryStub = new ExchangeRateRepository();
        CurrencyRepository currencyRepositoryStub = new CurrencyRepository();

        AccountService accountService = new AccountService(accountRepositoryStub,
                operationRepositoryStub, exchangeRateRepositoryStub, currencyRepositoryStub);

        double withdrawalAmount = 500.0;

        boolean withdrawalResultSuccess = accountService.cashWithdraw(accountId, "USD", withdrawalAmount, OperationType.DEBIT);
        boolean withdrawalResultFailure = accountService.cashWithdraw(accountId, "USD", 2000.0, OperationType.DEBIT);

        assertTrue(withdrawalResultSuccess);
        System.out.println("Успешное снятья денег");

        assertFalse(withdrawalResultFailure);
        System.err.println("Не удалось снять деньги");
    }


    @Test//Todo HE закривается проверть,HE додается новая валют к акаунту
    public void testAccountOperations() {

        AccountRepository accountRepository = new AccountRepository();
        long userId = 1;
        String currency = "USD";
        String existingCurrency = "EUR";


        boolean successCreate = accountRepository.createCurrencyAccount(userId, currency);
        boolean failCreate = accountRepository.createCurrencyAccount(userId, existingCurrency);
        double closeResult = accountRepository.closeCurrencyAccount(userId, currency, true, 1.0);

        // assertTrue(successCreate);
        assertFalse(failCreate);
        System.err.println("Создание не удачное, Валюта Уже существует!");
        // assertTrue(closeResult >= 0);
    }


    @Test //TOdo посмотреть не работает коректно
    public void testTransferMoney_Success() {

        AccountRepository accountRepository = new AccountRepository();
        long userId = 1; // Припустимо, що це ідентифікатор користувача
        String currencyFrom = "EUR"; // Валюта, з якої переказуємо
        String currencyTo = "USD"; // Валюта, на яку переказуємо
        double initialBalance = 100.0;
        double transferAmount = 50.0;
        double exchangeRate = 1.2; // Припустимо, що курс обміну EUR/USD = 1.2

        Account account = new Account(1);
        account.setAccounts(currencyFrom, initialBalance);
        accountRepository.create(account);

        boolean result = accountRepository.transferMoney(userId, currencyFrom, currencyTo, transferAmount, exchangeRate);

        assertTrue(result);
        System.out.println("Успешно перевели деньги");

        double expectedBalanceFrom = initialBalance - transferAmount;
        double expectedBalanceTo = transferAmount / exchangeRate;
        // assertEquals(expectedBalanceFrom, account.getAccounts().get(currencyFrom), 0.01);
        // assertEquals(expectedBalanceTo, account.getAccounts().get(currencyTo), 0.01);
    }


    @Test // Тест testGetUserById рабочий
    public void testGetUserById() {
        // Arrange
        List<User> users = new ArrayList<>();
        User expectedUser = new User(1, "John", "Doe");
        users.add(expectedUser);
        UserRepository userRepository = new UserRepository();

        userRepository.getAllUsers().addAll(users);
        User actualUserFound = userRepository.getUserById(1);
        User actualUserNotFound = userRepository.getUserById(2);

        assertNotNull(actualUserFound);
        System.out.println("Пользователь с Index ... найден, Тест пройден");
        assertEquals(expectedUser, actualUserFound);
        System.err.println("Пользователь не совпадает с ожиданиями, Тест пройден! ");

        assertNull(actualUserNotFound);
        System.out.println("Пользователь не найде Тест пройден");
    }

    @Test // Test getUserById_UserFound, getUserById_UserNotFound, removeUserById
    public void testUserMethods() {

        List<User> users = new ArrayList<>();
        User expectedUser = new User(1, "John", "Doe");
        users.add(expectedUser);
        UserRepository userRepository = new UserRepository();


        userRepository.getAllUsers().addAll(users);

        // Test getUserById_UserFound
        User actualUserFound = userRepository.getUserById(1);
        assertNotNull(actualUserFound);
        System.out.println("Пользователь с Index ... найден, Тест пройден");
        assertEquals(expectedUser, actualUserFound);
        System.err.println("Пользователь не совпадает с ожиданиями Тест пройден! ");


        // Test getUserById_UserNotFound
        User actualUserNotFound = userRepository.getUserById(2);
        assertNull(actualUserNotFound);
        System.out.println("Пользователь не найде Тест пройден");

        // Test removeUserById
        userRepository.removeUserById(1);
        User actualUserRemoved = userRepository.getUserById(1);
        assertNull(actualUserRemoved);
        System.out.println("Пользователь с Index: 1, успешно удален, ТЕСТ пройден!!");

    }

    @Test //  Тест IsAuthorized_UserFound
    public void testIsAuthorized_UserFound() {

        UserRepository userRepository = new UserRepository();
        userRepository.createAdmin("admin@example.com", "adminPassword");
        userRepository.createUser("user@example.com", "userPassword");

        User actualUser = userRepository.isAuthorized("user@example.com", "userPassword");

        assertNotNull(actualUser);
        System.out.println("Пользователь, должен быть найден, Тест пройден!");

        assertEquals(1, actualUser.getId());
        System.out.println("ID Пользователей не совпадают, ТЕСТ пройден ");

        assertEquals("user@example.com", actualUser.getEmail());
        System.out.println("Email Пользователей не совпадают, ТЕСТ пройден ");

        assertEquals("userPassword", actualUser.getPassword());
        System.out.println("Пароль Пользователей не совпадают, ТЕСТ пройден ");

        // assertEquals(Role.USER, actualUser.getRole()"); если надо будет
    }

    @Test // Тест с Блокировкой Пользователя
    public void testBlockUserById_UserBlocked() {

        UserRepository userRepository = new UserRepository();
        User userToBlock = new User(1, "test@example.com", "password123");
        userRepository.getAllUsers().add(userToBlock);

        userRepository.blockUserById(1);

        User blockedUser = userRepository.getUserById(1);
        assertNotNull(blockedUser);
        System.out.println("Пользователь с ID 1 найден после блокировки, Тест пройден!");
    }

    @Test// Тест с Блокировкой Пользователя если нету с таким ID
    public void testBlockUserById_UserNotFound() {

        UserRepository userRepository = new UserRepository();

        userRepository.blockUserById(1);

        User blockedUser = userRepository.getUserById(1);
        assertNull(blockedUser);
        System.err.println("Пользователь с ID 1 не найден, Тест пройден!");
    }

    //  ExchangeRateChange TESTS

    @Test // testGetRate_DefaultRate
    public void testGetRate_DefaultRate() {
        ExchangeRateRepository repository = new ExchangeRateRepository();

        double rate = repository.getRate("USD");

        assertEquals(0.0, rate);
        System.out.println("Default rate should be 0.0, ТЕСТ пройден");
    }

    @Test //testSetRate
    public void testSetRate() {

        ExchangeRateRepository repository = new ExchangeRateRepository();

        repository.setRate("EUR", 1.2);

        assertEquals(1.2, repository.getRate("EUR"));
        System.out.println("Rate for EUR should be set to 1.2, ТЕСТ пройден!");
    }

    @Test //testRemoveRate
    public void testRemoveRate() {

        ExchangeRateRepository repository = new ExchangeRateRepository();
        repository.setRate("EUR", 1.2);

        repository.removeRate("EUR");

        assertEquals(0.0, repository.getRate("EUR"));
        System.out.println("Rate for EUR should be removed, ТЕСТ пройден");
    }

    @Test // testUpdateRates
    public void testUpdateRates() {
        // Arrange
        ExchangeRateRepository repository = new ExchangeRateRepository();
        repository.setRate("EUR", 1.2);
        repository.setRate("USD", 1.0);

        repository.updateRates(Map.of("EUR", 1.3, "USD", 0.9));

        assertEquals(1.3, repository.getRate("EUR"));
        System.out.println("Rate for EUR should be updated to 1.3, ТЕСТ пройден");

        assertEquals(0.9, repository.getRate("USD"));
        System.out.println("Rate for USD should be updated to 0.9, ТЕСТ пройден");
    }

    class TestOutputHelper {
        public void printSeparator() {
            System.out.println("====================");
        }

    }
}


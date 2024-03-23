Тут будет описание проекта!!!

Регистрация с валидацией

USER

ID
email
password
status
isAdmin

Счет

Map <Валюта, Сколько денег> Запиcь в Файл и чтение с файла
Изменение курса валюты Тоже в файл

Model
    User (ID, Email, Password, Status, isAdmin)
    Account (ID=UserID, Balance(Map)(Ключ = Валюта)(Значение = сумма))
    Currency (Name ,LongName, Курс относительно Евро)
    Курс Валюты (Name, Курс, Дата)
    Операция (Id операции, Сумма, Валюта, Время, Тип операции, ID=UserID)
Repository
    UserRepository
                
        
    AccountRepository
    CurrencyRepository
Service
    UserService
            Создать пользователя
            удалить пользователя
            заблокировать пользователя
            сделать пользователя админом
            войти в аккаунт
            выйти из аккаунта
            
    AccountService
    CurrencyService 

    ПРИМЕР!!
    (Double сумма, Валюта1, Валюта 2)
    return (Double) сумму во второй валюте
DB
    История изменения Курса Валюты
    История операций
UI
    Меню User (Bank menu)
    Menu Admin
    Menu Start
Ошибки
    Ошибки


ID 1 
Map(Name, sum)

usd 200
eur 100

Игорь = 
Дмитрий = Currency
Михаил = Ошибки (emailValid, passwordValid, currencyValid)
Ярослав = UI (Меню) 
Андрей = Пользователи 


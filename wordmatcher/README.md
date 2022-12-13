# wordmatcher

### About

Сервис, предоставляющий возможность расставлять предложения в правильном порядке :)

### Requirements

- Installed [h2](https://www.h2database.com/html/main.html) database
- Java не ниже 17

### Features

- start() - Запуск пользовательского интерфейса
- getSentanse() - Запрос на базу данных для предложения, которое пользователю надо будет ввести. (У пользователя есть 3 попытки)

### Example
```
WordMatcher start
Hello, Ctacek. You use service of word matcher!
MENU:
1. Start
2. End
   1
   Write one of the topic's names: eat, it, sport, weather
   it
   [to] [each] [have] [a] [personal] [is] [Nowadays,] [student] [computer]
   Nowadays, each student is to have a personal computer
   
   true
   
   MENU:
1. Start
2. End
```


### Developer

Yudov Stanislav

[Telegram](https://t.me/CTACE4EK)

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
Good try
```
WordMatcher start
Hello, Ctacek. You use service of word matcher!
MENU:
1. Start
2. End
-> 1
Write one of the topic's names: eat, it, sport, weather
-> it
[is] [table] [which] [desk] [personal] [a] [desktop] [or] [a] [computer] [A] [to] [is] [placed] [on] [computer] [be] 
-> A desktop computer is a personal computer which is to be placed on a desk or table

Congratulations, that's right
MENU:
1. Start
2. End
-> 2
Goodbye ^_^
```
Bad try
```
WordMatcher start
Hello, Ctacek. You use service of word matcher!
MENU:
1. Start
2. End
-> 1
Write one of the topic's names: eat, it, sport, weather
-> eat
[eat] [We] [food] [healthy] [need] [time] [right] [at]
-> f
Wrong, try again! You have 2 attempts
-> f
Wrong, try again! You have 1 attempts
-> f

Today is not your day :c
```

### Database

#### Таблица sentences

Внутри таблицы хранятся предложения, разбитые на темы. Предложения добавляются заранее, но в разработке
добавление преложений и их модерация.

Таблица имеет следующие столбцы:
- id - Номер вопроса, заполняется автоматически.
- topic - Тема вопроса. Не может быть NULL
- sentence - Предложение. Не может быть NULL
- secontcorrect - Вторая вариация правильного предложения 
(Часто английские предложения имеют два и более различных способов написания) Может быть NULL
- (NUMBERcorrect () Могут быть NULL) - В планах


### Developer

Yudov Stanislav

[Telegram](https://t.me/CTACE4EK)

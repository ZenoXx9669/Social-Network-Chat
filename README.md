# Social Network Chat
Social Network Chat - Программа построена на архитектуре MVC (Model-View-Controller), где:
- Model отвечает за обработку данных и бизнес-логику.
- View представляет собой пользовательский интерфейс, отображающий данные пользователям.
- Controller обрабатывает пользовательские запросы, управляет взаимодействием между моделью и представлением.

Основная цель программы — предоставить пользователям возможность общаться друг с другом в режиме реального времени. Пользователи могут отправлять сообщения, просматривать список других активных пользователей и взаимодействовать через удобный интерфейс чата. Программа поддерживает WebSocket для передачи сообщений, что делает обмен информацией мгновенным и эффективным.


## Оглавление

- [Технологии](#технологии)
- [Запуск проекта](#запуск-проекта)
- [Структура проекта](#структура-проекта)
- [API Эндпоинты](#api-эндпоинты)
- [Безопасность](#безопасность)
- [Тестирование](#тестирование)
- [Контакты](#контакты)

## Технологии

- Java 17
- Spring Boot 3.3.2
- Spring Security
- Spring Data JPA
- Spring WEB
- Thymeleaf
- Hibernate
- PostgresSQL
- Liquibase
- MailSender
- Lombok
- Gradle

## Запуск проекта

### Docker
1. Запустите docker-compose.yml:
#### Если у вас не добавлен PostgresSQL
```
docker pull postgres
```
#### Запуск docker-compose
```
docker-compose up
```
### GitHub
1. Клонируйте репозиторий:

   ```bash
   git clone https://github.com/ZenoXx9669/TechShop.git
2. Откройте проект в вашей среде разработки (например, IntelliJ IDEA).

3. Настройте доступ к базе данных в файле application.properties или application.yml:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5433/YourDB
    spring.datasource.username=yourUsername
    spring.datasource.password=yourPassword
    ```
4. Откройте браузер(например:Chrome,Yandex,Microsoft Edge)
5. Приложение будет доступно по адресу: http://localhost:8891.(Рекомендую использовать разные браузеры для две пользователя)
## Структура проекта
- auth: содержит классы для управления пользователями, их авторизацией и ролями.
- models: сущности базы данных, такие как User,ChatMessage,ChatRoom,Permission.
- repositories: интерфейсы для взаимодействия с базой данных.
- view: содержит HTML-шаблоны для отображения данных пользователям (например, страницы чата, входа, регистрации).
- controllers: контроллеры для обработки HTTP-запросов.
- config: конфигурации Spring Security и другие необходимые настройки.
- ## API Эндпоинты
### Пользователи
- http://localhost:8891/register — регистрация нового пользователя.
- http://localhost:8891/verify-code — для подтверждение электронный почты.
- http://localhost:8891/login — авторизация пользователя.
### Чат
После успешного авторизации вас отправить в localhost:8891/chat,где вы можете отправить сообщение,общяться другими пользователями.

## Безопасность
### Проект использует Spring Security для аутентификации и авторизации. Реализованы роли:

- USER — базовый пользователь.
- ADMIN — админ.
## Тестирование
### Для тестирования приложения используется любой браузер. Для выполнения тестов выполните:
1. Войдите через ссылку(http://localhost:8891/login)
#### Авторизация
- Login and password for passing the test
```
   olzhas@gmail.com
```
```
   123
```
---
```
   zhalgas@gmail.com
```
```
   456
```
---
```
   esenov@gmail.com
```
```
   789
```
## Контакты
### Если у вас возникли вопросы или предложения, свяжитесь со мной по электронной почте: [olzhasjakenov@gmail.com](#olzhasjakenov@gmail.com)


# Thanks for watching!
# Спасибо, что посмотрели!

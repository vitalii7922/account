# Тестовое задание
Реализация java API для выполнения функций:
1) поиск в репозитории учётной записи (УЗ) по имени (имя УЗ — уникальный атрибут);

2) изменения у учётной записи фамилии.

В качестве репозитория использовать любую СУБД, взаимодействие с базой должно идти через jdbc.

На реализацию должны быть написаны unit-тесты (Mockito).

Проект должен собираться с помощью maven.
______
Используемые технологии и библиотеки в проекте:
* Java 11.0.4
* СУБД MySQL
* Lombok 1.8.12
* Spark Framework 2.5.4
* Maven
* JUnit 4.11
* Mockito 3.6.0
------
Для того чтобы запустить тесты, в папке проекта где находится pom.xml ввести:\
`mvn test `
------
Таблица запросов для взаимодествия с веб-сервером:

Метод        | URL          |Описание
------------- | ------------- |---------------------------------|
GET           | /api/account?firstName=value     |Запросить УЗ по имени  
POST  | /api/account  |Добавить учетную запись 
PUT  | /api/account?firstName=value&lastName=value |Обновить фамилию у УЗ c указанным именем
DELETE  | /api/account?firstName=value           |Удалить УЗ по указанному имени
-----------
##Примеры запросов пользователя к веб-серверу:
### Request
        POST /api/account
        http://localhost:8080/api/account
        {
        "firstName": "John",
        "lastName": "Black"
        }
   
### Response
    Status: 200 OK
        {
         "firstName": "John",
         "lastName": "Black"
        }     
___
### Request
        GET /api/account?firstName=John
        http://localhost:8080/api/account?firstName=John
### Response
    Status: 200 OK
        {
         "firstName": "John",
         "lastName": "Black"
        }
_____
### Request
        GET /api/account?firstName=Alex
        http://localhost:8080/api/account?firstName=Alex
### Response
    Status: 404 Not Found
        {
            "message": "Account with name Alex not found"
        }
_____
### Request
        DELETE /api/account?firstName=Alex
        http://localhost:8080/api/account?firstName=Alex
### Response
    Status: 200 OK
        {
            "message": "Account with first name Alex has been deleted"
        }

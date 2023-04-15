
# DigitLibrary  
the graduation project for TeachMeSkills    

Descriptions:   

DigitLibrary is an online library service that allows users to upload and download books, publish and read articles,  
and subscribe to the service.  


# Tests  
Before using the application, it is recommended to run tests to check its functionality. This can be done by selecting  
the StockMarket -> src -> test -> java folder and pressing Ctrl + Shift + F10,   
or by right-clicking and selecting "Run 'All Tests'".  


# Database  
DigitLibrary, the graduation project for TeachMeSkills

Description:
DigitLibrary is an online library service that allows users to upload and download books, publish and read articles, and subscribe to the service.

Tests:
Before using the application, it is recommended to run tests to check its functionality. This can be done by selecting the StockMarket -> src -> test -> java folder and pressing Ctrl + Shift + F10, or by right-clicking and selecting "Run 'All Tests'".

Database:
The main database used in this project is PostgresSQL. To connect to the database, you need to install PostgresSQL  
and configure the connection in the application.properties file:  
<pre>
spring.datasource.username=postgres
spring.datasource.password=root
spring.datasource.url=jdbc:postgresql://localhost:5432/digit_library_project_db
spring.datasource.driver-class-name=org.postgresql.Driver
</pre>
The database contains 6 tables:  
users - stores all user information, including login, password, name, email, and phone number.  
roles - stores user roles. There are three possible roles in the application: USER, ADMIN, SUBSCRIBER.  
books - stores information about books, including the author's ID and the file name for cloud storage.  
articles - stores the creation date, text of the article, and author ID.  
orders - stores the order status, payment method, creation date, user ID, and book ID.  

The application uses Flyway database migration. You can find the V1__create_tables.sql file in the   
src/main/resources/db/migration folder, where you can write scripts to create the database, as well  
as the V2__insert_tables.sql file for their initialization.


# Registration  
The security of this application is ensured by Spring Basic Security. To register, you need to pass the json format:  
(POST method: {"email": "test99@gmail.com","login": "user","name": "Alex","password": "password","phoneNumber": "1234567"})  
to "http://localhost:8080/registration", after which the user will be created and added to the database with ROLE USER   
and an encrypted password. The password can be changed by sending a POST request   
to "http://localhost:8080/user/updatePassword/{id}" and passing the new password in json format.   
The login can also be changed by sending a POST request to "http://localhost:8080/user/updateLogin.  


# Additionally:  
Docker is connected in the project, and docker-compose is configured. You can download the images of this application  
with the command: docker pull asil88/digitlibrary:v9. The application also uses Dropbox cloud storage, access to which  
can be obtained through the token provided during application registration on the site.   
The token can be specified in the DropBoxService.



# User Endpoints:

**GET http://localhost:8080/user/findAll**  
Описание: Получить список всех пользователей  
Права доступа: USER, ADMIN, SUBSCRIBER  
Параметры: Нет  
Коды ответа:  
200 OK - Возвращает список всех пользователей  
404 Not Found - Если список пользователей пуст  

**GET http://localhost:8080/user/{id}**
Описание: Получить информацию о пользователе по ID  
Права доступа: USER, ADMIN, SUBSCRIBER  
Параметры: ID - идентификатор пользователя  
Коды ответа:  
200 OK - Возвращает информацию о пользователе  
404 Not Found - Если пользователь не найден  

**PUT http://localhost:8080/user/updateInfo**  
Описание: Обновить информацию о пользователе  
Права доступа: USER, ADMIN, SUBSCRIBER  
Параметры: Объект User (в json формате)    
Коды ответа:  
204 No Content - Если информация о пользователе успешно обновлена  
409 Conflict - Если при валидации объекта пользователя произошла ошибка  

**PUT http://localhost:8080/user/updatePassword/{id}**  
Описание: Обновить пароль пользователя по ID  
Права доступа: USER, ADMIN, SUBSCRIBER  
Параметры: ID - идентификатор пользователя, newPassword - новый пароль(в json формате)    
Коды ответа:  
204 No Content - Если пароль пользователя успешно обновлен  
404 Not Found - Если пользователь не найден  

**PUT http://localhost:8080/user/updateLogin/{id}**  
Описание: Обновить логин пользователя по ID  
Права доступа: USER, ADMIN, SUBSCRIBER  
Параметры: ID - идентификатор пользователя, newLogin - новый логин (в json формате)    
Коды ответа:  
204 No Content - Если логин пользователя успешно обновлен  
404 Not Found - Если пользователь не найден  

**DELETE http://localhost:8080/user/{id}**  
Описание: Удалить пользователя по ID  
Права доступа: ADMIN  
Параметры: ID - идентификатор пользователя  
Коды ответа:  
204 No Content - Если пользователь успешно удален  
404 Not Found - Если пользователь не найден  

Прописанные права доступа:
Для удаления пользователя требуется роль ADMIN
Для обновления информации о пользователе и получения информации о пользователе по ID требуются  
роли USER, ADMIN, SUBSCRIBER. Для всех операций необходимо иметь соответствующую роль. Если роль   
пользователя не соответствует требуемой, сервер вернет ошибку 403 Forbidden.





# Order endpoints:  

**GET http://localhost:8080/order/findAll**   
Описание: Получить список всех заказов   
Права доступа: USER, ADMIN, SUBSCRIBER   
Параметры: Нет  
Коды ответа:  
200 OK - Возвращает список всех заказов  
404 Not Found - Если список заказов пуст  

**GET http://localhost:8080/order/{id}**  
Описание: Получить информацию о заказе по ID   
Права доступа: USER, ADMIN, SUBSCRIBER   
Параметры: ID - идентификатор заказа   
Коды ответа:   
200 OK - Возвращает информацию о заказе  
404 Not Found - Если заказ не найден   

**POST http://localhost:8080/order/add**  
Описание: Добавить заказ  
Права доступа: USER, ADMIN, SUBSCRIBER  
Параметры: Объект OrderRequest (в json формате)    
Коды ответа:  
204 No Content - Если информация о заказе успешно обновлена  
409 Conflict - Если при валидации объекта заказа произошла ошибка  

**PUT http://localhost:8080/order/update**  
Описание: Обновить информацию о заказе  
Права доступа: USER 
Параметры: Объект Order  (в json формате)  
Коды ответа:  
204 No Content - Если информация о заказе успешно обновлена  
409 Conflict - Если при валидации объекта заказа произошла ошибка

**PUT http://localhost:8080/order/status/{id}/{status}**  
Описание: Обновить информацию статуса в заказе  
Права доступа: USER
Параметры: ID - идентификатор заказа , status - на выбор ( EXPECTED, ACCEPTED, PROCESSED, FINISHED ) 
Коды ответа:  
204 No Content - Если информация о заказе успешно обновлена  
404 Not Found - Если заказ не найден  

**PUT http://localhost:8080/order/paymentMethod/{id}/{paymentMethod}**  
Описание: Обновить информацию метода оплаты в заказе  
Права доступа: USER 
Параметры: ID - идентификатор заказа , paymentMethod - на выбор ( Qiwi, WebMoney, VISA, MasterCard, Bitcoin, Naturoi )
Коды ответа:  
204 No Content - Если информация о заказе успешно обновлена  
404 Not Found - Если заказ не найден  

**DELETE http://localhost:8080/order/{id}**  
Описание: Удалить заказ по ID  
Права доступа: ADMIN  
Параметры: ID - идентификатор заказа  
Коды ответа:  
204 No Content - Если заказ успешно удален    
404 Not Found - Если заказ не найден  

Если роль заказа не соответствует требуемой, сервер вернет ошибку 403 Forbidden.





# Author endpoints:

**GET http://localhost:8080/author/findAll**   
Описание: Получить список всех авторов   
Права доступа: USER, ADMIN, SUBSCRIBER   
Параметры: Нет  
Коды ответа:  
200 OK - Возвращает список всех авторов  
404 Not Found - Если список авторов пуст  

**GET http://localhost:8080/author/{id}**  
Описание: Получить информацию о авторе по ID   
Права доступа: USER, ADMIN, SUBSCRIBER   
Параметры: ID - идентификатор автора   
Коды ответа:   
200 OK - Возвращает информацию о авторе  
404 Not Found - Если автор не найден

**POST http://localhost:8080/author/add**  
Описание: Добавить автора  
Права доступа: USER, ADMIN, SUBSCRIBER  
Параметры: Объект AuthorRequest  (в json формате)  
Коды ответа:  
204 No Content - Если информация о авторе успешно обновлена  
409 Conflict - Если при валидации объекта автора произошла ошибка

**PUT http://localhost:8080/author/update**  
Описание: Обновить информацию о авторе  
Права доступа: USER, ADMIN, SUBSCRIBER  
Параметры: Объект Author  (в json формате)  
Коды ответа:  
204 No Content - Если информация о авторе успешно обновлена  
409 Conflict - Если при валидации объекта автора произошла ошибка

**DELETE http://localhost:8080/author/{id}**  
Описание: Удалить заказ по ID  
Права доступа: ADMIN  
Параметры: ID - идентификатор заказа  
Коды ответа:  
204 No Content - Если автор успешно удален  
404 Not Found - Если автор не найден

Если роль автора не соответствует требуемой, сервер вернет ошибку 403 Forbidden.
 




# Article endpoints:

**GET http://localhost:8080/article/findAll**   
Описание: Получить список всех статей   
Права доступа: USER, ADMIN, SUBSCRIBER   
Параметры: Нет  
Коды ответа:  
200 OK - Возвращает список всех статей  
404 Not Found - Если список статей пуст

**GET http://localhost:8080/article/{id}**  
Описание: Получить информацию о авторе по ID   
Права доступа: USER, ADMIN, SUBSCRIBER   
Параметры: ID - идентификатор статьи   
Коды ответа:   
200 OK - Возвращает информацию о статьи  
404 Not Found - Если статья не найдена

**POST http://localhost:8080/article/add**  
Описание: Добавить статью  
Права доступа: USER, ADMIN, SUBSCRIBER  
Параметры: Объект ArticleRequest  (в json формате)  
Коды ответа:  
204 No Content - Если информация о статье успешно обновлена  
409 Conflict - Если при валидации объекта статьи произошла ошибка

**PUT http://localhost:8080/article/update**  
Описание: Обновить информацию о статье  
Права доступа: USER, ADMIN, SUBSCRIBER  
Параметры: Объект Article  (в json формате)  
Коды ответа:  
204 No Content - Если информация о статье успешно обновлена  
409 Conflict - Если при валидации объекта статьи произошла ошибка

**DELETE http://localhost:8080/article/{id}**  
Описание: Удалить статью по ID  
Права доступа: ADMIN  
Параметры: ID - идентификатор статьи  
Коды ответа:  
204 No Content - Если статья успешно удалена  
404 Not Found - Если статья не найдена

Если роль статьи не соответствует требуемой, сервер вернет ошибку 403 Forbidden.


# Book endpoints:

**GET http://localhost:8080/book/findAll**   
Описание: Получить список всех книг   
Права доступа: USER, ADMIN, SUBSCRIBER   
Параметры: Нет  
Коды ответа:  
200 OK - Возвращает список всех книг  
404 Not Found - Если список книг пуст

**GET http://localhost:8080/book/{id}**  
Описание: Получить информацию о книге по ID   
Права доступа: USER, ADMIN, SUBSCRIBER   
Параметры: ID - идентификатор книги   
Коды ответа:   
200 OK - Возвращает информацию о книге  
404 Not Found - Если книга не найдена

**POST http://localhost:8080/book/add**  
Описание: Добавить книгу  
Права доступа: USER, ADMIN, SUBSCRIBER  
Параметры: Объект BookRequest  (в json формате)  
Коды ответа:  
204 No Content - Если информация о книге успешно обновлена  
409 Conflict - Если при валидации объекта книги произошла ошибка

**PUT http://localhost:8080/book/update**  
Описание: Обновить информацию о книге  
Права доступа: USER, ADMIN, SUBSCRIBER  
Параметры: Объект Book  (в json формате)  
Коды ответа:  
204 No Content - Если информация о книге успешно обновлена  
409 Conflict - Если при валидации объекта книги произошла ошибка  

**DELETE http://localhost:8080/book/{id}**  
Описание: Удалить книгу по ID  
Права доступа: ADMIN  
Параметры: ID - идентификатор книги  
Коды ответа:  
204 No Content - Если книга успешно удалена  
404 Not Found - Если книга не найдена  

**PUT http://localhost:8080/book/price/{id}/{price}**  
Описание: Обновить информацию по стоимости книги  
Права доступа: ADMIN    
Параметры: ID - идентификатор книги, price - новая цена  
Коды ответа:  
204 No Content - Если стоимость книги успешно обновлена    
404 Not Found - Если книга не найдена  

**PUT http://localhost:8080/book/genre/{id}/{genre}**
Описание: Обновить информацию по жанру книги  
Права доступа: ADMIN    
Параметры: ID - идентификатор книги, genre - на выбор ( Drama, FairyTale, Fantasy, Fiction, Folklore, Historical )
Коды ответа:  
204 No Content - Если жанр  книги успешно обновлен    
404 Not Found - Если книга не найдена

**PUT http://localhost:8080/book/availability/{id}/{availability}**  
Описание: Обновить информацию по доступность книги  
Права доступа: ADMIN    
Параметры: ID - идентификатор книги, availability - на выбор (InStock, NotAvailable, OnOrder)  
Коды ответа:  
204 No Content - Если доступность книги успешно обновлена    
404 Not Found - Если книга не найдена


**POST http://localhost:8080/book/upload**  
Описание: Загрузить книгу на облачный сервер DropBoxService
Права доступа: USER, ADMIN, SUBSCRIBER  
Параметры:  обьект MultipartFile   
Коды ответа:  
200 OK - Если книге успешно загружена на сервер  
500 Internal Server Error - Если при загрузке книги произошла ошибка

**GET http://localhost:8080/book/{id}**  
Описание: Скачать  книгу с облачного хранилища  
Права доступа: USER, ADMIN, SUBSCRIBER   
Параметры: fileName
Коды ответа:   






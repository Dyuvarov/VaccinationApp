## Инстументы:
:small_blue_diamond: Java 11  
:small_blue_diamond: Spring Boot  
:small_blue_diamond: Spring Cloud (OpenFeign, Eureka)  
:small_blue_diamond: Spring Data  
:small_blue_diamond: Kafka  
:small_blue_diamond: Docker  
:small_blue_diamond: PostgreSQL  
:small_blue_diamond: Maven  

##Описание
Приложение для хранения информации о вакцинации населения, QR-кодов вакцинированных, персональных данных вакцинированных.  
Состоит из 6 микросервисов:  
:heavy_check_mark: [Person API](person-api/README.md) - для работы с персональными данными вакцинированных    
:heavy_check_mark: [Medical API](medical-api/README.md) - для работы с информацией о вакцинации  
:heavy_check_mark: [QR service](qr-service/README.md) - для генерации и хранения QR-кодов  
:heavy_check_mark: [API gateway](api-gateway/README.md) - для маршрутизации запросов, поступающих в систему  
:heavy_check_mark: [Service registry](service-registry/README.md) - для мониторинга микросервисов  
  
![architecture](architecture.jpeg)  
  
Все запросы из внешнего мира поступают в API gateway, откуда они перенаправляются в нужный сервис.  
Сервисы взаимодействуют между собой с помощью feign-клиентов.


##Запуск
1) docker-compose build
2) docker-compose up
# Проект Social Media API
* собрать проект maven `$MAVEN_HOME/bin/mvn clean package`
* создать Docker-образ приложения `docker build -t project-api .`
* запустить сервисы через docker-compose `docker-compose up -d`
* в браузере открыть ссылку `http://localhost:8080/api/v1/swagger-ui/index.html`
* остановить приложение `docker-compose down`


# Из требований не реализован 2 пункт "Взаимодействие пользователей"
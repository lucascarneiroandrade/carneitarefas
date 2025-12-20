.PHONY: mysql build run up down

APP_NAME=tarefas
PROFILE=docker
JAR=build/libs/$(APP_NAME)-0.0.1-SNAPSHOT.jar
MYSQL_SERVICE=mysql

mysql:
	docker-compose up -d $(MYSQL_SERVICE)
	@echo "⏳ Aguardando MySQL na porta 3306..."
	@until nc -z localhost 3306; do sleep 2; done
	@echo "✅ MySQL pronto"

build:
	./gradlew clean build -x test

run:
	java -jar $(JAR) --spring.profiles.active=$(PROFILE)

up: mysql build run

down:
	docker-compose down
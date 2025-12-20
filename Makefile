.PHONY: mysql build run up down

APP_NAME=tarefas
PROFILE=docker
JAR=build/libs/$(APP_NAME)-0.0.1-SNAPSHOT.jar
MYSQL_SERVICE=mysql

mysql:
	docker-compose up -d $(MYSQL_SERVICE)

build:
	./gradlew clean build

run:
	java -jar $(JAR) --spring.profiles.active=$(PROFILE)

up: mysql build run

down:
	docker-compose down
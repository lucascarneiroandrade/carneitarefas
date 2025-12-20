.PHONY: mysql build run up down

APP_NAME=tarefas
PROFILE=docker
JAR=build/libs/$(APP_NAME)-0.0.1-SNAPSHOT.jar
MYSQL_SERVICE=mysql
JAVA := $(shell command -v java)

mysql:
	docker-compose up -d $(MYSQL_SERVICE)

build:
	./gradlew clean build

check-java:
	@test -n "$(JAVA)" || { \
		echo "❌ Java não encontrado no PATH"; \
		exit 1; \
	}

run: check-java
	$(JAVA) -jar build/libs/tarefas-0.0.1-SNAPSHOT.jar --spring.profiles.active=docker

up: mysql build run

down:
	docker-compose down
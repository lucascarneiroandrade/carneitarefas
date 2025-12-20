.PHONY: mysql build run up down check-java

APP_NAME := tarefas
PROFILE := docker
VERSION := 0.0.1-SNAPSHOT
JAR := build/libs/$(APP_NAME)-$(VERSION).jar
MYSQL_SERVICE := mysql

# Detecta sistema operacional
ifeq ($(OS),Windows_NT)
	SHELL := bash
	GRADLEW := gradlew.bat
	JAVA_CHECK := where java >nul 2>&1
else
	GRADLEW := ./gradlew
	JAVA_CHECK := command -v java >/dev/null 2>&1
endif

# Detecta docker compose v1 ou v2
DOCKER_COMPOSE := $(shell \
	command -v docker-compose >/dev/null 2>&1 && echo docker-compose || echo docker compose \
)

check-java:
	@$(JAVA_CHECK) || (echo "❌ Java não encontrado no PATH" && exit 1)

mysql:
	$(DOCKER_COMPOSE) up -d $(MYSQL_SERVICE)

build:
	$(GRADLEW) clean build

run: check-java
	java -jar $(JAR) --spring.profiles.active=$(PROFILE)

up: mysql build run

down:
	$(DOCKER_COMPOSE) down

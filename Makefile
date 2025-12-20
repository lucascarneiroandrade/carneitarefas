.PHONY: mysql build run up down

APP_NAME=tarefas
PROFILE=docker
JAR=build/libs/$(APP_NAME)-0.0.1-SNAPSHOT.jar
MYSQL_SERVICE=mysql
JAVA := $(shell command -v java)
DOCKER_COMPOSE := $(shell \
	if command -v docker-compose >/dev/null 2>&1; then \
		echo docker-compose; \
	else \
		echo docker compose; \
	fi \
)


mysql:
	${DOCKER_COMPOSE} up -d $(MYSQL_SERVICE)

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
	${DOCKER_COMPOSE} down
package com.tarefas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TarefasApplication

fun main(args: Array<String>) {
	runApplication<TarefasApplication>(*args)
}

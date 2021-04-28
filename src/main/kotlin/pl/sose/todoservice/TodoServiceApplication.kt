package pl.sose.todoservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableSwagger2
@SpringBootApplication
class TodoServiceApplication

fun main(args: Array<String>) {
    runApplication<TodoServiceApplication>(*args)
}

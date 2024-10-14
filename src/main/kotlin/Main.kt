package com.github.ajcode404

import kotlin.system.measureTimeMillis

fun main() {

    // will not break since we are using a pool here
    println("with conn pool: ${withConnectionPool(1000)}")

    // will break at some point due to number conn open.
    try {
        println("without conn pool: ${withoutConnectionPooling(1000)}")
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

fun withoutConnectionPooling(iteration: Int) = measureTimeMillis {
    repeat(iteration) {
        println("conn: $it")
        val connection = createConnection()
        executeQuery(connection) { statement ->
            statement.execute("SELECT 1")
        }
    }
}

fun withConnectionPool(iteration: Int) = measureTimeMillis {
    val connPool = ConnectionPool(10)
    repeat(iteration) {
        executeQuery(connPool) { statement ->
            statement.execute("SELECT 1")
        }
    }
}

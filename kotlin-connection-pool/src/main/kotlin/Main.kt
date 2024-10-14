package com.github.ajcode404

import java.sql.Connection
import java.sql.DriverManager
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import kotlin.system.measureTimeMillis

const val JDBC_URL = "jdbc:h2:mem:testdb"
const val USER = "sa"
const val PASSWORD = ""

fun main() {
    println("with conn pool: ${withConnectionPool(100000)}")
    println("without conn pool: ${withoutConnectionPooling(100000)}")
}

private const val CAPACITY = 10

class ConnectionPool {

    private val connectionPool: BlockingQueue<Connection> = LinkedBlockingQueue(CAPACITY)

    init {
        repeat(CAPACITY) {
            connectionPool.put(conn())
        }
    }

    fun getConnection(): Connection {
        return connectionPool.take()
    }

    fun releaseConnection(conn: Connection) {
        connectionPool.offer(conn)
    }
}

fun withoutConnectionPooling(iteration: Int) = measureTimeMillis {
    repeat(iteration) {
        val connection: Connection = conn()
        executeQuery(connection)
        connection.close()
    }
}

fun withConnectionPool(iteration: Int) = measureTimeMillis {
    val connPool = ConnectionPool()
    repeat(iteration) {
        val conn = connPool.getConnection()
        conn.createStatement().execute("SELECT 1")
        connPool.releaseConnection(conn)
    }
}

fun conn(): Connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)

fun executeQuery(conn: Connection) {
    val statement = conn.createStatement()
    statement.execute("select  1")
}

package com.github.ajcode404

import java.sql.Connection
import java.sql.DriverManager
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import kotlin.system.measureTimeMillis

object DBConfig {
    const val JDBC_URL = "jdbc:postgresql://localhost:5433/"
    const val USER = "postgres"
    const val PASSWORD = ""
}

private const val CAPACITY = 10

fun main() {
    println("with conn pool: ${withConnectionPool(1000)}")
//    println("without conn pool: ${withoutConnectionPooling(1000)}")
}

class ConnectionPool {

    private val connectionPool: BlockingQueue<Connection> = LinkedBlockingQueue(CAPACITY)

    init {
        repeat(CAPACITY) {
            val conn = DriverManager.getConnection(DBConfig.JDBC_URL, DBConfig.USER, DBConfig.PASSWORD)
            connectionPool.put(conn)
        }
    }

    fun getConnection(): Connection {
        return connectionPool.take()
    }

    // note we are not closing the connection we are giving it back to pool
    fun releaseConnection(conn: Connection) {
        connectionPool.offer(conn)
    }
}

fun withoutConnectionPooling(iteration: Int) = measureTimeMillis {
    repeat(iteration) {
        println("conn: $it")
        val connection: Connection = DriverManager.getConnection(DBConfig.JDBC_URL, DBConfig.USER, DBConfig.PASSWORD)
        executeQuery(connection)
        // connection.close()
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

fun executeQuery(conn: Connection) {
    val statement = conn.createStatement()
    statement.execute("select  1")
}

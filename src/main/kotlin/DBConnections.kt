package com.github.ajcode404

import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

private object DBConfig {
    const val JDBC_URL = "jdbc:postgresql://localhost:5433/"
    const val USER = "postgres"
    const val PASSWORD = ""
}

class ConnectionPool(capacity: Int) {

    private val connectionPool: BlockingQueue<Connection> = LinkedBlockingQueue(capacity)

    init {
        repeat(capacity) {
            val conn = DriverManager.getConnection(DBConfig.JDBC_URL, DBConfig.USER, DBConfig.PASSWORD)
            connectionPool.put(conn)
        }
    }

    internal fun getConnection(): Connection {
        return connectionPool.take()
    }

    // note we are not closing the connection we are giving it back to pool
    internal fun releaseConnection(conn: Connection) {
        connectionPool.offer(conn)
    }
}

fun executeQuery(pool: ConnectionPool, execute: (it: Statement) -> Boolean) {
    val conn = pool.getConnection()
    executeQuery(conn, execute)
    pool.releaseConnection(conn)
}

fun executeQuery(conn: Connection, execute: (it: Statement) -> Boolean) =
    execute(conn.createStatement())

internal fun createConnection() =
    DriverManager.getConnection(DBConfig.JDBC_URL, DBConfig.USER, DBConfig.PASSWORD)

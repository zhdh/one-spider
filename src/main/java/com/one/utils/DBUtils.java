package com.one.utils;

import java.sql.*;

/**
 * SQLite utils
 * @author https://github.com/zhdh
 */
public class DBUtils {

    public static void createTable() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:db/data.db");
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DROP TABLE IF EXISTS t_one");
        stmt.executeUpdate("CREATE TABLE t_one (id INTEGER AUTO_INCREMENT PRIMARY KEY ,type STRING,date STRING,word STRING,imageURL STRING,articleTitle STRING, articleAbstract STRING,articleAuthor STRING,articleContent STRING,questionTitle STRING,questionAbstract STRING,questionConetnt STRING, insertTime TIMESTAMP default CURRENT_TIMESTAMP )");
        stmt.close();
        conn.close();
    }



}

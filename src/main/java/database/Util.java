package database;

import java.sql.*;

public class Util {

    private final static String DB_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
    private final static String DB_CONNECT_STRING = "jdbc:jtds:sybase://192.168.0.9:4100/**";
    private final static String DB_USER = "**";
    private final static String DB_PASSWORD = "****";

    public Connection getConnection() {
        try {
            Class.forName(DB_DRIVER);
            Connection connection = DriverManager.getConnection(DB_CONNECT_STRING, DB_USER, DB_PASSWORD);
            //System.out.println("Connection is OK");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            //System.out.println("Connection is FAILED");
            return null;
        }
    }

    private final static String ADDRESS_TABLE = "create table address(\n" +
            "    id numeric(10) identity,\n" +
            "    city varchar(100) not null,\n" +
            "    postcode varchar(20) null,\n" +
            "    CONSTRAINT id\n" +
            "    PRIMARY KEY NONCLUSTERED (id)\n" +
            "    )";

    private final static String EMPLOYEE = "create table employee(\n" +
            "    id numeric(10) identity,\n" +
            "    address_id numeric(10) not null references address(id),\n" +
            "    name varchar(50) not null,\n" +
            "    CONSTRAINT id\n" +
            "    PRIMARY KEY NONCLUSTERED (id)\n" +
            "    )";

    private final static String PROJECT = "create table project(\n" +
            "    id numeric(10) identity,\n" +
            "    title varchar(100) not null,\n" +
            "    CONSTRAINT id\n" +
            "    PRIMARY KEY NONCLUSTERED (id)\n" +
            ")";

    private final static String EMPLOYEE_PROJECT = "create table employee_project (\n" +
            "    employee_id numeric(10),\n" +
            "    project_id numeric(10)\n" +
            ")";

    public void createTable() {
        try (Connection connection = getConnection()) {
            try (PreparedStatement prSt = connection.prepareStatement(ADDRESS_TABLE)) {
                prSt.executeUpdate();
            }
            try (PreparedStatement prSt = connection.prepareStatement(EMPLOYEE)) {
                prSt.executeUpdate();
            }
            try (PreparedStatement prSt = connection.prepareStatement(PROJECT)) {
                prSt.executeUpdate();
            }
            try (PreparedStatement prSt = connection.prepareStatement(EMPLOYEE_PROJECT)) {
                prSt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

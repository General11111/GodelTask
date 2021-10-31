package by.general.utils;

import java.sql.*;

public class ConnectionUtils {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test?serverTimezone=UTC";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
    }

    public static void close(Connection con, Statement st, ResultSet rs){
        try {
            if(con!=null) con.close();
            if(st!=null) st.close();
            if(rs!=null) rs.close();
        }catch (SQLException ex){
            System.out.println("Can not close!");
        }
    }
}

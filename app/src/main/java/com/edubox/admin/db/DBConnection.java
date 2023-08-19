package com.edubox.admin.db;

import java.sql.*;
public class DBConnection {

    private Connection con=null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private String sql;

    DBConnection(){

    }

    public static Connection ConnectionDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:eduboxDB.db");
            System.out.println("Ceonnection Succeeded");
//			if(con!=null) {
//				System.out.println("Ceonnection Succeeded");
//			}else {
//				System.out.println("Ceonnection not Succeeded");
//			}

            return con;
        }catch(Exception e) {


            return null;
        }

    }
    public static void main(String args[]) {
//		DBConnection u = new DBConnection();
//		u.ConnectionDB();
    }
}

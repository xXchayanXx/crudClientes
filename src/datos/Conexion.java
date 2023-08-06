package datos;

import java.sql.*;
public class Conexion {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/control_clientes?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "21683176";
    private static String mensajeError ="";
    
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
        } catch (SQLException ex) {
            mensajeError = "Error de conexion";
            ex.printStackTrace(System.out);
        }
        return conn;
    }
    
    public String getMensajeError(){
        return this.mensajeError;
    }
    
        public static void close(Connection conn){
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
        public static void close(PreparedStatement ps){
        try {
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
        
        public static void close(ResultSet res){
        try {
            res.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
}

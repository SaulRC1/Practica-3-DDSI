/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author SaulRC1
 */
public class Conexion {

    private Connection Connect = null;

    public Conexion() throws SQLException {

        String cadenaConexion = "jdbc:oracle:thin:@172.17.20.75:1521:rabida";

        String usuario = "DDSI_050";
        String password = "DDSI_050";

        this.Connect = DriverManager.getConnection(cadenaConexion, usuario, password);

    }

    public Conexion(String sgdb, String ip, String bd, String usuario,
            String password) throws SQLException {
        
        if("oracle".equals(sgdb.toLowerCase())){
            
            String cadenaConexion = "jdbc:oracle:thin:@" + ip + ":1521" + ":" + bd;
            
            this.Connect = DriverManager.getConnection(cadenaConexion, usuario, password);
            
        }
        else if("mysql".equals(sgdb.toLowerCase()))
        {
            
        }

    }
    
    public void desconexion() throws SQLException{
        
        this.Connect.close();
    }
    
    public Connection getConnect() {
        
        return this.Connect;
    }
    
    public DatabaseMetaData informacionBD() throws SQLException {
        return this.Connect.getMetaData();
    }

}

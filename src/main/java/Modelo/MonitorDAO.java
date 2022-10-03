/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/*
create table MONITOR (
    codMonitor char(4),
    nombre varchar(300) not null,
    dni varchar(9) not null,
    telefono varchar(9),
    correo varchar(50),
    fechaEntrada varchar(10),
    nick varchar(6),
    CONSTRAINT CP_Monitor PRIMARY KEY (codMonitor));
*/
/**
 *
 * @author usuario
 */
public class MonitorDAO {

    private Conexion conexion;
    private PreparedStatement ps;

    public MonitorDAO(Conexion c) {
        this.conexion = c;
    }

    public ArrayList<Monitor> listaMonitores() throws SQLException {

        ArrayList<Monitor> listaMonitores = new ArrayList<Monitor>();

        String consulta = "SELECT * FROM MONITOR";
        ps = conexion.getConnect().prepareStatement(consulta);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            listaMonitores.add(new Monitor(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4), rs.getString(5),
                    rs.getString(6), rs.getString(7)));
        }

        return listaMonitores;
    }
    
    public ArrayList<Monitor> listaMonitorPorLetra(String letra) throws SQLException {
        
        ArrayList<Monitor> listaMonitores = new ArrayList<Monitor>();
        
        String consulta = "SELECT * FROM MONITOR WHERE nombre LIKE ?";
        ps = conexion.getConnect().prepareStatement(consulta);
        
        letra = letra + "%";
        ps.setString(1, letra);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            listaMonitores.add(new Monitor(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4), rs.getString(5),
                    rs.getString(6), rs.getString(7)));
        }
        
        return listaMonitores;
    }
    
    public void actualizarMonitor(Monitor update){
        String actualizar = "UPDATE MONITOR SET nombre = ?, dni = ?,   WHERE codMonitor = ?";
    }
    
    public void insertarMonitor(Monitor insert) throws SQLException{
        
        String insercion = "INSERT INTO MONITOR VALUES (?, ?, ?, ?, ?, ?, ?)";
        ps = conexion.getConnect().prepareStatement(insercion);
        
        ps.setString(1, insert.getCodMonitor());
        ps.setString(2, insert.getNombre());
        ps.setString(3, insert.getDni());
        ps.setString(4, insert.getTelefono());
        ps.setString(5, insert.getCorreo());
        ps.setString(6, insert.getFechaEntrada());
        ps.setString(7, insert.getNick());
        
        ps.executeUpdate();
        
    }
    
    public void eliminarMonitor(Monitor delete) throws SQLException{
        String eliminar = "DELETE FROM MONITOR WHERE codMonitor=?";
        
        ps = conexion.getConnect().prepareStatement(eliminar);
        
        ps.setString(1, delete.getCodMonitor());
        ps.executeUpdate();
    }

}

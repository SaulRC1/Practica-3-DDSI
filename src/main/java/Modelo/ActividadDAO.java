/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author usuario
 */
public class ActividadDAO {

    private Conexion conexion;
    private PreparedStatement ps;
    private CallableStatement stmt;

    public ActividadDAO(Conexion conexion) {
        this.conexion = conexion;
    }

    public void devolverActividades(JComboBox cb) {
        String consulta = "SELECT NOMBRE FROM ACTIVIDAD";
        cb.removeAllItems();
        try {
            ps = conexion.getConnect().prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cb.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActividadDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void devolverSocios(String idActividad, DefaultTableModel Tabla) {
        String sql = "{ call getSocios(?,?) }";
        ResultSet rs = null;
        try {
            stmt = conexion.getConnect().prepareCall(sql);
            stmt.setString(1, idActividad);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.executeUpdate();

            rs = (ResultSet) stmt.getObject(2);
            while (rs.next()) {
                System.out.println("Nombre: " + rs.getString(1) + "Correo: " + rs.getString(2));
                this.pintaTabla(Tabla, rs.getString(1), rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActividadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ActividadDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public String getIdActividad(String NombreActividad) {
        String consulta = "SELECT IDACTIVIDAD FROM ACTIVIDAD WHERE NOMBRE = ?";
        String idActividad = "";
        try {
            ps = conexion.getConnect().prepareStatement(consulta);
            ps.setString(1, NombreActividad);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                idActividad = rs.getString(1);
                System.out.println("id: " + idActividad);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ActividadDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return idActividad;
    }

    public void pintaTabla(DefaultTableModel Tabla, String Nombre, String Correo) {
        Object[] fila = new Object[2];

        fila[0] = Nombre;
        fila[1] = Correo;

        Tabla.addRow(fila);

    }
}

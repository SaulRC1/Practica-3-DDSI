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

/**
 *
 * @author usuario
 */
public class SocioDAO {
    
    private Conexion conexion;
    private PreparedStatement ps;

    public SocioDAO(Conexion conexion) {
        this.conexion = conexion;
    }
    
    public ArrayList<Socio> listaSocios() throws SQLException {

        ArrayList<Socio> listaSocios = new ArrayList<Socio>();

        String consulta = "SELECT * FROM SOCIO";
        ps = conexion.getConnect().prepareStatement(consulta);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            listaSocios.add(new Socio(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4), rs.getString(5),
                    rs.getString(6), rs.getString(7), rs.getString(8)));
        }

        return listaSocios;
    }
    
    public ArrayList<Socio> listaSocioPorLetra(String letra) throws SQLException {
        
        ArrayList<Socio> listaSocios = new ArrayList<Socio>();
        
        String consulta = "SELECT * FROM SOCIO WHERE nombre LIKE ?";
        ps = conexion.getConnect().prepareStatement(consulta);
        
        letra = letra + "%";
        ps.setString(1, letra);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            listaSocios.add(new Socio(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4), rs.getString(5),
                    rs.getString(6), rs.getString(7), rs.getString(8)));
        }
        
        return listaSocios;
    }
    
}

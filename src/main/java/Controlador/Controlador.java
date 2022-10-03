/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ActividadDAO;
import Modelo.Conexion;
import Modelo.Monitor;
import Modelo.MonitorDAO;
import Modelo.Socio;
import Modelo.SocioDAO;
import Vista.PanelMonitor;
import Vista.PanelSocio;
import Vista.PanelVacio;
import Vista.VistaActividades;
import Vista.VistaConsola;
import Vista.VistaMensajes;
import Vista.VistaPrincipal;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.Date;

/**
 *
 * @author usuario
 */
public class Controlador implements ActionListener {

    private Conexion conexion_bd;
    private boolean desconexionOK;
    private VistaPrincipal vPrincipal = null;
    private VistaConsola vConsola;
    private VistaMensajes vMensajes;
    private PanelMonitor vMonitor;
    private PanelSocio vSocio;
    private PanelVacio vVacio;
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    private VistaActividades vActividades;
    ArrayList<Monitor> ListaMonitores;
    ArrayList<Socio> ListaSocios;

    public Controlador(Conexion conexion_bd) {

        this.conexion_bd = conexion_bd;
        vConsola = new VistaConsola();
        vPrincipal = new VistaPrincipal();
        vMensajes = new VistaMensajes();
        vMonitor = new PanelMonitor();
        vSocio = new PanelSocio();
        vVacio = new PanelVacio();
        vActividades = new VistaActividades();
        //addListeners();
        //JOptionPane.showMessageDialog(vPrincipal, "Llego al controlador");

        vPrincipal.mi_GestionMonitores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                vPrincipalmi_GestionMonitoresactionPerformed(ev);
            }
        });

        vPrincipal.mi_GestionSocios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                vPrincipalmi_GestionSociosactionPerformed(ev);
            }
        });

        vPrincipal.mi_Salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                vPrincipalmi_SaliractionPerformed(ev);
            }
        });

        vMonitor.tblMonitor.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                vMonitortblMonitorMouseClicked(evt);
            }
        });

        vMonitor.btnInsertar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {

                Monitor insertar;

                try {
                    String codMonitor = vMonitor.txtCodigo.getText();
                    String nombre = vMonitor.txtNombre.getText();
                    String dni = vMonitor.txtDNI.getText();
                    String telefono = vMonitor.txtTelefono.getText();
                    String correo = vMonitor.txtCorreo.getText();
                    Date fechaChooser = vMonitor.date_fecha.getDate();
                    String fechaEntrada = "";
                    if (fechaChooser != null) {
                        fechaEntrada = formatoFecha.format(fechaChooser);
                    }
                    //String fechaEntrada = vMonitor.txtFecha.getText();
                    String nick = vMonitor.txtNick.getText();

                    insertar = new Monitor(codMonitor, nombre, dni, telefono, correo, fechaEntrada, nick);

                    MonitorDAO mi_monitor = new MonitorDAO(conexion_bd);

                    if (!ListaMonitores.contains(insertar)) {

                        try {
                            mi_monitor.insertarMonitor(insertar);
                            ListaMonitores.add(insertar);
                            vaciarTablaMonitores();
                            rellenarTablaMonitores(ListaMonitores);
                        } catch (SQLException e) {

                            VistaMensajes.MensajeDeError("Ha ocurrido un error: ", e.getMessage());

                        }

                    }

                } catch (Exception e) {

                    VistaMensajes.MensajeDeError("Inserte valores correctos en las celdas: ", e.getMessage());
                }
            }
        });

        vMonitor.btnEliminar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                Monitor eliminar;
                try {
                    String codMonitor = vMonitor.txtCodigo.getText();
                    String nombre = vMonitor.txtNombre.getText();
                    String dni = vMonitor.txtDNI.getText();
                    String telefono = vMonitor.txtTelefono.getText();
                    String correo = vMonitor.txtCorreo.getText();
                    //String fechaEntrada = vMonitor.txtFecha.getText();
                    Date fechaChooser = vMonitor.date_fecha.getDate();
                    String fechaEntrada = "";
                    if (fechaChooser != null) {
                        fechaEntrada = formatoFecha.format(fechaChooser);
                    }
                    String nick = vMonitor.txtNick.getText();

                    int opcion = JOptionPane.showConfirmDialog(null, "¿Quiere eliminarlo?");
                    System.out.println("Opcion: " + opcion);

                    if (opcion == 0) {
                        eliminar = new Monitor(codMonitor, nombre, dni, telefono, correo, fechaEntrada, nick);
                        MonitorDAO mi_monitor = new MonitorDAO(conexion_bd);
                        mi_monitor.eliminarMonitor(eliminar);

                        ListaMonitores = mi_monitor.listaMonitores();
                        vaciarTablaMonitores();
                        rellenarTablaMonitores(ListaMonitores);

                    }

                } catch (Exception e) {
                    VistaMensajes.MensajeDeError("Ha ocurrido un error: ", e.getMessage());
                }

            }
        });

        vMonitor.btnVaciar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                vaciarTablaMonitores();
            }
        });

        vMonitor.btnListar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                rellenarTablaMonitores(ListaMonitores);
            }
        });

        vPrincipal.mi_SociosInscritos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                mi_SociosInscritosactionPerformed(ev);
            }
        });
        
        vActividades.btnMostrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt){
                vActividadesbtnMostrarMouseClicked(evt);
            }
        });

        vPrincipal.setLocationRelativeTo(null);
        vPrincipal.getContentPane().setLayout(new CardLayout());
        vPrincipal.add(vVacio);
        vPrincipal.add(vSocio);
        vPrincipal.add(vMonitor);
        vMonitor.setVisible(false);
        vSocio.setVisible(false);
        vVacio.setVisible(true);

        vPrincipal.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*switch (e.getActionCommand()) {
            case "Cerrar":
                this.desconexionOK = this.desconectar();

                if (this.desconexionOK) {
                    //JOptionPane.showMessageDialog(vPrincipal, "Llego al controlador");
                    this.vMensajes.MensajeInformacion("Desconectado con exito!");
                    this.vPrincipal.dispose();
                    this.vConsola.vistaConsolaLogin("Desconectado con exito!");
                    
                }
                else{
                    
                }
            break;

        }*/

    }

    private boolean desconectar() {

        boolean resultado = false;

        try {
            this.conexion_bd.desconexion();
            this.vConsola.vistaConsolaLogin("Desonectado de la BD con exito!");
            resultado = true;
        } catch (Exception e) {
            this.vConsola.vistaConsolaLogin("Error al desconectarse de la BD", e.getMessage());
        }

        return resultado;

    }

    private void addListeners() {
        //vPrincipal.btn_Cerrar.addActionListener(this);
    }

    /**
     *
     */
    public DefaultTableModel modeloTablaMonitores = new DefaultTableModel() {

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

    };

    public DefaultTableModel modeloTablaSocios = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
    public DefaultTableModel modeloTablaActividades = new DefaultTableModel(){
      public boolean isCellEditable(int row, int column){
          return false;
      }  
    };

    public void dibujarTablaMonitores(PanelMonitor vMonitor) {
        vMonitor.tblMonitor.setModel(modeloTablaMonitores);

        String[] columnasTabla = {"Codigo", "Nombre", "DNI", "Telefono", "Correo", "Fecha Incorporacion", "Nick"};
        modeloTablaMonitores.setColumnIdentifiers(columnasTabla);

        vMonitor.tblMonitor.getTableHeader().setResizingAllowed(false);
        vMonitor.tblMonitor.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        vMonitor.tblMonitor.getColumnModel().getColumn(0).setPreferredWidth(40);
        vMonitor.tblMonitor.getColumnModel().getColumn(1).setPreferredWidth(240);
        vMonitor.tblMonitor.getColumnModel().getColumn(2).setPreferredWidth(70);
        vMonitor.tblMonitor.getColumnModel().getColumn(3).setPreferredWidth(70);
        vMonitor.tblMonitor.getColumnModel().getColumn(4).setPreferredWidth(200);
        vMonitor.tblMonitor.getColumnModel().getColumn(5).setPreferredWidth(150);
        vMonitor.tblMonitor.getColumnModel().getColumn(6).setPreferredWidth(60);

    }

    public void dibujarTablaSocios(PanelSocio vSocio) {
        vSocio.tblSocio.setModel(modeloTablaSocios);
        String[] columnasTabla = {"Num Socio", "Nombre", "DNI", "Telefono",
            "Correo", "Fecha de Nacimiento", "Fecha de Incorporacion", "Categoria"};

        modeloTablaSocios.setColumnIdentifiers(columnasTabla);

        vSocio.tblSocio.getTableHeader().setResizingAllowed(false);
        vSocio.tblSocio.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        vSocio.tblSocio.getColumnModel().getColumn(0).setPreferredWidth(40);
        vSocio.tblSocio.getColumnModel().getColumn(1).setPreferredWidth(240);
        vSocio.tblSocio.getColumnModel().getColumn(2).setPreferredWidth(70);
        vSocio.tblSocio.getColumnModel().getColumn(3).setPreferredWidth(70);
        vSocio.tblSocio.getColumnModel().getColumn(4).setPreferredWidth(200);
        vSocio.tblSocio.getColumnModel().getColumn(5).setPreferredWidth(150);
        vSocio.tblSocio.getColumnModel().getColumn(6).setPreferredWidth(150);
        vSocio.tblSocio.getColumnModel().getColumn(7).setPreferredWidth(50);
        //vSocio.tblSocio.getColumnModel().getColumn(8).setPreferredWidth(50);
        //vMonitor.tblMonitor.getColumnModel().getColumn(6).setPreferredWidth(60);

    }
    
    public void dibujarTablaActividades(VistaActividades vActividades){
        vActividades.tblActividad.setModel(modeloTablaActividades);
        String[] columnasTabla = {"Nombre de Socio", "Correo de Socio"};
        modeloTablaActividades.setColumnIdentifiers(columnasTabla);
        vActividades.tblActividad.getTableHeader().setResizingAllowed(false);
        vActividades.tblActividad.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }

    public void rellenarTablaMonitores(ArrayList<Monitor> monitores) {

        Object[] fila = new Object[7];

        for (int i = 0; i < monitores.size(); i++) {

            fila[0] = monitores.get(i).getCodMonitor();
            fila[1] = monitores.get(i).getNombre();
            fila[2] = monitores.get(i).getDni();
            fila[3] = monitores.get(i).getTelefono();
            fila[4] = monitores.get(i).getCorreo();
            fila[5] = monitores.get(i).getFechaEntrada();
            fila[6] = monitores.get(i).getNick();
            modeloTablaMonitores.addRow(fila);

        }

    }

    public void rellenarTablaSocios(ArrayList<Socio> socios) {
        Object[] fila = new Object[8];

        for (int i = 0; i < socios.size(); i++) {
            fila[0] = socios.get(i).getNumeroSocio();
            fila[1] = socios.get(i).getNombre();
            fila[2] = socios.get(i).getDni();
            fila[3] = socios.get(i).getTelefono();
            fila[4] = socios.get(i).getCorreo();
            fila[5] = socios.get(i).getFechaNacimiento();
            fila[6] = socios.get(i).getFechaEntrada();
            fila[7] = socios.get(i).getCategoria();

            modeloTablaSocios.addRow(fila);
        }
    }

    public void vaciarTablaMonitores() {
        while (modeloTablaMonitores.getRowCount() > 0) {
            modeloTablaMonitores.removeRow(0);
        }
    }

    public void vaciarTablaSocios() {
        while (modeloTablaSocios.getRowCount() > 0) {
            modeloTablaSocios.removeRow(0);
        }
    }
    
    public void vaciarTablaActividades(){
        while(modeloTablaActividades.getRowCount() > 0){
            modeloTablaActividades.removeRow(0);
        }
    }

    private void vPrincipalmi_GestionMonitoresactionPerformed(ActionEvent evt) {

        System.out.println("Clicked");

        vSocio.setVisible(false);
        vVacio.setVisible(false);
        this.dibujarTablaMonitores(vMonitor);
        MonitorDAO mi_monitor = new MonitorDAO(this.conexion_bd);
        try {
            this.ListaMonitores = mi_monitor.listaMonitores();
            this.vaciarTablaMonitores();
            this.rellenarTablaMonitores(ListaMonitores);
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        vMonitor.setVisible(true);

    }

    private void vMonitortblMonitorMouseClicked(MouseEvent evt) {
        int fila = vMonitor.tblMonitor.getSelectedRow();

        Monitor seleccionado = this.ListaMonitores.get(fila);

        vMonitor.txtCodigo.setText(seleccionado.getCodMonitor());
        vMonitor.txtCorreo.setText(seleccionado.getCorreo());
        vMonitor.txtDNI.setText(seleccionado.getDni());
        //vMonitor.txtFecha.setText(seleccionado.getFechaEntrada());
        String fecha = seleccionado.getFechaEntrada();
        try {
            Date fechaChooser = formatoFecha.parse(fecha);
            vMonitor.date_fecha.setDate(fechaChooser);
        } catch (ParseException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        vMonitor.txtNick.setText(seleccionado.getNick());
        vMonitor.txtNombre.setText(seleccionado.getNombre());
        vMonitor.txtTelefono.setText(seleccionado.getTelefono());
    }

    private void vPrincipalmi_SaliractionPerformed(ActionEvent ev) {

        System.exit(0);

    }

    private void vPrincipalmi_GestionSociosactionPerformed(ActionEvent ev) {
        vMonitor.setVisible(false);
        vVacio.setVisible(false);

        this.dibujarTablaSocios(vSocio);
        SocioDAO mi_socio = new SocioDAO(this.conexion_bd);
        try {
            this.ListaSocios = mi_socio.listaSocios();
            this.vaciarTablaSocios();
            this.rellenarTablaSocios(ListaSocios);
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        //vMonitor.setVisible(true);
        vSocio.setVisible(true);
    }

    private void mi_SociosInscritosactionPerformed(ActionEvent ev) {
        
        this.dibujarTablaActividades(vActividades);
        ActividadDAO act = new ActividadDAO(this.conexion_bd);
        act.devolverActividades(vActividades.cbActividades);
        vActividades.setVisible(true);

    }
    
    private void vActividadesbtnMostrarMouseClicked(MouseEvent evt){
        ActividadDAO miActividad = new ActividadDAO(this.conexion_bd);
        String Nombre_Actividad = (String) vActividades.cbActividades.getSelectedItem();
        String idActividad = miActividad.getIdActividad(Nombre_Actividad);
        this.vaciarTablaActividades();
        miActividad.devolverSocios(idActividad, modeloTablaActividades);
        
    }

}

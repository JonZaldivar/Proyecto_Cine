package proyectoCine.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import proyectoCine.domain.Horario;
import proyectoCine.domain.Pelicula;
import proyectoCine.domain.Sala;

import java.util.List;

public class JFramePago extends JFrame {

    private Pelicula pelicula;
    private Horario horario;
    private Sala sala;
    private String asientos;
    private double precioTotal;
    private String nombreCliente;
    private String correoCliente;
    private List<Pelicula> listaPeliculas;

    private JTextField txtNumTarjeta;
    private JTextField txtCaducidad;
    private JTextField txtCVV;

    public JFramePago(Pelicula pelicula, Horario horario, Sala sala,
                      String asientos, double precioTotal,
                      String nombreCliente, String correoCliente,
                      List<Pelicula> listaPeliculas) {

        this.pelicula = pelicula;
        this.horario = horario;
        this.sala = sala;
        this.asientos = asientos;
        this.precioTotal = precioTotal;
        this.nombreCliente = nombreCliente;
        this.correoCliente = correoCliente;
        this.listaPeliculas = listaPeliculas;

        ventana();
        this.setVisible(true);
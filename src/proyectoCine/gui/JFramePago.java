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

    private JTextField NumTarjeta;
    private JTextField Caducidad;
    private JTextField CVV;

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
        
        
    }
    
    private void ventana() {

        Color azulFondo = new Color(217, 234, 246);

        this.setTitle("Pago de entrada");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(azulFondo);

        // CABECERA
        JPanel panelCabecera = new JPanel();
        panelCabecera.setBackground(azulFondo);
        JLabel titulo = new JLabel("Introduce los datos de tu tarjeta");
        titulo.setFont(new Font("Dialog", Font.BOLD, 18));
        panelCabecera.add(titulo);
        this.add(panelCabecera, BorderLayout.NORTH);

        // CENTRO
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelCentral.setBackground(azulFondo);

        NumTarjeta = new JTextField();
        Caducidad = new JTextField();
        CVV = new JTextField();

        panelCentral.add(fieldConTitulo("Numero de tarjeta:", NumTarjeta));
        panelCentral.add(fieldConTitulo("Fecha de caducidad (MM/AA):", Caducidad));
        panelCentral.add(fieldConTitulo("CVV:", CVV));
        panelCentral.add(Box.createVerticalStrut(20));

        this.add(panelCentral, BorderLayout.CENTER);

        // BOTONES
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(azulFondo);

        JButton botonCancelar = new JButton("Cancelar");
        JButton botonPagar = new JButton("Pagar " + precioTotal + " €");

        botonCancelar.setBackground(new Color(231, 76, 60));
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setFocusPainted(false);

        botonPagar.setBackground(new Color(39, 174, 96));
        botonPagar.setForeground(Color.WHITE);
        botonPagar.setFocusPainted(false);

        panelBotones.add(botonCancelar);
        panelBotones.add(botonPagar);

        this.add(panelBotones, BorderLayout.SOUTH);

        // LISTENERS
        botonCancelar.addActionListener(e -> this.dispose());

        botonPagar.addActionListener(e -> procesarPago());
    }

    private void procesarPago() {
    	if (NumTarjeta.getText().isEmpty() ||
    			Caducidad.getText().isEmpty() ||
    			CVV.getText().isEmpty()) {
    		JOptionPane.showMessageDialog(this,
    				"Rellena todos los datos de la tarjeta.",
    				"Campos vacíos",
    				JOptionPane.WARNING_MESSAGE);
    		return;
    	}

    	JOptionPane.showMessageDialog(this,
    			"¡Pago realizado con éxito!\n\n" +
    					"Nombre: " + nombreCliente + "\n" +
    					"Correo: " + correoCliente + "\n" +
    					"Película: " + pelicula.getTitulo() + "\n" +
    					"Asientos: " + asientos,
    					"Pago Confirmado",
    					JOptionPane.INFORMATION_MESSAGE);

    	this.dispose();

	}

	private JPanel fieldConTitulo(String titulo, JTextField campo) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(8, 8, 8, 8));

        JLabel label = new JLabel(titulo);
        panel.add(label, BorderLayout.NORTH);
        panel.add(campo, BorderLayout.CENTER);

        return panel;
    }

    
}
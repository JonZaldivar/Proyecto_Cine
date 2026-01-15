package proyectoCine.gui;

import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import proyectoCine.domain.Horario;
import proyectoCine.domain.Pelicula;
import proyectoCine.domain.Sala;

public class JFrameReserva extends JFrame {

    private Pelicula pelicula;
    private Horario horario;
    private Sala sala;
    private String asientos;
    private double precioTotal;
    private List<Pelicula> listaPeliculas;

    // para que el usuario rellene con sus daos
    private JTextField textNombre;
    private JTextField textCorreo;

    // etiqueta + temporizador
    private JLabel labelTiempo;
    private JFrameTemporizadorReserva temporizador;

    private String codigoDescuento;   
    private int porcentajeDescuento =0;  
    
    private JTextField txtCodigoDescuento;
    private JLabel lblPrecioFinal;
    
    private boolean descuentoAplicado = false;

    
    

    public JFrameReserva(Pelicula pelicula, Horario horario, Sala sala, String asientos, double precioTotal, List<Pelicula> listaPeliculas, String codigoDescuento,
            int porcentajeDescuento) {
        this.pelicula = pelicula;
        this.horario = horario;
        this.sala = sala;
        this.asientos = asientos;
        this.precioTotal = precioTotal;
        this.listaPeliculas = listaPeliculas;
        this.codigoDescuento = codigoDescuento;
        this.porcentajeDescuento = porcentajeDescuento;
        

        ventana();
        this.setVisible(true);
    }

    //crea la ventana
    private void ventana() {

        Color azulFondo = new Color(217,234,246);
        this.setTitle("Confirmación de reserva");
        this.setSize(650, 720);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(azulFondo);

        // panel superior
        JPanel panelCabecera = new JPanel();
        panelCabecera.setBackground(azulFondo);

        java.net.URL logoUrl = getClass().getResource("/DeustoCine.png");
        ImageIcon logo = new ImageIcon(logoUrl);
        panelCabecera.add(new JLabel(logo));


        labelTiempo = new JLabel("Tiempo restante : 10:00");
        labelTiempo.setFont(new Font("Dialog", Font.BOLD, 14));
        panelCabecera.add(labelTiempo);


        this.add(panelCabecera, BorderLayout.NORTH);

        // temporizador de reserva de asientos
        temporizador = new JFrameTemporizadorReserva(labelTiempo);
        temporizador.start();


        // panel central para el resumen
        JPanel panelCentral = new JPanel();
        
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(azulFondo);
        panelCentral.setBorder(new EmptyBorder(10,10,10,10));

        panelCentral.add(crearSeccion("Pelicula:", pelicula.getTitulo()));
        panelCentral.add(crearSeccion("Sala:", Integer.toString(sala.getId())));
        panelCentral.add(crearSeccion("Horario:", horario.toString()));
        panelCentral.add(crearSeccion("Asientos:", asientos));
        
        
        panelCentral.add(Box.createVerticalStrut(10));

        JPanel panelSeccionDescuento = new JPanel();
        panelSeccionDescuento.setLayout(new BoxLayout(panelSeccionDescuento, BoxLayout.Y_AXIS));
        panelSeccionDescuento.setBackground(Color.WHITE);
        panelSeccionDescuento.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Código de descuento"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        
        JLabel lblDesc = new JLabel("Código de descuento:");
        lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDesc.setFont(new Font("Dialog", Font.PLAIN, 13));
        panelSeccionDescuento.add(lblDesc);

        panelSeccionDescuento.add(Box.createVerticalStrut(8));
        
        
        JPanel panelTextField = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTextField.setBackground(Color.WHITE);
        txtCodigoDescuento = new JTextField(15);
        txtCodigoDescuento.setHorizontalAlignment(JTextField.CENTER);
        panelTextField.add(txtCodigoDescuento);
        panelSeccionDescuento.add(panelTextField);

        panelSeccionDescuento.add(Box.createVerticalStrut(10));
        
        //boton de aplicar descuento
        JButton btnAplicar = new JButton("Aplicar descuento");
        btnAplicar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAplicar.setBackground(new Color(52, 152, 219));
        btnAplicar.setForeground(Color.WHITE);
        btnAplicar.setFocusPainted(false);
        btnAplicar.setFont(new Font("Arial", Font.BOLD, 12));
        btnAplicar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelSeccionDescuento.add(btnAplicar);

        panelCentral.add(panelSeccionDescuento);

        panelCentral.add(Box.createVerticalStrut(10));
        
        
        lblPrecioFinal = new JLabel("Total: " + String.format("%.2f", precioTotal) + " €");
        lblPrecioFinal.setFont(new Font("Dialog", Font.BOLD, 14));
        lblPrecioFinal.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(lblPrecioFinal);

        panelCentral.add(Box.createVerticalStrut(10));


        lblPrecioFinal = new JLabel("Total: " + precioTotal + " €");
        lblPrecioFinal.setFont(new Font("Dialog", Font.BOLD, 16));
        panelCentral.add(lblPrecioFinal);
        panelCentral.add(Box.createVerticalStrut(15));

       //datos del usuario
        JLabel lblDatos = new JLabel("Introduce tus datos para completar la reserva:");
        lblDatos.setFont(new Font("Dialog", Font.BOLD, 14));
        lblDatos.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelCentral.add(lblDatos);

        panelCentral.add(Box.createVerticalStrut(8));

        textNombre = new JTextField();
        textCorreo = new JTextField();

        panelCentral.add(fieldConTitulo("Nombre completo:", textNombre));
        panelCentral.add(fieldConTitulo("Correo electrónico:", textCorreo));

        panelCentral.add(Box.createVerticalStrut(15));

        this.add(panelCentral, BorderLayout.CENTER);

        // botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(azulFondo);

        JButton botonCancelar = new JButton("Cancelar");
        JButton botonConfirmar = new JButton("Confirmar Reserva");
        JButton botonModificar = new JButton("Modificar");

        botonCancelar.setBackground(new Color(231, 76, 60));
        botonConfirmar.setBackground(new Color(39, 174, 96));
        botonConfirmar.setForeground(Color.WHITE);
        botonModificar.setBackground(new Color(52, 152, 219));
        botonModificar.setForeground(Color.WHITE);

        panelBotones.add(botonCancelar);
        panelBotones.add(botonConfirmar);
        panelBotones.add(botonModificar);

        this.add(panelBotones, BorderLayout.SOUTH);
        
        //action listener de boton aplicar descuento
        btnAplicar.addActionListener(e -> {
        	String codigoIntroducido = txtCodigoDescuento.getText().trim();

            if (descuentoAplicado) {
            	JOptionPane.showMessageDialog(this,
                        "El descuento ya ha sido aplicado");
                return;
            }
            
            if (codigoDescuento == null) {
                JOptionPane.showMessageDialog(this,
                        "No dispone de ningún código de descuento");
                return;
            }

            if (codigoIntroducido.equalsIgnoreCase(codigoDescuento)) {
                double descuento = precioTotal * porcentajeDescuento / 100.0;
                precioTotal = precioTotal - descuento;

                lblPrecioFinal.setText(
                    "Total con descuento: " + precioTotal + " €"
                );
                descuentoAplicado=true;

                
                JOptionPane.showMessageDialog(this, "Descuento aplicado: " + porcentajeDescuento + "%");

            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Código de descuento no válido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // action listener de boton de cancelar
        botonCancelar.addActionListener(e -> {
            temporizador.detener();
            this.dispose();
        });

        //action listener de boton confirmar
        botonConfirmar.addActionListener(e -> {
            if (textNombre.getText().isEmpty() || textCorreo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, completa los datos",
                        "Campos vacíos",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                JFramePago ventanaPago = new JFramePago(
                        pelicula,
                        horario,
                        sala,
                        asientos,
                        precioTotal,
                        textNombre.getText(),
                        textCorreo.getText(),
                        listaPeliculas,
                        temporizador
                );
                ventanaPago.setVisible(true);
                this.dispose();
            }
        });

        botonModificar.addActionListener(e -> mostrarMenuModificar());

    }

    private void mostrarMenuModificar() {

        String[] opciones = {
            "Cambiar pelicula",
            "Cambiar horario",
            "Cambiar seleccion de asientos"
        };

        String seleccion = (String) JOptionPane.showInputDialog(
                this,
                "¿Que deseas modificar?",
                "Modificar reserva",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion == null) return; 

        temporizador.detener();
        this.dispose();

        switch (seleccion) {
            case "Cambiar pelicula":
                new JFramePrincipal(listaPeliculas).setVisible(true);
                break;

            case "Cambiar horario":
                new JFramePelicula(pelicula, listaPeliculas, codigoDescuento, porcentajeDescuento).setVisible(true);
                break;

            case "Cambiar seleccion de asientos":
                new JFrameSala(sala, pelicula, horario, listaPeliculas, codigoDescuento, porcentajeDescuento).setVisible(true);
                break;
        }
    }

    private JPanel crearSeccion(String titulo, String contenido) {
        JPanel panel = new JPanel(new GridLayout(2,1));
        panel.setBorder(new TitledBorder(titulo));
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel(contenido);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
        return panel;
    }

    private JPanel fieldConTitulo(String titulo, JTextField campo) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(5,5,5,5));

        JLabel lbl = new JLabel(titulo);
        panel.add(lbl, BorderLayout.NORTH);
        panel.add(campo, BorderLayout.CENTER);

        return panel;
    }
}

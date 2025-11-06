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
    private JTextField txtNombre;
    private JTextField txtCorreo;

    public JFrameReserva(Pelicula pelicula, Horario horario, Sala sala, String asientos, double precioTotal, List<Pelicula> listaPeliculas) {
        this.pelicula = pelicula;
        this.horario = horario;
        this.sala = sala;
        this.asientos = asientos;
        this.precioTotal = precioTotal;
        this.listaPeliculas = listaPeliculas;

        ventana();
        this.setVisible(true);
    }

    //crea la ventana
    private void ventana() {

    	
        Color azulFondo = new Color(217,234,246);
        Color blanco = Color.WHITE;
        this.setTitle("Confirmación de reserva");
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(azulFondo);

        // panel superior
        JPanel panelCabecera = new JPanel();
        panelCabecera.setBackground(azulFondo);

        java.net.URL logoUrl = getClass().getResource("/DeustoCine.png");
        ImageIcon logo = new ImageIcon(logoUrl);
        panelCabecera.add(new JLabel(logo));

        this.add(panelCabecera, BorderLayout.NORTH);

        // panel central para el resumen
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(azulFondo);
        panelCentral.setBorder(new EmptyBorder(10,10,10,10));

        // resumen
        panelCentral.add(crearSeccion("Pelicula:", pelicula.getTitulo()));
        panelCentral.add(crearSeccion("Sala:", Integer.toString(sala.getId())));
        panelCentral.add(crearSeccion("Horario:", horario.toString()));
        panelCentral.add(crearSeccion("Asientos:", asientos));
        panelCentral.add(crearSeccion("Total:", precioTotal + " €"));
        panelCentral.add(Box.createVerticalStrut(15));

        // FORMULARIO DE DATOS DEL USUARIO
        JLabel lblDatos = new JLabel("Introduce tus datos para completar la reserva:");
        lblDatos.setFont(new Font("Dialog", Font.BOLD, 14));
        lblDatos.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelCentral.add(lblDatos);

        panelCentral.add(Box.createVerticalStrut(8));

        txtNombre = new JTextField();
        txtCorreo = new JTextField();

        panelCentral.add(fieldConTitulo("Nombre completo:", txtNombre));
        panelCentral.add(fieldConTitulo("Correo electrónico:", txtCorreo));

        panelCentral.add(Box.createVerticalStrut(15));

        this.add(panelCentral, BorderLayout.CENTER);

        // botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(azulFondo);

        JButton botonCancelar = new JButton("Cancelar");
        JButton botonConfirmar = new JButton("Confirmar Reserva");
        JButton botonModificar = new JButton("Modificar");
        
        botonCancelar.setBackground(new Color(231, 76, 60));
        botonConfirmar.setForeground(Color.WHITE);
        botonCancelar.setFocusPainted(false);
        botonConfirmar.setBackground(new Color(39, 174, 96));
        botonConfirmar.setForeground(Color.WHITE);
        botonConfirmar.setFocusPainted(false);
        botonModificar.setBackground(new Color(52, 152, 219)); // azul bonito
        botonModificar.setForeground(Color.WHITE);
        botonModificar.setFocusPainted(false);
        
        panelBotones.add(botonCancelar);
        panelBotones.add(botonConfirmar);
        panelBotones.add(botonModificar);
        

        this.add(panelBotones, BorderLayout.SOUTH);

        // action listener
        botonCancelar.addActionListener(e -> this.dispose());

        botonConfirmar.addActionListener(e -> {
            if(txtNombre.getText().isEmpty() || txtCorreo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa los datos", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "¡Reserva realizada con exito!\n\n" +
                        "A nombre de: " + txtNombre.getText() + "\n" +
                        "Se enviara confirmacion a: " + txtCorreo.getText(),
                        "Reserva Confirmada",
                        JOptionPane.INFORMATION_MESSAGE);
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
                "Modificar Reserva",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion == null) return; // 

        switch (seleccion) {
            case "Cambiar pelicula":
                this.dispose();
                if (listaPeliculas == null) {
                    JOptionPane.showMessageDialog(this, "Error: No se obtuvo la lista de películas.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                new JFramePrincipal(listaPeliculas).setVisible(true);
                break;

            case "Cambiar horario":
                this.dispose();
                new JFramePelicula(pelicula, listaPeliculas).setVisible(true);
                break;

            case "Cambiar seleccion de asientos":
                this.dispose();
                new JFrameSala(sala, pelicula, horario, listaPeliculas).setVisible(true);
                break;
        }
    }
    
    //modificaciones esteticas
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

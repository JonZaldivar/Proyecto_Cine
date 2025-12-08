package proyectoCine.gui;

import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import proyectoCine.domain.Horario;
import proyectoCine.domain.Pelicula;
import proyectoCine.domain.Sala;

public class JFramePago extends JFrame {

    private Pelicula pelicula;
    private Horario horario;
    private Sala sala;
    private String asientos;
    private double precioTotal;
    private String nombre;
    private String correo;
    private List<Pelicula> listaPeliculas;

    private JTextField txtTitular;
    private JTextField txtNumeroTarjeta;
    private JTextField txtCaducidad;
    private JTextField txtCVV;

    public JFramePago(Pelicula pelicula,
                      Horario horario,
                      Sala sala,
                      String asientos,
                      double precioTotal,
                      String nombre,
                      String correo,
                      List<Pelicula> listaPeliculas) {

        this.pelicula = pelicula;
        this.horario = horario;
        this.sala = sala;
        this.asientos = asientos;
        this.precioTotal = precioTotal;
        this.nombre = nombre;
        this.correo = correo;
        this.listaPeliculas = listaPeliculas;

        setTitle("Pago de la Reserva");
        setSize(450, 370);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Color azulFondo = new Color(217,234,246);
        getContentPane().setBackground(azulFondo);

        // PANEL CENTRAL
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBorder(new EmptyBorder(15,15,15,15));
        panelCentral.setBackground(azulFondo);

        JLabel lblTitulo = new JLabel("Introduce los datos de tu tarjeta:");
        lblTitulo.setFont(new Font("Dialog", Font.BOLD, 15));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(lblTitulo);
        panelCentral.add(Box.createVerticalStrut(15));

        // CAMPOS
        txtTitular = crearCampoCorto();
        txtNumeroTarjeta = crearCampoCorto();
        txtCaducidad = crearCampoCorto();
        txtCVV = crearCampoCorto();

        panelCentral.add(fieldConTitulo("Titular de la tarjeta:", txtTitular));
        panelCentral.add(fieldConTitulo("Número de tarjeta:", txtNumeroTarjeta));
        panelCentral.add(fieldConTitulo("Fecha de caducidad (MM/AA):", txtCaducidad));
        panelCentral.add(fieldConTitulo("CVV:", txtCVV));

        add(panelCentral, BorderLayout.CENTER);

        // BOTONES
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(azulFondo);

        JButton btnPagar = new JButton("Confirmar Pago");
        btnPagar.setBackground(new Color(39, 174, 96));
        btnPagar.setForeground(Color.WHITE);
        btnPagar.setFocusPainted(false);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(231, 76, 60));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);

        panelBotones.add(btnCancelar);
        panelBotones.add(btnPagar);

        add(panelBotones, BorderLayout.SOUTH);

        // ACTION LISTENERS
        btnCancelar.addActionListener(e -> dispose());

        btnPagar.addActionListener(e -> {
            if (camposVacios()) {
                JOptionPane.showMessageDialog(this,
                    "Por favor, completa todos los campos.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(this,
                    "¡Pago realizado con éxito!\n\n" +
                    "Gracias por tu compra, " + nombre + ".\n" +
                    "Se enviará confirmación a: " + correo,
                    "Pago Confirmado",
                    JOptionPane.INFORMATION_MESSAGE
                );
                dispose();
            }
        });

        setVisible(true);
    }

    // ---- MÉTODOS AUXILIARES ----

    private JTextField crearCampoCorto() {
        JTextField campo = new JTextField();
        campo.setPreferredSize(new Dimension(200, 28)); // Campo estrecho
        return campo;
    }

    private boolean camposVacios() {
        return txtTitular.getText().isEmpty() ||
               txtNumeroTarjeta.getText().isEmpty() ||
               txtCaducidad.getText().isEmpty() ||
               txtCVV.getText().isEmpty();
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

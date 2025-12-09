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
   private JTextField textTitular;
   private JTextField textNumeroTarjeta;
   private JTextField textCaducidad;
   private JTextField textCVV;

   
   private JFrameTemporizadorReserva temporizador;
   private JLabel labelTiempo;
 
   public JFramePago(Pelicula pelicula,
                     Horario horario,
                     Sala sala,
                     String asientos,
                     double precioTotal,
                     String nombre,
                     String correo,
                     List<Pelicula> listaPeliculas,
                     JFrameTemporizadorReserva temporizador) { 
       this.pelicula = pelicula;
       this.horario = horario;
       this.sala = sala;
       this.asientos = asientos;
       this.precioTotal = precioTotal;
       this.nombre = nombre;
       this.correo = correo;
       this.listaPeliculas = listaPeliculas;
       this.temporizador = temporizador;
       
       
       setTitle("Pago de la Reserva");
       setSize(450, 400);
       setLocationRelativeTo(null);
       setLayout(new BorderLayout());
       Color azulFondo = new Color(217,234,246);
       getContentPane().setBackground(azulFondo);
       
       
       JPanel panelSuperior = new JPanel(new BorderLayout());
       panelSuperior.setBackground(azulFondo);
       JLabel labelTitulo = new JLabel("Introduce los datos de tu tarjeta:");
       labelTitulo.setFont(new Font("Dialog", Font.BOLD, 15));
       labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
       panelSuperior.add(labelTitulo, BorderLayout.CENTER);
       
       
       labelTiempo = new JLabel();
       labelTiempo.setFont(new Font("Dialog", Font.BOLD, 14));
       labelTiempo.setHorizontalAlignment(SwingConstants.RIGHT);
       panelSuperior.add(labelTiempo, BorderLayout.EAST);
       add(panelSuperior, BorderLayout.NORTH);
       
       
       temporizador.setLabel(labelTiempo);

       
       JPanel panelCentral = new JPanel();
       panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
       panelCentral.setBorder(new EmptyBorder(15,15,15,15));
       panelCentral.setBackground(azulFondo);

       
       textTitular = crearCampoCorto();
       textNumeroTarjeta = crearCampoCorto();
       textCaducidad = crearCampoCorto();
       textCVV = crearCampoCorto();
       panelCentral.add(fieldConTitulo("Titular de la tarjeta:", textTitular));
       panelCentral.add(fieldConTitulo("Número de tarjeta:", textNumeroTarjeta));
       panelCentral.add(fieldConTitulo("Fecha de caducidad (MM/AA):", textCaducidad));
       panelCentral.add(fieldConTitulo("CVV:", textCVV));
       add(panelCentral, BorderLayout.CENTER);
       
       
       JPanel panelBotones = new JPanel(new FlowLayout());
       panelBotones.setBackground(azulFondo);
       
       JButton botonPagar = new JButton("Confirmar Pago");
       botonPagar.setBackground(new Color(39, 174, 96));
       botonPagar.setForeground(Color.WHITE);
       botonPagar.setFocusPainted(false);
       
       JButton botonCancelar = new JButton("Cancelar");
       botonCancelar.setBackground(new Color(231, 76, 60));
       botonCancelar.setForeground(Color.WHITE);
       botonCancelar.setFocusPainted(false);
       panelBotones.add(botonCancelar);
       panelBotones.add(botonPagar);
       add(panelBotones, BorderLayout.SOUTH);
       
   
       
       botonCancelar.addActionListener(e -> {
           temporizador.detener();
           dispose();
       });
       botonPagar.addActionListener(e -> {
           if (camposVacios()) {
               JOptionPane.showMessageDialog(this,
                   "Por favor, completa todos los campos.",
                   "Datos incompletos",
                   JOptionPane.WARNING_MESSAGE
               );
           } else {
               temporizador.detener();
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
   
   
   private JTextField crearCampoCorto() {
       JTextField campo = new JTextField();
       campo.setPreferredSize(new Dimension(200, 28));
       return campo;
   }
   
   
   private boolean camposVacios() {
       return textTitular.getText().isEmpty() ||
              textNumeroTarjeta.getText().isEmpty() ||
              textCaducidad.getText().isEmpty() ||
              textCVV.getText().isEmpty();
   }
   
   
   private JPanel fieldConTitulo(String titulo, JTextField campo) {
       JPanel panel = new JPanel(new BorderLayout());
       panel.setBackground(Color.WHITE);
       panel.setBorder(new EmptyBorder(5,5,5,5));
       JLabel label = new JLabel(titulo);
       panel.add(label, BorderLayout.NORTH);
       panel.add(campo, BorderLayout.CENTER);
       return panel;
   }
}

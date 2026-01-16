package proyectoCine.gui;

import java.awt.*;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import proyectoCine.domain.Horario;
import proyectoCine.domain.Pelicula;
import proyectoCine.domain.Reserva;
import proyectoCine.domain.Sala;
import proyectoCine.persistence.CineGestorBD;

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
   private static CineGestorBD gestor;

   private JFrameTemporizadorReserva temporizador;
   private JLabel labelTiempo;

   private JLabel labelResumenPelicula;
   private JLabel labelResumenSala;
   private JLabel labelResumenAsientos;
   private JLabel labelResumenTotal;
   private JCheckBox checkGuardarTarjeta;
   private JCheckBox checkRecibirCorreo;
   private JComboBox<String> comboMetodoPago;
   private JProgressBar progressPaso;
   private JLabel labelErrores;
   private JPanel panelPrincipalCentral;

   private JPanel panelBotonesDerecha; // para poder añadir el botón final

   private static final Pattern REGEX_TARJETA = Pattern.compile("^[0-9]{13,19}$");
   private static final Pattern REGEX_CVV = Pattern.compile("^[0-9]{3,4}$");
   private static final Pattern REGEX_CADUCIDAD = Pattern.compile("^(0[1-9]|1[0-2])/([0-9]{2})$");

   public JFramePago(Pelicula pelicula,
                     Horario horario,
                     Sala sala,
                     String asientos,
                     double precioTotal,
                     String nombre,
                     String correo,
                     List<Pelicula> listaPeliculas,
                     JFrameTemporizadorReserva temporizador,CineGestorBD gestor) {
       this.pelicula = pelicula;
       this.horario = horario;
       this.gestor = gestor;
       this.sala = sala;
       this.asientos = asientos;
       this.precioTotal = precioTotal;
       this.nombre = nombre;
       this.correo = correo;
       this.listaPeliculas = listaPeliculas;
       this.temporizador = temporizador;
       
       setTitle("Pago de la Reserva");
       setSize(700, 460);
       setLocationRelativeTo(null);
       setLayout(new BorderLayout(10, 10));
       Color azulFondo = new Color(217,234,246);
       getContentPane().setBackground(azulFondo);
       
       JPanel panelSuperior = new JPanel(new BorderLayout(10, 0));
       panelSuperior.setBackground(azulFondo);
       panelSuperior.setBorder(new EmptyBorder(8, 12, 8, 12));

       JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
       panelTitulo.setOpaque(false);
       JLabel labelPaso = new JLabel("Paso 2 de 2 · Pago");
       labelPaso.setFont(new Font("Dialog", Font.PLAIN, 12));
       labelPaso.setForeground(new Color(80, 80, 80));
       JLabel labelTitulo = new JLabel("Introduce los datos de tu tarjeta:");
       labelTitulo.setFont(new Font("Dialog", Font.BOLD, 16));
       panelTitulo.add(labelPaso);
       panelTitulo.add(Box.createHorizontalStrut(20));
       panelTitulo.add(labelTitulo);
       panelSuperior.add(panelTitulo, BorderLayout.WEST);

       labelTiempo = new JLabel();
       labelTiempo.setFont(new Font("Dialog", Font.BOLD, 14));
       labelTiempo.setHorizontalAlignment(SwingConstants.RIGHT);
       labelTiempo.setForeground(new Color(192, 57, 43));
       panelSuperior.add(labelTiempo, BorderLayout.EAST);
       add(panelSuperior, BorderLayout.NORTH);
       
       if (temporizador != null) {
           temporizador.setLabel(labelTiempo);
       }

       progressPaso = new JProgressBar(0, 100);
       progressPaso.setValue(70);
       progressPaso.setStringPainted(true);
       progressPaso.setString("Reserva · Datos · Pago");
       progressPaso.setForeground(new Color(46, 204, 113));
       progressPaso.setBackground(Color.WHITE);
       progressPaso.setBorder(new EmptyBorder(0, 10, 5, 10));
       
       JPanel panelNorteContenedor = new JPanel(new BorderLayout());
       panelNorteContenedor.add(panelSuperior, BorderLayout.NORTH);
       
       panelNorteContenedor.add(progressPaso, BorderLayout.SOUTH);

    // Ahora añade este panel contenedor al JFrame en el NORTH
       add(panelNorteContenedor, BorderLayout.NORTH);

       panelPrincipalCentral = new JPanel(new GridLayout(1, 2, 10, 0));
       panelPrincipalCentral.setBorder(new EmptyBorder(10, 10, 10, 10));
       panelPrincipalCentral.setBackground(azulFondo);

       JPanel panelResumen = new JPanel();
       panelResumen.setLayout(new BoxLayout(panelResumen, BoxLayout.Y_AXIS));
       panelResumen.setBorder(new EmptyBorder(15, 15, 15, 15));
       panelResumen.setBackground(Color.WHITE);

       JLabel labelCabeceraResumen = new JLabel("Resumen de la compra");
       labelCabeceraResumen.setFont(new Font("Dialog", Font.BOLD, 14));
       panelResumen.add(labelCabeceraResumen);
       panelResumen.add(Box.createVerticalStrut(10));

       labelResumenPelicula = new JLabel();
       labelResumenSala = new JLabel();
       labelResumenAsientos = new JLabel();
       labelResumenTotal = new JLabel();

       labelResumenPelicula.setFont(new Font("Dialog", Font.PLAIN, 12));
       labelResumenSala.setFont(new Font("Dialog", Font.PLAIN, 12));
       labelResumenAsientos.setFont(new Font("Dialog", Font.PLAIN, 12));
       labelResumenTotal.setFont(new Font("Dialog", Font.BOLD, 13));
       labelResumenTotal.setForeground(new Color(39, 174, 96));

       rellenarLabelsResumen();

       panelResumen.add(labelResumenPelicula);
       panelResumen.add(Box.createVerticalStrut(5));
       panelResumen.add(labelResumenSala);
       panelResumen.add(Box.createVerticalStrut(5));
       panelResumen.add(labelResumenAsientos);
       panelResumen.add(Box.createVerticalStrut(10));
       panelResumen.add(new JSeparator());
       panelResumen.add(Box.createVerticalStrut(10));
       panelResumen.add(labelResumenTotal);
       panelResumen.add(Box.createVerticalStrut(15));

       checkRecibirCorreo = new JCheckBox("Enviar comprobante al correo: " + (correo != null ? correo : ""));
       checkRecibirCorreo.setSelected(true);
       checkRecibirCorreo.setOpaque(false);
       panelResumen.add(checkRecibirCorreo);

       panelResumen.add(Box.createVerticalStrut(10));
       checkGuardarTarjeta = new JCheckBox("Guardar método de pago para futuras compras");
       checkGuardarTarjeta.setOpaque(false);
       panelResumen.add(checkGuardarTarjeta);

       panelResumen.add(Box.createVerticalGlue());

       JPanel panelMetodo = new JPanel();
       panelMetodo.setOpaque(false);
       panelMetodo.setLayout(new BoxLayout(panelMetodo, BoxLayout.Y_AXIS));
       panelMetodo.setBorder(new EmptyBorder(10, 0, 0, 0));

       JLabel labelMetodo = new JLabel("Método de pago");
       labelMetodo.setFont(new Font("Dialog", Font.BOLD, 13));
       panelMetodo.add(labelMetodo);
       panelMetodo.add(Box.createVerticalStrut(6));
       comboMetodoPago = new JComboBox<>(new String[]{
               "Tarjeta de crédito / débito",
               "Tarjeta regalo",
               "PayPal (simulado)"
       });
       comboMetodoPago.setSelectedIndex(0);
       comboMetodoPago.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
       panelMetodo.add(comboMetodoPago);

       panelResumen.add(Box.createVerticalStrut(10));
       panelResumen.add(panelMetodo);

       panelPrincipalCentral.add(panelResumen);

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

       panelCentral.add(Box.createVerticalStrut(8));
       labelErrores = new JLabel(" ");
       labelErrores.setForeground(new Color(192, 57, 43));
       labelErrores.setFont(new Font("Dialog", Font.PLAIN, 11));
       panelCentral.add(labelErrores);

       panelPrincipalCentral.add(panelCentral);
       add(panelPrincipalCentral, BorderLayout.CENTER);
       
       JPanel panelBotones = new JPanel(new BorderLayout());
       panelBotones.setBackground(azulFondo);
       panelBotones.setBorder(new EmptyBorder(5, 10, 10, 10));
       
       panelBotonesDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
       panelBotonesDerecha.setOpaque(false);

       JButton botonPagar = new JButton("Confirmar Pago");
       botonPagar.setBackground(new Color(39, 174, 96));
       botonPagar.setForeground(Color.WHITE);
       botonPagar.setFocusPainted(false);
       botonPagar.setPreferredSize(new Dimension(150, 32));
       
       JButton botonCancelar = new JButton("Cancelar");
       botonCancelar.setBackground(new Color(231, 76, 60));
       botonCancelar.setForeground(Color.WHITE);
       botonCancelar.setFocusPainted(false);
       botonCancelar.setPreferredSize(new Dimension(120, 32));

       panelBotonesDerecha.add(botonCancelar);
       panelBotonesDerecha.add(botonPagar);

       JPanel panelBotonesIzquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
       panelBotonesIzquierda.setOpaque(false);
       JButton botonRellenarEjemplo = new JButton("Autorrellenar datos");
       botonRellenarEjemplo.setFocusPainted(false);
       botonRellenarEjemplo.setBackground(new Color(52, 152, 219));
       botonRellenarEjemplo.setForeground(Color.WHITE);
       botonRellenarEjemplo.setPreferredSize(new Dimension(150, 28));
       panelBotonesIzquierda.add(botonRellenarEjemplo);

       panelBotones.add(panelBotonesIzquierda, BorderLayout.WEST);
       panelBotones.add(panelBotonesDerecha, BorderLayout.EAST);

       add(panelBotones, BorderLayout.SOUTH);
       
       botonCancelar.addActionListener(e -> {
           if (temporizador != null) {
               temporizador.detener();
           }
           dispose();
       });

       botonRellenarEjemplo.addActionListener(e -> rellenarDatosEjemplo());

       botonPagar.addActionListener(e -> {
           if (camposVacios()) {
               labelErrores.setText("Completa todos los campos de la tarjeta antes de continuar.");
               JOptionPane.showMessageDialog(this,
                   "Por favor, completa todos los campos.",
                   "Datos incompletos",
                   JOptionPane.WARNING_MESSAGE
               );
           } else if (!validarCamposTarjeta()) {
               JOptionPane.showMessageDialog(this,
                   "Revisa el número de tarjeta, fecha y CVV.\nParece que alguno de los datos no es válido.",
                   "Datos no válidos",
                   JOptionPane.ERROR_MESSAGE
               );
           } else {
               if (temporizador != null) {
                   temporizador.detener();
               }
               
               CineGestorBD gestorBD = new CineGestorBD();
               Reserva reserva = new Reserva(pelicula, horario, sala, asientos, precioTotal);
               gestorBD.insertarReserva(reserva);

               StringBuilder sb = new StringBuilder();
               sb.append("¡Pago realizado con éxito!\n\n");
               if (nombre != null && !nombre.isEmpty()) {
                   sb.append("Gracias por tu compra, ").append(nombre).append(".\n");
               }
               if (checkRecibirCorreo.isSelected() && correo != null && !correo.isEmpty()) {
                   sb.append("Se enviará confirmación a: ").append(correo).append("\n");
               }
               if (checkGuardarTarjeta.isSelected()) {
                   sb.append("\nLa tarjeta se marcará como favorita para próximas reservas.");
               }

               JOptionPane.showMessageDialog(this,
                   sb.toString(),
                   "Pago Confirmado",
                   JOptionPane.INFORMATION_MESSAGE
               );

               botonPagar.setEnabled(false);
               botonPagar.setText("Pago completado");

               JButton botonFinalizar = new JButton("Volver al menú principal");
               botonFinalizar.setBackground(new Color(41, 128, 185));
               botonFinalizar.setForeground(Color.WHITE);
               botonFinalizar.setFocusPainted(false);
               botonFinalizar.setPreferredSize(new Dimension(200, 32));

               botonFinalizar.addActionListener(ev -> {
                   dispose();
                   JFramePrincipal principal = new JFramePrincipal(listaPeliculas,gestor);
                   principal.setVisible(true);
               });

               panelBotonesDerecha.add(botonFinalizar);
               panelBotonesDerecha.revalidate();
               panelBotonesDerecha.repaint();
           }
       });

       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
       panel.setBorder(new EmptyBorder(6,6,6,6));
       JLabel label = new JLabel(titulo);
       label.setFont(new Font("Dialog", Font.PLAIN, 12));
       panel.add(label, BorderLayout.NORTH);
       panel.add(campo, BorderLayout.CENTER);
       return panel;
   }

   private void rellenarLabelsResumen() {
       String tituloPeli = pelicula != null ? pelicula.getTitulo() : "Película no indicada";
       String textoPeli = "Película: " + tituloPeli;
       String textoHorario = "Horario: " + (horario != null ? horario.toString() : "-");
       String textoSala = "Sala: " + (sala != null ? sala.getId() : "-");
       String textoAsientos = "Asientos: " + (asientos != null ? asientos : "-");
       String textoTotal = String.format("Total: %.2f €", precioTotal);

       labelResumenPelicula.setText(textoPeli);
       labelResumenSala.setText(textoSala + " · " + textoHorario);
       labelResumenAsientos.setText(textoAsientos);
       labelResumenTotal.setText(textoTotal);
   }

   private boolean validarCamposTarjeta() {
       String numero = textNumeroTarjeta.getText().replaceAll("\\s|-", "");
       String cad = textCaducidad.getText().trim();
       String cvv = textCVV.getText().trim();
       boolean valido = true;
       StringBuilder sb = new StringBuilder();

       if (!REGEX_TARJETA.matcher(numero).matches() || !luhnValido(numero)) {
           valido = false;
           sb.append("Número de tarjeta no válido. ");
       }
       if (!REGEX_CADUCIDAD.matcher(cad).matches()) {
           valido = false;
           sb.append("Fecha de caducidad incorrecta. ");
       }
       if (!REGEX_CVV.matcher(cvv).matches()) {
           valido = false;
           sb.append("CVV inválido.");
       }

       if (!valido) {
           labelErrores.setText(sb.toString());
       } else {
           labelErrores.setText(" ");
       }
       return valido;
   }

   private boolean luhnValido(String numero) {
       int suma = 0;
       boolean alternar = false;
       for (int i = numero.length() - 1; i >= 0; i--) {
           char c = numero.charAt(i);
           if (!Character.isDigit(c)) return false;
           int n = c - '0';
           if (alternar) {
               n *= 2;
               if (n > 9) {
                   n = n - 9;
               }
           }
           suma += n;
           alternar = !alternar;
       }
       return suma % 10 == 0;
   }

   private void rellenarDatosEjemplo() {
       if (textTitular.getText().isEmpty()) {
           textTitular.setText(nombre != null && !nombre.isEmpty() ? nombre : "Nombre Apellidos");
       }
       if (textNumeroTarjeta.getText().isEmpty()) {
           textNumeroTarjeta.setText("4111 1111 1111 1111");
       }
       if (textCaducidad.getText().isEmpty()) {
           textCaducidad.setText("12/30");
       }
       if (textCVV.getText().isEmpty()) {
           textCVV.setText("123");
       }
   }

   public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> {
           Pelicula peliDummy = new Pelicula();
           try {
               peliDummy.setTitulo("Película de prueba");
           } catch (Exception ignored) {}

           Horario horarioDummy = null;
           Sala salaDummy = null;
           String asientosDummy = "C3 C4 C5";
           double totalDummy = 21.50;
           String nombreDummy = "Cliente de Prueba";
           String correoDummy = "correo@ejemplo.com";

           JFrameTemporizadorReserva tempDummy = null;

           new JFramePago(
                   peliDummy,
                   horarioDummy,
                   salaDummy,
                   asientosDummy,
                   totalDummy,
                   nombreDummy,
                   correoDummy,
                   null,
                   tempDummy,gestor
           );
       });
   }
   
   
}

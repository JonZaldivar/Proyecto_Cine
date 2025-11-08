
package proyectoCine.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import proyectoCine.domain.Horario;
import proyectoCine.domain.Pelicula;
import proyectoCine.domain.Sala;

public class JFrameSala extends JFrame {
    
    private Sala sala;
    private JPanel panelAsientos;
    private Pelicula pelicula;
    private Horario horario;
    private JComboBox<String>[][] comboAsientos;
    private JButton btnConfirmar;
    private JLabel Titulosalita;
    private List<Pelicula> listaPeliculas;
    
    // Colores personalizados
    private static final Color COLOR_FONDO_PRINCIPAL = new Color(25, 25, 35);
    private static final Color COLOR_HEADER = new Color(40, 40, 55);
    private static final Color COLOR_ASIENTO_DISPONIBLE = new Color(34, 139, 34);
    private static final Color COLOR_ASIENTO_SELECCIONADO = new Color(255, 193, 7);
    private static final Color COLOR_PANEL_ASIENTO = new Color(45, 45, 60);
    private static final Color COLOR_PANTALLA = new Color(200, 200, 200);
    
    // Iconos de asientos
    private ImageIcon iconoDisponible;
    private ImageIcon iconoSeleccionado;
    
    public JFrameSala(Sala sala) {
        this.sala = sala;
        this.pelicula = pelicula;
        this.horario = horario;
        crearIconos();
        inicializarComponentes();
        configurarVentana();
    }
    
    public JFrameSala(Sala sala, Pelicula pelicula, Horario horario, List<Pelicula> listaPeliculas) {
        this.sala = sala;
        this.pelicula = pelicula;
        this.horario = horario;
        this.listaPeliculas = listaPeliculas;
        crearIconos();
        inicializarComponentes();
        configurarVentana();
    }
    
    private void crearIconos() {
        String rutaImagen = "C:\\Users\\jon.zaldivar\\ProgAplicaciones\\Proyecto_Cine\\resources\\asiento_cine.png";
        
        try {
            ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
            Image img = iconoOriginal.getImage();
            Image imgEscalada = img.getScaledInstance(60, 40, Image.SCALE_SMOOTH);
            
            iconoDisponible = new ImageIcon(imgEscalada);
            iconoSeleccionado = new ImageIcon(imgEscalada); 
            
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen: " + e.getMessage());
            iconoDisponible = crearIconoAsiento(COLOR_ASIENTO_DISPONIBLE, 60, 40);
            iconoSeleccionado = crearIconoAsiento(COLOR_ASIENTO_SELECCIONADO, 60, 40);
        }
    }
    
    private ImageIcon crearIconoAsiento(Color color, int ancho, int alto) {
        BufferedImage image = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(color);
        g2d.fillRoundRect(5, 5, ancho - 10, alto - 20, 12, 12);
        g2d.setColor(color.darker());
        g2d.fillRoundRect(8, alto - 18, ancho - 16, 15, 10, 10);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    private void inicializarComponentes() {
        getContentPane().setBackground(COLOR_FONDO_PRINCIPAL);
        
        // Panel superior con título
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(COLOR_HEADER);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        String titulo = "Sala " + sala.getId();
        if (pelicula != null && horario != null) {
            titulo = pelicula.getTitulo() + " - " + horario.toString() + " - Sala " + sala.getId();
        }
        
        Titulosalita = new JLabel(titulo);
        Titulosalita.setFont(new Font("Arial", Font.BOLD, 22));
        Titulosalita.setForeground(Color.WHITE);
        panelSuperior.add(Titulosalita);
        
        // Panel de la pantalla
        JPanel panelPantalla = new JPanel();
        panelPantalla.setBackground(COLOR_FONDO_PRINCIPAL);
        panelPantalla.setBorder(BorderFactory.createEmptyBorder(15, 50, 15, 50));
        
        JPanel pantalla = new JPanel();
        pantalla.setBackground(COLOR_PANTALLA);
        pantalla.setPreferredSize(new Dimension(700, 45));
        pantalla.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        
        JLabel lblPantalla = new JLabel(" PANTALLA ", SwingConstants.CENTER);
        lblPantalla.setFont(new Font("Arial", Font.BOLD, 18));
        lblPantalla.setForeground(Color.BLACK);
        pantalla.setLayout(new BorderLayout());
        pantalla.add(lblPantalla);
        
        panelPantalla.add(pantalla);
        
        // Panel de asientos con pasillo central
        panelAsientos = new JPanel();
        panelAsientos.setLayout(new GridBagLayout());
        panelAsientos.setBackground(COLOR_FONDO_PRINCIPAL);
        panelAsientos.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Crear JComboBox de asientos
        comboAsientos = new JComboBox[sala.getFila()][sala.getColumna()];
        int pasilloColumna = sala.getColumna() / 2;
        
        for (int i = 0; i < sala.getFila(); i++) {
            // Etiqueta de fila
            gbc.gridx = 0;
            gbc.gridy = i;
            JLabel lblFila = new JLabel(String.valueOf((char)('A' + i)));
            lblFila.setFont(new Font("Arial", Font.BOLD, 16));
            lblFila.setForeground(Color.WHITE);
            panelAsientos.add(lblFila, gbc);
            
            int columnaActual = 1;
            
            for (int j = 0; j < sala.getColumna(); j++) {
                // Crear pasillo en el medio
                if (j == pasilloColumna) {
                    gbc.gridx = columnaActual++;
                    gbc.gridy = i;
                    JLabel lblPasillo = new JLabel("     ");
                    panelAsientos.add(lblPasillo, gbc);
                }
                
                gbc.gridx = columnaActual++;
                gbc.gridy = i;
                
                // Determinar precio según la fila
                String precioNormal = "";
                String precioReducida = "";
                String zonaNombre = "";
                
                if (i < 3) {
                    precioNormal = "Normal - 12€";
                    precioReducida = "Reducida - 9€";
                    zonaNombre = "ZONA DELANTERA";
                } else if (i < 6) {
                    precioNormal = "Normal - 10€";
                    precioReducida = "Reducida - 7€";
                    zonaNombre = "ZONA MEDIA (MEJOR VISTA)";
                } else {
                    precioNormal = "Normal - 8€";
                    precioReducida = "Reducida - 5€";
                    zonaNombre = "ZONA TRASERA";
                }
                
                // Crear JComboBox con opciones
                String[] opciones = {"Disponible", precioNormal, precioReducida};
                JComboBox<String> combo = new JComboBox<>(opciones);
                combo.setFont(new Font("Arial", Font.PLAIN, 10));
                combo.setPreferredSize(new Dimension(110, 25));
                combo.setBackground(Color.WHITE);
                
                // Crear panel para cada asiento
                JPanel panelAsiento = new JPanel();
                panelAsiento.setLayout(new BorderLayout(3, 3));
                panelAsiento.setBackground(COLOR_PANEL_ASIENTO);
                panelAsiento.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(80, 80, 100), 2),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
                panelAsiento.setPreferredSize(new Dimension(120, 110));
                
                // Imagen del asiento
                JLabel lblImagenAsiento = new JLabel(iconoDisponible);
                lblImagenAsiento.setHorizontalAlignment(SwingConstants.CENTER);
                
                // Etiqueta del asiento
                JLabel lblAsiento = new JLabel((char)('A' + i) + String.valueOf(j + 1), SwingConstants.CENTER);
                lblAsiento.setFont(new Font("Arial", Font.BOLD, 12));
                lblAsiento.setForeground(Color.WHITE);
                
                panelAsiento.add(lblAsiento, BorderLayout.NORTH);
                panelAsiento.add(lblImagenAsiento, BorderLayout.CENTER);
                panelAsiento.add(combo, BorderLayout.SOUTH);
                
                comboAsientos[i][j] = combo;
                
                // Tooltip informativo al pasar el ratón
                String tooltipTexto = String.format(
                    "<html><div style='padding: 5px;'>" +
                    "<b>Asiento %s%d</b><br>" +
                    "<b>%s</b><br>" +
                    "━━━━━━━━━━━━━━<br>" +
                    " Precio Normal: 12€<br>" +
                    " Precio Reducida: 9€<br>" +
                    "━━━━━━━━━━━━━━<br>" +
                    "<i>Haz clic para seleccionar</i>" +
                    "</div></html>",
                    (char)('A' + i), j + 1, zonaNombre
                );
                
                panelAsiento.setToolTipText(tooltipTexto);
                lblImagenAsiento.setToolTipText(tooltipTexto);
                combo.setToolTipText(tooltipTexto);
                
                // Cambiar color cuando se selecciona
                final int fila = i;
                final int columna = j;
                combo.addActionListener(e -> {
                    if (combo.getSelectedIndex() > 0) {
                        lblImagenAsiento.setIcon(iconoSeleccionado);
                        panelAsiento.setBackground(new Color(255, 200, 50));
                        panelAsiento.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(COLOR_ASIENTO_SELECCIONADO, 3),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)
                        ));
                    } else {
                        lblImagenAsiento.setIcon(iconoDisponible);
                        panelAsiento.setBackground(COLOR_PANEL_ASIENTO);
                        panelAsiento.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(80, 80, 100), 2),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)
                        ));
                    }
                });
                
                panelAsientos.add(panelAsiento, gbc);
            }
        }
        
        // Panel con leyenda
        JPanel panelLeyenda = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelLeyenda.setBackground(COLOR_FONDO_PRINCIPAL);
        panelLeyenda.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        panelLeyenda.add(crearItemLeyenda("Disponible", COLOR_ASIENTO_DISPONIBLE));
        panelLeyenda.add(crearItemLeyenda("Seleccionado", COLOR_ASIENTO_SELECCIONADO));
        
        // Panel inferior con botón confirmar
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(COLOR_HEADER);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        btnConfirmar = new JButton("✓ Confirmar Reserva");
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 16));
        btnConfirmar.setBackground(new Color(0, 123, 255));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setPreferredSize(new Dimension(200, 45));
        btnConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirmar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Efecto hover en el botón
        btnConfirmar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnConfirmar.setBackground(new Color(0, 105, 217));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnConfirmar.setBackground(new Color(0, 123, 255));
            }
        });
        
        btnConfirmar.addActionListener(e -> confirmarReserva());
        panelInferior.add(btnConfirmar);
        
        // Agregar componentes al frame
        setLayout(new BorderLayout(0, 0));
        add(panelSuperior, BorderLayout.NORTH);
        
        JPanel panelCentral = new JPanel(new BorderLayout(0, 0));
        panelCentral.setBackground(COLOR_FONDO_PRINCIPAL);
        panelCentral.add(panelPantalla, BorderLayout.NORTH);
        
        JScrollPane scrollAsientos = new JScrollPane(panelAsientos);
        scrollAsientos.getViewport().setBackground(COLOR_FONDO_PRINCIPAL);
        scrollAsientos.setBorder(null);
        panelCentral.add(scrollAsientos, BorderLayout.CENTER);
        panelCentral.add(panelLeyenda, BorderLayout.SOUTH);
        
        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private JPanel crearItemLeyenda(String texto, Color color) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panel.setBackground(COLOR_FONDO_PRINCIPAL);
        
        JPanel cuadroColor = new JPanel();
        cuadroColor.setPreferredSize(new Dimension(30, 30));
        cuadroColor.setBackground(color);
        cuadroColor.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        
        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        
        panel.add(cuadroColor);
        panel.add(label);
        
        return panel;
    }
    
    private void confirmarReserva() {
        StringBuilder asientosSeleccionados = new StringBuilder();
        int cantidadButacas = 0;
        double total = 0;

        for (int i = 0; i < sala.getFila(); i++) {
            for (int j = 0; j < sala.getColumna(); j++) {
                if (comboAsientos[i][j].getSelectedIndex() > 0) {
                    cantidadButacas++;

                    String seleccion = (String) comboAsientos[i][j].getSelectedItem();
                    asientosSeleccionados.append((char)('A' + i)).append(j+1).append("  ");

                    String precio = seleccion.substring(seleccion.indexOf("-") + 2).replace("€", "");
                    total += Double.parseDouble(precio);
                }
            }
        }

        if (cantidadButacas == 0) {
            JOptionPane.showMessageDialog(this,
                "Por favor, selecciona al menos un asiento",
                "⚠ Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Confirmar reserva?\n\n Asientos: " + asientosSeleccionados.toString() +
            "\n Total: " + String.format("%.2f", total) + "€",
            "Confirmación de Reserva",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (confirm == JOptionPane.OK_OPTION) {
            this.dispose();
            JFrameReserva ventanaReserva = new JFrameReserva(pelicula, horario, sala, 
                asientosSeleccionados.toString(), total, listaPeliculas);
            ventanaReserva.setVisible(true);
        }
    }
    
    private void configurarVentana() {
        setTitle(" Selección de Asientos - Sala " + sala.getId());
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Sala salaEjemplo = new Sala(1, 8, 10);
            JFrameSala frame = new JFrameSala(salaEjemplo);
            frame.setVisible(true);
        });
    }
}
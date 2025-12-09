package proyectoCine.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

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
    
    // Variables para hilos
    private JLabel lblAnimacionPantalla;
    private Thread hiloPantalla;
    private Thread hiloAsientosDestacados;
    private volatile boolean animacionActiva = true;
    private List<AsientoDestacado> asientosDestacados;
    
    // Colores personalizados
    private static final Color COLOR_FONDO_PRINCIPAL = new Color(25, 25, 35);
    private static final Color COLOR_HEADER = new Color(40, 40, 55);
    private static final Color COLOR_ASIENTO_DISPONIBLE = new Color(34, 139, 34);
    private static final Color COLOR_ASIENTO_SELECCIONADO = new Color(255, 193, 7);
    private static final Color COLOR_ASIENTO_OCUPADO = new Color(220, 53, 69);
    private static final Color COLOR_PANEL_ASIENTO = new Color(45, 45, 60);
    private static final Color COLOR_PANEL_ASIENTO_DISPONIBLE = new Color(50, 150, 50);
    private static final Color COLOR_PANTALLA = new Color(200, 200, 200);
    private static final Color COLOR_OFERTA = new Color(255, 140, 0); // Naranja para ofertas
    
    // Iconos de asientos
    private ImageIcon iconoDisponible;
    private ImageIcon iconoSeleccionado;
    private ImageIcon iconoOcupado;
    
    // Clase interna para manejar asientos destacados
    private class AsientoDestacado {
        int fila;
        int columna;
        JPanel panel;
        boolean esOferta;
        
        AsientoDestacado(int fila, int columna, JPanel panel) {
            this.fila = fila;
            this.columna = columna;
            this.panel = panel;
            this.esOferta = true;
        }
    }
    
    public JFrameSala(Sala sala) {
        this.sala = sala;
        this.pelicula = pelicula;
        this.horario = horario;
        this.asientosDestacados = new ArrayList<>();
        crearIconos();
        inicializarComponentes();
        configurarVentana();
        iniciarHilos();
    }
    
    public JFrameSala(Sala sala, Pelicula pelicula, Horario horario, List<Pelicula> listaPeliculas) {
        this.sala = sala;
        this.pelicula = pelicula;
        this.horario = horario;
        this.listaPeliculas = listaPeliculas;
        this.asientosDestacados = new ArrayList<>();
        crearIconos();
        inicializarComponentes();
        configurarVentana();
        iniciarHilos();
    }
    
    private void iniciarHilos() {
    	
        // El primer hilo de la pantalla: Animación de la pantalla del cine
        hiloPantalla = new Thread(() -> {
            String[] mensajes = {
                " BIENVENIDO AL CINE ",// diferentes animaciones del cine 
                " DISFRUTA LA FUNCIÓN ",
                " SELECCIONA TUS ASIENTOS",
                " PRÓXIMAMENTE ESTRENOS ",
                " OFERTAS ESPECIALES "
            };
            int indice = 0;
            
            while (animacionActiva) {
                try {
                    final String mensaje = mensajes[indice];
                    SwingUtilities.invokeLater(() -> {
                        if (lblAnimacionPantalla != null) {
                            lblAnimacionPantalla.setText(mensaje);
                        }
                    });
                    
                    indice = (indice + 1) % mensajes.length;
                    Thread.sleep(3000); // Cambiar mensaje cada 3 segundos
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        hiloPantalla.setName("HiloPantallaAnimacion");
        hiloPantalla.start();
        
        // Segunda Implementacion de hilo : Destacar asientos con ofertas especiales 
        hiloAsientosDestacados = new Thread(() -> {
            Random random = new Random(); // Con esto me creo aleatorios
            
            while (animacionActiva) {
                try {
                    Thread.sleep(800); // Con esto hago que empice a parpadear 
                    
                    SwingUtilities.invokeLater(() -> {
                        for (AsientoDestacado asiento : asientosDestacados) {
                            if (comboAsientos[asiento.fila][asiento.columna].getSelectedIndex() == 0) {
                                // Alternar entre color naranja brillante y verde
                                if (asiento.esOferta) {
                                    asiento.panel.setBackground(COLOR_OFERTA);
                                    asiento.panel.setBorder(BorderFactory.createCompoundBorder(
                                        BorderFactory.createLineBorder(COLOR_OFERTA, 3 ),
                                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                                    ));
                                } else {
                                    asiento.panel.setBackground(COLOR_PANEL_ASIENTO_DISPONIBLE);
                                    asiento.panel.setBorder(BorderFactory.createCompoundBorder(
                                        BorderFactory.createLineBorder(COLOR_ASIENTO_DISPONIBLE, 2),
                                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                                    ));
                                }
                                asiento.esOferta = !asiento.esOferta;
                            }
                        }
                    });
                    
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        hiloAsientosDestacados.setName("HiloAsientosDestacados");
        hiloAsientosDestacados.start();
    }
    
    private void crearIconos() {
        ImageIcon iconoOriginal = null;
        
        java.net.URL urlImagen = getClass().getResource("/asiento_cine.png");
        
        if (urlImagen != null) {
            iconoOriginal = new ImageIcon(urlImagen);
            
            try {
                Image img = iconoOriginal.getImage();
                Image imgEscalada = img.getScaledInstance(60, 40, Image.SCALE_SMOOTH);
                
                iconoDisponible = new ImageIcon(imgEscalada);
                iconoSeleccionado = new ImageIcon(imgEscalada);
                iconoOcupado = new ImageIcon(imgEscalada);
                
            } catch (Exception e) {
                System.out.println("Error al escalar la imagen: " + e.getMessage());
                iconoDisponible = crearIconoAsiento(COLOR_ASIENTO_DISPONIBLE, 60, 40);
                iconoSeleccionado = crearIconoAsiento(COLOR_ASIENTO_SELECCIONADO, 60, 40);
                iconoOcupado = crearIconoAsiento(COLOR_ASIENTO_OCUPADO, 60, 40);
            }
        } else {
            System.out.println("No se encontró la imagen asiento_cine.png en el classpath");
            iconoDisponible = crearIconoAsiento(COLOR_ASIENTO_DISPONIBLE, 60, 40);
            iconoSeleccionado = crearIconoAsiento(COLOR_ASIENTO_SELECCIONADO, 60, 40);
            iconoOcupado = crearIconoAsiento(COLOR_ASIENTO_OCUPADO, 60, 40);
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
        
        // Panel de la pantalla con animación
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
        
        lblAnimacionPantalla = new JLabel(" BIENVENIDO AL CINE ", SwingConstants.CENTER);
        lblAnimacionPantalla.setFont(new Font("Arial", Font.BOLD, 18));
        lblAnimacionPantalla.setForeground(Color.BLACK);
        pantalla.setLayout(new BorderLayout());
        pantalla.add(lblAnimacionPantalla);
        
        panelPantalla.add(pantalla);
        
        // Panel de asientos con pasillo central
        panelAsientos = new JPanel();
        panelAsientos.setLayout(new GridBagLayout());
        panelAsientos.setBackground(COLOR_FONDO_PRINCIPAL);
        panelAsientos.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Reducir una fila (usar sala.getFila() - 1)
        int filasVisibles = sala.getFila() - 3;
        
        // Crear JComboBox de asientos
        comboAsientos = new JComboBox[filasVisibles][sala.getColumna()];
        int pasilloColumna = sala.getColumna() / 2;
        
        // Random para asientos ocupados (aproximadamente 20-30% ocupados)
        Random random = new Random();
        
        // Seleccionar 3-5 asientos aleatorios para destacar como ofertas
        int numAsientosDestacados = 3 + random.nextInt(3);
        List<int[]> posicionesDestacadas = new ArrayList<>();
        
        while (posicionesDestacadas.size() < numAsientosDestacados) {
            int filaAleatoria = random.nextInt(filasVisibles);
            int columnaAleatoria = random.nextInt(sala.getColumna());
            int[] posicion = {filaAleatoria, columnaAleatoria};
            
            boolean existe = false;
            for (int[] p : posicionesDestacadas) {
                if (p[0] == posicion[0] && p[1] == posicion[1]) {
                    existe = true;
                    break;
                }
            }
            
            if (!existe) {
                posicionesDestacadas.add(posicion);
            }
        }
        
        for (int i = 0; i < filasVisibles; i++) {
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
                
                // Determinar si el asiento está ocupado aleatoriamente (25% de probabilidad)
                boolean estaOcupado = random.nextInt(100) < 25;
                
                // Verificar si es un asiento destacado
                boolean esDestacado = false;
                for (int[] pos : posicionesDestacadas) {
                    if (pos[0] == i && pos[1] == j && !estaOcupado) {
                        esDestacado = true;
                        break;
                    }
                }
                
                // Determinar precio según la fila (mismo formato para todos)
                String precioNormal = "";
                String precioReducida = "";
                String zonaNombre = "";
                
                if (i < 3) {
                    if (esDestacado) {
                        precioNormal = "Normal - 10€";
                        precioReducida = "Reducida - 7€";
                    } else {
                        precioNormal = "Normal - 12€";
                        precioReducida = "Reducida - 9€";
                    }
                    zonaNombre = "ZONA DELANTERA";
                } else if (i < 6) {
                    if (esDestacado) {
                        precioNormal = "Normal - 8€";
                        precioReducida = "Reducida - 5€";
                    } else {
                        precioNormal = "Normal - 10€";
                        precioReducida = "Reducida - 7€";
                    }
                    zonaNombre = "ZONA MEDIA (MEJOR VISTA)";
                } else {
                    if (esDestacado) {
                        precioNormal = "Normal - 6€";
                        precioReducida = "Reducida - 4€";
                    } else {
                        precioNormal = "Normal - 8€";
                        precioReducida = "Reducida - 5€";
                    }
                    zonaNombre = "ZONA TRASERA";
                }
                
                // Crear JComboBox con opciones
                String[] opciones;
                if (estaOcupado) {
                    opciones = new String[]{"Ocupado"};
                } else {
                    opciones = new String[]{"Disponible", precioNormal, precioReducida};
                }
                
                JComboBox<String> combo = new JComboBox<>(opciones);
                combo.setFont(new Font("Arial", Font.PLAIN, 10));
                combo.setPreferredSize(new Dimension(110, 25));
                combo.setEnabled(!estaOcupado);
                
                if (estaOcupado) {
                    combo.setBackground(new Color(240, 240, 240));
                } else {
                    combo.setBackground(Color.WHITE);
                }
                
                // Crear panel para cada asiento
                JPanel panelAsiento = new JPanel();
                panelAsiento.setLayout(new BorderLayout(3, 3));
                
                if (estaOcupado) {
                    panelAsiento.setBackground(new Color(180, 50, 50));
                } else {
                    panelAsiento.setBackground(COLOR_PANEL_ASIENTO_DISPONIBLE);
                }
                
                panelAsiento.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(estaOcupado ? COLOR_ASIENTO_OCUPADO : COLOR_ASIENTO_DISPONIBLE, 2),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
                panelAsiento.setPreferredSize(new Dimension(120, 110));
                
                // Imagen del asiento
                JLabel lblImagenAsiento = new JLabel(estaOcupado ? iconoOcupado : iconoDisponible);
                lblImagenAsiento.setHorizontalAlignment(SwingConstants.CENTER);
                
                // Etiqueta del asiento
                JLabel lblAsiento = new JLabel((char)('A' + i) + String.valueOf(j + 1), SwingConstants.CENTER);
                lblAsiento.setFont(new Font("Arial", Font.BOLD, 12));
                lblAsiento.setForeground(Color.WHITE);
                
                panelAsiento.add(lblAsiento, BorderLayout.NORTH);
                panelAsiento.add(lblImagenAsiento, BorderLayout.CENTER);
                panelAsiento.add(combo, BorderLayout.SOUTH);
                
                comboAsientos[i][j] = combo;
                
                // Agregar a lista de destacados si corresponde
                if (esDestacado) {
                    asientosDestacados.add(new AsientoDestacado(i, j, panelAsiento));
                }
                
                //Se ha utilizado IA generativa para esta parte 
                // Tooltip informativo al pasar el ratón
                String tooltipTexto;
                if (estaOcupado) {
                    tooltipTexto = String.format(
                        "<html><div style='padding: 5px;'>" +
                        "<b>Asiento %s%d</b><br>" +
                        "<b style='color: red;'>OCUPADO</b><br>" +
                        "━━━━━━━━━━━━━━<br>" +
                        "<i>Este asiento no está disponible</i>" +
                        "</div></html>",
                        (char)('A' + i), j + 1
                    );
                } else {
                    String preciosMostrar = esDestacado ? 
                        String.format("Precio Normal: %s<br> Precio Reducida: %s<br><b style='color: orange;'>¡OFERTA ESPECIAL!</b>", 
                            precioNormal.substring(precioNormal.indexOf("-") + 2), 
                            precioReducida.substring(precioReducida.indexOf("-") + 2)) :
                        String.format("Precio Normal: %s<br> Precio Reducida: %s", 
                            precioNormal.substring(precioNormal.indexOf("-") + 2), 
                            precioReducida.substring(precioReducida.indexOf("-") + 2));
                    
                    tooltipTexto = String.format(
                        "<html><div style='padding: 5px;'>" +
                        "<b>Asiento %s%d</b><br>" +
                        "<b>%s</b><br>" +
                        "━━━━━━━━━━━━━━<br>" +
                        "%s<br>" +
                        "━━━━━━━━━━━━━━<br>" +
                        "<i>Haz clic para seleccionar</i>" +
                        "</div></html>",
                        (char)('A' + i), j + 1, zonaNombre, preciosMostrar
                    );
                }
                
                panelAsiento.setToolTipText(tooltipTexto);
                lblImagenAsiento.setToolTipText(tooltipTexto);
                combo.setToolTipText(tooltipTexto);
                
                // Cambiar color cuando se selecciona (solo si no está ocupado)
                if (!estaOcupado) {
                    final int fila = i;
                    final int columna = j;
                    final boolean esAsientoDestacado = esDestacado;
                    
                    combo.addActionListener(e -> {
                        if (combo.getSelectedIndex() > 0) {
                            // Seleccionado: cambiar a amarillo
                            lblImagenAsiento.setIcon(iconoSeleccionado);
                            panelAsiento.setBackground(new Color(255, 200, 50));
                            panelAsiento.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(COLOR_ASIENTO_SELECCIONADO, 3),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)
                            ));
                            
                            // Si era destacado, quitar de la animación
                            if (esAsientoDestacado) {
                                asientosDestacados.removeIf(a -> a.fila == fila && a.columna == columna);
                            }
                        } else {
                            // Disponible: volver a verde
                            lblImagenAsiento.setIcon(iconoDisponible);
                            panelAsiento.setBackground(COLOR_PANEL_ASIENTO_DISPONIBLE);
                            panelAsiento.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(COLOR_ASIENTO_DISPONIBLE, 2),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)
                            ));
                            
                            // Si era destacado, volver a agregarlo a la animación
                            if (esAsientoDestacado) {
                                boolean yaExiste = false;
                                for (AsientoDestacado a : asientosDestacados) {
                                    if (a.fila == fila && a.columna == columna) {
                                        yaExiste = true;
                                        break;
                                    }
                                }
                                if (!yaExiste) {
                                    asientosDestacados.add(new AsientoDestacado(fila, columna, panelAsiento));
                                }
                            }
                        }
                    });
                }
                
                panelAsientos.add(panelAsiento, gbc);
            }
        }
        
        // Panel con leyenda
        JPanel panelLeyenda = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelLeyenda.setBackground(COLOR_FONDO_PRINCIPAL);
        panelLeyenda.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        panelLeyenda.add(crearItemLeyenda("Disponible", COLOR_ASIENTO_DISPONIBLE));
        panelLeyenda.add(crearItemLeyenda("Seleccionado", COLOR_ASIENTO_SELECCIONADO));
        panelLeyenda.add(crearItemLeyenda("Ocupado", COLOR_ASIENTO_OCUPADO));
        panelLeyenda.add(crearItemLeyenda("⭐ Oferta", COLOR_OFERTA));
        
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
        panelCentral.add(panelAsientos, BorderLayout.CENTER);
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

        int filasVisibles = sala.getFila() - 3;
        
        for (int i = 0; i < filasVisibles; i++) {
            for (int j = 0; j < sala.getColumna(); j++) {
                if (comboAsientos[i][j].getSelectedIndex() > 0 && !comboAsientos[i][j].getSelectedItem().equals("Ocupado")) {
                    cantidadButacas++;

                    String seleccion = (String) comboAsientos[i][j].getSelectedItem();
                    asientosSeleccionados.append((char)('A' + i)).append(j+1).append("  ");

                    String precio = seleccion.substring(seleccion.lastIndexOf("-") + 2).replace("€", "");
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
            detenerHilos(); // Detener hilos antes de cerrar
            this.dispose();
            JFrameReserva ventanaReserva = new JFrameReserva(pelicula, horario, sala, 
                asientosSeleccionados.toString(), total, listaPeliculas);
            ventanaReserva.setVisible(true);
        }
    }
    
    private void detenerHilos() {
        animacionActiva = false;
        if (hiloPantalla != null && hiloPantalla.isAlive()) {
            hiloPantalla.interrupt();
        }
        if (hiloAsientosDestacados != null && hiloAsientosDestacados.isAlive()) {
            hiloAsientosDestacados.interrupt();
        }
    }
    
    private void configurarVentana() {
        setTitle("Selección de Asientos - Sala " + sala.getId());
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Detener hilos al cerrar la ventana
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                detenerHilos();
            }//hillo
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Sala salaEjemplo = new Sala(1, 8, 10);
            JFrameSala frame = new JFrameSala(salaEjemplo);
            frame.setVisible(true);
        });
    }
}
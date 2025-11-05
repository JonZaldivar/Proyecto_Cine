package proyectoCine.gui;

import javax.swing.*;
import java.awt.*;

import proyectoCine.domain.Horario;
import proyectoCine.domain.Pelicula;
import proyectoCine.domain.Sala;

public class JFrameSala extends JFrame {
    
    private Sala sala;
    private JPanel panelAsientos;
    private Pelicula pelicula; // NUEVO: guardar película
    private Horario horario; // NUEVO: guardar horario
    private JComboBox<String>[][] comboAsientos;
    private JButton btnConfirmar;
    private JLabel Titulosalita;
    
    // Constructor original (solo sala)
    public JFrameSala(Sala sala) {
        this.sala = sala;
        this.pelicula = pelicula;
        this.horario = horario;
        inicializarComponentes();
        configurarVentana();
    }
    
    // NUEVO: Constructor con película y horario
    public JFrameSala(Sala sala, Pelicula pelicula, Horario horario) {
        this.sala = sala;
        this.pelicula = pelicula;
        this.horario = horario;
        inicializarComponentes();
        configurarVentana();
    }
    
    private void inicializarComponentes() {
        // Panel superior con título
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(51, 51, 51));
        
        // MODIFICADO: Mostrar información de película y horario si están disponibles
        String titulo = "Sala " + sala.getId();
        if (pelicula != null && horario != null) {
            titulo = pelicula.getTitulo() + " - " + horario.toString() + " - Sala " + sala.getId();
        }
        
        Titulosalita= new JLabel("Salita de Cine numero" + sala.getId());
        Titulosalita.setFont(new Font("Arial", Font.BOLD, 24));
        Titulosalita.setForeground(Color.WHITE);
        panelSuperior.add(Titulosalita);
        
        
        // Panel de la pantalla
        JPanel panelPantalla = new JPanel();
        panelPantalla.setBackground(new Color(200, 200, 200));
        JLabel lblPantalla = new JLabel("PANTALLA");
        lblPantalla.setFont(new Font("Arial", Font.BOLD, 16));
        panelPantalla.add(lblPantalla);
        
        // Panel de asientos (cuadrícula)
        panelAsientos = new JPanel();
        panelAsientos.setLayout(new GridLayout(sala.getFila(), sala.getColumna(), 10, 10));
        panelAsientos.setBackground(new Color(240, 240, 240));
        panelAsientos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Crear JComboBox de asientos
        comboAsientos = new JComboBox[sala.getFila()][sala.getColumna()];
        for (int i = 0; i < sala.getFila(); i++) {
            for (int j = 0; j < sala.getColumna(); j++) {
                // Determinar precio según la fila
                String precioNormal = "";
                String precioReducida = "";
                
                if (i < 3) { // Filas 1-3 (adelante)
                    precioNormal = "Normal - 12€";
                    precioReducida = "Reducida - 9€";
                } else if (i < 6) { // Filas 4-6 (medio)
                    precioNormal = "Normal - 10€";
                    precioReducida = "Reducida - 7€";
                } else { // Filas 7+ (atrás)
                    precioNormal = "Normal - 8€";
                    precioReducida = "Reducida - 5€";
                }
                
                // Crear JComboBox con opciones
                String[] opciones = {"Seleccionar", precioNormal, precioReducida};
                JComboBox<String> combo = new JComboBox<>(opciones);
                combo.setFont(new Font("Arial", Font.PLAIN, 11));
                
                // Crear panel para cada asiento con etiqueta y combo
                JPanel panelAsiento = new JPanel();
                panelAsiento.setLayout(new BorderLayout(2, 2));
                panelAsiento.setBackground(new Color(100, 200, 100));
                panelAsiento.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
                
                JLabel lblAsiento = new JLabel("F" + (i+1) + "-C" + (j+1), SwingConstants.CENTER);
                lblAsiento.setFont(new Font("Arial", Font.BOLD, 10));
                
                panelAsiento.add(lblAsiento, BorderLayout.NORTH);
                panelAsiento.add(combo, BorderLayout.CENTER);
                
                comboAsientos[i][j] = combo;
                panelAsientos.add(panelAsiento);
                
                // Cambiar color cuando se selecciona
                final int fila = i;
                final int columna = j;
                combo.addActionListener(e -> {
                    if (combo.getSelectedIndex() > 0) {
                        panelAsiento.setBackground(new Color(100, 150, 250)); // Azul = seleccionado
                    } else {
                        panelAsiento.setBackground(new Color(100, 200, 100)); // Verde = disponible
                    }
                });
            }
        }
        
        // Panel inferior con botón confirmar
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(new Color(51, 51, 51));
        btnConfirmar = new JButton("Confirmar Reserva");
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
        btnConfirmar.setBackground(new Color(0, 120, 215));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFocusPainted(false);
        
        // Acción del botón confirmar
        btnConfirmar.addActionListener(e -> confirmarReserva());
        
        panelInferior.add(btnConfirmar);
        
        // Agregar componentes al frame
        setLayout(new BorderLayout(10, 10));
        add(panelSuperior, BorderLayout.NORTH);
        
        JPanel panelCentral = new JPanel(new BorderLayout(5, 5));
        panelCentral.add(panelPantalla, BorderLayout.NORTH);
        panelCentral.add(panelAsientos, BorderLayout.CENTER);
        add(panelCentral, BorderLayout.CENTER);
        
        add(panelInferior, BorderLayout.SOUTH);
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
                    asientosSeleccionados.append("F").append(i+1).append("-C").append(j+1).append("  ");

                    String precio = seleccion.substring(seleccion.indexOf("-") + 2).replace("€", "");
                    total += Double.parseDouble(precio);
                }
            }
        }

        if (cantidadButacas == 0) {
            JOptionPane.showMessageDialog(this,
                "Por favor, selecciona al menos un asiento",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Confirmar reserva?\n\nAsientos: " + asientosSeleccionados.toString() +
            "\nTotal: " + String.format("%.2f", total) + "€",
            "Confirmación",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (confirm == JOptionPane.OK_OPTION) {
            this.dispose();
            JFrameReserva ventanaReserva= new JFrameReserva(pelicula, horario, sala, asientosSeleccionados.toString(), total);
            ventanaReserva.setVisible(true);
        }
    }
    
    private void configurarVentana() {
        setTitle("Selección de Asientos - Sala " + sala.getId());
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    // Método main para pruebas
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Sala salaEjemplo = new Sala(1, 8, 10);
            JFrameSala frame = new JFrameSala(salaEjemplo);
            frame.setVisible(true);
        });
    }
}
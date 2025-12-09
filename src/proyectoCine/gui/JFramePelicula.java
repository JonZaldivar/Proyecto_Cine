package proyectoCine.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import java.awt.Image;
import java.awt.Font;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.FlowLayout;

import proyectoCine.domain.Actor;
import proyectoCine.domain.Horario;
import proyectoCine.domain.Pelicula;
import proyectoCine.domain.Pelicula.Clasificacion;
import proyectoCine.domain.Pelicula.Genero;
import proyectoCine.domain.Reserva;
import proyectoCine.domain.Sala;
import proyectoCine.domain.Actor.Pais;

public class JFramePelicula extends JFrame {

    private static final long serialVersionUID = 1L;
    private Pelicula pelicula;
    private JLabel portadaLabel;
    private static List<Pelicula> listaPeliculas;

    public JFramePelicula(Pelicula pelicula, List<Pelicula> listaPeliculas) {
        this.pelicula = pelicula;
        this.listaPeliculas = listaPeliculas;

        // Configuración básica de la ventana
        setTitle(pelicula.getTitulo());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // Panel izquierdo con imagen, título y botón volver
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.setBackground(Color.WHITE);
        
        // ===== BOTÓN VOLVER (solo en esquina superior izquierda) =====
        JPanel panelBotonVolver = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelBotonVolver.setOpaque(false);
        
        JButton btnVolver = new JButton("← Volver");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 12));
        btnVolver.setBackground(new Color(33, 150, 243)); // Azul
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(21, 101, 192), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
                
        // Efecto hover
        btnVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVolver.setBackground(new Color(25, 118, 210));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVolver.setBackground(new Color(33, 150, 243));
            }
        });
                
        // Acción del botón: cerrar esta ventana y volver a la principal
        btnVolver.addActionListener(e -> {
            this.dispose(); // Cierra esta ventana
        });
        
        panelBotonVolver.add(btnVolver);
        
        // Panel que contiene botón volver y título
        JPanel panelSuperiorIzquierdo = new JPanel(new BorderLayout(0, 10));
        panelSuperiorIzquierdo.setOpaque(false);

        ImageIcon icon = null;
        
        java.net.URL urlImagen = getClass().getResource("/" + pelicula.getTitulo() + ".jpg");
        if (urlImagen == null) {
            urlImagen = getClass().getResource("/" + pelicula.getTitulo() + ".png");
        }
        
        if (urlImagen != null) {
            icon = new ImageIcon(urlImagen);
        } else {
            urlImagen = getClass().getResource("/DeustoCine.png");
            if (urlImagen != null) {
                icon = new ImageIcon(urlImagen);
            } else {
                icon = new ImageIcon(); // Imagen vacía si no se encuentra ninguna
            }
        }

        // Escala de la imagen
        Image img = icon.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);

        portadaLabel = new JLabel(scaledIcon);
        portadaLabel.setHorizontalAlignment(JLabel.CENTER);
        
        // Título de la película
        JLabel tituloLabel = new JLabel(pelicula.getTitulo(), JLabel.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        panelSuperiorIzquierdo.add(panelBotonVolver, BorderLayout.NORTH);
        panelSuperiorIzquierdo.add(tituloLabel, BorderLayout.CENTER);
        
        leftPanel.add(panelSuperiorIzquierdo, BorderLayout.NORTH);
        leftPanel.add(portadaLabel, BorderLayout.CENTER);
        
        add(leftPanel, BorderLayout.WEST);

        // Panel derecho
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.setBackground(Color.WHITE);

        // Panel de botones (arriba)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 4, 5, 5));
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        String[] buttonsText = { "Actores", "Horarios", "Reserva" };

        for (String text : buttonsText) {
            JButton boton = new JButton(text);
            boton.setFont(new Font("Arial", Font.PLAIN, 14));
            buttonPanel.add(boton);

            if (text.equals("Reserva")) {
                boton.addActionListener(e -> {
                    // Obtener los horarios disponibles de la película
                    List<Horario> horariosDisponibles = pelicula.getHorarios_disponibles();
                    
                    if (horariosDisponibles == null || horariosDisponibles.isEmpty()) {
                        JOptionPane.showMessageDialog(null, 
                            "No hay horarios disponibles para esta película", 
                            "Aviso", 
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    // Crear JComboBox solo con horarios disponibles de la película
                    JComboBox<Horario> jcomoHorarios = new JComboBox<>(
                        horariosDisponibles.toArray(new Horario[0])
                    );

                    // Renderer para mostrar texto
                    jcomoHorarios.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
                        JLabel label = new JLabel();
                        if (value != null) {
                            Horario horario = (Horario) value;
                            label.setText(horario.toString());
                            label.setHorizontalAlignment(JLabel.CENTER);
                        }
                        if (isSelected) {
                            label.setBackground(list.getSelectionBackground());
                            label.setForeground(list.getSelectionForeground());
                        }
                        label.setOpaque(true);
                        return label;
                    });

                    // Mostrar diálogo con JComboBox
                    int result = JOptionPane.showConfirmDialog(null, jcomoHorarios, "Selecciona un horario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        Horario horarioSeleccionado = (Horario) jcomoHorarios.getSelectedItem();
                        
                        if (horarioSeleccionado != null) {
                            // Crear una sala de ejemplo
                            Sala salaDisponible = new Sala(101, 8, 10);
                            
                            // Abrir ventana de selección de asientos
                            JFrameSala ventanaSala = new JFrameSala(salaDisponible, pelicula, horarioSeleccionado, listaPeliculas);
                            ventanaSala.setVisible(true);
                        }
                    }
                });
            } else if (text.equals("Actores")) {
                boton.addActionListener(e -> {
                    StringBuilder actoresList = new StringBuilder();
                    for (var actor : pelicula.getActores()) {
                        actoresList.append(actor.getNombre()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, actoresList.toString(), "Actores de " + pelicula.getTitulo(), JOptionPane.INFORMATION_MESSAGE);
                });
            } else if (text.equals("Horarios")) {
                boton.addActionListener(e -> {
                    StringBuilder horariosList = new StringBuilder();
                    for (Horario horario : pelicula.getHorarios_disponibles()) {
                        horariosList.append(horario.toString()).append("\n");
                    }
                    JOptionPane.showMessageDialog(
                        null,
                        horariosList.toString(),
                        "Horarios disponibles para " + pelicula.getTitulo(),
                        JOptionPane.INFORMATION_MESSAGE
                    );
                });
            }
        }

        rightPanel.add(buttonPanel, BorderLayout.NORTH);
        
        // Panel central
        JPanel panelCentral = new JPanel(new BorderLayout(5, 5));
        panelCentral.setBackground(Color.WHITE);
        
        // Panel de resumen
        JPanel resumenPanel = new JPanel(new BorderLayout());
        resumenPanel.setBorder(BorderFactory.createTitledBorder("Resumen"));
        resumenPanel.setBackground(Color.WHITE);
        
        JTextArea resumenArea = new JTextArea(pelicula.getResumen());
        resumenArea.setEditable(false);
        resumenArea.setLineWrap(true);
        resumenArea.setWrapStyleWord(true);
        resumenArea.setFont(new Font("Arial", Font.PLAIN, 13));
        resumenArea.setBackground(Color.WHITE);
        resumenArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(resumenArea);
        scrollPane.setBorder(null);
        resumenPanel.add(scrollPane, BorderLayout.CENTER);

        panelCentral.add(resumenPanel, BorderLayout.CENTER);
        
        // Panel de ficha técnica (entre resumen y valoración)
        JPanel panelFichaTecnica = new JPanel(new GridLayout(2, 2, 10, 5));
        panelFichaTecnica.setBorder(BorderFactory.createTitledBorder("Ficha Técnica"));
        panelFichaTecnica.setBackground(Color.WHITE);
        
        // Duración
        JLabel lblDuracionTitulo = new JLabel("Duración:");
        lblDuracionTitulo.setFont(new Font("Arial", Font.BOLD, 13));
        JLabel lblDuracionValor = new JLabel(pelicula.getDuracion() + " minutos");
        lblDuracionValor.setFont(new Font("Arial", Font.PLAIN, 13));
        
        // Género
        JLabel lblGeneroTitulo = new JLabel("Género:");
        lblGeneroTitulo.setFont(new Font("Arial", Font.BOLD, 13));
        JLabel lblGeneroValor = new JLabel(pelicula.getGenero().toString());
        lblGeneroValor.setFont(new Font("Arial", Font.PLAIN, 13));
        
        // Clasificación
        JLabel lblClasificacionTitulo = new JLabel("Clasificación:");
        lblClasificacionTitulo.setFont(new Font("Arial", Font.BOLD, 13));
        JLabel lblClasificacionValor = new JLabel(pelicula.getClasificacion().toString());
        lblClasificacionValor.setFont(new Font("Arial", Font.PLAIN, 13));
        
        // Director
        JLabel lblDirectorTitulo = new JLabel("Director:");
        lblDirectorTitulo.setFont(new Font("Arial", Font.BOLD, 13));
        JLabel lblDirectorValor = new JLabel(pelicula.getDirector());
        lblDirectorValor.setFont(new Font("Arial", Font.PLAIN, 13));
        
        panelFichaTecnica.add(lblDuracionTitulo);
        panelFichaTecnica.add(lblDuracionValor);
        panelFichaTecnica.add(lblGeneroTitulo);
        panelFichaTecnica.add(lblGeneroValor);
        panelFichaTecnica.add(lblClasificacionTitulo);
        panelFichaTecnica.add(lblClasificacionValor);
        panelFichaTecnica.add(lblDirectorTitulo);
        panelFichaTecnica.add(lblDirectorValor);
        
        // Panel contenedor para ficha técnica y valoración
        JPanel panelInferior = new JPanel(new GridLayout(2, 1, 5, 5));
        panelInferior.setBackground(Color.WHITE);
        panelInferior.add(panelFichaTecnica);
        
        // Panel de valoración
        JPanel panelValoracion = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER));
        panelValoracion.setBorder(BorderFactory.createTitledBorder("Valoración"));
        panelValoracion.setBackground(Color.WHITE);
        
        // Generar estrellas
        JLabel labelEstrellas = new JLabel();
        labelEstrellas.setText(generarEstrellas(pelicula.getValoracion()));
        labelEstrellas.setFont(new Font("Dialog", Font.BOLD, 40));
        labelEstrellas.setOpaque(true);
        
        // Mostrar valor numérico
        JLabel labelValoracion = new JLabel(String.format("%.1f", pelicula.getValoracion()));
        labelValoracion.setFont(new Font("Dialog", Font.BOLD, 40));
        labelValoracion.setOpaque(true);
        
        panelValoracion.add(labelEstrellas);
        panelValoracion.add(labelValoracion);
        
        panelInferior.add(panelValoracion);
        
        panelCentral.add(panelInferior, BorderLayout.SOUTH);
        
        rightPanel.add(panelCentral, BorderLayout.CENTER);

        add(rightPanel, BorderLayout.CENTER);
        setVisible(true);
    }
    
    // Metodo para generar estrellas
    private String generarEstrellas(double valoracion) {
        int estrellas = (int) Math.round(valoracion);
        StringBuilder string = new StringBuilder();
        
        for(int i = 0; i < 5; i++) {
            if(i < estrellas) {
                string.append("★");
            } else {
                string.append("☆");
            }
        }
        
        return string.toString();
    }
    
    
    // Método principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Actor actor5 = new Actor(5, "Emma Watson", LocalDate.of(1990, 4, 15), Pais.NAMIBIA);
            
            ArrayList<Horario> horarios3 = new ArrayList<>();
            horarios3.add(Horario.H0900);
            horarios3.add(Horario.H2130);
            
            Pelicula pelicula3 = new Pelicula(
                    3,
                    "Harry Potter y la Piedra Filosofal",
                    "Chris Columbus",
                    152,
                    Genero.FANTASIA,
                    List.of(actor5),
                    Clasificacion.TODAS,
                    "Un joven mago descubre su destino en Hogwarts.",
                    horarios3,
                    4.7
                );
            
            JFramePelicula frame = new JFramePelicula(pelicula3, listaPeliculas);
            frame.setVisible(true);
        });
    }
}
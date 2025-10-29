package proyectoCine.gui;

import java.awt.BorderLayout;
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

import proyectoCine.domain.Actor;
import proyectoCine.domain.Horario;
import proyectoCine.domain.Pelicula;
import proyectoCine.domain.Pelicula.Clasificacion;
import proyectoCine.domain.Reserva;
import proyectoCine.domain.Sala;
import proyectoCine.domain.Actor.Pais;

public class JFramePelicula extends JFrame {

    private static final long serialVersionUID = 1L;
    private Pelicula pelicula;
    private JLabel portadaLabel;

    public JFramePelicula(Pelicula pelicula) {
        this.pelicula = pelicula;

        // Configuración básica de la ventana
        setTitle(pelicula.getTitulo());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel izquierdo con imagen y título
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Carga la imagen original
        ImageIcon icon = new ImageIcon(
        	    getClass().getResource("/Paris Saint-Germain.png")
        	);


        // Escala la imagen
        Image img = icon.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);

        portadaLabel = new JLabel(scaledIcon);
        portadaLabel.setHorizontalAlignment(JLabel.CENTER);
        
        // Título de la película
        JLabel tituloLabel = new JLabel(pelicula.getTitulo(), JLabel.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        leftPanel.add(tituloLabel, BorderLayout.NORTH);
        leftPanel.add(portadaLabel, BorderLayout.CENTER);
        
        add(leftPanel, BorderLayout.WEST);

        // Panel derecho
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de botones (arriba)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 4, 5, 5));
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 0));

        String[] buttonsText = { "Actores", "Horarios", "Reserva" };

        for (String text : buttonsText) {
            JButton boton = new JButton(text);
            boton.setFont(new Font("Arial", Font.PLAIN, 14));
            buttonPanel.add(boton);

            if (text.equals("Reserva")) {
                boton.addActionListener(e -> {
                    // Obtener los horarios disponibles de la película
                    List<Horario> horariosDisponibles = pelicula.getHorarios_disponibles();
                    
                    // Verificar si hay horarios disponibles
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

                    // Renderer para mostrar texto centrado
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
							// Crear una nueva reserva
							Reserva reserva = new Reserva(pelicula, LocalDate.now(), horarioSeleccionado, new Sala(8902, 5, 5));
							JOptionPane.showMessageDialog(null, 
								"Reserva creada para " + pelicula.getTitulo() + " a las " + horarioSeleccionado.toString() + ", su asiento esta en la fila " + reserva.getSala().getFila() + " y columna " + reserva.getSala().getColumna(), 
								"Reserva Exitosa", 
								JOptionPane.INFORMATION_MESSAGE);
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
        
        // Panel de resumen (abajo)
        JPanel resumenPanel = new JPanel(new BorderLayout());
        resumenPanel.setBorder(BorderFactory.createTitledBorder("Resumen"));
        
        // Crear área de texto con el resumen de la película
        JTextArea resumenArea = new JTextArea(pelicula.getResumen());
        resumenArea.setEditable(false); // No permitir edición del texto
        resumenArea.setLineWrap(true); // Ajustar líneas automáticamente
        resumenArea.setWrapStyleWord(true); // Ajustar por palabras completas (no cortar palabras)
        resumenArea.setFont(new Font("Arial", Font.PLAIN, 13)); // Fuente Arial, normal, tamaño 13
        resumenArea.setBackground(getBackground()); // Mismo color de fondo que el panel
        resumenArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen interno de 10px
        
        JScrollPane scrollPane = new JScrollPane(resumenArea);
        scrollPane.setBorder(null);
        resumenPanel.add(scrollPane, BorderLayout.CENTER);

        rightPanel.add(resumenPanel, BorderLayout.CENTER);

        add(rightPanel, BorderLayout.CENTER);
    }
    
    

    // Método principal
 // Método principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Actor actor1 = new Actor(1, "Robert Downey Jr.", LocalDate.of(1965, 4, 4), Pais.ESTADOS_UNIDOS);
            Actor actor2 = new Actor(2, "Chris Evans", LocalDate.of(1981, 6, 13), Pais.PAISES_BAJOS);
            Actor actor3 = new Actor(3, "Scarlett Johansson", LocalDate.of(1984, 11, 22), Pais.FILIPINAS);
            
            ArrayList<Horario> horarios1 = new ArrayList<>();
            horarios1.add(Horario.H0900);
            horarios1.add(Horario.H1400);
            horarios1.add(Horario.H1900);
            
            Pelicula peliculaAvengers = new Pelicula(
                1,
                "Avengers: Endgame",
                "Anthony Russo",
                181,
                Pelicula.Genero.ACCION,
                List.of(actor1, actor2, actor3),
                Pelicula.Clasificacion.MAYORES_12,
                "Los Vengadores se enfrentan a Thanos en la batalla final.",
                horarios1
            );
            
            JFramePelicula frame = new JFramePelicula(peliculaAvengers);
            frame.setVisible(true);
        });
    }
}

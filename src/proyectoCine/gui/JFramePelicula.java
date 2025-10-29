package proyectoCine.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import proyectoCine.domain.Actor;
import proyectoCine.domain.Horario;
import proyectoCine.domain.Pelicula;
import proyectoCine.domain.Pelicula.Clasificacion;
import proyectoCine.domain.Reserva;
import proyectoCine.domain.Actor.Pais;

public class JFramePelicula extends JFrame {

    private static final long serialVersionUID = 1L;
    private Pelicula pelicula;
    private JLabel portadaLabel;

    public JFramePelicula(Pelicula pelicula) {
        this.pelicula = pelicula;

        // Configuración básica de la ventana
        setTitle("");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Carga la imagen original
        ImageIcon icon = new ImageIcon("C:\\Users\\jon.castano\\eclipse-workspace-segundo\\Proyecto_Cine\\resources\\Paris Saint-Germain.png");

        // Escala la imagen a un tamaño más grande (por ejemplo 200x200)
        Image img = icon.getImage().getScaledInstance(300, 500, Image.SCALE_SMOOTH);

        // Crea un nuevo ImageIcon con la imagen escalada
        ImageIcon scaledIcon = new ImageIcon(img);

        portadaLabel = new JLabel(pelicula.getTitulo(), scaledIcon, JLabel.CENTER);
        portadaLabel.setHorizontalTextPosition(JLabel.CENTER);
        portadaLabel.setVerticalTextPosition(JLabel.BOTTOM);
        add(portadaLabel, BorderLayout.WEST);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 5, 5));

        String[] buttonsText = { "Actores", "Resumen", "Horarios", "Reserva" };

        for (String text : buttonsText) {
            JButton boton = new JButton(text);
            buttonPanel.add(boton);

            if (text.equals("Reserva")) {
                boton.addActionListener(e -> {
                	 // Obtener los horarios disponibles de la película
                    List<Horario> horariosDisponibles = pelicula.getHorarios_disponibles(); // o el método que tengas
                    
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
                    JOptionPane.showMessageDialog(null, jcomoHorarios, "Selecciona un horario", JOptionPane.PLAIN_MESSAGE);
                });
            }else if(text.equals("Resumen")) {
				boton.addActionListener(e -> {
					JOptionPane.showMessageDialog(null, pelicula.getResumen(), "Resumen de " + pelicula.getTitulo(), JOptionPane.INFORMATION_MESSAGE);
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

        add(buttonPanel, BorderLayout.CENTER);
    }

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
            JFramePelicula pelicula = new JFramePelicula(
                    new Pelicula(
                            1,
                            "Avengers: Endgame",
                            "Anthony Russo",
                            181,
                            Pelicula.Genero.ACCION,
                            List.of(actor1, actor2, actor3),
                            Pelicula.Clasificacion.MAYORES_12,
                            "Los Vengadores se enfrentan a Thanos en la batalla final.",
                            horarios1
                        ));
            pelicula.setVisible(true);
        });
    }
}

package proyectoCine.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import proyectoCine.domain.Pelicula;
import proyectoCine.domain.Pelicula.Clasificacion;
import proyectoCine.domain.Reserva;

public class JFramePelicula extends JFrame {

    private static final long serialVersionUID = 1L;
    private Pelicula pelicula;
    private JLabel portadaLabel;

    public JFramePelicula(Pelicula pelicula) {
        this.pelicula = pelicula;

        // Configuración básica de la ventana
        setTitle("");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Carga la imagen original
        ImageIcon icon = new ImageIcon("C:\\Users\\jon.castano\\eclipse-workspace-segundo\\Proyecto_Cine\\resources\\Paris Saint-Germain.png");

        // Escala la imagen a un tamaño más grande (por ejemplo 200x200)
        Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);

        // Crea un nuevo ImageIcon con la imagen escalada
        ImageIcon scaledIcon = new ImageIcon(img);

        portadaLabel = new JLabel(pelicula.getTitulo(), scaledIcon, JLabel.CENTER);
        portadaLabel.setHorizontalTextPosition(JLabel.CENTER);
        portadaLabel.setVerticalTextPosition(JLabel.BOTTOM);
        add(portadaLabel, BorderLayout.NORTH);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4, 5, 5));

        String[] buttonsText = { "Actores", "Resumen", "Horarios", "Reserva" };

        for (String text : buttonsText) {
            JButton boton = new JButton(text);
            buttonPanel.add(boton);

            if (text.equals("Reserva")) {
                boton.addActionListener(e -> {
                    // JComboBox para seleccionar horario
                    JComboBox<Reserva.Horarios> jcomoHorarios = new JComboBox<>(Reserva.Horarios.values());

                    // Renderer para mostrar texto centrado
                    jcomoHorarios.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
                        JLabel label = new JLabel();
                        if (value != null) {
                            Reserva.Horarios horario = (Reserva.Horarios) value;
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
            }
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    // Método principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFramePelicula pelicula = new JFramePelicula(
                    new Pelicula(1, "Titulo Ejemplo", "Director Ejemplo", 120, Pelicula.Genero.ACCION, List.of(),Clasificacion.TODAS));
            pelicula.setVisible(true);
        });
    }
}

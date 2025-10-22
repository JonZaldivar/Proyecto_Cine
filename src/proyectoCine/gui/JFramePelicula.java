package proyectoCine.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import proyectoCine.domain.Pelicula;

public class JFramePelicula extends JFrame{

	private static final long serialVersionUID = 1L;
	private Pelicula pelicula;
	private JLabel portadaLabel;
	
	
	
	public JFramePelicula(Pelicula pelicula) {
		// Configuración básica de la ventana
    	setTitle(pelicula.getTitulo());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
     // Crear el display de la calculadora
        portadaLabel = new JLabel("Batman", new ImageIcon("C:\\Users\\jon.castano\\eclipse-workspace-segundo\\Proyecto_Cine\\resources\\Paris Saint-Germain.png"), JLabel.CENTER);
        portadaLabel.setHorizontalTextPosition(JLabel.CENTER);
        portadaLabel.setVerticalTextPosition(JLabel.BOTTOM);
        add(portadaLabel, BorderLayout.NORTH);
        // Panel de botones numéricos y de operaciones
        JPanel buttonPanel = new JPanel();
        // Definir una cuadrícula de 4x4 con 5 píxeles de separación
        buttonPanel.setLayout(new GridLayout(1, 4, 5, 5));

        // Crear los textos mostrados EN los botones
        String[] buttonsText = {
                "Actores", "Resumen", "Horarios", "Reserva",
        };
        
        // Crear los botones y añadirlos al panel        
        for (String text : buttonsText) {
        	JButton boton = new JButton(text);
            buttonPanel.add(boton);
        }
        // Añadir el panel de botones al centro de la ventana
        add(buttonPanel, BorderLayout.CENTER);
    }
	
	// Método principal
    public static void main(String[] args) {
        // Crear la ventana en el hilo de eventos de Swing
    	SwingUtilities.invokeLater(() -> {
        	// Crear una instancia de la calculadora y hacerla visible
    		JFramePelicula calculadora = new JFramePelicula(new Pelicula(1, "Titulo Ejemplo", "Director Ejemplo", 120, Pelicula.Genero.ACCION, List.of()));
            calculadora.setVisible(true);
        });
    }
	
}

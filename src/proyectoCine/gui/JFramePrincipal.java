package proyectoCine.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import proyectoCine.domain.Pelicula;

public class JFramePrincipal extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private List<Pelicula> peliculas;
	
	
	public JFramePrincipal(List<Pelicula> peliculas) {
		this.peliculas = peliculas;
		
		
		this.getContentPane().setLayout(new BorderLayout());
		
		JPanel panelCabecera = new JPanel();
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		
		ImageIcon logo = new ImageIcon("C:\\Users\\alejandro.garcia.p\\git\\Proyecto_Cine\\resources\\Paris Saint-Germain.png");
		panelCabecera.add(new JLabel(logo));
		panelCabecera.add(new JLabel(""));
		
		
		this.getContentPane().add(panelCabecera,BorderLayout.NORTH);
		
		
		this.setTitle("Ventana principal de Equipos");		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);	
		
	}
	
}

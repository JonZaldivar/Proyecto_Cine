package proyectoCine.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import proyectoCine.domain.Pelicula;

public class JFramePrincipal extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private List<Pelicula> peliculas;
	private JTable tablaPeliculas;
	private DefaultTableModel modeloDatosPeliculas;
	private JScrollPane scrollPeliculas;
	
	
	public JFramePrincipal(List<Pelicula> peliculas) {
		this.peliculas = peliculas;
		
		this.initTablaPelis();
		this.loadPelis();
		
		this.scrollPeliculas = new JScrollPane(this.tablaPeliculas);
		this.scrollPeliculas.setBorder(new TitledBorder("Películas"));
		this.tablaPeliculas.setFillsViewportHeight(true);
		
		this.getContentPane().setLayout(new BorderLayout());
		
		JPanel panelTablaPeliculas = new JPanel();
		panelTablaPeliculas.add(this.scrollPeliculas);
		
		
		this.getContentPane().add(panelTablaPeliculas,BorderLayout.CENTER);
		
		
		
		
		JPanel panelCabecera = new JPanel();
		panelCabecera.setBackground(Color.BLUE);
		
		ImageIcon logo = new ImageIcon("C:\\Users\\alejandro.garcia.p\\git\\Proyecto_Cine\\resources\\Paris Saint-Germain.png");
		panelCabecera.add(new JLabel(logo));
		
		
		
		this.getContentPane().add(panelCabecera,BorderLayout.NORTH);
		
		
		this.setTitle("Ventana principal de Cartelera");		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		
		
	}
	
	private void initTablaPelis() {
		
		Vector<String> cabeceraPelis = new Vector<String>(Arrays.asList("ID","TITULO","DIRECTOR","GENERO"));
		
		this.modeloDatosPeliculas = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceraPelis) {
			public boolean isCellEditable(int row , int column) {
				return false;
			}
		};
		
		this.tablaPeliculas = new JTable(this.modeloDatosPeliculas);
		this.tablaPeliculas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		
	}
	
	private void loadPelis() {
		this.modeloDatosPeliculas.setRowCount(0);
		
		for(Pelicula pelicula : this.peliculas) {
			this.modeloDatosPeliculas.addRow(new Object[] {
					pelicula.getId(),pelicula.getTitulo(),pelicula.getDirector()
					,pelicula.getGenero()
			});
		}
		
	}
	
}

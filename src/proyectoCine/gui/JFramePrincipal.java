package proyectoCine.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import proyectoCine.domain.Pelicula;
import proyectoCine.domain.Pelicula.Genero;

public class JFramePrincipal extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private List<Pelicula> peliculas;
	private JTable tablaPeliculas;
	private DefaultTableModel modeloDatosPeliculas;
	private JScrollPane scrollPeliculas;
	private String filtroActual = "";
	private JComboBox<Genero> comboGenero;
	
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
		
		JPanel panelFiltro = new JPanel();
		JTextField filtro = new JTextField();
		filtro.setColumns(20);
		panelFiltro.add(new JLabel("FILTRAR POR TÍTULO: "));
		panelFiltro.add(filtro);
		panelFiltro.setBackground(Color.green);
		
		filtro.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
		    @Override
		    public void insertUpdate(javax.swing.event.DocumentEvent e) { actualizar(); }
		    @Override
		    public void removeUpdate(javax.swing.event.DocumentEvent e) { actualizar(); }
		    @Override
		    public void changedUpdate(javax.swing.event.DocumentEvent e) { actualizar(); }

		    private void actualizar() {
		        filtroActual = filtro.getText();
		        actualizarFiltro(); // filtra la tabla
		        tablaPeliculas.repaint();       // fuerza al JTable a repintar con el nuevo renderer
		    }
		});
		
		
		
		this.getContentPane().add(panelFiltro,BorderLayout.SOUTH);
		
		
		JPanel panelEste = new JPanel(new GridLayout(3,1));
		JPanel panelFiltroGenero = new JPanel(new GridLayout(2,1));
		JLabel BuscaPorGenero = new JLabel("POR GÉNERO");
		BuscaPorGenero.setHorizontalAlignment(SwingConstants.CENTER);
		panelFiltroGenero.add(BuscaPorGenero);
		comboGenero = new JComboBox<Genero>(Genero.values());
		
		panelFiltroGenero.add(comboGenero);
		
		comboGenero.addActionListener(e -> {
		    
		    actualizarFiltro();
		});

		
		panelEste.add(panelFiltroGenero);
		panelEste.add(new JLabel("FOTO"));
		panelEste.add(new JLabel("FOTO"));
		
		
		this.getContentPane().add(panelEste,BorderLayout.EAST);
		
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
	
	private void actualizarFiltro() {
		Genero g = (Genero)comboGenero.getSelectedItem();
		String texto = filtroActual.toLowerCase();
		
		this.modeloDatosPeliculas.setRowCount(0);
		
		for(Pelicula p : this.peliculas) {
			
			boolean cumpleGenero = g.equals(Genero.CUALQUIERA) || g.equals(p.getGenero());
			boolean cumpleTitulo = p.getTitulo().toLowerCase().contains(texto);
			
			if(cumpleGenero && cumpleTitulo) {
				this.modeloDatosPeliculas.addRow(new Object[] {
						p.getId(),p.getTitulo(),p.getDirector(),p.getGenero()
				});
			}
		}
		
		
	}
	
}

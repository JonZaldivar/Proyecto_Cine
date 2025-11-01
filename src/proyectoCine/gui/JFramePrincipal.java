package proyectoCine.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import proyectoCine.domain.Actor;
import proyectoCine.domain.Horario;
import proyectoCine.domain.Pelicula;
import proyectoCine.domain.Pelicula.Clasificacion;
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
		this.tablaPeliculas.setFillsViewportHeight(true);
		
		this.tablaPeliculas.setRowHeight(150); // Ajusta según el tamaño del panel
		this.tablaPeliculas.getColumnModel().getColumn(0).setPreferredWidth(200);

		
		this.getContentPane().setLayout(new BorderLayout());
		
		JPanel panelTablaPeliculas = new JPanel();
		panelTablaPeliculas.add(this.scrollPeliculas);

		
		
		
		this.getContentPane().add(panelTablaPeliculas,BorderLayout.CENTER);
		
		TableCellRenderer renderer = new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				
				if (value instanceof JPanel) {
				    JPanel panel = (JPanel) value;

				    if (isSelected) {
				        panel.setBackground(Color.LIGHT_GRAY);
				        for (Component comp : panel.getComponents()) {
				            comp.setBackground(Color.LIGHT_GRAY);
				            
				        }
				    } else {
				    	panel.setBackground(Color.white);
				    	for (Component comp : panel.getComponents()) {
				            comp.setBackground(Color.white);
				    }}

				    return panel;
				}
				
				JLabel result = new JLabel(value.toString());
				
				
				if(isSelected) {
					result.setBackground(Color.LIGHT_GRAY);
				}
				
				
				
				if(column == 2) {
					result.setText(""); // limpiar texto
				    
				    // Intentar cargar JPG
					java.net.URL url = getClass().getResource("/" + value.toString() + ".jpg");
				    if(url == null) { // si no existe JPG, intentar PNG
				    	url = getClass().getResource("/" + value.toString() + ".png");
				    }
				    
				    if(url != null) {
				        result.setIcon(new ImageIcon(url));
				    } else {
				        result.setText(value.toString());
				    }
				}
				
				result.setHorizontalAlignment(SwingConstants.CENTER);
				result.setOpaque(true);
				
				
				
				return result;
			}
			
		};
		
		this.tablaPeliculas.setDefaultRenderer(Object.class, renderer);
		
		
		
		JPanel panelCabecera = new JPanel();
		panelCabecera.setBackground(new Color(217, 234, 246));
		
		java.net.URL logoUrl = getClass().getResource("/DeustoCine.png");
		ImageIcon logo = new ImageIcon(logoUrl);
		panelCabecera.add(new JLabel(logo));
		
		
		
		this.getContentPane().add(panelCabecera,BorderLayout.NORTH);
		
		JPanel panelFiltro = new JPanel(new GridLayout(1,2));
		
		JPanel panelFiltroTitulo = new JPanel(new GridLayout(2,1));
		JTextField filtro = new JTextField();
		filtro.setColumns(20);
		JLabel labelFiltroTitulo = new JLabel("Filtrar por título: ");
		labelFiltroTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		panelFiltroTitulo.add(labelFiltroTitulo);
		panelFiltroTitulo.add(filtro);
		panelFiltroTitulo.setBackground(new Color(217, 234, 246));
		
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
		
		JPanel panelFiltroGenero = new JPanel(new GridLayout(2,1));
		JLabel BuscaPorGenero = new JLabel("Filtrar por género: ");
		BuscaPorGenero.setHorizontalAlignment(SwingConstants.CENTER);
		panelFiltroGenero.add(BuscaPorGenero);
		comboGenero = new JComboBox<Genero>(Genero.values());
		panelFiltroGenero.setBackground(new Color(217, 234, 246));
		
		panelFiltroGenero.add(comboGenero);
		
		comboGenero.addActionListener(e -> {
		    
		    actualizarFiltro();
		});
		
		panelFiltro.add(panelFiltroTitulo);
		panelFiltro.add(panelFiltroGenero);
		
		
		
		this.getContentPane().add(panelFiltro,BorderLayout.SOUTH); //Añadimos panel filtro
		
		KeyListener listener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_C && e.isControlDown()) {

					boolean tieneAcceso = controlAcceso();
					
					if(tieneAcceso) {
						anyadirPeli();
					}
					
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		this.tablaPeliculas.addKeyListener(listener);
		filtro.addKeyListener(listener);
		
		this.tablaPeliculas.setToolTipText("");
		this.tablaPeliculas.addMouseMotionListener((new MouseMotionAdapter() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				int filaSelec = tablaPeliculas.rowAtPoint(e.getPoint());
				
				if(filaSelec!=-1) {
					JPanel panelCelda = (JPanel)modeloDatosPeliculas.getValueAt(filaSelec, 0);					
					JLabel labelTitulo = (JLabel) panelCelda.getComponent(0);
					String tituloPeli = labelTitulo.getName();
					
					for(Pelicula p : peliculas) {
						if(p.getTitulo().equals(tituloPeli)) {
							tablaPeliculas.setToolTipText(p.getResumen());
							break;
						}
					}
				}
			}
			
		}));
		
		JPanel panelEste = new JPanel();
		panelEste.setBackground(new Color(217, 234, 246));
		panelEste.setPreferredSize(new Dimension(165, 0));
		this.getContentPane().add(panelEste, BorderLayout.EAST);

		JPanel panelOeste = new JPanel();
		panelOeste.setBackground(new Color(217, 234, 246));
		panelOeste.setPreferredSize(new Dimension(165, 0)); 
		this.getContentPane().add(panelOeste, BorderLayout.WEST);
		
		
			
		
		this.setTitle("Ventana principal de Cartelera");		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		
		
		
	}
	
	private void initTablaPelis() {
		
		Vector<String> cabeceraPelis = new Vector<String>(Arrays.asList("TITULO","GENERO","CLASIFICACIÓN"));
		
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
			JPanel panelColumna = new JPanel(new BorderLayout());
			JPanel panelValBoton = new JPanel(new FlowLayout());
			JPanel panelTitulo = new JPanel();
			JLabel labelTitulo = new JLabel();
			
			java.net.URL url = getClass().getResource("/" + pelicula.getTitulo().toString() + ".jpg");
		    if(url == null) { // si no existe JPG, intentar PNG
		    	url = getClass().getResource("/" + pelicula.getTitulo().toString() + ".png");
		    }
		    
		    if(url != null) {
		        labelTitulo.setIcon(new ImageIcon(url));
		    } else {
		        labelTitulo.setText(pelicula.getTitulo().toString());
		    }
		    
		    labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		    labelTitulo.setVerticalAlignment(SwingConstants.CENTER);
		    labelTitulo.setName(pelicula.getTitulo());
		    panelTitulo.add(labelTitulo);
		    panelColumna.add(labelTitulo,BorderLayout.CENTER);
			
			
		    
			
			JLabel labelEstrellas = new JLabel();			
			labelEstrellas.setText(generarEstrellas(pelicula.getValoracion()));	
			labelEstrellas.setBackground(new Color(217, 234, 246));
			labelEstrellas.setFont(new Font("Dialog", Font.BOLD, 12));
			labelEstrellas.setOpaque(true);
		    		    
			panelValBoton.add(labelEstrellas);
			
			JButton botonValoracion = new JButton(
		    	    String.format("%.1f", pelicula.getValoracion())
		    	);

		    botonValoracion.setFont(botonValoracion.getFont().deriveFont(Font.BOLD));
		    botonValoracion.setBackground(new Color(217, 234, 246));
		    panelValBoton.add(botonValoracion);
			
			panelColumna.add(panelValBoton,BorderLayout.SOUTH);
			
			this.modeloDatosPeliculas.addRow(new Object[] {
					panelColumna,pelicula.getGenero(),pelicula.getClasificacion()
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
						p.getTitulo(),p.getGenero(),p.getClasificacion()
				});
			}
		}
		
		
	}
	
	private void anyadirPeli() {
		JComponent[] componentesP = new JComponent[8];
		componentesP[0] = new JLabel("Título: ");
		JTextField txtTitulo = new JTextField(50);
		componentesP[1] = txtTitulo;
		componentesP[2] = new JLabel("Director: ");
		JTextField txtDirector = new JTextField(50);
		componentesP[3] = txtDirector;
		componentesP[4] = new JLabel("Duración: ");
		JTextField txtDuracion = new JTextField(50);
		componentesP[5] = txtDuracion;
		JComboBox<Clasificacion> boxClasifi = new JComboBox<Clasificacion>(Clasificacion.values());
		boxClasifi.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			JLabel result = new JLabel();
			result.setText("");
			
			result.setHorizontalAlignment(SwingConstants.CENTER);
			result.setToolTipText("Seleccione editorial");
			
			result.setText(""); // limpiar texto

			java.net.URL url = getClass().getResource("/" + value.toString() + ".jpg");
			if(url == null) {
				url = getClass().getResource("/" + value.toString() + ".png");
			}

			if(url != null) {
			    result.setIcon(new ImageIcon(url));
			}
			return result;
			
			
		});
		
		componentesP[6] = boxClasifi;
		JComboBox<Genero> boxGenero = new JComboBox<Genero>(Genero.values());
		componentesP[7] = boxGenero;
		
		int resultadoP = JOptionPane.showConfirmDialog(null,componentesP
				, "Creación de nueva película",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
		
		if (resultadoP == JOptionPane.OK_OPTION) {
			Pelicula nueva = new Pelicula(
				    modeloDatosPeliculas.getRowCount() + 1,
				    txtTitulo.getText(),
				    txtDirector.getText(),
				    Integer.parseInt(txtDuracion.getText()),
				    (Genero) boxGenero.getSelectedItem(),
				    new ArrayList<Actor>(), // lista vacía de actores
				    (Clasificacion) boxClasifi.getSelectedItem(),
				    "Sinopsis no disponible.", // resumen por defecto
				    new ArrayList<Horario>(), // horarios por defecto
				    0.0 // valoración
				);
			this.peliculas.add(nueva);

			JPanel panelColumna = new JPanel(new BorderLayout());
			JPanel panelValBoton = new JPanel(new FlowLayout());
			JPanel panelTitulo = new JPanel();
			JLabel labelTitulo = new JLabel();
			
			java.net.URL url = getClass().getResource("/" + nueva.getTitulo().toString() + ".jpg");
		    if(url == null) { // si no existe JPG, intentar PNG
		    	url = getClass().getResource("/" + nueva.getTitulo().toString() + ".png");
		    }
		    
		    if(url != null) {
		        labelTitulo.setIcon(new ImageIcon(url));
		    } else {
		        labelTitulo.setText(nueva.getTitulo().toString());
		    }
		    
		    labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		    labelTitulo.setVerticalAlignment(SwingConstants.CENTER);
		    labelTitulo.setName(nueva.getTitulo());
		    panelTitulo.add(labelTitulo);
		    panelColumna.add(labelTitulo,BorderLayout.CENTER);
			
			
			
			JLabel labelEstrellas = new JLabel();			
			labelEstrellas.setText("Sin valorar");	
			labelEstrellas.setBackground(new Color(217, 234, 246));
			labelEstrellas.setFont(new Font("Dialog", Font.BOLD, 12));
			labelEstrellas.setOpaque(true);
		    		    
			panelValBoton.add(labelEstrellas);
			
			JButton botonValoracion = new JButton(
		    	    String.format("%.1f", nueva.getValoracion())
		    	);

		    botonValoracion.setFont(botonValoracion.getFont().deriveFont(Font.BOLD));
		    botonValoracion.setBackground(new Color(217, 234, 246));
		    panelValBoton.add(botonValoracion);
			
			panelColumna.add(panelValBoton,BorderLayout.SOUTH);
			
			this.modeloDatosPeliculas.addRow(new Object[] {
					panelColumna,nueva.getGenero(),nueva.getClasificacion()
			});
			
			this.tablaPeliculas.repaint();
		}
	}
	
	private boolean controlAcceso() {
		JComponent[] componentes = new JComponent[4];
		componentes[0] = new JLabel("Usuario: ");
		JTextField txtUsuario = new JTextField(50);
		componentes[1] = txtUsuario;
		componentes[2] = new JLabel("Contraseña: ");
		JTextField txtContrasenya = new JTextField(50);
		componentes[3] = txtContrasenya;
		
	int resultado = JOptionPane.showConfirmDialog(null,componentes,"Confirmación acceso",
			JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
	
	if (resultado == JOptionPane.OK_OPTION) {
		
		if(txtUsuario.getText().equals("Empresa") && txtContrasenya.getText().equals("123")) {
			return true;
			} else {
				return false;
			} 
		} else {
			return false;
	}
	
	}
	
	private String generarEstrellas(double valoracion) {
		
		int estrellas = (int) Math.round(valoracion);
		StringBuilder string = new StringBuilder();
		
		for(int i = 0 ; i <5;i++) {
			if(i<estrellas) {
				string.append("★");
			} else {
				 string.append("☆");
			}
		}
		
		return string.toString();
	}
}

package proyectoCine.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

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
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import proyectoCine.domain.Actor;
import proyectoCine.domain.Descuento;
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

        this.tablaPeliculas.setRowHeight(150);
        this.tablaPeliculas.getColumnModel().getColumn(0).setPreferredWidth(200);

        this.getContentPane().setLayout(new BorderLayout());

        JPanel panelTablaPeliculas = new JPanel();
        panelTablaPeliculas.setOpaque(true);
        panelTablaPeliculas.setBackground((new Color(217, 234, 246)));
        panelTablaPeliculas.add(this.scrollPeliculas);
        this.getContentPane().add(panelTablaPeliculas, BorderLayout.CENTER);

        TableCellRenderer renderer = new TableCellRenderer() {

            private JLabel findFirstLabel(Component c) {
                if (c instanceof JLabel) return (JLabel) c;
                if (c instanceof Container) {
                    Component[] childs = ((Container) c).getComponents();
                    for (Component ch : childs) {
                        JLabel found = findFirstLabel(ch);
                        if (found != null) return found;
                    }
                }
                return null;
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {

                if (value instanceof JPanel) {
                    JPanel panel = (JPanel) value;
                    panel.setBackground(new Color(217, 234, 246));

                    if (isSelected) {
                        panel.setBackground(Color.LIGHT_GRAY);
                    } else {
                        panel.setBackground(Color.white);
                    }
                    
                    // Chat GPT
                    JLabel labelImagen = findFirstLabel(panel);
                    labelImagen.setBackground(new Color(217, 234, 246));
                    if (labelImagen != null) {
                        
                        ImageIcon currentIcon = (ImageIcon) labelImagen.getIcon();

                        
                        if (currentIcon == null && labelImagen.getText() != null && !labelImagen.getText().isEmpty()) {
                           
                        } else if (currentIcon != null) {
                            Image img = currentIcon.getImage();

                            
                            int cellWidth = table.getColumnModel().getColumn(column).getWidth();
                            int cellHeight = table.getRowHeight(row);

                            
                            int reservedForBottom = 30; 
                            int maxHeight = Math.max(10, cellHeight - reservedForBottom);

                           
                            int imgW = currentIcon.getIconWidth();
                            int imgH = currentIcon.getIconHeight();
                            if (imgW <= 0 || imgH <= 0) {
                               
                                imgW = img.getWidth(null);
                                imgH = img.getHeight(null);
                            }
                            if (imgW <= 0 || imgH <= 0) {
                                
                            } else {
                                
                                double scale = Math.min((double) cellWidth / imgW, (double) maxHeight / imgH);
                                if (scale <= 0) scale = 1.0;
                                int newW = Math.max(1, (int) (imgW * scale));
                                int newH = Math.max(1, (int) (imgH * scale));

                                Image scaled = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
                                labelImagen.setIcon(new ImageIcon(scaled));
                                labelImagen.setText(""); 
                                labelImagen.setHorizontalAlignment(SwingConstants.CENTER);
                                labelImagen.setVerticalAlignment(SwingConstants.CENTER);
                            }
                            
                            //ChatGPT
                        }
                    }

                    for (Component comp : panel.getComponents()) {
                        if (isSelected) comp.setBackground(Color.LIGHT_GRAY);
                        else comp.setBackground(new Color(217, 234, 246));
                    }

                    return panel;
                }
                
                JLabel result = new JLabel();
                result.setBackground(new Color(217, 234, 246));
                result.setOpaque(true);
                result.setHorizontalAlignment(SwingConstants.CENTER);

                if (column == 2) {
                    result.setText("");

                    java.net.URL url = getClass().getResource("/" + value.toString() + ".jpg");
                    if (url == null) url = getClass().getResource("/" + value.toString() + ".png");

                    if (url != null) {
                        ImageIcon icon = new ImageIcon(url);
                        Image img = icon.getImage();

                        result.setIcon(new ImageIcon(img));
                    } else {
                        result.setText(value.toString());
                    }
                } else {
                    result.setText(value != null ? value.toString() : "");
                }

                if (isSelected) {
                    result.setBackground(Color.LIGHT_GRAY);
                } else {
                    result.setBackground(new Color(217, 234, 246));
                }

                return result;
            }
        };
        
        TableCellRenderer rendererHeader = new TableCellRenderer(){

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				
				JLabel result = new JLabel(value.toString());
				result.setOpaque(true);
				result.setHorizontalAlignment(SwingConstants.CENTER);
				result.setBackground(new Color(70, 130, 180));
				
				return result;
			}
        	
        };
        
        
        this.tablaPeliculas.getTableHeader().setDefaultRenderer(rendererHeader);
        this.tablaPeliculas.setDefaultRenderer(Object.class, renderer);
        this.tablaPeliculas.setShowGrid(false);
        JPanel panelCabecera = new JPanel();
        panelCabecera.setBackground(new Color(217, 234, 246));

        java.net.URL logoUrl = getClass().getResource("/DeustoCine.png");
        ImageIcon icon = new ImageIcon(logoUrl);
        Image img = icon.getImage();
        ImageIcon logo = new ImageIcon(img.getScaledInstance(100, 50, Image.SCALE_SMOOTH));
        panelCabecera.add(new JLabel(logo));

        this.getContentPane().add(panelCabecera, BorderLayout.NORTH);

        JPanel panelSur = new JPanel(new GridLayout(1, 2,10,10));

        JPanel panelFiltroTitulo = new JPanel(new GridLayout(2, 1));
        JTextField filtro = new JTextField();
        filtro.setColumns(15);
        JLabel labelFiltroTitulo = new JLabel("Filtrar por título: ");
        labelFiltroTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelFiltroTitulo.add(labelFiltroTitulo);
        panelFiltroTitulo.add(filtro);
        panelFiltroTitulo.setBackground(new Color(217, 234, 246));

        filtro.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizar();
            }

            private void actualizar() {
                filtroActual = filtro.getText();
                actualizarFiltro();
                tablaPeliculas.repaint();
            }

        });

        panelSur.add(panelFiltroTitulo);
        
        JButton botonDescuento = new JButton("¡CONSIGUE AQUÍ TU DESCUENTO!");
        botonDescuento.setFont(new Font("Arial",Font.BOLD,13));
        ArrayList<Descuento> descuentos = generarDescuentos();
        botonDescuento.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
                
                JFrameDescuento ventanaDescuento = new JFrameDescuento(descuentos);
                ventanaDescuento.setVisible(true);
            }
        });
        
        panelSur.add(botonDescuento);
        HiloFondoDescuento hiloBoton = new HiloFondoDescuento(botonDescuento,new Color(0, 102, 204) ,new Color(102, 178, 255),500);
        hiloBoton.start();
        this.getContentPane().add(panelSur, BorderLayout.SOUTH);

        KeyListener listener = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_C && e.isControlDown()) {

                    boolean tieneAcceso = controlAcceso();

                    if (tieneAcceso) {
                        anyadirPeli();
                    }

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        };

        this.tablaPeliculas.addKeyListener(listener);
        filtro.addKeyListener(listener);

        this.tablaPeliculas.setToolTipText("");
        this.tablaPeliculas.addMouseMotionListener((new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                int filaSelec = tablaPeliculas.rowAtPoint(e.getPoint());

                if (filaSelec != -1) {
                    JPanel panelCelda = (JPanel) modeloDatosPeliculas.getValueAt(filaSelec, 0);
                    JPanel panelTitulo = (JPanel) panelCelda.getComponent(0);
                    JLabel labelTitulo = (JLabel) panelTitulo.getComponent(0);
                    String tituloPeli = labelTitulo.getName();

                    for (Pelicula p : peliculas) {
                        if (p.getTitulo().equals(tituloPeli)) {
                            tablaPeliculas.setToolTipText(p.getResumen());
                            break;
                        }
                    }
                }
            }

        }));
        

        this.tablaPeliculas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    int row = tablaPeliculas.rowAtPoint(e.getPoint());
                    Pelicula p = peliculas.get(row);
                    
                    SwingUtilities.invokeLater(() -> new JFramePelicula(p, peliculas));
                }
            }
        });

        JPanel panelEste = new JPanel(new FlowLayout());
        panelEste.setBackground(new Color(217, 234, 246));
        panelEste.setPreferredSize(new Dimension(165, 0));

        JPanel panelFiltroGenero = new JPanel(new GridLayout(2, 1));
        JLabel BuscaPorGenero = new JLabel("Filtrar por género: ");
        BuscaPorGenero.setHorizontalAlignment(SwingConstants.CENTER);
        panelFiltroGenero.add(BuscaPorGenero);
        comboGenero = new JComboBox<Genero>(Genero.values());
        panelFiltroGenero.setBackground(new Color(217, 234, 246));
        panelFiltroGenero.add(comboGenero);

        JPanel panelBotonFiltro = new JPanel(new FlowLayout());
        panelBotonFiltro.setBackground(new Color(217, 234, 246));
        JButton botonFiltro = new JButton("VER CARTELERA");
        botonFiltro.setBackground(Color.blue);

        botonFiltro.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarFiltro();
            }

        });

        panelBotonFiltro.add(botonFiltro);

        panelEste.add(panelFiltroGenero);
        panelEste.add(panelBotonFiltro);

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

        Vector<String> cabeceraPelis = new Vector<String>(Arrays.asList("TITULO", "GENERO", "CLASIFICACIÓN"));

        this.modeloDatosPeliculas = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceraPelis) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.tablaPeliculas = new JTable(this.modeloDatosPeliculas);
        this.tablaPeliculas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    private void loadPelis() {
        this.modeloDatosPeliculas.setRowCount(0);

        for (Pelicula pelicula : this.peliculas) {
            
        	JPanel panelColumna = anyadirPanelPortada(pelicula);

            this.modeloDatosPeliculas.addRow(new Object[]{
                    panelColumna, pelicula.getGenero(), pelicula.getClasificacion()
            });

        }

    }

    private void actualizarFiltro() {
        Genero g = (Genero) comboGenero.getSelectedItem();
        String titulo = filtroActual.toLowerCase();

        this.modeloDatosPeliculas.setRowCount(0);

        for (Pelicula p : this.peliculas) {

            boolean cumpleGenero = g.equals(Genero.CUALQUIERA) || g.equals(p.getGenero());
            boolean cumpleTitulo = p.getTitulo().toLowerCase().contains(titulo);

            if (cumpleGenero && cumpleTitulo) {
                JPanel panelColumna = anyadirPanelPortada(p);

                this.modeloDatosPeliculas.addRow(new Object[]{
                        panelColumna, p.getGenero(), p.getClasificacion()
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
            result.setToolTipText("Seleccione clasificación");

            java.net.URL url = getClass().getResource("/" + value.toString() + ".jpg");
            if (url == null) {
                url = getClass().getResource("/" + value.toString() + ".png");
            }

            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                result.setIcon(icon);
            }
            return result;

        });

        componentesP[6] = boxClasifi;
        JComboBox<Genero> boxGenero = new JComboBox<Genero>(Genero.values());
        componentesP[7] = boxGenero;

        int resultadoP = JOptionPane.showConfirmDialog(null, componentesP
                , "Creación de nueva película", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (resultadoP == JOptionPane.OK_OPTION) {
            Pelicula nueva = new Pelicula(
                    modeloDatosPeliculas.getRowCount() + 1,
                    txtTitulo.getText(),
                    txtDirector.getText(),
                    Integer.parseInt(txtDuracion.getText()),
                    (Genero) boxGenero.getSelectedItem(),
                    new ArrayList<Actor>(),
                    (Clasificacion) boxClasifi.getSelectedItem(),
                    "Sinopsis no disponible.",
                    new ArrayList<Horario>(),
                    0.0
            );
            this.peliculas.add(nueva);

            JPanel panelColumna = anyadirPanelPortada(nueva);

            this.modeloDatosPeliculas.addRow(new Object[]{
                    panelColumna, nueva.getGenero(), nueva.getClasificacion()
            });

            this.tablaPeliculas.repaint();
        }
    }
    
    private ArrayList<Descuento> generarDescuentos(){
    	ArrayList<Descuento> listaDescuentos = new ArrayList<>();

        
        for (int i = 0; i < 60; i++) listaDescuentos.add(Descuento.SIN_DESCUENTO);
        for (int i = 0; i < 30; i++) listaDescuentos.add(Descuento.DIEZ);
        for (int i = 0; i < 8; i++) listaDescuentos.add(Descuento.VEINTICINCO);
        for (int i = 0; i < 2; i++) listaDescuentos.add(Descuento.CINCUENTA);
        
        return listaDescuentos;
    }

    private boolean controlAcceso() {
        JComponent[] componentes = new JComponent[4];
        componentes[0] = new JLabel("Usuario: ");
        JTextField txtUsuario = new JTextField(50);
        componentes[1] = txtUsuario;
        componentes[2] = new JLabel("Contraseña: ");
        JTextField txtContrasenya = new JTextField(50);
        componentes[3] = txtContrasenya;

        int resultado = JOptionPane.showConfirmDialog(null, componentes, "Confirmación acceso",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {

            if (txtUsuario.getText().equals("Empresa") && txtContrasenya.getText().equals("123")) {
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

        for (int i = 0; i < 5; i++) {
            if (i < estrellas) {
                string.append("★");
            } else {
                string.append("☆");
            }
        }

        return string.toString();
    }
    
    private JPanel anyadirPanelPortada(Pelicula p) {
    	JPanel panelColumna = new JPanel(new BorderLayout());
        JPanel panelVal = new JPanel(new FlowLayout());
        JPanel panelTitulo = new JPanel();
        JLabel labelTitulo = new JLabel();

        java.net.URL url = getClass().getResource("/" + p.getTitulo().toString() + ".jpg");
        if (url == null) url = getClass().getResource("/" + p.getTitulo().toString() + ".png");

        if (url != null) {
            labelTitulo.setIcon(new ImageIcon(url));
        } else {
            labelTitulo.setText(p.getTitulo().toString());
        }

        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitulo.setVerticalAlignment(SwingConstants.CENTER);
        labelTitulo.setName(p.getTitulo());
        panelTitulo.add(labelTitulo);
        panelColumna.add(panelTitulo, BorderLayout.CENTER);

        JLabel labelEstrellas = new JLabel();
        labelEstrellas.setText(generarEstrellas(p.getValoracion()));
        labelEstrellas.setBackground(Color.yellow);
        labelEstrellas.setFont(new Font("Dialog", Font.BOLD, 12));
        labelEstrellas.setOpaque(true);

        panelVal.add(labelEstrellas);

        JLabel labelValoracion = new JLabel(
                String.format("%.1f", p.getValoracion())
        );

        labelValoracion.setFont(labelValoracion.getFont().deriveFont(Font.BOLD));
        labelValoracion.setBackground(Color.yellow);
        labelValoracion.setOpaque(true);
        panelVal.add(labelValoracion);

        panelColumna.add(panelVal, BorderLayout.SOUTH);
        
        return panelColumna;
    }
    
    private class HiloFondoDescuento extends Thread {

        private JButton boton;
        private Color color1;
        private Color color2;
        private int duracion; // tiempo en milisegundos para ir de color1 a color2

        public HiloFondoDescuento(JButton boton, Color color1, Color color2, int duracion) {
            this.boton = boton;
            this.color1 = color1;
            this.color2 = color2;
            this.duracion = duracion;
        }
        
        
        //ChatGPT
        @Override
        public void run() {
            int pasos = 50;
            int delay = duracion / pasos;

            float ratio = 0f;
            boolean subiendo = true; // indica si sube hacia color2 o baja hacia color1
            
            
            while (true) {
                // Calcular color interpolado
                int r = (int) (color1.getRed()   + ratio * (color2.getRed()   - color1.getRed()));
                int g = (int) (color1.getGreen() + ratio * (color2.getGreen() - color1.getGreen()));
                int b = (int) (color1.getBlue()  + ratio * (color2.getBlue()  - color1.getBlue()));
                Color nuevoColor = new Color(r, g, b);

                SwingUtilities.invokeLater(() -> boton.setBackground(nuevoColor));

                // Actualizar ratio
                if (subiendo) {
                    ratio += 1.0 / pasos;
                    if (ratio >= 1f) {
                        ratio = 1f;
                        subiendo = false;
                    }
                } else {
                    ratio -= 1.0 / pasos;
                    if (ratio <= 0f) {
                        ratio = 0f;
                        subiendo = true;
                    }
                }
                
                //ChatGPT

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}



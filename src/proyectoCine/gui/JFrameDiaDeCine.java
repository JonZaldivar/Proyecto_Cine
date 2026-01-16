package proyectoCine.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import proyectoCine.domain.Opcion;

public class JFrameDiaDeCine extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<List<Opcion>> listaOpciones ;
	
	public JFrameDiaDeCine(List<List<Opcion>> listaOpciones) {
		this.listaOpciones = listaOpciones;
		this.setSize(900,600);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		JLabel labelTitulo = new JLabel("Opciones para el día en el cine");
		labelTitulo.setOpaque(true);
		labelTitulo.setFont(new Font("Rockwell", Font.BOLD, 24));
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitulo.setBackground(new Color(217, 234, 246));
		this.add(labelTitulo,BorderLayout.NORTH);
	
		JPanel panelOpciones = new JPanel();
		panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
		panelOpciones.setOpaque(true);
		
		if(listaOpciones!=null && !listaOpciones.isEmpty()) {
			for(int i = 0;i<listaOpciones.size();i++) {
				List<Opcion> opciones = listaOpciones.get(i);
				JPanel panelFila = new JPanel();
				panelFila.setBorder(BorderFactory.createTitledBorder("Grupo de opciones"));
				panelFila.setBackground(new Color(245, 245, 245));
				
				for(Opcion opcion : opciones) {
					JLabel lblOpcion = new JLabel(" • " + opcion.toString());
                    lblOpcion.setFont(new Font("Verdana", Font.PLAIN, 14));
                    panelFila.add(lblOpcion);
				}
				
				panelOpciones.add(panelFila);
			}
		}
		
		JScrollPane scrollPane = new JScrollPane(panelOpciones);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); 
        scrollPane.setBorder(null); 

        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
	}
	
	
	 public static void main(String[] args) {
		 List<List<Opcion>> opciones = null;
		 JFrameDiaDeCine jf = new JFrameDiaDeCine(opciones);
		 jf.setVisible(true);
	 }
}

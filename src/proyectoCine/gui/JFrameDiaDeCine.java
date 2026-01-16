package proyectoCine.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import proyectoCine.domain.Opcion;

public class JFrameDiaDeCine extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<List<Opcion>> listaOpciones ;
	
	public JFrameDiaDeCine(List<List<Opcion>> listaOpciones) {
		this.listaOpciones = listaOpciones;
		
		Color azulFondo = new Color(217, 234, 246);
		
		setTitle("Opciones para el día en el cine");
		this.setSize(500,600);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		getContentPane().setBackground(azulFondo);
		
		
		JLabel labelTitulo = new JLabel("Opciones para el día en el cine");
		labelTitulo.setOpaque(true);
		labelTitulo.setFont(new Font("Rockwell", Font.BOLD, 24));
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitulo.setBackground(azulFondo);
		labelTitulo.setBorder(new EmptyBorder(15, 10, 15, 10));
		this.add(labelTitulo,BorderLayout.NORTH);
	
		JPanel panelOpciones = new JPanel();
		panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
		panelOpciones.setBackground(azulFondo);
        panelOpciones.setBorder(new EmptyBorder(15, 15, 15, 15));
		
		if(listaOpciones!=null && !listaOpciones.isEmpty()) {
			int contador = 1;
            for (List<Opcion> opciones : listaOpciones) {

                JPanel panelGrupo = new JPanel();
                panelGrupo.setLayout(new BoxLayout(panelGrupo, BoxLayout.Y_AXIS));
                panelGrupo.setBackground(Color.WHITE);
                panelGrupo.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220)),
                        new EmptyBorder(10, 15, 10, 15)
                ));

                panelGrupo.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                JLabel tituloGrupo = new JLabel("Grupo de opciones " + contador);
                tituloGrupo.setFont(new Font("Dialog", Font.BOLD, 14));
                tituloGrupo.setBorder(new EmptyBorder(0, 0, 8, 0));
                panelGrupo.add(tituloGrupo);

                for (Opcion opcion : opciones) {
                    JLabel lblOpcion = new JLabel("• " + opcion.toString());
                    lblOpcion.setFont(new Font("Verdana", Font.PLAIN, 13));
                    lblOpcion.setBorder(new EmptyBorder(2, 10, 2, 0));
                    panelGrupo.add(lblOpcion);
                }

                panelOpciones.add(panelGrupo);
                panelOpciones.add(Box.createVerticalStrut(12));
                contador++;
            }
        } else {
            JLabel sinOpciones = new JLabel("No hay opciones disponibles para este día.");
            sinOpciones.setFont(new Font("Dialog", Font.ITALIC, 14));
            sinOpciones.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelOpciones.add(sinOpciones);
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

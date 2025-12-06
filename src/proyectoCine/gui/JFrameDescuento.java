package proyectoCine.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import proyectoCine.domain.Descuento;

public class JFrameDescuento extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	private List<Descuento> descuentos;
	
	
	public JFrameDescuento(ArrayList<Descuento> descuentos) {
		this.descuentos = descuentos;
		
		
		this.setSize(50,50);
		this.setLocationRelativeTo(null);
		
		
	}
	
	
	

}

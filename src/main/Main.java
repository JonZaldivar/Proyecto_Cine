package main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import proyectoCine.gui.JFramePrincipal;
import proyectoCine.domain.Pelicula;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<Pelicula> cartelera = new ArrayList<Pelicula>();
		
		SwingUtilities.invokeLater(() -> new JFramePrincipal(cartelera));
		

}}

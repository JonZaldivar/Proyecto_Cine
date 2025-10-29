package main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import proyectoCine.gui.JFramePrincipal;
import proyectoCine.domain.Pelicula;
import proyectoCine.domain.Pelicula.Genero;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<Pelicula> cartelera = new ArrayList<Pelicula>();
		cartelera.add(new Pelicula(1,"Batman","Yo",90,Genero.ACCION,null));
		
		SwingUtilities.invokeLater(() -> new JFramePrincipal(cartelera));
		
		System.out.println("nedindfiednid");
		

}}

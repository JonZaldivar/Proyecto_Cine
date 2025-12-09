package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;

import proyectoCine.gui.JFramePrincipal;
import proyectoCine.persistence.CineGestorBD;
import proyectoCine.domain.Actor;
import proyectoCine.domain.Actor.Pais;
import proyectoCine.domain.Horario;
import proyectoCine.domain.Pelicula;
import proyectoCine.domain.Pelicula.Clasificacion;
import proyectoCine.domain.Pelicula.Genero;

public class Main {

    public static void main(String[] args) {

    	CineGestorBD gestor = new CineGestorBD();
		List<Pelicula>cartelera = gestor.getPeliculas();
		 
		 

        

        // Crear GUI
        SwingUtilities.invokeLater(() -> new JFramePrincipal(cartelera));
    }
    
    
    public void asignarHorarios(List<Pelicula> peliculas) {
    	
    	Random random = new Random();
    	for(Pelicula p : peliculas) {
    		
    		ArrayList<Horario> horarios = new ArrayList<Horario>();
    		
    		for(int i = 0;i<2;i++) {
    			int numero = random.nextInt(Horario.values().length);
    			horarios.add(Horario.values()[numero]);
    		}
    		
    		p.setHorarios_disponibles(horarios);
    		
    	}
    }
}

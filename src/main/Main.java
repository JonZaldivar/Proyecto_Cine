package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
    	gestor.crearTablas();
        gestor.cargarPeliculas();
        gestor.cargarActores();
        gestor.cargarPeliculaActores();
        gestor.cargarHorarios();
		List<Pelicula>cartelera = gestor.getPeliculas();
		asignarHorarios(cartelera, gestor);
		 
		 

        

        // Crear GUI
        SwingUtilities.invokeLater(() -> new JFramePrincipal(cartelera));
    }
    
    
    public static void asignarHorarios(List<Pelicula> peliculas, CineGestorBD gestor) {

        Random random = new Random();

        for (Pelicula p : peliculas) {

            // Usamos un Set para evitar repetidos
            Set<Horario> setHorarios = new HashSet<>();

            
            while (setHorarios.size() < 3) {
                int idx = random.nextInt(Horario.values().length);
                setHorarios.add(Horario.values()[idx]);
            }
            
            ArrayList<Horario> lista = new ArrayList<>(setHorarios);
            
            p.setHorarios_disponibles(lista);
            
            gestor.insertarPeliculaHorario(p.getId(), lista);
        }
    }

}

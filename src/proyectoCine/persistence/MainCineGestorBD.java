package proyectoCine.persistence;

import java.util.ArrayList;
import java.util.List;

import proyectoCine.domain.Pelicula;

public class MainCineGestorBD {

	public static void main(String[] args) {
		 CineGestorBD gestor = new CineGestorBD();
		 List<Pelicula>p = gestor.getPeliculas();
		 System.out.println(p);
		 
	}

}

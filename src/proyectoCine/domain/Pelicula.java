package proyectoCine.domain;

import java.util.List;

public class Pelicula {
	
	public enum Genero {
	    ACCION, AVENTURAS, COMEDIA, DRAMA, FANTASIA,
	    HISTORICO, HORROR, MUSICAL, ROMANCE, CIENCIA_FICCION,
	    SUSPENSE, TERROR, WESTERN
	}

	private int id;
	private String titulo;
	private String director;
	private int duracion;
	private List<Actor> actores;
	private Genero genero;
	

}

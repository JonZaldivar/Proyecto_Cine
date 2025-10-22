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
	
	public Pelicula(int id, String titulo, String director, int duracion, Genero genero, List<Actor> actores) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.director = director;
		this.duracion = duracion;
		this.genero = genero;
		this.actores = actores;
	}
	
	public String getDirector() {
		return director;
	}
	
	
	public List<Actor> getActores() {
		return actores;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public int getDuracion() {
		return duracion;
	}
	
	public Genero getGenero() {
		return genero;
	}
	
	
	

}

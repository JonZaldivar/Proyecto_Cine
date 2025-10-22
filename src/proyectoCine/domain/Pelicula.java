package proyectoCine.domain;

import java.util.List;
import java.util.Objects;

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
	public Pelicula(int id, String titulo, String director, int duracion, List<Actor> actores, Genero genero) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.director = director;
		this.duracion = duracion;
		this.actores = actores;
		this.genero = genero;
	}
	
	public Pelicula(String titulo) {
		this.titulo = titulo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pelicula other = (Pelicula) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Pelicula [id=" + id + ", titulo=" + titulo + ", director=" + director + ", duracion=" + duracion
				+ ", actores=" + actores + ", genero=" + genero + "]";
	}

}

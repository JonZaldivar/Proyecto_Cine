package proyectoCine.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pelicula {
	
	public enum Genero {
	    CUALQUIERA,ACCION, AVENTURAS, COMEDIA, DRAMA, FANTASIA,
	    HISTORICO, HORROR, MUSICAL, ROMANCE, CIENCIA_FICCION,
	    SUSPENSE, TERROR, WESTERN
	}
	
	public enum Clasificacion {
		    TODAS,
		    MAYORES_7,
		    MAYORES_12,
		    MAYORES_16,
		    MAYORES_18;
	}
	
	
	
	
	

	private int id;
	private String titulo;
	private String director;
	private int duracion;
	private List<Actor> actores;
	private Genero genero;
	private Clasificacion clasificacion;
	private String resumen;
	private ArrayList<Horario> horarios_disponibles;
	
	

	public Pelicula(int id, String titulo, String director, int duracion, Genero genero, List<Actor> actores
			,Clasificacion clasificacion, String resumen) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.director = director;
		this.duracion = duracion;
		this.genero = genero;
		this.actores = actores;
		this.clasificacion = clasificacion;
		this.resumen = resumen;
		this.horarios_disponibles = new ArrayList<Horario>();
	}
	
	

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getTitulo() {
		return titulo;
	}



	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}



	public String getDirector() {
		return director;
	}



	public void setDirector(String director) {
		this.director = director;
	}



	public int getDuracion() {
		return duracion;
	}



	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}



	public List<Actor> getActores() {
		return actores;
	}



	public void setActores(List<Actor> actores) {
		this.actores = actores;
	}



	public Genero getGenero() {
		return genero;
	}



	public void setGenero(Genero genero) {
		this.genero = genero;
	}



	public Clasificacion getClasificacion() {
		return clasificacion;
	}



	public void setClasificacion(Clasificacion clasificacion) {
		this.clasificacion = clasificacion;
	}
	
	public String getResumen() {
		return resumen;
	}
	
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
	
	public ArrayList<Horario> getHorarios_disponibles() {
		return horarios_disponibles;
	}
	
	public void setHorarios_disponibles(ArrayList<Horario> horarios_disponibles) {
		this.horarios_disponibles = horarios_disponibles;
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
				+ ", actores=" + actores + ", genero=" + genero + ", clasificacion=" + clasificacion + "]";
	}

	

}

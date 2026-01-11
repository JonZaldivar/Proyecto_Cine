package proyectoCine.domain;

import java.util.Objects;

public class Opcion {
	private Pelicula pelicula;
	private Horario horario;
	
	public Opcion(Pelicula pelicula,Horario horario) {
		this.pelicula = pelicula;
		this.horario = horario;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	public Horario getHorario() {
		return horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	@Override
	public String toString() {
		return "Opcion [pelicula=" + pelicula + ", horario=" + horario + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(horario, pelicula);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Opcion other = (Opcion) obj;
		return horario == other.horario && Objects.equals(pelicula, other.pelicula);
	}
	
	
	
}

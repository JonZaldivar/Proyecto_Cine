package proyectoCine.domain;
import java.time.LocalDate;
public class Reserva {
	private Pelicula pelicula;
	private Horario horario;
	private Sala sala;
	private String asientosSeleccionados;
	private double precioTotal;
	
	
	public Reserva(Pelicula pelicula, Horario horario, Sala sala, String asientosSeleccionados, double precioTotal) {
		this.pelicula = pelicula;
		this.horario = horario;
		this.sala = sala;
		this.asientosSeleccionados = asientosSeleccionados;
		this.precioTotal = precioTotal;
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


	public Sala getSala() {
		return sala;
	}


	public void setSala(Sala sala) {
		this.sala = sala;
	}


	public String getAsientosSeleccionados() {
		return asientosSeleccionados;
	}


	public void setAsientosSeleccionados(String asientosSeleccionados) {
		this.asientosSeleccionados = asientosSeleccionados;
	}


	public double getPrecioTotal() {
		return precioTotal;
	}


	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}
	
	
	
	
	
}

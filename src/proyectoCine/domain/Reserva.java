package proyectoCine.domain;

import java.time.LocalDate;


public class Reserva {
	private Pelicula pelicula;
	private LocalDate fecha;
	private Horario horario;
	private Sala sala;
	
	
	public Reserva(Pelicula pelicula, LocalDate fecha, Horario horario, Sala sala) {
		this.pelicula = pelicula;
		this.fecha = fecha;
		this.horario = horario;
		this.sala = sala;
	}
	
	public Pelicula getPelicula() {
		return pelicula;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public Horario getHorario() {
		return horario;
	}
	public Sala getSala() {
		return sala;
	}
	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public void setHorario(Horario horario) {
		this.horario = horario;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
	}
	
	
}

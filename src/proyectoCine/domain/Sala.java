package proyectoCine.domain;

public class Sala {

	private int id;
	private int fila;
	private int columna;
	
	public Sala(int id, int fila, int columna) {
		this.id = id;
		this.fila = fila;
		this.columna = columna;
	}
	
	public int getId() {
		return id;
	}
	public int getFila() {
		return fila;
	}
	public int getColumna() {
		return columna;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setFila(int fila) {
		this.fila = fila;
	}
	public void setColumna(int columna) {
		this.columna = columna;
	}
}

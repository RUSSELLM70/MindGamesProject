package cenfotec.proyecto.tableros;

public class Pieza {


	public String movimientoPieza;
	public String jugador;
	public String nombre;
	
	public Pieza(String nombre,String jugador, String movimiento) {
		this.jugador = jugador;
		this.nombre = nombre;
		this.movimientoPieza = movimiento;
	}
	
	
	
}

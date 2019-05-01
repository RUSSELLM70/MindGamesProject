package cenfotec.proyecto.tableros;

public class PiezaAjedrez extends Pieza{
	
	public String movimiento;
	public String color;
	public int cantidadMovimientos=0;
	
	public PiezaAjedrez(String nombre, String jugador, String pieza, String color) {
		super(nombre, jugador, pieza);
		this.color=color;
		this.movimiento=PiezaAjedrez.retornarMovimiento(pieza);
	}
	
	
	public static String retornarMovimiento(String nombrePieza) {
		String movimientoPieza="";
		switch(nombrePieza) {
		case "peon":
			movimientoPieza = "frente/consume diagnonal";
			break;
		case "torre":
			movimientoPieza = "recto ilimitado";
			break;
		case "alfil":
			movimientoPieza = "Diagonal ilimitado";
			break;
		case "caballo":
			movimientoPieza = "movimiento en L";
			break;
		case "reina":
			movimientoPieza = "recto y diagonal ilimitado";
			break;
		case "rey":
			movimientoPieza = "recto 1 posicion";
			break;
		case "*":
			movimientoPieza = "Ninguno";
			break;
		}
		
		
		return movimientoPieza;
	}

	
	public void sumarMovimiento() {
		cantidadMovimientos++;
	}
	
	public void restarMovimiento() {
		cantidadMovimientos--;
	}
	
	
	public String getMovimiento() {
		return movimiento;
	}


	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public int getCantidadMovimientos() {
		return cantidadMovimientos;
	}


	public void setCantidadMovimientos(int cantidadMovimientos) {
		this.cantidadMovimientos = cantidadMovimientos;
	}
	
	
	
	
}

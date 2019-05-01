package cenfotec.proyecto.tableros;

public class PiezaDamas extends Pieza{

	private String color;
	
	public PiezaDamas(String nombre, String jugador, String movimiento, String color) {
		super(nombre, jugador, movimiento);
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public void convertirEnReina() {
		this.nombre = "R";
	}

}

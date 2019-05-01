package cenfotec.proyecto.complementoJuego;

public class Juego {

	String jugador1;
	String jugador2;
	String ganador;
	String perdedor;
	
	public Juego(String jugador1, String jugador2, String ganador, String perdedor) {
		super();
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
		this.ganador = ganador;
		this.perdedor = perdedor;
	}

	public String getJugador1() {
		return jugador1;
	}

	public void setJugador1(String jugador1) {
		this.jugador1 = jugador1;
	}

	public String getJugador2() {
		return jugador2;
	}

	public void setJugador2(String jugador2) {
		this.jugador2 = jugador2;
	}

	public String getGanador() {
		return ganador;
	}

	public void setGanador(String ganador) {
		this.ganador = ganador;
	}

	public String getPerdedor() {
		return perdedor;
	}

	public void setPerdedor(String perdedor) {
		this.perdedor = perdedor;
	}

	@Override
	public String toString() {
		return "Juego [jugador1=" + jugador1 + ", jugador2=" + jugador2 + ", ganador=" + ganador + ", perdedor="
				+ perdedor + "]";
	}
	

	
}

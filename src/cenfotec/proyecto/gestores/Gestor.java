package cenfotec.proyecto.gestores;

public abstract class Gestor {
	
	public abstract void iniciarPartida ();
	public abstract void continuarPartida ();
	public abstract void mostrarEstadoJuego();
	public abstract void guardarPartida ();
	public abstract void cargarPartida ();
	
}

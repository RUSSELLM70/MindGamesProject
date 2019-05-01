package cenfotec.proyecto.gestores;

import java.io.FileNotFoundException;
import java.io.IOException;

import cenfotec.proyecto.complementoJuego.JuegoAjedrez;

public class GestorAjedrez extends Gestor{
	@Override
	public void iniciarPartida() {
		JuegoAjedrez.iniciarPartida();
		
	}
	@Override
	public void continuarPartida() {
		JuegoAjedrez.continuarPartida();
		
	}
	@Override
	public void mostrarEstadoJuego() {
		JuegoAjedrez.ImprimirEstadoJuego();
		
	}
	@Override
	public void guardarPartida() {
		try {
			JuegoAjedrez.guardarPartida();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void cargarPartida() {
		try {
			JuegoAjedrez.cargarPartidaArchivoTexto("Ajedrez");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

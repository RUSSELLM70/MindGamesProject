package cenfotec.proyecto.gestores;

import java.io.FileNotFoundException;
import java.io.IOException;

import cenfotec.proyecto.complementoJuego.JuegoDamas;

public class GestorDamas extends Gestor{

	@Override
	public void iniciarPartida() {
		JuegoDamas.iniciarJuego();
		
	}

	@Override
	public void continuarPartida() {
		JuegoDamas.continuarPartida();
		
	}

	@Override
	public void mostrarEstadoJuego() {
		JuegoDamas.ImprimirEstadoJuego();
		
	}

	@Override
	public void guardarPartida() {
		try {
			JuegoDamas.guardarPartida();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cargarPartida() {
		try {
			JuegoDamas.cargarPartidaArchivoTexto("Damas");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

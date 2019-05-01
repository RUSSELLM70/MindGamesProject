package cenfotec.proyecto.piezas;

import cenfotec.proyecto.complementoJuego.JuegoAjedrez;
import cenfotec.proyecto.tableros.PartidaAjedrez;

public class Alfil extends PartidaAjedrez {
	static JuegoAjedrez JA;

	public static boolean movimientoAlfil(String posicionInicial, String posicionFinal) {
		boolean verificador = JA.calcularPiezasEnMedioDiagonal(posicionInicial, posicionFinal);

		return verificador;
	}
}

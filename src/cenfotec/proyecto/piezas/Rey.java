package cenfotec.proyecto.piezas;

import cenfotec.proyecto.complementoJuego.JuegoAjedrez;
import cenfotec.proyecto.tableros.PartidaAjedrez;

public class Rey extends PartidaAjedrez {
	
	static JuegoAjedrez JA;

	
	public static boolean movimientoRey(String posicionInicial, String posicionFinal) {
		boolean verificador = false;

		if (posicionFinal.contentEquals(
				posicionInicial.charAt(0) + "" + Character.getNumericValue(posicionInicial.charAt(1) + 1))
				|| posicionFinal.contentEquals(
						posicionInicial.charAt(0) + "" + Character.getNumericValue(posicionInicial.charAt(1) - 1))) {
			// Cuando el movimiento es en la misma columna.
			verificador = true;
		} else if (posicionFinal
				.contentEquals(JA.retornarSiguienteColumna(posicionInicial.charAt(0) + "") + posicionInicial.charAt(1))
				|| posicionFinal.contentEquals(
						JA.retornarAnteriorColumna(posicionInicial.charAt(0) + "") + posicionInicial.charAt(1))) {
			// Cuando el movimiento es en la misma fila.
			verificador = true;
		} else if (posicionFinal
				.contentEquals(JA.retornarSiguienteColumna(posicionInicial.charAt(0) + "")
						+ Character.getNumericValue(posicionInicial.charAt(1) + 1))
				|| posicionFinal.contentEquals(JA.retornarAnteriorColumna(posicionInicial.charAt(0) + "")
						+ Character.getNumericValue(posicionInicial.charAt(1) + 1))) {
			// Cuando el movimiento es diagonal arriba.
			// Trabajando en este if.
			verificador = true;
		} else if (posicionFinal
				.contentEquals(JA.retornarSiguienteColumna(posicionInicial.charAt(0) + "")
						+ Character.getNumericValue(posicionInicial.charAt(1) - 1))
				|| posicionFinal.contentEquals(JA.retornarAnteriorColumna(posicionInicial.charAt(0) + "")
						+ Character.getNumericValue(posicionInicial.charAt(1) - 1))) {
			// Cuando el movimiento es diagonal atras.
			verificador = true;
		}

		return verificador;
	}

}

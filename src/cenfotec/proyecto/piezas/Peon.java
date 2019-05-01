package cenfotec.proyecto.piezas;

import cenfotec.proyecto.complementoJuego.JuegoAjedrez;
import cenfotec.proyecto.tableros.PartidaAjedrez;
import cenfotec.proyecto.tableros.PiezaAjedrez;

public class Peon extends PartidaAjedrez {

	static JuegoAjedrez JA;
	
	public static boolean movimientoPeon(String posicionInicial, String posicionFinal, PiezaAjedrez peon) {
		boolean checker = false;

		if (posicionInicial.contentEquals(posicionFinal)) {
			System.out.println("Las coordenadas no pueden coincidir.");
		} else {
			if (contador % 2 == 0) {// Si pieza es negra.

				if (posicionInicial.charAt(0) == posicionFinal.charAt(0)) {// Cuando el peon es nuevo y quiere avanzar
																			// una
																			// sola posicion al frente.
					if (Character.getNumericValue(posicionInicial.charAt(1)) + 1 == Character
							.getNumericValue(posicionFinal.charAt(1))) {
						if (JA.retornarPiezaPosicion(posicionFinal).nombre.equals("--")) {
							checker = true;
						}
					}
					if (Character.getNumericValue(posicionInicial.charAt(1)) + 2 == Character
							.getNumericValue(posicionFinal.charAt(1)) && peon.getCantidadMovimientos() == 0) {
						if (JA.retornarPiezaPosicion(posicionFinal).nombre.equals("--")) {
							checker = true;
						}
					}

				} else if (posicionFinal.charAt(0) == JA.retornarSiguienteColumna(posicionInicial.charAt(0) + "")
						.charAt(0)) {
					// Cuando se quiere comer una posicion
					if (Character.getNumericValue(posicionInicial.charAt(1)) + 1 == Character
							.getNumericValue(posicionFinal.charAt(1))) {
						if (JA.retornarPiezaPosicion(posicionFinal).nombre.equals("--")) {

						} else {
							checker = true;
						}
					}
				} else if (posicionFinal.charAt(0) == JA.retornarAnteriorColumna(posicionInicial.charAt(0) + "")
						.charAt(0)) {
					// Cuando se quiere comer una posicion
					if (Character.getNumericValue(posicionInicial.charAt(1)) + 1 == Character
							.getNumericValue(posicionFinal.charAt(1))) {
						if (JA.retornarPiezaPosicion(posicionFinal).nombre.equals("--")) {

						} else {
							checker = true;
						}
					}
				}

			} else if (contador % 2 != 0) {// Si pieza es blanca.

				if (posicionInicial.charAt(0) == posicionFinal.charAt(0)) {// Cuando el peon es nuevo y quiere avanzar
																			// una
					// sola posicion al frente.
					if (Character.getNumericValue(posicionInicial.charAt(1)) - 1 == Character
							.getNumericValue(posicionFinal.charAt(1))) {
						if (JA.retornarPiezaPosicion(posicionFinal).nombre.equals("--")) {
							checker = true;
						}
					}
					if (Character.getNumericValue(posicionInicial.charAt(1)) - 2 == Character
							.getNumericValue(posicionFinal.charAt(1)) && peon.getCantidadMovimientos() == 0) {
						if (JA.retornarPiezaPosicion(posicionFinal).nombre.equals("--")) {
							checker = true;
						}
					}

				} else if (posicionFinal.charAt(0) == JA.retornarSiguienteColumna(posicionInicial.charAt(0) + "")
						.charAt(0)) {
//					Cuando se quiere comer una posicion
					if (Character.getNumericValue(posicionInicial.charAt(1)) - 1 == Character
							.getNumericValue(posicionFinal.charAt(1))) {
						if (JA.retornarPiezaPosicion(posicionFinal).nombre.equals("--")) {

						} else {
							checker = true;
						}
					}
				} else if (posicionFinal.charAt(0) == JA.retornarAnteriorColumna(posicionInicial.charAt(0) + "")
						.charAt(0)) {
					// Cuando se quiere comer una posicion
					if (Character.getNumericValue(posicionInicial.charAt(1)) - 1 == Character
							.getNumericValue(posicionFinal.charAt(1))) {
						if (JA.retornarPiezaPosicion(posicionFinal).nombre.equals("--")) {

						} else {
							checker = true;
						}
					}
				}

			}
		}

		return checker;
	}
}

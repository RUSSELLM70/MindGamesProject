package cenfotec.proyecto.piezas;

import cenfotec.proyecto.complementoJuego.JuegoAjedrez;
import cenfotec.proyecto.tableros.PartidaAjedrez;

public class Reina  extends PartidaAjedrez{
	static JuegoAjedrez JA;

	
	public static boolean movimientoReina(String posicionInicial, String posicionFinal) {
		boolean verificador = false;
		boolean encontrado = false;

		if (posicionInicial.contentEquals(posicionFinal)) {
			verificador = false;
		} else {
			// Evaluar movimiento en diagonales.
			encontrado = JA.calcularPiezasEnMedioDiagonal(posicionInicial, posicionFinal);

			if (encontrado == false) {
				// Evaluar movimiento al frente y hacia atras.
				encontrado = JA.movimientoLineal(posicionInicial, posicionFinal);
			}

			if (encontrado == false) {
				// Evaluar movimiento horizontal.
				encontrado = JA.movimientoHorizontal(posicionInicial, posicionFinal);
			}

			// Ultima evaluacion.
			if (encontrado == false) {
				verificador = false;
			} else {
				verificador = true;
			}
		}

		return verificador;
	}
}

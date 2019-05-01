package cenfotec.proyecto.piezas;

import cenfotec.proyecto.complementoJuego.JuegoAjedrez;
import cenfotec.proyecto.tableros.PartidaAjedrez;

public class Torre extends PartidaAjedrez{
	static JuegoAjedrez JA;

	public static boolean movimientoTorre(String posicionInicial, String posicionFinal) {

		boolean verificador = false;
		boolean encontrada = false;

		if (posicionInicial.contentEquals(posicionFinal)) {
			verificador = false;
		} else {
			// Evaluar movimiento en lineal.
			encontrada = JA.movimientoLineal(posicionInicial, posicionFinal);

			if (encontrada == false) {
				// Evaluar movimiento horizontal.
				encontrada = JA.movimientoHorizontal(posicionInicial, posicionFinal);
			}

			// Ultima evaluacion.
			if (encontrada == false) {
				verificador = false;
			} else {
				verificador = true;
			}
		}

		return verificador;
	}
}

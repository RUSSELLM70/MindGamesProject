package cenfotec.proyecto.piezas;

import cenfotec.proyecto.complementoJuego.JuegoAjedrez;
import cenfotec.proyecto.tableros.PartidaAjedrez;

public class Caballo  extends PartidaAjedrez{
	static JuegoAjedrez JA;

	
	public static boolean movimientoCaballo(String posicionInicial, String posicionFinal) {
		boolean verificador = false;

		String PosicionArribaDerechaUnaAlfrente = JA.retornarSiguienteColumna(
				JA.retornarSiguienteColumna(posicionInicial.charAt(0) + ""))
				+ (Character.getNumericValue(posicionInicial.charAt(1)) + 1);
		String PosicionArribaIzquierdaUnaAlfrente = JA.retornarAnteriorColumna(
				JA.retornarAnteriorColumna(posicionInicial.charAt(0) + ""))
				+ (Character.getNumericValue(posicionInicial.charAt(1)) + 1);
		String posicionAbajoDerechaUnaAtras = JA.retornarSiguienteColumna(
				JA.retornarSiguienteColumna(posicionInicial.charAt(0) + ""))
				+ (Character.getNumericValue(posicionInicial.charAt(1)) - 1);
		String posicionAbajoIzquierdaUnaAtras = JA.retornarAnteriorColumna(
				JA.retornarAnteriorColumna(posicionInicial.charAt(0) + ""))
				+ (Character.getNumericValue(posicionInicial.charAt(1)) - 1);
		String PosicionArribaDerechaTresAlfrente = JA.retornarSiguienteColumna(posicionInicial.charAt(0) + "")
				+ (Character.getNumericValue(posicionInicial.charAt(1)) + 2);
		String PosicionArribaIzquierdaTresAlfrente = JA.retornarAnteriorColumna(posicionInicial.charAt(0) + "")
				+ (Character.getNumericValue(posicionInicial.charAt(1)) + 2);
		String posicionAbajoDerechaTresAtras = JA.retornarSiguienteColumna(posicionInicial.charAt(0) + "")
				+ (Character.getNumericValue(posicionInicial.charAt(1)) - 2);
		String posicionAbajoIzquierdaTresAtras = JA.retornarAnteriorColumna(posicionInicial.charAt(0) + "")
				+ (Character.getNumericValue(posicionInicial.charAt(1)) - 2);

		if (posicionFinal.contentEquals(PosicionArribaDerechaUnaAlfrente)) {
			verificador = true;
		} else if (posicionFinal.contentEquals(PosicionArribaIzquierdaUnaAlfrente)) {
			verificador = true;
		} else if (posicionFinal.contentEquals(posicionAbajoDerechaUnaAtras)) {
			verificador = true;
		} else if (posicionFinal.contentEquals(posicionAbajoIzquierdaUnaAtras)) {
			verificador = true;
		} else if (posicionFinal.contentEquals(PosicionArribaDerechaTresAlfrente)) {
			verificador = true;
		} else if (posicionFinal.contentEquals(PosicionArribaIzquierdaTresAlfrente)) {
			verificador = true;
		} else if (posicionFinal.contentEquals(posicionAbajoDerechaTresAtras)) {
			verificador = true;
		} else if (posicionFinal.contentEquals(posicionAbajoIzquierdaTresAtras)) {
			verificador = true;
		} else {
			verificador = false;
		}

		return verificador;
	}

}

package cenfotec.proyecto.complementoJuego;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cenfotec.proyecto.generador.PersistenciaTexto;
import cenfotec.proyecto.generador.Serializer;
import cenfotec.proyecto.piezas.*;
import cenfotec.proyecto.tableros.PartidaAjedrez;
import cenfotec.proyecto.tableros.PiezaAjedrez;
import cenfotec.proyecto.tableros.Tablero;


public class JuegoAjedrez extends Juego {


	private static PartidaAjedrez partida = new PartidaAjedrez();
	private static int contador = partida.getContador();
	private static Scanner in = new Scanner(System.in);

	public JuegoAjedrez(String jugador1, String jugador2, String ganador, String perdedor) {
		super(jugador1, jugador2, ganador, perdedor);
	}

	public static void iniciarPartida() {

		System.out.println("\u001b[32;1m|***************************|");
		System.out.println("|Comienzan las piezas Negras|");
		System.out.println("|***************************|");
		partida = new PartidaAjedrez();
		String ganador="";

		contador = 2;
		boolean verificador = false;
		while (verificador == false) {
			ganador = EvaluarGanador();
			if(ganador.contentEquals("Ninguno")) {
				ImprimirEstadoJuego();
				switch (lecturaOpcionMenu()) {
				case "1":
					verificador = false;
					try {
					guardarPartida();
					}catch(Exception e) {
						System.out.println("Ocurrido un problema al guardar la partida.");
					}
					verificador = false;
					break;
				case "2":
					if (contador % 2 == 0) {
						System.out.println("|***************************|");
						System.out.println("|  Mover piezas negras      |");
						System.out.println("|***************************|");
						moverPieza("N");
					} else {
						System.out.println("|***************************|");
						System.out.println("|  Mover piezas blancas     |");
						System.out.println("|***************************|");
						moverPieza("B");
					}
					
					break;
				case "3":
					verificador = true;
					break;
				default:
					System.out.println("Opcion no valida.");
					verificador = false;
					break;

				}
			}else {
				ImprimirEstadoJuego();
				System.out.println("Gana el rey "+ ganador);
				System.out.println("            GAME OVER");
				verificador = true;
			}
		}

	}

	public static void continuarPartida(){
		System.out.println("count"  + contador);
		String ganador="";
		boolean verificador = false;
		
		while (verificador == false) {
			ganador = EvaluarGanador();
			if(ganador.contentEquals("Ninguno")) {
				ImprimirEstadoJuego();
				switch (lecturaOpcionMenu()) {
				case "1":
					if (contador % 2 == 0) {
						System.out.println("**************************");
						System.out.println("  Mover piezas negras.    ");
						System.out.println("**************************");					
						moverPieza("N");
					} else {
						System.out.println("**************************");
						System.out.println("  Mover piezas blancas.    ");
						System.out.println("**************************");				
						moverPieza("B");
					}
					verificador = false;
					break;
				case "2":
					try {
					guardarPartida();
					}catch(Exception e) {
						System.out.println("Ocurrio un problema al guardar la partida.");
					}
					verificador = false;
					break;
				case "3":
					verificador = true;
					break;
				default:
					System.out.println("Opcion no valida.");
					verificador = false;
					break;
				}
			}else {
				ImprimirEstadoJuego();
				System.out.println("Gana el rey "+ ganador);
				System.out.println("            GAME OVER");
				verificador = true;
			}
		}
	}
	
	public static void imprimirOpcionesJuego() {
		System.out.println("");
		System.out.println("******************");
		System.out.println("1.Guardar partida ");
		System.out.println("2.Mover Pieza     ");
		System.out.println("3.Salir           ");
		System.out.println("******************");

	}

	public static String lecturaOpcionMenu() {
		imprimirOpcionesJuego();
		String temp = "";
		temp = in.nextLine();
		if (temp.equals("1") || temp.equals("2") || temp.equals("3")) {

		} else {
			temp = "Opcion invalida, repita.";
		}
		return temp;
	}

	public static void moverPieza(String color) {
		String coordenadaInicial;
		String coordenadaFinal;
		String piezaRetorno = "";

		System.out.println("Ingrese la coordenada donde la pieza se encuentra ubicada:");
		coordenadaInicial = in.nextLine();
		System.out.println("Ingrese la coordenada final:");
		coordenadaFinal = in.nextLine();
		if (verificarPosicion(coordenadaInicial) == true
				&& verificarPosicion(coordenadaFinal) == true) {
			piezaRetorno = retornarObjetoEnPosicion(coordenadaInicial);
			if (color.charAt(0) == piezaRetorno.charAt(1)) {
				colocarPieza(coordenadaInicial, coordenadaFinal, color);

			} else {
				System.out.println(
						"No puedes mover la pieza en la posicion " + coordenadaInicial + " porque no te pertenece.");
			}

		} else {
			System.out.println("Coordenadas incorrectas.");
		}

	}
	
	public static boolean verificarPosicion(String posicionInicial) {
		boolean evaluador = verificarPosicionTablero(posicionInicial);
		if(posicionInicial.equals("") || posicionInicial.length() > 2) {
			evaluador = false;
		}
		return evaluador;
	}
	
	public static boolean verificarPosicionTablero(String x) {
		Pattern pattern = Pattern.compile("{2}^[abcdefgh][12345678]$");
		Matcher matcher = pattern.matcher(x);
		return matcher.matches();		 
	}

	private static void colocarPieza(String coordenadaInicial, String coordenadaFinal, String color) {
		PiezaAjedrez temp = null;
		// Validar que el movimiento sea valido segun la pieza.

		PiezaAjedrez piezaTemp = retornarPiezaPosicion(coordenadaInicial);
		boolean evaluador = validarMovimiento(piezaTemp, coordenadaInicial, coordenadaFinal);

		if (evaluador == true && verificarPiezasDiferenteEquipo(coordenadaInicial, coordenadaFinal) == false) {
			// Remover pieza de posicion inicial.
			for (int i = 0; i < 8; i++) {
				for (int e = 0; e < 8; e++) {
					if (coordenadaInicial.equals(partida.tablero[i][e])) {
						temp = partida.tableroPosiciones[i][e];
						partida.tableroPosiciones[i][e] = new PiezaAjedrez("--", "*", "*", "*");
					}
				}
			}

			// Recolocacion de la pieza.
			for (int i = 0; i < 8; i++) {
				for (int e = 0; e < 8; e++) {
					if (coordenadaFinal.equals(partida.tablero[i][e])) {
						partida.tableroPosiciones[i][e] = temp;
						partida.tableroPosiciones[i][e].sumarMovimiento();
					}
				}
			}
			contador++;
			partida.sumarcontador();
		} else if (evaluador == false) {
			System.out.println("No puedes realizar ese movimiento, vuelvelo a intentar.");
		}

	}

	public static boolean verificarPiezasDiferenteEquipo(String inicial, String ultimo) {
		boolean evaluador = false;
		String valorDeInicial = "";
		String valorDeUltimo = "";

		for (int i = 0; i < 8; i++) {
			for (int e = 0; e < 8; e++) {
				if (inicial.equals(partida.tablero[i][e])) {
					valorDeInicial = partida.tableroPosiciones[i][e].nombre;
				}
				if (ultimo.equals(partida.tablero[i][e])) {
					valorDeUltimo = partida.tableroPosiciones[i][e].nombre;
				}
			}
		}

		if (valorDeUltimo.charAt(1) == valorDeInicial.charAt(1)) {
			evaluador = true;
		} else {
			evaluador = false;
		}

		return evaluador;

	}

	public static boolean validarMovimiento(PiezaAjedrez pieza, String coordenadaInicial, String coordenadaFinal) {
		String codigo = pieza.nombre.charAt(0) + "";
		boolean evaluador = true;
		switch (codigo) {
		case "G":// Peon
			evaluador = Peon.movimientoPeon(coordenadaInicial, coordenadaFinal, pieza);
			break;

		case "R":// Torre
			evaluador = Torre.movimientoTorre(coordenadaInicial, coordenadaFinal);
			break;

		case "K":// Rey
			evaluador = Rey.movimientoRey(coordenadaInicial, coordenadaFinal);
			break;

		case "Q":// Reina
			evaluador = Reina.movimientoReina(coordenadaInicial, coordenadaFinal);
			break;

		case "N":// Caballo
			evaluador = Caballo.movimientoCaballo(coordenadaInicial, coordenadaFinal);
			break;

		case "B":// ALfil
			evaluador = Alfil.movimientoAlfil(coordenadaInicial, coordenadaFinal);
			break;

		}

		return evaluador;

	}

	public static String retornarObjetoEnPosicion(String posicion) {
		String retorno = "";
		for (int i = 0; i < 8; i++) {
			for (int e = 0; e < 8; e++) {
				if (posicion.equals(partida.tablero[i][e])) {
					retorno = partida.tableroPosiciones[i][e].nombre;
				}
			}
		}

		return retorno;
	}

	public static PiezaAjedrez[][] retornarTablerojuego() {
		return partida.getTableroPosiciones();
	}

	public static boolean movimientoHorizontal(String inicial, String Final) {
		boolean evaluador = true;
		boolean piezasEnMedio = false;
		String posicionActual = inicial;
		PiezaAjedrez piezaTemp = null;
		String lado = "";

		if (Character.getNumericValue(inicial.charAt(1)) == Character.getNumericValue(Final.charAt(1))) {

			lado = determinarDireccionHorizontal(inicial, Final);

			if (lado.contentEquals("izquierda")) {
				Final = retornarAnteriorColumna(Character.toString(Final.charAt(0))) + Final.charAt(1);
				while (!posicionActual.contentEquals(Final)) {
					posicionActual = retornarSiguienteColumna(Character.toString(posicionActual.charAt(0)))
							+ Character.toString(inicial.charAt(1));
					piezaTemp = retornarPiezaPosicion(posicionActual);
					if (piezaTemp.nombre.contentEquals("--")) {

					} else {
						piezasEnMedio = true;
					}
				}

			} else if (lado.contentEquals("derecha")) {
				Final = retornarSiguienteColumna(Character.toString(Final.charAt(0))) + Final.charAt(1);
				while (!posicionActual.contentEquals(Final)) {
					posicionActual = retornarAnteriorColumna(Character.toString(posicionActual.charAt(0)))
							+ Character.toString(inicial.charAt(1));
					piezaTemp = retornarPiezaPosicion(posicionActual);
					if (piezaTemp.nombre.contentEquals("--")) {

					} else {
						piezasEnMedio = true;
					}
				}

			} else {
				evaluador = false;
			}

			// Evaluacion Final.
			if (piezasEnMedio == true) {
				evaluador = false;
			}

		} else {
			evaluador = false;
		}

		return evaluador;
	}

	public static boolean movimientoLineal(String inicial, String Final) {
		boolean evaluador = true;
		boolean piezasEnMedio = false;
		String columna = Character.toString(inicial.charAt(0));
		PiezaAjedrez piezaTemp = null;
		int contador = 0;
		int posInicial = Character.getNumericValue(inicial.charAt(1));
		int posFinal = Character.getNumericValue(Final.charAt(1));

		if (inicial.charAt(0) == Final.charAt(0)) {

			if (posInicial < posFinal) {
				posInicial = posInicial + 1;
				for (int i = posInicial; i < posFinal; i++) {

					piezaTemp = retornarPiezaPosicion(columna + i);
					if (piezaTemp.nombre.contentEquals("--")) {

					} else {
						piezasEnMedio = true;
					}

					if (contador > 9) {
						i = posFinal;
					}
				}

			} else if (posInicial > posFinal) {
				posInicial = posInicial - 1;
				for (int i = posInicial; i > posFinal; i--) {

					piezaTemp = retornarPiezaPosicion(columna + i);
					if (piezaTemp.nombre.contentEquals("--")) {

					} else {
						piezasEnMedio = true;
					}

					if (contador > 9) {
						i = posFinal;
					}
				}
			} else {
				evaluador = false;
			}

		} else {
			evaluador = false;
		}

		if (piezasEnMedio == true) {
			evaluador = false;
		}

		return evaluador;

	}

	public static boolean calcularPiezasEnMedioDiagonal(String inicial, String Final) {
		boolean evaluador = false;
		boolean verificador = false;
		boolean piezasEnMedio = false;
		boolean sideFound = false;
		int siguiente = 0;
		String posicionTemporal = inicial;
		PiezaAjedrez piezaTemp = null;

		// Seccion de arriba derecha
		while (verificador == false) {
			if (posicionTemporal.contentEquals(Final)) {
				verificador = true;
				sideFound = true;
			} else {
				if (retornarSiguienteColumna(posicionTemporal.charAt(0) + "").contentEquals("NO")) {
					evaluador = false;
					verificador = true;
					piezasEnMedio = false;
				} else {
					siguiente = Character.getNumericValue(posicionTemporal.charAt(1)) + 1;
					posicionTemporal = retornarSiguienteColumna(posicionTemporal.charAt(0) + "") + siguiente;
					piezaTemp = retornarPiezaPosicion(posicionTemporal);
					if (piezaTemp.nombre.contentEquals("--")) {

					} else {
						if (retornarPiezaPosicion(Final).nombre.contentEquals(piezaTemp.nombre)) {

						} else {
							piezasEnMedio = true;
							verificador = true;
						}
					}
				}
			}
		}

		// Seccion de abajo derecha.
		if (sideFound == false) {

			evaluador = false;
			verificador = false;
			piezasEnMedio = false;
			sideFound = false;
			posicionTemporal = inicial;

			while (verificador == false) {
				if (posicionTemporal.contentEquals(Final)) {
					verificador = true;
					sideFound = true;
				} else {
					if (retornarSiguienteColumna(posicionTemporal.charAt(0) + "").contentEquals("NO")) {
						evaluador = false;
						verificador = true;
						piezasEnMedio = false;
					} else {
						siguiente = Character.getNumericValue(posicionTemporal.charAt(1)) - 1;
						posicionTemporal = retornarSiguienteColumna(posicionTemporal.charAt(0) + "") + siguiente;
						piezaTemp = retornarPiezaPosicion(posicionTemporal);
						if (piezaTemp.nombre.contentEquals("--")) {

						} else {
							if (retornarPiezaPosicion(Final).nombre.contentEquals(piezaTemp.nombre)) {

							} else {
								piezasEnMedio = true;
								verificador = true;
							}
						}
					}
				}
			}
		}

		posicionTemporal = inicial;

		// Seccion de arriba izquierda
		if (sideFound == false) {

			evaluador = false;
			verificador = false;
			piezasEnMedio = false;
			sideFound = false;
			posicionTemporal = inicial;

			while (verificador == false) {
				if (posicionTemporal.contentEquals(Final)) {
					verificador = true;
					sideFound = true;
				} else {
					if (retornarAnteriorColumna(posicionTemporal.charAt(0) + "").contentEquals("NO")) {
						evaluador = false;
						verificador = true;
						piezasEnMedio = false;
					} else {
						siguiente = Character.getNumericValue(posicionTemporal.charAt(1)) + 1;
						posicionTemporal = retornarAnteriorColumna(posicionTemporal.charAt(0) + "") + siguiente;
						piezaTemp = retornarPiezaPosicion(posicionTemporal);
						if (piezaTemp.nombre.contentEquals("--")) {

						} else {
							if (retornarPiezaPosicion(Final).nombre.contentEquals(piezaTemp.nombre)) {

							} else {
								piezasEnMedio = true;
								verificador = true;
							}
						}
					}
				}
			}
		}

		posicionTemporal = inicial;

		// seccion de abajo izquierda.
		if (sideFound == false) {

			evaluador = false;
			verificador = false;
			piezasEnMedio = false;
			sideFound = false;
			posicionTemporal = inicial;

			while (verificador == false) {
				if (posicionTemporal.contentEquals(Final)) {
					verificador = true;
					sideFound = true;
				} else {
					if (retornarAnteriorColumna(posicionTemporal.charAt(0) + "").contentEquals("NO")) {
						evaluador = false;
						verificador = true;
						piezasEnMedio = false;
					} else {
						siguiente = Character.getNumericValue(posicionTemporal.charAt(1)) - 1;
						posicionTemporal = retornarAnteriorColumna(posicionTemporal.charAt(0) + "") + siguiente;
						piezaTemp = retornarPiezaPosicion(posicionTemporal);
						if (piezaTemp.nombre.contentEquals("--")) {

						} else {
							if (retornarPiezaPosicion(Final).nombre.contentEquals(piezaTemp.nombre)) {

							} else {
								piezasEnMedio = true;
								verificador = true;
							}
						}
					}
				}
			}
		}

		// evaluacion final.
		if (piezasEnMedio == true || sideFound == false) {
			evaluador = false;
		} else {
			evaluador = true;
		}

		return evaluador;
	}

	public static String determinarDireccionHorizontal(String posicionInicial, String posicionFinal) {
		String lado = "";
		int primero = 0;
		int ultimo = 0;
		int fila = Character.getNumericValue(posicionInicial.charAt(1) - 1);
		for (int i = 0; i < 8; i++) {
			if (posicionInicial.equals(partida.tablero[fila][i]) && ultimo != 1) {
				primero = 1;
			}
			if (posicionFinal.equals(partida.tablero[fila][i]) && primero != 1) {
				ultimo = 1;
			}
		}

		if (primero > ultimo) {
			lado = "izquierda";
		} else if (primero < ultimo) {
			lado = "derecha";
		}

		return lado;
	}

	public static PiezaAjedrez retornarPiezaPosicion(String posicionInicial) {

		PiezaAjedrez piezaTemp = new PiezaAjedrez("--", "*", "*", "*");

		for (int i = 0; i < 8; i++) {
			for (int e = 0; e < 8; e++) {
				if (posicionInicial.equals(partida.tablero[i][e])) {
					piezaTemp = partida.tableroPosiciones[i][e];
				}
			}
		}

		return piezaTemp;

	}

	public static void ImprimirEstadoJuego() {
		
		System.out.print("\n   ");
		
		for(char i: Tablero.LetrasMenuTablero){ //printing letters across the top
			System.out.print("  " + i + "  ");
		}
		System.out.print("\n   ");
		for(int i = 0; i < 8; i++){
			System.out.print(" --- ");
		}

		System.out.print("\n");
		for (int i = 0; i < 8; i++) {
			System.out.print(" " + (i + 1) + " "); //print number on left side
			
			for (int e = 0; e < 8; e++) {
				System.out.print("| "+partida.tableroPosiciones[i][e].nombre + ""+"|");
			}
			System.out.print(" " + (i + 1) + " "); //number on right side
			
			System.out.print("\n   ");//to get next line
			
			for(int j = 0; j < 8; j++){
				System.out.print(" --- ");
			}
			
			System.out.print("\n");
			
		}
		System.out.print("   ");
		for(char i: Tablero.LetrasMenuTablero){ //printing letters across the bottom
			System.out.print("  " + i + "  ");
		}
		System.out.print("\n\n");
		
		
		

	}

	public static String EvaluarGanador() {

		String ganador = "Ninguno";
		boolean evaluadorNegro = false;
		boolean evaluadorBlanco = false;

		for (int i = 0; i < 8; i++) {
			for (int e = 0; e < 8; e++) {
				if (partida.tableroPosiciones[i][e].nombre.contentEquals("KB")) {
					evaluadorBlanco = true;
				} else if (partida.tableroPosiciones[i][e].nombre.contentEquals("KN")) {
					evaluadorNegro = true;
				} else {

				}
			}
		}

		if (evaluadorBlanco == true && evaluadorNegro == true) {

		} else if (evaluadorBlanco == true && evaluadorNegro == false) {
			ganador = "jugador2";
		} else if (evaluadorBlanco == false && evaluadorNegro == true) {
			ganador = "jugador1";
		}

		return ganador;
	}

	public static void guardarPartida() throws FileNotFoundException {

		String json = Serializer.convertirPartidaJSON(1);
		String nombrePartida = "";
		if (json.equals("Default")) {
			System.out.println("No se ha logrado guardar la partida, favor intentar de nuevo.");
		} else {
			System.out.println("Ingrese el nombre de la partida:");
			nombrePartida = in.nextLine();
			PersistenciaTexto.guardarArchivo(nombrePartida, json);
			System.out.println("Partida guardada en la siguiente direccion: C:\\Users\\Public\\Documents\\"
					+ nombrePartida + ".txt");
		}
	}

	public static boolean cargarPartidaArchivoTexto(String tipo) throws IOException {

		boolean evaluador = false;
		
		Tablero temp = PersistenciaTexto.compararJSONAjedrez(partida, tipo);
		if (temp != null){
			partida = (PartidaAjedrez) temp;
			contador = partida.getContador();
			evaluador = true;
		}else {
			evaluador = false;
		}
		
		return evaluador;
	}

	public static String retornarSiguienteColumna(String columnaActual) {

		switch (columnaActual) {
		case "a":
			return "b";
		case "b":
			return "c";
		case "c":
			return "d";
		case "d":
			return "e";
		case "e":
			return "f";
		case "f":
			return "g";
		case "g":
			return "h";
		case "h":
			return "NO";
		default:
			return "NO";
		}
	}

	public static String retornarAnteriorColumna(String columnaActual) {

		switch (columnaActual) {
		case "a":
			return "NO";
		case "b":
			return "a";
		case "c":
			return "b";
		case "d":
			return "c";
		case "e":
			return "d";
		case "f":
			return "e";
		case "g":
			return "f";
		case "h":
			return "g";
		default:
			return "NO";
		}
	}

	// Metodos Get y Set de la clase.

	public static PartidaAjedrez getPartida() {
		return partida;
	}

	public static void setPartida(PartidaAjedrez partida) {
		JuegoAjedrez.partida = partida;
	}
}



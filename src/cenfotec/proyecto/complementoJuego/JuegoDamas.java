package cenfotec.proyecto.complementoJuego;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cenfotec.proyecto.generador.PersistenciaTexto;
import cenfotec.proyecto.generador.Serializer;
import cenfotec.proyecto.tableros.PartidaDamas;
import cenfotec.proyecto.tableros.PiezaDamas;
import cenfotec.proyecto.tableros.Tablero;

public class JuegoDamas extends Juego {

	private static PartidaDamas partida = new PartidaDamas();
	private static Scanner in = new Scanner(System.in);

	public JuegoDamas(String jugador1, String jugador2, String ganador, String perdedor) {
		super(jugador1, jugador2, ganador, perdedor);
	}

	public static void iniciarJuego() {
		boolean verificador = false;
		String lector = "";

		while (verificador == false) {
			int cantNegras = contadorPiezasNegras();
			int cantBlancas = contadorPiezasBlancas();

			if (cantNegras <= 0) {
				verificador = true;
				ImprimirEstadoJuego();
				System.out.println("Ganan los peones Blancos.");
			} else if (cantBlancas <= 0) {
				verificador = true;
				ImprimirEstadoJuego();
				System.out.println("Ganan los peones Negros.");
			} else {

				if (cantNegras == 1 && cantBlancas == 1) {
					verificador = true;
					ImprimirEstadoJuego();
					System.out.println("Empate, ninguno ha ganado.");
				} else {
					ImprimirEstadoJuego();
					imprimirOpcionesJuego();
					// Menu y juego.
					lector = in.nextLine();
					switch (lector) {
					case "1":// mover.
						moverPieza();
						break;
			
					case "2":// Guardar partida.
						try {
							guardarPartida();
						} catch (FileNotFoundException e) {
							System.out.println("La partida no se ha podido guardar.");
						}
						break;
					case "3":// salir.
						verificador = true;
						break;
					default:
						break;
					}
				}
			}
		}
	}

	public static void continuarPartida() {
		iniciarJuego();
	}

	public static void moverPieza() {
		boolean movExtra = false;
		String coordenadaInicial;
		String coordenadaFinal;
		PiezaDamas pieza = null;
		boolean checker = false;
		boolean checkerColor = false;

		if (partida.getContador() % 2 != 0) {
			// Mueven piezas negras.
			System.out.println("Mueven las piezas Negras");
		} else {
			// Mueven piezas blancas.
			System.out.println("Mueven las piezas Blancas");
		}

		System.out.println("Ingrese la coordenada de inicio.");
		coordenadaInicial = in.nextLine();
		System.out.println("Ingrese la coordenada final.");
		coordenadaFinal = in.nextLine();

		if (verificarPosicionTablero(coordenadaInicial) == true && verificarPosicionTablero(coordenadaFinal) == true) {
			// Aqui ingreso la validacion del movimiento segun el tipo de pieza y la pieza
			// que mueva.
			pieza = retornarPiezaPosicion(coordenadaInicial);

			if (partida.getContador() % 2 != 0) {
				// Mueven piezas negras.
				if (pieza.getColor().contentEquals("N")) {
					checkerColor = true;
				}
			} else {
				// Mueven piezas blancas.
				if (pieza.getColor().contentEquals("B")) {
					checkerColor = true;
				}
			}

			if (checkerColor == true) {
				checker = validarMovimientoSegunPieza(pieza, coordenadaInicial, coordenadaFinal);
				if (checker == true) {
					partida.sumarContador();
					intercambiarPiezas(coordenadaInicial, coordenadaFinal);
					if (retornarSiguienteColumna(
							retornarSiguienteColumna(Character.toString(coordenadaInicial.charAt(0))))
									.contentEquals(Character.toString(coordenadaFinal.charAt(0)))
							|| retornarAnteriorColumna(
									retornarAnteriorColumna(Character.toString(coordenadaInicial.charAt(0))))
											.contentEquals(Character.toString(coordenadaFinal.charAt(0)))) {
						while (movExtra == false) {
							movExtra = movimientoExtra(coordenadaFinal);
						}
					}
				}

			} else {
				System.out.println("La pieza no te pertenece.");
			}

		} else {
			System.out.println("Movimiento invalido.");
		}
	}

	private static void intercambiarPiezas(String coordenadaInicial, String coordenadaFinal) {
		PiezaDamas temp = null;

		// Remover pieza de posicion inicial.
		for (int i = 0; i < 10; i++) {
			for (int e = 0; e < 10; e++) {
				if (coordenadaInicial.equals(partida.tablero[i][e])) {
					temp = partida.tableroPiezas[i][e];
					partida.tableroPiezas[i][e] = new PiezaDamas("-", "-", "-", "-");
				}
			}
		}

		// Recolocacion de la pieza.
		for (int i = 0; i < 10; i++) {
			for (int e = 0; e < 10; e++) {
				if (coordenadaFinal.equals(partida.tablero[i][e])) {
					partida.tableroPiezas[i][e] = temp;
				}
				// Convertir pieza en reina si la posicion Final esta en 1 o en X
				if (partida.tablero[i][e].contentEquals(coordenadaFinal)) {
					if (Character.toString(coordenadaFinal.charAt(1)).contentEquals("1")
							|| Character.toString(coordenadaFinal.charAt(1)).contentEquals("X")) {
						partida.tableroPiezas[i][e].convertirEnReina();

					}
				}
			}
		}

	}

	public static boolean movimientoExtra(String Final) {

		boolean checker = false;
		String valorDerechoFrente = "";
		String valorIzquierdoFrente = "";
		String valorDerechoAtras = "";
		String valorIzquierdoAtras = "";

		PiezaDamas temp = retornarPiezaPosicion(Final);
		if (temp.getColor().contentEquals("N") && temp.nombre.contentEquals("P")) {

			valorDerechoFrente = validarMovimientoExtraDelanteroDerecha(Final);
			valorIzquierdoFrente = validarMovimientoExtraDelanteroIzquierda(Final);
			checker = realizarMovimientoExtraPeon(Final, valorDerechoFrente, valorIzquierdoFrente, "Frente");

		} else if (temp.getColor().contentEquals("B") && temp.nombre.contentEquals("P")) {

			valorDerechoAtras = validarMovimientoExtraTraseroDerecha(Final);
			valorIzquierdoAtras = validarMovimientoExtraTraseroIzquierda(Final);
			checker = realizarMovimientoExtraPeon(Final, valorDerechoAtras, valorIzquierdoAtras, "Atras");

		} else if (temp.nombre.contentEquals("R")) {

			valorDerechoFrente = validarMovimientoExtraDelanteroDerecha(Final);
			valorIzquierdoFrente = validarMovimientoExtraDelanteroIzquierda(Final);
			valorDerechoAtras = validarMovimientoExtraTraseroDerecha(Final);
			valorIzquierdoAtras = validarMovimientoExtraTraseroIzquierda(Final);
			checker = realizarMovimientoExtraReina(Final, valorDerechoFrente, valorIzquierdoFrente, valorDerechoAtras,
					valorIzquierdoAtras);

		} else {
			checker = true;
		}

		return checker;
	}

	public static String validarMovimientoExtraDelanteroDerecha(String Final) {
		String posicion = retornarSiguienteColumna(retornarSiguienteColumna(Character.toString(Final.charAt(0))))
				+ retornarSiguienteColumna(retornarSiguienteColumna(Character.toString(Final.charAt(1))));

		return posicion;
	}

	public static String validarMovimientoExtraDelanteroIzquierda(String Final) {
		String posicion = retornarAnteriorColumna(retornarAnteriorColumna(Character.toString(Final.charAt(0))))
				+ retornarSiguienteColumna(retornarSiguienteColumna(Character.toString(Final.charAt(1))));

		return posicion;
	}

	public static String validarMovimientoExtraTraseroDerecha(String Final) {
		String posicion = retornarSiguienteColumna(retornarSiguienteColumna(Character.toString(Final.charAt(0))))
				+ retornarAnteriorColumna(retornarAnteriorColumna(Character.toString(Final.charAt(1))));

		return posicion;
	}

	public static String validarMovimientoExtraTraseroIzquierda(String Final) {
		String posicion = retornarAnteriorColumna(retornarAnteriorColumna(Character.toString(Final.charAt(0))))
				+ retornarAnteriorColumna(retornarAnteriorColumna(Character.toString(Final.charAt(1))));

		return posicion;
	}

	public static boolean realizarMovimientoExtraPeon(String Final, String derecha, String izquierda,
			String direccion) {
		boolean checker = false;
		String posicionEnMedio = "";

		if (derecha.length() < 2 && izquierda.length() < 2) {

		} else {
			if (direccion.contentEquals("Frente")) {
				if (derecha.length() == 2 && izquierda.length() == 2) {
					comprobarMovimientosMultiple(Final, derecha, izquierda, true);
					checker = true;
				} else if (derecha.length() == 2 && izquierda.length() != 2) {
					String posicionTempComida = derecha;
					PiezaDamas tempD = retornarPiezaPosicion(derecha);
					if (tempD.nombre.contentEquals("-")) {
						posicionTempComida = retornarAnteriorColumna(Character.toString(derecha.charAt(0)))
								+ Integer.toString(Character.getNumericValue(derecha.charAt(1)) - 1);
						tempD = retornarPiezaPosicion(posicionTempComida);
						if (!tempD.getColor().contentEquals(retornarPiezaPosicion(Final).getColor())
								&& !tempD.nombre.contentEquals("-")) {
							if (checker == false) {
								checker = movimientoExtra(Final);
							}
						} else {
							checker = true;
						}

					}
				} else if (izquierda.length() == 2 && derecha.length() != 2) {
					String posicionTempComida = izquierda;
					PiezaDamas tempD = retornarPiezaPosicion(izquierda);
					if (tempD.nombre.contentEquals("-")) {
						posicionTempComida = retornarSiguienteColumna(Character.toString(izquierda.charAt(0)))
								+ Integer.toString(Character.getNumericValue(izquierda.charAt(1)) - 1);
						tempD = retornarPiezaPosicion(posicionTempComida);
						if (!tempD.getColor().contentEquals(retornarPiezaPosicion(Final).getColor())
								&& !tempD.nombre.contentEquals("-")) {
							intercambiarPiezas(Final, izquierda);
							if (checker == false) {
								checker = movimientoExtra(Final);
							}
						} else {
							checker = true;
						}

					} else {
						checker = true;
					}

				} else {
					checker = true;
				}
			} else if (direccion.contentEquals("Atras")) {
				if (derecha.length() == 2 && izquierda.length() == 2) {
					comprobarMovimientosMultiple(Final, derecha, izquierda, false);
					checker = true;
				} else if (derecha.length() == 2 && izquierda.length() != 2) {
					
					PiezaDamas tempD = retornarPiezaPosicion(derecha);
					
					if (tempD.nombre.contentEquals("-")) {
						
						posicionEnMedio = retornarAnteriorColumna(Character.toString(derecha.charAt(0)))
								+ retornarSiguienteColumna(Character.toString(derecha.charAt(1)));
						
						tempD = retornarPiezaPosicion(posicionEnMedio);
						
						if(!tempD.nombre.contentEquals("-")
								&& retornarPiezaPosicion(Final).getColor() != tempD.getColor()) {
							intercambiarPiezas(Final, derecha);
							IntercambiarPiezaEnPosicion(posicionEnMedio, new PiezaDamas("-", "-", "-", "-"));
						if (checker == false) {
							checker = movimientoExtra(derecha);
						}
						}else {
							checker = true;
						}
						
					}
				} else if (izquierda.length() == 2 && derecha.length() != 2) {
					
					PiezaDamas tempD = retornarPiezaPosicion(izquierda);
					
					if(tempD.nombre.contentEquals("-")) {
						
						posicionEnMedio = retornarSiguienteColumna(Character.toString(izquierda.charAt(0)))
								+ retornarSiguienteColumna(Character.toString(izquierda.charAt(1)));
						
						tempD = retornarPiezaPosicion(posicionEnMedio);
						
						if (!tempD.nombre.contentEquals("-")
								&& retornarPiezaPosicion(Final).getColor() != tempD.getColor()) {
							intercambiarPiezas(Final, izquierda);
							IntercambiarPiezaEnPosicion(posicionEnMedio, new PiezaDamas("-", "-", "-", "-"));
							if (checker == false) {
								checker = movimientoExtra(izquierda);
							}
						}
					}else {
						checker = true;
					}
					
				} else {
					checker = true;
				}
			}
		}

		return checker;
	}

	public static boolean realizarMovimientoExtraReina(String Final, String derechaF, String izquierdaF,
			String derechaA, String izquierdaA) {
		boolean checker = true;
		
		if(derechaF.length() == 2 && izquierdaF.length() == 2 && derechaA.length() == 2 && izquierdaA.length() == 2) {
			//hay posibilidad de moverse a cualquier direccion.
		}else if(derechaF.length() <2 && izquierdaF.length() < 2 && derechaA.length() <2 && izquierdaA.length() < 2) {
			//No hay ni un solo movimiento que hacer.
		}else if(derechaF.length() == 2 && izquierdaF.length() < 2 && derechaA.length() <2 && izquierdaA.length() < 2) {
			//Solo hay movimiento a la derecha al frente
		}else if(derechaF.length() < 2 && izquierdaF.length() == 2 && derechaA.length() <2 && izquierdaA.length() < 2) {
			//Solo hay movimiento a la izquierda al frente
		}else if(derechaF.length() < 2 && izquierdaF.length() < 2 && derechaA.length() == 2 && izquierdaA.length() < 2) {
			//Solo hay movimiento a la derecha atras.
		}else if(derechaF.length() < 2 && izquierdaF.length() < 2 && derechaA.length() <2 && izquierdaA.length() == 2) {
			//Solo hay movimiento a la izquierda atras.
		}
		
		

		return checker;
	}

	public static void comprobarMovimientosMultiple(String Final, String derecha, String izquierda, boolean direccion) {
		String consultaDireccion = "";
		int valorDerecho = 0;
		int valorIzquierdo = 0;

		if (direccion == true) {
			valorDerecho = Character.getNumericValue(derecha.charAt(1)) - 1;
			valorIzquierdo = Character.getNumericValue(izquierda.charAt(1)) - 1;
		} else if (direccion == false) {
			valorDerecho = Character.getNumericValue(derecha.charAt(1)) + 1;
			valorIzquierdo = Character.getNumericValue(izquierda.charAt(1)) + 1;
		}

		String DerechaTemp = retornarAnteriorColumna(Character.toString(derecha.charAt(0))) + valorDerecho;
		String izquierdaTemp = retornarSiguienteColumna(Character.toString(izquierda.charAt(0))) + valorIzquierdo;
		PiezaDamas comidaDerecha = retornarPiezaPosicion(DerechaTemp);
		PiezaDamas comidaIzquierda = retornarPiezaPosicion(izquierdaTemp);
		String colorActual = retornarPiezaPosicion(Final).getColor();
		boolean derechaChecker = false;
		boolean izquierdaChecker = false;

		if (retornarPiezaPosicion(derecha).nombre.contentEquals("-")) {
			if (!colorActual.contentEquals(comidaDerecha.getColor()) && !comidaDerecha.nombre.contentEquals("-")
					&& !comidaDerecha.getColor().contentEquals("-")) {
				derechaChecker = true;
			}
		}

		if (retornarPiezaPosicion(izquierda).nombre.contentEquals("-")) {
			if (!colorActual.contentEquals(comidaIzquierda.getColor()) && !comidaIzquierda.nombre.contentEquals("-")
					&& !comidaIzquierda.getColor().contentEquals("-")) {
				izquierdaChecker = true;
			}
		}

		if (derechaChecker == true && izquierdaChecker == true) {
			boolean verificadorConsulta = false;
			while (verificadorConsulta == false) {
				System.out.println(
						"Tienes dos opciones para comer una pieza de tu enemigo:\n" + "1.Derecha.\n2.Izquierda.");
				consultaDireccion = in.nextLine();
				if (consultaDireccion.contentEquals("1") || consultaDireccion.contentEquals("2")) {
					verificadorConsulta = true;
				}
			}

			if (consultaDireccion.contentEquals("1")) {
				intercambiarPiezas(Final, derecha);
				IntercambiarPiezaEnPosicion(DerechaTemp, new PiezaDamas("-", "-", "-", "-"));

			} else if (consultaDireccion.contentEquals("2")) {
				intercambiarPiezas(Final, izquierda);
				IntercambiarPiezaEnPosicion(izquierdaTemp, new PiezaDamas("-", "-", "-", "-"));
			}

		} else if (derechaChecker == true) {
			intercambiarPiezas(Final, derecha);
			IntercambiarPiezaEnPosicion(DerechaTemp, new PiezaDamas("-", "-", "-", "-"));
		} else if (izquierdaChecker == true) {
			intercambiarPiezas(Final, izquierda);
			IntercambiarPiezaEnPosicion(izquierdaTemp, new PiezaDamas("-", "-", "-", "-"));
		}

	}

	public static void IntercambiarPiezaEnPosicion(String posicion, PiezaDamas reemplazo) {

		for (int i = 0; i < 10; i++) {
			for (int e = 0; e < 10; e++) {
				if (posicion.equals(partida.tablero[i][e])) {
					partida.tableroPiezas[i][e] = reemplazo;
				}
			}
		}

	}

	public static boolean validarMovimientoSegunPieza(PiezaDamas pieza, String inicio, String Final) {
		boolean checker = false;

		switch (Character.toString(pieza.nombre.charAt(0))) {

		case "P":
			checker = validarMovimientoPeon(pieza.getColor(), inicio, Final);
			break;

		case "R":
			checker = validarMovimientoReina(inicio, Final);
			break;

		default:
			checker = false;
			break;
		}

		return checker;
	}

	public static boolean validarMovimientoPeon(String color, String inicial, String Final) {
		boolean checker = false;

		if (color.contentEquals("N")) {
			checker = validarMovimientoSimple(inicial, Final);
		} else if (color.contentEquals("B")) {
			checker = validarMovimientoSimpleTrasero(inicial, Final);
		}

		return checker;
	}

	public static boolean validarMovimientoSimple(String inicial, String Final) {
		boolean checker = false;
		String movimiento = "";
		PiezaDamas piezaTemp = null;

		// piezas Negras.

		// Movimiento derecho frontal de una posicion
		movimiento = retornarSiguienteColumna(Character.toString(inicial.charAt(0)))
				+ retornarSiguienteColumna(Character.toString(inicial.charAt(1)));

		if (Final.contentEquals(movimiento)) {

			piezaTemp = retornarPiezaPosicion(Final);
			if (piezaTemp.nombre.contentEquals("-")) {
				checker = true;
			}

		} else {
			// Movimiento izquierdo frontal de una posicion
			movimiento = retornarAnteriorColumna(Character.toString(inicial.charAt(0)))
					+ retornarSiguienteColumna(Character.toString(inicial.charAt(1)));
			if (Final.contentEquals(movimiento)) {
				piezaTemp = retornarPiezaPosicion(Final);
				if (piezaTemp.nombre.contentEquals("-")) {
					checker = true;
				}
			} else {

				// Movimiento derecho frontal de 3 posiciones
				movimiento = retornarSiguienteColumna(retornarSiguienteColumna(Character.toString(inicial.charAt(0))))
						+ retornarSiguienteColumna(retornarSiguienteColumna(Character.toString(inicial.charAt(1))));
				if (Final.contentEquals(movimiento)) {
					piezaTemp = retornarPiezaPosicion(Final);
					if (piezaTemp.nombre.contentEquals("-")) {
						movimiento = retornarSiguienteColumna(Character.toString(inicial.charAt(0)))
								+ (retornarSiguienteColumna(Character.toString(inicial.charAt(1))));
						piezaTemp = retornarPiezaPosicion(movimiento);
						if (!piezaTemp.nombre.contentEquals("-")) {
							// Aqui meto la validacion del color para comer las piezas.
							if (!piezaTemp.getColor().contentEquals(retornarPiezaPosicion(inicial).getColor())) {
								removerPieza(movimiento);
								checker = true;
							}
						} else {
							checker = false;
						}

					}
				} else {

					// Movimiento izquierdo frontal de una posicion
					movimiento = retornarAnteriorColumna(retornarAnteriorColumna(Character.toString(inicial.charAt(0))))
							+ retornarSiguienteColumna(retornarSiguienteColumna(Character.toString(inicial.charAt(1))));
					if (Final.contentEquals(movimiento)) {
						// 33 15 medio = 24
						piezaTemp = retornarPiezaPosicion(Final);
						if (piezaTemp.nombre.contentEquals("-")) {
							movimiento = retornarAnteriorColumna(Character.toString(inicial.charAt(0)))
									+ (retornarSiguienteColumna(Character.toString(inicial.charAt(1))));
							piezaTemp = retornarPiezaPosicion(movimiento);
							if (!piezaTemp.nombre.contentEquals("-")) {
								// Introducir aqui la validacion, para comer la pieza.
								if (!piezaTemp.getColor().contentEquals(retornarPiezaPosicion(inicial).getColor())) {
									removerPieza(movimiento);
									checker = true;
								}
							} else {
								checker = false;
							}
						}
					}
				}
			}
		}

		return checker;
	}

	public static boolean validarMovimientoSimpleTrasero(String inicial, String Final) {
		boolean checker = false;
		String movimiento = "";
		PiezaDamas piezaTemp = null;

		// piezas Blancas.
		// Movimiento derecho frontal de una posicion
		movimiento = retornarSiguienteColumna(Character.toString(inicial.charAt(0)))
				+ retornarAnteriorColumna(Character.toString(inicial.charAt(1)));

		if (Final.contentEquals(movimiento)) {

			piezaTemp = retornarPiezaPosicion(Final);
			if (piezaTemp.nombre.contentEquals("-")) {
				checker = true;
			}

		} else {
			// Movimiento izquierdo frontal de una posicion
			movimiento = retornarAnteriorColumna(Character.toString(inicial.charAt(0)))
					+ retornarAnteriorColumna(Character.toString(inicial.charAt(1)));
			if (Final.contentEquals(movimiento)) {
				piezaTemp = retornarPiezaPosicion(Final);
				if (piezaTemp.nombre.contentEquals("-")) {
					checker = true;
				}
			} else {

				// Movimiento derecho frontal de 3 posiciones
				movimiento = retornarSiguienteColumna(retornarSiguienteColumna(Character.toString(inicial.charAt(0))))
						+ retornarAnteriorColumna(retornarAnteriorColumna(Character.toString(inicial.charAt(1))));
				if (Final.contentEquals(movimiento)) {
					piezaTemp = retornarPiezaPosicion(Final);
					// 47 65 medio = 56
					if (piezaTemp.nombre.contentEquals("-")) {
						movimiento = retornarSiguienteColumna(Character.toString(inicial.charAt(0)))
								+ (retornarAnteriorColumna(Character.toString(inicial.charAt(1))));
						piezaTemp = retornarPiezaPosicion(movimiento);
						if (!piezaTemp.nombre.contentEquals("-")) {
							// Validacion para cambiar la pieza.
							if (!piezaTemp.getColor().contentEquals(retornarPiezaPosicion(inicial).getColor())) {
								removerPieza(movimiento);
								checker = true;
							}
						} else {
							checker = false;
						}

					}
				} else {

					// Movimiento izquierdo frontal de una posicion
					movimiento = retornarAnteriorColumna(retornarAnteriorColumna(Character.toString(inicial.charAt(0))))
							+ retornarAnteriorColumna(retornarAnteriorColumna(Character.toString(inicial.charAt(1))));
					// 24 46 38
					if (Final.contentEquals(movimiento)) {
						piezaTemp = retornarPiezaPosicion(Final);
						if (piezaTemp.nombre.contentEquals("-")) {
							movimiento = retornarAnteriorColumna(Character.toString(inicial.charAt(0)))
									+ (retornarAnteriorColumna(Character.toString(inicial.charAt(1))));
							piezaTemp = retornarPiezaPosicion(movimiento);
							if (!piezaTemp.nombre.contentEquals("-")) {

								// Aqui remuevo la pieza que este en movimiento, en caso de que la pieza tenga
								// diferente color
								if (!piezaTemp.getColor().contentEquals(retornarPiezaPosicion(inicial).getColor())) {
									removerPieza(movimiento);
									checker = true;
								}

							} else {
								checker = false;
							}
						}
					}
				}
			}
		}

		return checker;
	}

	private static void removerPieza(String posicion) {

		PiezaDamas temp = new PiezaDamas("-", "-", "-", "-");

		for (int i = 0; i < 10; i++) {
			for (int e = 0; e < 10; e++) {
				if (partida.tablero[i][e].contentEquals(posicion)) {
					partida.tableroPiezas[i][e] = temp;
				}
			}
		}
	}

	public static boolean validarMovimientoReina(String inicial, String Final) {
		boolean checker = false;

		checker = validarMovimientoSimple(inicial, Final);

		if (checker == false) {
			checker = validarMovimientoSimpleTrasero(inicial, Final);
		}

		return checker;
	}

	public static String retornarSiguienteColumna(String columnaActual) {

		switch (columnaActual) {
		case "1":
			return "2";
		case "2":
			return "3";
		case "3":
			return "4";
		case "4":
			return "5";
		case "5":
			return "6";
		case "6":
			return "7";
		case "7":
			return "8";
		case "8":
			return "9";
		case "9":
			return "X";
		case "X":
			return "NO";
		default:
			return "NO";
		}
	}

	public static String retornarAnteriorColumna(String columnaActual) {

		switch (columnaActual) {
		case "1":
			return "NO";
		case "2":
			return "1";
		case "3":
			return "2";
		case "4":
			return "3";
		case "5":
			return "4";
		case "6":
			return "5";
		case "7":
			return "6";
		case "8":
			return "7";
		case "9":
			return "8";
		case "X":
			return "9";
		default:
			return "NO";
		}
	}

	public static PiezaDamas retornarPiezaPosicion(String posicionInicial) {

		PiezaDamas piezaTemp = new PiezaDamas("-", "-", "-", "-");

		for (int i = 0; i < 10; i++) {
			for (int e = 0; e < 10; e++) {
				if (posicionInicial.equals(partida.tablero[i][e])) {
					piezaTemp = partida.tableroPiezas[i][e];
				}
			}
		}

		return piezaTemp;

	}

	public static String retornarObjetoEnPosicion(String posicion) {
		String retorno = "";
		for (int i = 0; i < 10; i++) {
			for (int e = 0; e < 10; e++) {
				if (posicion.equals(partida.tablero[i][e])) {
					retorno = partida.tableroPiezas[i][e].nombre;
				}
			}
		}
		return retorno;
	}

	public static boolean verificarPosicionTablero(String x) {
		Pattern pattern = Pattern.compile("{2}^[123456789X][123456789X]$");
		Matcher matcher = pattern.matcher(x);
		return matcher.matches();
	}

	public static void imprimirOpcionesJuego() {
		System.out.println("");
		System.out.println("|***********************|");
		System.out.println("|1.Mover Pieza		|");
		System.out.println("|2.Guardar partida	|");
		System.out.println("|3.Salir         	|");
		System.out.println("|***********************|");

	}

	public static int contadorPiezasNegras() {
		int contador = 0;

		for (int i = 0; i < 10; i++) {
			for (int e = 0; e < 10; e++) {
				if (partida.tableroPiezas[i][e].getColor().contentEquals("N")) {
					contador++;
				}
			}
		}

		return contador;
	}

	public static int contadorPiezasBlancas() {
		int contador = 0;

		for (int i = 0; i < 10; i++) {
			for (int e = 0; e < 10; e++) {
				if (partida.tableroPiezas[i][e].getColor().contentEquals("B")) {
					contador++;
				}
			}
		}

		return contador;
	}

	public static void ImprimirEstadoJuego() {
		
		
		
		System.out.println("\u001b[35;1m|*******************************|");
		System.out.println("|    Movimientos Sugeridos      |");
		System.out.println("|*******************************|");
		System.out.println();

		for (int i = 0; i < 10; i++) {
			System.out.print("  ");
			for (int e = 0; e < 10; e++) {
				System.out.print(partida.tablero[i][e] + " ");
			}
			
			
			System.out.println();
		}

		System.out.println("|*******************************|");
		System.out.println("|           DAMAS TABLERO       |");
		System.out.println("|*******************************|");
		System.out.print("\n   ");
		
		for(char i: Tablero.LetrasMenuTableroD){
			System.out.print("  " + i + "  ");
		}
		System.out.print("\n   ");
		for(int i = 0; i < 10; i++){
			System.out.print(" --- ");
		}

		System.out.print("\n");
		for (int i = 0; i < 10; i++) {
			System.out.print(" " + (10 - i) + " "); //print number on left side
			
			for (int e = 0; e < 10; e++) {
				System.out.print("| "+partida.tableroPiezas[i][e].nombre + partida.tableroPiezas[i][e].getColor() + " ");
			}
			System.out.print(" " + (10 - i) + " "); //number on right side
			
			System.out.print("\n   ");//to get next line
			
			for(int j = 0; j < 10; j++){
				System.out.print(" --- ");
			}
			
			System.out.print("\n");
			
		}
		System.out.print("   ");
		for(char i: Tablero.LetrasMenuTableroD){ //printing letters across the bottom
			System.out.print("  " + i + "  ");
		}

	}

	public static void guardarPartida() throws FileNotFoundException {

		String json = Serializer.convertirPartidaJSON(2);
		String nombrePartida = "";
		if (json.equals("Default")) {
			System.out.println("Upps, no se ha logrado convertir la partida en formato JSON.");
		} else {
			System.out.println("Ingrese el nombre de la partida:");
			nombrePartida = in.nextLine();
			PersistenciaTexto.guardarArchivo(nombrePartida, json);
			System.out.println("Partida guardada en la siguiente direccion: C:\\Users\\Public\\Documents\\"
					+ nombrePartida + ".txt");
		}
	}

	public static PartidaDamas getPartida() {
		return partida;
	}

	public static void setPartida(PartidaDamas partida) {
		JuegoDamas.partida = partida;
	}

	public static boolean cargarPartidaArchivoTexto(String tipo) throws IOException {

		boolean checker = false;

		Tablero temp = PersistenciaTexto.compararJSONDamas(partida, tipo);
		if (temp != null) {
			partida = (PartidaDamas) temp;
			checker = true;
		} else {
			checker = false;
		}

		return checker;
	}

	public static String retornarLogo(String pieza) {

		String unicodeMessage = pieza;

		switch (unicodeMessage) {
		case "PN":
			unicodeMessage = "\u25C9";
			break;
		case "PB":
			unicodeMessage = "\u25CE";
			break;
		case "RN":
			unicodeMessage = "\u25D5";
			break;
		case "RB":
			unicodeMessage = "\u25D4";
			break;
		case "--":
			unicodeMessage = "\u25A2";
			break;
		default:
			unicodeMessage = "\u25EB";
			break;

		}
		return unicodeMessage;
	}

	public static String retornarLogoColor(String color) {

		String unicodeMessage = color;

		switch (unicodeMessage) {
		case "NR":
			unicodeMessage = "\u25A1";
			break;
		case "BL":
			unicodeMessage = "\u25A0";
			break;

		}
		return unicodeMessage;

	}

}

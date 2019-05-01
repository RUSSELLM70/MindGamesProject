package cenfotec.proyecto.componentesUI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import cenfotec.proyecto.gestores.Gestor;
import cenfotec.proyecto.gestores.GestorAjedrez;
import cenfotec.proyecto.gestores.GestorDamas;
import cenfotec.proyecto.gestores.GestorGo;
import cenfotec.proyecto.utilitarios.enums.TipoJuego;

public class ComponentesMenu {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public void InicioJuego() throws IOException {	
		boolean noSalir = true;
		boolean subMenu = true;
		
		do {
			imprimirMenu(menuPrincipal);
			int opcionMenu = leerOpcion();
			int opcionUsuario;
			
			do {
				imprimirMenu(menuJuego);
				opcionUsuario = leerOpcion();
				switch (opcionMenu) {
					case 1:
						subMenu = iniciarComandoJuego(opcionUsuario, TipoJuego.DAMAS);
						break;
					case 2:
						subMenu = iniciarComandoJuego(opcionUsuario, TipoJuego.AJEDREZ);
						break;
					case 3:
						subMenu = iniciarComandoJuego(opcionUsuario, TipoJuego.GO);	
						break;
					case 4:
						noSalir= false;
						break;
			}	
			}while (subMenu);
			
		} while (noSalir);	
	}
	
	public static void imprimirMenu(String[] opcionesMenu) {
		for (int i = 0; i < opcionesMenu.length; i++) {
			System.out.println(opcionesMenu[i]);
		}
	}

	public static int leerOpcion() throws IOException {
		int valorIngresado = Integer.parseInt(in.readLine());
		
		return valorIngresado;
	}

	public boolean iniciarComandoJuego (int pOpcionMenuJuego, TipoJuego pJuegoObjetivo) throws IOException {
		boolean noSalirJuego = false; 
		Gestor conexionJuego = FabricaGestor.obtenerJuego(pJuegoObjetivo);
		
		switch (pOpcionMenuJuego) {
			case 1:
				conexionJuego.iniciarPartida();
				break;
			case 2:
				conexionJuego.cargarPartida();
				break;
			case 3:
				conexionJuego.continuarPartida();
				break;
			case 4:
				conexionJuego.guardarPartida();
				break;
			case 5:
				noSalirJuego = false;
				break;
		}
		
		return noSalirJuego;
	}
	
	private static String[] menuPrincipal= {"\u001b[32m|------------------------|\n"
			+ "|  Bienvedido a MindGames|\n"
			+ "|   al juego de mesa     |\n"
			+ "|------------------------|\u001b[0m",
              "\u001b[33m***********************",
              "|    1. Damas         |", 
              "|    2. Ajedrez       |", 
              "|    3. Go            |", 
              "|    4. Salir         |",
              "***********************\u001b[0m"};

	private static String[] menuJuego= {"\u001b[35;1m1. Iniciar partida.", 
            "2. Cargar partida.", 
            "3. Reanudar partida." , 
            "4. Descargar partida actual." ,
            "5. Salir." + "\n",
            "POR FAVOR, SELECCIONE UNA OPCION: " };
}

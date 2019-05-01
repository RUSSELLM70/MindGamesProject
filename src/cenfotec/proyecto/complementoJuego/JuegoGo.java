package cenfotec.proyecto.complementoJuego;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import cenfotec.proyecto.generador.PersistenciaTexto;
import cenfotec.proyecto.generador.Serializer;
import cenfotec.proyecto.tableros.PartidaGo;
import cenfotec.proyecto.tableros.Tablero;

public class JuegoGo extends Juego{

	private static PartidaGo partida = new PartidaGo();
	private static Scanner in = new Scanner(System.in);
	
	public JuegoGo(String jugador1, String jugador2, String ganador, String perdedor) {
		super(jugador1, jugador2, ganador, perdedor);
	}

	public static void ImprimirEstadoJuego() {
		System.out.println("           |-------------------------------|");
		System.out.println("           |          Coordenadas          |");
		System.out.println("           |-------------------------------|");
		System.out.println();
		PartidaGo test = new PartidaGo();
		for (int i = 0; i < 19; i++) {
			System.out.print("  ");
			for (int e = 0; e < 19; e++) {
				System.out.print(test.tablero[i][e] + " ");
			}
			System.out.println();
		}
	}
	
	public static void guardarPartida() throws FileNotFoundException {
		
		String json = Serializer.convertirPartidaJSON(3);
		String nombrePartida = "";
		if(json.equals("Default")) {
			System.out.println("Upps, no se ha logrado convertir la partida en formato JSON.");
		}else {
			System.out.println("Ingrese el nombre de la partida:");
			nombrePartida = in.nextLine();
			PersistenciaTexto.guardarArchivo(nombrePartida, json);
			System.out.println("Partida guardada en la siguiente direccion: C:\\Users\\Public\\Documents\\"+nombrePartida+".txt");
		}
	}
	
	public static PartidaGo getPartida() {
		return partida;
	}

	public static void setPartida(PartidaGo partida) {
		JuegoGo.partida = partida;
	}

	public static boolean cargarPartidaArchivoTexto(String tipo) throws IOException {

		boolean checker = false;
		
		Tablero temp = PersistenciaTexto.compararJSONAjedrez(partida, tipo);
		if (temp != null){
			partida = (PartidaGo) temp;
			checker = true;
		}else {
			checker = false;
		}
		
		return checker;
	}
	
}

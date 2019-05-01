package cenfotec.proyecto.generador;

import com.google.gson.Gson;

import cenfotec.proyecto.complementoJuego.JuegoAjedrez;
import cenfotec.proyecto.complementoJuego.JuegoDamas;
import cenfotec.proyecto.complementoJuego.JuegoGo;
import cenfotec.proyecto.tableros.PartidaAjedrez;
import cenfotec.proyecto.tableros.PartidaDamas;
import cenfotec.proyecto.tableros.PartidaGo;
import cenfotec.proyecto.tableros.Tablero;

public class Serializer {

	//Deserializacion.
	
	public static PartidaAjedrez convertirJSONPartidaAjedrez(String temp) {
		Gson gson = new Gson();
		return gson.fromJson(temp, PartidaAjedrez.class);
	}
	
	public static PartidaGo convertirJSONPartidaGo(String temp) {
		Gson gson = new Gson();
		return gson.fromJson(temp, PartidaGo.class);
	}
	
	public static PartidaDamas convertirJSONPartidaDamas(String temp) {
		Gson gson = new Gson();
		return gson.fromJson(temp, PartidaDamas.class);
	}
	
	//Serializacion.
	
	
	public static String convertirPartidaJSON(int tipoJuego) {
		String partidaTemp = "Default";
		Gson gson = new Gson();
		try{
			partidaTemp = gson.toJson(retornarPartida(tipoJuego));
		}catch(Exception e) {
			System.out.println(e);
		}
		
		return partidaTemp;
	}
	
	
	
	public static Tablero retornarPartida(int tipoJuego) {
		
		Tablero partida = null;
		switch(tipoJuego) {
		case 1:
			partida = JuegoAjedrez.getPartida();
			break;
		case 2:
			partida = JuegoDamas.getPartida();
			break;
		case 3:
			partida = JuegoGo.getPartida();
			break;
		}
		
		return partida;
	}
	
	
	
}

package cenfotec.proyecto.tableros;

public class PartidaAjedrez extends Tablero {
	protected static int contador = 2;

	public PiezaAjedrez[][] tableroPosiciones;

	public PartidaAjedrez() {

		this.tipoJuego = "Ajedrez";
		this.tablero = new String[8][8];
		tableroPosiciones = new PiezaAjedrez[8][8];
		String letra = "";
		//Se agregan las coordenadas del tablero.
		for(int i=0;i<8;i++) {
			letra = PartidaAjedrez.retornarLetraCordenada(i);
			for(int e=0;e<8;e++) {
				tablero[e][i] = letra+(e+1);
			}
			
		}
		//Se agregan las piezas y sus respectivas posiciones iniciales.
		for(int i=0;i<8;i++) {
			for(int e=2;e<6;e++) {
				tableroPosiciones[e][i] = new PiezaAjedrez("--", "*", "*", "*");
			}
		}
		//Ciclos for para agregar los peones
		for(int i=0;i<8;i++) {
			PiezaAjedrez peon = new PiezaAjedrez("GN", "Jugador1", "peon", "Negro");
			tableroPosiciones[1][i] = peon;
		}
		for(int i=0;i<8;i++) {
			PiezaAjedrez peon = new PiezaAjedrez("GB", "Jugador2", "peon", "Blanco");
			tableroPosiciones[6][i] = peon;
		}
		//Se agregan las piezas principales del juego
		tableroPosiciones[0][0] = retonarPiezaSegunPosicion(0, "torre");
		tableroPosiciones[0][1] = retonarPiezaSegunPosicion(0, "caballo");
		tableroPosiciones[0][2] = retonarPiezaSegunPosicion(0, "alfil");
		tableroPosiciones[0][3] = retonarPiezaSegunPosicion(0, "rey");
		tableroPosiciones[0][4] = retonarPiezaSegunPosicion(0, "reina");
		tableroPosiciones[0][5] = retonarPiezaSegunPosicion(0, "alfil");
		tableroPosiciones[0][6] = retonarPiezaSegunPosicion(0, "caballo");
		tableroPosiciones[0][7] = retonarPiezaSegunPosicion(0, "torre");
		
		tableroPosiciones[7][0] = retonarPiezaSegunPosicion(7, "torre");
		tableroPosiciones[7][1] = retonarPiezaSegunPosicion(7, "caballo");
		tableroPosiciones[7][2] = retonarPiezaSegunPosicion(7, "alfil");
		tableroPosiciones[7][3] = retonarPiezaSegunPosicion(7, "rey");
		tableroPosiciones[7][4] = retonarPiezaSegunPosicion(7, "reina");
		tableroPosiciones[7][5] = retonarPiezaSegunPosicion(7, "alfil");
		tableroPosiciones[7][6] = retonarPiezaSegunPosicion(7, "caballo");
		tableroPosiciones[7][7] = retonarPiezaSegunPosicion(7, "torre");
		
	}

	public static String retornarLetraCordenada(int columna) {

		String letra = "";
		switch (columna) {

		case 0:
			letra = "a";
			break;
		case 1:
			letra = "b";
			break;
		case 2:
			letra = "c";
			break;
		case 3:
			letra = "d";
			break;
		case 4:
			letra = "e";
			break;
		case 5:
			letra = "f";
			break;
		case 6:
			letra = "g";
			break;
		case 7:
			letra = "h";
			break;
		}
		return letra;
	}
	
	public static PiezaAjedrez retonarPiezaSegunPosicion(int fila, String tipoPieza) {
		
		String color = "";
		String tipoColor = "";
		String jugador = "";
		String nombre = "";
		String pieza = tipoPieza;
		
		if(fila==0) {
			color = "negro";
			tipoColor = "N";
			jugador = "Jugador 1";
		}else {
			color = "blanco";
			tipoColor = "B";
			jugador = "Jugador 2";
		}
		
		switch(tipoPieza){
			//revisar con el profesor, porque el caballo esta mal nombrado, en ingles la letra asociada es N y para el rey es K.
			case "rey":
				nombre = "K"+tipoColor;
				break;
			case "reina":
				nombre = "Q"+tipoColor;
				break;
			case "caballo":
				nombre = "N"+tipoColor;
				break;
			case "alfil":
				nombre = "B"+tipoColor;
				break;
			case "torre":
				nombre = "R"+tipoColor;
				break;
			
		}
		
		
		PiezaAjedrez piezaRetorno = new PiezaAjedrez(nombre, jugador, pieza, color);
		return piezaRetorno;
	}

	public PiezaAjedrez[][] getTableroPosiciones() {
		return tableroPosiciones;
	}

	public void setTableroPosiciones(PiezaAjedrez[][] tableroPosiciones) {
		this.tableroPosiciones = tableroPosiciones;
	}

	
	public int getContador() {
		return contador;
	}
	

	public void setContador(int contador) {
		this.contador = contador;
	}

	public void resetearContador() {
		contador = 2;
	}

	public void sumarcontador() {
		contador++;
	}
	
	public void restarcontador() {
		contador--;
	}

	@Override
	public void ImprimirEstadoJuego() {
		// TODO Auto-generated method stub
		
	}
}

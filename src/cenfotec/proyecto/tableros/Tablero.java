package cenfotec.proyecto.tableros;

public abstract class Tablero {

	public String[][] tablero;
	public String tipoJuego;
	

	public String[][] getTablero() {
		return tablero;
	}
	public void setTablero(String[][] tablero) {
		this.tablero = tablero;
	}
	public String getTipoJuego() {
		return tipoJuego;
	}
	public void setTipoJuego(String tipoJuego) {
		this.tipoJuego = tipoJuego;
	}
	
	
	public static final char LetrasMenuTablero[] = {'A','B','C','D','E','F','G','H'};
	public static final char LetrasMenuTableroD[] = {'A','B','C','D','E','F','G','H','I','J'};

	public final static int NumerosMenuTablero[] = {1,2,3,4,5,6,7,8};//symbols on the side of tablero
	public abstract void ImprimirEstadoJuego();
	}


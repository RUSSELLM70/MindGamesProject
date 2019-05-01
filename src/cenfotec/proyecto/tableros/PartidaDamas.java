package cenfotec.proyecto.tableros;

public class PartidaDamas extends Tablero {

	public String[][] tableroColores = new String[10][10];
	public PiezaDamas[][] tableroPiezas = new PiezaDamas[10][10];
	private int contador = 2;

	public PartidaDamas() {

		this.tipoJuego = "Damas";
		this.tablero = new String[10][10];

		// Se agregan las coordenadas del tablero.
		for (int i = 0; i < 10; i++) {
			for (int e = 0; e < 10; e++) {
				if ((i + 1) == 10 && (e + 1) == 10) {
					tablero[e][i] = ("X") + ("X");
				} else if ((i + 1) == 10) {
					tablero[e][i] = ("X") + (e + 1);
				} else if ((e + 1) == 10) {
					tablero[e][i] = (i + 1) + ("X");
				} else {
					tablero[e][i] = (i + 1) + "" + (e + 1);
				}

			}

		}
		String color = "BL";
		// Se agregan las coordenadas del tablero.
		for (int i = 0; i < 10; i++) {
			for (int e = 0; e < 10; e++) {
				color = devolverColorOpuesto(color);
				tableroColores[e][i] = color;
			}
			color = devolverColorOpuesto(color);
		}
		
		
		
		//Ciclo for para colocar las piezas blancas.
		PiezaDamas temporal = null;
		for (int i = 0; i < 10; i++) {
			for (int e = 0; e < 10; e++) {
				//String nombre, String jugador, String movimiento, String color
				if(tableroColores[i][e].equals("BL")) {
					temporal = devolverPieza(4);
				}else {
					temporal = devolverPieza(i);
				}
				tableroPiezas[i][e] = temporal;
				
			}
		}
		
	}
	
	public String devolverColorOpuesto(String color) {
		if (color.equals("BL")) {
			color = "NR";//"\u26AB";
		} else if (color.equals("NR")) {
			color = "BL";//"\u25EF";
		}
		return color;
	}
	
	public PiezaDamas devolverPieza(int fila) {
		PiezaDamas temporal=null;
		if(fila>=0 && fila <=3) {
			temporal = new PiezaDamas("P","N","P","N");
		}else if(fila>=4 && fila <=5) {
			temporal = new PiezaDamas("-","-","-","-");
		}else if(fila>=6) {
			temporal = new PiezaDamas("P","B","P","B");
		}
		return temporal;
	}

	public String[][] getTableroColores() {
		return tableroColores;
	}

	public void setTableroColores(String[][] tableroColores) {
		this.tableroColores = tableroColores;
	}

	public PiezaDamas[][] getTableroPiezas() {
		return tableroPiezas;
	}

	public void setTableroPiezas(PiezaDamas[][] tableroPiezas) {
		this.tableroPiezas = tableroPiezas;
	}

	public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}
	
	public void resetContador() {
		this.contador = 2;
	}
	
	public void sumarContador() {
		this.contador++;
	}
	
	public void restarContador() {
		this.contador--;
	}

	@Override
	public void ImprimirEstadoJuego() {
		// TODO Auto-generated method stub
		
	}

}

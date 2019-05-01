package cenfotec.proyecto.tableros;

public class PartidaGo extends Tablero {

	public PartidaGo() {

		this.tipoJuego = "Go";
		this.tablero = new String[19][19];

		// Se agregan las coordenadas del tablero.
		for (int i = 0; i < 19; i++) {
			for (int e = 0; e < 19; e++) {
				tablero[e][i] = retornarValorLetra(i + 1) + retornarValorLetra(e + 1);
			}

		}

	}

	public String retornarValorLetra(int numero) {
		String letra = numero + "";
		if (numero >= 10) {
			switch (numero) {

			case 10:
				letra = "X";
				break;
			case 11:
				letra = "E";
				break;
			case 12:
				letra = "T";
				break;
			case 13:
				letra = "N";
				break;
			case 14:
				letra = "F";
				break;
			case 15:
				letra = "G";
				break;
			case 16:
				letra = "H";
				break;
			case 17:
				letra = "Y";
				break;
			case 18:
				letra = "K";
				break;
			case 19:
				letra = "M";
				break;

			}
		}

		return letra;
	}

	@Override
	public void ImprimirEstadoJuego() {
		// TODO Auto-generated method stub
		
	}

}

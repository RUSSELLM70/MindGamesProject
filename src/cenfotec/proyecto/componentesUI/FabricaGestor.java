package cenfotec.proyecto.componentesUI;

import cenfotec.proyecto.gestores.*;
import cenfotec.proyecto.utilitarios.enums.TipoJuego;

public class FabricaGestor {
	
	public static Gestor obtenerJuego (TipoJuego pJuegoObjetivo) {
		switch (pJuegoObjetivo) {
			case AJEDREZ:
				return new GestorAjedrez();
			case DAMAS:
				return new GestorDamas();
			case GO:
				return new GestorGo();
			default:
				throw new RuntimeException ("Not implemented");
		}
	}

}

package cenfotec.proyecto.componentesUI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import cenfotec.proyecto.gestores.GestorAjedrez;
import cenfotec.proyecto.gestores.GestorDamas;
import cenfotec.proyecto.gestores.GestorGo;

public class UI extends ComponentesMenu{

	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	

	public static void main(String[] args) throws IOException {		
		ComponentesMenu instanciaComponentes = new ComponentesMenu ();
		instanciaComponentes.InicioJuego();
		System.out.println("\n" + "Gracias por usar nuestro sistema.");
	}

}

package cenfotec.proyecto.generador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import cenfotec.proyecto.tableros.PartidaDamas;
import cenfotec.proyecto.tableros.PartidaGo;
import cenfotec.proyecto.tableros.Tablero;


public class PersistenciaTexto {

	private static Scanner in = new Scanner(System.in);
	private static String direccionArchivos = "C:\\Users\\Public\\Documents\\";
	private static File file;
	
	public static boolean guardarArchivo(String nombre, String datos) throws FileNotFoundException {
		
		boolean checker = false;
		direccionArchivos = "C:\\Users\\Public\\Documents\\";
		direccionArchivos = direccionArchivos+nombre+".txt";
		file = new File(direccionArchivos);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(datos);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
		return checker;
	}
	
	public static String leerArchivoTexto(String nombre) throws IOException {
		String lecturaTemp = "";
		String lectura="";
		direccionArchivos = "C:\\Users\\Public\\Documents\\";
		direccionArchivos = direccionArchivos+nombre+".txt";
		FileReader fileReader = new FileReader(direccionArchivos);
		try {
			
			BufferedReader buffer = new BufferedReader(fileReader);
			
			while((lecturaTemp=buffer.readLine())!=null) {
				lectura = lectura + lecturaTemp;
			}
			fileReader.close();
		}catch(Exception e) {
			fileReader.close();
			System.out.println("No  logra leer el archivo.");
		}
		
		return lectura;
	}
	
	public static Tablero compararJSONAjedrez(Tablero partida, String tipo) {
		System.out.println("Ingrese el nombre de la partida:");
		String nombreArchivo = in.nextLine();
		try {
			String temp = PersistenciaTexto.leerArchivoTexto(nombreArchivo);
			partida = Serializer.convertirJSONPartidaAjedrez(temp);
			if(partida.getTipoJuego().equals(tipo)) {
				return partida;
			}else {
				System.out.println("Verifique que el archivo pertenezca a una partida de " + tipo +".");
				return null;
			}
			
		}catch(Exception e) {
			System.out.println("No se ha cargado el archivo, verifique que el archivo exista.");
			return null;
		}
	}
	
	
	public static PartidaDamas compararJSONDamas(PartidaDamas partida, String tipo) {
		System.out.println("Ingrese el nombre de la partida:");
		String nombreArchivo = in.nextLine();
		try {
			String temp = PersistenciaTexto.leerArchivoTexto(nombreArchivo);
			partida = Serializer.convertirJSONPartidaDamas(temp);
			if(partida.getTipoJuego().equals(tipo)) {
				return partida;
			}else {
				System.out.println("Verifique que el archivo pertenezca a una partida de " + tipo +".");
				return null;
			}
			
		}catch(Exception e) {
			System.out.println("No se ha cargado el archivo, verifique que el archivo exista.");
			return null;
		}
	}
	
	
	public static PartidaGo compararJSONGo(PartidaGo partida, String tipo) {
		System.out.println("Ingrese el nombre de la partida:");
		String nombreArchivo = in.nextLine();
		try {
			String temp = PersistenciaTexto.leerArchivoTexto(nombreArchivo);
			partida = Serializer.convertirJSONPartidaGo(temp);
			if(partida.getTipoJuego().equals(tipo)) {
				return partida;
			}else {
				System.out.println("Verifique que el archivo pertenezca a una partida de " + tipo +".");
				return null;
			}
			
		}catch(Exception e) {
			System.out.println("No se ha cargado el archivo, verifique que el archivo exista.");
			return null;
		}
	}
	
	
	
}

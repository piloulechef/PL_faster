package Test;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class test {
	
//	public static void main(String[] args) throws IOException {
//		
//		File mfile = new File("data");
//		if (!mfile.exists()) {
//			throw new IOException("Le fichier saisi : "+ "data" + ", n'existe pas.");
//		}
//		
//		Scanner sc = new Scanner(mfile);
//
//		String line;
//		
//		do {
//			line = sc.nextLine();
//			System.err.println(line);
//			System.out.println(line);
//		}
//		while (!line.startsWith("NOMBRE_DE_JOBS"));
//		Scanner lineSc = new Scanner(line);
//		lineSc.next();
//		if (!lineSc.hasNextInt()) { 
//			lineSc.next();
//		}
//		//n =lineSc.nextInt();
//		System.out.println("n = " + lineSc.nextInt());
//		
////		coordX = new double[nbSommets];
////		coordY = new double[nbSommets];
////		labels = new String[nbSommets];
////		demande = new int[nbSommets];
//
//		while (!line.startsWith("NOMBRE_DE_MACHINES_M")) {
//			line = sc.nextLine();
//			System.err.println(line);
//		}
//		lineSc.close();
//		lineSc = new Scanner(line);
//		lineSc.next();
//		if (!lineSc.hasNextInt()) {
//			lineSc.next();
//		}
//		//M = lineSc.nextInt();
//		System.out.println("M = " + lineSc.nextInt() );
//		//capaVehicule = new int[nbVehicules];
//		
//
//		while (!line.startsWith("NOMBRE_DE_MACHINES_F")) {
//			line = sc.nextLine();
//			System.err.println(line);
//		}
//		lineSc.close();
//		lineSc = new Scanner(line);
//		lineSc.next();
//		if (!lineSc.hasNextInt()) {
//			lineSc.next();
//		}
//		//int F = lineSc.nextInt();
//		System.out.println("F = " + lineSc.nextInt() );
//		//capaVehicule = new int[nbVehicules];
//		
//
//		while (!line.startsWith("NOMBRE_DE_NIVEAUX")) {
//			line = sc.nextLine();
//			System.err.println(line);
//		}
//		lineSc.close();
//		lineSc = new Scanner(line);
//		lineSc.next();
//		if (!lineSc.hasNextInt()) {
//			lineSc.next();
//		}
//		//L = lineSc.nextInt();
//		System.out.println("L = " + lineSc.nextInt());
//		//capaVehicule = new int[nbVehicules];
//
//		
//		while (!line.startsWith("DUE_DATE")) {
//			line = sc.nextLine();
//			System.err.println(line);
//		}
//		line = sc.nextLine();
//
//		int idx = 0;
//		for (int s=0;s<20;s++){
//			assert(idx<20);//?????????????????
//			lineSc = new Scanner(line);
//			lineSc.useLocale(Locale.US);//??????????????
//			//d[idx] = lineSc.nextDouble();
//			System.out.println("d["+idx+"] = " + lineSc.nextDouble());
//			line = sc.nextLine();
//			idx++;
//		}
//		
//		while (!line.startsWith("PENALITE")) {
//			line = sc.nextLine();
//			System.err.println(line);
//		}
//		line = sc.nextLine();
//		
//		lineSc.close();
//		sc.close();
//	}
//
//}
}
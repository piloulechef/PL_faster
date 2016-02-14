package Test;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class test {
	
	public static void main(String[] args) throws IOException {
		


		File mfile = new File(args[0]);
		if (!mfile.exists()) {
			throw new IOException("Le fichier saisi : "+ args[0] + ", n'existe pas.");
		}
		
		Scanner sc = new Scanner(mfile);

		String line;
		
		do {
			line = sc.nextLine();
			System.err.println(line);
		}
		while (!line.startsWith("NOMBRE_DE_JOBS"));
		Scanner lineSc = new Scanner(line);
		lineSc.next();
		if (!lineSc.hasNextInt()) { 
			lineSc.next();
		}
		//n =lineSc.nextInt();
		System.out.println(lineSc.nextInt());
//		coordX = new double[nbSommets];
//		coordY = new double[nbSommets];
//		labels = new String[nbSommets];
//		demande = new int[nbSommets];

		while (!line.startsWith("NOMBRE_DE_MACHINES_M")) {
			line = sc.nextLine();
			System.err.println(line);
		}
		lineSc.close();
		lineSc = new Scanner(line);
		lineSc.next();
		if (!lineSc.hasNextInt()) {
			lineSc.next();
		}
		int M = lineSc.nextInt();
		//capaVehicule = new int[nbVehicules];
		

		while (!line.startsWith("NOMBRE_DE_MACHINES_F")) {
			line = sc.nextLine();
			System.err.println(line);
		}
		lineSc.close();
		lineSc = new Scanner(line);
		lineSc.next();
		if (!lineSc.hasNextInt()) {
			lineSc.next();
		}
		int F = lineSc.nextInt();
		//capaVehicule = new int[nbVehicules];
		

		while (!line.startsWith("NOMBRE_DE_NIVEAUX")) {
			line = sc.nextLine();
			System.err.println(line);
		}
		lineSc.close();
		lineSc = new Scanner(line);
		lineSc.next();
		if (!lineSc.hasNextInt()) {
			lineSc.next();
		}
		//L = lineSc.nextInt();
		System.out.println(lineSc.nextInt());
		//capaVehicule = new int[nbVehicules];

		
		while (!line.startsWith("DUE_DATE")) {
			line = sc.nextLine();
			
		}
		
		while(!line.startsWith("0")){
			line = sc.nextLine();
			
		}

		int idx = 0;
		for (int s=0;s<=19;s++){
			assert(idx<=19);//?????????????????
			lineSc = new Scanner(line);
			lineSc.next();
			lineSc.useLocale(Locale.US);//??????????????
			//d[idx] = lineSc.nextDouble();
			System.out.println("d["+idx+"] = "+ lineSc.nextInt());
			line = sc.nextLine();
			idx++;
		}
		
		while (!line.startsWith("PENALITE")) {
			line = sc.nextLine();
			System.err.println(line);
		}
		
		while(!line.startsWith("0")){
			line = sc.nextLine();
			
		}
		
		int idx1 = 0;
		for (int s=0;s<=19;s++){
			assert(idx1<=19);//?????????????????
			lineSc = new Scanner(line);
			lineSc.next();
			lineSc.useLocale(Locale.US);//??????????????
			//pen[idx1] = lineSc.nextDouble();
			System.out.println("pen["+idx1+"] = "+lineSc.nextDouble());
			line = sc.nextLine();
			idx1++;
		}
		
		while (!line.startsWith("PROCESSING_TIME")) {
			line = sc.nextLine();
			System.err.println(line);
		}
		
		while (!line.startsWith("NIVEAU_1")) {
			line = sc.nextLine();
			System.err.println(line);
		}
		
		while (!line.startsWith("JOBS")) {
			line = sc.nextLine();
			System.err.println(line);
		}
		
		while (!line.startsWith("M1")) {
			line = sc.nextLine();
			System.err.println(line);
		}
		
		int noMach = 0;
		
		while(noMach<M)
		{
			while (!line.startsWith("0")) {
				line = sc.nextLine();
				
			}
			
			int idx2 = 0;
		
			for (int s=0;s<=19;s++)
			{
				assert(idx2<=19);//?????????????????
				lineSc = new Scanner(line);
				lineSc.next();
				lineSc.useLocale(Locale.US);//??????????????
				//p1[idx2][noMach+1] = lineSc.nextDouble();
				int mach = noMach+1;
				System.out.println("p1["+idx2+"]["+mach+"] = " + lineSc.nextInt());
				line = sc.nextLine();
				idx2++;
				
			}
				
			noMach++;
		}
		
		while (!line.startsWith("NIVEAU_2")) {
			line = sc.nextLine();
			System.err.println(line);
		}
			
		int noMachF = 1;
		
		while(noMachF<=F)
		{
			System.out.println("F" + noMachF);
			String print = "F" + noMachF;
			while(!line.startsWith(print)){	
			line = sc.nextLine();
			}
			
			int idx2 = 0;
		
			while (!line.startsWith("0")) {
				line = sc.nextLine();
				
			}
			
			for (int s=0;s<=19;s++)
			{
				assert(idx2<=19);//?????????????????
				lineSc = new Scanner(line);
				lineSc.next();
				lineSc.useLocale(Locale.US);//??????????????
				//p2[idx2][noMachF-1] = lineSc.nextDouble();
				int nof = noMachF-1;
				System.out.println("p2["+idx2+"]["+nof+"] = " + lineSc.nextInt());
				line = sc.nextLine();
				idx2++;
				
			}
				
			noMachF++;
			
		}
		System.out.println("blkacxlk");
		System.out.println(line);
		//String line2 = sc.nextLine();
		//System.out.println(line2);
		System.out.println(!line.startsWith("SETUP_TIME"));
		
		while (!line.startsWith("SETUP_TIME")) {
			line = sc.nextLine();
			System.err.println(line);
		}
		
		while (!line.startsWith("NIVEAU1")) {
			line = sc.nextLine();
			System.err.println(line);
		}
		
		int noMachM = 1; 
		
		while(noMachM<=M){
			
			while(!line.startsWith("M"+noMachM)){
				line = sc.nextLine();
				System.err.println(line);
				}
				
		
				int noJobs = 0;
		
			while(noJobs<=19)
			{
				System.out.println("JOB_"+noJobs);
				while(!line.startsWith("JOB_"+noJobs))
				{
					line = sc.nextLine();
					
				}
				
				while(!line.startsWith("0"))
				{
					line = sc.nextLine();
					//System.err.println(line);
				}

			int idx2 = 0;
		
			for (int s=0;s<=19;s++)
			{
				
				
				assert(idx2<=19);//?????????????????
				lineSc = new Scanner(line);
				lineSc.next();
				lineSc.useLocale(Locale.US);//??????????????
				//s1[noJobs-1][idx2][noMachM] = lineSc.nextDouble();
				int job=noJobs;
				int mach = noMachM-1;
				System.out.println("s1["+job+"]["+idx2+"]["+mach+"] = " + lineSc.nextInt());
				line = sc.nextLine();
				idx2++;
				
			}
				
			noJobs++;
		}
		noMachM ++ ; 
		}
		
		
		while (!line.startsWith("NIVEAU2")) {
			line = sc.nextLine();
			System.err.println(line);
		}
		line = sc.nextLine();
		
		int nosMachF = 1; 
		
		while(nosMachF<=F){
			
			System.out.println("F"+nosMachF);
			
			while(!line.startsWith("F"+nosMachF)){
				line = sc.nextLine();
				
				}
				line = sc.nextLine();
		
				int noJobs = 0;
		
			while(noJobs<=19)
			{
				System.out.println("JOB_"+noJobs);
				
				while(!line.startsWith("JOB_"+noJobs))
				{
					line = sc.nextLine();
					
				}
			
				while(!line.startsWith("0"))
				{
					line = sc.nextLine();
					
				}
			

			int idx2 = 0;
		
			for (int s=0;s<=19;s++)
			{
				assert(idx2<=19);//?????????????????
				lineSc = new Scanner(line);
				lineSc.next();
				lineSc.useLocale(Locale.US);//??????????????
				//s2[noJobs-1][idx2][nosMachF] = lineSc.nextDouble();
				int job = noJobs;
				System.out.println("s2["+job+"]["+idx2+"]["+nosMachF+"] =" + lineSc.nextDouble());
				line = sc.nextLine();
				idx2++;
				
			}
				
			noJobs++;
	}
		nosMachF ++ ; 
		}
		
		sc.close();
		lineSc.close();
		
	}
}

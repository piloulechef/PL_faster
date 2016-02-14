package Instances;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;



public class InstancesMachines {
		// --------------------------------------------
		// --------------- ATTRIBUTS ------------------
		// --------------------------------------------
		
		/** Nombre de jobs*/
		private int n;
	
		/**Nombre de machine au niveau 1*/
		private int M;
		
		/**Nombre de machine au niveau 2*/
		private int F;
	
		/**big M*/
		private double R;
		
		/**setup time pour le niveau 1*/
		private double[][][] s1;
		
		/**setup time pour le niveau 2*/
		private double[][][] s2;
		
		/**processing time pour les jobs sur niveau 1*/
		private double[][] p1;
		
		/**processing time pour les jobs sur niveau 2*/
		private double[][] p2;
		
		/**due date pour le job*/
		private double[] d;
		
		/**pénalité pour le job*/
		private double[] pen; 
		
		/** Nombre de niveaux*/
		private int L; 
	
		/** Nom du fichier TSPLib correspondant à l'instance chargée */
		private String fileName;

		/** Nom du fichier TSPLib correspondant à l'instance chargée */
		private String fileName1;
		
		/** Nom du fichier TSPLib correspondant à l'instance chargée */
		private String fileName2;

		// --------------------------------------------
		// --------------- ACCESSEURS -----------------
		// --------------------------------------------

		/** @return le nombre de jobs */
		public int getNbJobs() {
			return n;
		}

		/** @return le nombre de machines au niveau 1 */
		public int getNbM() {
			return M;
		}
		
		/** @return le nombre de machines au niveau 2 */
		public int getNbF() {
			return F;
		}
		
		/** @return Le big M*/
		public double getBigM() {
			
			//TODO
			return 0;
			
		}
		
		/** @return le tableau des setup pour le niveau 1*/
		public double[][][] getTabSetupM() {
			return s1;
		}
		
		/** @return le tableau des setup pour le niveau 2*/
		public double[][][] getTabSetupF() {
			return s2;
		}
		
		/** @return le tableau des processing time pour le niveau 1*/
		public double[][] getTabProcessM() {
			return p1;
		}
		
		/** @return le tableau des processing time pour le niveau 2*/
		public double[][] getTabProcessF() {
			return p2;
		}
		
		/** @return les due date */
		public double[] getTabDD() {
			return d;
		}
		
		/** @return les pénalités */
		public double[] getTabPen() {
			return pen;
		}
		
		/** @return le nombre de niveaux */
		public double getNbNvx() 
		{
			return L;
		}
		
		
		/**
		 * @param i
		 * 			 job i 
		 * @param m
		 * 			sur la machine m au niveau 2
		 * @return le processing time du job i sur la machine m au niveau 1
		 * @throws Exception
		 **/
		public double getP1(int i, int m) throws Exception 
		{
			
			//TODO
			return 0;
		}
			
		/**
		 * @param i
		 * 			 job i 
		 * @param f
		 * 			sur la machine f au niveau 2
		 * @return le processing time du job i sur la machine m au niveau 2
		 * @throws Exception
		 **/
		public double getP2(int i, int f) throws Exception 
		{
			return 0;
			//TODO
	
		}
		
		/**
		 * @param i
		 * 			 job i 
		 * @param m
		 * 			sur la machine m au niveau 1
		 * @return le setup time du job i sur la machine m au niveau 1
		 * @throws Exception
		 **/
		public double getS1(int i, int m) throws Exception 
		{
			
			//TODO
			return 0 ;
		}
		
		/**
		 * @param i
		 * 			 job i 
		 * @param f
		 * 			sur la machine f au niveau 1
		 * @return le setup time du job i sur la machine m au niveau 2
		 * @throws Exception
		 **/
		public double getS2(int i, int f) throws Exception 
		{
			return 0 ; 
			//TODO
	
		}
		
		
		/**
		 * @param i
		 * 			job i 
		 * @return la due date du job i 
		 * @throws Exception
		 **/
		public double getDueDate(int i) throws Exception 
		{
			return 0 ; 
			//TODO
	
		}
		
		/**
		 * @param i
		 * 			job i 
		 * @return la penalité du job i 
		 * @throws Exception
		 **/
		public double getPenalite(int i) throws Exception 
		{
			return 0 ; 
			//TODO
	
		}

		/**
		 * @return Renvoie le nom du fichier chargé.
		 */
		public String getFileName() 
		{
			return fileName;
		}
		
		/**
		 * @return Renvoie le nom du fichier chargé.
		 */
		public String getFile1Name() 
		{
			return fileName1;
		}
		
		/**
		 * @return Renvoie le nom du fichier chargé.
		 */
		public String getFile2Name() 
		{
			return fileName2;
		}
		
		// --------------------------------------
		// ------------ CONSTRUCTEUR ------------
		// --------------------------------------

		/**
		 * Constructeur : crée un objet du type Instance et charge l'instance du
		 * problème correspondant au fichier fileName.
		 * 
		 * @param fName
		 *          fichier d'instance.
		 * @throws IOException
		 *           Exception retournée en cas d'erreur de lecture dans le fichier.
		 */
		public InstancesMachines(String fName) throws IOException
		{
			fileName = fName;
			read();
			
		}
	

		// --------------------------------------
		// -------------- METHODES --------------
		// --------------------------------------

		
		/**
		 * méthode pour lire le document "data.txt"
		 * @throws IOException
		 */
		private void read() throws IOException {
			
			File mfile = new File(fileName);
		if (!mfile.exists()) {
			throw new IOException("Le fichier saisi : "+ fileName + ", n'existe pas.");
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
			
			
//*******************************************************************
//************EVENTUELLEMENT A FAIRE SI BESOIN **********************
//*******************************************************************		
//		/**
//		 * Imprime la solution sur la sortie out.
//		 * 
//		 * @param out
//		 *          : sortie
//		 */
//		public void print(PrintStream out) {
//
//			out.println("Matrice de distances :");
//			for (int i = 0; i < nbSommets; i++) {
//				for (int j =0; j < nbSommets; j++) {
//					out.print(distances[i][j]+ ";"); 
//				}
//				out.println();
//			}
//			out.println();
//		}
//
//	}
//



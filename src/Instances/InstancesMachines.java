package Instances;

import java.io.IOException;

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
		private double[][][] p1;
		
		/**processing time pour les jobs sur niveau 2*/
		private double[][][] p2;
		
		/**due date pour le job*/
		private double[] d;
		
		/**pénalité pour le job*/
		private double[] pen; 
		
		/** Nombre de niveaux*/
		private int L; 
	
		/** Nom du fichier TSPLib correspondant à l'instance chargée */
		private String fileName;


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
		public double[][][] getTabProcessM() {
			return p1;
		}
		
		/** @return le tableau des processing time pour le niveau 2*/
		public double[][][] getTabProcessF() {
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
			
			//TODO
	
		}

		/**
		 * @return Renvoie le nom du fichier chargé.
		 */
		public String getFileName() 
		{
			return fileName;
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
			while (!line.startsWith("DIMENSION"));
			Scanner lineSc = new Scanner(line);
			lineSc.next();
			if (!lineSc.hasNextInt()) { 
				lineSc.next();
			}
			nbSommets =lineSc.nextInt();
			coordX = new double[nbSommets];
			coordY = new double[nbSommets];
			labels = new String[nbSommets];
			demande = new int[nbSommets];


			while (!line.startsWith("NB_VEHICULES")) {
				line = sc.nextLine();
				System.err.println(line);
			}
			lineSc.close();
			lineSc = new Scanner(line);
			lineSc.next();
			if (!lineSc.hasNextInt()) {
				lineSc.next();
			}
			int nbVehicules = lineSc.nextInt();
			capaVehicule = new int[nbVehicules];

			while (!line.startsWith("SECTION_COORD_NOEUDS")) {
				line = sc.nextLine();
				System.err.println(line);
			}
			line = sc.nextLine();

			int idx = 0;
			for (int s=0;s<nbSommets;s++){
				assert(idx<nbSommets);//?????????????????
				lineSc = new Scanner(line);
				lineSc.useLocale(Locale.US);//??????????????
				labels[idx] = lineSc.next();
				coordX[idx] = lineSc.nextDouble();
				coordY[idx] = lineSc.nextDouble();
				line = sc.nextLine();
				idx++;
			}

			// Création de la matrice de distances
			distances = new long[nbSommets][]; 
			for (int i = 0; i < nbSommets; i++) {
				distances[i] = new long[nbSommets];
			}

			// Calcul des distances 
			for (int i = 0; i < nbSommets; i++) {
				distances[i][i] = 0;
				for (int j = i + 1; j < nbSommets; j++) {
					long dist = distance(i,j);
					//				System.out.println("Distance " + i + " " +j + ": " + dist);
					distances[i][j] = dist;
					distances[j][i] = dist;
				}
			}


			while (!line.startsWith("SECTION_DEMANDE")) {
				line = sc.nextLine();
				System.err.println(line);
			}
			line = sc.nextLine();

			idx = 0;
			for (int s=0;s<nbSommets;s++){
				assert(idx<nbSommets);//???????????????
				lineSc = new Scanner(line);
				lineSc.useLocale(Locale.US);//??????????????
				lineSc.next();
				demande[idx] = lineSc.nextInt();
				line = sc.nextLine();
				idx++;
			}
			
			
			while (!line.startsWith("SECTION_CAPACITE")) {
				line = sc.nextLine();
				System.err.println(line);
			}
			line = sc.nextLine();

			idx = 0;
			for (int s=0;s<nbVehicules;s++){
				assert(idx<nbSommets);
				lineSc = new Scanner(line);
				lineSc.useLocale(Locale.US);
				lineSc.next();
				capaVehicule[idx] = lineSc.nextInt();
				line = sc.nextLine();
				idx++;
			}
			lineSc.close();
			sc.close();
		}

		/** Calcule la distance entre deux sommets */
		private long distance(int i, int j) {
			double dx = coordX[i] - coordX[j];
			double dy = coordY[i] - coordY[j];
			return (long) Math.rint(Math.hypot(dx, dy));
		}

		/**
		 * 
		 * @return la plus grande abscisse
		 */
		public double getMaxX() {

			return getMax(coordX);
		}

		/**
		 * 
		 * @return la plus grande ordonnée
		 */
		public double getMaxY() {

			return getMax(coordY);
		}

		/**
		 * 
		 * @return la plus petite abscisse
		 */
		public double getMinX() {

			return getMin(coordX);
		}

		/**
		 * 
		 * @return la plus petite ordonnée
		 */
		public double getMinY() {

			return getMin(coordY);
		}

		private double getMax(double[] array) {
			double maxVal = Double.MIN_VALUE;
			for (int i = 0; i < nbSommets; i++) {
				if (maxVal < array[i])
					maxVal = array[i];
			}

			return maxVal;
		}

		private double getMin(double[] array) {
			double minVal = Double.MAX_VALUE;
			for (int i = 0; i < nbSommets; i++) {
				if (minVal > array[i])
					minVal = array[i];
			}

			return minVal;
		}

		/**
		 * Imprime la solution sur la sortie out.
		 * 
		 * @param out
		 *          : sortie
		 */
		public void print(PrintStream out) {

			out.println("Matrice de distances :");
			for (int i = 0; i < nbSommets; i++) {
				for (int j =0; j < nbSommets; j++) {
					out.print(distances[i][j]+ ";"); 
				}
				out.println();
			}
			out.println();
		}

	}


}

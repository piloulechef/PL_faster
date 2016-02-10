package Instances;

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
		private double[] duedate;
		
		/**pénalité pour le job*/
		private double[] penalite; 
		
		/** Nombre de niveaux*/
		private int nbNivx; 
	
		/** Nom du fichier TSPLib correspondant à l'instance chargée */
		private String fileName;


		// --------------------------------------------
		// --------------- ACCESSEURS -----------------
		// --------------------------------------------

		/** @return le nombre de sommets dans le problème */
		public int getNbSommets() {
			return nbSommets;
		}

		/**
		 * @param i
		 *          numéro de sommet (doit être compris entre 0 et le nombre de
		 *          sommets du problème-1).
		 * @return l'abcisse du sommet i.
		 **/
		public double getX(int i) throws Exception {
			if ((i<0) || (i>nbSommets-1))
				throw new Exception("Erreur : " + i + " n\'est pas un numéro de sommet compris entre 0 et " + (nbSommets-1));
			return coordX[i];
		}

		/**
		 * @param i
		 *          numéro de sommet (doit être compris entre 0 et le nombre de
		 *          sommets du problème-1).
		 * @return l'ordonnée du sommet i.
		 * @throws Exception
		 **/
		public double getY(int i) throws Exception {
			if ((i<0) || (i>nbSommets-1))
				throw new Exception("Erreur : " + i + " n\'est pas un numéro de sommet compris entre 0 et " + (nbSommets-1));
			return coordY[i];
		}

		/**
		 * @param i
		 *          numéro de sommet (doit être compris entre 0 et le nombre de
		 *          sommets du problème-1).
		 * @return le label du sommet i.
		 * @throws Exception
		 **/
		public String getLabel(int i) throws Exception {
			if ((i < 0) || (i > nbSommets-1))
				throw new Exception("Erreur : " + i + " n\'est pas un numéro de sommet compris entre 1 et " + (nbSommets-1));
			return labels[i];
		}

		/**
		 * @param i
		 * @return la capacité du véhicule i
		 */
		public int getCapaVehicule(int i) {
			return capaVehicule[i];
		}
		
		/**
		 * 
		 * @return le nombre de véhicule
		 */
		public int getNbVehicule(){
			return capaVehicule.length;
		}


		/**
		 * @param i
		 *          numéro de sommet (doit être compris entre 0 et le nombre de
		 *          sommets du problème-1).
		 * @return la demande du sommet i.
		 * @throws Exception
		 **/
		public int getDemande(int i) throws Exception {
			if ((i < 0) || (i > nbSommets-1))
				throw new Exception("Erreur : " + i + " n\'est pas un numéro de sommet compris entre 0 et " + (nbSommets-1));
			return demande[i];
		}
		
		
		
		

		/**
		 * Retourne la distance euclienne arrondie à l'entier le plus proche entre
		 * deux sommets. Toutes les distances sont calculées à la lecture de
		 * l'instance, cette fonction ne fait pas de calcul. Note : les problèmes sont
		 * symmétriques, la distance de i à j est la même que la distance de j à i.
		 * 
		 * @param i
		 *          numéro de sommet de départ (doit être compris entre 0 et le nombre
		 *          de sommets du problème).
		 * @param j
		 *          numéro de sommet d'artrivée (doit être compris entre 0 et le
		 *          nombre de sommets du problème).
		 * @return la distance euclidienne arrondie à l'entier lle plus proche entre
		 *         les sommets i et j.
		 * @throws Exception
		 *           retourne une exception si i ou j ne sont pas des numéros de
		 *           position valide.
		 **/
		public long getDistances(int i, int j) throws Exception {
			if ((i<0) || (i>nbSommets-1))
				throw new Exception("Erreur : " + i + " n\'est pas un numéro de sommet compris entre 0 et " + (nbSommets-1));
			if ((j<0) || (j>nbSommets-1))
				throw new Exception("Erreur : " + j + " n\'est pas un numéro de sommet compris entre 0 et " + (nbSommets-1));
			return distances[i][j];
		}


		/**
		 * @return Renvoie la matrice de disance
		 */
		public long[][] getDistances() {
			return distances;
		}


		/**
		 * @return Renvoie le nom du fichier chargé.
		 */
		public String getFileName() {
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
		public InstanceCapacite(String fName) throws IOException
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
				assert(idx<nbSommets);
				lineSc = new Scanner(line);
				lineSc.useLocale(Locale.US);
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
				assert(idx<nbSommets);
				lineSc = new Scanner(line);
				lineSc.useLocale(Locale.US);
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

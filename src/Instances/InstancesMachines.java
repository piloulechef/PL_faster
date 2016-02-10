package Instances;

public class InstancesMachines {
		// --------------------------------------------
		// --------------- ATTRIBUTS ------------------
		// --------------------------------------------

		/** Nombre de niveaux*/
		private int nbNiveaux; 
		
		/** Nombre de jobs*/
		private int nbJobs;
		
		/**Nombre de machine au niveau 1*/
		private int nbM;
		

		/**Nombre de machine au niveau 2*/
		private int nbF;
		
		/**p�nalit� pour le job*/
		private double[] penalite; 
		
		/**processing time pour le
		
		
		/** Ordonn�es */
		private double[] coordY;

		/**
		 * Label des sommets
		 */
		private String[] labels;


		/** Nom du fichier TSPLib correspondant � l'instance charg�e */
		private String fileName;


		// --------------------------------------------
		// --------------- ACCESSEURS -----------------
		// --------------------------------------------

		/** @return le nombre de sommets dans le probl�me */
		public int getNbSommets() {
			return nbSommets;
		}

		/**
		 * @param i
		 *          num�ro de sommet (doit �tre compris entre 0 et le nombre de
		 *          sommets du probl�me-1).
		 * @return l'abcisse du sommet i.
		 **/
		public double getX(int i) throws Exception {
			if ((i<0) || (i>nbSommets-1))
				throw new Exception("Erreur : " + i + " n\'est pas un num�ro de sommet compris entre 0 et " + (nbSommets-1));
			return coordX[i];
		}

		/**
		 * @param i
		 *          num�ro de sommet (doit �tre compris entre 0 et le nombre de
		 *          sommets du probl�me-1).
		 * @return l'ordonn�e du sommet i.
		 * @throws Exception
		 **/
		public double getY(int i) throws Exception {
			if ((i<0) || (i>nbSommets-1))
				throw new Exception("Erreur : " + i + " n\'est pas un num�ro de sommet compris entre 0 et " + (nbSommets-1));
			return coordY[i];
		}

		/**
		 * @param i
		 *          num�ro de sommet (doit �tre compris entre 0 et le nombre de
		 *          sommets du probl�me-1).
		 * @return le label du sommet i.
		 * @throws Exception
		 **/
		public String getLabel(int i) throws Exception {
			if ((i < 0) || (i > nbSommets-1))
				throw new Exception("Erreur : " + i + " n\'est pas un num�ro de sommet compris entre 1 et " + (nbSommets-1));
			return labels[i];
		}

		/**
		 * @param i
		 * @return la capacit� du v�hicule i
		 */
		public int getCapaVehicule(int i) {
			return capaVehicule[i];
		}
		
		/**
		 * 
		 * @return le nombre de v�hicule
		 */
		public int getNbVehicule(){
			return capaVehicule.length;
		}


		/**
		 * @param i
		 *          num�ro de sommet (doit �tre compris entre 0 et le nombre de
		 *          sommets du probl�me-1).
		 * @return la demande du sommet i.
		 * @throws Exception
		 **/
		public int getDemande(int i) throws Exception {
			if ((i < 0) || (i > nbSommets-1))
				throw new Exception("Erreur : " + i + " n\'est pas un num�ro de sommet compris entre 0 et " + (nbSommets-1));
			return demande[i];
		}
		
		
		
		

		/**
		 * Retourne la distance euclienne arrondie � l'entier le plus proche entre
		 * deux sommets. Toutes les distances sont calcul�es � la lecture de
		 * l'instance, cette fonction ne fait pas de calcul. Note : les probl�mes sont
		 * symm�triques, la distance de i � j est la m�me que la distance de j � i.
		 * 
		 * @param i
		 *          num�ro de sommet de d�part (doit �tre compris entre 0 et le nombre
		 *          de sommets du probl�me).
		 * @param j
		 *          num�ro de sommet d'artriv�e (doit �tre compris entre 0 et le
		 *          nombre de sommets du probl�me).
		 * @return la distance euclidienne arrondie � l'entier lle plus proche entre
		 *         les sommets i et j.
		 * @throws Exception
		 *           retourne une exception si i ou j ne sont pas des num�ros de
		 *           position valide.
		 **/
		public long getDistances(int i, int j) throws Exception {
			if ((i<0) || (i>nbSommets-1))
				throw new Exception("Erreur : " + i + " n\'est pas un num�ro de sommet compris entre 0 et " + (nbSommets-1));
			if ((j<0) || (j>nbSommets-1))
				throw new Exception("Erreur : " + j + " n\'est pas un num�ro de sommet compris entre 0 et " + (nbSommets-1));
			return distances[i][j];
		}


		/**
		 * @return Renvoie la matrice de disance
		 */
		public long[][] getDistances() {
			return distances;
		}


		/**
		 * @return Renvoie le nom du fichier charg�.
		 */
		public String getFileName() {
			return fileName;
		}

		// --------------------------------------
		// ------------ CONSTRUCTEUR ------------
		// --------------------------------------

		/**
		 * Constructeur : cr�e un objet du type Instance et charge l'instance du
		 * probl�me correspondant au fichier fileName.
		 * 
		 * @param fName
		 *          fichier d'instance.
		 * @throws IOException
		 *           Exception retourn�e en cas d'erreur de lecture dans le fichier.
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

			// Cr�ation de la matrice de distances
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
		 * @return la plus grande ordonn�e
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
		 * @return la plus petite ordonn�e
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

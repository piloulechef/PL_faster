package Instances;

import instance.Instance;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

public class SolutionMachine {
	
	// --------------------------------------------
		// --------------- ATTRIBUTS ------------------
		// --------------------------------------------

		/**
		 * Nombre de machines M
		 */
		private int M;
		
		/**
		 * Nombre de machines F
		 */
		private int F;

		/** nombre de jobs dans le probl�me. */
		private int n;
		
		/**
		 * total maxespan + les p�nalit�s
		 */
		private long objectif;
		
		/**
		 * Solution stock�e sous la forme d'une liste de tableaux. Le i�me �l�ment dans 
		 * le tableau de la j�me liste correspond pour i strictment inf�rieur � M 
		 * au i�me job ordonnanc� sur la machine M. Et pour i plus grand ou �gal �
		 *  M, c'est le i�me job sur la machine F.
		 */
		private ArrayList<int[]> affectation;

		/** Probl�me associ� � la solution */
		private InstancesMachines instance;

		
		/** Code d'erreur disponible apr�s appel � la m�thode <code>validate</code> */
		private String error;

		// --------------------------------------------
		// --------------- ACCESSEURS -----------------
		// --------------------------------------------


		public ArrayList<int[]> getAffectation() {
			return affectation;
		}

		public void setAffectation(ArrayList<int[]> affectation) {
			this.affectation = affectation;
		}

		public int getNbCamion() {
			return nbCamion;
		}

		public void setNbCamion(int nbCamion) {
			this.nbCamion = nbCamion;
		}

		/**
		 * @return Longueur de la tourn�e. Attention il faut soit maintenir cette
		 *         valeur correcte lorsqu'on modifie la solution soit la recalculer
		 *         (m�thodes <code>evaluate</code>) pour que la valeur soit correcte.
		 */
		public long getObjectif() {
			return objectif;
		}

		/**
		 * Fixe la valeur de l'objectif � m_objectif
		 * 
		 * @param m_objectif
		 *          : valeur de l'objectif
		 */
		public void setObjectif(long m_objectif) {
			this.objectif = m_objectif;
		}


		/**
		 * 
		 * @return Probl�me associ� � la solution
		 */
		public Instance getInstance() {
			return instance;
		}

		/**
		 * @param position
		 *          position pour laquelle on veut conna�tre le num�ro de sommet.
		 *        @param idCamion num�ro du camion
		 * @return Retourne le num�ro du sommet en i�me position dans la tourn�e du camion k.
		 * @throws Exception
		 *           retourne une exception si i n'est pas un num�ro de position
		 *           valide.
		 */
		public int getSolution(int position, int idCamion)  throws Exception {
			if ((position<0) || (position>nbSommets-1))
				throw new Exception("Erreur Instance.getSolution : " + position + " n\'est pas un num�ro de sommet compris entre 0 et "
						+ (nbSommets-1));
			return affectation.get(idCamion)[position];
		}


		/**
		 * @return Code d'erreur disponible apr�s appel � la m�thode
		 *         <code>validate</code>
		 */
		public String getError() {
			return error;
		}

		// --------------------------------------
		// ------------ CONSTRUCTEUR ------------
		// --------------------------------------

		/** Cr�e un objet repr�sentant une solution du probl�me <code>inst</code>. */
		public Solution(Instance inst, int nbCamion) {
			instance = inst;
			nbSommets = inst.getNbSommets();
			this.nbCamion = nbCamion;
			affectation = new ArrayList<int[]>();
			for (int i=0;i<nbCamion;i++){
				int[] route = new int[nbSommets];
				Arrays.fill(route,-1);
				affectation.add(route);
			}
		}

		// --------------------------------------
		// -------------- METHODES --------------
		// --------------------------------------

		/**
		 * Ins�re le sommet s en i�me position dans la tourn�e k.
		 * 
		 * @param s
		 *          num�ro du sommet � ins�rer.
		 * @param i
		 *          position d'insertion du sommet s.
		 *          @param k : num�ro de la tourn�e
		 * @throws Exception
		 *           retourne une exception si i n'est pas un num�ro de position
		 *           valide.
		 */
		public void setVertexPosition(int s, int i, int k) throws Exception {
			if ((i<0) || (i>nbSommets-1))
				throw new Exception("Erreur Instance.setVertexPosition(i,s) : la valeur de i : " + i + ", n\'est pas un indice compris entre 1 et "+nbSommets);
			if ((s<0) || (s>nbSommets-1))
				throw new Exception("Erreur Instance.setVertexPosition(i,s,k) : la valeur de s : " + s
						+ ", n\'est pas un num�ro de sommet compris entre 0 et " + (nbSommets-1));
			affectation.get(k)[i] = s;
		}

		/**
		 * Ajouter une tourn�e � la solution (en cr�ant un nouvel �l�ment dans la liste)
		 * @param tournee
		 * @throws Exception
		 */
		public void ajouterTournee(int[] tournee) throws Exception{ 
			if (tournee.length != nbSommets)
				throw new Exception("La tourn�e ajout�e n'a pas la bonne dimension")	;
			affectation.add(tournee);
		}


		/**
		 * Recalcule et retourne la longueur de la tourn�e.
		 * 
		 * @throws Exception
		 */ 
		public double evaluate() throws Exception {
			objectif  = 0;
			for (int[] route:affectation){
				for (int i=0; i<nbSommets-1;i++){
					if (route[i]!=-1 && route[i+1]!= -1)
						objectif += instance.getDistances(route[i], route[i+1]);
				}
			}
			return objectif;
		}

		/**
		 * V�rifie que la soution est une solution r�alisable du TSP. Les op�rations /
		 * tests r�alis�s sont les suivants : <li>recalcule la longueur de la tourn�e
		 * (appel � la m�thode <code>evaluate</code>). <li>v�rifie que chaque sommet
		 * est servi une fois et une seule. Lorsqu'une erreur est rencontr�e, les
		 * messages d'erreurs sont accessibles en apppelant la m�thode
		 * <code>getError()</code>.
		 * 
		 * @return Renvoie le booleen <code>true</code> si la solution est valide,
		 *         <code>false</code> sinon.
		 * @throws Exception
		 */
		public boolean validate() throws Exception {
			boolean result = true;
			error = "";
			int[] occurences = new int[nbSommets];
			Arrays.fill(occurences, 0);
			for (int idRoute =0;idRoute < affectation.size();idRoute++){
				int[] route = affectation.get(idRoute);
				if (route[0] != 0){
					error += "Erreur : le sommet 0 n'est pas visit� en premier pour la route " + ".\n";
					result = false;
				}
				for (int i=0; i<nbSommets;i++){
					if (route[i]!=-1){
						occurences[route[i]]++;
					}
					else
					{
						if (route[i-1] != 0){
							error += "Erreur : le sommet 0 n'est pas visit� en dernier pour la route " + ".\n";
							result = false;
						}
						i=nbSommets;
					}
				}
				if (route[nbSommets-1] != 0 && route[nbSommets-1]!= -1){
					error += "Erreur : le sommet 0 n'est pas visit� en dernier pour la route " + ".\n";
					result = false;
				}
			}
		
			for (int i=1; i<nbSommets;i++){
				if (occurences[i] !=1) {
					error += "Erreur : le sommet " + (i + 1) + " est visit� " + occurences[i] + " fois.\n";
					result = false;
				} 
			}
			evaluate();
			if (result)
				error += "La solution est r�alisable.";
			else 
				error += "La solution n'est pas r�alisable.";
			return result;
		}


		/**
		 * Imprime la solution sur la sortie <code>out</code>.
		 * 
		 * @param out
		 *          : sortie
		 */
		public void print(PrintStream out) {
			out.println("R�solution de longueur " + objectif + ", solution :");
			int index = 0;
			for (int[] route:affectation){

				out.print("Route "+index + " : ");
				out.print(route[0]);
				for (int i = 1; i< nbSommets;i++) {
					if (route[i] != -1)
						out.print("-"+route[i]);			
				}
				out.println();
				index++;
			}

		}


}

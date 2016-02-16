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

		/** nombre de jobs dans le problème. */
		private int n;
		
		/**
		 * total maxespan + les pénalités
		 */
		private long objectif;
		
		/**
		 * Solution stockée sous la forme d'une liste de tableaux. Le ième élément dans 
		 * le tableau de la jème liste correspond pour i strictment inférieur à M 
		 * au ième job ordonnancé sur la machine M. Et pour i plus grand ou égal à
		 *  M, c'est le iéme job sur la machine F.
		 */
		private ArrayList<int[]> affectation;

		/** Problème associé à la solution */
		private InstancesMachines instance;

		
		/** Code d'erreur disponible après appel à la méthode <code>validate</code> */
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
		 * @return Longueur de la tournée. Attention il faut soit maintenir cette
		 *         valeur correcte lorsqu'on modifie la solution soit la recalculer
		 *         (méthodes <code>evaluate</code>) pour que la valeur soit correcte.
		 */
		public long getObjectif() {
			return objectif;
		}

		/**
		 * Fixe la valeur de l'objectif à m_objectif
		 * 
		 * @param m_objectif
		 *          : valeur de l'objectif
		 */
		public void setObjectif(long m_objectif) {
			this.objectif = m_objectif;
		}


		/**
		 * 
		 * @return Problème associé à la solution
		 */
		public Instance getInstance() {
			return instance;
		}

		/**
		 * @param position
		 *          position pour laquelle on veut connaître le numéro de sommet.
		 *        @param idCamion numéro du camion
		 * @return Retourne le numéro du sommet en ième position dans la tournée du camion k.
		 * @throws Exception
		 *           retourne une exception si i n'est pas un numéro de position
		 *           valide.
		 */
		public int getSolution(int position, int idCamion)  throws Exception {
			if ((position<0) || (position>nbSommets-1))
				throw new Exception("Erreur Instance.getSolution : " + position + " n\'est pas un numéro de sommet compris entre 0 et "
						+ (nbSommets-1));
			return affectation.get(idCamion)[position];
		}


		/**
		 * @return Code d'erreur disponible après appel à la méthode
		 *         <code>validate</code>
		 */
		public String getError() {
			return error;
		}

		// --------------------------------------
		// ------------ CONSTRUCTEUR ------------
		// --------------------------------------

		/** Crée un objet représentant une solution du problème <code>inst</code>. */
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
		 * Insère le sommet s en ième position dans la tournée k.
		 * 
		 * @param s
		 *          numéro du sommet à insérer.
		 * @param i
		 *          position d'insertion du sommet s.
		 *          @param k : numéro de la tournée
		 * @throws Exception
		 *           retourne une exception si i n'est pas un numéro de position
		 *           valide.
		 */
		public void setVertexPosition(int s, int i, int k) throws Exception {
			if ((i<0) || (i>nbSommets-1))
				throw new Exception("Erreur Instance.setVertexPosition(i,s) : la valeur de i : " + i + ", n\'est pas un indice compris entre 1 et "+nbSommets);
			if ((s<0) || (s>nbSommets-1))
				throw new Exception("Erreur Instance.setVertexPosition(i,s,k) : la valeur de s : " + s
						+ ", n\'est pas un numéro de sommet compris entre 0 et " + (nbSommets-1));
			affectation.get(k)[i] = s;
		}

		/**
		 * Ajouter une tournée à la solution (en créant un nouvel élément dans la liste)
		 * @param tournee
		 * @throws Exception
		 */
		public void ajouterTournee(int[] tournee) throws Exception{ 
			if (tournee.length != nbSommets)
				throw new Exception("La tournée ajoutée n'a pas la bonne dimension")	;
			affectation.add(tournee);
		}


		/**
		 * Recalcule et retourne la longueur de la tournée.
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
		 * Vérifie que la soution est une solution réalisable du TSP. Les opérations /
		 * tests réalisés sont les suivants : <li>recalcule la longueur de la tournée
		 * (appel à la méthode <code>evaluate</code>). <li>vérifie que chaque sommet
		 * est servi une fois et une seule. Lorsqu'une erreur est rencontrée, les
		 * messages d'erreurs sont accessibles en apppelant la méthode
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
					error += "Erreur : le sommet 0 n'est pas visité en premier pour la route " + ".\n";
					result = false;
				}
				for (int i=0; i<nbSommets;i++){
					if (route[i]!=-1){
						occurences[route[i]]++;
					}
					else
					{
						if (route[i-1] != 0){
							error += "Erreur : le sommet 0 n'est pas visité en dernier pour la route " + ".\n";
							result = false;
						}
						i=nbSommets;
					}
				}
				if (route[nbSommets-1] != 0 && route[nbSommets-1]!= -1){
					error += "Erreur : le sommet 0 n'est pas visité en dernier pour la route " + ".\n";
					result = false;
				}
			}
		
			for (int i=1; i<nbSommets;i++){
				if (occurences[i] !=1) {
					error += "Erreur : le sommet " + (i + 1) + " est visité " + occurences[i] + " fois.\n";
					result = false;
				} 
			}
			evaluate();
			if (result)
				error += "La solution est réalisable.";
			else 
				error += "La solution n'est pas réalisable.";
			return result;
		}


		/**
		 * Imprime la solution sur la sortie <code>out</code>.
		 * 
		 * @param out
		 *          : sortie
		 */
		public void print(PrintStream out) {
			out.println("Résolution de longueur " + objectif + ", solution :");
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

package Instances;

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

		public int getM() {
			return M;
		}
		
		public void setM(int M) {
			this.M = M;
		}

		public int getF() {
			return F;
		}
		
		public void setF(int F) {
			this.F = F;
		}
		
		public int getn() {
			return n;
		}
		
		public void setn(int n) {
			this.n = n;
		}

		/**
		 * @return makespan + pénalités. Attention il faut soit maintenir cette
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
		public InstancesMachines getInstance() {
			return instance;
		}

		/**
		 * @param position
		 *          position pour laquelle on veut savoir le job dans le tableau
		 *        @param idMachine numéro de machine
		 * @return Retourne le numéro du job en ième position dans le planning de la machine M.
		 * @throws Exception
		 *           retourne une exception si i n'est pas un numéro de position
		 *           valide.
		 */
		public int getSolution(int  position, int idMachine)  throws Exception {
			if ((position<0) || (position>n))
				throw new Exception("Erreur Instance.getSolution : " + position + " n\'est pas un numéro de job compris entre 1 et "
						+ (n-1));
			return affectation.get(idMachine)[position];
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
		public SolutionMachine(InstancesMachines inst, int n, int M, int F) {
			instance = inst;
			n = inst.getNbJobs();
			M = inst.getNbM();
			F = inst.getNbF();
			this.n = n;
			this.M= M;
			this.F= F;
			affectation = new ArrayList<int[]>();
			for (int i=0;i<M;i++){
				int[] ordoM = new int[n];
				Arrays.fill(ordoM,-1);
				affectation.add(ordoM);
			}
			for (int i=M;i<F;i++){
				int[] ordoF = new int[n];
				Arrays.fill(ordoF,-1);
				affectation.add(ordoF);
			}
		}

		// --------------------------------------
		// -------------- METHODES --------------
		// --------------------------------------

		/**
		 * Insère le job j en ième position dans sur la machine m.
		 * 
		 * @param j
		 *          numéro de job à insérer.
		 * @param i
		 *          position d'insertion du job j.
		 *          @param m : numéro de la machine
		 * @throws Exception
		 *           retourne une exception si i n'est pas un numéro de position
		 *           valide.
		 */
		public void setVertexPosition(int j, int i, int m) throws Exception {
			if ((i<0) || (i>n))
				throw new Exception("Erreur Instance.setVertexPosition(i,j) : la valeur de i : " + i + ", n-\'est pas un indice compris entre 0 et "+ (n-1));
			if ((j<0) || (j>n))
				throw new Exception("Erreur Instance.setVertexPosition(i,j,m) : la valeur de j : " + j
						+ ", n\'est pas un numéro de job compris entre 0 et " + (n-1));
			affectation.get(m)[i] = j;
		}

		/**
		 * Ajouter une machine à l'ordo (en créant un nouvel élément dans la liste)
		 * @param ordomach
		 * @throws Exception
		 */
		public void ajouterTournee(int[] ordomach) throws Exception{ 
			if (ordomach.length != n)
				throw new Exception("L'ordomachine ajouté n'a pas la bonne dimension")	;
			affectation.add(ordomach);
		}


		/**
		 * Recalcule et retourne le makspan de l'ordo.
		 * 
		 * @throws Exception
		 */ 
		public double evaluate() throws Exception {
			objectif  = 0;
			
			//TODO
			
			return objectif;
		
				}
			
			
		

		/**
		 * Vérifie que la soution est une solution réalisable du problème. Les opérations /
		 * tests réalisés sont les suivants : 
		 *  Lorsqu'une erreur est rencontrée, les messages d'erreurs sont accessibles 
		 *  en apppelant la méthode
		 * <code>getError()</code>.
		 * 
		 * @return Renvoie le booleen <code>true</code> si la solution est valide,
		 *         <code>false</code> sinon.
		 * @throws Exception
		 */
		public boolean validate() throws Exception {
			return false;
			//TODO??
		}


		/**
		 * Imprime la solution sur la sortie <code>out</code>.
		 * 
		 * @param out
		 *          : sortie
		 */
		public void print() {
			
			//TODO
			
		}


}

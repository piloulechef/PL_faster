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
		 * @return makespan + p�nalit�s. Attention il faut soit maintenir cette
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
		public InstancesMachines getInstance() {
			return instance;
		}

		/**
		 * @param position
		 *          position pour laquelle on veut savoir le job dans le tableau
		 *        @param idMachine num�ro de machine
		 * @return Retourne le num�ro du job en i�me position dans le planning de la machine M.
		 * @throws Exception
		 *           retourne une exception si i n'est pas un num�ro de position
		 *           valide.
		 */
		public int getSolution(int  position, int idMachine)  throws Exception {
			if ((position<0) || (position>n))
				throw new Exception("Erreur Instance.getSolution : " + position + " n\'est pas un num�ro de job compris entre 1 et "
						+ (n-1));
			return affectation.get(idMachine)[position];
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
		 * Ins�re le job j en i�me position dans sur la machine m.
		 * 
		 * @param j
		 *          num�ro de job � ins�rer.
		 * @param i
		 *          position d'insertion du job j.
		 *          @param m : num�ro de la machine
		 * @throws Exception
		 *           retourne une exception si i n'est pas un num�ro de position
		 *           valide.
		 */
		public void setVertexPosition(int j, int i, int m) throws Exception {
			if ((i<0) || (i>n))
				throw new Exception("Erreur Instance.setVertexPosition(i,j) : la valeur de i : " + i + ", n-\'est pas un indice compris entre 0 et "+ (n-1));
			if ((j<0) || (j>n))
				throw new Exception("Erreur Instance.setVertexPosition(i,j,m) : la valeur de j : " + j
						+ ", n\'est pas un num�ro de job compris entre 0 et " + (n-1));
			affectation.get(m)[i] = j;
		}

		/**
		 * Ajouter une machine � l'ordo (en cr�ant un nouvel �l�ment dans la liste)
		 * @param ordomach
		 * @throws Exception
		 */
		public void ajouterTournee(int[] ordomach) throws Exception{ 
			if (ordomach.length != n)
				throw new Exception("L'ordomachine ajout� n'a pas la bonne dimension")	;
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
		 * V�rifie que la soution est une solution r�alisable du probl�me. Les op�rations /
		 * tests r�alis�s sont les suivants : 
		 *  Lorsqu'une erreur est rencontr�e, les messages d'erreurs sont accessibles 
		 *  en apppelant la m�thode
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

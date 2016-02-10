	import ilog.concert.IloException;
	import ilog.concert.IloLinearNumExpr;
	import ilog.concert.IloNumVar;
	import ilog.concert.IloObjectiveSense;
	import ilog.cplex.IloCplex;
	import ilog.cplex.IloCplex.DoubleParam;

	import java.text.DecimalFormat;
	import java.text.DecimalFormatSymbols;



	public class Solver {

	  // Temps limite en secondes
	  private static final double TimeLimit = 60;

	  /**
	   * @param args
	   * @throws IloException
	   */
	  public static void main(String[] args) throws IloException {
		 
	    // DonnÃ©es

	    int n ; /* le nombre de jobs à ordonnancer */
	    int m; /* les m machines du niveau 1*/
	    int l; /* les levels */
		int f; /* les f machines du niveau 2*/
		int R; /* paramètre très grand*/
		double [][][] s1= new double [n+1][n+1][m]; 
		/* setup time entre le job i et le job j sur la machine k au niveau 1*/
		double [][][] s2= new double [n+1][n+1][f]; 
		/* setup time entre le job i et le job j sur la machine k au niveau 2*/
		double [][] p1= new double [n+1][m]; 
		/* temps de process du job i sur la machine k au niveau 1 */
		double [][] p2= new double [n+1][f]; 
		/* temps de process du job i sur la machine k au niveau 2 */
		double [] d = new double [n]; /* due-dates de chaque job */
		double [] pen = new double [n]; /* penalité de retard */

	    // CrÃ©ation de l'environnement Cplex
	    IloCplex cplex = new IloCplex();
	    cplex.setParam(DoubleParam.TiLim, TimeLimit);
	    
	    /* VARIABLES*/
	    
	    /*x1[i,j,k]=1 si le job j est ordonnancé après le job i sur la machine k au niveau 1 */
	    IloNumVar[][][] x1 = new IloNumVar[n+1][n+1][m];
	    /*x2[i,j,k]=1 si le job j est ordonnancé après le job i sur la machine k au niveau 2 */
	    IloNumVar[][][] x2 = new IloNumVar[n+1][n+1][f];
	    /*y1[i,k]=1 si le job i est ordonnancé sur la machine k au niveau 1 */
	    IloNumVar[][] y1 = new IloNumVar[n+1][m];
	    /*y2[i,k]=1 si le job i est ordonnancé sur la machine k au niveau 2 */
	    IloNumVar[][] y2 = new IloNumVar [n+1][f];
	    /*date de fin du job i au niveau l*/
	    IloNumVar [][] c = new IloNumVar [n+1][l];
	    /*variable qui va être supérieure à toutes les variables c[i,l] (cf fonction-objectif) */
	    IloNumVar z;


	    // Instantier les variables
	    for (int i = 0; i < nbMatieresPremieres; i++)
	      x[i] = cplex.numVar(0, stock[i], "x" + i);


	    // Variable reprÃ©sentant la fonction objectif
	    IloNumVar Z;
	    Z = cplex.numVar(0, Double.MAX_VALUE, "obj");

	    // Contrainte 1: Respect du pourcentage minimum
	    for (int j = 0; j < nbElements; j++) {
	      IloLinearNumExpr expr = cplex.linearNumExpr();
	      for (int i = 0; i < nbMatieresPremieres; i++) {
	        expr.addTerm(compoMatiere[i][j], x[i]);
	      }
	      cplex.addLe(pourcentageMin[j] * quantite, expr);
	    }

	    // Contrainte 2: Respect du pourcentage maximum
	    for (int j = 0; j < nbElements; j++) {
	      IloLinearNumExpr expr = cplex.linearNumExpr();
	      for (int i = 0; i < nbMatieresPremieres; i++) {
	        expr.addTerm(compoMatiere[i][j], x[i]);
	      }
	      cplex.addGe(pourcentageMax[j] * quantite, expr);
	    }

	    // Contrainte 3: Respect de la quantitÃ© produite
	    IloLinearNumExpr expr = cplex.linearNumExpr();
	    for (int i = 0; i < nbMatieresPremieres; i++) {
	      expr.addTerm(1, x[i]);
	    }
	    cplex.addGe(expr, quantite);

	    // Objectif: minimiser le cout
	    cplex.addObjective(IloObjectiveSense.Minimize, Z);

	    // Objective function
	    IloLinearNumExpr exprObj = cplex.linearNumExpr();
	    for (int i = 0; i < nbMatieresPremieres; i++) {
	      exprObj.addTerm(cout[i], x[i]);
	    }
	    cplex.addEq(exprObj, Z);

	    // RÃ©solution (result est Ã  vrai si cplex a trouvÃ© une solution rÃ©alisable)
	    boolean result = cplex.solve();

	    // Affichage des rÃ©sultats
	    if (result) {
	      System.out.println("*** " + cplex.getStatus() + "\n*** Valeur trouvÃ©e: " + cplex.getObjValue() + " ***");

	      for (int i = 0; i < nbMatieresPremieres; i++) {
	        System.out.println("La quantitÃ© de " + i + " est de " + cplex.getValue(x[i]) + " kgs");
	          }

	      DecimalFormatSymbols symb = new DecimalFormatSymbols();
	      symb.setDecimalSeparator('.');
	      DecimalFormat df = new DecimalFormat("#.00", symb);
	      // df.setDecimalFormatSymbols()
	      System.out.println("Le coÃ»t total est de " + df.format(cplex.getObjValue()) + " euros");
	        }

	  }
	}


}

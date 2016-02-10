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
	    int M; /* les m machines du niveau 1*/
	    int L; /* les levels */
		int F; /* les f machines du niveau 2*/
		int R; /* paramètre très grand*/
		
		//R à déterminer
		
		double [][][] s1= new double [n+1][n+1][M]; 
		/* setup time entre le job i et le job j sur la machine k au niveau 1*/
		double [][][] s2= new double [n+1][n+1][F]; 
		/* setup time entre le job i et le job j sur la machine k au niveau 2*/
		double [][] p1= new double [n+1][M]; 
		/* temps de process du job i sur la machine k au niveau 1 */
		double [][] p2= new double [n+1][F]; 
		/* temps de process du job i sur la machine k au niveau 2 */
		double [] d = new double [n]; /* due-dates de chaque job */
		double [] pen = new double [n]; /* penalité de retard */

	    // CrÃ©ation de l'environnement Cplex
	    IloCplex cplex = new IloCplex();
	    cplex.setParam(DoubleParam.TiLim, TimeLimit);
	    
	    /* VARIABLES*/
	    
	    /*x1[i,j,k]=1 si le job j est ordonnancé après le job i sur la machine k au niveau 1 */
	    IloNumVar[][][] x1 = new IloNumVar[n+1][n+1][M];
	    /*x2[i,j,k]=1 si le job j est ordonnancé après le job i sur la machine k au niveau 2 */
	    IloNumVar[][][] x2 = new IloNumVar[n+1][n+1][F];
	    /*y1[i,k]=1 si le job i est ordonnancé sur la machine k au niveau 1 */
	    IloNumVar[][] y1 = new IloNumVar[n+1][M];
	    /*y2[i,k]=1 si le job i est ordonnancé sur la machine k au niveau 2 */
	    IloNumVar[][] y2 = new IloNumVar [n+1][F];
	    /*date de fin du job i au niveau l*/
	    IloNumVar [][] c = new IloNumVar [n+1][L];
	    /*variable qui va être supérieure à toutes les variables c[i,l] (cf fonction-objectif) */
	    IloNumVar W;


	    // Instantier les variables
	    for (int i = 0; i < n+1; i++)
		    for (int j = 0; j < n+1; j++)
			    for (int k = 0; k < M; i++)
			    	x1[i][j][k] = cplex.intVar(0,1, "x1" + i+"_"+j+"_"+k);
	    
	    for (int i = 0; i < n+1; i++)
		    for (int j = 0; j < n+1; j++)
			    for (int k = 0; k < F; i++)
			    	x2[i][j][k] = cplex.intVar(0,1, "x2" + i+"_"+j+"_"+k);
	    
	    for (int i = 0; i < n+1; i++)
	    	for (int k = 0; k < F; i++)
			    	y1[i][k] = cplex.intVar(0,1, "y1" + i+"_"+k);
	    
	    
	   for(int i = 0 ; i < n+1 ; i++){
		   for (int k = 0 ; k<F ; k++){
			   y2[i][k] = cplex.intVar(0, 1, "y2" + i + "_" + k);
		   }
	   }
	   
	   for(int i; i<n+1 ; i ++){
		   for(int ln = 1 ; ln<=L ; ln++){
			   c[i][ln] = cplex.numVar(0, R , "c" + i + "_" + ln);
		   }
	   }


	    // Variable reprÃ©sentant la fonction objectif
	    IloNumVar Z;
	    Z = cplex.numVar(0, Double.MAX_VALUE, "obj");
	    
	    /*******************************************************************************/
	    /********************CONTRAINTES PL 8 jusqu'à la fin****************************/
	    /*******************************************************************************/
	    
	  
	    //Contrainte 8 
	    		
	    /* si le job j est ordonnancé après le job i, on s'assure que la date de fin du job j est supérieure à
	    celle du job i par au moins le temps de process du job j plus le setup time entre les deux jobs
		(pour les deux niveaux) */
	    		
	    for(int i = 0 ; i < n+1 ; i ++){
	    	for( int j = 0 ; j < n ; j++){
	    		
	    		IloLinearNumExpr expr = cplex.linearNumExpr();
	    		IloLinearNumExpr expr2 = cplex.linearNumExpr();
	    		IloLinearNumExpr expr3 = cplex.linearNumExpr();
	    		
	    		expr.addTerm(c[j][2],1);
	    		expr.addTerm(c[i][2],-1);
	    		
	    		for(int k = 0 ; k<F;k++){
	    		expr.addTerm(x2[i][j][k],-R);
	    		}
	    		
	    		expr.setConstant(R);
	    		
	    		for(int k = 0 ; k<F ; k++){
	    			expr2.addTerm(s2[i][j][k], y2[i][k]);
	    			expr2.addTerm(p2[j][k], y2[i][k]);
	    		}
	    		
	    		cplex.addGe(expr,expr2 );
	    		
	    	}
	    }
	    	
	    	
	    	//Contrainte 9 
	    	
	    	/* s'assurer que la date de fin d'un job au level 2 est tout le temps supérieure à sa date de fin au
	    	level 1 par au moins le process time du job au level 2 */	
	    
	    	for(int i = 0 ; i<n ; i++){
	    		IloLinearNumExpr expr = cplex.linearNumExpr();
	    		IloLinearNumExpr expr2 = cplex.linearNumExpr();
	    		
	    		expr.addTerm(c[i][2], 1);
	    		expr.addTerm(c[i][1], -1);		
	    				
	    		for(int k = 0 ; k<F ; k++  ){
	    			expr2.addTerm(p2[i][k] , y2[i][k]);
	    		}
	    		
	    		cplex.addGe(expr, expr2);
	    		
	    	}
	    	
	    	
//	    	s.t. c10 {k in 1..m}: sum {j in 1..n} x1[0,j,k]=1;
//	    	s.t. c11 {k in 1..m}: sum {i in 1..n} x1[i,0,k]=1;
//	    	s.t. c12 {k in 1..f}: sum {j in 1..n} x2[0,j,k]=1;
//	    	s.t. c13 {k in 1..f}: sum {i in 1..n} x2[i,0,k]=1;
	    	
	    	//Contraintes 10, 11, 12, 13 
	    	
	    	/* s'assurer le job fictif n+1 est ordonnancé en premier et en dernier sur les deux niveaux sur toutes
	    	les machines (l'ordonnancement des jobs fait un cycle) */
	    	
	    	//C10
	    	
	    	for(int k = 0 ; k<M; k ++){
	    		IloLinearNumExpr expr = cplex.linearNumExpr();
	    		
	    		for(int j = 0 ; j<n ; j ++){
	    			expr.addTerm(x1[n+1][j][k], 1);
	    		}
	    		
	    		cplex.addEq(1, expr);
	    	}
	    	
	    	//C11
	    	
	    	for(int k = 0 ; k<M; k ++){
	    		IloLinearNumExpr expr = cplex.linearNumExpr();
	    		
	    		for(int i = 0 ; i<n ; i ++){
	    			expr.addTerm(x1[i][n+1][k], 1);
	    		}
	    		
	    		cplex.addEq(1, expr);
	    	}
	    	
	    	//C12
	    	
	    	for(int k = 0 ; k<F; k ++){
	    		IloLinearNumExpr expr = cplex.linearNumExpr();
	    		
	    		for(int j = 0 ; j<n ; j ++){
	    			expr.addTerm(x1[n+1][j][k], 1);
	    		}
	    		
	    		cplex.addEq(1, expr);
	    	}
	    	
	    	//C13
	    	
	    	for(int k = 0 ; k<F; k ++){
	    		IloLinearNumExpr expr = cplex.linearNumExpr();
	    		
	    		for(int i = 0 ; i<n ; i ++){
	    			expr.addTerm(x1[i][n+1][k], 1);
	    		}
	    		
	    		cplex.addEq(1, expr);
	    	}
	    	
	    	
	    	
	    	
	    	
	    /*******************************************************************************/
	    /********************FINCONTRAINTE PL 8 jusqu'à la fin**************************/
	    /*******************************************************************************/
	    
	    	

	    // Objectif: minimiser le cout
	    cplex.addObjective(IloObjectiveSense.Minimize, Z);

	    //minimize dateFinOrdonnancement: z + sum {i in 1..n} pen[i]*(c[i,2]-d[i]);
	    
	    /* la fonction-objectif (on cherche à minimiser la date de fin au plus tard de toutes les tâches
	    et à ,pénaliser les retards)*/
	    
	    // Objective function
	    IloLinearNumExpr exprObj = cplex.linearNumExpr();
	   
	      exprObj.addTerm(W,1);
	      
	      for( int i = 0 ; i < n ; i ++){
	    	  exprObj.addTerm(pen[i], c[i][2]);
	    	  double expr = -pen[i]*d[i];
	    	  exprObj.setConstant(expr);
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

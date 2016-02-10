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

    	    int n = 6; /* le nombre de jobs à ordonnancer */
    	    int M = 1; /* les m machines du niveau 1*/
    	    int L =2 ; /* les levels */
    		int F = 1; /* les f machines du niveau 2*/
    		int R = 100; /* paramètre très grand*/
    		
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
    	    IloNumVar W = new ();

    	    // Instantier les variables
    	    for (int i = 0; i <= n; i++)
    		    for (int j = 0; j <= n; j++)
    			    for (int k = 1; k <= M; k++)
    			    	if (i!=j) {
    			    	x1[i][j][k] = cplex.intVar(0,1, "x1" + i+"_"+j+"_"+k);
    			    	}
    	    
    	    for (int i = 0; i <= n; i++)
    		    for (int j = 0; j <= n; j++)
    			    for (int k = 1; k <= F; k++)
    			    	x2[i][j][k] = cplex.intVar(0,1, "x2" + i+"_"+j+"_"+k);
    	    
    	    for (int i = 0; i <= n; i++)
    	    	for (int k = 1; k <= F; k++)
    			    	y1[i][k] = cplex.intVar(0,1, "y1" + i+"_"+k);

    	   for(int i = 0 ; i <= n; i++){
    		   for (int k = 1 ; k<=F ; k++){
    			   y2[i][k] = cplex.intVar(0, 1, "y2" + i + "_" + k);
    		   }
    	   }
    	   
    	   for(int i=0; i<= n ; i ++){
    		   for(int ln = 1 ; ln<=L ; ln++){
    			   c[i][ln] = cplex.numVar(0, R , "c" + i + "_" + ln);
    		   }
    	   }

    	    // Variable reprÃ©sentant la fonction objectif
    	    IloNumVar Z;
    	    Z = cplex.numVar(0, Double.MAX_VALUE, "obj");
    	    
    	    // Contrainte 1: 
    	    for (int i = 1; i <= n; i++) {
    	      IloLinearNumExpr expr = cplex.linearNumExpr();
    	      for (int k = 1; k <= M; k++) {
    	        expr.addTerm(1, y1[i][k]);
    	      }
 		     cplex.addEq(1, expr);

          }
    	    
    	 // Contrainte 2: 
    	    for (int i = 1; i <= n; i++) {
    		      IloLinearNumExpr expr = cplex.linearNumExpr();
    		      for (int k = 1; k <= F; k++) {
    		        expr.addTerm(1, y2[i][k]);
    		      }
			      cplex.addEq(1, expr);

    		    }

    	    // Contrainte 3: 
    	    for (int i = 0; i <= n; i++) {
    		    for (int k = 1; k <= M; k++) {
    	    IloLinearNumExpr expr = cplex.linearNumExpr();
    	    for (int j = 0; j <= n; j++) {
    	      expr.addTerm(1, x1[i][j][k]);
    		    }
		    cplex.addEq(1, expr);

    		   }
    	    }
    	    	    
    	 // Contrainte 4: 
    	    for (int i = 0; i <= n; i++) {
    		    for (int k = 1; k <= F; k++) {
    	    IloLinearNumExpr expr = cplex.linearNumExpr();
    	    for (int j = 0; j <= n; j++) {
    	      expr.addTerm(1, x2[i][j][k]);
    		  }
  		  cplex.addEq(1, expr);

    		 }
    	    }
    	    
    		 // Contrainte 5: 
    	    for (int i = 0; i <= n; i++) {
    		    for (int k = 1; k <= M; k++) {
    	    IloLinearNumExpr expr = cplex.linearNumExpr();
    	    for (int j = 0; j <= n; j++) {
    	      expr.addTerm(1, x1[j][i][k]);
    	    	}
  		  cplex.addEq(1, expr);

    		   }
    	    }
    	    	    
    		 // Contrainte 6: 
    	    for (int i = 0; i <= n; i++) {
    		    for (int k = 1; k <= F; k++) {
    	    IloLinearNumExpr expr = cplex.linearNumExpr();
    	    for (int j = 0; j <= n; j++) {
    	      expr.addTerm(1, x2[j][i][k]);
    	    }
  		  cplex.addEq(1, expr);

    		    }
    	    }
    	    	    		
    	  // Contrainte 7: 
    	   for (int i = 0; i <= n; i++) {
    		   for (int j = 0; j <= n; j++) {
    			   IloLinearNumExpr expr = cplex.linearNumExpr();
    	    	      expr.addTerm(c[j][1],1);
    	    	      expr.addTerm(c[i][1],-1);
    	    	      for (int k=1; k<=M; k++) {
    	    	    	  expr.addTerm(x1[i][j][k],-R);
    	    	      }
    	    	      expr.setConstant(R);
    				  IloLinearNumExpr expr1 = cplex.linearNumExpr();
    				  for (int k=1; k<=M; k++) {
    	    	    	  expr1.addTerm(s1[i][j][k],y1[i][k]);
    	    	    	  expr1.addTerm(p1[j][k],y1[i][k]);
    	    	      }
    				  cplex.addGe(expr,expr1);
    		   }
    		   }
    	   
    	   
    	  
    	    //Contrainte 8 		
    	    for(int i = 0 ; i <= n ; i ++){
    	    	for( int j = 0 ; j <= n ; j++){
    	    		
    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
    	    		IloLinearNumExpr expr2 = cplex.linearNumExpr();
    	    		
    	    		expr.addTerm(c[j][2],1);
    	    		expr.addTerm(c[i][2],-1);
    	    		
    	    		for(int k = 1 ; k<=F;k++){
    	    		expr.addTerm(x2[i][j][k],-R);
    	    		}
    	    		
    	    		expr.setConstant(R);
    	    		
    	    		for(int k = 1 ; k<=F ; k++){
    	    			expr2.addTerm(s2[i][j][k], y2[i][k]);
    	    			expr2.addTerm(p2[j][k], y2[i][k]);
    	    		}
    	    		
    	    		cplex.addGe(expr,expr2 );
    	    		
    	    	}
    	    }
    	    	//Contrainte 9 
    	    
    	    	for(int i = 1 ; i<=n ; i++){
    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
    	    		IloLinearNumExpr expr2 = cplex.linearNumExpr();
    	    		
    	    		expr.addTerm(c[i][2], 1);
    	    		expr.addTerm(c[i][1], -1);		
    	    				
    	    		for(int k = 1 ; k<=F ; k++  ){
    	    			expr2.addTerm(p2[i][k] , y2[i][k]);
    	    		}
    	    		
    	    		cplex.addGe(expr, expr2);
    	    		
    	    	}
    	    	    	
    	    	//Contrainte 10
    	    	
    	    	for(int k = 1 ; k<=M; k ++){
    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
    	    		
    	    		for(int j = 1 ; j<=n ; j ++){
    	    			expr.addTerm(x1[0][j][k], 1);
    	    		}
    	    		
    	    		cplex.addEq(1, expr);
    	    	}
    	    	
    	    	//Contrainte 11
    	    	
    	    	for(int k = 1 ; k<=M; k ++){
    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
    	    		
    	    		for(int i = 1; i<=n ; i ++){
    	    			expr.addTerm(x1[i][0][k], 1);
    	    		}
    	    		
    	    		cplex.addEq(1, expr);
    	    	}
    	    	
    	    	//Contrainte 12
    	    	
    	    	for(int k = 1 ; k<=F; k ++){
    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
    	    		
    	    		for(int j = 1 ; j<=n ; j ++){
    	    			expr.addTerm(x2[0][j][k], 1);
    	    		}
    	    		
    	    		cplex.addEq(1, expr);
    	    	}
    	    	
    	    	//Contrainte 13
    	    	
    	    	for(int k = 1 ; k<=F; k ++){
    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
    	    		
    	    		for(int i = 1 ; i<=n ; i ++){
    	    			expr.addTerm(x2[i][0][k], 1);
    	    		}
    	    		
    	    		cplex.addEq(1, expr);
    	    	} 
    	   
    	   // Contrainte 14: 
    	    for (int i = 1; i <= n; i++) {
    		  cplex.addGe(W, c[i][2]);
    	    }
    		    
    	      	      	    	        		
    	    // Objectif: on cherche à minimiser la date de fin au plus tard de toutes les tâches
    	   // et à ,pénaliser les retards)
    	    
    	    cplex.addObjective(IloObjectiveSense.Minimize, Z);

    	    // Objective function
    	    IloLinearNumExpr exprObj = cplex.linearNumExpr();
    	   
    	      exprObj.addTerm(W,1);
    	      
    	      for( int i = 1 ; i <= n ; i ++){
    	    	  exprObj.addTerm(pen[i], c[i][2]);
    	    	  double expr = -pen[i]*d[i];
    	    	  exprObj.setConstant(expr);
    	      }
    	      
    	    cplex.addEq(exprObj, Z);

    	    // RÃ©solution (result est Ã  vrai si cplex a trouvÃ© une solution rÃ©alisable)
    	    boolean result = cplex.solve();

    	    // Affichage des rÃ©sultats
    	    if (result) {
    	      System.out.println("*** " + cplex.getStatus() + "\n*** Valeur trouvÃ©e: " + cplex.getObjValue() + " ***");

    	      System.out.println("La date de fin d'ordo est : " + cplex.getValue(W));
    	          

    	      DecimalFormatSymbols symb = new DecimalFormatSymbols();
    	      symb.setDecimalSeparator('.');
    	      DecimalFormat df = new DecimalFormat("#.00", symb);
    	      // df.setDecimalFormatSymbols()
    	      System.out.println("Le coÃ»t total est de " + df.format(cplex.getObjValue()) + " euros");
    	        }

    	  }
    	}


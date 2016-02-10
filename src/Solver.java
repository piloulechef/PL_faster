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
    	  private static final double TimeLimit = 100000000;

    	  /**
    	   * @param args
    	   * @throws IloException
    	   */
    	  public static void main(String[] args) throws IloException {
    		 
    	    // Données

    	    int n = 6; /* le nombre de jobs � ordonnancer */
    	    int M = 2; /* les m machines du niveau 1*/
    	    int L =2 ; /* les levels */
    		int F = 2; /* les f machines du niveau 2*/
    		int R = 200; /* param�tre tr�s grand*/
    		
    		//R � d�terminer
    		
    		double [][][] s1= new double [n+1][n+1][M]; 
    		/* setup time entre le job i et le job j sur la machine k au niveau 1*/
    		double [][][] s2= new double [n+1][n+1][F]; 
    		/* setup time entre le job i et le job j sur la machine k au niveau 2*/
    		double [][] p1= {
    	        {
    	            0.0, 0.0
    	        }, {
    	            3.0, 1.0
    	        }, {
    	            2.0, 0.3
    	        }, {
    	            1.0, 90.0
    	        }, {
    	            1.0, 96.0
    	        }, {
    	            2.0, 0.4
    	        }, {
    	            2.0, 0.6
    	        }
    	    };
    		/* temps de process du job i sur la machine k au niveau 1 */
    		double [][] p2= {
    				{
        	            0.0, 0.0
        	        }, {
        	            3.0, 1.0
        	        }, {
        	            2.0, 0.3
        	        }, {
        	            1.0, 90.0
        	        }, {
        	            10.0, 96.0
        	        }, {
        	            3.0, 0.4
        	        }, {
        	            25.0, 0.6
        	        }
        	    };
   
    		/* temps de process du job i sur la machine k au niveau 2 */
    		double [] d = {1000,1000,1000,1000,1000,1000,1000}; /* due-dates de chaque job */
    		double [] pen = {1,2,3,4,5,6}; /* penalit� de retard */

    	    // Création de l'environnement Cplex
    	    IloCplex cplex = new IloCplex();
    	    cplex.setParam(DoubleParam.TiLim, TimeLimit);

    	    
    	    /* VARIABLES*/
    	    
    	    /*x1[i,j,k]=1 si le job j est ordonnanc� apr�s le job i sur la machine k au niveau 1 */
    	    IloNumVar[][][] x1 = new IloNumVar[n+1][n+1][M];
    	    /*x2[i,j,k]=1 si le job j est ordonnanc� apr�s le job i sur la machine k au niveau 2 */
    	    IloNumVar[][][] x2 = new IloNumVar[n+1][n+1][F];
    	    /*y1[i,k]=1 si le job i est ordonnanc� sur la machine k au niveau 1 */
    	    IloNumVar[][] y1 = new IloNumVar[n+1][M];
    	    /*y2[i,k]=1 si le job i est ordonnanc� sur la machine k au niveau 2 */
    	    IloNumVar[][] y2 = new IloNumVar [n+1][F];
    	    /*date de fin du job i au niveau l*/
    	    IloNumVar [][] c = new IloNumVar [n+1][L];
    	    /*variable qui va �tre sup�rieure � toutes les variables c[i,l] (cf fonction-objectif) */
    	    IloNumVar [] W = new IloNumVar [1];

    	    // Instantier les variables
    	    for (int i = 0; i <= n; i++)
    		    for (int j = 0; j <= n; j++)
    			    for (int k = 0; k < M; k++)
    			    	if (i!=j) {
    			    	x1[i][j][k] = cplex.intVar(0,1, "x1" + i+"_"+j+"_"+k);
    			    	}
    			    	else { 
        			    x1[i][j][k] = cplex.intVar(0,0);
    			    	}
    			    	
    	    
    	    
    	    for (int i = 0; i <= n; i++)
    		    for (int j = 0; j <= n; j++)
    			    for (int k = 0; k < F; k++)
    	    				if (i!=j) {
    	    				x2[i][j][k] = cplex.intVar(0,1, "x1" + i+"_"+j+"_"+k);
    	    					}
    	    					else  {
    	    					x2[i][j][k] = cplex.intVar(0,0);
    	    					}
    	    
    	    for (int i = 0; i <= n; i++)
    	    	for (int k = 0; k < F; k++)
    			    	y1[i][k] = cplex.intVar(0,1, "y1" + i+"_"+k);

    	   for(int i = 0 ; i <= n; i++){
    		   for (int k = 0 ; k<F ; k++){
    			   y2[i][k] = cplex.intVar(0, 1, "y2" + i + "_" + k);
    		   }
    	   }
    	   
    	   for(int i=0; i<= n ; i ++){
    		   for(int ln = 0 ; ln<L ; ln++){
    			   c[i][ln] = cplex.numVar(0, R , "c" + i + "_" + ln);
    		   }
    	   }
    	   for (int i=0 ; i<1; i++) {
    		   W[i]= cplex.numVar(0,10000000,"W");
    	   }

    	    // Variable représentant la fonction objectif
    	    IloNumVar Z;
    	    Z = cplex.numVar(0, Double.MAX_VALUE, "obj");
    	    
    	    // Contrainte 1: 
    	    for (int i = 1; i <= n; i++) {
    	      IloLinearNumExpr expr = cplex.linearNumExpr();
    	      for (int k = 0; k < M; k++) {
    	        expr.addTerm(1, y1[i][k]);
    	      }
 		     cplex.addEq(1, expr);

          }
    	    
    	 // Contrainte 2: 
    	    for (int i = 1; i <= n; i++) {
    		      IloLinearNumExpr expr = cplex.linearNumExpr();
    		      for (int k = 0; k < F; k++) {
    		        expr.addTerm(1, y2[i][k]);
    		      }
			      cplex.addEq(1, expr);

    		    }

    	    // Contrainte 3 : 
    	    for (int i = 0; i <= n; i++) {
    		    for (int k = 0; k < M; k++) {
    	    IloLinearNumExpr expr = cplex.linearNumExpr();
    	    for (int j = 0; j <= n; j++) {
    	      expr.addTerm(1, x1[i][j][k]);
    		    }
  		    cplex.addEq(expr,1);
    		   }
    	    }
    	    	    
    	 // Contrainte 4: 
    	    for (int i = 0; i <= n; i++) {
    		    for (int k = 0; k < F; k++) {
    	    IloLinearNumExpr expr = cplex.linearNumExpr();
    	    for (int j = 0; j <= n; j++) {
    	      expr.addTerm(1, x2[i][j][k]);
    		  }
  		  cplex.addEq(1, expr);

    		 }
    	    }
    	    
    		 // Contrainte 5: 
    	    for (int i = 0; i <= n; i++) {
    		    for (int k = 0; k < M; k++) {
    	    IloLinearNumExpr expr = cplex.linearNumExpr();
    	    for (int j = 0; j <= n; j++) {
    	      expr.addTerm(1, x1[j][i][k]);
    	    	}
  		  cplex.addEq(1, expr);

    		   }
    	    }
    	    	    
    		 // Contrainte 6: 
    	    for (int i = 0; i <= n; i++) {
    		    for (int k = 0; k < F; k++) {
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
    	    	      for (int k=0; k<M; k++) {
    	    	    	  expr.addTerm(x1[i][j][k],-R);
    	    	      }
    	    	      expr.setConstant(R);
    				  IloLinearNumExpr expr1 = cplex.linearNumExpr();
    				  for (int k=0; k<M; k++) {
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
    	    		
    	    		expr.addTerm(c[j][1],1);
    	    		expr.addTerm(c[i][1],-1);
    	    		
    	    		for(int k = 0 ; k<F;k++){
    	    		expr.addTerm(x2[i][j][k],-R);
    	    		}
    	    		
    	    		expr.setConstant(R);
    	    		
    	    		for(int k =0 ; k<F ; k++){
    	    			expr2.addTerm(s2[i][j][k], y2[i][k]);
    	    			expr2.addTerm(p2[j][k], y2[i][k]);
    	    		}
    	    		
    	    		cplex.addGe(expr,expr2);
    	    		
    	    	}
    	    }
    	    	//Contrainte 9 
    	    
    	    	for(int i = 1 ; i<=n ; i++){
    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
    	    		IloLinearNumExpr expr2 = cplex.linearNumExpr();
    	    		
    	    		expr.addTerm(c[i][1], 1);
    	    		expr.addTerm(c[i][0], -1);		
    	    				
    	    		for(int k = 0 ; k<F ; k++  ){
    	    			expr2.addTerm(p2[i][k] , y2[i][k]);
    	    		}
    	    		
    	    		cplex.addGe(expr, expr2);
    	    		
    	    	}
    	    	    	
    	    	//Contrainte 10
    	    	
    	    	for(int k = 0 ; k<M; k ++){
    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
    	    		
    	    		for(int j = 1 ; j<=n ; j ++){
    	    			expr.addTerm(x1[0][j][k], 1);
    	    		}
    	    		
    	    		cplex.addEq(1, expr);
    	    	}
    	    	
    	    	//Contrainte 11
    	    	
    	    	for(int k = 0 ; k<M; k ++){
    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
    	    		
    	    		for(int i = 1; i<=n ; i ++){
    	    			expr.addTerm(x1[i][0][k], 1);
    	    		}
    	    		
    	    		cplex.addEq(1, expr);
    	    	}
    	    	
    	    	//Contrainte 12
    	    	
    	    	for(int k = 0 ; k<F; k ++){
    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
    	    		
    	    		for(int j = 1 ; j<=n ; j ++){
    	    			expr.addTerm(x2[0][j][k], 1);
    	    		}
    	    		
    	    		cplex.addEq(1, expr);
    	    	}
    	    	
    	    	//Contrainte 13
    	    	
    	    	for(int k = 0 ; k<F; k ++){
    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
    	    		
    	    		for(int i = 1 ; i<=n ; i ++){
    	    			expr.addTerm(x2[i][0][k], 1);
    	    		}
    	    		
    	    		cplex.addEq(1, expr);
    	    	} 
    	   
    	   // Contrainte 14: 
    	    for (int i = 1; i <= n; i++) {
    		  cplex.addGe(W[0], c[i][1]);
    	    }
    		    
    	      	      	    	        		
    	    // Objectif: on cherche � minimiser la date de fin au plus tard de toutes les t�ches
    	   // et � ,p�naliser les retards)
    	    
    	    cplex.addObjective(IloObjectiveSense.Minimize, Z);

    	    // Objective function
    	    IloLinearNumExpr exprObj = cplex.linearNumExpr();
    	   
    	      exprObj.addTerm(W[0],1);
    	      
    	      for( int i = 0 ; i < n ; i ++){
    	    	  exprObj.addTerm(pen[i], c[i][1]);
    	    	  double expr = -pen[i]*d[i];
    	    	  exprObj.setConstant(expr);
    	      }
    	      
    	    cplex.addEq(exprObj, Z);

    	    // Résolution (result est �  vrai si cplex a trouvé une solution réalisable)
    	    boolean result = cplex.solve();

    	    // Affichage des résultats
    	    if (result) {
    	      System.out.println("*** " + cplex.getStatus() + "\n*** Valeur trouvée: " + cplex.getObjValue() + " ***");

    	      System.out.println("La date de fin d'ordo est : " + cplex.getValue(W[0]));
    	         	   
    	      DecimalFormatSymbols symb = new DecimalFormatSymbols();
    	      symb.setDecimalSeparator('.');
    	      DecimalFormat df = new DecimalFormat("#.00", symb);
    	      // df.setDecimalFormatSymbols()
    	      System.out.println("Le coût total est de " + df.format(cplex.getObjValue()) + " euros");
    	      for (int i = 0; i < n; i++) {
    		      for (int j = 0; j < n; j++) {
        		      for (int k = 0; k < M; k++) {
    		    		  System.out.println(x1[i][j][k]);
    		  	  
    		         }
    		      }
    		    }
    	        }
    	    else {
    	    	System.out.println("tata");
    	    }

    	  }
    	}


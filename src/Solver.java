 import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloObjectiveSense;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.DoubleParam;
import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloObjectiveSense;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.DoubleParam;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import Instances.InstancesMachines;
import Instances.SolutionMachine;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

    	public class Solver {

    	  // Temps limite en secondes
    	  private static final double TimeLimit = 100000000;
    	  
    	  /** Stocke la solution du problème */
    	  private SolutionMachine solution;
    	  
    	  /** Donnnées du problème */
    	  private InstancesMachines instance;
    	  
    	  /** Temps alloué à la résolution du problème */
    	  private long time;

    	// --------------------------------------------
    		// --------------- ACCESSEURS -----------------
    		// --------------------------------------------

    		// Pour accéder aux attributs de la classe TSPSolver

    		/** @return la solution du problème */
    		public SolutionMachine getSolution() {
    			return solution;
    		}

    		/** @return Donnnées du problème de TSP */
    		public InstancesMachines getInstance() {
    			return instance;
    		}

    		/** @return Temps alloué à la résolution du problème */
    		public long getTime() {
    			return time;
    		}
    		
    		
    		/**
    		 * Fixe la solution du problème
    		 * 
    		 * @param m_solution
    		 *          : la solution du problème
    		 */
    		public void setSolution(SolutionMachine m_solution) {
    			this.solution = m_solution;
    		}

    		/**
    		 * Fixe l'instance du problème
    		 * 
    		 * @param m_instance
    		 *          : l'instance du problème
    		 */
    		public void setInstance(InstancesMachines m_instance) {
    			this.instance = m_instance;
    		}

    		/**
    		 * Fixe le temps alloué à la résolution du problème
    		 * 
    		 * @param time
    		 *          : temps alloué à la résolution du problème
    		 */
    		public void setTime(long time) {
    			this.time = time;
    		}

    	  /**
    	   * @param args
    	   * @throws IloException
    	   */
    	  public SolutionMachine  pl_faster() throws Exception {
    	    // DonnÃ©es

    	    int n = instance.getNbJobs(); /* le nombre de jobs à ordonnancer */
    	    int M = instance.getNbM(); /* les m machines du niveau 1*/
    	    int L =(int) instance.getNbNvx() ; /* les levels */
    		int F = instance.getNbF(); /* les f machines du niveau 2*/
    		double R = instance.getBigM(); /* paramètre très grand*/
    		
    		//R à déterminer
    		
    		/** 
    		 * setup time entre le job i et le job j 
    		 * sur la machine k au niveau 1
    		 */
    		double [][][] s1= new double [n+1][n+1][M]; 
    		
    		//remplissage 
    		
    		for ( int i = 0 ; i<n+1 ; i ++){
    			for (int j = 0; j<n+1;j++){
    				for(int k=0; k<M;k++){
    			s1[i][j][k] = instance.getS1(i, j, k);
    				}
    			}
    		}
    		
    		/** setup time entre le job i et le job j sur la machine k au niveau 2*/   		
    		double [][][] s2= new double [n+1][n+1][F]; 
    		
    		for ( int i = 0 ; i<n+1 ; i ++){
    			for (int j = 0; j<n+1;j++){
    				for(int k=0; k<M;k++){
    			s2[i][j][k] = instance.getS2(i, j, k);
    				}
    			}
    		}
    		
    		/** temps de process du job i sur la machine k au niveau 1 */
    		double [][] p1= new double[n+1][M];
    		
    		
    		for ( int i = 0 ; i<n+1 ; i ++){
    				for(int k=0; k<M;k++){
    			p1[i][k] = instance.getP1(i, k);
    				}
    			
    		}
    		
    		/** temps de process du job i sur la machine k au niveau 2 */
    		double [][] p2= new double[n+1][F];
        	    
    		for ( int i = 0 ; i<n+1 ; i ++){
				for(int k=0; k<M;k++){
			p2[i][k] = instance.getP2(i, k);
				}
			}
   
    		 /** due-dates de chaque job */
    		double [] d = new double[n+1];
    				
    		/** penalité de retard */
    		double [] pen = new double[n+1];
    		
    		
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
    	    IloNumVar [] W = new IloNumVar [1];

    	    // Instancier les variables
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

    	    // Variable reprÃ©sentant la fonction objectif
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
    		    
    	      	      	    	        		
    	    // Objectif: on cherche à minimiser la date de fin au plus tard de toutes les tâches
    	   // et à ,pénaliser les retards)
    	    
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

    	    // RÃ©solution (result est Ã  vrai si cplex a trouvÃ© une solution rÃ©alisable)
    	    boolean result = cplex.solve();

    	    // Affichage des rÃ©sultats
    	    if (result) {
    	      System.out.println("*** " + cplex.getStatus() + "\n*** Valeur trouvÃ©e: " + cplex.getObjValue() + " ***");

    	      System.out.println("La date de fin d'ordo est : " + cplex.getValue(W[0]));
    	      
    	    }
    	    return solution;
    	  }
    	  public void solve() throws Exception {
    		  solution = pl_faster();
    	  }
    	}
//    	      DecimalFormatSymbols symb = new DecimalFormatSymbols();
//    	      symb.setDecimalSeparator('.');
//    	      DecimalFormat df = new DecimalFormat("#.00", symb);
//    	      // df.setDecimalFormatSymbols()
//    	      System.out.println("Le coÃ»t total est de " + df.format(cplex.getObjValue()) + " euros");
//    	      for (int i = 0; i < n; i++) {
//    		      for (int j = 0; j < n; j++) {
//
//        		      for (int k = 0; k < M; k++) {
//        		    	  if(cplex.getValue(x1[i][j][k])>0.5) {
//        		    		  System.out.println(x1[i][j][k]+"="+cplex.getValue(x1[i][j][k]));
//        		    	  }
//    		  	  
//    		         }
//    		      }
//    		    }
//    	      for (int i = 0; i <= n; i++) {
//    		      for (int j = 0; j <= n; j++) {
//        		      for (int k = 0; k < F; k++) {
//        		    	  if(cplex.getValue(x2[i][j][k])>0.5) {
//        		    		  System.out.println(x2[i][j][k]+"="+cplex.getValue(x2[i][j][k]));
//        		    	  }
//    		  	  
//    		         }
//    		      }
//    		    }
//    	      for (int i = 0; i <= n; i++) {
//    	    	  System.out.println(c[i][1]+"="+cplex.getValue(c[i][1]));	
//    	    	  //System.out.println(c[i][0]+"="+cplex.getValue(c[i][]));
//        	}
//    		      
// 
//    	        }
//    	    else {
//    	    	System.out.println("tata");
//    	    }
//
//    	  }
//    	}
//        		      for (int k = 0; k < M; k++) {
//    		    		  System.out.println(x1[i][j][k]);
//    		  	  
//    		         }
//    		      }
//    		    }
//    	        }
//    	    else {
//    	    	System.out.println("tata");
//    	    }

    	  
    	


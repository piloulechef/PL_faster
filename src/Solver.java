import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloObjectiveSense;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.DoubleParam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Scanner;

    	public class Solver {

    	  // Temps limite en secondes
    	  private static final double TimeLimit = 60;
  	  
    	  
    	  /**
    	   * @param arg
    	   * @throws IloException
    	 * @throws IOException 
    	   */
    	  public static void main(String[] args) throws IloException, IOException {
    		  
    		  // Données

    	  	    int n ; /* le nombre de jobs � ordonnancer */
    	  	    int M ; /* les m machines du niveau 0*/
    	  		int F ; /* les f machines du niveau 1*/
    	  	    int L ; /* les levels */
    	  		int R ; /* param�tre tr�s grand*/
    	  		int W_MAXVAL = 1000;
    	  		
    	  		
    		  
    		  String filename = null;
    		  filename = args[2];
    		  
    		  File mfile = new File(filename);
    			if (!mfile.exists()) {
    				throw new IOException("Le fichier saisi : "+ filename + ", n'existe pas.");
    			}
    			
    			Scanner sc = new Scanner(mfile);

    			String line;
    			
    			do {
    				line = sc.nextLine();
    				//System.err.println(line);
    			}
    			while (!line.startsWith("NOMBRE_DE_JOBS"));
    			Scanner lineSc = new Scanner(line);
    			lineSc.next();
    			if (!lineSc.hasNextInt()) { 
    				lineSc.next();
    			}
    			n =lineSc.nextInt();
    			//System.out.println(lineSc.nextInt());
//    			coordX = new double[nbSommets];
//    			coordY = new double[nbSommets];
//    			labels = new String[nbSommets];
//    			demande = new int[nbSommets];
    			

    			while (!line.startsWith("NOMBRE_DE_MACHINES_M")) {
    				line = sc.nextLine();
    				//System.err.println(line);
    			}
    			lineSc.close();
    			lineSc = new Scanner(line);
    			lineSc.next();
    			if (!lineSc.hasNextInt()) {
    				lineSc.next();
    			}
    			M = lineSc.nextInt();
    			//capaVehicule = new int[nbVehicules];
    			

    			while (!line.startsWith("NOMBRE_DE_MACHINES_F")) {
    				line = sc.nextLine();
    				//System.err.println(line);
    			}
    			lineSc.close();
    			lineSc = new Scanner(line);
    			lineSc.next();
    			if (!lineSc.hasNextInt()) {
    				lineSc.next();
    			}
    			F = lineSc.nextInt();
    			//capaVehicule = new int[nbVehicules];
    			

    			while (!line.startsWith("NOMBRE_DE_NIVEAUX")) {
    				line = sc.nextLine();
    				System.err.println(line);
    			}
    			lineSc.close();
    			lineSc = new Scanner(line);
    			lineSc.next();
    			if (!lineSc.hasNextInt()) {
    				lineSc.next();
    			}
    			L = lineSc.nextInt();
    			//System.out.println(lineSc.nextInt());
    			//capaVehicule = new int[nbVehicules];

    			while (!line.startsWith("BIG_M")) {
    				line = sc.nextLine();
    				//System.err.println(line);
    			}
    			lineSc.close();
    			lineSc = new Scanner(line);
    			lineSc.next();
    			if (!lineSc.hasNextInt()) {
    				lineSc.next();
    			}
    			R = lineSc.nextInt();

    			double [][][] s0= new double [n+1][n+1][M]; 
    	  		
    	  		/** setup time entre le job i et le job j sur la machine k au niveau 1*/   		
    	  		double [][][] s1= new double [n+1][n+1][F]; 
    	  		
    	  		
    	  		
    	  		/** temps de process du job i sur la machine k au niveau 0 */
    	  		double [][] p0= new double[n+1][M];
    	  		
    	  		
    	  		/** temps de process du job i sur la machine k au niveau 1 */
    	  		double [][] p1= new double[n+1][F];
    	      	    
    	  		
//    	  		 /** due-dates de chaque job */
//    	  		double [] d = new double[n+1];
//    	  				
//    	  		/** penalit� de retard */
//    	  		double [] pen = new double[n+1];
//    	  		
    	  		
    	  		/* temps de process du job i sur la machine k au niveau 2 */
//    	  		double [] d = {1000,1000,1000,1000,1000,1000,1000}; /* due-dates de chaque job */
    	 // 		double [] pen = {1,2,3,4,5,6}; /* penalit� de retard */

    		  
    			
    			
    			
    			while (!line.startsWith("DUE_DATE")) {
    				line = sc.nextLine();
    				//System.err.println(line);
    				
    			}
    			
    			while(!line.startsWith("0")){
    				line = sc.nextLine();
    				//System.err.println(line);
    				
    			}

    			int idx = 0;
    			for (int s=0;s<=n-1;s++){
    				assert(idx<=n-1);//?????????????????
    				lineSc = new Scanner(line);
    				lineSc.next();
    				lineSc.useLocale(Locale.US);//??????????????
    				//double dd = lineSc.nextDouble();
    				//System.out.println("dd = " + dd);
    				//d[idx] = dd;
    				//System.out.println("d["+idx+"] = "+ lineSc.nextInt());
    				line = sc.nextLine();
    				
    				idx++;
    			}
    			
    			while (!line.startsWith("PENALITE")) {
    				line = sc.nextLine();
    				//System.err.println(line);
    			}
    			
    			while(!line.startsWith("0")){
    				line = sc.nextLine();
    				
    			}
    			
    			int idx1 = 0;
    			for (int s=0;s<=n-1;s++){
    				assert(idx1<=n-1);//?????????????????
    				lineSc = new Scanner(line);
    				lineSc.next();
    				lineSc.useLocale(Locale.US);//??????????????
    				//pen[idx1] = lineSc.nextDouble();
    				//System.out.println("pen["+idx1+"] = "+lineSc.nextDouble());
    				line = sc.nextLine();
    				idx1++;
    			}
    			
    			while (!line.startsWith("PROCESSING_TIME")) {
    				line = sc.nextLine();
    				//System.err.println(line);
    			}
    			
    			while (!line.startsWith("NIVEAU_1")) {
    				line = sc.nextLine();
    				//System.err.println(line);
    			}
    			
    			while (!line.startsWith("JOBS")) {
    				line = sc.nextLine();
    				System.err.println(line);
    			}
    			
    			while (!line.startsWith("M1")) {
    				line = sc.nextLine();
    				//System.err.println(line);
    			}
    			
    			int noMach = 0;
    			
    			while(noMach<M)
    			{
    				while (!line.startsWith("0")) {
    					line = sc.nextLine();
    					
    				}
    				
    				int idx2 = 0;
    			
    				for (int s=0;s<=n-1;s++)
    				{
    					assert(idx2<=n-1);//?????????????????
    					lineSc = new Scanner(line);
    					lineSc.next();
    					lineSc.useLocale(Locale.US);//??????????????
    					p0[idx2][noMach] = lineSc.nextDouble();
    					//System.out.println("p0["+idx2+"]["+noMach+"] = " + p0[idx2][noMach]);
    					//int mach = noMach+1;
    					//System.out.println("p1["+idx2+"]["+mach+"] = " + lineSc.nextInt());
    					line = sc.nextLine();
    					idx2++;
    					
    				}
    					
    				noMach++;
    			}
    			
    			while (!line.startsWith("NIVEAU_2")) {
    				line = sc.nextLine();
    				//System.err.println(line);
    			}
    				
    			int noMachF = 1;
    			
    			while(noMachF<=F)
    			{
    				//System.out.println("F" + noMachF);
    				String print = "F" + noMachF;
    				while(!line.startsWith(print)){	
    				line = sc.nextLine();
    				}
    				
    				int idx2 = 0;
    			
    				while (!line.startsWith("0")) {
    					line = sc.nextLine();
    					
    				}
    				
    				for (int s=0;s<=n-1;s++)
    				{
    					assert(idx2<=n-1);//?????????????????
    					lineSc = new Scanner(line);
    					lineSc.next();
    					lineSc.useLocale(Locale.US);//??????????????
    					p1[idx2][noMachF-1] = lineSc.nextDouble();
    					//System.out.println("p1["+idx2+"]["+(noMachF-1)+"] = " + p1[idx2][noMachF-1]);
    					//int nof = noMachF-1;
    					//System.out.println("p2["+idx2+"]["+nof+"] = " + lineSc.nextInt());
    					line = sc.nextLine();
    					idx2++;
    					
    				}
    					
    				noMachF++;
    				
    			}
//    			System.out.println("blkacxlk");
//    			System.out.println(line);
    			//String line2 = sc.nextLine();
    			//System.out.println(line2);
    			//System.out.println(!line.startsWith("SETUP_TIME"));
    			
    			while (!line.startsWith("SETUP_TIME")) {
    				line = sc.nextLine();
    				//System.err.println(line);
    			}
    			
    			while (!line.startsWith("NIVEAU1")) {
    				line = sc.nextLine();
    				//System.err.println(line);
    			}
    			
    			int noMachM = 1; 
    			
    			while(noMachM<=M){
    				
    				while(!line.startsWith("M"+noMachM)){
    					line = sc.nextLine();
    					//System.err.println(line);
    					}
    					
    			
    					int noJobs = 0;
    			
    				while(noJobs<=n-1)
    				{
    					System.out.println("JOB_"+noJobs);
    					while(!line.startsWith("JOB_"+noJobs))
    					{
    						line = sc.nextLine();
    						
    					}
    					
    					while(!line.startsWith("0"))
    					{
    						line = sc.nextLine();
    						//System.err.println(line);
    					}

    				int idx2 = 0;
    			
    				for (int s=0;s<=n-1;s++)
    				{
    				
    					assert(idx2<=n-1);//?????????????????
    					lineSc = new Scanner(line);
    					lineSc.next();
    					lineSc.useLocale(Locale.US);//??????????????
    					s0[noJobs][idx2][noMachM-1] = lineSc.nextDouble();
    					//System.out.println("s1["+noJobs+"]["+idx2+"]["+(noMachM-1)+"] = " + s1[noJobs][idx2][noMachM-1]);
    					//int job=noJobs;
    					//int mach = noMachM-1;
    					//System.out.println("s1["+job+"]["+idx2+"]["+mach+"] = " + lineSc.nextInt());
    					line = sc.nextLine();
    					idx2++;
    					
    				}
    					
    				noJobs++;
    			}
    			noMachM ++ ; 
    			}
    			
    			
    			while (!line.startsWith("NIVEAU2")) {
    				line = sc.nextLine();
    				//System.err.println(line);
    			}
    			line = sc.nextLine();
    			
    			int nosMachF = 1; 
    			
    			while(nosMachF<=F){
    				
    				System.out.println("F"+nosMachF);
    				
    				while(!line.startsWith("F"+nosMachF)){
    					line = sc.nextLine();
    					
    					}
    					line = sc.nextLine();
    			
    					int noJobs = 0;
    			
    				while(noJobs<=n-1)
    				{
    					System.out.println("JOB_"+noJobs);
    					
    					while(!line.startsWith("JOB_"+noJobs))
    					{
    						line = sc.nextLine();
    						
    					}
    				
    					while(!line.startsWith("0"))
    					{
    						line = sc.nextLine();
    						
    					}
    				

    				int idx2 = 0;
    			
    				for (int s=0;s<=n-1;s++)
    				{
    					assert(idx2<=n-1);//?????????????????
    					lineSc = new Scanner(line);
    					lineSc.next();
    					lineSc.useLocale(Locale.US);//??????????????
    					s1[noJobs][idx2][nosMachF-1] = lineSc.nextDouble();
    					//int job = noJobs;
    					//System.out.println("s1["+job+"]["+idx2+"]["+(nosMachF-1)+"] =" + s1[noJobs][idx2][nosMachF-1]);
    					line = sc.nextLine();
    					

    					idx2++;
    					
    				}
    					
    				noJobs++;
    		}
    			nosMachF ++ ; 
    			}
    			
    			sc.close();
    			lineSc.close();
    			
    		

    		  
    	   
    	    // Création de l'environnement Cplex
    	    IloCplex cplex = new IloCplex();
    	    cplex.setParam(DoubleParam.TiLim, TimeLimit);

    	    
    	    /* VARIABLES*/
    	    
    	    /*x0[i,j,k]=1 si le job j est ordonnanc� apr�s le job i sur la machine k au niveau 0 */
    	    IloNumVar[][][] x0 = new IloNumVar[n+1][n+1][M];
    	    /*x1[i,j,k]=1 si le job j est ordonnanc� apr�s le job i sur la machine k au niveau 1 */
    	    IloNumVar[][][] x1 = new IloNumVar[n+1][n+1][F];
    	    /*y0[i,k]=1 si le job i est ordonnanc� sur la machine k au niveau 0 */
    	    IloNumVar[][] y0 = new IloNumVar[n+1][M];
    	    /*y1[i,k]=1 si le job i est ordonnanc� sur la machine k au niveau 1 */
    	    IloNumVar[][] y1 = new IloNumVar [n+1][F];
    	    /*date de fin du job i au niveau l*/
    	    IloNumVar [][] c = new IloNumVar [n+1][L];
    	    /*variable qui va �tre sup�rieure � toutes les variables c[i,l] (cf fonction-objectif) */
    	    IloNumVar [] W = new IloNumVar [1];

    	    // Instantier les variables
    	    for (int i = 0; i <= n; i++)
    		    for (int j = 0; j <= n; j++)
    			    for (int k = 0; k < M; k++){
    			    	if ((i!=j)||(i==0)||(j==0)) {
    			    //	x0[i][j][k] = cplex.numVar(0,1, "x0[" + i+";"+j+";"+k+"]");
    			    	x0[i][j][k] = cplex.intVar(0,1, "x0[" + i+";"+j+";"+k+"]");

    			    	}
    			    	else { 
        			    x0[i][j][k] = cplex.intVar(0,0);
    			    	}
    			    }
    	    			
    	    
    			    	
    	    
    	    
    	    for (int i = 0; i <= n; i++)
    		    for (int j = 0; j <= n; j++)
    			    for (int k = 0; k < F; k++)
    	    				if ((i!=j)||(i==0)||(j==0)) {
    	    			//	x1[i][j][k] = cplex.numVar(0,1, "x1[" + i+";"+j+";"+k+"]");
    	    				x1[i][j][k] = cplex.intVar(0,1, "x1[" + i+";"+j+";"+k+"]");

    	    					}
    	    					else  {
    	    					x1[i][j][k] = cplex.intVar(0,0);
    	    					}
    	    
    	    for (int i = 0; i <= n; i++)
    	    	for (int k = 0; k < M; k++)
			    	//y0[i][k] = cplex.numVar(0,1, "y0[" + i+";"+k+"]");
	    			y0[i][k] = cplex.intVar(0,1, "y0[" + i+";"+k+"]");

    	   for(int i = 0 ; i <= n; i++){
    		   for (int k = 0 ; k<F ; k++){
    			 //  y1[i][k] = cplex.numVar(0, 1, "y1[" + i + ";" + k+"]");
    			   y1[i][k] = cplex.intVar(0, 1, "y1[" + i + ";" + k+"]");

    		   }
    	   }
    	   
    	   for(int i=0; i<= n ; i ++){
    		   for(int ln = 0 ; ln<L ; ln++){
    			   c[i][ln] = cplex.numVar(0, R , "c[" + i + ";" + ln+"]");
    		   }
    	   }
    	   for (int i=0 ; i<1; i++) {
    		   W[i]= cplex.numVar(0,W_MAXVAL,"W");
    	   }
    	   

    	    // Variable représentant la fonction objectif
    	    IloNumVar Z;
    	    Z = cplex.numVar(0, Double.MAX_VALUE, "obj");
    	    
    	 // Contrainte 1: 
    	    //impose que chaque job n'est ordonnanc� qu'une fois sur chaque machine
    	    // au niveau 0
    	    for (int i = 1; i <= n; i++) {
    	      IloLinearNumExpr expr = cplex.linearNumExpr();
    	      for (int k = 0; k <= M-1; k++) {
    	        expr.addTerm(1, y0[i][k]);
    	      }
 		     cplex.addEq(1, expr);
 		     }
    	    
    	    
    	 // Contrainte 2: 
    	    //impose que chaque job n'est ordonnanc� qu'une fois sur chaque machine
    	    // au niveau 1
    	    for (int i = 1; i <= n; i++) {
    		      IloLinearNumExpr expr = cplex.linearNumExpr();
    		      for (int k = 0; k <= F-1; k++) {
    		        expr.addTerm(1, y1[i][k]);
    		      }
			      cplex.addEq(1, expr);

    		    }

    	    // Contrainte 3 : 
    	    //impose que chaque job ordonnanc� sur un machine au niveau 0 doit avoir
    	    //un successeur sur cette machine.
    	    for (int i = 0; i <= n; i++) {
    	    	for (int k =0; k <= M-1;k++) {
    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
    	    		for (int j = 0; j <= n; j++) {
    	      	      expr.addTerm(1, x0[i][j][k]);
    	    		}
    	    		  cplex.addEq(expr, y0[i][k]);
    	    	}
    	    }
//    	    	    
////    	 // Contrainte 4: 
    	    //impose que chaque job ordonnanc� sur un machine au niveau 1 doit avoir
    	    //un successeur sur cette machine.
    	    for (int i = 0; i <= n; i++) {
    		    for (int k = 0; k <= F-1; k++) {
    		    	IloLinearNumExpr expr = cplex.linearNumExpr();
    		    	for (int j = 0; j <= n; j++) {
    		    		expr.addTerm(1, x1[i][j][k]);
    		    		}
    		    	cplex.addEq(expr, y1[i][k]);
    		    	}
    		    }
//    	    
//    		 // Contrainte 5: 
    	    //impose que chaque job ordonnanc� sur un machine au niveau 0 doit avoir
    	    //un pr�decesseur sur cette machine.
    	    for (int i = 0; i <= n; i++) {
    		    for (int k = 0; k <= M-1; k++) {
    		    	IloLinearNumExpr expr = cplex.linearNumExpr();
    		    	for (int j = 0; j <= n; j++) {
    		    		expr.addTerm(1, x0[j][i][k]);
    		    		}
    		    	cplex.addEq(expr, y0[i][k]);
    		    	}
    		    }
//    	    	    
//    		 // Contrainte 6: 
    	    //impose que chaque job ordonnanc� sur un machine au niveau 1 doit avoir
    	    //un pr�decesseur sur cette machine.
    	    for (int i = 0; i <= n; i++) {
    		    for (int k = 0; k <= F-1; k++) {
    		    	IloLinearNumExpr expr = cplex.linearNumExpr();
    		    	for (int j = 0; j <= n; j++) {
    		    		expr.addTerm(1, x1[j][i][k]);
    		    		}
    		    	cplex.addEq(expr, y1[i][k]);
    		    	}
    		    }
    	    	  
    	   //Contrainte 7: 
    	    
     	  //impose le completion time du premier job ordonnanc� sur une machine
     	  //au niveau 0 d'�tre sup�rieur � son processing time
     	   for (int i = 1; i <= n; i++) {
 			   IloLinearNumExpr expr = cplex.linearNumExpr();
     		   for (int k = 0; k <= M-1; k++) {
     			   expr.addTerm(p0[i][k],y0[i][k]);
     		   }
 			   cplex.addGe(c[i][0],expr);

     	   }
     	   
     	// Contrainte 8: 
     	 //impose le completion time du premier job ordonnanc� sur une machine
     	   //au niveau 1 d'�tre sup�rieur � son processing time
     	  
     	   for (int i = 1; i <= n; i++) {
 			   IloLinearNumExpr expr = cplex.linearNumExpr();
     		   for (int k = 0; k <= F-1; k++) {
     			   expr.addTerm(p1[i][k],y1[i][k]);
     		   }
 			   cplex.addGe(c[i][1],expr);

     	   }
     	   
     	   
    	  // Contrainte 9: 
    	    //d�finie le completion time comme �tant sup�rieur au processing time 
    	    //plus le temps de setup entre le job et son pr�decesseur au niveau 0
    	   for (int i = 0; i <= n; i++) {
    		   for (int j = 1; j <= n; j++) {
    			   for (int k = 0; k <= M-1; k++) {
        			   IloLinearNumExpr expr = cplex.linearNumExpr();
        			   expr.addTerm(c[j][0],1);
     	    	       expr.addTerm(c[i][0],-1);
     	    	       expr.addTerm(x0[i][j][k],-R);
     	    	       expr.setConstant(R);   	    	      
    				   cplex.addGe(expr,s0[i][j][k]+p0[j][k]);
    			   }
    		   }
    	   }
    	   
 
	        	     
    	    	  
    	    //Contrainte 10 :  		
    	 //d�finie le completion time comme �tant sup�rieur au processing time 
   	    //plus le temps de setup entre le job et son predecesseur au niveau 1
    	   for (int i = 0; i <= n; i++) {
    		   for (int j = 1; j <= n; j++) {
    			   for (int k = 0; k <= F-1; k++) {
        			   IloLinearNumExpr expr = cplex.linearNumExpr();
        			   expr.addTerm(c[j][1],1);
     	    	       expr.addTerm(c[i][1],-1);
     	    	       expr.addTerm(x1[i][j][k],-R);
     	    	       expr.setConstant(R);   	    	      
    				   cplex.addGe(expr,s1[i][j][k]+p1[j][k]);
    			   }
    		   }
    	   }
    	   
    	   //Contrainte 11 :  
  	   //impose le completion time d'un job au niveau 1 � �tre sup�rieur 
  	   // au completion time de ce job au niveau 0 par au moins le 
  	   //processing time de ce job au niveau 1
  	    
  	    	for(int i = 1 ; i<=n ; i++){
  	    		IloLinearNumExpr expr = cplex.linearNumExpr();
  	    		IloLinearNumExpr expr2 = cplex.linearNumExpr();
  	    		
  	    		expr.addTerm(c[i][1], 1);
  	    		expr.addTerm(c[i][0], -1);		
  	    				
  	    		for(int k = 0 ; k<=F-1 ; k++  ){
  	    			expr2.addTerm(p1[i][k] , y1[i][k]);
  	    		}
  	    		
  	    		cplex.addGe(expr, expr2);
  	    		
  	    	}
  	    	
    	
       	// Essai: 

    	   
//    	   for (int i = 1; i <= n; i++) {
//    		   for (int j = 1; j <= n; j++) {
//    			   for (int k = 0; k <= M-1; k++) {
//        			   IloLinearNumExpr expr = cplex.linearNumExpr();
//        			   expr.addTerm(c[i][0],1);
//     	    	       expr.addTerm(c[j][0],-1);
//     	    	       expr.addTerm(x0[i][j][k],R);
//    				   cplex.addGe(expr,s0[j][i][k]+p0[i][k]);
//    			   }
//    		   }
//    	   }
//    	   for (int i = 1; i <= n; i++) {
//    		   for (int j = 1; j <= n; j++) {
//    			   for (int k = 0; k <= F-1; k++) {
//        			   IloLinearNumExpr expr = cplex.linearNumExpr();
//        			   expr.addTerm(c[i][1],1);
//     	    	       expr.addTerm(c[j][1],-1);
//     	    	       expr.addTerm(x1[i][j][k],R);
//    				   cplex.addGe(expr,s1[j][i][k]+p1[i][k]);
//    			   }
//    		   }
//    	   }
    	   
    	   
    	
    	    	    	
    	    	//Contrainte 12
    	    	//impose je job 0 � �tre en premier sur chaque machine du niveau 0 
    	    	for(int k = 0 ; k<=M-1; k ++){
    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
    	    		
    	    		for(int j = 0 ; j<=n ; j ++){
    	    			expr.addTerm(x0[0][j][k], 1);
    	    		}
    	    		
    	    		cplex.addEq(1, expr);
    	    	}
    	    	
//    	    	//Contrainte 13
    	    	//impose je job 0 � �tre en dernier sur chaque machine du niveau 0
    	    	
    	    	for(int k = 0 ; k<=M-1; k ++){
    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
    	    		
    	    		for(int i = 0 ; i<=n ; i ++){
    	    			expr.addTerm(x0[i][0][k], 1);
    	    		}
    	    		
    	    		cplex.addEq(1, expr);
    	    	}
  	
//    	    	//Contrainte 14
    	    	//impose le job 0 � �tre en dernier sur chaque machine au niveau 1
    	    	for(int k = 0 ; k<=F-1; k ++){
    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
    	    		
    	    		for(int j = 0 ; j<=n ; j ++){
    	    			expr.addTerm(x1[0][j][k], 1);
    	    		}
    	    		
    	    		cplex.addEq(1, expr);
    	    	}
    	    	
//    	    	//Contrainte 15
//    	    	//impose le job 0 � �tre en premier sur chaque machine au niveau 1
    	    	for(int k = 0 ; k<=F-1; k ++){
    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
    	    		
    	    		for(int i = 0 ; i<=n ; i ++){
    	    			expr.addTerm(x1[i][0][k], 1);
    	    		}
    	    		
    	    		cplex.addEq(1, expr);
    	    	} 
    	   
    	   // Contrainte 16: 
    	    	//permet de d�finir l'objectif en minimax
    	    for (int i = 0; i <= n; i++) {
    		  cplex.addGe(W[0], c[i][1]);
    	    }
    	    
    	      	      	    	        		
    	    // Objectif: on cherche � minimiser la date de fin au plus tard de toutes les t�ches
    	   // et � ,p�naliser les retards)
    	    
    	    cplex.addObjective(IloObjectiveSense.Minimize, Z);

    	    // Objective function
    	    IloLinearNumExpr exprObj = cplex.linearNumExpr();
    	   
    	      exprObj.addTerm(W[0],1);
    	      
//    	      for( int i = 0 ; i < n ; i ++){
//    	    	  exprObj.addTerm(pen[i], c[i][1]);
//    	    	  double expr = -pen[i]*d[i];
//    	    	  exprObj.setConstant(expr);
//    	      }
    	      
    	    cplex.addEq(exprObj, Z);

    	    // Résolution (result est �  vrai si cplex a trouvé une solution réalisable)
    	    boolean result = cplex.solve();

    	    // Affichage des résultats
    	    if (result) {
    	    	
    	    	
    	    //  System.out.println("*** " + cplex.getStatus() + "\n*** Valeur trouvée: " + cplex.getObjValue() + " ***");
    	    // System.out.println("La date de fin d'ordo est : " + cplex.getValue(Z));
    	         	   
    	      DecimalFormatSymbols symb = new DecimalFormatSymbols();
    	      symb.setDecimalSeparator('.');
    	      DecimalFormat df = new DecimalFormat("#.00", symb);
    	      // df.setDecimalFormatSymbols()
    	      
    	     
    	      
    	     //affichage solution
    	      
    	      System.out.println("la date de fin d'ordo est : " + df.format(cplex.getObjValue()) + " min");
    	      
    	      for(int l=0; l<=L-1;l++)
    	      {
    	    	  
    	    	  System.out.println("Ordonnancement pour le niveau " + l + " : ");
    	    	  if( l==0){
    	    	  for(int m = 0 ; m<=M-1;m++)
    	    	  {
    	    		  System.out.println("	- pour la machine "+ m + " : ");
    	    		  int im=0;
    	    		  String ordo ="0";
    	    		  String completiontime="0";
    	    		  do
    	    		  {
    	    			  int jm = 0;
    	    			  int c1 = 0;
    	    			  //int compteur = 0;
    	    			  
    	    			  while(jm<=n-1 && c1==0)
    	    			  {
    	    				 // System.out.println("avant if : "+ compteur);
    	    				  //System.out.println(cplex.getValue(x0[im][jm][m]));
    	    				  //System.out.println(cplex.getValue(x0[im][jm][m])>0.5);
        	    				  if(cplex.getValue(x0[im][jm][m])>0.0000001)
        	    				  {
            	    				  //System.out.println("dans if : " +compteur);

        	    					  c1=1;
        	    					  ordo = ordo + " - " + jm;
        	    					  if(jm!=0)
        	    					  {
        	    					  completiontime = completiontime + " - ("+jm+")" + cplex.getValue(c[jm][l]);
        	    					  }else
        	    					  	{
        	    						  completiontime = completiontime+ " - "+jm;
        	    					  
        	    					  	}
        	    					  
        	    				  }else{
            	    				  //System.out.println("dans else : " +compteur);

            	    				  jm++;}
        	    				  //System.out.println("apr�s if : " +compteur);
        	    				  //compteur ++;

        	    				  
    	    			  }
    	    			  
    	    			  im = jm;
        	    					  
    	    		  }while(im!=0);
    	    			  
    	    			//System.out.println(ordo);	
    	    			System.out.println("\n" + completiontime+ "\n");
    	    			
    	    		}
    	    	  
    	    	  //BIG ELSE DU LEVEL
    	    	  			}else{
    	    	  				
    	    	  				for(int f = 0 ; f<=F-1;f++)
    	    	    	    	  {
    	    	    	    		  System.out.println("	- pour la machine "+ f + " : ");
    	    	    	    		  int i_f=0;
    	    	    	    		  String ordof ="0";
    	    	    	    		  String completiontimef = "0";
    	    	    	    		  do
    	    	    	    		  {
    	    	    	    			  int jf = 0;
    	    	    	    			  int c2 = 0;
    	    	    	    			  
    	    	    	    			  while(jf<=n-1 && c2!=1)
    	    	    	    			  {
    	    	        	    				  if(cplex.getValue(x1[i_f][jf][f])>0.0000001)
    	    	        	    				  {
    	    	        	    					  c2=1;
    	    	        	    					  ordof = ordof + " - " + jf;
    	    	        	    					  if(jf!=0)
    	    	        	    					  {
    	    	        	    					  completiontimef = completiontimef + " - ("+jf+")" + cplex.getValue(c[jf][l]);
    	    	        	    					  }else
    	    	        	    					  	{
    	    	        	    						  completiontimef = completiontimef + " - "+jf;
    	    	        	    					  
    	    	        	    					  	}
    	    	        	    				  }else{jf++;}
    	    	        	    				  
    	    	    	    			  }
    	    	    	    			  
    	    	    	    			  i_f = jf;
    	    	        	    					  
    	    	    	    		  }while(i_f!=0);
    	    	    	    			  
    	    	    	    			//System.out.println(ordof);	
    	    	    	    			System.out.println("\n" + completiontimef+ "\n");
    	    	    	    			
    	    	    	    		}
    	    	    	    	  
    	    	  				
    	    	  				
    	    	  				
    	    	  				
    	    	  			}
    	    	}
    	    	  
//    	      
//    	    	  int n ; /* le nombre de jobs � ordonnancer */
//    	    	  	    int M ; /* les m machines du niveau 0*/
//    	    	  		int F ; /* les f machines du niveau 1*/
//    	    	  	    int L ; /* les levels */
//    	    	  		int R ; /* param�tre tr�s grand*/
//    	      
//    	      
//    	      
//    	      for (int i = 0; i <= n; i++) {
//    		      for (int j = 0; j <= n; j++) {
//        		      for (int k = 0; k <= M-1; k++) {
//        		    	  if(cplex.getValue(x0[i][j][k])>0.5) {
//        		    		  System.out.println(x0[i][j][k]+"="+Math.ceil(cplex.getValue(x0[i][j][k])));
//        		    	  }
//    		  	  
//    		         }
//    		      }
//    		    }
//    	      for (int i = 0; i <= n; i++) {
//    		      for (int j = 0; j <= n; j++) {
//        		      for (int k = 0; k < F; k++) {
//        		    	  if(cplex.getValue(x1[i][j][k])>0.5) {
//        		    		  System.out.println(x1[i][j][k]+"="+Math.ceil(cplex.getValue(x1[i][j][k])));
//        		    	  }
//    		  	  
//    		         }
//    		      }
//    		    }
//
//    	      for (int i = 0; i <= n; i++) {
//        		     for (int k = 0; k < M; k++) {
//        		    	  if(cplex.getValue(y0[i][k])>0.5) {
//        		    		  System.out.println(y0[i][k]+"="+cplex.getValue(y0[i][k]));
//        		    	  }
//    		      }
//    		    }
//    	      
//    	      for (int i = 0; i <= n; i++) {
//     		     for (int k = 0; k < F; k++) {
//     		    	  if(cplex.getValue(y1[i][k])>0.5) {
//     		    		  System.out.println(y1[i][k]+"="+cplex.getValue(y1[i][k]));
//     		    	  }
// 		      }
// 		    }
//    	      
//    	      for (int i = 0; i <= n; i++) {
//    	    	  System.out.println(c[i][0]+"="+cplex.getValue(c[i][0]));	
//    	    	  System.out.println(c[i][1]+"="+cplex.getValue(c[i][1]));
//    	    	  }
//    	      System.out.println("obj = "+cplex.getValue(exprObj));
//    		      
 
    	        }
    	    else {
    	    	System.out.println("Erreur, pas de r�sultat");
      	  //    System.out.println(cplex.getModel().toString());
      	    //  cplex.exportModel("Modele_PL.lp");

    	    }

    	  }
    	


public int nothinghamza(){
//
//
//import Instances.InstancesMachines;
//import Instances.SolutionMachine;
//	
//import ilog.concert.IloException;
//import ilog.concert.IloLinearNumExpr;
//import ilog.concert.IloNumVar;
//import ilog.concert.IloObjectiveSense;
//import ilog.cplex.IloCplex;
//import ilog.cplex.IloCplex.DoubleParam;
//
//import java.text.DecimalFormat;
//import java.text.DecimalFormatSymbols;
//	
//	
//
//	    public class Solver {
//
//	    	  // Temps limite en secondes
//	    	  private static final double TimeLimit = 100000000;
//	    	  private int W_MAXVAL = 1000;
//
//	    	  /** Stocke la solution du probl�me */
//	    	  private SolutionMachine solution;
//	    	  
//	    	  /** Donnn�es du probl�me */
//	    	  private InstancesMachines instance;
//	    	  
//	    	  /** Temps allou� � la r�solution du probl�me */
//	    	  private long time;
//
//	    	// --------------------------------------------
//	    		// --------------- ACCESSEURS -----------------
//	    		// --------------------------------------------
//
//	    		// Pour acc�der aux attributs de la classe TSPSolver
//
//	    		/** @return la solution du probl�me */
//	    		public SolutionMachine getSolution() {
//	    			return solution;
//	    		}
//
//	    		/** @return Donnn�es du probl�me de TSP */
//	    		public InstancesMachines getInstance() {
//	    			return instance;
//	    		}
//
//	    		/** @return Temps allou� � la r�solution du probl�me */
//	    		public long getTime() {
//	    			return time;
//	    		}
//	    		
//	    		
//	    		/**
//	    		 * Fixe la solution du probl�me
//	    		 * 
//	    		 * @param m_solution
//	    		 *          : la solution du probl�me
//	    		 */
//	    		public void setSolution(SolutionMachine m_solution) {
//	    			this.solution = m_solution;
//	    		}
//
//	    		/**
//	    		 * Fixe l'instance du probl�me
//	    		 * 
//	    		 * @param m_instance
//	    		 *          : l'instance du probl�me
//	    		 */
//	    		public void setInstance(InstancesMachines m_instance) {
//	    			this.instance = m_instance;
//	    		}
//
//	    		/**
//	    		 * Fixe le temps allou� � la r�solution du probl�me
//	    		 * 
//	    		 * @param time
//	    		 *          : temps allou� � la r�solution du probl�me
//	    		 */
//	    		public void setTime(long time) {
//	    			this.time = time;
//	    		}
//
//	    	  /**
//	    	   * @param args
//	    	   * @throws IloException
//	    	   */
//	    	  
//	    	  /**
//	    	   * @param args
//	    	   * @throws IloException
//	    	   */
//	    	
//	    public SolutionMachine  pl_faster() throws Exception {
//	    	    	  
//	    	    
//	    			 // Données
//
//	        	    int n = instance.getNbJobs(); /* le nombre de jobs � ordonnancer */
//	        	    int M = instance.getNbM(); /* les m machines du niveau 1*/
//	        	    int L =(int) instance.getNbNvx() ; /* les levels */
//	        		int F = instance.getNbF(); /* les f machines du niveau 2*/
//	        		double R = instance.getBigM(); /* param�tre tr�s grand*/
//	        		
//	        		//R � d�terminer
//	    	    
//	        		/** 
//	        		 * setup time entre le job i et le job j 
//	        		 * sur la machine k au niveau 1
//	        		 */
//	        		double [][][] s0= new double [n+1][n+1][M]; 
//	        		
//	        		//remplissage 
//	        		
//	        		for ( int i = 0 ; i<n+1 ; i ++)
//	        		{
//	        			for (int j = 0; j<n+1;j++)
//	        			{
//	        				for(int k=0; k<M;k++)
//	        				{
//	        			s0[i][j][k] = instance.getS0(i, j, k);
//	        				}
//	        			}
//	        		}
//	        		
//	        		/** setup time entre le job i et le job j sur la machine k au niveau 2*/   		
//	        		double [][][] s1= new double [n+1][n+1][F]; 
//	        		
//	        		for ( int i = 0 ; i<n+1 ; i ++)
//	        		{
//	        			for (int j = 0; j<n+1;j++)
//	        			{
//	        				for(int k=0; k<M;k++)
//	        				{
//	        			s1[i][j][k] = instance.getS1(i, j, k);
//	        				}
//	        			}
//	        		}
//	        		
//	        		/** temps de process du job i sur la machine k au niveau 1 */
//	        		double [][] p0= new double[n+1][M];
//	        		
//	        		
//	        		for ( int i = 0 ; i<n+1 ; i ++)
//	        		{
//	        				for(int k=0; k<M;k++)
//	        				{
//	        			p0[i][k] = instance.getP0(i, k);
//	        				}
//	        			
//	        		}
//	        		
//	        		/** temps de process du job i sur la machine k au niveau 2 */
//	        		double [][] p1= new double[n+1][F];
//	            	    
//	        		for ( int i = 0 ; i<n+1 ; i ++)
//	        		{
//	    				for(int k=0; k<M;k++)
//	    				{
//	    			p1[i][k] = instance.getP1(i, k);
//	    				}
//	    			}
//	       
//	        		 /** due-dates de chaque job */
//	        		double [] d = new double[n+1];
//	        				
//	        		/** penalit� de retard */
//	        		double [] pen = new double[n+1];
//	        		
//	    		
//	    	    // Création de l'environnement Cplex
//	    	    IloCplex cplex = new IloCplex();
//	    	    cplex.setParam(DoubleParam.TiLim, TimeLimit);
//
//	    	    
//	    	    /* VARIABLES*/
//	    	    
//	    	    /*x0[i,j,k]=1 si le job j est ordonnanc� apr�s le job i sur la machine k au niveau 0 */
//	    	    IloNumVar[][][] x0 = new IloNumVar[n+1][n+1][M];
//	    	    /*x1[i,j,k]=1 si le job j est ordonnanc� apr�s le job i sur la machine k au niveau 1 */
//	    	    IloNumVar[][][] x1 = new IloNumVar[n+1][n+1][F];
//	    	    /*y0[i,k]=1 si le job i est ordonnanc� sur la machine k au niveau 0 */
//	    	    IloNumVar[][] y0 = new IloNumVar[n+1][M];
//	    	    /*y1[i,k]=1 si le job i est ordonnanc� sur la machine k au niveau 1 */
//	    	    IloNumVar[][] y1 = new IloNumVar [n+1][F];
//	    	    /*date de fin du job i au niveau l*/
//	    	    IloNumVar [][] c = new IloNumVar [n+1][L];
//	    	    /*variable qui va �tre sup�rieure � toutes les variables c[i,l] (cf fonction-objectif) */
//	    	    IloNumVar [] W = new IloNumVar [1];
//
//	    	    // Instantier les variables
//	    	    for (int i = 0; i <= n; i++)
//	    	    {
//	    		    for (int j = 0; j <= n; j++)
//	    			    for (int k = 0; k < M; k++)
//	    			    {
//	    			    	if ((i!=j)||(i==0)||(j==0)) 
//	    			    	{
//	    			    //	x0[i][j][k] = cplex.numVar(0,1, "x0[" + i+";"+j+";"+k+"]");
//	    			    	x0[i][j][k] = cplex.intVar(0,1, "x0[" + i+";"+j+";"+k+"]");
//
//	    			    	}
//	    			    	else 
//	    			    	{ 
//	        			    x0[i][j][k] = cplex.intVar(0,0);
//	    			    	}
//	    			    }
//	    	    }
//	    	    			
//	    	    
//	    			    	
//	    	    
//	    	    
//	    	    for (int i = 0; i <= n; i++)
//	    	    {
//	    		    for (int j = 0; j <= n; j++)
//	    		    {
//	    			    for (int k = 0; k < F; k++)
//	    			    {
//	    	    				if ((i!=j)||(i==0)||(j==0)) {
//	    	    			//	x1[i][j][k] = cplex.numVar(0,1, "x1[" + i+";"+j+";"+k+"]");
//	    	    				x1[i][j][k] = cplex.intVar(0,1, "x1[" + i+";"+j+";"+k+"]");
//
//	    	    					}
//	    	    					else  {
//	    	    					x1[i][j][k] = cplex.intVar(0,0);
//	    	    					}
//	    			    }
//	    		    }
//	    	    }
//	    	    
//	    	    for (int i = 0; i <= n; i++)
//	    	    {
//	    	    	for (int k = 0; k < M; k++)
//	    	    	{
//				    	//y0[i][k] = cplex.numVar(0,1, "y0[" + i+";"+k+"]");
//		    			y0[i][k] = cplex.intVar(0,1, "y0[" + i+";"+k+"]");
//	    	    	}
//	    	    }
//
//	    	   for(int i = 0 ; i <= n; i++)
//	    	   {
//	    		   for (int k = 0 ; k<F ; k++)
//	    		   {
//	    			 //  y1[i][k] = cplex.numVar(0, 1, "y1[" + i + ";" + k+"]");
//	    			   y1[i][k] = cplex.intVar(0, 1, "y1[" + i + ";" + k+"]");
//
//	    		   }
//	    	   }
//	    	   
//	    	   for(int i=0; i<= n ; i ++)
//	    	   {
//	    		   for(int ln = 0 ; ln<L ; ln++)
//	    		   {
//	    			   c[i][ln] = cplex.numVar(0, R , "c[" + i + ";" + ln+"]");
//	    		   }
//	    	   }
//	    	   for (int i=0 ; i<1; i++) 
//	    	   {
//	    		   W[i]= cplex.numVar(0,W_MAXVAL,"W");
//	    	   }
//	    	   
//
//	    	    // Variable représentant la fonction objectif
//	    	    IloNumVar Z;
//	    	    Z = cplex.numVar(0, Double.MAX_VALUE, "obj");
//	    	    
//	    	 // Contrainte 1: 
//	    	    for (int i = 1; i <= n; i++) {
//	    	    	IloLinearNumExpr expr = cplex.linearNumExpr();
//	    	      	for (int k = 0; k <= M-1; k++)
//	    	      	{
//	    	      		expr.addTerm(1, y0[i][k]);
//	    	      	}
//	    	      	cplex.addEq(1, expr);
//	 		     }
//	    	    
//	    	    
//	    	 // Contrainte 2: 
//	    	    for (int i = 1; i <= n; i++) 
//	    	    {
//	    		      IloLinearNumExpr expr = cplex.linearNumExpr();
//	    		      for (int k = 0; k <= F-1; k++) 
//	    		      {
//	    		        expr.addTerm(1, y1[i][k]);
//	    		      }
//				      cplex.addEq(1, expr);
//
//	    		    }
//
//	    	    // Contrainte 3 : 
//	    	    for (int i = 0; i <= n; i++)
//	    	    {
//	    	    	for (int k =0; k <= M-1;k++) 
//	    	    	{
//	    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
//	    	    		for (int j = 0; j <= n; j++) 
//	    	    		{
//	    	      	      expr.addTerm(1, x0[i][j][k]);
//	    	    		}
//	    	    		  cplex.addEq(expr, y0[i][k]);
//	    	    	}
//	    	    }
////	    	    	    
//////	    	 // Contrainte 4: 
//	    	    for (int i = 0; i <= n; i++) 
//	    	    {
//	    		    for (int k = 0; k <= F-1; k++) 
//	    		    {
//	    		    	IloLinearNumExpr expr = cplex.linearNumExpr();
//	    		    	for (int j = 0; j <= n; j++) 
//	    		    	{
//	    		    		expr.addTerm(1, x1[i][j][k]);
//	    		    		}
//	    		    	cplex.addEq(expr, y1[i][k]);
//	    		    	}
//	    		    }
////	    	    
////	    		 // Contrainte 5: 
//	    	    for (int i = 0; i <= n; i++) 
//	    	    {
//	    		    for (int k = 0; k <= M-1; k++) 
//	    		    {
//	    		    	IloLinearNumExpr expr = cplex.linearNumExpr();
//	    		    	for (int j = 0; j <= n; j++) 
//	    		    	{
//	    		    		expr.addTerm(1, x0[j][i][k]);
//	    		    		}
//	    		    	cplex.addEq(expr, y0[i][k]);
//	    		    	}
//	    		    }
////	    	    	    
////	    		 // Contrainte 6: 
//	    	    for (int i = 0; i <= n; i++)
//	    	    {
//	    		    for (int k = 0; k <= F-1; k++) 
//	    		    {
//	    		    	IloLinearNumExpr expr = cplex.linearNumExpr();
//	    		    	for (int j = 0; j <= n; j++) 
//	    		    	{
//	    		    		expr.addTerm(1, x1[j][i][k]);
//	    		    	}
//	    		    	cplex.addEq(expr, y1[i][k]);
//	    		    }
//	    	    }
//	    	    	    		
//	    	  // Contrainte 7: 
//	    	   for (int i = 0; i <= n; i++) 
//	    	   {
//	    		   for (int j = 1; j <= n; j++) 
//	    		   {
//	    			   for (int k = 0; k <= M-1; k++) 
//	    			   {
//	        			   IloLinearNumExpr expr = cplex.linearNumExpr();
//	        			   expr.addTerm(c[j][0],1);
//	     	    	       expr.addTerm(c[i][0],-1);
//	     	    	       expr.addTerm(x0[i][j][k],-R);
//	     	    	       expr.setConstant(R);   	    	      
//	    				   cplex.addGe(expr,s0[i][j][k]+p0[j][k]);
//	    			   }
//	    		   }
//	    	   }
//	    	   
//	 
//		        	     
//	    	    	  
//	    	    //Contrainte 8 		
//	    	   for (int i = 0; i <= n; i++) 
//	    	   {
//	    		   for (int j = 1; j <= n; j++) 
//	    		   {
//	    			   for (int k = 0; k <= F-1; k++) 
//	    			   {
//	        			   IloLinearNumExpr expr = cplex.linearNumExpr();
//	        			   expr.addTerm(c[j][1],1);
//	     	    	       expr.addTerm(c[i][1],-1);
//	     	    	       expr.addTerm(x1[i][j][k],-R);
//	     	    	       expr.setConstant(R);   	    	      
//	    				   cplex.addGe(expr,s1[i][j][k]+p1[j][k]);
//	    			   }
//	    		   }
//	    	   }
//	    	   // Contrainte 7 nadj: 
//	    	   for (int i = 1; i <= n; i++)
//	    	   {
//				   IloLinearNumExpr expr = cplex.linearNumExpr();
//	    		   for (int k = 0; k <= M-1; k++) 
//	    		   {
//	    			   expr.addTerm(p0[i][k],y0[i][k]);
//	    		   }
//				   cplex.addGe(c[i][0],expr);
//
//	    	   }
//	    	// Contrainte 7 nadj2: 
//	    	   for (int i = 1; i <= n; i++) 
//	    	   {
//				   IloLinearNumExpr expr = cplex.linearNumExpr();
//	    		   for (int k = 0; k <= F-1; k++) 
//	    		   {
//	    			   expr.addTerm(p1[i][k],y1[i][k]);
//	    		   }
//				   cplex.addGe(c[i][1],expr);
//
//	    	   }
//	    	   
//	       	// Essai: 
//
//	    	   
////	    	   for (int i = 1; i <= n; i++) {
////	    		   for (int j = 1; j <= n; j++) {
////	    			   for (int k = 0; k <= M-1; k++) {
////	        			   IloLinearNumExpr expr = cplex.linearNumExpr();
////	        			   expr.addTerm(c[i][0],1);
////	     	    	       expr.addTerm(c[j][0],-1);
////	     	    	       expr.addTerm(x0[i][j][k],R);
////	    				   cplex.addGe(expr,s0[j][i][k]+p0[i][k]);
////	    			   }
////	    		   }
////	    	   }
////	    	   for (int i = 1; i <= n; i++) {
////	    		   for (int j = 1; j <= n; j++) {
////	    			   for (int k = 0; k <= F-1; k++) {
////	        			   IloLinearNumExpr expr = cplex.linearNumExpr();
////	        			   expr.addTerm(c[i][1],1);
////	     	    	       expr.addTerm(c[j][1],-1);
////	     	    	       expr.addTerm(x1[i][j][k],R);
////	    				   cplex.addGe(expr,s1[j][i][k]+p1[i][k]);
////	    			   }
////	    		   }
////	    	   }
//	    	   
//	    	   
//	    	    	//Contrainte 9 
//	    	    
//	    	    	for(int i = 1 ; i<=n ; i++)
//	    	    	{
//	    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
//	    	    		IloLinearNumExpr expr2 = cplex.linearNumExpr();
//	    	    		
//	    	    		expr.addTerm(c[i][1], 1);
//	    	    		expr.addTerm(c[i][0], -1);		
//	    	    				
//	    	    		for(int k = 0 ; k<F ; k++  )
//	    	    		{
//	    	    			expr2.addTerm(p1[i][k] , y1[i][k]);
//	    	    		}
//	    	    		
//	    	    		cplex.addGe(expr, expr2);
//	    	    		
//	    	    	}
//	    	    	    	
//	    	    	//Contrainte 10
//	    	    	
//	    	    	for(int k = 0 ; k<M; k ++)
//	    	    	{
//	    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
//	    	    		
//	    	    		for(int j = 0 ; j<=n ; j ++)
//	    	    		{
//	    	    			expr.addTerm(x0[0][j][k], 1);
//	    	    		}
//	    	    		
//	    	    		cplex.addEq(1, expr);
//	    	    	}
//	    	    	
////	    	    	//Contrainte 11
//	    	    	
//	    	    	for(int k = 0 ; k<M; k ++)
//	    	    	{
//	    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
//	    	    		
//	    	    		for(int i = 0 ; i<=n ; i ++)
//	    	    		{
//	    	    			expr.addTerm(x0[i][0][k], 1);
//	    	    		}
//	    	    		
//	    	    		cplex.addEq(1, expr);
//	    	    	}
//	  	
////	    	    	//Contrainte 12
//	    	    	
//	    	    	for(int k = 0 ; k<F; k ++)
//	    	    	{
//	    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
//	    	    		
//	    	    		for(int j = 0 ; j<=n ; j ++)
//	    	    		{
//	    	    			expr.addTerm(x1[0][j][k], 1);
//	    	    		}
//	    	    		
//	    	    		cplex.addEq(1, expr);
//	    	    	}
//	    	    	
////	    	    	//Contrainte 13
////	    	    	
//	    	    	for(int k = 0 ; k<F; k ++)
//	    	    	{
//	    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
//	    	    		
//	    	    		for(int i = 0 ; i<=n ; i ++)
//	    	    		{
//	    	    			expr.addTerm(x1[i][0][k], 1);
//	    	    		}
//	    	    		
//	    	    		cplex.addEq(1, expr);
//	    	    	} 
//	    	   
//	    	   // Contrainte 14: 
//	    	    for (int i = 0; i <= n; i++) 
//	    	    {
//	    		  cplex.addGe(W[0], c[i][1]);
//	    	    }
//	    	    
//	    	      	      	    	        		
//	    	    // Objectif: on cherche � minimiser la date de fin au plus tard de toutes les t�ches
//	    	   // et � ,p�naliser les retards)
//	    	    
//	    	    cplex.addObjective(IloObjectiveSense.Minimize, Z);
//
//	    	    // Objective function
//	    	    IloLinearNumExpr exprObj = cplex.linearNumExpr();
//	    	   
//	    	      exprObj.addTerm(W[0],1);
//	    	      
////	    	      for( int i = 0 ; i < n ; i ++){
////	    	    	  exprObj.addTerm(pen[i], c[i][1]);
////	    	    	  double expr = -pen[i]*d[i];
////	    	    	  exprObj.setConstant(expr);
////	    	      }
//	    	      
//	    	    cplex.addEq(exprObj, Z);
//
//	    	    // Résolution (result est �  vrai si cplex a trouvé une solution réalisable)
//	    	    boolean result = cplex.solve();
//
//	    	    // Affichage des résultats
//	    	    if (result) {
//	    	    //  System.out.println("*** " + cplex.getStatus() + "\n*** Valeur trouvée: " + cplex.getObjValue() + " ***");
//
//	    	    // System.out.println("La date de fin d'ordo est : " + cplex.getValue(Z));
//	    	         	   
//	    	      DecimalFormatSymbols symb = new DecimalFormatSymbols();
//	    	      symb.setDecimalSeparator('.');
//	    	      DecimalFormat df = new DecimalFormat("#.00", symb);
//	    	      // df.setDecimalFormatSymbols()
//	    	      System.out.println("la date de fin d'ordo est : " + df.format(cplex.getObjValue()) + " min");
//	    	      for (int i = 0; i <= n; i++) {
//	    		      for (int j = 0; j <= n; j++) {
//	        		      for (int k = 0; k <= M-1; k++) {
//	        		    	  if(cplex.getValue(x0[i][j][k])>0.5) {
//	        		    		  System.out.println(x0[i][j][k]+"="+Math.ceil(cplex.getValue(x0[i][j][k])));
//	        		    	  }
//	    		  	  
//	    		         }
//	    		      }
//	    		    }
//	    	      for (int i = 0; i <= n; i++) {
//	    		      for (int j = 0; j <= n; j++) {
//	        		      for (int k = 0; k < F; k++) {
//	        		    	  if(cplex.getValue(x1[i][j][k])>0.5) {
//	        		    		  System.out.println(x1[i][j][k]+"="+Math.ceil(cplex.getValue(x1[i][j][k])));
//	        		    	  }
//	    		  	  
//	    		         }
//	    		      }
//	    		    }
//
//	    	      for (int i = 0; i <= n; i++) {
//	        		     for (int k = 0; k < M; k++) {
//	        		    	  if(cplex.getValue(y0[i][k])>0.5) {
//	        		    		  System.out.println(y0[i][k]+"="+cplex.getValue(y0[i][k]));
//	        		    	  }
//	    		      }
//	    		    }
//	    	      
//	    	      for (int i = 0; i <= n; i++) {
//	     		     for (int k = 0; k < F; k++) {
//	     		    	  if(cplex.getValue(y1[i][k])>0.5) {
//	     		    		  System.out.println(y1[i][k]+"="+cplex.getValue(y1[i][k]));
//	     		    	  }
//	 		      }
//	 		    }
//	    	      
//	    	      for (int i = 0; i <= n; i++) {
//	    	    	  System.out.println(c[i][0]+"="+cplex.getValue(c[i][0]));	
//	    	    	  System.out.println(c[i][1]+"="+cplex.getValue(c[i][1]));
//	    	    	  }
//	    	      System.out.println("obj = "+cplex.getValue(exprObj));
//	    		      
//	 
//	    	        }
//	    	    else {
//	    	    	System.out.println("Erreur, pas de r�sultat");
//	    	    	//System.out.println(cplex.getModel().toString());
//	    	    	cplex.exportModel("solver_pl.lp");
//	      	  //    System.out.println(cplex.getModel().toString());
//	      	    //  cplex.exportModel("Modele_PL.lp");
//
//	    	    }
//
//	    	    return solution;
//	    		}
//	    		
//	   
//	    
//	    public void solve() throws Exception 
//	    {
//	    		solution = pl_faster();
//	    }
//
return 0 ; 
}



public int nothing(){
// /*import ilog.concert.IloException;
//import ilog.concert.IloLinearNumExpr;
//import ilog.concert.IloNumVar;
//import ilog.concert.IloObjectiveSense;
//import ilog.cplex.IloCplex;
//import ilog.cplex.IloCplex.DoubleParam;
//import ilog.concert.IloException;
//import ilog.concert.IloLinearNumExpr;
//import ilog.concert.IloNumVar;
//import ilog.concert.IloObjectiveSense;
//import ilog.cplex.IloCplex;
//import ilog.cplex.IloCplex.DoubleParam;
//
//
//import java.text.DecimalFormat;
//import java.text.DecimalFormatSymbols;
//
//import Instances.InstancesMachines;
//import Instances.SolutionMachine;
//
//import java.text.DecimalFormat;
//import java.text.DecimalFormatSymbols;
//
//    	public class Solver {
//
//    	  // Temps limite en secondes
//    	  private static final double TimeLimit = 100000000;
//    	  
//    	  /** Stocke la solution du probl�me */
//    	  private SolutionMachine solution;
//    	  
//    	  /** Donnn�es du probl�me */
//    	  private InstancesMachines instance;
//    	  
//    	  /** Temps allou� � la r�solution du probl�me */
//    	  private long time;
//
//    	// --------------------------------------------
//    		// --------------- ACCESSEURS -----------------
//    		// --------------------------------------------
//
//    		// Pour acc�der aux attributs de la classe TSPSolver
//
//    		/** @return la solution du probl�me */
//    		public SolutionMachine getSolution() {
//    			return solution;
//    		}
//
//    		/** @return Donnn�es du probl�me de TSP */
//    		public InstancesMachines getInstance() {
//    			return instance;
//    		}
//
//    		/** @return Temps allou� � la r�solution du probl�me */
//    		public long getTime() {
//    			return time;
//    		}
//    		
//    		
//    		/**
//    		 * Fixe la solution du probl�me
//    		 * 
//    		 * @param m_solution
//    		 *          : la solution du probl�me
//    		 */
//    		public void setSolution(SolutionMachine m_solution) {
//    			this.solution = m_solution;
//    		}
//
//    		/**
//    		 * Fixe l'instance du probl�me
//    		 * 
//    		 * @param m_instance
//    		 *          : l'instance du probl�me
//    		 */
//    		public void setInstance(InstancesMachines m_instance) {
//    			this.instance = m_instance;
//    		}
//
//    		/**
//    		 * Fixe le temps allou� � la r�solution du probl�me
//    		 * 
//    		 * @param time
//    		 *          : temps allou� � la r�solution du probl�me
//    		 */
//    		public void setTime(long time) {
//    			this.time = time;
//    		}
//
//    	  /**
//    	   * @param args
//    	   * @throws IloException
//    	   */
//    	  public SolutionMachine  pl_faster() throws Exception {
//    	    // Données
//
//    	    int n = instance.getNbJobs(); /* le nombre de jobs � ordonnancer */
//    	    int M = instance.getNbM(); /* les m machines du niveau 1*/
//    	    int L =(int) instance.getNbNvx() ; /* les levels */
//    		int F = instance.getNbF(); /* les f machines du niveau 2*/
//    		double R = instance.getBigM(); /* param�tre tr�s grand*/
//    		
//    		//R � d�terminer
//    		
//    		/** 
//    		 * setup time entre le job i et le job j 
//    		 * sur la machine k au niveau 1
//    		 */
//    		double [][][] s1= new double [n+1][n+1][M]; 
//    		
//    		//remplissage 
//    		
//    		for ( int i = 0 ; i<n+1 ; i ++){
//    			for (int j = 0; j<n+1;j++){
//    				for(int k=0; k<M;k++){
//    			s1[i][j][k] = instance.getS1(i, j, k);
//    				}
//    			}
//    		}
//    		
//    		/** setup time entre le job i et le job j sur la machine k au niveau 2*/   		
//    		double [][][] s2= new double [n+1][n+1][F]; 
//    		
//    		for ( int i = 0 ; i<n+1 ; i ++){
//    			for (int j = 0; j<n+1;j++){
//    				for(int k=0; k<M;k++){
//    			s2[i][j][k] = instance.getS2(i, j, k);
//    				}
//    			}
//    		}
//    		
//    		/** temps de process du job i sur la machine k au niveau 1 */
//    		double [][] p1= new double[n+1][M];
//    		
//    		
//    		for ( int i = 0 ; i<n+1 ; i ++){
//    				for(int k=0; k<M;k++){
//    			p1[i][k] = instance.getP1(i, k);
//    				}
//    			
//    		}
//    		
//    		/** temps de process du job i sur la machine k au niveau 2 */
//    		double [][] p2= new double[n+1][F];
//        	    
//    		for ( int i = 0 ; i<n+1 ; i ++){
//				for(int k=0; k<M;k++){
//			p2[i][k] = instance.getP2(i, k);
//				}
//			}
//   
//    		 /** due-dates de chaque job */
//    		double [] d = new double[n+1];
//    				
//    		/** penalit� de retard */
//    		double [] pen = new double[n+1];
//    		
//    		
//    	    // Création de l'environnement Cplex
//    	    IloCplex cplex = new IloCplex();
//    	    cplex.setParam(DoubleParam.TiLim, TimeLimit);
//
//    	    
//    	    /* VARIABLES*/
//    	    
//    	    /*x1[i,j,k]=1 si le job j est ordonnanc� apr�s le job i sur la machine k au niveau 1 */
//    	    IloNumVar[][][] x1 = new IloNumVar[n+1][n+1][M];
//    	    /*x2[i,j,k]=1 si le job j est ordonnanc� apr�s le job i sur la machine k au niveau 2 */
//    	    IloNumVar[][][] x2 = new IloNumVar[n+1][n+1][F];
//    	    /*y1[i,k]=1 si le job i est ordonnanc� sur la machine k au niveau 1 */
//    	    IloNumVar[][] y1 = new IloNumVar[n+1][M];
//    	    /*y2[i,k]=1 si le job i est ordonnanc� sur la machine k au niveau 2 */
//    	    IloNumVar[][] y2 = new IloNumVar [n+1][F];
//    	    /*date de fin du job i au niveau l*/
//    	    IloNumVar [][] c = new IloNumVar [n+1][L];
//    	    /*variable qui va �tre sup�rieure � toutes les variables c[i,l] (cf fonction-objectif) */
//    	    IloNumVar [] W = new IloNumVar [1];
//
//    	    // Instancier les variables
//    	    for (int i = 0; i <= n; i++)
//    		    for (int j = 0; j <= n; j++)
//    			    for (int k = 0; k < M; k++)
//    			    	if (i!=j) {
//    			    	x1[i][j][k] = cplex.intVar(0,1, "x1" + i+"_"+j+"_"+k);
//    			    	}
//    			    	else { 
//        			    x1[i][j][k] = cplex.intVar(0,0);
//    			    	}
//    			    	
//    	    
//    	    
//    	    for (int i = 0; i <= n; i++)
//    		    for (int j = 0; j <= n; j++)
//    			    for (int k = 0; k < F; k++)
//    	    				if (i!=j) {
//    	    				x2[i][j][k] = cplex.intVar(0,1, "x1" + i+"_"+j+"_"+k);
//    	    					}
//    	    					else  {
//    	    					x2[i][j][k] = cplex.intVar(0,0);
//    	    					}
//    	    
//    	    for (int i = 0; i <= n; i++)
//    	    	for (int k = 0; k < F; k++)
//    			    	y1[i][k] = cplex.intVar(0,1, "y1" + i+"_"+k);
//
//    	   for(int i = 0 ; i <= n; i++){
//    		   for (int k = 0 ; k<F ; k++){
//    			   y2[i][k] = cplex.intVar(0, 1, "y2" + i + "_" + k);
//    		   }
//    	   }
//    	   
//    	   for(int i=0; i<= n ; i ++){
//    		   for(int ln = 0 ; ln<L ; ln++){
//    			   c[i][ln] = cplex.numVar(0, R , "c" + i + "_" + ln);
//    		   }
//    	   }
//    	   for (int i=0 ; i<1; i++) {
//    		   W[i]= cplex.numVar(0,10000000,"W");
//    	   }
//
//    	    // Variable représentant la fonction objectif
//    	    IloNumVar Z;
//    	    Z = cplex.numVar(0, Double.MAX_VALUE, "obj");
//    	    
//    	    // Contrainte 1: 
//    	    for (int i = 1; i <= n; i++) {
//    	      IloLinearNumExpr expr = cplex.linearNumExpr();
//    	      for (int k = 0; k < M; k++) {
//    	        expr.addTerm(1, y1[i][k]);
//    	      }
// 		     cplex.addEq(1, expr);
//
//          }
//    	    
//    	 // Contrainte 2: 
//    	    for (int i = 1; i <= n; i++) {
//    		      IloLinearNumExpr expr = cplex.linearNumExpr();
//    		      for (int k = 0; k < F; k++) {
//    		        expr.addTerm(1, y2[i][k]);
//    		      }
//			      cplex.addEq(1, expr);
//
//    		    }
//
//    	    // Contrainte 3 : 
//    	    for (int i = 0; i <= n; i++) {
//    		    for (int k = 0; k < M; k++) {
//    	    IloLinearNumExpr expr = cplex.linearNumExpr();
//    	    for (int j = 0; j <= n; j++) {
//    	      expr.addTerm(1, x1[i][j][k]);
//    		    }
//  		    cplex.addEq(expr,1);
//    		   }
//    	    }
//    	    	    
//    	 // Contrainte 4: 
//    	    for (int i = 0; i <= n; i++) {
//    		    for (int k = 0; k < F; k++) {
//    	    IloLinearNumExpr expr = cplex.linearNumExpr();
//    	    for (int j = 0; j <= n; j++) {
//    	      expr.addTerm(1, x2[i][j][k]);
//    		  }
//  		  cplex.addEq(1, expr);
//
//    		 }
//    	    }
//    	    
//    		 // Contrainte 5: 
//    	    for (int i = 0; i <= n; i++) {
//    		    for (int k = 0; k < M; k++) {
//    	    IloLinearNumExpr expr = cplex.linearNumExpr();
//    	    for (int j = 0; j <= n; j++) {
//    	      expr.addTerm(1, x1[j][i][k]);
//    	    	}
//  		  cplex.addEq(1, expr);
//
//    		   }
//    	    }
//    	    	    
//    		 // Contrainte 6: 
//    	    for (int i = 0; i <= n; i++) {
//    		    for (int k = 0; k < F; k++) {
//    	    IloLinearNumExpr expr = cplex.linearNumExpr();
//    	    for (int j = 0; j <= n; j++) {
//    	      expr.addTerm(1, x2[j][i][k]);
//    	    }
//  		  cplex.addEq(1, expr);
//
//    		    }
//    	    }
//    	    	    		
//    	  // Contrainte 7: 
//    	   for (int i = 0; i <= n; i++) {
//    		   for (int j = 0; j <= n; j++) {
//    			   IloLinearNumExpr expr = cplex.linearNumExpr();
//    	    	      expr.addTerm(c[j][1],1);
//    	    	      expr.addTerm(c[i][1],-1);
//    	    	      for (int k=0; k<M; k++) {
//    	    	    	  expr.addTerm(x1[i][j][k],-R);
//    	    	      }
//    	    	      expr.setConstant(R);
//    				  IloLinearNumExpr expr1 = cplex.linearNumExpr();
//    				  for (int k=0; k<M; k++) {
//    	    	    	  expr1.addTerm(s1[i][j][k],y1[i][k]);
//    	    	    	  expr1.addTerm(p1[j][k],y1[i][k]);
//    	    	      }
//    				  cplex.addGe(expr,expr1);
//    		   }
//    		   }
//    	   
//    	   
//    	  
//    	    //Contrainte 8 		
//    	    for(int i = 0 ; i <= n ; i ++){
//    	    	for( int j = 0 ; j <= n ; j++){
//    	    		
//    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
//    	    		IloLinearNumExpr expr2 = cplex.linearNumExpr();
//    	    		
//    	    		expr.addTerm(c[j][1],1);
//    	    		expr.addTerm(c[i][1],-1);
//    	    		
//    	    		for(int k = 0 ; k<F;k++){
//    	    		expr.addTerm(x2[i][j][k],-R);
//    	    		}
//    	    		
//    	    		expr.setConstant(R);
//    	    		
//    	    		for(int k =0 ; k<F ; k++){
//    	    			expr2.addTerm(s2[i][j][k], y2[i][k]);
//    	    			expr2.addTerm(p2[j][k], y2[i][k]);
//    	    		}
//    	    		
//    	    		cplex.addGe(expr,expr2);
//    	    		
//    	    	}
//    	    }
//    	    	//Contrainte 9 
//    	    
//    	    	for(int i = 1 ; i<=n ; i++){
//    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
//    	    		IloLinearNumExpr expr2 = cplex.linearNumExpr();
//    	    		
//    	    		expr.addTerm(c[i][1], 1);
//    	    		expr.addTerm(c[i][0], -1);		
//    	    				
//    	    		for(int k = 0 ; k<F ; k++  ){
//    	    			expr2.addTerm(p2[i][k] , y2[i][k]);
//    	    		}
//    	    		
//    	    		cplex.addGe(expr, expr2);
//    	    		
//    	    	}
//    	    	    	
//    	    	//Contrainte 10
//    	    	
//    	    	for(int k = 0 ; k<M; k ++){
//    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
//    	    		
//    	    		for(int j = 1 ; j<=n ; j ++){
//    	    			expr.addTerm(x1[0][j][k], 1);
//    	    		}
//    	    		
//    	    		cplex.addEq(1, expr);
//    	    	}
//    	    	
//    	    	//Contrainte 11
//    	    	
//    	    	for(int k = 0 ; k<M; k ++){
//    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
//    	    		
//    	    		for(int i = 1; i<=n ; i ++){
//    	    			expr.addTerm(x1[i][0][k], 1);
//    	    		}
//    	    		
//    	    		cplex.addEq(1, expr);
//    	    	}
//    	    	
//    	    	//Contrainte 12
//    	    	
//    	    	for(int k = 0 ; k<F; k ++){
//    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
//    	    		
//    	    		for(int j = 1 ; j<=n ; j ++){
//    	    			expr.addTerm(x2[0][j][k], 1);
//    	    		}
//    	    		
//    	    		cplex.addEq(1, expr);
//    	    	}
//    	    	
//    	    	//Contrainte 13
//    	    	
//    	    	for(int k = 0 ; k<F; k ++){
//    	    		IloLinearNumExpr expr = cplex.linearNumExpr();
//    	    		
//    	    		for(int i = 1 ; i<=n ; i ++){
//    	    			expr.addTerm(x2[i][0][k], 1);
//    	    		}
//    	    		
//    	    		cplex.addEq(1, expr);
//    	    	} 
//    	   
//    	   // Contrainte 14: 
//    	    for (int i = 1; i <= n; i++) {
//    		  cplex.addGe(W[0], c[i][1]);
//    	    }
//    		    
//    	      	      	    	        		
//    	    // Objectif: on cherche � minimiser la date de fin au plus tard de toutes les t�ches
//    	   // et � ,p�naliser les retards)
//    	    
//    	    cplex.addObjective(IloObjectiveSense.Minimize, Z);
//
//    	    // Objective function
//    	    IloLinearNumExpr exprObj = cplex.linearNumExpr();
//    	   
//    	      exprObj.addTerm(W[0],1);
//    	      
//    	      for( int i = 0 ; i < n ; i ++){
//    	    	  exprObj.addTerm(pen[i], c[i][1]);
//    	    	  double expr = -pen[i]*d[i];
//    	    	  exprObj.setConstant(expr);
//    	      }
//    	      
//    	    cplex.addEq(exprObj, Z);
//
//    	    // Résolution (result est �  vrai si cplex a trouvé une solution réalisable)
//    	    boolean result = cplex.solve();
//
//    	    // Affichage des résultats
//    	    if (result) {
//    	      System.out.println("*** " + cplex.getStatus() + "\n*** Valeur trouvée: " + cplex.getObjValue() + " ***");
//
//    	      System.out.println("La date de fin d'ordo est : " + cplex.getValue(W[0]));
//    	      
//    	    }
//    	    return solution;
//    	  }
//    	  public void solve() throws Exception {
//    		  solution = pl_faster();
//    	  }
//    	}
////    	      DecimalFormatSymbols symb = new DecimalFormatSymbols();
////    	      symb.setDecimalSeparator('.');
////    	      DecimalFormat df = new DecimalFormat("#.00", symb);
////    	      // df.setDecimalFormatSymbols()
////    	      System.out.println("Le coût total est de " + df.format(cplex.getObjValue()) + " euros");
////    	      for (int i = 0; i < n; i++) {
////    		      for (int j = 0; j < n; j++) {
////
////        		      for (int k = 0; k < M; k++) {
////        		    	  if(cplex.getValue(x1[i][j][k])>0.5) {
////        		    		  System.out.println(x1[i][j][k]+"="+cplex.getValue(x1[i][j][k]));
////        		    	  }
////    		  	  
////    		         }
////    		      }
////    		    }
////    	      for (int i = 0; i <= n; i++) {
////    		      for (int j = 0; j <= n; j++) {
////        		      for (int k = 0; k < F; k++) {
////        		    	  if(cplex.getValue(x2[i][j][k])>0.5) {
////        		    		  System.out.println(x2[i][j][k]+"="+cplex.getValue(x2[i][j][k]));
////        		    	  }
////    		  	  
////    		         }
////    		      }
////    		    }
////    	      for (int i = 0; i <= n; i++) {
////    	    	  System.out.println(c[i][1]+"="+cplex.getValue(c[i][1]));	
////    	    	  //System.out.println(c[i][0]+"="+cplex.getValue(c[i][]));
////        	}
////    		      
//// 
////    	        }
////    	    else {
////    	    	System.out.println("tata");
////    	    }
////
////    	  }
////    	}
////        		      for (int k = 0; k < M; k++) {
////    		    		  System.out.println(x1[i][j][k]);
////    		  	  
////    		         }
////    		      }
////    		    }
////    	        }
////    	    else {
////    	    	System.out.println("tata");
////    	    }
//
//    	  
//   
	return 0;
}

}
    	
    	
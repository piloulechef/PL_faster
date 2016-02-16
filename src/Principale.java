import Instances.InstancesMachines;


public class Principale {
	
	
	public static void main(String[] arg) {
	    String filename = null;
	    long max_time = 300;
	    boolean verbose = false;
	    //boolean graphical = false;

	     filename = arg[0];
	     

	    // --- create and solve problems ---
	    try {
	      Solver solver = new Solver();
	      // create a new problem; data is read from file filename
	      InstancesMachines prob = new InstancesMachines(filename);
	      solver.setInstancesMachines(prob);
	      solver.setSolution(new Solution(prob,0));

	      // print the data [uncomment if wanted]
	      // prob.printData(System.err);

	      // solve the problem
	      long t = System.currentTimeMillis();
	      solver.solve();
	      t = System.currentTimeMillis() - t;

	      
	      // evaluate the solution (and check whether it is feasible)
	      boolean feasible = solver.getSolution().validate();

	      // sortie du programme: fileName;routeLength;t;e
	      // e est un code d'erreur :
	      // e = 0 -> solution réalisable dans les temps
	      // e = 1 -> solution non réalisable
	      // e= 2 -> temps dépassé
	      int e = 0;
	      if (!feasible)
	        e = 1;
	      else {
	        if (t > (max_time + 1) * 1000) {
	          e = 2;
	          System.err.println("Temps dépassé !!!");
	        }
	      }
	      System.out.println(filename + ";" + solver.getSolution().getObjectif() + ";" + t + ";" + e);

	      // if verbose, print the solution
	      if (verbose) {
	        solver.getSolution().print(System.err);
	        if (e == 1)
	          System.err.println("Erreur dans la solution: " + solver.getSolution().getError());
	      }

	      // If graphical and no error, draw
	      if (feasible && graphical) {
	        // Graphical solution
	    	  
	        new MainFrame(prob, solver.getSolution());

	      }
	    } catch (IOException e) {
	      System.err.println("Erreur dans la lecture du fichier: " + e.getMessage());
	      System.exit(1);
	    } catch (Exception e) {
	      System.err.printf("error: %s", e.getMessage());
	      System.err.println();
	      e.printStackTrace(System.err);
	      System.exit(1);
	    }
	    return;
	  }
	}


}

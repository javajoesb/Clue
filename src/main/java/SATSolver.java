/**
 * SATSolver.java - a simple Java interface to the zchaff SAT solver.
 * See http://cs.gettysburg.edu/~tneller/nsf/clue/ for details.
 *
 * @author Todd Neller
 * @version 1.0
 *

Copyright (C) 2005 Todd Neller

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

Information about the GNU General Public License is available online at:
  http://www.gnu.org/licenses/
To receive a copy of the GNU General Public License, write to the Free
Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
02111-1307, USA.

 */

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class SATSolver {
  public static final int FALSE = -1;
  public static final int UNKNOWN = 0;
  public static final int TRUE = 1;

  public static void main(String[] args) {
    // Liar and truth-teller example test code:
    final int[][] clauses = { { -1, -2 }, { 2, 1 }, { -2, -3 }, { 3, 2 }, { -3, -1 }, { -3, -2 }, { 1, 2, 3 } };
    final SATSolver s = new SATSolver();
    for (int i = 0; i < clauses.length; i++) {
      s.addClause(clauses[i]);
    }
    System.out.println("Knowledge base is satisfiable: " + s.makeQuery());
    System.out.print("Is Cal a truth-teller? ");
    final int result = s.testLiteral(3);
    if (result == FALSE) {
      System.out.println("No.");
    } else if (result == TRUE) {
      System.out.println("Yes.");
    } else {
      System.out.println("Unknown.");
    }
  }

  public ArrayList<int[]> clauses = new ArrayList<int[]>();

  public ArrayList<int[]> queryClauses = new ArrayList<int[]>();

  public void addClause(int[] clause) {
    clauses.add(clause.clone());
  }

  public void addQueryClause(int[] clause) {
    queryClauses.add(clause.clone());
  }

  public void clearClauses() {
    clauses.clear();
  }

  public void clearQueryClauses() {
    queryClauses.clear();
  }

  public boolean makeQuery() {
    try {
      int maxVar = 0;
      final ArrayList<int[]> allClauses = new ArrayList<int[]>(clauses);
      allClauses.addAll(queryClauses);
      for (final int[] clause : allClauses) {
        for (final int literal : clause) {
          maxVar = Math.max(Math.abs(literal), maxVar);
        }
      }
      final PrintStream out = new PrintStream(new File("query.cnf"));
      out.println("c This DIMACS format CNF file was generated by SatSolver.java");
      out.println("c Do not edit.");
      out.println("p cnf " + maxVar + " " + allClauses.size());
      for (final int[] clause : allClauses) {
        for (final int literal : clause) {
          out.print(literal + " ");
        }
        out.println("0");
      }
      out.close();
      final Process process = Runtime.getRuntime().exec("./zchaff query.cnf");
      final Scanner sc = new Scanner(process.getInputStream());
      sc.findWithinHorizon("RESULT:", 0);
      final String result = sc.next();
      sc.close();
      process.waitFor();
      return result.equals("SAT");
    } catch (final Exception e) {
      System.out.println(e);
    }
    return false;
  }

  public int testLiteral(int literal) {
    int result = UNKNOWN;
    clearQueryClauses();
    final int[] clauseT = { literal };
    addQueryClause(clauseT);
    if (!makeQuery()) {
      result = FALSE;
    } else {
      clearQueryClauses();
      final int[] clauseF = { -literal };
      addQueryClause(clauseF);
      if (!makeQuery()) {
        result = TRUE;
      }
    }
    clearQueryClauses();
    return result;
  }
}

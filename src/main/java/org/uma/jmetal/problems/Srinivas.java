//  Srinivas.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package org.uma.jmetal.problems;

import org.uma.jmetal.core.Problem;
import org.uma.jmetal.core.Solution;
import org.uma.jmetal.core.Variable;
import org.uma.jmetal.encodings.solutiontype.BinaryRealSolutionType;
import org.uma.jmetal.encodings.solutiontype.RealSolutionType;
import org.uma.jmetal.util.JMetalException;

/**
 * Class representing problem Srinivas
 */
public class Srinivas extends Problem {

  /**
   *
   */
  private static final long serialVersionUID = 7618455041050464606L;


  /**
   * Constructor.
   * Creates a default instance of the Srinivas problem
   *
   * @param solutionType The solution type must "Real" or "BinaryReal".
   */
  public Srinivas(String solutionType) throws JMetalException {
    numberOfVariables_ = 2;
    numberOfObjectives_ = 2;
    numberOfConstraints_ = 2;
    problemName_ = "Srinivas";

    lowerLimit_ = new double[numberOfVariables_];
    upperLimit_ = new double[numberOfVariables_];
    for (int var = 0; var < numberOfVariables_; var++) {
      lowerLimit_[var] = -20.0;
      upperLimit_[var] = 20.0;
    }

    if (solutionType.compareTo("BinaryReal") == 0) {
      solutionType_ = new BinaryRealSolutionType(this);
    } else if (solutionType.compareTo("Real") == 0) {
      solutionType_ = new RealSolutionType(this);
    } else {
      throw new JMetalException("Error: solution type " + solutionType + " invalid");
    }
  }

  /**
   * Evaluates a solution
   *
   * @param solution The solution to evaluate
   * @throws org.uma.jmetal.util.JMetalException
   */
  public void evaluate(Solution solution) throws JMetalException {
    Variable[] variable = solution.getDecisionVariables();

    double[] f = new double[numberOfObjectives_];

    double x1 = variable[0].getValue();
    double x2 = variable[1].getValue();
    f[0] = 2.0 + (x1 - 2.0) * (x1 - 2.0) + (x2 - 1.0) * (x2 - 1.0);
    f[1] = 9.0 * x1 - (x2 - 1.0) * (x2 - 1.0);

    solution.setObjective(0, f[0]);
    solution.setObjective(1, f[1]);
  } // evaluate


  /**
   * Evaluates the constraint overhead of a solution
   *
   * @param solution The solution
   * @throws org.uma.jmetal.util.JMetalException
   */
  public void evaluateConstraints(Solution solution) throws JMetalException {
    Variable[] variable = solution.getDecisionVariables();

    double[] constraint = new double[this.getNumberOfConstraints()];

    double x1 = variable[0].getValue();
    double x2 = variable[1].getValue();

    constraint[0] = 1.0 - (x1 * x1 + x2 * x2) / 225.0;
    constraint[1] = (3.0 * x2 - x1) / 10.0 - 1.0;

    double total = 0.0;
    int number = 0;
    for (int i = 0; i < this.getNumberOfConstraints(); i++) {
      if (constraint[i] < 0.0) {
        number++;
        total += constraint[i];
      }
    }

    solution.setOverallConstraintViolation(total);
    solution.setNumberOfViolatedConstraint(number);
  } // evaluateConstraints
} // Srinivas
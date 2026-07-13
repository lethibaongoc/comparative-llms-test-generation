package org.apache.commons.math.analysis.solvers;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.commons.math.analysis.UnivariateRealFunction;

public class BisectionSolverTest {

    @Test
    void testSolve() {
        UnivariateRealFunction f = x -> x;
        BisectionSolver solver = new BisectionSolver();
        double result = solver.solve(f, -1, 1, 0.5);
        assertTrue(result >= -1 && result <= 1);
    }

    @Test
    void testSolveIgnoreInitialGuess() {
        UnivariateRealFunction f = x -> x;
        BisectionSolver solver = new BisectionSolver();
        double result1 = solver.solve(f, -1, 1, -10);
        double result2 = solver.solve(f, -1, 1, 10);
        assertEquals(result1, result2, 1e-6);
    }

    @Test
    void testSolveSameAsSolveWithoutInitial() {
        UnivariateRealFunction f = x -> x;
        BisectionSolver solver = new BisectionSolver();
        double result1 = solver.solve(f, -1, 1, 0.5);
        double result2 = solver.solve(f, -1, 1);
        assertEquals(result1, result2, 1e-6);
    }
}

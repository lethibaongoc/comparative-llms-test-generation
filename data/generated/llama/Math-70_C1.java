package org.apache.commons.math.analysis.solvers;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.commons.math.analysis.UnivariateRealFunction;

public class BisectionSolverTest {

    @Test
    public void testSolve() throws MaxIterationsExceededException, FunctionEvaluationException {
        BisectionSolver solver = new BisectionSolver();
        UnivariateRealFunction f = x -> x * x - 2;
        double min = 0;
        double max = 2;
        double initial = 1;
        double result = solver.solve(f, min, max, initial);
        assertTrue(result >= min && result <= max);
        assertEquals(0, f.value(result), 1e-6);
    }

    @Test
    public void testSolveMinEqualsMax() throws MaxIterationsExceededException, FunctionEvaluationException {
        BisectionSolver solver = new BisectionSolver();
        UnivariateRealFunction f = x -> x * x - 2;
        double min = 1;
        double max = 1;
        double initial = 1;
        assertThrows(MaxIterationsExceededException.class, () -> solver.solve(f, min, max, initial));
    }

    @Test
    public void testSolveMinGreaterThanMax() throws MaxIterationsExceededException, FunctionEvaluationException {
        BisectionSolver solver = new BisectionSolver();
        UnivariateRealFunction f = x -> x * x - 2;
        double min = 2;
        double max = 1;
        double initial = 1;
        assertThrows(MaxIterationsExceededException.class, () -> solver.solve(f, min, max, initial));
    }

    @Test
    public void testSolveFunctionEvaluationException() throws MaxIterationsExceededException, FunctionEvaluationException {
        BisectionSolver solver = new BisectionSolver();
        UnivariateRealFunction f = x -> {
            throw new FunctionEvaluationException("Test exception");
        };
        double min = 0;
        double max = 2;
        double initial = 1;
        assertThrows(FunctionEvaluationException.class, () -> solver.solve(f, min, max, initial));
    }
}

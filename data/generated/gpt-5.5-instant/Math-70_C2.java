@Test
public void testSolve_ignoresInitialAndFindsRoot() throws Exception {
    BisectionSolver solver = new BisectionSolver();
    UnivariateRealFunction function = x -> x * x - 4.0;

    double result = solver.solve(function, 0.0, 3.0, 100.0);

    assertEquals(2.0, result, 1.0e-6);
}

@Test
public void testSolve_differentInitialValuesProduceSameResult() throws Exception {
    BisectionSolver solver = new BisectionSolver();
    UnivariateRealFunction function = x -> x - 1.5;

    double result1 = solver.solve(function, 0.0, 3.0, 0.1);
    double result2 = solver.solve(function, 0.0, 3.0, 2.9);

    assertEquals(result1, result2, 1.0e-10);
    assertEquals(1.5, result1, 1.0e-6);
}

@Test
public void testSolve_initialOutsideIntervalIsIgnored() throws Exception {
    BisectionSolver solver = new BisectionSolver();
    UnivariateRealFunction function = x -> x + 2.0;

    double result = solver.solve(function, -5.0, 0.0, 1000.0);

    assertEquals(-2.0, result, 1.0e-6);
}

@Test
public void testSolve_propagatesFunctionEvaluationException() {
    BisectionSolver solver = new BisectionSolver();
    UnivariateRealFunction function = x -> {
        throw new FunctionEvaluationException(x);
    };

    assertThrows(
        FunctionEvaluationException.class,
        () -> solver.solve(function, -1.0, 1.0, 0.0)
    );
}

@Test
public void testSolve_invalidIntervalThrowsException() {
    BisectionSolver solver = new BisectionSolver();
    UnivariateRealFunction function = x -> x * x + 1.0;

    assertThrows(
        IllegalArgumentException.class,
        () -> solver.solve(function, -1.0, 1.0, 0.0)
    );
}

@Test
void testSolveIgnoresInitialAndFindsRoot() throws Exception {
    BisectionSolver solver = new BisectionSolver();
    UnivariateRealFunction function = x -> x * x - 4.0;

    assertEquals(2.0, solver.solve(function, 0.0, 5.0, 4.5), 1e-6);
    assertEquals(2.0, solver.solve(function, 0.0, 5.0, 0.5), 1e-6);
}

@Test
void testSolveWithInitialMatchesThreeArgumentOverload() throws Exception {
    BisectionSolver solver = new BisectionSolver();
    UnivariateRealFunction function = x -> x - 3.0;

    double expected = solver.solve(function, 0.0, 10.0);
    double actual = solver.solve(function, 0.0, 10.0, 9.0);

    assertEquals(expected, actual, 0.0);
}

@Test
void testSolvePropagatesFunctionEvaluationException() {
    BisectionSolver solver = new BisectionSolver();
    UnivariateRealFunction function = x -> {
        throw new FunctionEvaluationException(x);
    };

    assertThrows(
        FunctionEvaluationException.class,
        () -> solver.solve(function, 0.0, 10.0, 5.0)
    );
}

@Test
public void testSolve_IgnoresInitialGuessAndFindsRoot() throws Exception {
    BisectionSolver solver = new BisectionSolver();
    UnivariateRealFunction f = new UnivariateRealFunction() {
        @Override
        public double value(double x) {
            return x * x - 4.0;
        }
    };

    double result = solver.solve(f, 0.0, 3.0, 1.5);

    assertEquals(2.0, result, 1e-6);
}

@Test
public void testSolve_DifferentInitialGuessProducesSameResult() throws Exception {
    BisectionSolver solver = new BisectionSolver();
    UnivariateRealFunction f = new UnivariateRealFunction() {
        @Override
        public double value(double x) {
            return x * x - 4.0;
        }
    };

    double resultWithInitial = solver.solve(f, 0.0, 3.0, 2.9);
    double resultWithoutInitial = solver.solve(f, 0.0, 3.0);

    assertEquals(resultWithoutInitial, resultWithInitial, 0.0);
}

@Test
public void testSolve_InvalidIntervalThrowsExceptionRegardlessOfInitialGuess() {
    BisectionSolver solver = new BisectionSolver();
    UnivariateRealFunction f = new UnivariateRealFunction() {
        @Override
        public double value(double x) {
            return x * x + 1.0;
        }
    };

    assertThrows(IllegalArgumentException.class, () -> solver.solve(f, -1.0, 1.0, 0.0));
}

@Test
void testSolveDelegatesToSolveWithoutInitial() throws Exception {
    UnivariateRealFunction f = x -> x * x - 4;
    BisectionSolver solver = new BisectionSolver();

    // Bisection method on f(x) = x^2 - 4 with root at x = 2
    double result = solver.solve(f, 0.0, 5.0, 2.5);
    assertEquals(2.0, result, 1e-6);
}

@Test
void testSolveThrowsExceptionWhenNoRoot() {
    UnivariateRealFunction f = x -> x * x + 4; // Always positive, no root
    BisectionSolver solver = new BisectionSolver();

    assertThrows(IllegalArgumentException.class, () -> {
        solver.solve(f, 0.0, 5.0, 2.5);
    });
}

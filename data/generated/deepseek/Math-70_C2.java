@Test
void testSolveDelegatesToTwoArgSolve() {
    UnivariateRealFunction f = x -> x * x - 4;
    BisectionSolver solver = new BisectionSolver();
    double min = 0;
    double max = 5;
    double initial = 2.5;
    double expected = solver.solve(f, min, max);
    double actual = solver.solve(f, min, max, initial);
    assertEquals(expected, actual, 1e-12);
}

@Test
void testSolveWithDifferentInitialValue() {
    UnivariateRealFunction f = x -> x * x - 4;
    BisectionSolver solver = new BisectionSolver();
    double min = 0;
    double max = 5;
    double initial1 = 2.5;
    double initial2 = 4.0;
    double result1 = solver.solve(f, min, max, initial1);
    double result2 = solver.solve(f, min, max, initial2);
    assertEquals(result1, result2, 1e-12);
}

@Test
void testSolveWithNullFunction() {
    BisectionSolver solver = new BisectionSolver();
    assertThrows(NullPointerException.class,
        () -> solver.solve(null, 0, 1, 0.5));
}

@Test
void testSolveWithInvalidBounds() {
    UnivariateRealFunction f = x -> x - 2;
    BisectionSolver solver = new BisectionSolver();
    assertThrows(IllegalArgumentException.class,
        () -> solver.solve(f, 5, 0, 2.5));
}

@Test
void testSolveWithNoRootInInterval() {
    UnivariateRealFunction f = x -> x * x + 1;
    BisectionSolver solver = new BisectionSolver();
    assertThrows(IllegalArgumentException.class,
        () -> solver.solve(f, -1, 1, 0));
}

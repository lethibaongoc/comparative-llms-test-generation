@Test
void shouldCallTwoArgSolveMethod() throws MaxIterationsExceededException, FunctionEvaluationException {
    BisectionSolver solver = new BisectionSolver();
    UnivariateRealFunction f = x -> x * x - 4;
    double min = 0.0;
    double max = 5.0;
    double initial = 2.5;

    double result = solver.solve(f, min, max, initial);

    assertThat(result).isEqualTo(solver.solve(f, min, max));
}

@Test
void shouldReturnRootWhenInitialWithinBounds() throws MaxIterationsExceededException, FunctionEvaluationException {
    BisectionSolver solver = new BisectionSolver();
    UnivariateRealFunction f = x -> x - 3;
    double min = 0.0;
    double max = 10.0;
    double initial = 5.0;

    double result = solver.solve(f, min, max, initial);

    assertThat(result).isEqualTo(3.0);
}

@Test
void shouldThrowMaxIterationsExceededExceptionWhenNoRoot() {
    BisectionSolver solver = new BisectionSolver();
    UnivariateRealFunction f = x -> x * x + 1;
    double min = -10.0;
    double max = 10.0;
    double initial = 0.0;

    assertThatThrownBy(() -> solver.solve(f, min, max, initial))
        .isInstanceOf(MaxIterationsExceededException.class);
}

@Test
void shouldHandleNegativeRoot() throws MaxIterationsExceededException, FunctionEvaluationException {
    BisectionSolver solver = new BisectionSolver();
    UnivariateRealFunction f = x -> x + 5;
    double min = -10.0;
    double max = 0.0;
    double initial = -5.0;

    double result = solver.solve(f, min, max, initial);

    assertThat(result).isEqualTo(-5.0);
}

@Test
void shouldIgnoreInitialParameterAndUseTwoArgSolve() throws MaxIterationsExceededException, FunctionEvaluationException {
    BisectionSolver solver = new BisectionSolver();
    UnivariateRealFunction f = x -> x - 2;
    double min = 1.0;
    double max = 3.0;
    double initial = 100.0;

    double result = solver.solve(f, min, max, initial);
    double expected = solver.solve(f, min, max);

    assertThat(result).isEqualTo(expected);
}

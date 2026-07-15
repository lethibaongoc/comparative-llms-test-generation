@Test
void solve_DelegatesToThreeParameterSolveMethod() throws Exception {
    // Arrange
    UnivariateRealFunction mockFunction = mock(UnivariateRealFunction.class);
    double min = 0.0;
    double max = 1.0;
    double initial = 0.5;
    double expectedRoot = 0.25;

    // Spy or subclass the BisectionSolver to mock/verify the overloaded solve(f, min, max) call
    BisectionSolver solver = spy(new BisectionSolver());
    doReturn(expectedRoot).when(solver).solve(mockFunction, min, max);

    // Act
    double actualRoot = solver.solve(mockFunction, min, max, initial);

    // Assert
    assertEquals(expectedRoot, actualRoot, "The 4-parameter solve method should return the result of the 3-parameter solve method.");
    verify(solver).solve(mockFunction, min, max);
}

@Test
void solve_PropagatesMaxIterationsExceededException() throws Exception {
    // Arrange
    UnivariateRealFunction mockFunction = mock(UnivariateRealFunction.class);
    double min = 0.0;
    double max = 1.0;
    double initial = 0.5;

    BisectionSolver solver = spy(new BisectionSolver());
    doThrow(new MaxIterationsExceededException(100))
        .when(solver).solve(mockFunction, min, max);

    // Act & Assert
    assertThrows(MaxIterationsExceededException.class, () -> {
        solver.solve(mockFunction, min, max, initial);
    }, "MaxIterationsExceededException should propagate upward from the delegated solve method.");
}

@Test
void solve_PropagatesFunctionEvaluationException() throws Exception {
    // Arrange
    UnivariateRealFunction mockFunction = mock(UnivariateRealFunction.class);
    double min = 0.0;
    double max = 1.0;
    double initial = 0.5;

    BisectionSolver solver = spy(new BisectionSolver());
    doThrow(new FunctionEvaluationException(0.5))
        .when(solver).solve(mockFunction, min, max);

    // Act & Assert
    assertThrows(FunctionEvaluationException.class, () -> {
        solver.solve(mockFunction, min, max, initial);
    }, "FunctionEvaluationException should propagate upward from the delegated solve method.");
}

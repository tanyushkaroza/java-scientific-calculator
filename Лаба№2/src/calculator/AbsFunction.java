package calculator;

public class AbsFunction extends MathFunction {
	@Override
    public double evaluate(String arguments, ExpressionEvaluator evaluator) {
        double[] args = parseArguments(arguments, evaluator);
        if (args.length != 1) throw new IllegalArgumentException("abs требует 1 аргумент");
        return Math.abs(args[0]);
    }
}

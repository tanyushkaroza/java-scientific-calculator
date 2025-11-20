package calculator;

public class TanFunction extends MathFunction {
	@Override
    public double evaluate(String arguments, ExpressionEvaluator evaluator) {
        double[] args = parseArguments(arguments, evaluator);
        if (args.length != 1) throw new IllegalArgumentException("tan требует 1 аргумент");
        return Math.tan(args[0]);
    }
}


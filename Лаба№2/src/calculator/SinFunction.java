package calculator;

public class SinFunction extends MathFunction {
	@Override
    public double evaluate(String arguments, ExpressionEvaluator evaluator) {
        double[] args = parseArguments(arguments, evaluator);
        if (args.length != 1) throw new IllegalArgumentException("sin требует 1 аргумент");
        return Math.sin(args[0]);
    }
}

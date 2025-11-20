package calculator;

public class CosFunction extends MathFunction {
	@Override
    public double evaluate(String arguments, ExpressionEvaluator evaluator) {
        double[] args = parseArguments(arguments, evaluator);
        if (args.length != 1) throw new IllegalArgumentException("cos требует 1 аргумент");
        return Math.cos(args[0]);
    }
}

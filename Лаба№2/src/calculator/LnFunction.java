package calculator;

public class LnFunction extends MathFunction {
	@Override
    public double evaluate(String arguments, ExpressionEvaluator evaluator) {
        double[] args = parseArguments(arguments, evaluator);
        if (args.length != 1) throw new IllegalArgumentException("ln требует 1 аргумент");
        if (args[0] <= 0) throw new IllegalArgumentException("ln определен только для положительных чисел");
        return Math.log(args[0]);
    }
}

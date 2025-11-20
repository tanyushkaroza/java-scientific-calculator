package calculator;

public class SqrtFunction extends MathFunction {
	@Override
    public double evaluate(String arguments, ExpressionEvaluator evaluator) {
        double[] args = parseArguments(arguments, evaluator);
        if (args.length != 1) throw new IllegalArgumentException("sqrt требует 1 аргумент");
        if (args[0] < 0) throw new IllegalArgumentException("sqrt определен только для неотрицательных чисел");
        return Math.sqrt(args[0]);
    }
}

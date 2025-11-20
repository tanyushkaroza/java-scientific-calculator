package calculator;

public class LogFunction extends MathFunction {
	@Override
    public double evaluate(String arguments, ExpressionEvaluator evaluator) {
        double[] args = parseArguments(arguments, evaluator);
        if (args.length != 1) throw new IllegalArgumentException("log требует 1 аргумент");
        if (args[0] <= 0) throw new IllegalArgumentException("log определен только для положительных чисел");
        return Math.log(args[0]);
    }
}

package calculator;

public class PowFunction extends MathFunction {
	@Override
    public double evaluate(String arguments, ExpressionEvaluator evaluator) {
        double[] args = parseArguments(arguments, evaluator);
        if (args.length != 2) throw new IllegalArgumentException("pow требует 2 аргумента: основание и степень");
        return Math.pow(args[0], args[1]);
    }
}


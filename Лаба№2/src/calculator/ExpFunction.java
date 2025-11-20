package calculator;

public class ExpFunction extends MathFunction {
	@Override
    public double evaluate(String arguments, ExpressionEvaluator evaluator) {
        double[] args = parseArguments(arguments, evaluator);
        if (args.length != 1) throw new IllegalArgumentException("exp требует 1 аргумент");
        return Math.exp(args[0]);
    }
}


package calculator;

public class CtgFunction extends MathFunction {
	@Override
    public double evaluate(String arguments, ExpressionEvaluator evaluator) {
        double[] args = parseArguments(arguments, evaluator);
        if (args.length != 1) throw new IllegalArgumentException("ctg требует 1 аргумент");
        double tanValue = Math.tan(args[0]);
        if (Math.abs(tanValue) < 1e-10) throw new IllegalArgumentException("ctg не определен для данного угла");
        return 1.0 / tanValue;
    }
}

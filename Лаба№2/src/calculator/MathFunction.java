package calculator;

public abstract class MathFunction {
public abstract double evaluate(String arguments, ExpressionEvaluator evaluator);
    
    protected double[] parseArguments(String arguments, ExpressionEvaluator evaluator) {
        String[] argStrings = arguments.split(",");
        double[] args = new double[argStrings.length];
        for (int i = 0; i < argStrings.length; i++) {
            args[i] = evaluator.evaluate(argStrings[i].trim());
        }
        return args;
    }
}

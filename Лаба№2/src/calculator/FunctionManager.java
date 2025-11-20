package calculator;
import java.util.HashMap;
import java.util.Map;

public class FunctionManager {
    private Map<String, MathFunction> functions;
    
    public FunctionManager() {
        functions = new HashMap<>();
        initializeFunctions();
    }
    
    private void initializeFunctions() {
        functions.put("sin", new SinFunction());
        functions.put("cos", new CosFunction());
        functions.put("tan", new TanFunction());
        functions.put("tg", new TanFunction());
        functions.put("ctg", new CtgFunction());
        functions.put("log", new LogFunction());
        functions.put("ln", new LnFunction());
        functions.put("pow", new PowFunction());
        functions.put("sqrt", new SqrtFunction());
        functions.put("exp", new ExpFunction());
        functions.put("abs", new AbsFunction());
    }
    
    public boolean hasFunction(String name) {
        return functions.containsKey(name.toLowerCase());
    }
    
    public double evaluateFunction(String name, String arguments, ExpressionEvaluator evaluator) {
        MathFunction function = functions.get(name.toLowerCase());
        if (function == null) {
            throw new IllegalArgumentException("Неизвестная функция: " + name);
        }
        return function.evaluate(arguments, evaluator);
    }
}
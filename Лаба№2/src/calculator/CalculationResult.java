package calculator;
import java.util.Set;

public class CalculationResult {
	private String expression;
    private double result;
    private Set<String> used_variables;
    
    public CalculationResult(String expression, double result, Set<String> usedVariables) {
        this.expression = expression;
        this.result = result;
        this.used_variables = usedVariables;
    }
    
    public void display() {
        System.out.println("Выражение: " + expression);
        if (!used_variables.isEmpty()) {
            System.out.println("Использованные переменные: " + used_variables);
        }
        System.out.printf("Результат: %.6f\n", result);
    }
    
    public String getExpression() { 
    	return expression; 
    }
    
    public double getResult() { 
    	return result; 
    }
    
    public Set<String> getUsedVariables() { 
    	return used_variables; 
    }

}

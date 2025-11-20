package calculator;
import java.util.*;
import java.util.regex.*;

public class ExpressionEvaluator {
    private ValueManager valueManager;
    private FunctionManager functionManager;
    private CheckExpression checkExpression;
    
    public ExpressionEvaluator(ValueManager valueManager) {
        this.valueManager = valueManager;
        this.functionManager = new FunctionManager();
        this.checkExpression = new CheckExpression();
    }
    
    public double evaluate(String expression) {
        String normalizedExpression = normalizeBrackets(expression);
        checkExpression.validate(normalizedExpression);
        String processedExpression = processFunctions(normalizedExpression);
        return evaluateWithStack(processedExpression);
    }
    
    private String normalizeBrackets(String expression) {
        return expression.replace('[', '(').replace(']', ')')
                        .replace('{', '(').replace('}', ')');
    }
    
    private String processFunctions(String expression) {
        Pattern pattern = Pattern.compile("(\\w+)\\(([^()]+)\\)");
        Matcher matcher = pattern.matcher(expression);
        StringBuffer result = new StringBuffer();
        
        while (matcher.find()) {
            String functionName = matcher.group(1);
            String arguments = matcher.group(2);
            
            if (functionManager.hasFunction(functionName)) {
                double resultValue = functionManager.evaluateFunction(functionName, arguments, this);
                String replacement = String.valueOf(resultValue);
                if (resultValue < 0) {
                    replacement = "(" + replacement + ")";
                }
                matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
            } else {
                matcher.appendReplacement(result, Matcher.quoteReplacement(matcher.group(0)));
            }
        }
        matcher.appendTail(result);
        return result.toString();
    }
    
    private String replaceVariables(String expression) {
        String result = expression;
        List<String> varNames = new ArrayList<>(valueManager.getNames());
        varNames.sort((a, b) -> Integer.compare(b.length(), a.length()));
        
        for (String varName : varNames) {
            Double value = valueManager.getValue(varName);
            if (value != null) {
                String replacement = value.toString();
                if (value < 0) {
                    replacement = "(" + replacement + ")";
                }
                result = result.replaceAll("\\b" + Pattern.quote(varName) + "\\b", Matcher.quoteReplacement(replacement));
            }
        }
        return result;
    }
    
    public Set<String> extractVariables(String expression) {
        Set<String> variables = new HashSet<>();
        String expressionWithoutFunctions = removeFunctions(expression);
        String[] tokens = expressionWithoutFunctions.split("[+\\-*/()\\s]+");
        
        for (String token : tokens) {
            if (token.isEmpty()) continue;
            
            if (isValidVariable(token) && !token.equals("pi") && !token.equals("e")) {
                if (!functionManager.hasFunction(token)) {
                    variables.add(token);
                }
            }
        }
        return variables;
    }
    
    private boolean isValidVariable(String token) {
        if (token.isEmpty() || (!Character.isLetter(token.charAt(0)) && token.charAt(0) != '_')) {
            return false;
        }
        for (int i = 1; i < token.length(); i++) {
            char c = token.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '_') {
                return false;
            }
        }
        return true;
    }
    
    private String removeFunctions(String expression) {
        Pattern pattern = Pattern.compile("(\\w+)\\(([^()]+|.*?)\\)");
        Matcher matcher = pattern.matcher(expression);
        StringBuffer result = new StringBuffer();
        
        while (matcher.find()) {
            String functionName = matcher.group(1);
            String arguments = matcher.group(2);
            if (functionManager.hasFunction(functionName)) {
                matcher.appendReplacement(result, Matcher.quoteReplacement(arguments));
            } else {
                matcher.appendReplacement(result, Matcher.quoteReplacement(matcher.group(0)));
            }
        }
        matcher.appendTail(result);
        return result.toString();
    }
    
    private double evaluateWithStack(String expression) {
        expression = replaceVariables(expression);
        expression = expression.replaceAll("\\s+", "");
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            
            if (Character.isDigit(c) || c == '.' || (c == '-' && (i == 0 || expression.charAt(i - 1) == '(' || 
                isOperator(expression.charAt(i - 1))))) {
                StringBuilder sb = new StringBuilder();
                if (c == '-') {
                    sb.append('-');
                    i++;
                    if (i >= expression.length()) {
                        throw new IllegalArgumentException("Незавершенное отрицательное число");
                    }
                }
              
                while (i < expression.length() && 
                       (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i));
                    i++;
                }
                i--;
                
                try {
                    numbers.push(Double.parseDouble(sb.toString()));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Некорректное число: " + sb.toString());
                }
            }
            else if (c == '(') {
                operators.push(c);
            }
            else if (c == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    if (numbers.size() < 2) {
                        throw new IllegalArgumentException("Недостаточно операндов для операции");
                    }
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                }
                if (operators.isEmpty()) {
                    throw new IllegalArgumentException("Несбалансированные скобки");
                }
                operators.pop();
            }
            else if (isOperator(c)) {
                while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
                    if (numbers.size() < 2) {
                        throw new IllegalArgumentException("Недостаточно операндов для операции");
                    }
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.push(c);
            } else {
                throw new IllegalArgumentException("Недопустимый символ: " + c);
            }
        }
        
        while (!operators.isEmpty()) {
            if (operators.peek() == '(') {
                throw new IllegalArgumentException("Несбалансированные скобки");
            }
            if (numbers.size() < 2) {
                throw new IllegalArgumentException("Недостаточно операндов для операции");
            }
            numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
        }
        
        if (numbers.size() != 1) {
            throw new IllegalArgumentException("Некорректное выражение");
        }
        
        return numbers.pop();
    }
    
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
    
    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(') return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) return false;
        return true;
    }
    
    private double applyOperation(char op, double b, double a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': 
                if (b == 0) throw new IllegalArgumentException("Деление на ноль");
                return a / b;
            default: throw new IllegalArgumentException("Неизвестная операция: " + op);
        }
    }
    
    public CalculationResult evaluateWithResult(String expression) {
        Set<String> usedVariables = extractVariables(expression);
        double result = evaluate(expression);
        
        CalculationResult calcResult = new CalculationResult(expression, result, usedVariables);
        
        return calcResult;
    }
}

package calculator;
import java.util.Stack;

public class CheckExpression {
	
    public void validate(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Ошибка! Выражение не может быть пустым");
        }
        checkBrackets(expression);
        checkSyntax(expression);
    }
    
	private void checkBrackets(String expression) {
        Stack<Character> bracketStack = new Stack<>();
        
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            
            if (c == '(' || c == '[' || c == '{') {
                bracketStack.push(c);
            }
            else if (c == ')' || c == ']' || c == '}') {
                if (bracketStack.isEmpty()) {
                    throw new IllegalArgumentException("Ошибка! Непарные скобки: закрывающая скобка '" + c + "' без соответствующей открывающей");
                }
                
                char lastOpenBracket = bracketStack.pop();
                
                if (!isMatchingBrackets(lastOpenBracket, c)) {
                    throw new IllegalArgumentException("Ошибка! Несоответствие типов скобок: '" + lastOpenBracket + "' и '" + c + "'");
                }
            }
        }
        
        if (!bracketStack.isEmpty()) {
            throw new IllegalArgumentException("Ошибка! Непарные скобки: отсутствуют закрывающие скобки для '" + bracketStack.peek() + "'");
        }
    }
    
    private boolean isMatchingBrackets(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '[' && close == ']') ||
               (open == '{' && close == '}');
    }
    
    private void checkSyntax(String expression) {
        if (expression.matches(".*[+\\-*/]{2,}.*")) {
            throw new IllegalArgumentException("Ошибка! Некорректная последовательность операторов");
        }
    }

}
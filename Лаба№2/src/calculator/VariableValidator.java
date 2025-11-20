package calculator;

public class VariableValidator {
	public void validateName(String name) {
        if (!name.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
            throw new IllegalArgumentException("Некорректное имя переменной: " + name);
        }
        if (name.equals("pi") || name.equals("e")) {
            throw new IllegalArgumentException("Имя '" + name + "' зарезервировано для констант");
        }
    }
}

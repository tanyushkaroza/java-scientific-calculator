package calculator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ValueManager {
    private Map<String, Double> values;
    private VariableValidator validator;
    
    public ValueManager() {
        values = new HashMap<>();
        this.validator = new VariableValidator();
        initializeConstants();
    }
    
    private void initializeConstants() {
        values.put("pi", Math.PI);
        values.put("e", Math.E);
    }
    
    private boolean isReservedConstant(String name) {
        return name.equals("pi") || name.equals("e");
    }
    
    public void setValue(String name, double value) {
        validator.validateName(name);
        values.put(name, value);
    }
    
    public Double getValue(String name) {
        return values.get(name);
    }
    
    public boolean findVariable(String name) { 
        return values.containsKey(name);
    }
    
    public void removeVariable(String name) {
        if (isReservedConstant(name)) {
            throw new IllegalArgumentException("Нельзя удалить константу: " + name);
        }
        values.remove(name);
    }
    
    public void updateVariableValue(String variableName, double newValue) {
        if (!values.containsKey(variableName)) {
            throw new IllegalArgumentException("Ошибка! Переменная '" + variableName + "' не найдена");
        } else if (isReservedConstant(variableName)) {
            throw new IllegalArgumentException("Ошибка! Нельзя изменить значение константы: " + variableName);
        } else {
            values.put(variableName, newValue);
        }
    }
    
    public Set<String> getNames() {  
        return new HashSet<>(values.keySet());
    }
    
    public Set<String> getUserVariables() {
        Set<String> userVars = new HashSet<>();
        for (String name : values.keySet()) {
            if (!isReservedConstant(name)) {
                userVars.add(name);
            }
        }
        return userVars;
    }
    
    public Set<String> getConstants() {
        Set<String> constants = new HashSet<>();
        for (String name : values.keySet()) {
            if (isReservedConstant(name)) {
                constants.add(name);
            }
        }
        return constants;
    }
    
    public int getUserVariablesCount() {
        return getUserVariables().size();
    }
    
    public int getConstantsCount() {
        return getConstants().size();
    }
    
    public void inputValues(Scanner scanner, Set<String> requiredVariables) {
        if (requiredVariables == null || requiredVariables.isEmpty()) {
            return;
        }
        
        Set<String> missingVars = new HashSet<>(requiredVariables);
        missingVars.removeAll(values.keySet());
        
        for (String varName : missingVars) {
            boolean valueAssigned = false;
            do {
                System.out.print("Введите значение переменной " + varName + " = ");
                try {
                    double value = scanner.nextDouble();
                    setValue(varName, value);
                    valueAssigned = true;
                } catch (InputMismatchException e) {
                    System.out.println("Ошибка! Введите числовое значение");
                    scanner.nextLine();
                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                    scanner.nextLine();
                }
            } while (!valueAssigned);
        }
    }
    
    public void displayAllValues() {
        if (values.isEmpty()) {
            System.out.println("Переменные не определены");
            return;
        }
        
        System.out.println("Текущие значения:");
        System.out.println("Константы");
        for (String constant : getConstants()) {
            System.out.printf("%s = %.6f\n", constant, values.get(constant));
        }
        
        Set<String> userVars = getUserVariables();
        if (!userVars.isEmpty()) {
            System.out.println("Пользовательские переменные");
            for (String var : userVars) {
                System.out.printf("%s = %.6f\n", var, values.get(var));
            }
        }
    }
    
    public void displayUserVariables() {
        Set<String> userVars = getUserVariables();
        if (userVars.isEmpty()) {
            System.out.println("Пользовательские переменные не определены");
            return;
        }
        
        System.out.println("Пользовательские переменные:");
        for (String var : userVars) {
            System.out.printf("%s = %.6f\n", var, values.get(var));
        }
    }
    
    public void displayConstants() {
        Set<String> constants = getConstants();
        System.out.println("Системные константы:");
        for (String constant : constants) {
            System.out.printf("%s = %.6f\n", constant, values.get(constant));
        }
    }
    
    public void clearUserVariables() {
        values.entrySet().removeIf(entry -> 
            !entry.getKey().equals("pi") && !entry.getKey().equals("e"));
    }
    
    public void clear() {  
        values.clear();
        initializeConstants();
    }
    
    public int size() {
        return values.size();
    }
    
    public boolean isEmpty() {
        return getUserVariables().isEmpty();
    }
    
    public boolean hasUserVariables() {
        return !getUserVariables().isEmpty();
    }
}
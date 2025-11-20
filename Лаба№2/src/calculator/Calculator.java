package calculator;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class Calculator {
    private ValueManager valueManager;
    private ExpressionEvaluator evaluator;
    private Scanner scanner;
    
    public Calculator() {
        this.valueManager = new ValueManager();
        this.evaluator = new ExpressionEvaluator(valueManager);
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        while (true) {
            String menu = """
                Выберите действие:
                1. Вычислить выражение
                2. Управление переменными
                3. Показать все значения
                4. Выход
                Ваш выбор:\s""";
            System.out.print(menu);
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        calculateExpression();
                        break;
                    case 2:
                        manageVariables();
                        break;
                    case 3:
                        valueManager.displayAllValues();
                        break;
                    case 4:
                        System.out.println("Выход...");
                        return;
                    default:
                        System.out.println("Неверный выбор!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка! Введите число от 1 до 4");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
    
    private void calculateExpression() {
        System.out.print("Введите выражение: ");
        String expression = scanner.nextLine().trim();
        
        try {
            Set<String> variables = evaluator.extractVariables(expression);
            valueManager.inputValues(scanner, variables);
            CalculationResult result = evaluator.evaluateWithResult(expression);
            result.display();
            
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    
    private void manageVariables() {
        String variablesMenu = """
            Управление переменными:
            1. Добавить переменную
            2. Изменить переменную
            3. Удалить переменную
            4. Показать переменные
            Ваш выбор:\s""";
        System.out.print(variablesMenu);
        
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    addVariable();
                    break;
                case 2:
                    updateVariable();
                    break;
                case 3:
                    removeVariable();
                    break;
                case 4:
                    valueManager.displayUserVariables();
                    break;
                default:
                    System.out.println("Неверный выбор!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Ошибка! Введите число");
            scanner.nextLine();
        }
    }
    
    private void addVariable() {
        System.out.print("Введите имя переменной: ");
        String name = scanner.nextLine().trim();
        System.out.print("Введите значение: ");
        
        try {
            double value = scanner.nextDouble();
            valueManager.setValue(name, value);
            System.out.println("Переменная " + name + " = " + value + " добавлена");
        } catch (InputMismatchException e) {
            System.out.println("Ошибка! Введите числовое значение");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    
    private void updateVariable() {
        System.out.print("Введите имя переменной для изменения: ");
        String name = scanner.nextLine().trim();
        
        if (!valueManager.findVariable(name)) {
            System.out.println("Переменная не найдена");
            return;
        }
        
        System.out.print("Введите новое значение: ");
        try {
            double value = scanner.nextDouble();
            valueManager.updateVariableValue(name, value);
            System.out.println("Переменная " + name + " обновлена");
        } catch (InputMismatchException e) {
            System.out.println("Ошибка! Введите числовое значение");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    
    private void removeVariable() {
        System.out.print("Введите имя переменной для удаления: ");
        String name = scanner.nextLine().trim();
        
        try {
            valueManager.removeVariable(name);
            System.out.println("Переменная " + name + " удалена");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.start();
    }
}

package Calc;

/**
 * Calculator - Singleton that handles all calculation logic
 * Separate from UI concerns
 */
public class Calculator {
    // Singleton instance
    private static volatile Calculator instance;
    
    // Calculation state
    private String currentOperand;
    private String previousOperand;
    private String operation;
    
    // Observer for UI updates
    private CalculatorUI ui;
    
    // Private constructor for Singleton
    private Calculator() {
        clear();
    }
    
    // Singleton getInstance with double-checked locking
    public static Calculator getInstance() {
        if (instance == null) {
            synchronized (Calculator.class) {
                if (instance == null) {
                    instance = new Calculator();
                }
            }
        }
        return instance;
    }
    
    // Register UI for updates (Observer pattern)
    public void setUI(CalculatorUI ui) {
        this.ui = ui;
        notifyUI();
    }
    
    // Core calculation methods
    public void clear() {
        currentOperand = "";
        previousOperand = "";
        operation = "";
        notifyUI();
    }
    
    public void appendNumber(String number) {
        // Prevent multiple zeros
        if (currentOperand.equals("0") && number.equals("0")) {
            return;
        }
        
        // Prevent multiple decimal points
        if (number.equals(".") && currentOperand.contains(".")) {
            return;
        }
        
        // Replace initial zero with new number
        if (currentOperand.equals("0") && !number.equals(".")) {
            currentOperand = number;
        } else {
            currentOperand += number;
        }
        
        notifyUI();
    }
    
    public void deleteLastDigit() {
        if (!currentOperand.isEmpty()) {
            currentOperand = currentOperand.substring(0, currentOperand.length() - 1);
            notifyUI();
        }
    }
    
    public void setOperation(String op) {
        if (currentOperand.isEmpty() && !previousOperand.isEmpty()) {
            operation = op;
            notifyUI();
            return;
        }
        
        if (currentOperand.isEmpty()) {
            return;
        }
        
        // Chain operations
        if (!previousOperand.isEmpty()) {
            compute();
        }
        
        operation = op;
        previousOperand = currentOperand;
        currentOperand = "";
        notifyUI();
    }
    
    public void compute() {
        if (currentOperand.isEmpty() || previousOperand.isEmpty() || operation.isEmpty()) {
            return;
        }
        
        try {
            float curr = Float.parseFloat(currentOperand);
            float prev = Float.parseFloat(previousOperand);
            float result = 0;
            
            switch (operation) {
                case "+" -> result = prev + curr;
                case "-" -> result = prev - curr;
                case "×" -> result = prev * curr;
                case "÷" -> {
                    if (curr == 0) {
                        handleError("Cannot divide by zero");
                        return;
                    }
                    result = prev / curr;
                }
                default -> {
                    return;
                }
            }
            
            currentOperand = formatResult(result);
            previousOperand = "";
            operation = "";
            notifyUI();
            
        } catch (NumberFormatException e) {
            handleError("Invalid input");
        }
    }
    
    public void toggleSign() {
        if (!currentOperand.isEmpty() && !currentOperand.equals("0")) {
            try {
                float value = -Float.parseFloat(currentOperand);
                currentOperand = formatResult(value);
                notifyUI();
            } catch (NumberFormatException e) {
                // Ignore invalid input
            }
        }
    }
    
    public void addDecimalPoint() {
        if (currentOperand.isEmpty()) {
            currentOperand = "0.";
        } else if (!currentOperand.contains(".")) {
            currentOperand += ".";
        }
        notifyUI();
    }
    
    // Helper methods
    private String formatResult(float value) {
        if (value == (int) value) {
            return Integer.toString((int) value);
        } else {
            return Float.toString(value);
        }
    }
    
    private void handleError(String message) {
        clear();
        currentOperand = "Error";
        notifyUI();
        // Reset after showing error
        javax.swing.Timer timer = new javax.swing.Timer(1500, e -> clear());
        timer.setRepeats(false);
        timer.start();
    }
    
    private void notifyUI() {
        if (ui != null) {
            ui.updateDisplay(currentOperand, 
                           previousOperand + (operation.isEmpty() ? "" : " " + operation));
        }
    }
    
    // Getters for display (if UI needs direct access)
    public String getCurrentDisplay() {
        return currentOperand;
    }
    
    public String getPreviousDisplay() {
        return previousOperand + (operation.isEmpty() ? "" : " " + operation);
    }
}
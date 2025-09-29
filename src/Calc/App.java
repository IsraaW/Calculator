package Calc;

import javax.swing.SwingUtilities;

/**
 *
 * @author youcefhmd
 */

public class App {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Get the singleton Calculator instance
            Calculator calculator = Calculator.getInstance();
            
            // Create UI and connect it to the calculator
            CalculatorUI ui = new CalculatorUI(calculator);
            
            // Show the calculator window
            ui.showCalculator();
        });
    }
}
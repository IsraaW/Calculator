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
            
            CalculatorUI ui = CalculatorUI.getInstance(calculator);
            
            // Show the calculator window
            ui.showCalculator();
        });
    }
}
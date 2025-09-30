package Calc;

import javax.swing.SwingUtilities;
<<<<<<< HEAD

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorBuilder builder = new StandardCalculatorBuilder();
            CalculatorEngineer engineer = new CalculatorEngineer(builder);
            engineer.makeCalculator(); 
            Calculator calc = engineer.getCalculator();
            calc.setVisible(true);
        });
    }
}
=======

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
>>>>>>> 88c97ac160273971bf3cc07d8274c910363861cf

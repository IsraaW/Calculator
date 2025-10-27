package Calc;

import javax.swing.SwingUtilities;


public class CalculatorFacade {
    
    
    public static void createAndShowCalculator() {
        SwingUtilities.invokeLater(() -> {
            // Step 1: Create the builder
            CalculatorBuilder builder = new StandardCalculatorBuilder();
            
            // Step 2: Create the engineer (director) with the builder
            CalculatorEngineer engineer = new CalculatorEngineer(builder);
            
            // Step 3: Build the calculator in the correct sequence
            engineer.makeCalculator();
            
            // Step 4: Get the constructed calculator
            Calculator calculator = engineer.getCalculator();
            
            // Step 5: Display the calculator
            calculator.setVisible(true);
        });
    }
    
    /**
     * Creates and displays a calculator with custom builder.
     * This overloaded method provides flexibility while maintaining simplicity.
     * 
     * @param builder Custom calculator builder
     */
    public static void createAndShowCalculator(CalculatorBuilder builder) {
        SwingUtilities.invokeLater(() -> {
            CalculatorEngineer engineer = new CalculatorEngineer(builder);
            engineer.makeCalculator();
            Calculator calculator = engineer.getCalculator();
            calculator.setVisible(true);
        });
    }
    
    /**
     * Creates a calculator without displaying it.
     * Useful for testing or when you need the calculator instance
     * before showing it.
     * 
     * @return The constructed Calculator instance
     */
    public static Calculator createCalculator() {
        CalculatorBuilder builder = new StandardCalculatorBuilder();
        CalculatorEngineer engineer = new CalculatorEngineer(builder);
        engineer.makeCalculator();
        return engineer.getCalculator();
    }
    
    /**
     * Creates a calculator with custom builder without displaying it.
     * 
     * @param builder Custom calculator builder
     * @return The constructed Calculator instance
     */
    public static Calculator createCalculator(CalculatorBuilder builder) {
        CalculatorEngineer engineer = new CalculatorEngineer(builder);
        engineer.makeCalculator();
        return engineer.getCalculator();
    }
}

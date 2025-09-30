package Calc;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorBuilder builder = new StandardCalculatorBuilder();
            CalculatorEngineer engineer = new CalculatorEngineer(builder);
            engineer.makeCalculator(); // build in the right order
            Calculator calc = engineer.getCalculator();
            calc.setVisible(true);
        });
    }
}

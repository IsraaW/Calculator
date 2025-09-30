package Calc;

public class CalculatorEngineer {

    private final CalculatorBuilder builder;

    public CalculatorEngineer(CalculatorBuilder builder) {
        this.builder = builder;
    }

    public void makeCalculator() {
        builder.buildComponents(); // 1) create components
        builder.buildContentSize(); // 2) size/window
        builder.buildDisplay(); // 3) clear AFTER components exist
        builder.buildEventHandlers(); // 4) wire events
    }

    public Calculator getCalculator() {
        return builder.getCalculator();
    }
}

package Calc;

public class StandardCalculatorBuilder implements CalculatorBuilder {

    private Calculator calculator;

    public StandardCalculatorBuilder() {
        this.calculator = new Calculator();
    }

    @Override
    public void buildComponents() {
        // Components already built in constructor
        // This step ensures they exist
    }

    @Override
    public void buildContentSize() {
        calculator.setContentSize(400, 700);
    }

    @Override
    public void buildDisplay() {
        calculator.initializeDisplay();
    }

    @Override
    public void buildEventHandlers() {
        calculator.setupEventHandlers();
    }

    @Override
    public Calculator getCalculator() {
        return this.calculator;
    }
}
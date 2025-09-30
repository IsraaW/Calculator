package Calc;

public interface CalculatorBuilder {
    void buildComponents();

    void buildContentSize();

    void buildDisplay();

    void buildEventHandlers();

    Calculator getCalculator();
}

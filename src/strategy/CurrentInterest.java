package strategy;

public class CurrentInterest implements InterestStrategy {
    @Override
    public double calculateInterest(double balance) { return balance * 0.01; }
}

package strategy;

public class LoanInterest implements InterestStrategy {
    @Override
    public double calculateInterest(double balance) { return balance * 0.05; }
}

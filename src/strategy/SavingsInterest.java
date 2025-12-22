package strategy;

public class SavingsInterest implements InterestStrategy {
    @Override
    public double calculateInterest(double balance) {
        return balance * 0.03; // فائدة 3% على التوفير
    }
}

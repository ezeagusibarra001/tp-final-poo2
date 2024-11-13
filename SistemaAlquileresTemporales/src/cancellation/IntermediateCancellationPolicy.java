package cancellation;

public class IntermediateCancellationPolicy extends CancellationPolicy {

	@Override
	public double calculateRefund(double totalAmount, int daysBeforeStart, double dailyPrice) {
		if (daysBeforeStart >= 20) {
            return totalAmount;
        } else if (daysBeforeStart >= 10) {
            return totalAmount * 0.5; 
        } else {
            return 0; 
        }
	}

}

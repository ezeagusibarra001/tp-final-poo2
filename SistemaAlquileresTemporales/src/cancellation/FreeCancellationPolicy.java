package cancellation;

public class FreeCancellationPolicy extends CancellationPolicy {

	@Override
	public double calculateRefund(double totalAmount, int daysBeforeStart, double dailyPrice) {
		if (daysBeforeStart >= 10) {
            return totalAmount;
        } else {
            return totalAmount - 2 * dailyPrice; 
        }
	}

}


package cancellation;

public class noCancellationPolicy extends CancellationPolicy {

	@Override
	public double calculateRefund(double totalAmount, int daysBeforeStart, double dailyPrice) {
	
		return 0;
	}

}

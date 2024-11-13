package cancellation;

public abstract class CancellationPolicy {
	
	public abstract double calculateRefund(double totalAmount, int daysBeforeStart, double dailyPrice);
	
}

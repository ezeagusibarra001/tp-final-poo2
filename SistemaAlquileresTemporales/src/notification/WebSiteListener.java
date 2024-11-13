package notification;

public class WebSiteListener implements EventListener {

	HomePagePublisher publisher;

	public WebSiteListener(HomePagePublisher publisher) {
		this.setPublisher(publisher);
	}

	private void setPublisher(HomePagePublisher publisher) {
		this.publisher = publisher;
		
	}

	@Override
	public void update(PropertyEvent event) {
		String propertyType = event.getPropertyType(); 
		double price = event.getPropertyPrice();
		
		publisher.publish(createMessage(propertyType, price));
		
	}

	private String createMessage(String propertyType, double price) {
		return "No te pierdas esta oferta: Un inmueble " + propertyType + " a tan sólo " + price + " pesos";
	}
}

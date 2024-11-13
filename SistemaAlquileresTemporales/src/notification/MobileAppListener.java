package notification;

public class MobileAppListener implements EventListener{

	PopUpWindow window;
	
	public MobileAppListener(PopUpWindow window) {
		this.setWindow(window);
	}
	
	private void setWindow(PopUpWindow window) {
		this.window = window;		
	}
		

	@Override
	public void update(PropertyEvent event) {
		String propertyType = event.getPropertyType(); 
		
		window.popUp(createMessage(propertyType), "BLACK", 12);
	}

	private String createMessage(String propertyType) {
		
		return "El/la " + propertyType + " que te interesa se ha liberado! Corre a reservarlo!";
	}

}

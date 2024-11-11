package property;

public class Photo {
	private String url;

	public Photo(String url) {
		this.setUrl(url);
	}

	private String getUrl() {
		return url;
	}
	
	private void setUrl(String url) {
		this.url = url;
	}
}

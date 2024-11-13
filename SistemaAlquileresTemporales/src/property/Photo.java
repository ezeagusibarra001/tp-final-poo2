package property;

public class Photo {
	private String url;

	public Photo(String url) {
		this.setUrl(url);
	}

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}

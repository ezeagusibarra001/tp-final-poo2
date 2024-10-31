package property;

public class Photo {
	private String url;

	public Photo(String url) {
		super();
		this.setUrl(url);
	}

	public String getUrl() {
		return url;
	}

	private void setUrl(String url) {
		this.url = url;
	}

}

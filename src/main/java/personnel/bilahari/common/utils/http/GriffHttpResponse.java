package com.onmobile.apps.griff.utils;

/**
 * @author pawan.kumar
 * @created Sep 10, 2013
 */
public class GriffHttpResponse {
	
	private int statusCode;
	private String responseStr;
	private long timeTaken;
	
	private String url;
	
	/**
	 * @param statusCode
	 * @param responseStr
	 * @param timeTaken
	 */
	GriffHttpResponse(int statusCode, String responseStr, long timeTaken) {
		super();
		this.statusCode = statusCode;
		this.responseStr = responseStr;
		this.timeTaken = timeTaken;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @return the responseStr
	 */
	public String getResponseStr() {
		return responseStr;
	}
	/**
	 * @param responseStr the responseStr to set
	 */
	public void setResponseStr(String responseStr) {
		this.responseStr = responseStr;
	}

	/**
	 * @return the timeTaken
	 */
	public long getTimeTaken() {
		return timeTaken;
	}

	/**
	 * @param timeTaken the timeTaken to set
	 */
	public void setTimeTaken(long timeTaken) {
		this.timeTaken = timeTaken;
	}

	@Override
	public String toString() {
		return "GriffHttpResponse [statusCode=" + statusCode + ", responseStr="
				+ responseStr + ", timeTaken=" + timeTaken + ", url=" + url
				+ "]";
	}
	
	
	
}

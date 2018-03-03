package org.lolobored.http;

/**
 * Specific Exception for any HTTP calls
 */
public class HttpException extends Exception {

	private String request;
	private String response;
	private String url;
	private String operation;
	private Integer httpStatusCode;

	/**
	 * Low level HTTP call error
	 * @param message the error message
	 * @param th the encountered exception
	 */
	public HttpException(String message, Throwable th) {
		this(message, null, null, null, null, null, th);
	}

	/**
	 * The error is supposed to be thrown when encountering an HTTP status code 2xx
	 * @param message the error message
	 * @param request the initial request (when available)
	 * @param response the response from the server (when available)
	 * @param url the URL which was called
	 * @param statusCode the resulting status code
	 * @param operation the operation (GET/POST...)
	 */
	public HttpException(String message, String request, String response, String url, Integer statusCode, String operation) {
		this(message, request, response, url, statusCode, operation, null);
	}

	/**
	 * The error is supposed to be thrown when encountering an HTTP error while calling the service
	 * @param message the error message
	 * @param request the initial request (when available)
	 * @param response the response from the server (when available)
	 * @param url the URL which was called
	 * @param statusCode the resulting status code
	 * @param operation the operation (GET/POST...)
	 * @param th the exception encountered
	 */
	public HttpException(String message, String request, String response, String url, Integer statusCode, String operation, Throwable th) {
		super(message, th);
		this.setRequest(request);
		this.setResponse(response);
		this.setUrl(url);
		this.setOperation(operation);
		this.setHttpStatusCode(statusCode);
	}

	/**
	 * Returns the request used in the HTTP call
	 * @return
	 */
	public String getRequest() {
		return request;
	}

	/**
	 * Sets the request for [the HTTP call
	 * @param request
	 */
	public void setRequest(String request) {
		this.request = request;
	}

	/**
	 * Gets the response by the HTTP server
	 * @return
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * Sets the response returned by the HTTP server
	 * @param response
	 */
	public void setResponse(String response) {
		this.response = response;
	}

	/**
	 * Gets the URL called by the HTTP Utility
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the URL called by the HTTP Utility
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the operation (GET/POST...)
	 * @return
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * Sets the operation (GET/POST...)
	 * @param operation
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * Retrieve the HTTP status code returned by the call
	 * @return
	 */
	public Integer getHttpStatusCode() {
		return httpStatusCode;
	}

	/**
	 * Sets the HTTP code returned by the call
	 * @param httpStatusCode
	 */
	public void setHttpStatusCode(Integer httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
}

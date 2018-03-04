package org.lolobored.http;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Specific Exception for any HTTP calls
 */
@Data
@EqualsAndHashCode(callSuper=false)
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
		this.request= request;
		this.response= response;
		this.url= url;
		this.operation= operation;
		this.httpStatusCode= statusCode;
	}


}

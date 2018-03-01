package org.lolobored.http;

/**
 * @author llaborde
 */
public class HttpException extends Exception {

    private String request;
    private String response;
    private String url;
    private String operation;
    private Integer httpStatusCode;

    public HttpException(String message, Throwable th) {
        this(message, null, null, null, null, null, th);
    }

    public HttpException(String message, String request, String response, String url, Integer statusCode, String operation) {
        this(message, request, response, url, statusCode, operation, null);
    }

    public HttpException(String message, String request, String response, String url, Integer statusCode, String operation, Throwable th) {
        super(message, th);
        this.setRequest(request);
        this.setResponse(response);
        this.setUrl(url);
        this.setOperation(operation);
        this.setHttpStatusCode(statusCode);
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }
}

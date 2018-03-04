package org.lolobored.http;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.SingleClientConnManager;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HttpUtil is a utility class to interact with any HTTP server
 */
public class HttpUtil {

	public static final String HTTP_GET = "GET";
	public static final String HTTP_PUT = "PUT";
	public static final String HTTP_POST = "POST";
	public static final String HTTP_DELETE = "DELETE";

	public static final String FORM_URLENCODED = "application/x-www-form-urlencoded";

	private static Map<Boolean, HttpUtil> instance = new HashMap();
	private static HttpClient httpClient;

	/**
	 * Create an HttpUtil class
	 * Handling SSL or not
	 *
	 * @param disableSSL
	 * @throws HttpException
	 */
	protected HttpUtil(boolean disableSSL) throws HttpException {
		httpClient = getHttpClient(disableSSL);
	}

	/**
	 * Multiton management to ease retrieval of an SSL-HTTP compliant utility or not
	 *
	 * @param bypassSSL defines whether or not we want to handle SSL
	 * @return the relative HttpUtil (SSL compliant or not)
	 * @throws HttpException
	 */
	public static synchronized final HttpUtil getInstance(Boolean bypassSSL) throws HttpException {
		HttpUtil httpUtil = instance.get(Boolean.valueOf(bypassSSL));
		if (httpUtil == null) {
			httpUtil = new HttpUtil(bypassSSL);
			instance.put(Boolean.valueOf(bypassSSL), httpUtil);
		}
		return httpUtil;
	}

	/**
	 * Performs a GET onto the HTTP server
	 *
	 * @param url         the URL to call
	 * @param httpHeaders a Map object representing the HTTP headers to be sent with the operation
	 * @return JSON response from the server
	 * @throws HttpException
	 */
	public String get(String url, Map<String, String> httpHeaders) throws HttpException {

		return callHttp(url, "", null, HTTP_GET, httpHeaders);
	}

	/**
	 * Performs a PUT onto the HTTP server
	 *
	 * @param url         the URL to call
	 * @param request     the JSON request to be performed
	 * @param httpHeaders a Map object representing the HTTP headers to be sent with the operation
	 * @throws HttpException
	 * @return JSON response from the server
	 */
	public String put(String url, String request, Map<String, String> httpHeaders) throws HttpException {

		return callHttp(url, request, null, HTTP_PUT, httpHeaders);
	}

	/**
	 * Performs a POST onto the HTTP server
	 *
	 * @param url         the URL to call
	 * @param request     the JSON request to be performed
	 * @param httpHeaders a Map object representing the HTTP headers to be sent with the operation
	 * @throws HttpException
	 * @return JSON response from the server
	 */
	public String post(String url, String request, Map<String, String> httpHeaders) throws HttpException {

		return callHttp(url, request, null, HTTP_POST, httpHeaders);
	}

	/**
	 * Performs a POST Using URL-ENCODED format onto the HTTP server (used for Plex authentication)
	 *
	 * @param url         the URL to call
	 * @param paramsPair  the key/value representation of the URL-ENCODED format
	 * @param httpHeaders a Map object representing the HTTP headers to be sent with the operation
	 * @throws HttpException
	 * @return JSON response from the server
	 */
	public String postUrlEncoded(String url, List<NameValuePair> paramsPair, Map<String, String> httpHeaders) throws HttpException {

		return callHttp(url, null, paramsPair, HTTP_POST, httpHeaders);
	}

	/**
	 * Performs a DELETE onto the HTTP server
	 *
	 * @param url         the URL to call
	 * @param httpHeaders a Map object representing the HTTP headers to be sent with the operation
	 * @return JSON response from the server
	 * @throws HttpException
	 */
	public String delete(String url, Map<String, String> httpHeaders) throws HttpException {

		return callHttp(url, "", null, HTTP_DELETE, httpHeaders);
	}

	/**
	 * Utility to get the appropriate HTTP Client object based on the operation
	 *
	 * @param url           the URL to call
	 * @param operationType The operation to perform (GET/POST...)
	 * @return
	 * @throws HttpException
	 */
	private HttpRequestBase retrieveHttpRequestBase(String url, String operationType) throws HttpException {
		switch (operationType) {
			case HTTP_GET:
				return new HttpGet(url);
			case HTTP_PUT:
				return new HttpPut(url);
			case HTTP_POST:
				return new HttpPost(url);
			case HTTP_DELETE:
				return new HttpDelete(url);
			default:
				throw new HttpException("Unrecognized operation type [" + operationType + "]", null, null, url, null, operationType);
		}

	}

	/**
	 * Centralized HTTP call (used indistinctively for GET / POST..)
	 *
	 * @param url           the URL to call
	 * @param request       the JSON request to be performed
	 * @param paramsPair    the key/value representation of the URL-ENCODED format
	 * @param httpHeaders   a Map object representing the HTTP headers to be sent with the operation
	 * @param operationType the operation to perform (GET/POST...)
	 * @return JSON response from the server
	 * @throws HttpException
	 */
	private String callHttp(String url, String request, List<NameValuePair> paramsPair, String operationType, Map<String, String> httpHeaders) throws HttpException {
		String httpResponse = "";
		HttpResponse response = null;
		// retrieve the relevant HTTP Client object based on the operation and the URL
		HttpRequestBase httpOperation = retrieveHttpRequestBase(url, operationType);

		// Maps the header
		for (Map.Entry<String, String> entrySet : httpHeaders.entrySet()) {
			httpOperation.setHeader(entrySet.getKey(), entrySet.getValue());
		}

		try {
			// In case of post and put we need to set a body
			if (httpOperation instanceof HttpPost) {

				// URL-ENCODED or plain RAW JSON text is supported for POST
				if (FORM_URLENCODED.equals(httpHeaders.getOrDefault("Content-Type", ""))) {
					((HttpPost) httpOperation).setEntity(new UrlEncodedFormEntity(paramsPair));
				} else {
					StringEntity params = new StringEntity(request, "UTF-8");
					((HttpPost) httpOperation).setEntity(params);
				}
			} else if (httpOperation instanceof HttpPut) {
				StringEntity params = new StringEntity(request, "UTF-8");
				((HttpPut) httpOperation).setEntity(params);
			}

			// Retrieving the response from the HTTP server
			response = httpClient.execute(httpOperation);
			httpResponse = IOUtils.toString(response.getEntity().getContent());
			IOUtils.closeQuietly(response.getEntity().getContent());

			// Success HTTP code is 2xx
			if (!(response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() <= 299)) {
				throw new HttpException("HTTP call failed", request, httpResponse, url, response.getStatusLine().getStatusCode(), operationType);
			}
			return httpResponse;

		} catch (IOException err) {
			throw new HttpException("HTTP call failed due to IO Exception", request, httpResponse, url, null, operationType, err);
		}
		finally{
			if (response != null) {
				try {
					IOUtils.closeQuietly(response.getEntity().getContent());
				}
				catch (Exception err){}
			}
		}
	}

	/**
	 * Utility to get an SSL compliant HTTP Client or not
	 *
	 * @param bypassSSL do we have to bypass SSL checks
	 * @return hte relevant HTTP Client
	 * @throws HttpException
	 */
	private HttpClient getHttpClient(boolean bypassSSL) throws HttpException {
		if (!bypassSSL) {
			return HttpClientBuilder.create().build();
		} else {
			try {
				// Disable Hostname verification
				HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
				SSLContext sslContext = SSLContext.getInstance("SSL");
				// set up a TrustManager that trusts everything
				sslContext.init(null, new TrustManager[]{new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkClientTrusted(X509Certificate[] certs,
																				 String authType) {
					}

					public void checkServerTrusted(X509Certificate[] certs,
																				 String authType) {
					}
				}}, new SecureRandom());

				SSLSocketFactory sf = new SSLSocketFactory(sslContext);
				sf.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
				Scheme httpsScheme = new Scheme("https", 443, sf);
				SchemeRegistry schemeRegistry = new SchemeRegistry();
				schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
				schemeRegistry.register(httpsScheme);

				// apache HttpUtil version >4.2 should use BasicClientConnectionManager
				ClientConnectionManager cm = new SingleClientConnManager(schemeRegistry);
				HttpClient httpClient = new DefaultHttpClient(cm);
				HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
				return httpClient;
			} catch (KeyManagementException err) {
				throw new HttpException(err.getMessage(), err);
			} catch (NoSuchAlgorithmException err) {
				throw new HttpException("SSL Algorithm is not available " + err.getMessage(), err);
			}
		}
	}
}

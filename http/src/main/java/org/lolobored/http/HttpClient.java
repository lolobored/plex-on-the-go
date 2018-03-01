package org.lolobored.http;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The <code>HttpClient</code> is ... //TODO document class header
 */
public class HttpClient {

    public static final String HTTP_GET = "GET";
    public static final String HTTP_PUT = "PUT";
    public static final String HTTP_POST = "POST";
    public static final String HTTP_DELETE = "DELETE";

    public static final String FORM_URLENCODED= "application/x-www-form-urlencoded";


    public static String get(String url, Map<String, String> httpHeaders, boolean disableSSL) throws HttpException {

        return callHttp(url, "", null, HTTP_GET, httpHeaders, disableSSL);
    }

    public static String put(String url, String request, Map<String, String> httpHeaders, boolean disableSSL) throws HttpException {

        return callHttp(url, request,null, HTTP_PUT, httpHeaders, disableSSL);
    }

    public static String post(String url, String request, Map<String, String> httpHeaders, boolean disableSSL) throws HttpException {

        return callHttp(url, request, null, HTTP_POST, httpHeaders, disableSSL);
    }

    public static String postUrlEncoded(String url, List<NameValuePair> paramsPair, Map<String, String> httpHeaders, boolean disableSSL) throws HttpException {

        return callHttp(url, null, paramsPair, HTTP_POST, httpHeaders, disableSSL);
    }

    public static String delete(String url, Map<String, String> httpHeaders, boolean disableSSL) throws HttpException {

        return callHttp(url, "", null, HTTP_DELETE, httpHeaders, disableSSL);
    }

    private static HttpRequestBase retrieveHttpRequestBase(String url, String operationType) throws HttpException {
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

    private static String callHttp(String url, String request, List<NameValuePair> paramsPair, String operationType, Map<String, String> httpHeader, boolean disableSSL) throws HttpException {
        String httpResponse = "";
        HttpRequestBase httpOperation = retrieveHttpRequestBase(url, operationType);
        org.apache.http.client.HttpClient client = getHttpClient(disableSSL);
        for (Map.Entry<String, String> entrySet : httpHeader.entrySet()) {
            httpOperation.setHeader(entrySet.getKey(), entrySet.getValue());
        }

        try {

            if (httpOperation instanceof HttpPost) {
                if (FORM_URLENCODED.equals(httpHeader.getOrDefault("Content-Type", ""))){
                    ((HttpPost) httpOperation).setEntity(new UrlEncodedFormEntity(paramsPair));
                }
                else {
                    StringEntity params = new StringEntity(request);
                    ((HttpPost) httpOperation).setEntity(params);
                }
            } else if (httpOperation instanceof HttpPut) {
                StringEntity params = new StringEntity(request);
                ((HttpPut) httpOperation).setEntity(params);
            }


            HttpResponse response = client.execute(httpOperation);
            httpResponse = IOUtils.toString(response.getEntity().getContent());
            IOUtils.closeQuietly(response.getEntity().getContent());
            if (!(response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() <= 299)) {
                throw new HttpException("HTTP call failed", request, httpResponse, url, response.getStatusLine().getStatusCode(), operationType);
            }
            return httpResponse;

        } catch (IOException err) {
            throw new HttpException("HTTP call failed due to IO Exception", request, httpResponse, url, null, operationType, err);
        }
    }

    private static org.apache.http.client.HttpClient getHttpClient(boolean disableSSL) throws HttpException {
        if (!disableSSL) {
            return HttpClientBuilder.create().build();
        } else {
            try {
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

                // apache HttpClient version >4.2 should use BasicClientConnectionManager
                ClientConnectionManager cm = new SingleClientConnManager(schemeRegistry);
                org.apache.http.client.HttpClient httpClient = new DefaultHttpClient(cm);
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

/*
 * Koekiebox CONFIDENTIAL
 *
 * [2012] - [2017] Koekiebox (Pty) Ltd
 * All Rights Reserved.
 *
 * NOTICE: All information contained herein is, and remains the property
 * of Koekiebox and its suppliers, if any. The intellectual and
 * technical concepts contained herein are proprietary to Koekiebox
 * and its suppliers and may be covered by South African and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material is strictly
 * forbidden unless prior written permission is obtained from Koekiebox.
 */

package com.backbase.csmdemo.dao;

import com.backbase.csmdemo.dao.exception.HttpClientException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * Base class for all REST related calls.
 *
 * @author jasonbruwer
 * @since v1.0
 *
 * @see JSONObject
 * @see HttpEntity
 * @see HttpResponse
 * @see HttpClient
 * @see ContentType
 * @see StringEntity
 */
public abstract class ABaseClientWS {

    //public static final String APPLICATION_JSON_CHARSET_UTF8 = "application/json; charset=UTF-8";

    public static final String CONTENT_TYPE_HEADER = "Content-type";

    //Protected variables used by subclasses...
    protected String endpointUrl = "https://localhost:8443/fluid-ws/";
    protected String serviceTicket;

    private static String EQUALS = "=";
    private static String AMP = "&";

    private static String REGEX_AMP = "\\&";
    private static String REGEX_EQUALS = "\\=";

    public static boolean IS_IN_JUNIT_TEST_MODE = false;

    public static String SYSTEM_PROP_FLUID_TRUST_STORE = "fluid.httpclient.truststore";
    public static String SYSTEM_PROP_FLUID_TRUST_STORE_PASSWORD = "fluid.httpclient.truststore.password";

    private CloseableHttpClient closeableHttpClient;

    /**
     * The HTML Form Name and Value mapping.
     */
    public static class FormNameValue{
        private String name;
        private String value;

        /**
         * Sets the HTML name and value.
         *
         * @param nameParam The HTML name.
         * @param valueParam The HTML value.
         */
        public FormNameValue(String nameParam, String valueParam) {
            this.name = nameParam;
            this.value = valueParam;
            this.value = ABaseClientWS.encodeParam(this.value);
        }

        /**
         * Gets the Form Param Name.
         *
         * @return The Form Param Name.
         */
        public String getName() {
            return this.name;
        }

        /**
         * Gets the Form Param Value.
         *
         * @return The Form Param Value.
         */
        public String getValue() {
            return value;
        }
    }

    /**
     * The HTML Header Name and Value mapping.
     */
    public static class HeaderNameValue{
        private String name;
        private String value;

        /**
         * Sets the HTML Header name and value.
         *
         * @param nameParam The HTML header name.
         * @param valueParam The HTML header value.
         */
        public HeaderNameValue(String nameParam, String valueParam) {
            this.name = nameParam;
            this.value = valueParam;
        }

        /**
         * Gets the Form Param Name.
         *
         * @return The Form Param Name.
         */
        public String getName() {
            return this.name;
        }

        /**
         * Gets the Form Param Value.
         *
         * @return The Form Param Value.
         */
        public String getValue() {
            return value;
        }
    }

    /**
     * The HTTP Method type to use.
     *
     * See: https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol#Request_methods
     */
    private enum HttpMethod
    {
        GET,
        POST,
        PUT,
        DELETE
    }

    /**
     * Creates a new client and sets the Base Endpoint URL.
     *
     * @param endpointBaseUrlParam URL to base endpoint.
     */
    public ABaseClientWS(String endpointBaseUrlParam) {
        super();

        if(endpointBaseUrlParam == null || endpointBaseUrlParam.trim().isEmpty())
        {
            this.endpointUrl = "https://localhost:8443/fluid-ws/";
        }
        else
        {
            this.endpointUrl = endpointBaseUrlParam;
        }
    }

    /**
     * Performs an HTTP request with {@code postfixUrlParam} on {@code httpClientParam}.
     *
     * @param httpClientParam The Apache Http Client to use.
     * @param httpUriRequestParam The Apache URI Request.
     * @param responseHandlerParam The response from the request handler.
     * @param postfixUrlParam URL mapping after the Base endpoint.
     * @return Return body as JSON.
     *
     * @see HttpClient
     * @see HttpUriRequest
     * @see ResponseHandler
     */
    private String executeHttp(
            HttpClient httpClientParam,
            HttpUriRequest httpUriRequestParam,
            ResponseHandler responseHandlerParam,
            String postfixUrlParam)
    throws HttpClientException
    {
        try {
            Object returnedObj = httpClientParam.execute(httpUriRequestParam, responseHandlerParam);
            if(returnedObj instanceof String)
            {
                return (String)returnedObj;
            }

            throw new HttpClientException(
                    "Expected 'String' got '"+(
                            (returnedObj == null) ? null:returnedObj.getClass().getName()));
        }
        //IO Problem...
        catch (IOException e) {

            if(e instanceof UnknownHostException)
            {
                throw new HttpClientException(
                        "Unable to reach host '"+
                                this.endpointUrl.concat(postfixUrlParam)+"'. "+e.getMessage(),
                        e);
            }

            if(e instanceof ConnectException)
            {
                throw new HttpClientException(e.getMessage(), e);
            }

            throw new HttpClientException(
                    e.getMessage(), e);
        }
    }

    /**
     * Performs an HTTP-GET request with {@code postfixUrlParam}.
     *
     * @param postfixUrlParam URL mapping after the Base endpoint.
     * @return Return body as JSON.
     *
     * @throws HttpClientException If HTTP communication fails.
     * 
     * @see JSONObject
     *
     */
    public JSONObject getJson(
            String postfixUrlParam)
            throws HttpClientException{

        return this.getJson(postfixUrlParam, null);
    }

    /**
     * Performs an HTTP-GET request with {@code postfixUrlParam}.
     *
     * @param postfixUrlParam URL mapping after the Base endpoint.
     * @return Return body as JSON.
     *
     * @throws HttpClientException If HTTP communication fails.
     *
     * @see JSONObject
     *
     */
    public JSONArray getJsonArray(
            String postfixUrlParam)
            throws HttpClientException{

        return this.getJsonArray(postfixUrlParam, null);
    }

    /**
     * Performs an HTTP-GET request with {@code postfixUrlParam}.
     *
     * @param postfixUrlParam URL mapping after the Base endpoint.
     * @param headerNameValuesParam The HTTP Headers to include.
     *
     * @return Return body as JSON.
     *
     * @throws HttpClientException If HTTP communication fails.
     *
     * @see JSONObject
     */
    public JSONArray getJsonArray(
            String postfixUrlParam,
            List<HeaderNameValue> headerNameValuesParam)
            throws HttpClientException{

        CloseableHttpClient httpclient = this.getClient();

        try {
            HttpGet httpGet = new HttpGet(this.endpointUrl.concat(postfixUrlParam));

            if(headerNameValuesParam != null && !headerNameValuesParam.isEmpty())
            {
                for(HeaderNameValue headerNameVal : headerNameValuesParam)
                {
                    if(headerNameVal.getName() == null || headerNameVal.getName().trim().isEmpty())
                    {
                        continue;
                    }

                    if(headerNameVal.getValue() == null)
                    {
                        continue;
                    }

                    httpGet.setHeader(headerNameVal.getName(), headerNameVal.getValue());
                }
            }

            // Create a custom response handler
            ResponseHandler<String> responseHandler = this.getJsonResponseHandler(
                    this.endpointUrl.concat(postfixUrlParam));

            String responseBody = this.executeHttp(
                    httpclient, httpGet, responseHandler, postfixUrlParam);

            if(responseBody == null || responseBody.trim().isEmpty())
            {
                throw new HttpClientException(
                        "No response data from '"+
                                this.endpointUrl.concat(postfixUrlParam)+"'.");
            }

            //Iter until we hit the array bracket...
            while (!responseBody.startsWith("[") && !responseBody.isEmpty())
            {
                responseBody = responseBody.substring(1, responseBody.length());
            }
            
            JSONArray jsonOjb = new JSONArray(responseBody);

            return jsonOjb;
        }
        //
        catch (JSONException jsonExcept)
        {
            throw new HttpClientException(jsonExcept.getMessage());
        }
    }

    
    /**
     * Performs an HTTP-GET request with {@code postfixUrlParam}.
     *
     * @param postfixUrlParam URL mapping after the Base endpoint.
     * @param headerNameValuesParam The HTTP Headers to include.
     *
     * @return Return body as JSON.
     *
     * @throws HttpClientException If HTTP communication fails.
     *
     * @see JSONObject
     */
    public JSONObject getJson(
            String postfixUrlParam,
            List<HeaderNameValue> headerNameValuesParam)
    throws HttpClientException{

        CloseableHttpClient httpclient = this.getClient();

        try {
            HttpGet httpGet = new HttpGet(this.endpointUrl.concat(postfixUrlParam));

            if(headerNameValuesParam != null && !headerNameValuesParam.isEmpty())
            {
                for(HeaderNameValue headerNameVal : headerNameValuesParam)
                {
                    if(headerNameVal.getName() == null || headerNameVal.getName().trim().isEmpty())
                    {
                        continue;
                    }

                    if(headerNameVal.getValue() == null)
                    {
                        continue;
                    }

                    httpGet.setHeader(headerNameVal.getName(), headerNameVal.getValue());
                }
            }

            // Create a custom response handler
            ResponseHandler<String> responseHandler = this.getJsonResponseHandler(
                    this.endpointUrl.concat(postfixUrlParam));

            String responseBody = this.executeHttp(
                    httpclient, httpGet, responseHandler, postfixUrlParam);

            if(responseBody == null || responseBody.trim().isEmpty())
            {
                throw new HttpClientException(
                        "No response data from '"+
                                this.endpointUrl.concat(postfixUrlParam)+"'.");
            }

            JSONObject jsonOjb = new JSONObject(responseBody);

            return jsonOjb;
        }
        //
        catch (JSONException jsonExcept)
        {
            throw new HttpClientException(jsonExcept.getMessage());
        }
    }

    /**
     * Get a text based response handler used mainly for JSON.
     *
     * @param urlCalledParam The url called.
     * @return String based response handler.
     * @throws HttpClientException If HTTP communication fails.
     */
    private ResponseHandler<String> getJsonResponseHandler(final String urlCalledParam)
    throws HttpClientException{
        // Create a custom response handler
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

            /**
             * Process the {@code responseParam} and return text if valid.
             *
             * @param responseParam The HTTP response from the server.
             * @return Text response.
             * @throws IOException If there are any communication or I/O problems.
             */
            public String handleResponse(final HttpResponse responseParam) throws IOException {

                int status = responseParam.getStatusLine().getStatusCode();
                if (status == 404) {
                    throw new IOException(
                            "Endpoint for Service not found. URL ["+
                                    urlCalledParam+"].");
                }
                //
                else if (status >= 200 && status < 300) {
                    HttpEntity entity = responseParam.getEntity();

                    String responseJsonString = (entity == null) ? null:
                            EntityUtils.toString(entity);

                    if(responseJsonString != null &&
                            responseJsonString.startsWith("<html>"))
                    {
                        throw new IOException(
                                "Return data is HTML instead of JSON.");
                    }

                    return responseJsonString;
                }
                //Bad Request... Server Side Error meant for client...
                else if (status == 400) {
                    HttpEntity entity = responseParam.getEntity();

                    String responseJsonString = (entity == null) ? null :
                            EntityUtils.toString(entity);

                    return responseJsonString;
                }
                //
                else {
                    HttpEntity entity = responseParam.getEntity();

                    String responseString = (entity != null) ?
                            EntityUtils.toString(entity) : null;

                    throw new IOException(
                            "Unexpected response status: " + status+". "
                            +responseParam.getStatusLine().getReasonPhrase()+". \nResponse Text ["+
                                    responseString+"]");
                }
            }
        };

        return responseHandler;
    }

    /**
     * Translates a string into {@code application/x-www-form-urlencoded}
     * format using a specific encoding scheme. This method uses the
     * supplied encoding scheme to obtain the bytes for unsafe
     * characters.
     *
     * @param textParam The text to URL encode.
     * @return Encoded text from {@code textParam}.
     *
     * @see URLEncoder#encode(String, String)
     */
    public static String encodeParam(String textParam)
    {
        if(textParam == null)
        {
            return null;
        }

        try {
            return URLEncoder.encode(textParam,"UTF-8");
        }
        //
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Checks whether the {@code textParam} is {@code null} or empty.
     *
     * @param textParam The text to check.
     * @return Whether the {@code textParam} is empty.
     */
    protected final boolean isEmpty(String textParam) {
        return (textParam == null) ? true : textParam.trim().isEmpty();
    }

    /**
     * Creates a new Http client.
     *
     * If part of a test run, the Http client will accept
     * self signed certificates.
     *
     * See flag {@code IS_IN_JUNIT_TEST_MODE}.En
     *
     * @return CloseableHttpClient that may or may not accept
     * self signed certificates.
     *
     * @throws HttpClientException If HTTP communication fails.
     */
    private CloseableHttpClient getClient()
    throws HttpClientException{
        if(this.closeableHttpClient != null)
        {
            return this.closeableHttpClient;
        }

        //Only accept self signed certificate if in Junit test case.
        String pathToFluidTrustStore = this.getPathToFluidSpecificTrustStore();
        //Test mode...
        if(IS_IN_JUNIT_TEST_MODE || pathToFluidTrustStore != null)
        {
            SSLContextBuilder builder = new SSLContextBuilder();

            try {
                //builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
                if(pathToFluidTrustStore == null)
                {
                    builder.loadTrustMaterial(new SSLTrustAll());
                }
                else
                {
                    String password = "";
                    if(IS_IN_JUNIT_TEST_MODE)
                    {
                        builder.loadTrustMaterial(
                                new File(pathToFluidTrustStore),
                                password.toCharArray(),
                                new SSLTrustAll());
                    }
                    else
                    {
                        builder.loadTrustMaterial(
                                new File(pathToFluidTrustStore),
                                password.toCharArray());
                    }
                }

                SSLContext sslContext = builder.build();

                this.closeableHttpClient = HttpClients.custom().setSSLSocketFactory(
                        new SSLConnectionSocketFactory(sslContext)).build();
            }
            //Changed for Java 1.6 compatibility...
            catch (NoSuchAlgorithmException e) {

                throw new HttpClientException(
                        "NoSuchAlgorithm: Unable to load self signed trust material. "+e.getMessage(),
                        e);
            } catch (KeyManagementException e) {

                throw new HttpClientException(
                        "KeyManagement: Unable to load self signed trust material. "+e.getMessage(), e);
            } catch (KeyStoreException e) {

                throw new HttpClientException(
                        "KeyStore: Unable to load self signed trust material. "+e.getMessage(), e);
            } catch (CertificateException e) {

                throw new HttpClientException(
                        "Certificate: Unable to load self signed trust material. "+e.getMessage(), e);
            } catch (IOException ioError) {

                throw new HttpClientException(
                        "IOError: Unable to load self signed trust material. "+ioError.getMessage(), ioError);
            }
        }
        //Default HTTP Client...
        else
        {
            /*CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials
                    = new UsernamePasswordCredentials("admin", "12345");
            provider.setCredentials(AuthScope.ANY, credentials);
            */
            
            this.closeableHttpClient = HttpClients.createDefault();
        }

        return this.closeableHttpClient;
    }

    /**
     * Retrieves the system property for the Fluid specific trust store.
     *
     * @return The {@code fluid.httpclient.truststore} system property value.
     *
     * @see System
     * @see java.util.Properties
     */
    private String getPathToFluidSpecificTrustStore()
    {
        String fluidSystemTrustStore =
                System.getProperty(SYSTEM_PROP_FLUID_TRUST_STORE);

        if(fluidSystemTrustStore == null || fluidSystemTrustStore.trim().isEmpty())
        {
            return null;
        }

        File certFile = new File(fluidSystemTrustStore);

        if(certFile.exists() && certFile.isFile())
        {
            return fluidSystemTrustStore;
        }

        return null;
    }

    /**
     * If the HTTP Client is set, this will
     * close and clean any connections that needs to be closed.
     *
     */
    public void closeAndClean()
    {
        CloseConnectionRunnable closeConnectionRunnable =
                new CloseConnectionRunnable(this);

        Thread closeConnThread = new Thread(
                closeConnectionRunnable,"Close ABaseClientWS Connection");
        closeConnThread.start();
    }

    /**
     * Close the SQL and ElasticSearch Connection, but not in
     * a separate {@code Thread}.
     *
     * @throws HttpClientException If we are unable to close.
     */
    protected void closeConnectionNonThreaded()
    throws HttpClientException
    {
        if(this.closeableHttpClient != null)
        {
            try {
                this.closeableHttpClient.close();
            }
            //
            catch (IOException e) {

                throw new HttpClientException(
                        "Unable to close Http Client connection. "+
                                e.getMessage(), e);
            }
        }

        this.closeableHttpClient = null;
    }

    /**
     * Trust all SSL type connections.
     */
    private static final class SSLTrustAll implements TrustStrategy
    {
        /**
         *
         * @param x509Certificates List of X509 certificates.
         * @param stringParam Text.
         * @return {@code true}, always trusted.
         * @throws CertificateException If there is a cert problem.
         */
        @Override
        public boolean isTrusted(X509Certificate[] x509Certificates, String stringParam) throws CertificateException {
            return true;
        }
    }

    /**
     * Utility class to close the connection in a thread.
     */
    private static class CloseConnectionRunnable implements Runnable{

        private ABaseClientWS baseClientWS;

        /**
         * The resource to close.
         *
         * @param aBaseClientWSParam Base utility to close.
         */
        public CloseConnectionRunnable(ABaseClientWS aBaseClientWSParam) {
            this.baseClientWS = aBaseClientWSParam;
        }

        /**
         * Performs the threaded operation.
         */
        @Override
        public void run() {

            if(this.baseClientWS == null)
            {
                return;
            }

            try {
                this.baseClientWS.closeConnectionNonThreaded();
            }
            //
            catch (HttpClientException eParam) {
                eParam.printStackTrace();
            }
        }
    }
}

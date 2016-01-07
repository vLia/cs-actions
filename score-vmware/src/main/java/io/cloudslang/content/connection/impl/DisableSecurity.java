package io.cloudslang.content.connection.impl;

import io.cloudslang.content.constants.Constants;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.*;

/**
 * Created by Mihai Tusa.
 * 10/27/2015.
 */
public class DisableSecurity {

    /*
     * Authentication is handled by using a TrustManager and supplying a hostname verifier method.
     * (The host name verifier is declared in the main function.)
     */
    private static class TrustAllTrustManager implements TrustManager, X509TrustManager {

        public X509Certificate[] getAcceptedIssuers() {return null;}

        public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {}

        public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {}
    }

    public static void trustEveryone() throws NoSuchAlgorithmException, KeyManagementException {
        // Declare a host name verifier that will automatically enable the connection.
        // The host name verifier is invoked during the SSL handshake.
        HostnameVerifier verifier = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                return true;
            }
        };

        // Create the trust manager.
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager trustManager = new TrustAllTrustManager();
        trustAllCerts[0] = trustManager;

        // Create the SSL context
        SSLContext sc = SSLContext.getInstance(Constants.SSL);

        // Create the session context
        SSLSessionContext sslsc = sc.getServerSessionContext();

        // Initialize the contexts; the session context takes the trust manager.
        sslsc.setSessionTimeout(0);
        sc.init(null, trustAllCerts, null);

        // Use the default socket factory to create the socket for the secure connection
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Set the default host name verifier to enable the connection.
        HttpsURLConnection.setDefaultHostnameVerifier(verifier);
    }
}

package com.yunli.bigdata.producer.sample.configuration;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

/**
 * @author david
 * @date 2023-12-15 11:07:18
 */
public class HttpsClientRequestFactory extends SimpleClientHttpRequestFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpsClientRequestFactory.class);

  @Override
  protected void prepareConnection(HttpURLConnection connection, String httpMethod) {
    try {
      if (!(connection instanceof HttpsURLConnection)) {
        throw new RuntimeException("An instance of HttpsURLConnection is expected");
      }

      HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;

      TrustManager[] trustAllCerts = new TrustManager[]{
          new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
              return null;
            }
            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
              LOGGER.debug("checkClientTrusted from certs ");
            }
            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
              LOGGER.debug("checkServerTrusted from certs ");
            }

          }
      };
      SSLContext sslContext = SSLContext.getInstance("TLS");
      sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
      httpsConnection.setSSLSocketFactory(new CustomSSLSocketFactory(sslContext.getSocketFactory()));

      httpsConnection.setHostnameVerifier((s, sslSession) -> true);

      super.prepareConnection(httpsConnection, httpMethod);
    } catch (Exception e) {
      LOGGER.warn("connect error of exception: {}", e.getMessage(), e);
    }
  }


  /**
   * We need to invoke sslSocket.setEnabledProtocols(new String[] {"SSLv3"});
   * see http://www.oracle.com/technetwork/java/javase/documentation/cve-2014-3566-2342133.html (Java 8 section)
   */
  // SSLSocketFactory用于创建 SSLSockets
  private static class CustomSSLSocketFactory extends SSLSocketFactory {

    private final SSLSocketFactory delegate;

    public CustomSSLSocketFactory(SSLSocketFactory delegate) {
      this.delegate = delegate;
    }

    // 返回默认启用的密码套件。除非一个列表启用，对SSL连接的握手会使用这些密码套件。
    // 这些默认的服务的最低质量要求保密保护和服务器身份验证
    @Override
    public String[] getDefaultCipherSuites() {
      return delegate.getDefaultCipherSuites();
    }

    // 返回的密码套件可用于SSL连接启用的名字
    @Override
    public String[] getSupportedCipherSuites() {
      return delegate.getSupportedCipherSuites();
    }


    @Override
    public Socket createSocket(final Socket socket, final String host, final int port,
        final boolean autoClose) throws IOException {
      final Socket underlyingSocket = delegate.createSocket(socket, host, port, autoClose);
      return overrideProtocol(underlyingSocket);
    }


    @Override
    public Socket createSocket(final String host, final int port) throws IOException {
      final Socket underlyingSocket = delegate.createSocket(host, port);
      return overrideProtocol(underlyingSocket);
    }

    @Override
    public Socket createSocket(final String host, final int port, final InetAddress localAddress,
        final int localPort) throws
        IOException {
      final Socket underlyingSocket = delegate.createSocket(host, port, localAddress, localPort);
      return overrideProtocol(underlyingSocket);
    }

    @Override
    public Socket createSocket(final InetAddress host, final int port) throws IOException {
      final Socket underlyingSocket = delegate.createSocket(host, port);
      return overrideProtocol(underlyingSocket);
    }

    @Override
    public Socket createSocket(final InetAddress host, final int port, final InetAddress localAddress,
        final int localPort) throws
        IOException {
      final Socket underlyingSocket = delegate.createSocket(host, port, localAddress, localPort);
      return overrideProtocol(underlyingSocket);
    }

    private Socket overrideProtocol(final Socket socket) {
      if (!(socket instanceof SSLSocket)) {
        throw new RuntimeException("An instance of SSLSocket is expected");
      }
      ((SSLSocket) socket).setEnabledProtocols(new String[]{"TLSv1.2"});
      return socket;
    }
  }
}
package club.ok328.wnz.srv.util;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HttpClientUtils {

	Logger logger = LoggerFactory.getLogger(HttpClientUtils.class); // 日志

	private SSLContext sslcontext;
	private String networkErrorJson = "{\"flag\":\"998\",\"errmsg\":\"通讯异常\",\"results\":{}}";

	private HttpClientUtils() {
	}

	/**
	 * 默认实例化 默认接受所有的ssl证书
	 *
	 * @return HttpClientUtils实例
	 */
	public static HttpClientUtils defaultInstance() {
		HttpClientUtils httpClientUtils = new HttpClientUtils();
		httpClientUtils.setDefaultSSLContext();
		return httpClientUtils;
	}

	public static HttpClientUtils instanceWithoutSSL() {
		HttpClientUtils httpClientUtils = new HttpClientUtils();
		return httpClientUtils;
	}

	/**
	 * 实例化并装载CA证书
	 *
	 * @return HttpClientUtils实例
	 */
	public static HttpClientUtils instance(String CA_filePath) {
		HttpClientUtils httpClientUtils = new HttpClientUtils();
		httpClientUtils.setSSLContext(new File(CA_filePath));
		return httpClientUtils;
	}

	/**
	 * 设置CA证书
	 */
	@SuppressWarnings("deprecation")
	private void setSSLContext(File CA_file) {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			FileInputStream instream = new FileInputStream(CA_file);
			try {
				trustStore.load(instream, "nopassword".toCharArray());
			} finally {
				instream.close();
			}

			// Trust own CA and all self-signed certs
			sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("setSSLContext error : " + e);
		}
	}

	/**
	 * 默认接受证书
	 */
	private void setDefaultSSLContext() {
		try {
			sslcontext = SSLContext.getInstance("TLS");

			X509TrustManager tm = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}
			};
			sslcontext.init(null, new TrustManager[] { tm }, null); // SSL协议
		} catch (NoSuchAlgorithmException e) {
			logger.error("error : " + e.getMessage());
			e.printStackTrace();
		} catch (KeyManagementException e) {
			logger.error("error : " + e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	public String httpsPostJson(String uri, Map<String, String> header, String json) throws IOException {
		if ("".equals(uri) || uri == null) {
			logger.error("https post url is null");
			return "";
		}
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		logger.info("create httpclient ...");
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		HttpPost httpPost = new HttpPost(uri); // 请求地址
		if (header != null) {
			Iterator its = header.entrySet().iterator();
			while (its.hasNext()) {
				Entry entry = (Entry) its.next();
				logger.debug("header key " + entry.getKey().toString() + " value " + header.get(entry.getKey()).toString());
				httpPost.setHeader(entry.getKey().toString(), header.get(entry.getKey()).toString());
			}
		}
		CloseableHttpResponse response = null;
		try {
			if (StringUtils.isNotBlank(json)) {
				// 请求参数
				StringEntity se = new StringEntity(json, ContentType.create("text/html", "UTF-8"));
				httpPost.setEntity(se);
			}

			logger.info("HttpResponse is opening ... ");
			response = httpclient.execute(httpPost);
			if (response != null) {
				HttpEntity entity = response.getEntity();
				String jsonStr = "";
				if (entity != null) {
					jsonStr = EntityUtils.toString(entity);
				}
				EntityUtils.consume(entity); // 关闭entity
				return jsonStr;
			} else {
				return networkErrorJson;
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		} catch (ClientProtocolException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		} finally {
			if (response != null) {
				// 关闭CloseableHttpResponse
				try {
					response.close();
					logger.info("HttpResponse closed ");
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
		return "";
	}
	
	/**
	 * Get方法调用接口。
	 * @param url
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "deprecation", "unused" })
	public Map<String,Object> httpsGet(String uri,Map<String, String> header) throws IOException {
		Map<String,Object> result=new HashMap<String, Object>();
		
		if ("".equals(uri) || uri == null) {
			logger.error("https GET url is null");
			result.put("status", 500);
			return result;
		}
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		logger.info("create httpclient ...");
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		HttpGet httpGet = new HttpGet(uri); // 请求地址
		
		if (header != null) {
			Iterator its = header.entrySet().iterator();
			while (its.hasNext()) {
				Entry entry = (Entry) its.next();
				logger.debug("header key " + entry.getKey().toString() + " value " + header.get(entry.getKey()).toString());
				httpGet.setHeader(entry.getKey().toString(), header.get(entry.getKey()).toString());
			}
		}
		
		CloseableHttpResponse response = null;
		try {
			
			logger.info("HttpResponse is opening ... ");
			response = httpclient.execute(httpGet);
			int status=response.getStatusLine().getStatusCode();
			if (response != null) {
				HttpEntity entity = response.getEntity();
				String jsonStr = "";
				if (entity != null) {
					jsonStr = EntityUtils.toString(entity,"utf-8");
				}
				EntityUtils.consume(entity); // 关闭entity
				result.put("body", jsonStr);
				result.put("status", status);
				return result;
			} else {
				result.put("body", networkErrorJson);
				result.put("status", 500);
				return result;
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		} catch (ClientProtocolException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		} finally {
			if (response != null) {
				// 关闭CloseableHttpResponse
				try {
					response.close();
					logger.info("HttpResponse closed ");
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
		return result;
	}
	

}

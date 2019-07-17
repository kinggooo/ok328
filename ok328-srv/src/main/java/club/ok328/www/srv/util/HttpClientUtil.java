package club.ok328.www.srv.util;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.*;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import org.apache.log4j.Logger;

/**
 *
 * @author Luo Kai
 *
 */

public class HttpClientUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	/**
	 * Post方法调用接口。
	 * @param url
	 * @param params
	 * @param charset
	 * @return
	 */
	public static String doPost(String url, Map<String, String> params, String charset) {

		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();

		client.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
		client.getHttpConnectionManager().getParams().setSoTimeout(60000);

		PostMethod method = new PostMethod(url);
		// 设置Http Post数据
		method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
		if (params != null) {
			Set<String> keySet = params.keySet();
			NameValuePair[] param = new NameValuePair[keySet.size()];
			int i = 0;
			for (String key : keySet) {
				param[i] = new NameValuePair(key, params.get(key));
				i++;
			}
			method.setRequestBody(param);
		}
		InputStream responseBodyStream = null;
		InputStreamReader streamReader = null;
		BufferedReader reader = null;
		try {
			int responseCode = client.executeMethod(method);
			if (responseCode == HttpStatus.SC_OK) {

				responseBodyStream = method.getResponseBodyAsStream();
				streamReader = new InputStreamReader(responseBodyStream, charset);
				reader = new BufferedReader(streamReader);
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}

			}else{
				logger.error("执行HTTP Post请求" + url + "时，返回错误代码：return code = {}", responseCode);
			}

		} catch (IOException e) {
			logger.error("执行HTTP Post请求" + url + "时，发生异常！", e);
		} finally {
			try {
				if(null != responseBodyStream){
					responseBodyStream.close();
				}
				if(null != streamReader){
					streamReader.close();
				}
				if(null != reader){
					reader.close();
				}
			} catch (IOException e) {
				logger.error("执行HTTP Post请求" + url + "时，发生异常，关闭流异常！", e);
			}
			method.releaseConnection();
		}
		return response.toString();
	}

	/**
	 * Get方法调用接口。
	 * @param url
	 * @return
	 */
	public static String doGet(String url,String charset) {

		//创建默认的httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String retMsg = null;
        try {

        	URI uri = new URI(url);

            //用get方法发送http请求
            HttpGet get = new HttpGet(uri);

            //get.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            CloseableHttpResponse httpResponse = null;
            //发送get请求
            httpResponse = httpClient.execute(get);
            try{
                //response实体
            	HttpEntity entity = httpResponse.getEntity();
                if (null != entity){
                  //  System.out.println("响应状态码:"+ httpResponse.getStatusLine());
                	retMsg = EntityUtils.toString(entity,charset);
                }
            }catch (Exception e){
            	logger.error("执行HTTP Get请求" + url + "时，发生异常，返回值读取异常！", e);
            }
            finally{
                httpResponse.close();
            }

        } catch (Exception e) {
        	logger.error("执行HTTP Get请求" + url + "时，发生异常！", e);
        }
        finally{

            try{
                if (httpClient != null){
                	httpClient.close();
                }

            } catch (IOException e){
            	logger.error("执行HTTP Get请求" + url + "时，发生链接关闭异常！", e);
            }
        }

        return retMsg;
	}
	/**
     * Post方法调用接口。
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public static String doPost(String url, Map<String, String> params,  String charset,int Timeout) {

        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();

        client.getHttpConnectionManager().getParams().setConnectionTimeout(Timeout);
        client.getHttpConnectionManager().getParams().setSoTimeout(Timeout);
        PostMethod method = new PostMethod(url);

        // 设置Http Post数据
        method.setRequestHeader("Connection", "close");
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(1, false));
        if (params != null) {
            Set<String> keySet = params.keySet();
            NameValuePair[] param = new NameValuePair[keySet.size()];
            int i = 0;
            for (String key : keySet) {
                param[i] = new NameValuePair(key, params.get(key));
                i++;
            }
            method.setRequestBody(param);
        }
        InputStream responseBodyStream = null;
        InputStreamReader streamReader = null;
        BufferedReader reader = null;
        try {
            int responseCode = client.executeMethod(method);
            if (responseCode == HttpStatus.SC_OK) {

                responseBodyStream = method.getResponseBodyAsStream();
                streamReader = new InputStreamReader(responseBodyStream, charset);
                reader = new BufferedReader(streamReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

            }else{
				logger.error("执行HTTP Post请求" + url + "时，返回错误代码：return code = {}", responseCode);
			}

        } catch (IOException e) {
            logger.error("执行HTTP Post请求" + url + "时，发生异常！", e);
        } finally {
            try {
                if(null != responseBodyStream){
                    responseBodyStream.close();
                }
                if(null != streamReader){
                    streamReader.close();
                }
                if(null != reader){
                    reader.close();
                }
            } catch (IOException e) {
                logger.error("执行HTTP Post请求" + url + "时，发生异常，关闭流异常！", e);
            }
            method.releaseConnection();
        }

        return response.toString();
    }


    /**
     * 新方法实现文件上传。
     * @param url
     * @param params
     * @param charset
     * @param timeout
     * @param byteData
     * @return
     */
    public static String doPost(String url, Map<String, String> params, String charset, int timeout, String fileSuffex, String dataName , byte[] byteData)  {


        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();

        // 由于要上传的文件可能比较大 , 因此在此设置最大的连接超时时间
        client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
        client.getHttpConnectionManager().getParams().setSoTimeout(timeout);

        PostMethod method = new PostMethod(url);

        // 设置Http Post数据
        method.setRequestHeader("Accept-Language","zh-cn,zh;q=0.5");
        method.setRequestHeader("Accept-Charset","GBK,utf-8;q=0.7,*;q=0.7");
        method.setRequestHeader("Connection","keep-alive");

        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler(1, false));

        if (params != null) {

        	Part[] parts = new Part[params.size() + 1];

        	int iLoop = 0;
        	HttpMethodParams parameters = new HttpMethodParams();
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
            	parameters.setParameter(key, params.get(key));
            	method.setParameter(key, params.get(key));
            	Part strParam = new StringPart(key, params.get(key), charset);
            	parts[iLoop] = strParam;
            	iLoop ++;
            }

            method.setParams(parameters);

            String filename = String.valueOf(System.currentTimeMillis()) + "." + fileSuffex;
            ByteArrayPartSource partSource = new ByteArrayPartSource(filename,byteData);
            FilePart file = new FilePart(dataName, partSource,"multipart/form-data",charset);
            parts[iLoop] = file;

            MultipartRequestEntity multReq = new MultipartRequestEntity(parts,method.getParams());
            method.setRequestEntity(multReq);

        }else{
        	logger.error("参数设置错误，params = null");
        	return null;
        }


        InputStream responseBodyStream = null;
        InputStreamReader streamReader = null;
        BufferedReader reader = null;
        try {

            int responseCode = client.executeMethod(method);
            if (responseCode == HttpStatus.SC_OK) {

                responseBodyStream = method.getResponseBodyAsStream();
                streamReader = new InputStreamReader(responseBodyStream, charset);
                reader = new BufferedReader(streamReader);
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }else{

            	logger.error("执行HTTP Post请求" + url + "时，发生异常！ responseCode = {}", responseCode);
            }

        } catch (IOException e) {
            logger.error("执行HTTP Post请求" + url + "时，发生IO操作异常！ error = {}", e.getMessage());
        }
        catch (Exception e){
        	logger.error("执行HTTP Post请求" + url + "时，发生异常！ error = {}", e.getMessage());

        } finally {
            try {
                if(null != responseBodyStream){
                    responseBodyStream.close();
                }
                if(null != streamReader){
                    streamReader.close();
                }
                if(null != reader){
                    reader.close();
                }
            } catch (IOException e) {
                logger.error("执行HTTP Post请求" + url + "时，发生关闭流异常！", e);
            }
            method.releaseConnection();
        }

        return response.toString();

    }

    /**
     * 新方法实现文件上传。
     * @param url
     * @param params
     * @param charset
     * @param timeout
     * @param byteData
     * @return
     */
    public static String doPost(String url, Map<String, String> params, String charset, int timeout, String fileSuffex, List<String> dataName , List<byte[]> byteData)  {


        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();

        // 由于要上传的文件可能比较大 , 因此在此设置最大的连接超时时间
        client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
        client.getHttpConnectionManager().getParams().setSoTimeout(timeout);

        PostMethod method = new PostMethod(url);

        // 设置Http Post数据
        method.setRequestHeader("Accept-Language","zh-cn,zh;q=0.5");
        method.setRequestHeader("Accept-Charset","GBK,utf-8;q=0.7,*;q=0.7");
        method.setRequestHeader("Connection","keep-alive");

        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler(1, false));

        if (params != null) {

            Part[] parts = new Part[params.size() + byteData.size()];

            int iLoop = 0;
            HttpMethodParams parameters = new HttpMethodParams();
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                parameters.setParameter(key, params.get(key));
                method.setParameter(key, params.get(key));
                Part strParam = new StringPart(key, params.get(key), charset);
                parts[iLoop] = strParam;
                iLoop ++;
            }

            method.setParams(parameters);
            String filename;
            ByteArrayPartSource partSource;
            FilePart file;
            int i = 0;
            for (byte[] data: byteData) {
                filename = String.valueOf(System.currentTimeMillis())+"_"+iLoop + "." + fileSuffex;
                partSource = new ByteArrayPartSource(filename,data);
                file = new FilePart(dataName.get(i), partSource,"multipart/form-data",charset);
                parts[iLoop] = file;
                iLoop++;
                i++;
            }


            MultipartRequestEntity multReq = new MultipartRequestEntity(parts,method.getParams());
            method.setRequestEntity(multReq);

        }else{
            logger.error("参数设置错误，params = null");
            return null;
        }


        InputStream responseBodyStream = null;
        InputStreamReader streamReader = null;
        BufferedReader reader = null;
        try {

            int responseCode = client.executeMethod(method);
            if (responseCode == HttpStatus.SC_OK) {

                responseBodyStream = method.getResponseBodyAsStream();
                streamReader = new InputStreamReader(responseBodyStream, charset);
                reader = new BufferedReader(streamReader);
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }else{
                StringBuffer resp= new StringBuffer();
                responseBodyStream = method.getResponseBodyAsStream();
                streamReader = new InputStreamReader(responseBodyStream, charset);
                reader = new BufferedReader(streamReader);
                String line = "";
                while ((line = reader.readLine()) != null) {
                    resp.append(line);
                }
                logger.error("执行HTTP Post请求" + url + "时，发生异常！ responseCode = {},message={}", responseCode,resp);
            }

        } catch (IOException e) {
            logger.error("执行HTTP Post请求" + url + "时，发生IO操作异常！ error = {}", e.getMessage());
        }
        catch (Exception e){
            logger.error("执行HTTP Post请求" + url + "时，发生异常！ error = {}", e.getMessage());

        } finally {
            try {
                if(null != responseBodyStream){
                    responseBodyStream.close();
                }
                if(null != streamReader){
                    streamReader.close();
                }
                if(null != reader){
                    reader.close();
                }
            } catch (IOException e) {
                logger.error("执行HTTP Post请求" + url + "时，发生关闭流异常！", e);
            }
            method.releaseConnection();
        }

        return response.toString();

    }


    /**
     * Post方法调用接口。
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public static String doPost(String url, Map<String, String> params,Map<String,String> headers, String charset) {
        String result="";
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();

        client.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
        client.getHttpConnectionManager().getParams().setSoTimeout(60000);

        PostMethod method = new PostMethod(url);
        // 设置Http Post数据
        method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        for(Map.Entry<String, String> head : headers.entrySet()){
            method.setRequestHeader(head.getKey(),head.getValue());
        }

        if (params != null) {
            Set<String> keySet = params.keySet();
            NameValuePair[] param = new NameValuePair[keySet.size()];
            int i = 0;
            for (String key : keySet) {
                param[i] = new NameValuePair(key, params.get(key));
                i++;
            }
            method.setRequestBody(param);
        }
        InputStream responseBodyStream = null;
        InputStreamReader streamReader = null;
        BufferedReader reader = null;
        try {
            int responseCode = client.executeMethod(method);
            result= String.valueOf(responseCode);
            if (responseCode == HttpStatus.SC_OK) {

                responseBodyStream = method.getResponseBodyAsStream();
                streamReader = new InputStreamReader(responseBodyStream, charset);
                reader = new BufferedReader(streamReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

            }else{
                logger.error("执行HTTP Post请求" + url + "时，返回错误代码：return code = {}", responseCode);
            }

        } catch (IOException e) {
            logger.error("执行HTTP Post请求" + url + "时，发生异常！", e);
        } finally {
            try {
                if(null != responseBodyStream){
                    responseBodyStream.close();
                }
                if(null != streamReader){
                    streamReader.close();
                }
                if(null != reader){
                    reader.close();
                }
            } catch (IOException e) {
                logger.error("执行HTTP Post请求" + url + "时，发生异常，关闭流异常！", e);
            }
            method.releaseConnection();
        }
        return  result;
    }
}

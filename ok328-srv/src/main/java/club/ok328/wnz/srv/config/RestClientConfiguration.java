package club.ok328.wnz.srv.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

//@Configuration
public class RestClientConfiguration {
    @Value("${remote.maxTotalConnect:0}")
    private int maxTotalConnect; //连接池的最大连接数默认为0

    @Value("${remote.maxConnectPerRoute:200}")
    private int maxConnectPerRoute; //单个主机的最大连接数

    @Value("${remote.connectTimeout:2000}")
    private int connectTimeout; //连接超时默认2s

    @Value("${remote.readTimeout:30000}")
    private int readTimeout; //读取超时默认30s

    /**
     * create ClosablehttpClient
     *
     * @return httpClient
     */
//    @Bean(name = )
//    public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
//
//        //https config
//        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
//
//        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
//                .loadTrustMaterial(null, acceptingTrustStrategy)
//                .build();
//
//        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext
//                ,null, null, NoopHostnameVerifier.INSTANCE);
//
//        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
//                .register("http", PlainConnectionSocketFactory.getSocketFactory())
//                .register("https", csf)
//                .build();
//
//        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
//        // 最大连接数3000
//        connectionManager.setMaxTotal(300);
//        // 路由链接数400
//        connectionManager.setDefaultMaxPerRoute(150);
//        RequestConfig requestConfig = RequestConfig.custom()
//                .setSocketTimeout(60000)
//                .setConnectTimeout(60000)
//                .setConnectionRequestTimeout(10000)
//                .build();
//
//        HttpComponentsClientHttpRequestFactory requestFactory =
//                new HttpComponentsClientHttpRequestFactory();
//
//        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig)
//                .setConnectionManager(connectionManager)
//                .evictExpiredConnections()
//                .evictIdleConnections(30, TimeUnit.SECONDS)
//                .build();
//        requestFactory.setHttpClient(httpClient);
//        return new RestTemplate(requestFactory);
//    }
    @Bean
    public ClientHttpRequestFactory createClientHttpRequestFactory() {
//        HttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(this.maxTotalConnect)
//                .setMaxConnPerRoute(this.maxConnectPerRoute).build();
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
//                httpClient);


        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectionRequestTimeout(connectTimeout);
        clientHttpRequestFactory.setReadTimeout(readTimeout);
        return clientHttpRequestFactory;
    }

    @Bean
//    @LoadBalanced
    RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {

        RestTemplate template = new RestTemplate(clientHttpRequestFactory);

//        List<HttpMessageConverter<?>> converters = template.getMessageConverters();
//        converters.add(new ByteArrayHttpMessageConverter());

//        UTF8HttpMessageConverter utf8Converter = new UTF8HttpMessageConverter();
//        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
//        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        supportedMediaTypes.add(MediaType.TEXT_HTML);
//        utf8Converter.setSupportedMediaTypes(supportedMediaTypes);
//        converters.add(utf8Converter);

        //重新设置StringHttpMessageConverter字符集为UTF-8，解决中文乱码问题
//        HttpMessageConverter<?> converterTarget = null;
//        for (HttpMessageConverter<?> item : converterList) {
//            if (StringHttpMessageConverter.class == item.getClass()) {
//                converterTarget = item;
//                break;
//            }
//        }
//        if (null != converterTarget) {
//            converterList.remove(converterTarget);
//        }
//        converterList.add(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));


//        FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
//        fastJsonConverter.setSupportedMediaTypes(supportedMediaTypes);
//        fastJsonConverter.setFeatures(JsonUtils.features);
//        converters.add(fastJsonConverter);

//        template.setMessageConverters(converters);
        return template;
    }
}


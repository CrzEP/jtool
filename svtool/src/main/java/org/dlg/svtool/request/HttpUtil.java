package org.dlg.svtool.request;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * http请求工具
 *
 * @author lingui
 */
@Slf4j
public class HttpUtil {

    /**
     * 请求配置
     */
    private static final RequestConfig REQUEST_CONFIG;

    static {
        REQUEST_CONFIG = RequestConfig.custom()
                // 客户端和服务器建立连接的timeout
                .setConnectTimeout(1000 * 60)
                // 指从连接池获取连接的timeout
                .setConnectionRequestTimeout(6000)
                // 客户端从服务器读取数据的timeout
                .setSocketTimeout(1000 * 60 * 3)
                .build();
    }

    /**
     * 发送get 请求
     *
     * @param url 请求地址
     * @return 响应
     */
    public static String get(String url) {
        log.debug("请求地址：" + url);
        // 创建Http Get请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(REQUEST_CONFIG);
        return request(httpGet);
    }

    /**
     * 发送post 请求
     *
     * @param url 请求地址
     * @return 响应
     */
    private static String post(String url, String body) {
        log.debug("请求地址：" + url);
        log.debug("请求参数：" + body);
        // 创建Http Post请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(REQUEST_CONFIG);
        // 创建请求内容
        StringEntity entity = new StringEntity(body, ContentType.APPLICATION_JSON);
        httpPost.setEntity(entity);
        return request(httpPost);
    }

    /**
     * 请求
     *
     * @param request 请求
     * @return 消息
     */
    public static String request(HttpUriRequest request) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result;
        try {
            // 执行http请求
            response = httpClient.execute(request);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                log.debug("响应内容：" + result);
            } else {
                log.error("响应状态码：" + response.getStatusLine().getStatusCode());
                throw new RuntimeException("请求失败 状态码：" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("调用失败 url: " + e.getMessage());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                log.error("关闭流异常", e);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String result = get("http://www.baidu.com");
        System.out.println(result);
    }
}

package com.check.commom.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: Http请求工具类
 * @author: Mr.ZHAO
 * @cereate: 2018/7/30 14:37
 */
public class HttpClientUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * 发送http get请求
     *
     * @param url 请求路径
     * @return
     */
    public static String doGet(String url) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(url);
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            logger.error("发送http请求失败, 异常信息为: ", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                logger.error("发送http请求, 释放资源失败, 异常信息为: ", e);
            }
        }
        return resultString;
    }

    /**
     * 发送http post请求
     *
     * @param url   请求路径
     * @param param 请求参数
     * @return
     */
    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            logger.error("发送http请求失败, 异常信息为: ", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error("发送http请求, 释放资源失败, 异常信息为: ", e);
            }
        }
        return resultString;
    }

    /**
     * 发送http post json请求
     *
     * @param url  请求路径
     * @param json 请求参数
     * @return
     */
    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            logger.error("发送http请求失败, 异常信息为: ", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error("发送http请求, 释放资源失败, 异常信息为: ", e);
            }
        }
        return resultString;
    }

    /**
     * 上传文件
     *
     * @param name 文件参数名
     * @param url  上传路径
     * @param file 文件
     */
    public static String doPostFile(String name, String url, File file) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        CloseableHttpResponse httpResponse = null;
        String resultString = "";

        // 设置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000000).build();

        HttpPost httpPost = new HttpPost(url);

        httpPost.setConfig(requestConfig);

        // 封装文件对象
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

        multipartEntityBuilder.addBinaryBody(name, file);

        multipartEntityBuilder.addTextBody("comment", "this is comment");

        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);

        try {
            // 执行请求
            httpResponse = httpClient.execute(httpPost);

            HttpEntity responseEntity = httpResponse.getEntity();

            int statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
                StringBuffer buffer = new StringBuffer();
                String str = "";
                while (StringUtils.isNotBlank(str = reader.readLine())) {
                    buffer.append(str);
                }
                resultString = buffer.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (httpResponse != null) {
                    httpResponse.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }
}

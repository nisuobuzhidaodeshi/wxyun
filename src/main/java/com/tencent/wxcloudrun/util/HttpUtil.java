package com.tencent.wxcloudrun.util;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author cf
 * @date 2022/2/18 12:56
 */
public class HttpUtil {
    public static<T> T execute(HttpGet httpGet, Class<T> valueType){
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //接收返回值
        CloseableHttpResponse response = null;
        String content = "";
        try {
            //请求执行
            response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode()==200){
                content = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println("--------->" + content);
                //"session_key":"lYnV6U9agZWlDNzrFQdS+w==","openid":"oQIC95NvIRaZHmknEm6oCFaQvcLc"
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            System.out.println("getSession-ClientProtocolException");
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        T t = JacksonUtils.readValue(content, valueType);

        return t;
    }
}

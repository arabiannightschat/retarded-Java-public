package com.nights.retarded.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	// get
	public static String get(String url ,Map<String,String> params) {
		return HttpUtil.get(url ,"" ,params);
	}
	public static String get(String url ,String api ,Map<String,String> params) {

    	String charset = "UTF-8";
    	int timeout = 30;
		CloseableHttpClient httpclient = HttpClients.createDefault();
        Object retVal = "";
        try {
            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
            if (params != null) {
                for (Map.Entry<String, String> param : params.entrySet()) {
                    qparams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
            }
            String paramstr = URLEncodedUtils.format(qparams, charset);
            if(paramstr!=null && paramstr.trim().length()>0){
            	api = api + "?" + paramstr;
            }
            HttpGet httpget = new HttpGet(url+api);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout * 1000).build();
        	httpget.setConfig(requestConfig); 
            HttpResponse resp = httpclient.execute(httpget);
            retVal = EntityUtils.toString(resp.getEntity(), charset);
            return retVal.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
        	try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}

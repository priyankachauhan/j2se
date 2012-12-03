package com.pugwoo.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * http get數據
 * 2012-12-3 下午03:23:35
 */
public class Get {

	/**
	 * 從url獲取數據
	 * 1.如果encode不為null，則使用編碼，否則不使用編碼
	 * 2.如果代理proxy不為null，則使用proxy
	 * 3.如果out不為null，則寫入到文件中，此時返回null；如果out為null，則返回string
	 */
	public static String get(String url, String encode, HttpHost proxy,
			FileOutputStream out) throws ClientProtocolException, IOException {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		String content = null;

		try {
			if (proxy != null) {
				httpclient.getParams().setParameter(
						ConnRoutePNames.DEFAULT_PROXY, proxy);
			}

			HttpGet req = new HttpGet(url);
			HttpResponse rsp = httpclient.execute(req);

			// 獲得html頁面源碼
			HttpEntity entity = rsp.getEntity();
			if (entity != null) {
				if (out != null) {
					out.write(EntityUtils.toByteArray(entity));
				} else {
					content = EntityUtils.toString(entity);
				}
			}

			// 獲得請求返回狀態
			// rsp.getStatusLine()
			// 獲得http頭部
			// rsp.getAllHeaders()

		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return content;
	}

	@SuppressWarnings("unchecked")
	public static String post(String url, Map<String, String> params,
			String encode, HttpHost proxy, FileOutputStream out)
			throws ClientProtocolException, IOException {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		String content = null;

		try {
			if (proxy != null) {
				httpclient.getParams().setParameter(
						ConnRoutePNames.DEFAULT_PROXY, proxy);
			}

			HttpPost req = new HttpPost(url);

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			if (params != null) {
				Iterator iter = params.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
							.next();
					nvps.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
				}
			}
			
			req.setEntity(new UrlEncodedFormEntity(nvps));

			HttpResponse rsp = httpclient.execute(req);

			// 獲得html頁面源碼
			HttpEntity entity = rsp.getEntity();
			if (entity != null) {
				if (out != null) {
					out.write(EntityUtils.toByteArray(entity));
				} else {
					content = EntityUtils.toString(entity);
				}
			}

			// 獲得請求返回狀態
			// rsp.getStatusLine()
			// 獲得http頭部
			// rsp.getAllHeaders()

		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return content;
	}

	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		/**
		 * 代理示例
		 */
		HttpHost proxy = null;
		proxy = new HttpHost("proxy.tencent.com", 8080, "http");

		/**
		 * 演示直接獲取某個網頁
		 */
		String url = "http://www.google.com/";
		String content = get(url, null, proxy, null);
		System.out.println(content);
		System.out.println(content.getBytes().length + "字節");

		/**
		 * 演示下載文件（二進制）
		 */
		//		String img_url = "https://www.google.com/images/google_favicon_128.png";
		//		FileOutputStream out = new FileOutputStream("google_favicon_128.png");
		//		get(img_url, null, proxy, out);

		/**
		 * 演示post數據
		 */
		String post_url = "http://127.0.0.1:8080/userRegister/login";
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "nick");
		params.put("password", "123456");
		String post_content = post(post_url, params, null, null, null);
		System.out.println(post_content);
	}

}

package kr.ne.abc.template.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;

public class ApiHelper {
	
	/**
	 * getCallCorsUrl
	 * @param requestUrl
	 * @return
	 */
	public static String getCallCorsUrl(String requestUrl) {
		
		String strResult = "";
		
		try {
			strResult = httpGET(requestUrl);
			
			// String to JSON
			//strResult = new JSONObject(strResult).toString(2);
			
			// JSON to JSON.value
			//JSONObject joResponse = new JSONObject(strResult);
			//JSONObject joValue = JSONHelper.getJSONObject(joResponse, "value", new JSONObject());
			//strResult = joValue.optString("build-version","");
			
			//System.out.println("postCallCorsUrl strResult ============================ : " + strResult);
			
		} catch (JSONException e) {
			e.printStackTrace();
			
		}
		
//		getCallCorsUrl
//		try {
//			String requestUrl = "https://127.0.0.1:8887/version";
//			String strResult = ApiHelper.getCallCorsUrl(requestUrl);
//			System.out.println("strResult ============================== : " + strResult);
//			
//			JSONObject joResponse = new JSONObject(strResult);
//			JSONObject joValue = JSONHelper.getJSONObject(joResponse, "value", new JSONObject());
//			String buildVersion = joValue.optString("build-version","");
//			System.out.println("buildVersion : " + buildVersion);			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
		
		return strResult;
		
	}
	
	/**
	 * Calling ajax url with JSON request value
	 * Platform, javascript Cross-Origin Resource Sharing between SVMs API for communication without trouble
	 * Calling other domain json object request, response
	 * request : {"requestUrl" : "https://ds.nodehome.io/snshost","serviceID":"nodehome","parameters":"~~~~~"}
	 * response : {"result":"OK", "list" : "values...."}
	 * @param map
	 * @return
	 * @throws IOException
	 */
	public static String postCallCorsUrl(
			@RequestBody HashMap<String, Object> map) throws IOException {
		
		String requestUrl = "";		
		if(map!=null) { 
			requestUrl = (String)map.get("requestUrl");
			map.remove("requestUrl"); 
		}
		 
		String strResult = ""; 
		String JSONInput = new JSONObject(map).toString();
		//System.out.println("JSONInput =============================== : " + JSONInput);
		
		try { 
			strResult = postJSON(requestUrl, JSONInput);
			//System.out.println("postCallCorsUrl strResult ============================ : " + strResult);
		
		} catch(Exception e) { 
			e.printStackTrace();
		 
		}
		
		return strResult;
		
	}
	
	/**
	 * httpGET
	 * @param requestUrl
	 * @return
	 */
	public static String httpGET(
			String requestUrl) {
		
        //http client 생성
		HttpClient client = new HttpClient();

        //get 메서드와 URL 설정
	    GetMethod method = new GetMethod(requestUrl);
	    
	    // Provide custom retry handler is necessary
	    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
	    		new DefaultHttpMethodRetryHandler(3, false));

	    try {	    	
			// Execute the method.
			int statusCode = client.executeMethod(method);
			
			if (statusCode != HttpStatus.SC_OK) {
			    System.err.println("Method failed: " + method.getStatusLine());
			}
			
			// Read the response body.
			byte[] responseBody = method.getResponseBody();
			
			return new String(responseBody);
			
	    } catch (HttpException e) {
	      System.err.println("Fatal protocol violation: " + e.getMessage());
	      e.printStackTrace();
	      
	    } catch (IOException e) {
	      System.err.println("Fatal transport error: " + e.getMessage());
	      e.printStackTrace();
	      
	    } finally {
	      // Release the connection.
	      method.releaseConnection();
	      
	    }  
	    
		return "";
		
	}

	
	/**
	 * Cross-
	 * Platform, javascript Cross-Origin Resource Sharing between SVMs API for communication without trouble
	 * Calling other domain json object request, response
	 * request : {"requestUrl" : "https://ds.nodehome.io/snshost","serviceID":"nodehome","parameters":"~~~~~"}
	 * response : {"result":"OK", "list" : "values...."}
	 * @param requestUrl
	 * @param strQuery
	 * @return
	 * @throws IOException
	 */
	public static String postJSON(String requestUrl, String strQuery) throws IOException {
		String strResponse = null;

		byte[] bytResponse = null;
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(requestUrl);
		method.setRequestHeader("Content-type", "application/json");
		method.setRequestHeader("Connection", "close");				
		method.setRequestEntity( new StringRequestEntity( strQuery ) );
		try {
			int statusCode = client.executeMethod(method);
					
			if (statusCode == HttpStatus.SC_OK) {
				InputStream is = method.getResponseBodyAsStream();
				bytResponse = EtcUtils.readFile(is);
			} else {
				return "";
			}
			
		} catch (HttpException e1) {
			e1.printStackTrace();
			return "";
			
		} catch (IOException e2) {
			e2.printStackTrace();
			return "";
			
		} finally {
			if (method != null)
				method.releaseConnection();
			
		}
		
		if(bytResponse != null) {
			try {
				strResponse = new String(bytResponse,"utf-8").trim();
				//System.out.println("strResponse : " + strResponse);
				if(!strResponse.startsWith("{") ) {  
					strResponse.replace('\"', '\'');
					strResponse = "{ \"ec\":-1,\"value\":\"{}\",\"ref\":\"" + strResponse + "\" }";
				}
				strResponse = new JSONObject(strResponse).toString(2);
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return "";
				
			} catch (JSONException e) {
				e.printStackTrace();
				return "";
				
			}
		}
		
		return strResponse;
		
	}
	
	/**
	 * getClientIP
	 * @param request
	 * @return
	 */
	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR​");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		    ip = request.getHeader("Proxy-Client-IP");
		    //System.out.println("Proxy-Client-IP : " + ip);
		    
		}

		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		    ip = request.getHeader("WL-Proxy-Client-IP");
		    //System.out.println("WL-Proxy-Client-IP : " + ip);

		}

		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		    ip = request.getHeader("HTTP_CLIENT_IP");
		    //System.out.println("HTTP_CLIENT_IP : " + ip);

		}

		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		    //System.out.println("HTTP_X_FORWARDED_FOR : " + ip);

		}

		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		    ip = request.getRemoteAddr();
		    //System.out.println("getRemoteAddr : " + ip);

		}

	    return ip;
	    
	}	

}

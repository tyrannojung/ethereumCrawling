package kr.ne.abc.template.common.util;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.json.JSONArray;
import org.json.JSONException;
public class JSONHelper {

	/**
	 * check members
	 * ex) 	JSONHelper.isAllMember(joValue, new String[] {"ver","network","chain_id"});
	 * @param json
	 * @param keys
	 * @return true/false
	 */
	public static boolean isAllMember(JSONObject json, String[] keys)
	{
		if(json == null) return false;
		for(String key : keys)
		{
			if(!json.has(key))
				return false;
		}
		return true;
	}
	public static boolean isMember(JSONObject json, String key)
	{
		if(json == null) return false;
		return json.has(key);
	}
	public static JSONObject parse(String jsonContent)
	{
		if(jsonContent == null)	return null;
		try {
			JSONObject json = new JSONObject(jsonContent);
			return json;
		} catch(JSONException ignore) {
			;
		}
		return null;
	}

	public static JSONObject getJSONObject(JSONObject json, String key,JSONObject defaultValue)
	{
		if(json == null) return defaultValue;
		try {
			return json.getJSONObject(key);
		} catch(JSONException ignore) {
			return defaultValue;
		}
	}
	public static JSONArray getJSONArray(JSONObject json, String key,JSONArray defaultValue)
	{
		if(json == null) return defaultValue;
		try {
			return json.getJSONArray(key);
		} catch(JSONException ignore) {
			return defaultValue;
		}
	}
	public static String getString(JSONObject json, String key,String defaultValue)
	{
		if(json == null) return defaultValue;
		try {
			return json.getString(key);
		} catch(JSONException ignore) {
			return defaultValue;
		}
	}
	public static boolean getBoolean(JSONObject json, String key,boolean defaultValue)
	{
		if(json == null) return defaultValue;
		try {
			return json.getBoolean(key);
		} catch(JSONException ignore) {
			return defaultValue;
		}
	}
	public static long getLong(JSONObject json, String key,long defaultValue)
	{
		if(json == null) return defaultValue;
		try {
			return json.getLong(key);
		} catch(JSONException ignore) {
			return defaultValue;
		}
	}
	public static int getInt(JSONObject json, String key,int defaultValue)
	{
		if(json == null) return defaultValue;
		try {
			return json.getInt(key);
		} catch(JSONException ignore) {
			return defaultValue;
		}
	}
	public static BigDecimal getBigDecimal(JSONObject json, String key,BigDecimal defaultValue)
	{
		if(json == null) return defaultValue;
		try {
			return json.getBigDecimal(key);
		} catch(JSONException ignore) {
			return defaultValue;
		}
	}
	public static BigInteger getBigInteger(JSONObject json, String key,BigInteger defaultValue)
	{
		if(json == null) return defaultValue;
		try {
			return json.getBigInteger(key);
		} catch(JSONException ignore) {
			return defaultValue;
		}
	}
}

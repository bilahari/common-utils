package com.onmobile.apps.griff.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.log4j.Logger;

import com.onmobile.apps.griff.data.bean.ContentBean;
import com.onmobile.apps.griff.service.exceptions.DependencyException;
import com.onmobile.apps.griff.service.exceptions.InvalidInputException;

/**
 * @author pawan.kumar
 * @created Sep 10, 2013
 */
public class ServiceUtils implements ServiceConstants{
	private static Logger logger = Logger.getLogger(ServiceUtils.class);
	private static Logger cdrlogger = Logger.getLogger("BILLCDR");

	private static final String propsFileName = "service";
	static String TOMCAT_HOME = System.getProperty("catalina.home");
	private static final String RESOURCE_FILE_PATH = TOMCAT_HOME+"/conf/griff/resources";
	private static Object rlock = new Object();
	private static HashMap<String, ResourceBundle> resourceMap = new HashMap<String, ResourceBundle>();

	private static int _HTTP_CONNECTION_TIMEOUT = getValForKey(HTTP_CONNECTION_TIMEOUT, 20000);
	private static int _HTTP_SOCKET_TIMEOUT = getValForKey(HTTP_SOCKET_TIMEOUT, 20000);
	private static int _HTTP_MAX_TOTAL_CONNECTIONS = getValForKey(HTTP_MAX_TOTAL_CONNECTIONS, 500);
	private static int _HTTP_MAX_HOST_CONNECTIONS = getValForKey(HTTP_MAX_HOST_CONNECTIONS, 100);
	private static String _HTTP_PROXY_HOST = getValForKey(HTTP_PROXY_HOST, "host");
	private static int _HTTP_PROXY_PORT_INT = getValForKey(HTTP_PROXY_PORT_INT, -1);

	private static String _SUB_MGR_HTTP_PROXY_REQUIRED = getValForKey(SUB_MGR_HTTP_PROXY_REQUIRED, "false");
	private static String _SUB_MGR_HTTP_PROXY_HOST = getValForKey(SUB_MGR_HTTP_PROXY_HOST, "host");
	private static int _SUB_MGR_HTTP_PROXY_PORT_INT = getValForKey(SUB_MGR_HTTP_PROXY_PORT_INT, -1);

	private static String _ATLANTIS_HTTP_PROXY_REQUIRED = getValForKey(ATLANTIS_HTTP_PROXY_REQUIRED, "false");
	private static String _ATLANTIS_HTTP_PROXY_HOST = getValForKey(ATLANTIS_HTTP_PROXY_HOST, "host");
	private static int _ATLANTIS_HTTP_PROXY_PORT_INT = getValForKey(ATLANTIS_HTTP_PROXY_PORT_INT, -1);

	private static String _MESSAGING_HTTP_PROXY_REQUIRED = getValForKey(MESSAGING_HTTP_PROXY_REQUIRED, "false");
	private static String _MESSAGING_HTTP_PROXY_HOST = getValForKey(MESSAGING_HTTP_PROXY_HOST, "host");
	private static int _MESSAGING_HTTP_PROXY_PORT_INT = getValForKey(MESSAGING_HTTP_PROXY_PORT_INT, -1);

	private static String _YOUTUBE_HTTP_PROXY_REQUIRED = getValForKey(YOUTUBE_HTTP_PROXY_REQUIRED, "false");
	private static String _YOUTUBE_HTTP_PROXY_HOST = getValForKey(YOUTUBE_HTTP_PROXY_HOST, "host");
	private static int _YOUTUBE_HTTP_PROXY_PORT_INT = getValForKey(YOUTUBE_HTTP_PROXY_PORT_INT, -1);

	private static String _RBT_HTTP_PROXY_REQUIRED = getValForKey(RBT_HTTP_PROXY_REQUIRED, "false");
	private static String _RBT_HTTP_PROXY_HOST = getValForKey(RBT_HTTP_PROXY_HOST, "host");
	private static int _RBT_HTTP_PROXY_PORT_INT = getValForKey(RBT_HTTP_PROXY_PORT_INT, -1);

	private static String _BI_HTTP_PROXY_REQUIRED = getValForKey(BI_HTTP_PROXY_REQUIRED, "false");
	private static String _BI_HTTP_PROXY_HOST = getValForKey(BI_HTTP_PROXY_HOST, "host");
	private static int _BI_HTTP_PROXY_PORT_INT = getValForKey(BI_HTTP_PROXY_PORT_INT, -1);

	private static String _PACKS_HTTP_PROXY_REQUIRED = getValForKey(PACKS_HTTP_PROXY_REQUIRED, "false");
	private static String _PACKS_HTTP_PROXY_HOST = getValForKey(PACKS_HTTP_PROXY_HOST, "host");
	private static int _PACKS_HTTP_PROXY_PORT_INT = getValForKey(PACKS_HTTP_PROXY_PORT_INT, -1);

	private static String _MSEARCH_HTTP_PROXY_REQUIRED = getValForKey(MSEARCH_HTTP_PROXY_REQUIRED, "false");
	private static String _MSEARCH_HTTP_PROXY_HOST = getValForKey(MSEARCH_HTTP_PROXY_HOST, "host");
	private static int _MSEARCH_HTTP_PROXY_PORT_INT = getValForKey(MSEARCH_HTTP_PROXY_PORT_INT, -1);
	private static MultiThreadedHttpConnectionManager multiThreadedHttpConnectionManager = getMultiThreadedHttpConnectionManager();
	public static final String C_PROP_MSISDN_HEADER_NAMES_KEY = "MSISDN_HEADER_NAMES";	
	public static final String C_COMMON_LEVEL_ONE_SEPERATOR = ",";
	//public static final String C_PROP_FILE_NAME_DELIVERY_PARAMETERS = "delivery_parameters";

	public static final String C_PROP_IS_MSISDN_FROM_COOKIE_TRUE = "TRUE";
	public static final String C_PROP_MSISDN_COOKIE_NAME = "MSISDN_COOKIE_NAME";

	/*Device Handler specific Constants*/
	public static final String C_WURFL_URL=getValForKey("WURFL_SERVICE_URL", "");
	static char[] hexChar = {
		'0' , '1' , '2' , '3' ,
		'4' , '5' , '6' , '7' ,
		'8' , '9' , 'a' , 'b' ,
		'c' , 'd' , 'e' , 'f'};

	public static String getValForKey(String key, String defaultVal) {
		return getValForKey(propsFileName, key, defaultVal);
	}


	public static ResourceBundle getBundle(String file) throws IOException {
		logger.debug("Entered RESOURCE_FILE is "+file);
		ResourceBundle bundle= null;
		if(resourceMap.containsKey(file))
			return resourceMap.get(file);
		synchronized (rlock) {
			if(resourceMap.containsKey(file))
				return resourceMap.get(file);
			logger.debug("Checking for "+RESOURCE_FILE_PATH+"/"+file+".properties");
			bundle = new PropertyResourceBundle(new FileInputStream(RESOURCE_FILE_PATH+"/"+file+".properties"));
			resourceMap.put(file, bundle);
		}
		return bundle;
	}

	public static String getValForKey(String propFilename, String key, String defaultVal) {
		String sVal = null;
		try{
			ResourceBundle bundle = getBundle(propFilename);
			sVal = bundle.getString(key);
			if(null == sVal)
				sVal = defaultVal;
		}catch(Exception e){
			logger.error("Error while trying to read key-"+key+",from prop file-"+propFilename);
			sVal = defaultVal;
		}	
		logger.debug("Key:-"+key+", Value:-"+sVal);
		return sVal;
	}

	public static int getValForKey(String key, int defaultVal) {
		return getValForKey(propsFileName, key, defaultVal);
	}

	public static int getValForKey(String propFilename, String key, int defaultVal) {
		String sVal = null;
		int iVal = 0;
		try{
			ResourceBundle bundle = getBundle(propFilename);
			sVal = bundle.getString(key);
			if(null == sVal)
				iVal = defaultVal;
			else
				iVal = Integer.parseInt(sVal);

		}catch(Exception e){
			logger.error("Error while trying to read key-"+key+",from prop file-"+propFilename);
			iVal = defaultVal;
		}	
		logger.debug("Key:-"+key+", Value:-"+iVal);
		return iVal;
	}

	public static boolean isNullLength(String str){
		if((str == null) || (str.trim().length() < 1))
			return true;
		return false;
	}
	
	public static void copyParamsForURLReplacement(Map<String, String> sourceMap, Map<String, String> destMap) throws DependencyException{
		if(null == sourceMap){
			logger.info("Params map to be added to url params is null");
			return;
		}
		if(null == destMap){
			logger.info("destination map is null");
			throw new DependencyException("destination map is null");
		}

		try{
			Set<String> keySet = sourceMap.keySet();
			
			for(Iterator<String> it = keySet.iterator();it.hasNext();){
				String key = it.next();
				String value = sourceMap.get(key);
				if(value!=null){
					destMap.put("$"+key.toUpperCase()+"$", value);
				}
			}
		}catch(Exception e){
			throw new DependencyException(e.getMessage());
		}
	}

	public static GriffHttpResponse hitSubMgrURL(String url)
			throws DependencyException{
		HostConfiguration hostConfiguration = new HostConfiguration();
		if(!ServiceUtils.isNullLength(_SUB_MGR_HTTP_PROXY_REQUIRED)
				&&(_SUB_MGR_HTTP_PROXY_REQUIRED.equalsIgnoreCase("TRUE"))){
			String proxyHost = _SUB_MGR_HTTP_PROXY_HOST;
			if(isNullLength(proxyHost))
				proxyHost = _HTTP_PROXY_HOST;

			int proxyPort = _SUB_MGR_HTTP_PROXY_PORT_INT;
			if(proxyPort <= 0)
				proxyPort = _HTTP_PROXY_PORT_INT;

			logger.debug("setting proxy host : "+proxyHost);
			logger.debug("setting proxy port : "+proxyPort);
			hostConfiguration.setProxy(proxyHost, proxyPort);
		}
		return getHttpResponse(hostConfiguration, url);
	}

	public static GriffHttpResponse hitURL(String url, boolean proxyRequired, String proxyHost, int proxyPort)
			throws DependencyException{
		HostConfiguration hostConfiguration = new HostConfiguration();
		if(proxyRequired) {
			logger.debug("setting proxy host : "+proxyHost);
			logger.debug("setting proxy port : "+proxyPort);
			hostConfiguration.setProxy(proxyHost, proxyPort);
		}
		return getHttpResponse(hostConfiguration, url);
	}

	public static GriffHttpResponse hitAtlantisURL(String url)
			throws DependencyException{
		HostConfiguration hostConfiguration = new HostConfiguration();
		if(!ServiceUtils.isNullLength(_ATLANTIS_HTTP_PROXY_REQUIRED)
				&&(_ATLANTIS_HTTP_PROXY_REQUIRED.equalsIgnoreCase("TRUE"))){
			String proxyHost = _ATLANTIS_HTTP_PROXY_HOST;
			if(isNullLength(proxyHost))
				proxyHost = _HTTP_PROXY_HOST;

			int proxyPort = _ATLANTIS_HTTP_PROXY_PORT_INT;
			if(proxyPort <= 0)
				proxyPort = _HTTP_PROXY_PORT_INT;

			logger.debug("setting proxy host : "+proxyHost);
			logger.debug("setting proxy port : "+proxyPort);
			hostConfiguration.setProxy(proxyHost, proxyPort);
		}
		return getHttpResponse(hostConfiguration, url);
	}

	


	public static GriffHttpResponse hitMessagingURL(String url)
			throws DependencyException{
		HostConfiguration hostConfiguration = new HostConfiguration();
		if(!ServiceUtils.isNullLength(_MESSAGING_HTTP_PROXY_REQUIRED)
				&&(_MESSAGING_HTTP_PROXY_REQUIRED.equalsIgnoreCase("TRUE"))){
			String proxyHost = _MESSAGING_HTTP_PROXY_HOST;
			if(isNullLength(proxyHost))
				proxyHost = _HTTP_PROXY_HOST;

			int proxyPort = _MESSAGING_HTTP_PROXY_PORT_INT;
			if(proxyPort <= 0)
				proxyPort = _HTTP_PROXY_PORT_INT;

			logger.debug("setting proxy host : "+proxyHost);
			logger.debug("setting proxy port : "+proxyPort);
			hostConfiguration.setProxy(proxyHost, proxyPort);
		}
		return getHttpResponse(hostConfiguration, url);
	}

	public static GriffHttpResponse hitYoutubeURL(String url)
			throws DependencyException{
		HostConfiguration hostConfiguration = new HostConfiguration();
		if(!ServiceUtils.isNullLength(_YOUTUBE_HTTP_PROXY_REQUIRED)
				&&(_YOUTUBE_HTTP_PROXY_REQUIRED.equalsIgnoreCase("TRUE"))){
			String proxyHost = _YOUTUBE_HTTP_PROXY_HOST;
			if(isNullLength(proxyHost))
				proxyHost = _HTTP_PROXY_HOST;

			int proxyPort = _YOUTUBE_HTTP_PROXY_PORT_INT;
			if(proxyPort <= 0)
				proxyPort = _HTTP_PROXY_PORT_INT;
			System.out.println(proxyHost+" "+proxyPort);
			logger.debug("setting proxy host : "+proxyHost);
			logger.debug("setting proxy port : "+proxyPort);
			hostConfiguration.setProxy(proxyHost, proxyPort);
		}
		return getHttpResponse(hostConfiguration, url);
	}

	public static GriffHttpResponse hitBusinessIntelligenceURL(String url)
			throws DependencyException{
		HostConfiguration hostConfiguration = new HostConfiguration();
		if(!ServiceUtils.isNullLength(_BI_HTTP_PROXY_REQUIRED)
				&&(_BI_HTTP_PROXY_REQUIRED.equalsIgnoreCase("TRUE"))){
			String proxyHost = _BI_HTTP_PROXY_HOST;
			if(isNullLength(proxyHost))
				proxyHost = _HTTP_PROXY_HOST;

			int proxyPort = _BI_HTTP_PROXY_PORT_INT;
			if(proxyPort <= 0)
				proxyPort = _HTTP_PROXY_PORT_INT;
			System.out.println(proxyHost+" "+proxyPort);
			logger.debug("setting proxy host : "+proxyHost);
			logger.debug("setting proxy port : "+proxyPort);
			hostConfiguration.setProxy(proxyHost, proxyPort);
		}
		return getHttpResponse(hostConfiguration, url);
	}

	public static GriffHttpResponse hitPacksURL(String url)
			throws DependencyException{
		HostConfiguration hostConfiguration = new HostConfiguration();
		if(!ServiceUtils.isNullLength(_PACKS_HTTP_PROXY_REQUIRED)
				&&(_PACKS_HTTP_PROXY_REQUIRED.equalsIgnoreCase("TRUE"))){
			String proxyHost = _PACKS_HTTP_PROXY_HOST;
			if(isNullLength(proxyHost))
				proxyHost = _HTTP_PROXY_HOST;

			int proxyPort = _PACKS_HTTP_PROXY_PORT_INT;
			if(proxyPort <= 0)
				proxyPort = _HTTP_PROXY_PORT_INT;
			System.out.println(proxyHost+" "+proxyPort);
			logger.debug("setting proxy host : "+proxyHost);
			logger.debug("setting proxy port : "+proxyPort);
			hostConfiguration.setProxy(proxyHost, proxyPort);
		}
		return getHttpResponse(hostConfiguration, url);
	}


	public static GriffHttpResponse hitMsearchURL(String url)
			throws DependencyException{
		HostConfiguration hostConfiguration = new HostConfiguration();
		if(!ServiceUtils.isNullLength(_MSEARCH_HTTP_PROXY_REQUIRED)
				&&(_MSEARCH_HTTP_PROXY_REQUIRED.equalsIgnoreCase("TRUE"))){
			String proxyHost = _MSEARCH_HTTP_PROXY_HOST;
			if(isNullLength(proxyHost))
				proxyHost = _HTTP_PROXY_HOST;

			int proxyPort = _MSEARCH_HTTP_PROXY_PORT_INT;
			if(proxyPort <= 0)
				proxyPort = _HTTP_PROXY_PORT_INT;

			logger.debug("setting proxy host : "+proxyHost);
			logger.debug("setting proxy port : "+proxyPort);
			hostConfiguration.setProxy(proxyHost, proxyPort);
		}
		return getHttpResponse(hostConfiguration, url);
	}

	public static GriffHttpResponse getHttpResponse(HostConfiguration hostConfiguration, String url)
			throws DependencyException{
		return getHttpResponse(hostConfiguration, url, -1, -1, null);
	}

	public static GriffHttpResponse getHttpResponse(HostConfiguration hostConfiguration, String url,
			Map headers)throws DependencyException{
		return getHttpResponse(hostConfiguration, url, -1, -1, headers);
	}

	public static GriffHttpResponse getHttpResponse(HostConfiguration hostConfiguration, String url,
			int httpTimeout, int socketTimeout, Map headers)
					throws DependencyException{
		int timeTaken = -1;

		String subStatus = null;
		int httpStatusCode = -1;
		GetMethod method = null;
		logger.info("hitting URL : "+url);

		try
		{
			method = new GetMethod(url);
			if((headers != null) && (headers.size() > 0)){
				Iterator itr = headers.keySet().iterator();
				while(itr.hasNext()){
					String key = (String)itr.next();
					String value = (String)headers.get(key);
					logger.debug("Adding request header: "+key+"="+value);
					method.addRequestHeader(key, value);
				}
			}

			HttpClientParams clientParams = new HttpClientParams();
			if(httpTimeout > -1){
				logger.debug("Setting ConnectionManagerTimeout = "+httpTimeout);
				clientParams.setConnectionManagerTimeout(httpTimeout);
			}
			if(socketTimeout > -1){
				logger.debug("Setting SoTimeout = "+socketTimeout);
				clientParams.setSoTimeout(socketTimeout);
			}
			HttpClient httpClient = new HttpClient(clientParams, multiThreadedHttpConnectionManager);

			long startTime = System.currentTimeMillis();
			httpStatusCode = httpClient.executeMethod(hostConfiguration, method);
			long endTime = System.currentTimeMillis();
			logger.debug("httpStatusCode : "+httpStatusCode);
			if (httpStatusCode != HttpStatus.SC_OK && httpStatusCode < 600){
				String msg = "Method failed: " + method.getStatusLine();
				logger.error(msg);
				throw new HttpException(msg);
			}
			subStatus = method.getResponseBodyAsString();
			if(subStatus != null)
				subStatus = subStatus.trim();

			timeTaken = Integer.valueOf(""+(endTime - startTime));

			GriffHttpResponse responseObj = new GriffHttpResponse(httpStatusCode, subStatus, timeTaken);
			if(logger.isDebugEnabled()){
				logger.debug("Response for hitting url :"+url+" is :"+responseObj.toString());
			}
			return responseObj;
		}catch (HttpException hte){
			logger.error(hte.getMessage(), hte);
			//hte.printStackTrace();
			throw new DependencyException(hte.getMessage());
		}catch (IOException ioe){
			logger.error(ioe.getMessage(), ioe);
			//ioe.printStackTrace();
			throw new DependencyException(ioe.getMessage());
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			//e.printStackTrace();
			throw new DependencyException(e.getMessage());
		}finally{
			try{
				if(method != null)
					method.releaseConnection();
			}catch (Exception e) {
				logger.error("exception ocurred while releasing http pooled connection", e);
			}
		}
	}

	public static File getHttpResponse(HostConfiguration hostConfiguration, String url,
			int httpTimeout, int socketTimeout, Map headers, String filePrefix)
					throws DependencyException{
		int timeTaken = -1;

		String subStatus = null;
		int httpStatusCode = -1;
		GetMethod method = null;
		logger.info("hitting URL : "+url);

		try
		{
			method = new GetMethod(url);
			if((headers != null) && (headers.size() > 0)){
				Iterator itr = headers.keySet().iterator();
				while(itr.hasNext()){
					String key = (String)itr.next();
					String value = (String)headers.get(key);
					logger.debug("Adding request header: "+key+"="+value);
					method.addRequestHeader(key, value);
				}
			}

			HttpClientParams clientParams = new HttpClientParams();
			if(httpTimeout > -1){
				logger.debug("Setting ConnectionManagerTimeout = "+httpTimeout);
				clientParams.setConnectionManagerTimeout(httpTimeout);
			}
			if(socketTimeout > -1){
				logger.debug("Setting SoTimeout = "+socketTimeout);
				clientParams.setSoTimeout(socketTimeout);
			}
			HttpClient httpClient = new HttpClient(clientParams, multiThreadedHttpConnectionManager);

			long startTime = System.currentTimeMillis();
			httpStatusCode = httpClient.executeMethod(hostConfiguration, method);
			long endTime = System.currentTimeMillis();
			logger.debug("httpStatusCode : "+httpStatusCode);

			logger.info("Response Code : " + httpStatusCode);
			BufferedReader br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
			char []tmpBuf = new char[2048];
			int count;

			String pattern = "dd_MM_yyyy_HH_mm_ss_SSS";
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			String time = format.format(new Date());

			String basePath = getValForKey("QUARTZ_FEED_TEMP_FOLDER", "");
			File responseFile = new File(basePath+File.separator+filePrefix +"_"+ time + ".feed");

			if(!responseFile.exists())
				responseFile.createNewFile();
			FileWriter fw=null;
			try{
				fw = new FileWriter(responseFile);

				while((count=br.read(tmpBuf))!=-1){
					fw.write(tmpBuf, 0, count);
					fw.flush();
				}
			}finally{
				fw.close();
			}

			long diff = System.currentTimeMillis()-startTime;
			logger.info("Process Time:"+diff);
			if (httpStatusCode != HttpStatus.SC_OK){
				logger.error("Http Request not Accepted.");
				responseFile.delete();
				return null;
			}

			logger.info("Returning File " + responseFile.getAbsolutePath());

			return responseFile;
		}catch (HttpException hte){
			logger.error(hte.getMessage(), hte);
			//hte.printStackTrace();
			throw new DependencyException(hte.getMessage());
		}catch (IOException ioe){
			logger.error(ioe.getMessage(), ioe);
			//ioe.printStackTrace();
			throw new DependencyException(ioe.getMessage());
		}finally{
			try{
				if(method != null)
					method.releaseConnection();
			}catch (Exception e) {
				logger.error("exception ocurred while releasing http pooled connection", e);
			}
		}
	}

	public static GriffHttpResponse postHttpResponse(HostConfiguration hostConfiguration, String url, Map params)
			throws HttpException, IOException{
		return postHttpResponse(hostConfiguration, url, -1, -1, params, null);
	}

	public static GriffHttpResponse postHttpResponse(HostConfiguration hostConfiguration, String url,
			Map params, Map headers)
					throws HttpException, IOException{
		return postHttpResponse(hostConfiguration, url, -1, -1, params, headers);
	}

	public static GriffHttpResponse postHttpResponse(HostConfiguration hostConfiguration, String url,
			int httpTimeout, int socketTimeout, Map params, Map headers)
					throws HttpException, IOException{
		int timeTaken = -1;

		String subStatus = null;
		int httpStatusCode = -1;
		PostMethod method = null;
		logger.debug("hitting URL : "+url);

		try
		{
			method = new PostMethod(url);
			if((params != null) && (params.size() > 0)){
				Iterator itr = params.keySet().iterator();
				while(itr.hasNext()){
					String key = (String)itr.next();
					String value = (String)params.get(key);
					logger.debug("Adding request param: "+key+"="+value);
					method.addParameter(key, value);
				}
			}
			if((headers != null) && (headers.size() > 0)){
				Iterator itr = headers.keySet().iterator();
				while(itr.hasNext()){
					String key = (String)itr.next();
					String value = (String)headers.get(key);
					logger.debug("Adding request header: "+key+"="+value);
					method.addRequestHeader(key, value);
				}
			}

			HttpClientParams clientParams = new HttpClientParams();
			if(httpTimeout > -1){
				logger.debug("Setting ConnectionManagerTimeout = "+httpTimeout);
				clientParams.setConnectionManagerTimeout(httpTimeout);
			}
			if(socketTimeout > -1){
				logger.debug("Setting SoTimeout = "+socketTimeout);
				clientParams.setSoTimeout(socketTimeout);
			}
			HttpClient httpClient = new HttpClient(clientParams, multiThreadedHttpConnectionManager);

			long startTime = System.currentTimeMillis();
			httpStatusCode = httpClient.executeMethod(hostConfiguration, method);
			long endTime = System.currentTimeMillis();
			logger.debug("httpStatusCode : "+httpStatusCode);
			if (httpStatusCode != HttpStatus.SC_OK){
				String msg = "Method failed: " + method.getStatusLine();
				logger.error(msg);
				throw new HttpException(msg);
			}
			subStatus = method.getResponseBodyAsString();
			if(subStatus != null)
				subStatus = subStatus.trim();
			logger.debug("url response : "+subStatus);

			timeTaken = Integer.valueOf(""+(endTime - startTime));
			logger.debug("timeTaken = "+timeTaken+"ms");

			GriffHttpResponse responseObj = new GriffHttpResponse(httpStatusCode, subStatus, timeTaken);
			return responseObj;
		}catch (HttpException hte){
			logger.error(hte.getMessage(), hte);
			hte.printStackTrace();
			throw hte;
		}catch (IOException ioe){
			logger.error(ioe.getMessage(), ioe);
			ioe.printStackTrace();
			throw ioe;
		}finally{
			if(method != null)
				method.releaseConnection();
		}
	}

	private static MultiThreadedHttpConnectionManager getMultiThreadedHttpConnectionManager(){
		HttpConnectionManagerParams httpManagerParams = new HttpConnectionManagerParams();
		httpManagerParams.setConnectionTimeout(_HTTP_CONNECTION_TIMEOUT);
		httpManagerParams.setSoTimeout(_HTTP_SOCKET_TIMEOUT);
		httpManagerParams.setMaxTotalConnections(_HTTP_MAX_TOTAL_CONNECTIONS);
		httpManagerParams.setDefaultMaxConnectionsPerHost(_HTTP_MAX_HOST_CONNECTIONS);
		MultiThreadedHttpConnectionManager multiThreadedHttpConnectionManager = new MultiThreadedHttpConnectionManager();
		multiThreadedHttpConnectionManager.setParams(httpManagerParams);
		return multiThreadedHttpConnectionManager;
	}

	public static String getUuidWithoutHyphen(){
		String uuid = generateUUID();
		if (uuid == null){
			logger.error("uuid obtained is null.");
			long ts = System.currentTimeMillis();
			uuid = ""+ts;
		}else{
			uuid = uuid.replaceAll("-", "");
		}
		return uuid;
	}

	private static String generateUUID(){
		return UUID.randomUUID().toString();

	}

	public static String getEncodedString(String str)throws DependencyException{
		if(str == null)
			return "";
		else{
			String encStr = null;
			try{
				encStr = java.net.URLEncoder.encode(str,"UTF-8");
			}catch (UnsupportedEncodingException e) {
				logger.error("exception occurred while encoding "+str, e);
				throw new DependencyException(e.getMessage());
			}
			return encStr;
		}
	}

	public static String getDecodedString(String str)throws DependencyException{
		if(str == null)
			return "";
		else{
			String decStr = null;
			try{
				decStr = java.net.URLDecoder.decode(str,"UTF-8");
			}catch (UnsupportedEncodingException e) {
				logger.error("exception occurred while decoding "+str, e);
				throw new DependencyException(e.getMessage());
			}
			return decStr;
		}
	}

	public static ArrayList getArrayListFromString(String sInput, String sDelimiter){
		if((isNullLength(sInput))||(isNullLength(sDelimiter))){
			logger.debug("getArrayListFromString is called with sInput-"+sInput+",sDelimiter-"+sDelimiter);
			return null;
		}
		String sVals[] = sInput.split(",");
		String sTmp = null;
		ArrayList opList = null;
		if((sVals!=null) && (sVals.length > 0)){
			opList = new ArrayList();
			for(int i=0;i<sVals.length;i++){
				sTmp = sVals[i];
				if(!isNullLength(sTmp)){
					if(!opList.contains(sTmp.trim())){
						opList.add(sTmp.trim());
					}
				}
			}
		}
		return opList;
	}

	public static String getHexID(String str){		
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			try {
				md.update(str.getBytes());
				MessageDigest tc1 = (MessageDigest)md.clone();
				byte[] sDigest = tc1.digest();
				return toHexString(sDigest);
			} catch (CloneNotSupportedException cnse) {
				logger.error("Exception:couldnt make digest of partial content for :"+str +" Reason:"+cnse.getMessage());
			}
		}
		catch(NoSuchAlgorithmException nsae)
		{
			logger.error("Exception:couldnt make digest of partial content for :"+str+" Reason:"+nsae.getMessage());
		}

		return null;
	}

	public static String toHexString ( byte[] b ){
		StringBuffer sb = new StringBuffer( b.length * 2 );
		for ( int i=0; i<b.length; i++ ){
			// look up high nibble char
			sb.append( hexChar [( b[i] & 0xf0 ) >>> 4] );

			// look up low nibble char
			sb.append( hexChar [b[i] & 0x0f] );
		}
		return sb.toString();
	}

	public static void writeCDR(String str){
		cdrlogger.fatal(str);
	}

	public static String checkAndReplace(String str, String placeholder, String value){
		String retStr = str;
		if(str != null){
			if(str.indexOf(placeholder) > -1){
				logger.debug("replacing "+placeholder+" with "+value);
				String encodedValue=value;
				try {
					encodedValue = URLEncoder.encode(value, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.error(e.getMessage(),e);
				}
				retStr = str.replace(placeholder, encodedValue);
				logger.debug("url after replacing "+placeholder+" = "+retStr);
			}
		}
		return retStr;
	}

	public static String populateUrl(String url, Map map){
		logger.debug("before replace url = "+url);
		Set keyset = map.keySet();
		Iterator itr = keyset.iterator();
		while(itr.hasNext()){
			String key = (String)itr.next();
			String value = (String)map.get(key);
			url = checkAndReplace(url, key, value);
		}
		logger.debug("after replace url = "+url);
		return url;
	}

	public static boolean checkBooleanValue(String value){
		boolean flag=false;
		try{
			flag=Boolean.valueOf(value);
		}
		catch(Exception e){
			flag=false;
			logger.error("Error for high/low resolution content :"+e.getMessage());
		}
		logger.debug("Is High content resolution required : "+flag);		
		return flag;

	}


	/*checks if catalogId is valid*/
	public boolean isValidCatalogId(ContentBean contentBean) {
		boolean bIsValidCatalogId = false;
		java.sql.Timestamp now_TS = new java.sql.Timestamp(System
				.currentTimeMillis());
		if (contentBean != null) {
			java.sql.Timestamp exp_TS = (java.sql.Timestamp) contentBean.getEnddate();
			int iCompare = now_TS.compareTo(exp_TS);
			if ((iCompare == 0) || (iCompare < 0)) {
				bIsValidCatalogId = true;
			}
		}
		return bIsValidCatalogId;
	}

	public static void checkForNull(String varName, Object instance) throws InvalidInputException{
		if(null == instance){
			throw new InvalidInputException(varName+" cannot be null");
		}
	}

}

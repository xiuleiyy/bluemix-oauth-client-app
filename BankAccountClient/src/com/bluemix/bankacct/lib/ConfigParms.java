package com.bluemix.bankacct.lib;

import java.io.IOException;

import org.apache.http.message.BasicHeader;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

public class ConfigParms {

	public final static String SCOPE = "bluemix.scope";
	public final static String CLIENT_ID = "testClient";
	public final static String CLIENT_SECRET = "testClientSecret";
	public final static String SECURITYNAME = "AppUserRegistry";
	public final static String CLOUDHOST = "mybluemix.net";
	public final static String CLIENT_CONTEXTROOT = "http://BankAccountClient"
			+ "." + CLOUDHOST;
	public final static String RESOURCE_CONTEXTROOT = "http://BankAccountResource"
			+ "." + CLOUDHOST;

	public final static String[] securityEndpoints = getSecurityEndpoints();
	public final static String OAUTH20_TOKEN_URI = securityEndpoints[0];
	public final static String OAUTH20_AUTHORIZE_URI = securityEndpoints[1];
	public final static String OAUTH20_CHECK_TOKEN_URI = securityEndpoints[2];


	public final static String KEY_AUTHZ = "Authorization";
	public final static String KEY_BEARER = "Bearer";

	public final static String GET_ACCESS_TOKEN_REQ_PARAM = "grant_type=client_credentials";
	public final static String GET_ACCESS_TOKEN_REQ_CODE_PARAM = "grant_type=authorization_code";
	public final static String GET_ACCESS_TOKEN_REQ_REFRESH_PARM = "grant_type=refresh_token";
	public final static String RESPONSE_TYPE_PARM = "response_type=code";
	public final static String DEFAULT_ENCODE = "UTF-8";
	public final static BasicHeader DEFAULT_CONTENT_TYPE_HEADER = new BasicHeader(
			"Content-Type", "application/x-www-form-urlencoded");
	public final static BasicHeader JSON_CONTENT_TYPE_HEADER = new BasicHeader(
			"Content-Type", "application/json");
	public final static BasicHeader DEFAULT_ACCEPT_HEADER = new BasicHeader(
			"Accept",
			"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

	public final static String SEARCH_PAGE = CLIENT_CONTEXTROOT
			+ "/customer_serch.jsp";
	public final static String REDIRECT_URI = "redirect_uri=" + CLIENT_CONTEXTROOT + "/GetToken";

	public final static String GETCODEENDPOINT = OAUTH20_AUTHORIZE_URI + "?"
			+ RESPONSE_TYPE_PARM + "&" + "client_id=" + CLIENT_ID;
	public final static String GETCODEENDPOINT_SCOPES = OAUTH20_AUTHORIZE_URI
			+ "?" + RESPONSE_TYPE_PARM + "&" + "scope=" + SCOPE + "&"
			+ "client_id=" + CLIENT_ID;

	public final static String[] getSecurityEndpoints() {

		String[] securityEndpoints = new String[3];

		String vcap_services = System.getenv("VCAP_SERVICES");

		System.out.println("vcap_services is " + vcap_services);

		JSONObject vcap_service_obj = null;
		try {
			vcap_service_obj = JSONObject.parse(vcap_services);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray security_services = (JSONArray) vcap_service_obj
				.get(SECURITYNAME);

		System.out.println("security_services is "
				+ security_services.toString());

		JSONObject first_security = (JSONObject) security_services.get(0);
		JSONObject first_credential = (JSONObject) first_security
				.get("credentials");

		JSONObject authorize = (JSONObject) first_credential.get("authorize");
		String authorize_endpoint = (String) authorize.get("endpoint");
		System.out.println("authorize_endpoint is  " + authorize_endpoint);

		JSONObject getAccessToken = (JSONObject) first_credential
				.get("getAccessToken");
		String getAccessToken_endpoint = (String) getAccessToken
				.get("endpoint");
		System.out.println("getAccessToken_endpoint is  "
				+ getAccessToken_endpoint);

		JSONObject checkToken = (JSONObject) first_credential.get("checkToken");
		String checkToken_endpoint = (String) checkToken.get("endpoint");
		System.out.println("checkToken_endpoint is  " + checkToken_endpoint);

		securityEndpoints[0] = getAccessToken_endpoint;
		securityEndpoints[1] = authorize_endpoint;
		securityEndpoints[2] = checkToken_endpoint;

		return securityEndpoints;
	}

}

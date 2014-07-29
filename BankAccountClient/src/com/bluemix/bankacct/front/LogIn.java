package com.bluemix.bankacct.front;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.auth.Credentials;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.ByteArrayEntity;
//import org.apache.http.impl.auth.BasicScheme;
//import org.apache.http.impl.client.DefaultHttpClient;

import com.bluemix.bankacct.lib.ConfigParms;


/**
 * Servlet implementation class LogIn
 */
@WebServlet("/LogIn")
public class LogIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogIn() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();	
		String access_token = (String) session.getAttribute("access_token");
		System.out.print("access_token from session is " +session.getAttribute("access_token"));
		if(session.getAttribute("access_token")!=null&&checkToken(access_token)){
			
			// Request customer search with access_token
			response.sendRedirect(ConfigParms.SEARCH_PAGE);
			
		}else if(session.getAttribute("access_token")!=null&&!checkToken(access_token)){
			
			String refresh_token = (String) session.getAttribute("refresh_token");
			DefaultHttpClient httpClient = Utils.createHttpClient();
			String tokens[] = new String[2];
			
			String getAccessTokenURI = ConfigParms.GET_ACCESS_TOKEN_REQ_REFRESH_PARM + "&" + "refresh_token=" + refresh_token;
			
			HttpPost accessTokenReq = new HttpPost(
					ConfigParms.OAUTH20_TOKEN_URI);
			HttpEntity reqEntity = new ByteArrayEntity(
					getAccessTokenURI.getBytes(ConfigParms.DEFAULT_ENCODE));

			accessTokenReq.setHeader(ConfigParms.DEFAULT_CONTENT_TYPE_HEADER);
			accessTokenReq.setHeader(ConfigParms.DEFAULT_ACCEPT_HEADER);

			Credentials clientCredentials = new UsernamePasswordCredentials(
					ConfigParms.CLIENT_ID, ConfigParms.CLIENT_SECRET);
			accessTokenReq.addHeader(BasicScheme.authenticate(
					clientCredentials, ConfigParms.DEFAULT_ENCODE, false));

			accessTokenReq.setEntity(reqEntity);
			
			HttpResponse accessTokenResp = httpClient.execute(accessTokenReq);
						
			if (accessTokenResp.getStatusLine().toString().contains("200")){
				System.out.println("accessTokenResp is " + accessTokenResp);
				tokens = Utils.readTokensFromResponse(accessTokenResp);
				System.out.println("tokens[] is " + Arrays.toString(tokens));
				
				session.setAttribute("access_token", tokens[0]);
				System.out.println("session.access_token is "
						+ session.getAttribute("access_token"));
				session.setAttribute("refresh_token", tokens[1]);
				System.out.println("session.refresh_token is "
						+ session.getAttribute("refresh_token"));
				
				response.sendRedirect(ConfigParms.SEARCH_PAGE);
			}else{
				response.sendRedirect(ConfigParms.GETCODEENDPOINT);
			}			
			
		}else{
			String scope = request.getParameter("scope");						
			if(scope==null||scope==""){
				System.out.println("Front.LogIn No access_token and no scope, will be redirect to: " + ConfigParms.GETCODEENDPOINT);			
				response.sendRedirect(ConfigParms.GETCODEENDPOINT);	
			} else{				
				System.out.println("Front.LogIn No access_token with scope " + scope + ", will be redirect to: " + ConfigParms.GETCODEENDPOINT_SCOPES);			
				response.sendRedirect(ConfigParms.GETCODEENDPOINT_SCOPES);	
			}		
			
		}			
	}
	
public boolean checkToken(String access_token) throws IOException{
		
		DefaultHttpClient httpClient = Utils.createHttpClient();

		HttpResponse accessTokenResp = null;
		
		try {

			String CHECK_TOKEN_PARM = "token=" + access_token;

			HttpPost accessTokenReq = new HttpPost(
					ConfigParms.OAUTH20_CHECK_TOKEN_URI);
			HttpEntity reqEntity = new ByteArrayEntity(
					CHECK_TOKEN_PARM.getBytes(ConfigParms.DEFAULT_ENCODE));

			accessTokenReq.setHeader(ConfigParms.DEFAULT_CONTENT_TYPE_HEADER);
			accessTokenReq.setHeader(ConfigParms.DEFAULT_ACCEPT_HEADER);

			Credentials clientCredentials = new UsernamePasswordCredentials(
					ConfigParms.CLIENT_ID, ConfigParms.CLIENT_SECRET);
			accessTokenReq.addHeader(BasicScheme.authenticate(
					clientCredentials, ConfigParms.DEFAULT_ENCODE, false));

			accessTokenReq.setEntity(reqEntity);
			System.out.println("accessTokenReq.toString() is "
					+ accessTokenReq.toString());

			accessTokenResp = httpClient.execute(accessTokenReq);
			System.out.println("accessTokenResp is " + accessTokenResp);

			System.out.println("accessTokenResp.getStatusLine() is "
					+ accessTokenResp.getStatusLine());

		} catch (ClientProtocolException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		if (accessTokenResp.getStatusLine().toString().contains("200")){
			return true;
		}else {
			return false;
		}
		
		
		
	}

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		doPost(request, response);
	}


}

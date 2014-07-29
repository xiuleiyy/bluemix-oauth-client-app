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

import com.bluemix.bankacct.lib.ConfigParms;

/**
 * Servlet implementation class TestCode
 */
@WebServlet("/GetToken")
public class GetToken extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetToken() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String code = request.getParameter("code");
		HttpSession session = request.getSession();
		session.setAttribute("code", code);
		
		System.out.println("request.getParameter(code) is " + request.getParameter("code"));

		String tokens[] = getToken(code);

		session.setAttribute("access_token", tokens[0]);
		System.out.println("session.access_token is "
				+ session.getAttribute("access_token"));
		session.setAttribute("refresh_token", tokens[1]);
		System.out.println("session.refresh_token is "
				+ session.getAttribute("refresh_token"));

		response.sendRedirect(ConfigParms.SEARCH_PAGE);
	}

	public String[] getToken(String code) throws IOException {

		DefaultHttpClient httpClient = Utils.createHttpClient();
		String tokens[] = new String[2];

		try {

			String endpointToken = ConfigParms.GET_ACCESS_TOKEN_REQ_CODE_PARAM + "&"
					+ "code=" + code + "&" + ConfigParms.REDIRECT_URI;
			System.out
					.println("request token endpointToken is" + endpointToken);

			HttpPost accessTokenReq = new HttpPost(
					ConfigParms.OAUTH20_TOKEN_URI);
			HttpEntity reqEntity = new ByteArrayEntity(
					endpointToken.getBytes(ConfigParms.DEFAULT_ENCODE));

			accessTokenReq.setHeader(ConfigParms.DEFAULT_CONTENT_TYPE_HEADER);
			accessTokenReq.setHeader(ConfigParms.DEFAULT_ACCEPT_HEADER);

			Credentials clientCredentials = new UsernamePasswordCredentials(
					ConfigParms.CLIENT_ID, ConfigParms.CLIENT_SECRET);
			accessTokenReq.addHeader(BasicScheme.authenticate(
					clientCredentials, ConfigParms.DEFAULT_ENCODE, false));
			
			accessTokenReq.setEntity(reqEntity);
			System.out.println("accessTokenReq.toString() is "
					+ accessTokenReq.toString());

			HttpResponse accessTokenResp = httpClient.execute(accessTokenReq);
			System.out.println("accessTokenResp.getStatusLine().toString() is "
					+ accessTokenResp.getStatusLine().toString());
			System.out.println("accessTokenResp is " + accessTokenResp);
			tokens = Utils.readTokensFromResponse(accessTokenResp);
			System.out.println("tokens is " + Arrays.toString(tokens));

		} catch (ClientProtocolException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		return tokens;

	}

}

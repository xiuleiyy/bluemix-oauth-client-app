package com.bluemix.bankacct.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.bluemix.bankacct.lib.ConfigParms;
import net.sf.json.*;


@WebServlet("/CustomerSearch")
public class CustomerSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws java.io.IOException {

		HttpSession session = request.getSession();
		System.out.println("access token from session is "
				+ session.getAttribute("access_token"));

		response.setContentType("text/html");

		CustomerAcct tempCustomer;
		
		String customerName = request.getParameter("param").toUpperCase();

		System.out.println("customerName in customerSearch is" + customerName);
		tempCustomer = new CustomerAcct();

		String endpointRoot = ConfigParms.RESOURCE_CONTEXTROOT
				+ "/rest/Customers?customerName=";
		String tmpCustomerName = customerName.replace(" ", "%20");
		String endpoint = endpointRoot + tmpCustomerName + "&"
				+ "access_token=" + session.getAttribute("access_token");
		System.out.println("customer search endpoint with access token is "
				+ endpoint);
		DefaultHttpClient httpClient = Utils.createHttpClient();
		String jsonStr = null;
		try {

			HttpGet customerSearchReq = new HttpGet(endpoint);

			HttpResponse customerSearchResp = httpClient
					.execute(customerSearchReq);
			System.out.println("customerSearchResp is " + customerSearchResp);
			System.out.println("customerSearchResp.getEntity() is " + customerSearchResp.getEntity());
			System.out.println("customerSearchResp.toString() is " + customerSearchResp.toString());
			
			System.out.println("customerSearchResp.getAllHeaders() is " + customerSearchResp.getAllHeaders());
			
			System.out.println("customerSearchResp.getStatusLine() is " + customerSearchResp.getStatusLine());

			jsonStr = Utils.getContentFromResponse(customerSearchResp);

			System.out.println("jsonStr from customerSearchResp is " + jsonStr);

		} catch (ClientProtocolException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		if (jsonStr != null) {

			JSONObject obj = JSONObject.fromObject(jsonStr);
			JSONObject obj2 = obj.getJSONObject("tempCustomer");
			tempCustomer.setCustomerAcct(obj2.getInt("customerAcct"));
			tempCustomer.setCustomerMoney(obj2.getDouble("customerMoney"));
			tempCustomer.setCustomerName(obj2.getString("customerName"));

			printResults(response.getWriter(), tempCustomer);

		} else {
			System.out.println("jsonStr is null, user need to authorize again");
			response.sendRedirect(ConfigParms.GETCODEENDPOINT_SCOPES);

		}

		

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws java.io.IOException {
		doPost(request, response);
	}

	private void printResults(PrintWriter out, CustomerAcct customerAcct) {
		String title = "Customer Search Results";

		out.println("<HTML><HEAD><link rel=\"stylesheet\" href=\"css/style.css\"><TITLE>");
		out.println(title);
		out.println("</TITLE></HEAD><body><div class = 'container'>");
		out.println("<H1 align=\"center\">" + title + "</H1>");
		out.println("<BR><BR><BR>");

		out.println("<TABLE align='center' width=600px>");
		out.println("<TBODY align = 'left'>");

		out.println("<TR>");
		out.println("<TH>Customer</TH>");
		out.println("<TH>Account Balance </TH>");
		out.println("</TR>");

		out.println("<TR>");
		out.println("<TD>" + customerAcct.getCustomerName() + "</TD>");
		out.println("<TD id='balance'>"
				+ NumberFormat.getCurrencyInstance().format(
						customerAcct.getCustomerMoney()) + "</TD>");
		out.println("</TR>");

		out.println("</TBODY>");
		out.println("</TABLE>");
	}

}
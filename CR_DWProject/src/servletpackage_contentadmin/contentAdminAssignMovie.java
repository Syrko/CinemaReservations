package servletpackage_contentadmin;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import cinemacomponents.*;
import databasepackage.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auxpackage.CookieManager;

/**
 * Servlet implementation class contentAdminAssignMovie
 */
@WebServlet("/contentAdminAssignMovie")
public class contentAdminAssignMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public contentAdminAssignMovie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = CookieManager.getCookies(request);
		if(cookies == null || !cookies[1].getValue().equals("contentAdmin")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println(
				"<!DOCTYPE html>" +
						"<html>" +
						"<head>" +
						"<title>Assign</title>" +
						"<h1>Assign Movie to Cinema</h1>" +
						"<style>"+
						"body{"+
						"  background-color: #1A1A1D;"+
						"  color: #EEF4ED;"+
						"  font-size:18px;"+
						"}"+
						"input, select{"+
						"  background-color: #950740;"+
						"  color: white;"+
						"  text-align: center;"+
						"  border: none;"+
						"  height: 30px;"+
						"}"+
						"form{margin-left:30px;}"+
						".Buttons:hover {background-color: #950740; cursor: pointer;}"+
						".Buttons{background-color: #C3073F; color: white; padding: 10px; font-size: 14px; border: none; margin-bottom: 80px; width:80px; height: 35px;}"+
						"select{border: none;}"+
						"h1{border: none; padding: 2%; color:white; background-color: #6F2232; min-height: 20px; font-size: 33px;}"+
						"</style>" +
						"</head>" +
						"<body style='margin-left:30px;'>" +


						"<form method='post' action='assigningMovie'>" +

						"  Assign this movie to cinema:<br>" +
						"  <br>" +
						"  <select name='Cinema'>");
						ArrayList<Cinema> cinemas = Database.getAllCinemas();
						for(Cinema cinema : cinemas) {
							out.println("<option value='" + cinema.getCinemaID() + "'>" + cinema.getCinemaID() + "</option>");
						}
						out.println(
								"</select>" +
								"  <br>" +
								"  <br>" +
								"  Choose a start date: <input type='date' name='date' id='start' min='2019-06-09'>" +
								"  <br><br>" +
								"  Movie will be playing for: <input type='number' name='periodMonths' min='0' max='11' value='0'> Months and" +
								"  <input type='number' name='periodDays' min='0' max='30' value='1'> Days" +
								"  <br>" +
								"  <br>" +
								"	<input type='hidden' name='filmid' value='" + request.getParameter("filmid") + "'>" +
								"  <input class='Buttons' type='reset' value='Clear'>" +
								"  <input class='Buttons' type='submit' value='Add' style='margin-left:10px;'>" +
								"</form>" +

								"<script>" +
								"  n =  new Date();" +
								"  y = n.getFullYear();" +
								"  m = n.getMonth() + 1;" +
								"  d = n.getDate();" +
								"  if(m < 10){" +
								"    m = '0' + m;" +
								"  }" +
								"  if(d < 10){" +
								"    d = '0' + d;" +
								"  }" +
								"  document.getElementById('start').defaultValue = y + '-' + m + '-' +  d;" +
								"</script>" +
								"<form style='position:fixed;left:5%;bottom:10%;width:10%;' method='post' action='LogoutServlet'>" +
								"  <input class='Buttons' type='submit' name='logout' value='Logout'>" +
								"</form>" +
								"</body>" +
								"</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

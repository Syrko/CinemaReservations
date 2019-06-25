package servletpackage_contentadmin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auxpackage.CookieManager;
import databasepackage.Database;

/**
 * Servlet implementation class addingMovie
 */
@WebServlet("/addingMovie")
public class addingMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public addingMovie() {
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
		String title = request.getParameter("filmTitle");
		String category = request.getParameter("filmCategory");
		String description = request.getParameter("filmDescription");
		Database.CreateFilm(title, category, description);

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(
				"<!DOCTYPE html>" +
						"<html>" +
						"<head>" +
						"  <title> Movie Added...</title>" +
						"<style>"+
						"body{"+
						"  background-color: #1A1A1D;"+
						"  color: #EEF4ED;"+
						"  font-size:18px;"+
						"}"+
						"input{"+
						"  background-color: #950740;"+
						"  color: white;"+
						"  text-align: center;"+
						"  border: none;"+
						"  height: 25px;"+
						"  margin-top: 5px;"+
						"  margin-bottom: 5px;"+
						"}"+
						".Buttons:hover {background-color: #950740; cursor: pointer;}"+
						".Buttons{background-color: #C3073F; color: white; padding: 10px; font-size: 14px; border: none; margin-bottom: 80px; width:70px; height: 35px;}"+
						"form{margin-left:30px;}"+
						"h1{border: none; padding: 2%; color:white; background-color: #6F2232; min-height: 20px; font-size: 33px;}"+
						"</style>"+
						"</head>" +
						"<body>" +
						"  <form method='post' action='ContentAdminServlet'> "+
						"     <h1> Movie Added Successfully! </h1>" +
						"    <input type='submit' value='OK' class='Buttons'>" +
						"  </form>" +
						"<form style='position:fixed;left:5%;bottom:10%;width:10%;' method='post' action='LogoutServlet'>" +
						"  <input type='submit' name='logout' value='Logout' class='Buttons'>" +
						"</form>" +
						"</body>" +
						"</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

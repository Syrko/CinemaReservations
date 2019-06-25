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

/**
 * Servlet implementation class contentAdminAddMovie
 */
@WebServlet("/contentAdminAddMovie")
public class contentAdminAddMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public contentAdminAddMovie() {
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
		cookies[0].getValue();

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println(
				"<!DOCTYPE html>" +
						"<html>" +
						"<head>" +
						"<title>Add Movie</title>" +
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
						"<h1>Add Movie</h1>" +
						"<form action='addingMovie' method='get'>" +
						"  Movie Title:<br>" +
						"  <input type='text' name='filmTitle' required><br>" +
						"  Movie Category:<br>" +
						"  <input type='text' name='filmCategory' required><br>" +
						"  Movie Description:<br>" +
						"  <input type='text' name='filmDescription' required>" +
						"  <br>" +
						"  <br>" +
						"  <input type='reset' value='Clear' class='Buttons'>" +
						"  <input type='submit' value='Add' style='margin-left:75px;' class='Buttons'>" +
						"</form>" +
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

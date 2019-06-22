package servletpackage_admin;

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
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = CookieManager.getCookies(request);
		if(cookies == null || !cookies[1].getValue().equals("admin")) {
			System.out.println(cookies[1].getValue());
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(
				"<!DOCTYPE html>"+
						"<html>"+
						"<head>"+
						"<title>Admin Page</title>"+
						"<h1>Welcome, " + cookies[0].getValue() + "</h1>"+
						"<h2>You are in the Admin page</h2><br>"+
						"</head>"+
						"<body style='margin-left:20px;'>"+
						"<form method='post' action=ViewUserInformationServlet>"+
						"  <input type='text' name='username' placeholder='Input Username'>"+
						"  <input type='submit' value='View User'>"+
						"  <br><br>"+
						"</form>"+
						"<form method='get' action=CreateUserServlet>" +
						"  <input type='submit' value='Create New User'>"+
						"</form>" +
						"<form style='position:fixed;bottom:60%;width:10%;' method='post' action='LogoutServlet'>"+
						"  <input type='submit' name='logout' value='Logout'>"+
						"</form>"+
						"</body>"+
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

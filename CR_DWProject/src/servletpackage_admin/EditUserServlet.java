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
import databasepackage.Database;
import userspackage.*;

/**
 * Servlet implementation class EditUserServlet
 */
@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUserServlet() {
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
		
		User user = Database.getUser(request.getParameter("username"), request.getParameter("usertype"));
		
		out.println(
				"<!DOCTYPE html>"+
						"<html>"+
						"<head>"+
						"<title>Edit User</title>"+
						"<h1>Edit an existing User</h1>"+
						"</head>"+
						"<body style='margin-left:20px;'>"+
						"<div style='width:250px;'>"+
						"<form method='post' action='editingUser' >"+
						"  <br><br>"+
						"  Name: <input type='text' value='"+user.getName()+"' name='name' style='width:100%;' required>"+
						"  <br><br>"+
						"  Username: <input type='text' value='"+user.getUsername()+"' name='username' style='width:100%;' required>"+
						"  <br><br>"+
						"  Password: <input type='password' value='"+user.getPassword()+"' name='password' style='width:100%;' required>"+
						"<input type='hidden' name='usertype' value='"+ request.getParameter("usertype") +"'>" +
						"<input type='hidden' name='oldUsername' value='"+ user.getUsername() +"'>" +
						"  <br><br>"+
						"  <input type='submit' value='Save Changes'>"+
						"</form>"+
						"<form style='position:fixed;bottom:10%;width:10%;' method='post' action='LogoutServlet'>"+
						"  <input type='submit' name='logout' value='Logout'>"+
						"</form>"+
						"</body>"+
						"</html>"
				);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

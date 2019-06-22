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
 * Servlet implementation class CreateUserServlet
 */
@WebServlet("/CreateUserServlet")
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUserServlet() {
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
						"<title>Create User</title>"+
						"<h1>Create a new User!</h1>"+
						"</head>"+
						"<body style='margin-left:20px;'>"+
						"<div style='width:250px;'>"+
						"<form method='post' action=creatingUserServlet>"+
						" Choose User Type:"+
						"  <select name='usertype'>"+
						"    <option value='customer'>Customer</option>"+
						"    <option value='contentadmin'>Content Admin</option></select>"+
						"  <br><br>"+
						"  Name: <input type='text' placeholder='Input Name' name='name' style='width:100%;' required>"+
						"  <br><br>"+
						"  Username: <input type='text' placeholder='Input Username' name='username' style='width:100%;' required>"+
						"  <br><br>"+
						"  Password: <input type='password' placeholder='Input Password' name='password' style='width:100%;' required>"+
						"  <br><br>"+
						"  <input type='submit' value='Create User'>"+
						"</form>"+
						"</div>" +
						"<form style='position:fixed;bottom:10%;width:10%;' method='post' action='LogoutServlet'>"+
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

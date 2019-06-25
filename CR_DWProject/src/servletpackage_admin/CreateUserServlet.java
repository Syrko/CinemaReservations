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
						"  height: 25px;"+
						"  margin-top: 5px;"+
						"  margin-bottom: 5px;"+
						"}"+
						"textarea{"+
						"  background-color: #4E4E50;"+
						"  color:white;"+
						"  text-indent: 5px;"+
						"}"+
						".Buttons:hover {background-color: #950740; cursor: pointer;}"+
						".Buttons{background-color: #C3073F; color: white; padding: 10px; font-size: 14px; border: none; margin-bottom: 80px; width:auto; height: auto;}"+
						"form{margin-left:30px;}"+
						"h1{border: none; padding: 2%; color:white; background-color: #6F2232; min-height: 20px; font-size: 33px;}"+
						"</style>"+
						"</head>"+
						"<body style='margin-left:20px;'>"+
						"<h1>Create a new User!</h1>"+
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
						"  <input type='submit' value='Create User' class='Buttons'>"+
						"</form>"+
						"</div>" +
						"<form style='position:fixed;bottom:10%;width:10%;' method='post' action='LogoutServlet'>"+
						"  <input type='submit' name='logout' value='Logout' class='Buttons'>"+
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

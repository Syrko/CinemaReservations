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
						"<h1>Edit an existing User</h1>"+
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
						"  <input type='submit' value='Save Changes' class='Buttons'>"+
						"</form>"+
						"<form style='position:fixed;bottom:10%;width:10%;' method='post' action='LogoutServlet'>"+
						"  <input type='submit' name='logout' value='Logout' class='Buttons'>"+
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

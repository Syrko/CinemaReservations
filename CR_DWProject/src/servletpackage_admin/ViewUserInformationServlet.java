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
 * Servlet implementation class ViewUserInformationServlet
 */
@WebServlet("/ViewUserInformationServlet")
public class ViewUserInformationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewUserInformationServlet() {
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

		String username = request.getParameter("username");
		if(Database.userExists(username).equals("&no_user")) {
			out.println(
					"<!DOCTYPE html>"+
							"<html>"+
							"<head>"+
							"<title>User Details</title>"+
							"<style>"+
							"  body{"+
							"    background-color: #1A1A1D;"+
							"    color: #EEF4ED;"+
							"    font-size:18px;"+
							"  }"+
							"h1{border: none; padding: 2%; color:white; background-color: #6F2232; min-height: 20px; font-size: 33px;}"+
							".Buttons:hover {background-color: #950740; cursor: pointer;}"+
							".Buttons{background-color: #C3073F; color: white; padding: 10px; font-size: 14px; border: none; margin-bottom: 80px; width:auto; height: auto; margin-left:30px;}"+
							"</style>"+
							"</head>"+
							"<body>"+
							"<h1>No user '" + username + "' was found!" +
							"<form method='get' action='AdminServlet'>"+
							"<input type='submit' value='Try Again'>" +
							"</form>"+
							"<form style='position:fixed;bottom:35%;width:10%;' method='post' action='LogoutServlet'>"+
							"  <input type='submit' name='logout' value='Logout'>"+
							"</form>"+
							"</body>"+
							"</html>");
			return;
		}
		else if(Database.userExists(username).equals("customer")) {
			Customer customer = (Customer)(Database.getUser(username, "customer"));
			out.print(
					"<!DOCTYPE html>"+
							"<html>"+
							"<head>"+
							"<title>User Details</title>"+
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
							"<body>"+
							"<h1>View User Details</h1><br>"+
							"<form>"+
							"  <textarea rows='15' cols='50' readonly=true; margin=auto; style='resize:none;'>"+
							"Name: " + customer.getName()+"\n"+
							"Username: " + customer.getUsername()+"\n"+
							"Password: ");
							for(int i=0;i<customer.getPassword().length();i++) {
								out.print("*");
							}
							out.println(
									"\n"+
							"  </textarea>"+
							"</form>"+
							"<br>"+
							"<form method='post' action='EditUserServlet'>"+
							"  <input type='hidden' name='username' value='" + customer.getUsername() + "'>"+
							"  <input type='hidden' name='usertype' value='customer'>"+
							"  <input type='submit' value='Edit User' class='Buttons'>"+
							"</form>"+
							"<br>"+
							"<form method='post' action='DeleteUserServlet'>"+
							"  <input type='hidden' name='username' value='" + customer.getUsername() + "'>"+
							"  <input type='hidden' name='usertype' value='customer'>"+
							"  <input type='submit' value='Delete User' class='Buttons'>"+
							"</form>"+
							"<form style='position:fixed;bottom:10%;width:10%;' method='post' action='LogoutServlet'>"+
							"  <input type='submit' name='logout' value='Logout' class='Buttons'>"+
							"</form>"+
							"</body>"+
							"</html>");
							return;
		}
		else if(Database.userExists(username).equals("contentadmin")) {
			ContentAdmin contentAdmin = (ContentAdmin)(Database.getUser(username, "contentadmin"));
			out.print(
					"<!DOCTYPE html>"+
							"<html>"+
							"<head>"+
							"<title>User Details</title>"+
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
							"<body>"+
							"<h1>View User Details</h1><br>"+
							"<form>"+
							"  <textarea rows='15' cols='50' readonly=true; margin=auto; style='resize:none;'>"+
							"Name: " + contentAdmin.getName()+"\n"+
							"Username: " + contentAdmin.getUsername()+"\n"+
							"Password: ");
							for(int i=0;i<contentAdmin.getPassword().length();i++) {
								out.print("*");
							}
							out.println(
									"\n"+
							"  </textarea>"+
							"</form>"+
							"<br>"+
							"<form method='post' action='EditUserServlet'>"+
							"  <input type='hidden' name='username' value='" + contentAdmin.getUsername() + "'>"+
							"  <input type='hidden' name='usertype' value='contentadmin'>"+
							"  <input type='submit' value='Edit User' class='Buttons'>"+
							"</form>"+
							"<br>"+
							"<form method='post' action='DeleteUserServlet'>"+
							"  <input type='hidden' name='username' value='" + contentAdmin.getUsername() + "'>"+
							"  <input type='hidden' name='usertype' value='contentadmin'>"+
							"  <input type='submit' value='Delete User' class='Buttons'>"+
							"</form>"+
							"<form style='position:fixed;bottom:35%;width:10%;' method='post' action='LogoutServlet'>"+
							"  <input type='submit' name='logout' value='Logout' class='Buttons'>"+
							"</form>"+
							"</body>"+
							"</html>");
							return;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

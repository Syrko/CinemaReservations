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
 * Servlet implementation class creatingUserServlet
 */
@WebServlet("/creatingUserServlet")
public class creatingUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public creatingUserServlet() {
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
		boolean creationFlag = false;
		if(request.getParameter("usertype").equals("customer")) {
			creationFlag = Database.CreateUser(new Customer(request.getParameter("name"), request.getParameter("username"), request.getParameter("password")), "customer");
		}
		else if(request.getParameter("usertype").equals("contentadmin")) {
			creationFlag = Database.CreateUser(new Customer(request.getParameter("name"), request.getParameter("username"), request.getParameter("password")), "contentadmin");
		}
		if(creationFlag)
			out.println(
				"<!DOCTYPE html>" +
						"<html>" +
						"<head>" +
						"  <title> User Created...</title>" +
						"<style>"+
						"body{"+
						"  background-color: #1A1A1D;"+
						"  color: #EEF4ED;"+
						"  font-size:18px;"+
						"}"+
						".Buttons:hover {background-color: #950740; cursor: pointer;}"+
						".Buttons{background-color: #C3073F; color: white; padding: 10px; font-size: 14px; border: none; margin-bottom: 80px; margin-left:20px; width:auto; height: auto;}"+
						"h1{border: none; padding: 2%; color:white; background-color: #6F2232; min-height: 20px; font-size: 33px;}"+
						"</style>"+
						"</head>" +
						"<body>" +
						"  <form method='post' action='AdminServlet'> "+
						"     <h1> User Created Successfully! </h1>" +
						"    <input type='submit' value='OK' class='Buttons'>" +
						"  </form>" +
						"<form style='position:fixed;bottom:10%;width:10%;' method='post' action='LogoutServlet'>" +
						"  <input type='submit' name='logout' value='Logout' class='Buttons'>" +
						"</form>" +
						"</body>" +
						"</html>");
		else
			out.println(
					"<!DOCTYPE html>" +
							"<html>" +
							"<head>" +
							"  <title> User Created...</title>" +
							"<style>"+
							"body{"+
							"  background-color: #1A1A1D;"+
							"  color: #EEF4ED;"+
							"  font-size:18px;"+
							"}"+
							".Buttons:hover {background-color: #950740; cursor: pointer;}"+
							".Buttons{background-color: #C3073F; color: white; padding: 10px; font-size: 14px; border: none; margin-bottom: 80px; margin-left:20px; width:auto; height: auto;}"+
							"h1{border: none; padding: 2%; color:white; background-color: #6F2232; min-height: 20px; font-size: 33px;}"+
							"</style>"+
							"</head>" +
							"<body>" +
							"  <form method='post' action='AdminServlet'> "+
							"     <h1> User was not created! </h1>" +
							"    <input type='submit' value='OK' class='Buttons'>" +
							"  </form>" +
							"<form style='position:fixed;bottom:10%;width:10%;' method='post' action='LogoutServlet'>" +
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

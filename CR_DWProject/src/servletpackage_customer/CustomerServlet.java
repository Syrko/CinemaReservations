package servletpackage_customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auxpackage.CookieManager;
import cinemacomponents.Film;
import databasepackage.Database;

/**
 * Servlet implementation class CustomerServlet
 */
@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = CookieManager.getCookies(request);
		if(cookies == null || !cookies[1].getValue().equals("customer")) {
			System.out.println(cookies[1].getValue());
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.print(
				"<!DOCTYPE html>"+
						"<html>"+
						"<head>"+
						"<title>Customer</title>"+
						"</head>"+
						"<body style='margin-left:20px;'>"+
						"<h1>Welcome, " + cookies[0].getValue() + "</h1>"+
						"<h2>Click here to view your reservations:</h2>"+
						"<form method='post' action='ViewReservations'>"+
						"  <input type='submit' value='My Reservations'>"+
						"</form>"+
						"<br>"+
						"<h2>Choose the movie you want to make a reservation for:</h2>"+
						"<form method='get' action='MakeReservation'>"+
						"<select name='movies'>");
		ArrayList<Film> films = Database.getAllFilms();
		for(Film film : films) {
			out.println("<option value='" + film.getFilmID() + "'>" + film.getFilmTitle() + "</option>");
		}
		out.println(	
						"</select>"+
						"<input type='submit' value='Make Reservation' style='margin-left:20px;'>"+
						"</form>"+
						"<form style='margin-top:100px;width:10%;' method='post' action='LogoutServlet'>"+
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

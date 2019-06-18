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

import java.time.LocalDate;
import cinemacomponents.*;
import databasepackage.Database;

/**
 * Servlet implementation class assigningMovie
 */
@WebServlet("/assigningMovie")
public class assigningMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public assigningMovie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = CookieManager.getCookies(request);
		if(cookies == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		Film film = Database.getFilm(request.getParameter("filmid"));
		Cinema cinema = Database.getCinema(request.getParameter("Cinema"));
		LocalDate startDate = LocalDate.parse(request.getParameter("date"));
		LocalDate endDate = startDate.plusDays(Integer.parseInt(request.getParameter("periodDays"))).plusMonths(Integer.parseInt(request.getParameter("periodMonths")));
		Integer numberOfReservations = 0;
		boolean isAvailable = true;
		Database.CreateProvoli(film, cinema, startDate, endDate, numberOfReservations, isAvailable);
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(
				"<!DOCTYPE html>" +
						"<html>" +
						"<head>" +
						"  <title> Movie Assigned...</title>" +
						"</head>" +
						"<body>" +
						"  <form method='post' action='ContentAdminServlet'> "+
						"     <h1> Movie Assigned Successfully! </h1>" +
						"    <input type='submit' value='OK'>" +
						"  </form>" +
						"<form style='position:fixed;left:5%;bottom:10%;width:10%;' method='post' action='LogoutServlet'>" +
						"  <input type='submit' name='logout' value='Logout'>" +
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

package servletpackage_contentadmin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auxpackage.*;
import databasepackage.*;
import cinemacomponents.Film;
/**
 * Servlet implementation class deleteMovieServlet
 */
@WebServlet("/deleteMovieServlet")
public class deleteMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteMovieServlet() {
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
		
		Film film = Database.getFilm(request.getParameter("movieID"));
		int result = Database.deleteFilm(film);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		if(result == 1) {
			out.println(
					"<!DOCTYPE html>" +
							"<html>" +
							"<head>" +
							"  <title> Movie Deletion...</title>" +
							"</head>" +
							"<body>" +
							"  <form method='post' action='ContentAdminServlet'> "+
							"     <h1> Movie was not deleted!<br> It still has provoles!</h1>" +
							"    <input type='submit' value='OK'>" +
							"  </form>" +
							"<form style='position:fixed;left:5%;bottom:10%;width:10%;' method='post' action='LogoutServlet'>" +
							"  <input type='submit' name='logout' value='Logout'>" +
							"</form>" +
							"</body>" +
							"</html>");
			return;
		}
		
		out.println(
				"<!DOCTYPE html>" +
						"<html>" +
						"<head>" +
						"  <title> Movie Deleted...</title>" +
						"</head>" +
						"<body>" +
						"  <form method='post' action='ContentAdminServlet'> "+
						"     <h1> Movie Deleted Successfully! </h1>" +
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

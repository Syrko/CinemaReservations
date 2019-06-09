package servletpackage;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;

import cinemacomponents.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import databasepackage.Database;

/**
 * Servlet implementation class ContentAdminServlet
 */
@WebServlet("/ContentAdminServlet")
public class ContentAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ContentAdminServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShowContentAdmBasic(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void ShowContentAdmBasic(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(
				"<!DOCTYPE html>" +
				"<html>" +
				"<head>" +
				"	<title>Content Admin</title>" +
				"	<h1>Content Admin</h1>" +
				"	<style>" +
				"	</style>" +
				"</head>" +
				"<body>" +
				" <form style='float: left' action='contentAdminSelectMovie' method='get'>" +
				"    <select name='movies'>");
		ArrayList<Film> films = Database.getAllFilms();
		for(Film film : films) {
			out.println("<option value='" + film.getFilmID() + "'>" + film.getFilmTitle() + "</option>");
		}
		out.println(	
				"	</select>" +
				"   <input type='submit' value='View Info'>" +
				"  </form>" +
				"<br><br><br>" +
				"	<form action='contentAdminAddMovie' method='post'>" +
				"		<input type='submit' value='Add Movie'>" +
				"	</form>" +
				"</body>" +
				"</html>");		
	}

}

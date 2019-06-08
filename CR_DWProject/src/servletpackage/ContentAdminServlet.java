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
		// TODO Auto-generated method stub
//		if(request.getAttribute("contentAdmin") == null) {
//			response.sendError(HttpServletResponse.SC_FORBIDDEN);
//			return;
//		}
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
				"	<link rel='stylesheet' type='text/css' href='contentAdmin.css'>" +
				"</head>" +
				"<body style='background-color: #1A1A1D;'>" +
				"	<h1 class='pageTitle'>Content Admin Page</h1>" +
			
				"<div class='divmovielist'>" +
				"  <form class='movielistForm'>" +
				"    <textarea rows='15' cols='50' class='movielist' id='movielisting' readonly=true; margin=auto;>" +
				"    </textarea>" +
				"  </form>" +
				"  <button class='assignButton'>Assign</button>" +
				"</div>" +

				"<div class='divdropdown'>" +
				"<p id='selectText' class='dropdownText'>Select a Movie to view</p>" +
				"<div class='dropdown'>" +
				"  <button class='dropbtn'>Dropdown</button>" +
				"<div class='dropdown-content' style=\"overflow-y:auto; max-height:400%;\">");
		ArrayList<Film> films = Database.getAllFilms();
		for(Film film : films) {
			out.println("<a href='#' onclick='${pageContext.request.contextPath}/contentAdminSelectMovie'>" + film.getFilmTitle() +	"</a>");
		}
		out.println(	
				"	</div>" +
				"</div>" +
				"</div>" +
				"<div class=\"addMovie\">" +
				"  <h1 class=\"addText\">Click here to add a new movie</h1>" +
				"  <button class=\"addButton\"> Add Movie</button>" +
				"</div>" +
				// Javascript
				"<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js\"></script>" +
				"<script>"+
				"	function updateListing(){" +
				"		$.get(\"\", function(data, status){alert(data);});" +
				"</script>" +
				"</body>" +
				"</html>");
				
	}

}

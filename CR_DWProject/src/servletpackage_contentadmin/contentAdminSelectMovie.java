package servletpackage_contentadmin;

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
import cinemacomponents.*;
import databasepackage.Database;

/**
 * Servlet implementation class contentAdminSelectMovie
 */
@WebServlet("/contentAdminSelectMovie")
public class contentAdminSelectMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public contentAdminSelectMovie() {
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Film film = Database.getFilm(request.getParameter("movies"));
		ArrayList<Provoli> provoles = Database.getProvolesForFilm(film);
		out.println(
				"<!DOCTYPE html>" +
						"<html>" +
						"<head>" +
						"<title>" + film.getFilmTitle() + "</title>" +
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
						".Buttons{background-color: #C3073F; color: white; padding: 10px; font-size: 14px; border: none; margin-bottom: 50px; width:auto; height: auto;}"+
						"form{margin-left:30px;}"+
						"h1{border: none; padding: 2%; color:white; background-color: #6F2232; min-height: 20px; font-size: 33px;}"+
						"</style>"+
						"</head>" +
						"<body>" +
						"<h1>" + film.getFilmTitle() + "</h1>" +
						"<form method='get' action='contentAdminAssignMovie'>" +
						"  <textarea name='details' rows='8' cols='80' style='resize: none;' readonly= 'true';>" +
						"Film ID: " + film.getFilmID() + "\nTitle: " + film.getFilmTitle() + "\nCategory: " + film.getFilmCategory() + "\nDescription: " + film.getFilmDescription() + "\n\n");
						for(Provoli provoli : provoles) {
							out.println("ID: " + provoli.getProvoliID());
							out.println("Cinema: " + provoli.getProvoliCinema().getCinemaID());
							out.println("Start: " + provoli.getProvoliStartDate());
							out.println("End: " + provoli.getProvoliEndDate());
							out.println("Available: " + provoli.getProvoliIsAvailable());
							out.println("============================================");
						}

						out.println("	</textarea><br>" +
						"	<input type='hidden' name='filmid' value='" + film.getFilmID() + "'>" +
						"  <input type='submit' value='Create Provoli' class='Buttons'>" +
						"</form>" +
						"<br><br>" +
						"<form method='get' action='deleteProvoliServlet'>" +
						"<input type='submit' name='deleteProv' value='Delete Provoli' class='Buttons'>" +
						"<input type='hidden' name='movieID' value='" + film.getFilmID() + "'>" +
						"</form><br>" +
						"<form method='get' action='deleteMovieServlet'>" +
						"<input type='submit' name='delete' value='Delete Movie' class='Buttons'>" +
						"<input type='hidden' name='movieID' value='" + film.getFilmID() + "'>" +
						"</form>" +
						"<br><br><br>" +
						"<button onClick='back()' class='Buttons'>Return</button>" +
						"<form method='post' action='LogoutServlet'>" +
						"  <input type='submit' name='logout' value='Logout' class='Buttons'>" +
						"</form>" +
						"<script>" +
						"function back() {" +
						"  window.history.back();" +
						"}" +
						"</script>" +
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

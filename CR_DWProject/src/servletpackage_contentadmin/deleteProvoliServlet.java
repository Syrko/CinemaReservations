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
import databasepackage.*;

/**
 * Servlet implementation class deleteProvoliServlet
 */
@WebServlet("/deleteProvoliServlet")
public class deleteProvoliServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteProvoliServlet() {
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
		ArrayList<Provoli> provoles = Database.getProvolesForFilm(film);

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println(
				"<!DOCTYPE html>" +
						"<html>" +
						"<head>" +
						"<title>Delete Provoli</title>" +
						"  <style>"+
						"  body{"+
						"    background-color: #1A1A1D;"+
						"    color: #EEF4ED;"+
						"    font-size:18px;"+
						"  }"+
						"  input, select{"+
						"    background-color: #950740;"+
						"    color: white;"+
						"    text-align: center;"+
						"    border: none;"+
						"    height: 25px;"+
						"    margin-top: 5px;"+
						"    margin-bottom: 5px;"+
						"  }"+
						"  textarea{"+
						"    background-color: #4E4E50;"+
						"    color:white;"+
						"    text-indent: 5px;"+
						"  }"+
						"  .Buttons:hover {background-color: #950740; cursor: pointer;}"+
						"  .Buttons{background-color: #C3073F; color: white; padding: 10px; font-size: 14px; border: none; margin-left: 20px; width:auto; height: auto;}"+
						"  form{margin-left:30px;}"+
						"  h1{border: none; padding: 2%; color:white; background-color: #6F2232; min-height: 20px; font-size: 33px;}"+
						"  </style>"+
						"</head>" +
						"<body style='margin-left:20px;'>" +
						"<form method='get' action = 'deletingProvoli'>");
		if(provoles.isEmpty() || provoles == null) {
			out.println(
					"<h1>No provoles available for the film " + film.getFilmTitle() + "</h1>");
		}
		else {
			out.println("<h1>Choose a Provoli from Movie: "+ film.getFilmTitle() +" to Delete:</h1>");
			out.println("<select name='provoles'>");
			for(Provoli prov : provoles) {
				out.println("<option value='" + prov.getProvoliID() + "'>" + prov.getProvoliID() + " </option>");
			}
			out.println("</select>");
			out.println("<input type='submit' value='Delete' style='margin-left:5px;' class='Buttons'>");
		}
		out.println(
					"<input type='button' value='Return' onclick='goBack()' class='Buttons'>" +
				"</form>" +
				"<form style='position:fixed;bottom:10%;width:10%;' method='post' action='LogoutServlet'>" +
				"  <input type='submit' name='logout' value='Logout' class='Buttons'>" +
				"<script>" +
				"function goBack(){" +
				"  window.history.back();}" +
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

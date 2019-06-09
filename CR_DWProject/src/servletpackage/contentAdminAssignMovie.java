package servletpackage;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import cinemacomponents.*;
import databasepackage.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class contentAdminAssignMovie
 */
@WebServlet("/contentAdminAssignMovie")
public class contentAdminAssignMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public contentAdminAssignMovie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println(
				"<!DOCTYPE html>" +
						"<html>" +
						"<head>" +
						"<title>Assign</title>" +
						"<h1>Assign Movie to Cinema</h1>" +
						"</head>" +
						"<body style='margin-left:30px;'>" +


						"<form method='post' action='assigningMovie'>" +

						"  Assign this movie to cinema:<br>" +
						"  <br>" +
						"  <select name='Cinema'>");
						ArrayList<Cinema> cinemas = Database.getAllCinemas();
						for(Cinema cinema : cinemas) {
							out.println("<option value='" + cinema.getCinemaID() + "'>" + cinema.getCinemaID() + "</option>");
						}
						out.println(
								"</select>" +
								"  <br>" +
								"  <br>" +
								"  Choose a start date: <input type='date' name='date' id='start' min='2019-06-09'>" +
								"  <br><br>" +
								"  Movie will be playing for: <input type='number' name='periodMonths' min='0' max='11' value='0'> Months and" +
								"  <input type='number' name='periodDays' min='0' max='30' value='1'> Days" +
								"  <br>" +
								"  <br>" +
								"	<input type='hidden' name='filmid' value='" + request.getParameter("filmid") + "'>" +
								"  <input type='reset' value='Clear'>" +
								"  <input type='submit' value='Add' style='margin-left:10px;'>" +
								"</form>" +

								"<script>" +
								"  n =  new Date();" +
								"  y = n.getFullYear();" +
								"  m = n.getMonth() + 1;" +
								"  d = n.getDate();" +
								"  if(m < 10){" +
								"    m = '0' + m;" +
								"  }" +
								"  if(d < 10){" +
								"    d = '0' + d;" +
								"  }" +
								"  document.getElementById('start').defaultValue = y + '-' + m + '-' +  d;" +
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

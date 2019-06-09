package servletpackage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class contentAdminAddMovie
 */
@WebServlet("/contentAdminAddMovie")
public class contentAdminAddMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public contentAdminAddMovie() {
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
						"<title>Add Movie</title>" +
						"<h1>Add Movie</h1>" +
						"</head>" +
						"<body style='margin-left:30px;'>" +

						"<form action='addingMovie' method='get'>" +
						"  Movie Title:<br>" +
						"  <input type='text' name='filmTitle'><br>" +
						"  Movie Category:<br>" +
						"  <input type='text' name='filmCategory'><br>" +
						"  Movie Description:<br>" +
						"  <input type='text' name='filmDescription'>" +
						"  <br>" +
						"  <br>" +
						"  <input type='reset' value='Clear'>" +
						"  <input type='submit' value='Add' style='margin-left:75px;'>" +
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

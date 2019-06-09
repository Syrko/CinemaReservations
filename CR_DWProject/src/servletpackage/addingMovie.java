package servletpackage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import databasepackage.Database;

/**
 * Servlet implementation class addingMovie
 */
@WebServlet("/addingMovie")
public class addingMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addingMovie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("filmTitle");
		String category = request.getParameter("filmCategory");
		String description = request.getParameter("filmDescription");
		Database.CreateFilm(title, category, description);
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().append("<script type=\"text/javascript\">" + "alert('Movie Added to Database');" + "</script>");
		
		response.sendRedirect(request.getContextPath() + "/ContentAdminServlet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

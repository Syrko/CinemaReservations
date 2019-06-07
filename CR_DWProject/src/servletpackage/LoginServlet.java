package servletpackage;

import databasepackage.Database;
import auxpackage.*;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Pair<Boolean, String> ValidationResult = Database.ValidateCredentials(username, password);
		if(ValidationResult == null) {
			// TODO HTML error pop-up
		}
		else {
			if(ValidationResult.getVar1()) {
				switch(ValidationResult.getVar2()) {
					case "admin":
						// TODO Show admin html
						break;
					case "contentAdmin":
						ShowContentAdminHTML();
						break;
					case "customer":
						// TODO Show customer html
						break;
					default:
						//TODO Error pop up
				}
			}
			else {
				// TODO error for appropriate exception
				// exception info in ValidationResult.getVar2()
			}
		}
	}

	private HttpServletResponse ShowContentAdminHTML(HttpServletResponse response) {
		response.getWriter().append("");
		
		
		return response;
	}
}

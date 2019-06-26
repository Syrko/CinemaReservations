package servletpackage_customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auxpackage.CookieManager;
import cinemacomponents.Provoli;
import databasepackage.Database;
import userspackage.Customer;

/**
 * Servlet implementation class reserveSeats
 */
@WebServlet("/reserveSeats")
public class reserveSeats extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public reserveSeats() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = CookieManager.getCookies(request);
		if(cookies == null || !cookies[1].getValue().equals("customer")) {
			System.out.println(cookies[1].getValue());
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		Customer customer = (Customer)(Database.getUser(cookies[0].getValue(), cookies[1].getValue()));
		Provoli provoli = Database.getProvoli(request.getParameter("provoli"));
		LocalDate date = LocalDate.parse(request.getParameter("date"));
		int numberOfTickets = Integer.parseInt(request.getParameter("numberOfTickets"));

		if(date.isBefore(provoli.getProvoliStartDate_d()) || date.isAfter(provoli.getProvoliEndDate_d())) {
			out.println(
					"<!DOCTYPE html>"+
							"<html>"+
							"<head>"+
							"<title>Error</title>"+
							"<style>"+
							"body{"+
							"  background-color: #1A1A1D;"+
							"  color: #EEF4ED;"+
							"  font-size:18px;"+
							"}"+
							".Buttons:hover {background-color: #950740; cursor: pointer;}"+
							".Buttons{background-color: #C3073F; color: white; padding: 10px; font-size: 14px; border: none; margin-bottom: 80px; margin-left:20px; width:auto; height: auto;}"+
							"h1{border: none; padding: 2%; color:white; background-color: #6F2232; min-height: 20px; font-size: 33px;}"+
							"</style>"+
							"</head>"+
							"<body style='margin-left:20px;'>"+
							"<h1>Your Reservation was not completed!</h1><h2 style='color:red'> Please enter valid Provoli dates.</h2>"+
							"<form method='get' action='CustomerServlet'>"+
							"  <input type='submit' value='Return' class='Buttons'>"+
							"</form>"+
							"</body>"+
							"</html>"
					);
			return;
		}
		else if(!(provoli.getProvoliCinema().getCinemaNumberOfSeats()-Database.getReservedSeats(provoli)>numberOfTickets)) {
			out.println(
					"<!DOCTYPE html>"+
							"<html>"+
							"<head>"+
							"<title>Error</title>"+
							"<style>"+
							"body{"+
							"  background-color: #1A1A1D;"+
							"  color: #EEF4ED;"+
							"  font-size:18px;"+
							"}"+
							".Buttons:hover {background-color: #950740; cursor: pointer;}"+
							".Buttons{background-color: #C3073F; color: white; padding: 10px; font-size: 14px; border: none; margin-bottom: 80px; margin-left:20px; width:auto; height: auto;}"+
							"h1{border: none; padding: 2%; color:white; background-color: #6F2232; min-height: 20px; font-size: 33px;}"+
							"</style>"+
							"</head>"+
							"<body style='margin-left:20px;'>"+
							"<h1>Your Reservation was not completed.</h1><h2 style='color:red'>There are not enough available seats for your selected Provoli."
							+ "<br>Please try again...</h2>"+
							"<form method='get' action='CustomerServlet'>"+
							"  <input type='submit' value='Return' class='Buttons'>"+
							"</form>"+
							"</body>"+
							"</html>"
					);
			return;
		}
		else {
			Database.makeReservation(provoli.getProvoliID(), numberOfTickets, date, customer.getUsername());
			out.println(
					"<html>"+
							"<head>"+
							"<title>Success!</title>"+
							"<style>"+
							"body{"+
							"  background-color: #1A1A1D;"+
							"  color: #EEF4ED;"+
							"  font-size:18px;"+
							"}"+
							".Buttons:hover {background-color: #950740; cursor: pointer;}"+
							".Buttons{background-color: #C3073F; color: white; padding: 10px; font-size: 14px; border: none; margin-bottom: 80px; margin-left:20px; width:auto; height: auto;}"+
							"h1{border: none; padding: 2%; color:white; background-color: #6F2232; min-height: 20px; font-size: 33px;}"+
							"</style>"+
							"</head>"+
							"<body style='margin-left:20px;'>"+
							"<h1>Your Reservation was completed successfully!</h1>"+
							"<form method='get' action='CustomerServlet'>"+
							"  <input type='submit' value='OK!' class='Buttons'>"+
							"</form>"+
							"</body>"+
							"</html>"
				);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

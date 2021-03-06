package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.EmployeeDao;
import com.revature.models.Employee;
import com.revature.service.EmployeeService;

public class RequestHelper {
	// employeeservice
	private static EmployeeService eserv = new EmployeeService(new EmployeeDao());
	// object mapper (for frontend)
	private static ObjectMapper om = new ObjectMapper();

	/**
	 * What does this method do?
	 * 
	 * It extracts the parameters from a request (username and password) from the UI
	 * It will call the confirmLogin() method from the EmployeeService and see if a
	 * user with that username and password exists
	 * 
	 * Who will provide the method with the HttpRequest? The UI We need to build an
	 * html doc with a form that will send these prameters to the method
	 */
	public static void processLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// 1. Extract the parameters from the request (username & password)
		String username = request.getParameter("username");
		String password = request.getParameter("password"); // use fn + arrow key < or > to get to the beginning or end
															// of a line of code
		// use ctrl + arrow key to go from word to word

		// 2. call the confirm login(0 method from the employeeService and see what it
		// returns
		Employee e = eserv.confirmLogin(username, password);

		// 3. If the user exists, lets print their info to the screen
		if (e.getId() > 0) {

			// grab the session
			HttpSession session = request.getSession();

			// add the user to the session
			session.setAttribute("the-user", e);

			// alternatively you can redirect to another resource instead of printing out
			// dynamically

			// print out the user's data with the print writer
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");

			out.println("<h1>Welcome " + e.getFirstName() + "!</h1>");
			out.println("<h3>You have successfully logged in!</h3>");

			// you COULD print the object out as a JSON string
			String jsonString = om.writeValueAsString(e);
			out.println(jsonString);

		} else {
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("No user found, sorry");

			// Shout out to Gavin for figuring this out -- 204 doesn't return a response
			// body
//				response.setStatus(204); // 204 meants successful connection to the server, but no content found
		}
	}

	public static void processRegistration(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. Extract params
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		// 2. Construct an employee
		Employee e = new Employee(firstname, lastname, username, password);
		// 3. Call register() from service layer

		// TODO: Better logic to handle runtime exceptions (duplicate usernames)

		int id = eserv.register(e);
		// 4. check ID for > 0, if so great success!
		if (id > 0) {
			e.setId(id);
			HttpSession session = request.getSession();
			session.setAttribute("user", e);
			request.getRequestDispatcher("welcome.html").forward(request, response);
		} else {
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<h1>Failed to register</h1>");
			out.println("<a href='index.html'>Back</a>");
		}
	}

	public static void processEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Employee> employees = eserv.getAll();
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(om.writeValueAsString(employees));
	}
}

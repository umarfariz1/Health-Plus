package com.java;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeletePatientDetails extends HttpServlet {
	String PatientName;
	String PatientMobileNo;
	String PatientEmailId;
	String PatientDob;
	String PatientGender;

	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		res.setContentType("text/html");

		String mobileNumber = req.getParameter("mobileNumber");

		String query2 = "select * from patientdetails where patient_mobile_no=?  ";

		// creating object of other class
		NewPatientReg object = new NewPatientReg();
		res.setContentType("text/html");

		// Data base connection is started by calling the Dbconnection method from
		// NewPatinetReg class
		Connection con = null;
		try {
			con = object.DbConnection();

			PreparedStatement pre;

			pre = con.prepareStatement(query2);

			pre.setString(1, mobileNumber);

			ResultSet rst;

			rst = pre.executeQuery();

			// Initializing boot strap
			out.println("<link rel='stylesheet' href='css/bootstrap.css'> </link> ");

			// Html code for color in webpage
			out.println("<body style='Background-color:green; color:white; margin'>");
			out.println("<div style='width:500px; margin:auto; margin-top:150px' >");
			out.println("<h2 style='text-align:center; margin-bottom:50px;'>Delete Patinet Details</h2>");

			// collecting data from the data base and assigning into variable
			while (rst.next()) {
				PatientName = rst.getString(1);

				// printing the data in the web page in table format by using html

				out.println("<h4 style='text-align:center;'>Do you Want to delete " + PatientName + "'s details?</h4>");

				out.println("<table class='table table-hover;'style='text-align:center;' >");

				out.println("<td><a href='DeletePatientDetails2?mobileNumber=" + mobileNumber
						+ "'><button class='btn'>yes</button></a></td>");
				out.println("<td><a href='ViewPatientDeatils?mobileNumber=" + mobileNumber
						+ "'><button class='btn'>No</button></a></td>");

				out.println("<td><a href='UserInterface.html'><button class='btn'>Home</button></a></td></tr>");
				out.println("</div>");
			}
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}

		out.println("</table>");
		out.println("</form>");
		out.println("</body>");
	}

}

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

public class ViewHistory extends HttpServlet {

	String patientName;
	
	String dateOfAppointment;
	String timing;
	String doctorName;

	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		String mobileNumber = req.getParameter("PatientMobileNumber");
		req.setAttribute("PatientMobileNo", mobileNumber);

		boolean checkMobileNumber = false;
		NewPatientReg mobileNumberCheck = new NewPatientReg();
		// Initializing boot strap
		out.println("<link rel='stylesheet' href='css/bootstrap.css'> </link> ");

		// Html code for color in webpage
		out.println("<body style='Background-color:green; color:white; margin'>");
		out.println("<div style='width:1000px; margin:auto; margin-top:50px' >");
		out.println("<h2 style='text-align:center; margin-bottom:50px;'>Patinet History</h2>");
		try {
			checkMobileNumber = mobileNumberCheck.CheckMobileNumber(mobileNumber);

			System.out.println(checkMobileNumber);
			if (checkMobileNumber) {

				// Query for viewing Patient Details from Data Base where mobile number is given
				// from the user
				String query2 = "select date_of_appointment, doctor_name, timing from appointment_details where mobile_number=?  ";
				String query = "select patient_name from patientdetails where patient_mobile_no=?  ";

				// creating object of other class
				NewPatientReg object = new NewPatientReg();
				res.setContentType("text/html");

				// Data base connection is started by calling the Dbconnection method from
				// NewPatinetReg class
				Connection con = null;
				try {
					con = object.DbConnection();

					PreparedStatement pre;
					PreparedStatement pre2;

					pre = con.prepareStatement(query2);
					pre2 = con.prepareStatement(query);

					pre2.setString(1, mobileNumber);

					ResultSet rst1 = pre2.executeQuery();

					while (rst1.next()) {
						patientName = rst1.getString(1);
						System.out.println(patientName);
					}
					out.println("<table 'style='text-align:center;' >");
					out.println(
							"<tr><td style='text-align:left;'><h6>Name         		:</h6></td><td>  </td> <td>"+patientName+" Name</td></tr>");
					
					out.println(
							"<tr><td style='text-align:left;'><h6>Mobile Number		: </h6></td><td>  </td>  <td>"+mobileNumber+"</td></tr>");
					out.println("</table>");
					out.println("<table class='table table-hover;'style='text-align:center;' >");
					out.println(
							"<tr style='text-align:center;'><th>Doctor's Name</th><th>Date of Appointment</th><th>Timing</th></tr>");
					pre.setString(1, mobileNumber);
					ResultSet rst;
					rst = pre.executeQuery();
					// collecting data from the data base and assigning into variable
					while (rst.next()) {
						dateOfAppointment = rst.getString(1);

						doctorName = rst.getString(2);

						timing = rst.getString(3);

						// printing the data in the web page in table format by using html

						out.println("<tr><td>" + doctorName + "</td><td>"
								+ dateOfAppointment + "</td><td>" + timing + "</td></tr>");
					
					}
					out.println("</table>");
					out.println("<a href='viewHistory.html'><button class='btn'>Back</button></a>");
					out.println("</div>");
					
				} catch (ClassNotFoundException | SQLException e) {

					e.printStackTrace();
				}

			} else {
				res.setContentType("text/html");

				out.println(
						"<h2 style='text-align:center; margin-bottom:50px; color:red;' >Patinet Details not found!!!<br>Please Try agin with valid details</h2>");
				out.println("<div style='width:100px; margin:auto' >");
				out.println("<a href='ViewPatientDetails.html'><button class='btn'>Back</button></a>");
				out.println("</div>");
			}

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		out.println("</div>");

		out.println("</body>");
	}
}

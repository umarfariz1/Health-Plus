package com.java;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewDoctorDetails extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		NewPatientReg object = new NewPatientReg();
		PrintWriter out = res.getWriter();
		res.setContentType("text/html");

		out.println("<link rel='stylesheet' href='css/bootstrap.css'> </link> ");

		out.println("<body style='color:white; background-color:green; '>");

		String DoctorMobileNo = null;
		String DoctorSpeciality = null;
		String DoctorTiming = null;
		String DoctorFee = null;

		Connection con = null;
		try {

			con = object.DbConnection();

			String fetchDoctorDetails = "select  doctor_mobileno, specialist, fee, timing from doctordetails where doctor_name=?";
			
			PreparedStatement pre1 = con.prepareStatement(fetchDoctorDetails);
			
			String doctorName = req.getParameter("doctor");
			
			pre1.setString(1, doctorName);
			
			ResultSet rst1 = pre1.executeQuery();

			while (rst1.next()) {
				DoctorMobileNo = rst1.getString(1);
				DoctorSpeciality = rst1.getString(2);
				DoctorTiming = rst1.getString(4);
				DoctorFee = rst1.getString(3);
			}
			

			out.println("<div style='width:800px; margin:auto; margin-top:100px' >");
			
			out.println("<table class='table table-hover table-stripped;' style='text-align:center;'>");

			out.println("<h2 style='font:times new roman; text-align:center; margin-bottom:20px;'>Doctor's Details</h2>");

			out.println("<tr><td>Doctor's Name:</td> ");
			out.println("<td><h6>Dr." + doctorName + "</h6></td></tr>");

			out.println("<tr><td>Speciality:</td> ");
			out.println("<td><h6>" + DoctorSpeciality + "</h6></td></tr>");

			out.println("<tr><td>Doctor's Mobile Number:</td> ");
			out.println("<td><h6>" + DoctorMobileNo + "</h6></td></tr>");

			out.println("<tr><td>Availability:</td> ");
			out.println("<td><h6>" + DoctorTiming + "</h6></td></tr>");

			out.println("<tr><td>Consulting Fees:</td> ");

			out.println("<td><h6>" + DoctorFee + "</h6></td></tr>");
			out.println("</table>");

			out.println("<div style='width:200px; margin:auto;' >");
			out.println("<table class='table table-hover;'>");
			
			out.println("<tr><td><a href='UserInterface.html'><button class='btn'>Home</button></a></td>");
			out.println("<td><a href='viewDoctorDetails.html'><button class='btn'>back</button></a></td></tr>");
			out.println("</table>");
			out.println("</div>");

		}

		catch (Exception e) {

		}
		out.println("</div> ");
		out.println("</table>");

		out.println("</body>");

	}
}
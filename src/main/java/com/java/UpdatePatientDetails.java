package com.java;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdatePatientDetails extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		String patinetMobileNo = req.getParameter("mobileNumber");
		String patinetName = req.getParameter("PatientName1");
		System.out.println("Patinet Name" + patinetName);
		String patinetEmailId = req.getParameter("PatientEmailId");
		String patinetGender = req.getParameter("PatientGender");
		String patinetDob = req.getParameter("PatientDob");
		System.out.println("Mobile Number:" + patinetMobileNo);
		// Initializing boot strap
		out.println("<link rel='stylesheet' href='css/bootstrap.css'> </link> ");

		// Html code for color in web page
		out.println("<body style='Background-color:green; color:white; margin text-align:center;'>");
		out.println("<div style='margin-top:150px;' >");
		

		// creating object of other class
		NewPatientReg object = new NewPatientReg();
		res.setContentType("text/html");

		String query = "update patientdetails set patient_name=?, patient_email=?, patient_dob=?, patient_gender=? where  patient_mobile_no=?";

		Connection con = null;
		try {

			// Data base connection is started by calling the Dbconnection method from
			// NewPatinetReg class
			con = object.DbConnection();

			PreparedStatement pre = con.prepareStatement(query);
			pre.setString(1, patinetName);
			pre.setString(2, patinetEmailId);
			pre.setString(3, patinetDob);
			pre.setString(4, patinetGender);
			pre.setString(5, patinetMobileNo);

			int confirmation = pre.executeUpdate();
			if (confirmation > 0) {
				out.println("<h2 style='text-align:center;'>Updated sucessfully</h2>");
			} else {
				out.println("<h2 style='text-align:center;'>Not Updated</h2>");
			}
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		out.println("<div class='text-center' style='margin-top:30px;'>");
		out.println("<a href='UserInterface.html'><button class='btn btn-primary' >Home</button></a>");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");

	}

}

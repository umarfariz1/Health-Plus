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

public class UpdatePatinetDetailsCheck extends HttpServlet {
	String PatientName;
	String PatientMobileNo;
	String PatientEmailId;
	String PatientDob;
	String PatientGender;

	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		String patinetMobileNo = req.getParameter("mobileNumber");

		// Initializing boot strap
		out.println("<link rel='stylesheet' href='css/bootstrap.css'> </link> ");

		// Html code for color in webpage
		out.println("<body style='Background-color:green; color:white; margin'>");
		out.println("<div style='width:1200px; margin:auto; margin-top:150px' >");
		out.println("<h2 style='text-align:center; margin-bottom:50px;'>Patinet Details</h2>");

		// Query for viewing Patient Details from Data Base where mobile number is given
		// from the user
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

			pre.setString(1, patinetMobileNo);

			ResultSet rst;

			rst = pre.executeQuery();

			// collecting data from the data base and assigning into variable
			while (rst.next()) {
				PatientName = rst.getString(1);

				PatientMobileNo = rst.getString(2);

				PatientEmailId = rst.getString(3);
				PatientDob = rst.getString(4);
				PatientGender = rst.getString(5);

				// printing the data in the web page in table format by using html
				out.println("<form action='UpdatePatinetDetailsinDb?mobileNumber="+patinetMobileNo+"' method='post'>");
				out.println("<table class='table table-hover;'style='text-align:center;' >");
				
				System.out.println("Mobile before:"+patinetMobileNo);
				out.println(
						"<tr style='text-align:center;'><th>Name</th> <th>Mobile Number</th><th>Email ID</th><th>DOB</th><td>Gender</th></tr>");
				out.println("<tr><td>" + PatientName + "</td><td>" + PatientMobileNo + "</td><td>" + PatientEmailId
						+ "</td><td>" + PatientDob + "</td><td>" + PatientGender + "</td></tr>");
				out.println("<tr><td><input type='text' name='PatientName1' value=" + PatientName
						+ "  ></td><td><input type='number' name='PatientMobileNo' value=" + PatientMobileNo
						+ "></td><td><input type='email' name='PatientEmailId' value=" + PatientEmailId + ">"
						+ "</td><td><input type='date' name='PatientDob' value=" + PatientDob
						+ "></td><td><input type='text' name='PatientGender' value=" + PatientGender + "></td></tr>");

				out.println("<tr><td><button type='submit' class='btn'>Update</button></td>");
				out.println("<td><button type='reset' class='btn'>Reset</button></td>");
			
				out.println("<td><a href='UserInterface.html'><button type=''  class='btn'>Home</button></a></td></tr>");
				
				out.println("</div>");
				out.println("</table>");
				out.println("</form>");
			}
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}

	}

}

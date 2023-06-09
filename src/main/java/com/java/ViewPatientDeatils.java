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

public class ViewPatientDeatils extends HttpServlet {
	String PatientName;
	String PatientMobileNo;
	String PatientEmailId;
	String PatientDob;
	String PatientGender;

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
					out.println("<div style='width:1000px; margin:auto; margin-top:150px' >");
					out.println("<h2 style='text-align:center; margin-bottom:50px;'>Patinet Details</h2>");
		try {
		checkMobileNumber =	mobileNumberCheck.CheckMobileNumber(mobileNumber);
		
			
		
		
		System.out.println(checkMobileNumber);
		if(checkMobileNumber) {
		
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

			pre.setString(1, mobileNumber);

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
				out.println("<table class='table table-hover;'style='text-align:center;' >");
				out.println(
						"<tr style='text-align:center;'><th>Name</th> <th>Mobile Number</th><th>Email ID</th><th>DOB</th><td>Gender</th></tr>");
				out.println("<tr><td>" + PatientName + "</td><td>" + PatientMobileNo + "</td><td>" + PatientEmailId
						+ "</td><td>" + PatientDob + "</td><td>" + PatientGender + "</td></tr>");

				out.println("<tr><td><a href='UpdatePatinetDetails?mobileNumber="+PatientMobileNo+"'><button class='btn'>Edit</button></a></td>");

				out.println("<td><a href='DeletePatientDetails?mobileNumber=" + PatientMobileNo
						+ "'><button class='btn'>Delete</button></a></td>");

				out.println("<td><a href='UserInterface.html'><button class='btn'>Home</button></a></td></tr>");
				out.println("</div>");
				out.println("</table>");
			}
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		
		}else {
			res.setContentType("text/html");
			
			out.println("<h2 style='text-align:center; margin-bottom:50px; color:red;' >Patinet Details not found!!!<br>Please Try agin with valid details</h2>");
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

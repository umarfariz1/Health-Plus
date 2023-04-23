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
		String query2 = "select * from patientdetails where patient_mobile_no=?  ";
		NewPatientReg object = new NewPatientReg();
		res.setContentType("text/html");

//Data base connection is started
		Connection con = null;
		try {
			con = object.DbConnection();

			PreparedStatement pre;

			pre = con.prepareStatement(query2);

			pre.setString(1, mobileNumber);

			ResultSet rst;

			rst = pre.executeQuery();
			out.println("<link rel='stylesheet' href='css/bootstrap.css'> </link> ");
			out.println("<body style='Background-color:green; color:white; margin'>");
			out.println("<form style='width:1000px; margin:auto; margin-top:150px' >");
			out.println("<h2 style='text-align:center; margin-bottom:50px;'>Patinet Details</h2>");

			while (rst.next()) {
				PatientName = rst.getString(1);

				PatientMobileNo = rst.getString(2);
				PatientEmailId = rst.getString(3);
				PatientDob = rst.getString(4);
				PatientGender = rst.getString(5);
				out.println("<table class='table table-hover;'style='text-align:center;'>");
				out.println(
						"<tr style='text-align:center;'><th>Name</th> <th>Mobile Number</th><th>Email ID</th><th>DOB</th><td>Gender</th></tr>");
				out.println("<tr><td>" + PatientName + "</td><td>" + PatientMobileNo + "</td><td>" + PatientEmailId
						+ "</td><td>" + PatientDob + "</td><td>" + PatientGender + "</td></tr>");
				out.println("<tr><td><a href=''><button class='btn'>Edit</button></a></td>");
				out.println("<td><a href=''><button class='btn'>Delete</button></a></td>");
				out.println("<td><button action='UserInterface.html' class='btn'>Home</button>/td></tr>");

			}
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		out.println("</table>");
		out.println("</form>");
		out.println("</body>");
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ViewPatientDeatils ob = new ViewPatientDeatils();
		ob.ViewPatinetDetails("9787136690");
	}

	public void ViewPatinetDetails(String mobileNumber) throws SQLException, ClassNotFoundException {
		String query2 = "select * from patientdetails where patient_mobile_no=?  ";
		NewPatientReg object = new NewPatientReg();

		Connection con = object.DbConnection();

		PreparedStatement pre = con.prepareStatement(query2);
		pre.setString(1, mobileNumber);
		ResultSet rst = pre.executeQuery();

		while (rst.next()) {
			PatientName = rst.getString(1);
			System.out.println(PatientName);
			PatientMobileNo = rst.getString(2);
			PatientEmailId = rst.getString(3);
			PatientDob = rst.getString(4);
			PatientGender = rst.getString(5);

		}

	}
}

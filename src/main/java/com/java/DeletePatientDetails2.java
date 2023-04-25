package com.java;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeletePatientDetails2 extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		String mobileNumber = req.getParameter("mobileNumber");

		String query = "delete from patientdetails where patient_mobile_no=?  ";
		NewPatientReg object = new NewPatientReg();
		res.setContentType("text/html");
		out.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
		out.println("<body style='background-color:green; color:white; text-align:center;'>");


out.println("<div style='margin:auto; width:500px; margin-top:150px;'>");

		// Data base connection is started by calling the Dbconnection method from
		// NewPatinetReg class
		Connection con = null;
		try {
			con = object.DbConnection();

			PreparedStatement pre;

			pre = con.prepareStatement(query);

			pre.setString(1, mobileNumber);

			int confirmation = pre.executeUpdate();

			if (confirmation > 0) {
				out.println("<h2>Deleted Sucessfully</h2>");
			} else {
				out.println("<h2>Details not deleted!!! Try Again Later</h2>");
			}
out.println("<a href=''><button class='btn' style='margin-top:10px;'>Home</button></a>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		out.println("</div>");
		out.println("</body>");
	
	}
}
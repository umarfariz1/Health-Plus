package com.java;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewPatientReg extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// Assigning the request from user to individual Variables
		String patientName = req.getParameter("patinetName");
		String patientMobile = req.getParameter("patinetMobileNo");
		String patientEmail = req.getParameter("patientEmail");
		String patientDob = req.getParameter("patinetDob");
		String patientGender = req.getParameter("gender");

		// PrintWriter object to print the result in Web page
		PrintWriter out = res.getWriter();
		res.setContentType("text/html");

		// Link bootstrap
		out.println("<link rel='stylesheet' href=' css/bootstrap.css'></link> ");
		out.println("<body style='background-color:green; color:white;'>");

		// Creating object to call DB connection method.
		NewPatientReg object = new NewPatientReg();

		// Checking if the Mobile Number already exists by calling mobileNumberCheck
		// Method
		try {
			boolean mobileNumberCheck = object.CheckMobileNumber(patientMobile);

			// if the value is not true. then the user's details are updated in the Data
			// base
			if (!mobileNumberCheck) {

				try {

					// executing insert query by calling insert data method and returning the int
					// value to check if the query is executed successfully
					int update = object.insertData(patientName, patientMobile, patientEmail, patientDob, patientGender);
					if (update > 0) {
						out.println("<h2 style='color:green; text-align:center;'>Registered Sucessfully!!!!!!!!</h2>");
					} else {
						out.println("<h2>Not Registered!! Please Try Again Later</h2>");
					}
				} catch (ClassNotFoundException f) {
					out.print("ClassNotfound in Inserting into DB");
					f.printStackTrace();
				} catch (SQLException f) {

					f.printStackTrace();
				}
			} else {
				out.println("<h2 style='text-align:center;'> Mobile Number Already Exists</h2>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println("<table style='margin:auto; margin-top:100px; align-text:center;'>");
		out.println("<td><a href ='UserInterface.html'><button style='btn'>Home</button></a></td>");
		out.println("</table>");

		out.println("</body>");
	}

//Data Base connection is created in this method
	public Connection DbConnection() throws ClassNotFoundException {
		Connection con = null;
		Class.forName("org.postgresql.Driver");
		try {
			con = DriverManager.getConnection("jdbc:postgresql://http://192.168.35.235:5432/HealthPlus", "postgres", "9787136690");
		} catch (SQLException e) {

			e.printStackTrace();
		}
		System.out.println("Connection Sucessful");

		return con;
	}

	public int insertData(String name, String mobileNo, String email, String dob, String gender)
			throws ClassNotFoundException, SQLException {
		String query = "insert into patientdetails (patient_name, patient_mobile_no, patient_email, patient_dob , patient_gender,patient_age,prefix)"
				+ "	VALUES (?, ?, ?, ?, ?,?,?);";
		NewPatientReg object = new NewPatientReg();
		Connection con = object.DbConnection();
		String age = String.valueOf(object.AgeCalculator(dob));
		String patient_contractions;
		if(gender.equalsIgnoreCase("male")) {
			patient_contractions="Mr";
		}else {
			patient_contractions="Mrs";
		}
		System.out.println("Name: "+patient_contractions+ ""+name+"");
		System.out.println("Age of Patient "+age+" if born in "+dob);
		PreparedStatement pst = con.prepareStatement(query);
		pst.setString(1, name);
		pst.setString(2, mobileNo);
		pst.setString(3, email);
		pst.setString(4, dob);
		pst.setString(5, gender);
		pst.setString(6, age);
		pst.setString(7, patient_contractions);

		int update = pst.executeUpdate();

		return update;
	}

	public boolean CheckMobileNumber(String mobileNo) throws ClassNotFoundException, SQLException {
		String query2 = "select * from patientdetails where patient_mobile_no=?  ";
		NewPatientReg object = new NewPatientReg();
		Connection con = object.DbConnection();
		PreparedStatement pst = con.prepareStatement(query2);
		pst.setString(1, mobileNo);
		ResultSet rst = pst.executeQuery();
		boolean mobileNumberCheck = (rst.next());
		return mobileNumberCheck;

	}

	public int AgeCalculator(String dob) {
		LocalDate curDate = LocalDate.now();
		LocalDate dob1 = LocalDate.parse(dob);
		if ((dob1 != null) & (curDate != null)) {
			return Period.between(dob1, curDate).getYears();

		} else {
			return 0;
		}

	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

	}
}

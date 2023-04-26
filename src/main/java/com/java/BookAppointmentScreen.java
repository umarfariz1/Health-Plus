package com.java;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookAppointmentScreen extends HttpServlet {

	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		NewPatientReg object = new NewPatientReg();
		PrintWriter out = res.getWriter();
		res.setContentType("text/html");
		
		out.println("<link rel='stylesheet' href='css/bootstrap.css'> </link> ");
		
		
		out.println("<body style='color:white; background-color:green; '>");
		
		

		String patientName = null;
		String patientAge = null;
		String patientEmailId;
		String patientGender = null;
		String patientDob;
		String prefix = null;

		String patientMobileNumber = req.getParameter("patinetMobileNo");
		String dateOfApoointment = req.getParameter("dateOfAppointment");

		String DoctorMobileNo = null;
		String DoctorSpeciality = null;
		String DoctorTiming = null;
		String DoctorFee = null;

		String fetchPatientDetails = "select patient_name, patient_mobile_no, patient_email, patient_dob, patient_gender, patient_age, prefix from patientdetails where patient_mobile_no=?";

		Connection con = null;
		try {

			con = object.DbConnection();
			PreparedStatement pre = con.prepareStatement(fetchPatientDetails);
			pre.setString(1, patientMobileNumber);
			ResultSet rst = pre.executeQuery();

			while (rst.next()) {
				patientName = rst.getString(1);
				
				patientMobileNumber = rst.getString(2);
				patientEmailId = rst.getString(3);
				patientDob = rst.getString(4);
				patientGender = rst.getString(5);
				patientAge = rst.getString(6);
				prefix = rst.getString(7);
			}
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

			String bookAppointment = "insert into  appointment_details(name,mobile_number, date_of_appointment, doctor_name, fees, timing)"
					+ "	VALUES (?, ?, ?, ?, ?, ?);";
			PreparedStatement pre2 = con.prepareStatement(bookAppointment);
			pre2.setString(1, patientName);
			pre2.setString(2, patientMobileNumber);
			pre2.setString(4, doctorName);
			pre2.setString(3, dateOfApoointment);
			pre2.setString(5, DoctorFee);
			pre2.setString(6, DoctorTiming);
			int confirmation = pre2.executeUpdate();
			System.out.println(confirmation);
			if(confirmation>0) {
				out.println("<div style='width:1000px; margin:auto; margin-top:100px' >");
				out.println("<table class='table table-hover;' style='text-align:center;'>");
				
				out.println("<h2 style='text-align:center; margin-bottom:20px;'>Appointment Booked Sucessfull</h2>");
				
				out.println("<tr><td>Name:</td> ");
				out.println("<td><h6>"+prefix+"."+patientName+"</h6></td>");
				out.println("<td>Doctor's Name:</td> ");
				out.println("<td><h6>Dr."+doctorName+"</h6></td></tr>");
				
				out.println("<tr><td>Age:</td> ");
				out.println("<td><h6>"+patientAge+"</h6></td>");
				out.println("<td>Speciality:</td> ");
				out.println("<td><h6>"+DoctorSpeciality+"</h6></td></tr>");
				
				out.println("<tr><td>Gender:</td> ");
				out.println("<td><h6>"+patientGender+"</h6></td>");
				out.println("<td>Doctor's Mobile Number:</td> ");
				out.println("<td><h6>"+DoctorMobileNo+"</h6></td></tr>");
				
				out.println("<tr><td>Date of Appointment:</td> ");
				out.println("<td><h6>"+dateOfApoointment+"</h6></td>");
				
				out.println("<td>Timing:</td> ");
				out.println("<td><h6>"+DoctorTiming+"</h6></td></tr>");
				
				out.println("<tr><td>Consulting Fees:</td> ");
				
				out.println("<td><h6>"+DoctorFee+"</h6></td></tr>");
				out.println("</table>");
				out.println("<h4 style='text-align:center;'>Please be on time!!! <br>Thank You</h4>");
				
				out.println("<div style='width:1000px; margin:auto; margin-top:100px' >");
				out.println("<a href='UserInterface.html'><button>Home</button></a>");
				out.println("</div>");
				
				
			}else {
				out.println("Not Updated");
			}

		} catch (Exception e) {

		}
		out.println("</div> ");
		out.println("</table>");
		
		out.println("</body>");
		
		
	}

}

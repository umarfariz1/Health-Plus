package com.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookAppointmentScreen extends HttpServlet {
	String patientName;
	String patientMobileNo;
	String patientAge;
	String patientEmailId;
	String patientGender;
	String patientDob;
	String prefix;

	String DoctorName;
	String DoctorSpeciality;
	String DoctorTiming;
	String DoctorFee;
	public void service(HttpServletRequest req, HttpServletResponse res) {
		NewPatientReg object = new NewPatientReg();
		String patientMobileNumber = req.getParameter("patinetMobileNo");
		String doctorName = req.getParameter("doctor");
		String fetchPatientDetails = "select * from patientdetails where patient_mobile_no=?";
		String fetchDoctorDetails = "select * from doctordetails where doctor_name=?";
		String updateAppointment = "insert into  doctordetails where doctor_name=?";
		try {

			Connection con = object.DbConnection();
			PreparedStatement pre = con.prepareStatement(fetchPatientDetails);
			PreparedStatement pre1 = con.prepareStatement(fetchDoctorDetails);
			ResultSet rst = pre.executeQuery(fetchPatientDetails);
			ResultSet rst1 = pre.executeQuery(fetchDoctorDetails);
			
			while (rst.next()) {
				patientName = rst.getString(1);
				patientMobileNo = rst.getString(2);
				patientEmailId = rst.getString(3);
				patientDob = rst.getString(4);
				patientGender = rst.getString(5);
				patientDob = rst.getString(6);
				prefix = rst.getString(8);
			
				
		while (rst1.next()) {
			DoctorName = rst1.getString(1);
			DoctorSpeciality = rst1.getString(2);
			DoctorTiming = rst1.getString(3);
			DoctorFee = rst1.getString(4);
			
		}
			}
		} catch (Exception e) {

		}

	}

}

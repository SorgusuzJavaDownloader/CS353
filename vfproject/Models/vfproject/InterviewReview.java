package vfproject;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InterviewReview{

	private String CompanyName;
	private String username;
	private String InterviewProcess;


	public InterviewReview(String interviewProcess, String companyName, String username) {
		InterviewProcess = interviewProcess;
		CompanyName = companyName;
		this.username = username;
	}


	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getInterviewProcess() {
		return InterviewProcess;
	}
	public void setInterviewProcess(String interviewProcess) {
		InterviewProcess = interviewProcess;
	}


}


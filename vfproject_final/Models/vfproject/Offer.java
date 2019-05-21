package vfproject;

public class Offer {
	public Offer(String companyName, String jobName, int jobID, String jobDescription, String position, int salary) {
		CompanyName = companyName;
		JobName = jobName;
		JobID = jobID;
		JobDescription = jobDescription;
		Position = position;
		Salary = salary;
	}
	String CompanyName;
	String JobName;
	int JobID;
	String JobDescription;
	String Position;
	int Salary;
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	public String getJobName() {
		return JobName;
	}
	public void setJobName(String jobName) {
		JobName = jobName;
	}
	public int getJobID() {
		return JobID;
	}
	public void setJobID(int jobID) {
		JobID = jobID;
	}
	public String getJobDescription() {
		return JobDescription;
	}
	public void setJobDescription(String jobDescription) {
		JobDescription = jobDescription;
	}
	public String getPosition() {
		return Position;
	}
	public void setPosition(String position) {
		Position = position;
	}
	public int getSalary() {
		return Salary;
	}
	public void setSalary(int salary) {
		Salary = salary;
	}
	
	
	
	
}

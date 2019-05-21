package vfproject;

public class Applicant {
	
	public RegularUser user;
	public Job job;
	public Company company;
	public int applicationID;
	
	
	public Applicant(RegularUser user, Job job, Company company, int applicationID) {
		this.user = user;
		this.job = job;
		this.company = company;
		this.applicationID = applicationID;
	}
	public RegularUser getUser() {
		return user;
	}
	public void setUser(RegularUser user) {
		this.user = user;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public int getApplicationID() {
		return applicationID;
	}
	public void setApplicationID(int applicationID) {
		this.applicationID = applicationID;
	}
	
	
}

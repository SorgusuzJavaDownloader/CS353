package vfproject;

public class Application {
	
	User user;
	Job job;
	Company company;
	int applicationID;
	
	
	public Application(User user, Job job, Company company, int applicationID) {
		super();
		this.user = user;
		this.job = job;
		this.company = company;
		this.applicationID = applicationID;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
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

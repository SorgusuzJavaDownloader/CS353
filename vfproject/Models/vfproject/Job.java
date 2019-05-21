package vfproject;

public class Job {
	String jobName;
	int salary;
	String jobDesc;
	String position;
	int JobID;
	
	public int getJobID() {
		return JobID;
	}

	public void setJobID(int jobID) {
		JobID = jobID;
	}

	public Job(String jobName, int salary, String jobDesc, String position, int JobID) {
		this.jobName = jobName;
		this.salary = salary;
		this.jobDesc = jobDesc;
		this.position = position;
		this.JobID = JobID;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}

package vfproject;

public class Following {

	User user;
	Company company;
	int followsID;
	
	public Following(User user, Company company, int followsID) {
		super();
		this.user = user;
		this.company = company;
		this.followsID = followsID;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public int getFollowsID() {
		return followsID;
	}
	public void setFollowsID(int followsID) {
		this.followsID = followsID;
	}
	
	
}

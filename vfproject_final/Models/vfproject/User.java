package vfproject;


public class User{

	public String username;
	public String password;
	/*public String email;
	public String resume;
	int age;*/
	
	
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;

	}
	public User() {
		
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}

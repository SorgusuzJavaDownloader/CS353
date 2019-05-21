package vfproject;


public class RegularUser{

	public String username;
	public String password;
	public String email;
	public String resume;
	public String address;
	public String phonenumber;
	int age;
	
	public RegularUser(String username, String password, String email, String resume, String address,
			String phonenumber, int age) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.resume = resume;
		this.address = address;
		this.phonenumber = phonenumber;
		this.age = age;
	}
	
	
	
	public RegularUser() {
		
	}
	
	public String getEmail() {
		return email;
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
	public void setEmail(String email) {
		this.email = email;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}

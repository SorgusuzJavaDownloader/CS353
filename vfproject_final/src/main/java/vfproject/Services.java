package vfproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;


@Service
public class Services {
	
	final Connection conn;

	public Services() throws ClassNotFoundException, SQLException{
		conn = DBConnection.conn;
	}
	
	public String signupPage() {
		return "Signup";
	}
	public String CompanySignupPage() {
		return "CompanySignup";
	}
	public String initPage() {
		return "Initpage";
	}
	public String loginPage() {
		return "Login";
	}
	public String userPage() {
		return "UserPage";
	}
	public String companyPage() {
		return "CompanyPage";
	}
	public String companyIDDisp() {
		return "CompanyIDDisp";
	}
	public String companyLoginPage() {
		return "CompanyLogin";
	}
	public String searchCompanyPage(){
		return "SearchCompany";
	}
	public String searchJobByNameOrCompanyPage(){
		return "SearchJobByNameOrCompany";
	}
	public String appliedJobList() {
		return "AppliedJobList";
	}
	public String createAJobPage() {
		return "CreateAJob";
	}
	public String followingPage() {
		return "Following";
	}
	public String viewApplicants() {
		return "ViewApplicants";
	}
	public String signupErrorPage() {
		return "SignupErrorPage";
	}
	public String signupErrorPage2() {
		return "SignupErrorPage2";
	}
	public String Settings() {
		return "Settings";
	}
	public String CompanySettings() {
		return "CompanySettings";
	}
	public String Hires() {
		return "Hires";
	}
	public String SubmitReview() {
		return "SubmitReview";
	}
	public String PostReview() {
		return "PostReview";
	}
	public String viewReview() {
		return "ViewReview";
	}
	public String viewInterviewReview() {
		return "ViewInterviewReview";
	}
	public String companyViewReview() {
		return "CompanyViewReview";
	}
	public String postInterviewReview() {
		return "PostInterviewReview";
	}
	
	public String createRegularUser(String username, 
									String password, 
									String password2,
									String resume,
									String email,
									String address,
									String age,
									String phoneNum) throws SQLException {
		//System.out.println("geldi buraya  --> username : " + username + "   --> password : " + password );
		String query = null;
		String query2 = null;
		if(password.equals(password2))
			if(age != null) {
				query = "Insert Into User (username, password, userresume, email, address, age, phonenumber, bantime) Values ( \"" 
										+ username+ "\", \"" 
										+ password + "\", \""
										+ resume+ "\", \""
										+ email+ "\", \"" 
										+ address +"\"," 
										+ Integer.parseInt(age) + ", \""
										+ phoneNum +"\", 0" +");";
			}else {
				query = "Insert Into User (username, password, userresume, address, age, phonenumber, bantime) Values ( \"" 
						+ username+ "\", \"" 
						+ password + "\", \""
						+ resume+ "\", \"" 
						+ address +"\"," 
						+ 0 + ", \""
						+ phoneNum +"\", 0" +");";
			}
		else {
			System.out.println("Account could not created.");
			return "SignupErrorPage";
		}
		query2 = "Select * from User where username='" + username +"';";
		ResultSet set = conn.prepareStatement(query2).executeQuery();
		if(set.next()) { 
			return "SignupErrorPage2";
		}
		System.out.println("Query : " + query);
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not added to database!   username: "+ username + " password: " + password);
			System.out.println(e.getMessage());
		}
		return initPage();
	}
	/*public ArrayList<Job> getAppliedJobs(String username) throws SQLException{
		String query = "Select * From Job Natural Join Apply Where UserName = \"" + username +"\";";
		ResultSet set = conn.prepareStatement(query).executeQuery();
		ArrayList<Application> applications = new ArrayList<>();
		while(set.next()) {(User user, Job job, Company company, int applicationID)
			
			applications.add(new Application());
		}
		
		
		return null;
	}*/
	public void createJob(String JobName,String JobDescription,String Position,String Salary, String CompanyID) throws SQLException {
		int JobID = 0;
		
		int checker = 0;
		while(checker == 0) {
			int rand = (int) (Math.random()*1000000000);
			String checkQuery = "Select JobID From Job Where JobID = " + rand + ";";
			ResultSet set = conn.prepareStatement(checkQuery).executeQuery();
			if(set.next() == false) {
				checker = 1;
				JobID = rand;
			}
		}
		String query = "Insert Into Job (JobID, JobName, JobDescription, Position, Salary) Values("
				+ JobID + ",\""
				+ JobName + "\",\""
				+ JobDescription + "\",\""
				+ Position + "\",\""
				+ Salary + "\");";
		conn.prepareStatement(query).executeUpdate();
		
		String query2 = "Insert Into Offers(JobID, JobName, CompanyID) Values ("
				+ JobID +",\""
				+ JobName + "\",\""
				+ CompanyID+"\""
				+");";
		conn.prepareStatement(query2).executeUpdate();
	}
	public String createCompany(String name, String email, String address, String ID) throws SQLException {
		String query = null;
		String key = "asd";
		String checker = "asd";
		while(key.equals(checker) == true) {
			key = getAlphaNumericString(20);
			
			String query2 = "Select * From Company Where CompanyID = \"" + key +"\";";
			ResultSet set = conn.prepareStatement(query2).executeQuery();
			if(set.next() == true)
				checker = set.getString("CompanyID");
			else 
				checker = "asdasd";
				
			//System.out.println("This is key" + key);
		}
		String query2 = "Select * From Company Where CompanyName = \"" + name +"\";";
		ResultSet set = conn.prepareStatement(query2).executeQuery();
		if(set.next() == true)
			return null;
		
		query = "Insert Into Company (CompanyID, CompanyName, CompanyEmail, CompanyAddress) Values ( \""
				+ key + "\", \""
				+ name + "\", \""
				+ email+ "\", \"" 
				+ address +"\");";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not added to database! CompanyName: "+ name);
			System.out.println(e.getMessage());
		}
		ID = key;
		return key;
	}
	
	public void deleteApplication(int JobID, String username, String companyID) throws SQLException {
		String query = "Delete from Apply Where JobID = \"" + JobID + "\" and CompanyID = \"" + companyID + "\" and UserName = \"" + username + "\";";
		System.out.println(query);
		conn.prepareStatement(query).executeUpdate();
	}
	public void addApplication(int JobID, String CompanyID, String Username) throws SQLException {
		String query = "Insert Into Apply(JobID, CompanyID, UserName) Values (\"" + JobID + "\", \"" + CompanyID +"\",\"" + Username + "\");" ;
		
		String query2 = "Select * from Apply  Where JobID = \""+JobID+"\" and CompanyID = \"" + CompanyID + "\" and UserName = \"" + Username +"\";";
		ResultSet set = conn.prepareStatement(query2).executeQuery();
		if(set.next() == false)
			conn.prepareStatement(query).executeUpdate();
		else
			return;
		
	}
	
	public void addFollowed(String CompanyID, String Username) throws SQLException {
		String query = "Insert Into Follows(CompanyID, Username) Values (\"" + CompanyID +"\",\"" + Username + "\");" ;
		String query2 = "Select * from Follows  Where CompanyID = \"" + CompanyID + "\" and Username = \"" + Username +"\";";
		ResultSet set = conn.prepareStatement(query2).executeQuery();
		if(set.next() == false)
			conn.prepareStatement(query).executeUpdate();
		else
			return;
	}
	public void addHired(String CompanyID, String username, int JobID) throws SQLException {
		String query = "Insert Into Hires(CompanyID, username, JobID) Values (\"" + CompanyID + "\",\"" + username + "\",\"" + JobID + "\");";
		String query2 = "Select * from Hires  Where CompanyID = \"" + CompanyID + "\" and username = \"" + username + "\" and JobID = \"" + JobID +"\";";
		ResultSet set = conn.prepareStatement(query2).executeQuery();
		if(set.next() == false)
			conn.prepareStatement(query).executeUpdate();
		else
			return;
	}
	public void addAdmin(String username, String password) {
		
		String query = "Insert Into Admin (UserName, Password) Values ( \"" 
				+ username+ "\", \"" 
				+ password + "\");";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not added to database!(admin)   username: "+ username + " password: " + password);
			System.out.println(e.getMessage());
		}
	}
	public User login(String username, String password) throws SQLException {
		System.out.println(username +" "+ password);
		String query  = "Select * from User where username=\"" + username +"\" and password = \"" + password +"\";";
		String queryForAdmin = "Select * from Admin where username=\"" + username +"\" and password = \"" + password +"\";";
		
		ResultSet set1 = conn.prepareStatement(query).executeQuery();
		ResultSet set2 = conn.prepareStatement(queryForAdmin).executeQuery();
		
		String usr1;
		String usr2;
		String pass1;
		String pass2;
		
		if (set1.next() == true) {
			usr1 = set1.getString("username");
			pass1 = set1.getString("password");
			System.out.println("ROW user = " + usr1 + " " + pass1);
			User x = new User();
			return new User(usr1, pass1);
		}
		else if(set2.next() == true) {
			usr2 = set2.getString("username");
			pass2 = set2.getString("password");
			System.out.println("ROW admin = " + usr2 + " " + pass2);
			return new User(usr2, pass2);
		}else {
			System.out.println("Ben buradayım");
			return null;
		}
	}
	public Company companyLogin(String ID)  throws SQLException{
		String query  = "Select * from Company where CompanyID=\"" + ID +"\";";
		ResultSet set1 = conn.prepareStatement(query).executeQuery();
		if(set1.next() == false)
			return null;
		else {
			String CompanyID = set1.getString("CompanyID");
			String CompanyName = set1.getString("CompanyName");
			String CompanyAddress = set1.getString("CompanyAddress");
			String CompanyEmail= set1.getString("CompanyEmail");
			Company company = new Company(CompanyID, CompanyName, CompanyEmail, CompanyAddress);
			return company;
		}

		
	}
	public String getAlphaNumericString(int n){ 
  
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
        StringBuilder sb = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++) { 
            int index 
                = (int)(AlphaNumericString.length()* Math.random()); 
            sb.append(AlphaNumericString.charAt(index)); 
        } 
  
        return sb.toString(); 
    } 
	public ArrayList<Company> SearchCompany(String CompanyName) throws SQLException{
		
		ArrayList<Company> compList = new ArrayList<>();
		String query  = "Select * from Company where CompanyName=\"" + CompanyName +"\";";
		ResultSet set = conn.prepareStatement(query).executeQuery();
		while(set.next()) {
			String ID = set.getString("CompanyID");
			String name = set.getString("CompanyName");
			String address = set.getString("CompanyAddress");
			String email= set.getString("CompanyEmail");
			Company c = new Company(ID, name, email, address);
			compList.add(c);
		}
		return compList;
	}
	public ArrayList<Offer> findJobByCompanyOrName(String str) throws SQLException{
		String query1 = "Select * from Company Natural Join Offers Natural Join Job Where Job.JobName = \""+str+"\";";
		String query2 = "Select * from Company Natural Join Offers Natural Join Job Where Company.CompanyName = \""+str+"\";";
		ResultSet set1 = conn.prepareStatement(query1).executeQuery();
		ResultSet set2 = conn.prepareStatement(query2).executeQuery();
		ArrayList<Offer> offers = new ArrayList<>();
		// ResultSetMetaData rsmd = set1.getMetaData();
		
		while(set1.next()) {
			offers.add(new Offer(set1.getString("CompanyName"),set1.getString("JobName"),set1.getInt("JobID"), set1.getString("JobDescription"),set1.getString("Position"),set1.getInt("Salary")));
		}
		while(set2.next()) {
			offers.add(new Offer(set2.getString("CompanyName"),set2.getString("JobName"),set2.getInt("JobID"), set2.getString("JobDescription"),set2.getString("Position"),set2.getInt("Salary")));
		}
		return offers;
	}
	
	public User getUserbyName(String username) throws SQLException {
		String query = "Select * from User where username=\"" + username + "\";";
		ResultSet set = conn.prepareStatement(query).executeQuery();
		User user = null;
		while(set.next()) {
			user = new User(set.getString("username"), set.getString("password"));
		}
		return user;
		
	}
	
	public RegularUser getRegularUserbyName(String username) throws SQLException {
		String query = "Select * from User where username=\"" + username + "\";";
		ResultSet set = conn.prepareStatement(query).executeQuery();
		RegularUser user = null;
		while(set.next()) {
			user = new RegularUser(set.getString("username"), set.getString("password"), set.getString("email"), set.getString("userresume"),  set.getString("address"),set.getString("phonenumber"),set.getInt("age"));
		}
		return user;
		
	}
	public Company getCompanybyID(String id) throws SQLException{
		String query = "Select * from Company where CompanyID=\"" + id + "\";";
		ResultSet set = conn.prepareStatement(query).executeQuery();
		Company company = null;
		while(set.next()) {
			company = new Company(set.getString("CompanyID"), set.getString("CompanyName"), set.getString("CompanyEmail"), set.getString("CompanyAddress"));
		}
		return company;
	}
	
	public Job getJobbyID(int id) throws SQLException {
		String query = "Select * from Job where JobID=\"" + id + "\";";
		ResultSet set = conn.prepareStatement(query).executeQuery();
		Job job = null;
		while(set.next()) {
			job = new Job(set.getString("JobName"), set.getInt("Salary"), set.getString("JobDescription"), set.getString("Position"), set.getInt("JobID"));
		}
		return job;
	}
	
	public ArrayList<Application> getUserApplications(String username) throws SQLException{
		
		ArrayList<Application> appList = new ArrayList<>();
		String query = "Select * from Apply where UserName=\"" + username + "\";";
		ResultSet set = conn.prepareStatement(query).executeQuery();
		while(set.next()) {
			String usr = set.getString("username");
			String companyID = set.getString("CompanyID");
			int jobID = set.getInt("JobID");
			int appID = set.getInt("ApplicationID");
			
			appList.add(new Application(getUserbyName(usr), getJobbyID(jobID), getCompanybyID(companyID), appID));
		}
		return appList;
	}

	public ArrayList<Following> getUserFollows(String username) throws SQLException {
		ArrayList<Following> fList = new ArrayList<>();
		String query = "Select * from Follows where Username=\"" + username + "\";";
		ResultSet set = conn.prepareStatement(query).executeQuery();
		while(set.next()) {
			String usr = set.getString("Username");
			String companyID = set.getString("CompanyID");
			int fID = set.getInt("FollowsID");
			
			fList.add(new Following(getUserbyName(usr), getCompanybyID(companyID), fID));
		}
		return fList;
	}
	public ArrayList<Applicant> getApplicants(String companyName) throws SQLException {
		ArrayList<Applicant> aList = new ArrayList<>();
		String query = "Select * from User Natural Join Apply Natural Join Company where CompanyName=\"" + companyName + "\";";
		ResultSet set = conn.prepareStatement(query).executeQuery();
		while(set.next()) {
			String cID = set.getString("CompanyID");
			int jobID = set.getInt("JobID");
			int appID = set.getInt("ApplicationID");
			
			RegularUser regUser = new RegularUser(set.getString("username"), set.getString("password"), set.getString("email"), set.getString("userresume"),  set.getString("address"),set.getString("phonenumber"),set.getInt("age"));
			aList.add(new Applicant(regUser, getJobbyID(jobID), getCompanybyID(cID), appID));
		}
		return aList;
	}
	public ArrayList<Applicant> getHired(String companyID) throws SQLException{
		ArrayList<Applicant> employees = new ArrayList<>();
		String query = "Select * from Hires where CompanyID =\"" + companyID + "\";";
		ResultSet set = conn.prepareStatement(query).executeQuery();
		while(set.next()) {
			RegularUser usr = getRegularUserbyName(set.getString("username"));
			System.out.println("JobID :    " + set.getInt("JobID")  );
			Job job = getJobbyID(set.getInt("JobID"));
			System.out.println("JobName :    " +   job.getJobName());
			Company company = getCompanybyID(set.getString("CompanyID"));
			employees.add(new Applicant(usr, job, company, 0));
		}
		return employees;
	}
	public ArrayList<Company> getCompanies() throws SQLException{
		ArrayList<Company>  companies = new ArrayList<>();
		String query = "Select * from Company;";
		ResultSet set = conn.prepareStatement(query).executeQuery();
		while(set.next()) {
			String companyID = set.getString("CompanyID");
			String companyName = set.getString("CompanyName");
			String companyEmail = set.getString("CompanyEmail");
			String companyAddress= set.getString("CompanyAddress");
			companies.add(new Company(companyID, companyName, companyEmail, companyAddress));
		}
		return companies;
	}
	public void addInterviewReview(String CompanyName, String username, String process) throws SQLException {
		String query = "Insert Into InterviewReview(CompanyName, username, InterviewProcess) Values (\"" + CompanyName + "\", \"" + username +"\",\"" + process + "\");" ;
		String query2 = "Select * from InterviewReview  Where CompanyName = \""+CompanyName+"\" and username = \"" +username + "\" and InterviewProcess = \"" + process +"\";";
		ResultSet set = conn.prepareStatement(query2).executeQuery();
		if(set.next() == false)
			conn.prepareStatement(query).executeUpdate();
		else
			return;
	}

	public void createEmployeePost(String username, String companyName, String salaryInfo, String position, String pros, String cons,
			String workExp) throws SQLException {
		String query = "Insert Into EmployeeReview(CompanyName,username,Pros,Cons,Position,SalaryInfo,WorkExp) Values ('"
				+ companyName + "','"
				+ username + "','"
				+ pros + "','"
				+ cons + "','"
				+ position + "','"
				+ salaryInfo + "','"
				+ workExp + "');";
		conn.prepareStatement(query).executeUpdate();
	}
	public ArrayList<InterviewReview> getInterviewReview() throws SQLException{
		String query = "Select * from InterviewReview;";
		ResultSet set = conn.prepareStatement(query).executeQuery(); 
		ArrayList<InterviewReview> erarr = new ArrayList<>();
		while(set.next()) {
			InterviewReview er = new InterviewReview(set.getString("InterviewProcess"),set.getString("CompanyName"),set.getString("username"));
			erarr.add(er);
		}
		return erarr;
	}

	public ArrayList<EmployeeReview> getEmployeeReview() throws SQLException{
		String query = "Select * from EmployeeReview;";
		ResultSet set = conn.prepareStatement(query).executeQuery(); 
		ArrayList<EmployeeReview> erarr = new ArrayList<>();
		while(set.next()) {
			EmployeeReview er = new EmployeeReview(set.getString("Pros"),set.getString("Cons"),set.getString("Position"),set.getString("SalaryInfo"), set.getString("WorkExp"),set.getString("username"),set.getString("CompanyName"));
			erarr.add(er);
		}
		return erarr;
	}
	
	
}
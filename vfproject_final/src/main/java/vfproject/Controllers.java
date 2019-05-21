package vfproject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Controllers {
	
	@Autowired
	private Services Service;
	private User user;
	private Company company;
	
	@RequestMapping(value = "/Signup", method = RequestMethod.POST)
	public String createRegularUser(@RequestParam String username , 
									@RequestParam String password ,
									@RequestParam String password2,
									@RequestParam String resume   ,
									@RequestParam String email    ,
									@RequestParam String address  ,
									@RequestParam String age      ,
									@RequestParam String phoneNum
									) throws SQLException {
		return Service.createRegularUser(username, password, password2, resume, email, address, age, phoneNum);
	}
	@RequestMapping(value = "/CompanySignup", method = RequestMethod.POST)
	public String createCompany(@RequestParam String CompanyName , 
									@RequestParam String CompanyEmail ,
									@RequestParam String CompanyAddress,
									Model model
									) throws SQLException {
		String ID = null;
		String key = Service.createCompany(CompanyName, CompanyEmail, CompanyAddress,ID);
		company = new Company(key, CompanyName, CompanyEmail, CompanyAddress);
		if(key == null) {
			return CompanysignupPage();
		}
		else {
			return displayCompanyID(model);
		}	
	}
	@RequestMapping(value = "/CompanyIDDisp")
		public String displayCompanyID(Model model) throws SQLException {
		if(company != null) {
			String ID = company.getCompanyID();
			model.addAttribute("CompID", ID);
			return Service.companyIDDisp();
		}
		else
			return Service.initPage();
	}
	@RequestMapping(value = "/CompanyLogin", method = RequestMethod.POST)
	public String companyLogin(@RequestParam("CompanyID") String ID, Model model) throws SQLException, InterruptedException {
		Company company = Service.companyLogin(ID);
		if(company == null) {
			return Service.companyLoginPage();
		}
		else {
			user = null;
			this.company = company;
			return companyPage(model);
		}
	}
	@RequestMapping(value = "/CompanyPage", method = RequestMethod.POST)
	public String companyPage(Model model) {
		if(company != null) {
			String compName = company.getCompanyName();
			model.addAttribute("CompName", compName);
			return Service.companyPage();
		}
		else {
			return Service.initPage();
		}
	}
	@RequestMapping(value = "/Login", method = RequestMethod.POST)
	public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) throws SQLException, InterruptedException {
		
		User usr = Service.login(username, password);
		if(usr == null) {
			return Service.loginPage();
		}
		else {
			company = null;
			user = usr;
			return userPage(model);
		}
		
	}
	@RequestMapping(value = "/SearchJob")
	public String searchJob(Model model) throws SQLException, InterruptedException {
		if(user != null) {
			//String username = user.getUsername();
			Connection conn = DBConnection.conn;
			String query = "Select JobName, Salary, JobDescription, Position From Offers Natural Join Job;";
			ResultSet set = conn.prepareStatement(query).executeQuery();
			ArrayList<Job> jobs = new ArrayList<>();
			while(set.next()) {
				Job job = new Job(set.getString("JobName"),set.getInt("Salary"),set.getString("JobDescription"),set.getString("Position"), set.getInt("JobID"));
				jobs.add(job);
			}
			model.addAttribute("jobs", jobs);
			return "SearchJob";
		}
		else
			return Service.initPage();
	}
	@RequestMapping(value = "/SearchCompany", params = "SearchCompany", method = RequestMethod.POST)
	public String searchCompany1(@RequestParam("SearchCompany") String CompanyName, Model model) throws SQLException {
		if(user != null) {
			//System.out.println("***********************************************************************************************************search company");
			ArrayList<Company> compList = Service.SearchCompany(CompanyName);
			if(compList.isEmpty())
				System.out.println("ben b0şum");
			model.addAttribute("compList", compList);
			return Service.searchCompanyPage();
		}
		else
			return Service.initPage();
	}
	@RequestMapping(value = "/SearchCompany", params = "Follow", method = RequestMethod.POST)
	public String searchCompany2(@RequestParam String CompanyName, Model model) throws SQLException {
		if(user != null) {
			//System.out.println("Follow button");
			String query = "Select * from Company Where CompanyName = '"+ CompanyName + "';";
			ResultSet set =  Service.conn.prepareStatement(query).executeQuery();
			while(set.next()) {
				String username = user.getUsername();
				String companyID = set.getString("CompanyID");
				System.out.println("girdi girdi girdi");
				Service.addFollowed(companyID, username);
			}
			ArrayList<Following> fList = Service.getUserFollows(user.getUsername());
			model.addAttribute("fList", fList);
			return Service.followingPage();
		}
		else
			return Service.initPage();
	}
	@RequestMapping(value = "/ViewApplicants", params = "Hire", method = RequestMethod.POST)
	public String hire(@RequestParam String Username1,@RequestParam int JobID, Model model) throws SQLException {
		if(company != null) {
			System.out.println("hire button");
			Service.addHired(company.getCompanyID(), Username1, JobID);
			Service.deleteApplication(JobID, Username1, company.getCompanyID());
			return Hires(model);
		}
		else
			return Service.initPage();
	}
	@RequestMapping(value = "/ViewApplicants", params = "Reject", method = RequestMethod.POST)
	public String reject(@RequestParam String Username2,@RequestParam int JobID, Model model) throws SQLException {
		if(company != null) {
			System.out.println("reject button");
			Service.deleteApplication(JobID, Username2, company.getCompanyID());
			return Hires(model);
		}
		else
			return Service.initPage();
	}
	@RequestMapping(value = "/SearchJobByNameOrCompany",params = "Search",method = RequestMethod.POST)
	public String searchJobByNameOrCompany1(@RequestParam("SearchJob") String str, Model model) throws SQLException {
		if(user != null) {
			System.out.println("Search button");
			ArrayList<Offer> offerList = Service.findJobByCompanyOrName(str);
			//if(offerList.isEmpty())
				//System.out.println("ben b0şum");
			model.addAttribute("offerList", offerList);
			return Service.searchJobByNameOrCompanyPage();
		}else
			return Service.initPage();
	}
	@RequestMapping(value = "/SearchJobByNameOrCompany",params = "Apply",method = RequestMethod.POST)
	public String searchJobByNameOrCompany2(@RequestParam int ID, Model model) throws SQLException {
		if(user != null) {
			System.out.println("Apply button");
			
			String query = "Select * from Offers Natural Join Company Where JobID = '"+ ID + "';";
			ResultSet set =  Service.conn.prepareStatement(query).executeQuery();
			while(set.next()) {
				String userName = user.getUsername();
				int jobID = set.getInt("JobID");
				String companyID = set.getString("CompanyID");
				Service.addApplication(jobID, companyID, userName);
			}
			ArrayList<Application> appList = Service.getUserApplications(user.getUsername());
			model.addAttribute("appList", appList);
			return Service.appliedJobList();
		}else
			return Service.initPage();
	}
	@RequestMapping(value = "/SubmitReview",params = "SubmitReview", method = RequestMethod.POST)
	public String submitReviews1(@RequestParam String CompanyName, Model model) throws SQLException {
		if(user != null) {
			return postReview(CompanyName, model);
		}
		else
			return Service.initPage();
		
	}

	@RequestMapping(value = "/CreateAJob", method = RequestMethod.POST)
	public String createJob(@RequestParam String JobName,
							@RequestParam String JobDescription,
							@RequestParam String Position,
							@RequestParam String Salary) throws SQLException{
		if(company != null) {
			Service.createJob(JobName,JobDescription,Position,Salary,company.getCompanyID());
			return Service.createAJobPage();
		}else
			return Service.initPage();
	}
	@RequestMapping(value= "/PostReview", method = RequestMethod.POST)
	public String submitPostReview(@RequestParam String CompanyName,
									@RequestParam String SalaryInfo,
									@RequestParam String position,
									@RequestParam String Pros,
									@RequestParam String Cons,
									@RequestParam String WorkExp,
									Model model) throws SQLException{
		if(user != null) {
			Service.createEmployeePost(user.getUsername(),CompanyName, SalaryInfo, position, Pros, Cons, WorkExp);
			System.out.println("---->" +CompanyName);
		
			return postReview(CompanyName, model);
		}
		else
			return Service.initPage();
	}
	@RequestMapping(value= "/PostInterviewReview", method = RequestMethod.POST)
	public String submitPostReview(@RequestParam String CompanyName,
								   @RequestParam String InterviewProcess,
									Model model) throws SQLException{
		if(user != null) {
			Service.addInterviewReview(CompanyName, user.getUsername(), InterviewProcess);
			System.out.println("---->" +CompanyName);
			return postReview(CompanyName, model);
		}
		else
			return Service.initPage();
	}
	
	@RequestMapping(value = "/UserPage")
	public String userPage(Model model) throws InterruptedException {
		if(user != null) {
			model.addAttribute("userzaa", "Hello " + user.getUsername());
			return Service.userPage();
		}else
			return Service.initPage();
	}
	@RequestMapping(value = "/SearchCompany")
	public String searchCompanyPage(){
		if(user != null)
			return Service.searchCompanyPage();
		else
			return Service.initPage();
	}
	@RequestMapping("/Signup")
	public String signupPage() {
		return Service.signupPage();
	}
	@RequestMapping("/CompanyPage")
	public String CompanyPage() {
		if(company != null)
			return Service.companyPage();
		else 
			return Service.initPage();
	}
	@RequestMapping("/SignupErrorPage")
	public String signupErrorPage() {
		return Service.signupErrorPage();
	}
	@RequestMapping("/SignupErrorPage2")
	public String signupErrorPage2() {
		return Service.signupErrorPage2();
	}
	@RequestMapping("/CompanySignup")
	public String CompanysignupPage() {
		return Service.CompanySignupPage();
	}
	@RequestMapping("/Initpage")
	public String initPage() {
		return Service.initPage();
	}
	@RequestMapping("/Login")
	public String loginPage() {
		/*try {
			ArrayList<Offer> o = Service.findJobByCompanyOrName("job1");
			for(int i = 0; i < o.size(); i++) {
				System.out.println(o.get(i).getCompanyName());
				System.out.println(o.get(i).getJobName());
				System.out.println(o.get(i).getJobDescription());
				System.out.println(o.get(i).getPosition());	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return Service.loginPage();
	}
	@RequestMapping("/CompanyLogin")
	public String companyLoginPage(){
		return Service.companyLoginPage();
	}
	@RequestMapping("/SearchJobByNameOrCompany")
	public String searchJobByNameOrCompany(){
		if(user != null)
			return Service.searchJobByNameOrCompanyPage();
		else
			return Service.initPage();
	}
	@RequestMapping("/CreateAJob")
	public String createAJob(){
		if(company != null)
			return Service.createAJobPage();
		else
			return Service.initPage();
	}
	@RequestMapping("/AppliedJobList")
	public String appliedJobListPage(Model model) throws SQLException {
		if(user != null) {
			ArrayList<Application> applist = Service.getUserApplications(user.getUsername());
			model.addAttribute("appList", applist);
			return Service.appliedJobList();
		}
		else
			return Service.initPage();
	}
	@RequestMapping("/ViewApplicants")
	public String viewApplicants(Model model) throws SQLException {
		if(company != null) {
			ArrayList<Applicant> applicants = Service.getApplicants(company.getCompanyName());
			if(applicants.isEmpty()  )
				System.out.println("Boş");
			model.addAttribute("applicants", applicants);
			for(int i = 0; i < applicants.size(); i++) {
				System.out.println("CompanyID: " + applicants.get(i).getCompany().getCompanyID()
								+ " Email: " + applicants.get(i).getUser().getEmail()
								+ " JobID :" + applicants.get(i).getJob().getJobID());
			}
			return Service.viewApplicants();
		}
		else
			return Service.initPage();
	}
	@RequestMapping("/Following")
	public String followingPage(Model model) throws SQLException {
		if(user != null) {
			ArrayList<Following> fList = Service.getUserFollows(user.getUsername());
			model.addAttribute("fList", fList);
			return Service.followingPage();
		}
		else
			return Service.initPage();
	}
	@RequestMapping("/Logout")
	public String logOutPage() {
		if(user!=null)
			user=null;
		if(company!=null)
			company=null;
		
		return Service.initPage();	
	}
	@RequestMapping(value = "/Settings", params="password", method = RequestMethod.POST)
	public String Settings(@RequestParam String password) throws SQLException{
		if (user != null) {
			String usr = user.getUsername();
			String query = null;
			if(password != null) {
				query = "Update User Set password='" + password + "'Where username='" + usr + "';";
				ResultSet set = Service.conn.prepareStatement(query).executeQuery();
			}
			return Service.Settings();
		}
		else
			return Service.initPage();
	}
	
	@RequestMapping(value = "/SettingsResume", params="resume", method = RequestMethod.POST)
	public String SettingsResume(@RequestParam String resume) throws SQLException{
		if(user != null) {
			String usr = user.getUsername();
			String query = null;
			if(resume != null) {
				query = "Update User Set userresume='" + resume + "'Where username='" + usr + "';";
				ResultSet set = Service.conn.prepareStatement(query).executeQuery();
			}
			return Service.Settings();
		}else
			return Service.initPage();
	}
	
	@RequestMapping(value = "/SettingsAddress", params="address", method = RequestMethod.POST)
	public String SettingsAdress(@RequestParam String address) throws SQLException{
		if(user != null) {
			String usr = user.getUsername();
			String query = null;
			if(address != null) {
				query = "Update User Set address='" + address + "'Where username='" + usr + "';";
				ResultSet set = Service.conn.prepareStatement(query).executeQuery();
			}
			return Service.Settings();
		}else
			return Service.initPage();
	}
	
	@RequestMapping(value = "/SettingsPhoneNum", params="phoneNum", method = RequestMethod.POST)
	public String SettingsPhoneNum(@RequestParam String phoneNum) throws SQLException{
		if (user != null) {
			String usr = user.getUsername();
			String query = null;
			if(phoneNum != null) {
				query = "Update User Set phonenumber='" + phoneNum + "'Where username='" + usr + "';";
				ResultSet set = Service.conn.prepareStatement(query).executeQuery();
			}
			return Service.Settings();
		}else
			return Service.initPage();
	}
	@RequestMapping(value = "/SettingsEmail", params="email", method = RequestMethod.POST)
	public String SettingsEmail(@RequestParam String email) throws SQLException{
		if(user != null) {
			String usr = user.getUsername();
			String query = null;
			if(email != null) {
				query = "Update User Set email='" + email + "'Where username='" + usr + "';";
				ResultSet set = Service.conn.prepareStatement(query).executeQuery();
			}
			return Service.Settings();
		}else
			return Service.initPage();
	}
	@RequestMapping(value = "/Settings")
	public String Settings(){
		if(user != null)
			return Service.Settings();
		else
			return Service.initPage();
	}
	@RequestMapping(value = "/SettingsEmail")
	public String SettingsEmail(){
		if(user != null)
			return Service.Settings();
		else
			return Service.initPage();
	}
	@RequestMapping(value = "/SettingsResume")
	public String SettingsResume(){
		if(user != null)
			return Service.Settings();
		else
			return Service.initPage();
	}
	@RequestMapping(value = "/SettingAddresss")
	public String SettingsAddress(){
		if(user != null)
			return Service.Settings();
		else
			return Service.initPage();
	}
	@RequestMapping(value = "/SettingsPhoneNum")
	public String SettingsPhoneNum(){
		if(user != null)
			return Service.Settings();
		else
			return Service.initPage();
	}
	
	@RequestMapping(value = "/CompanySettingsEmail", params="CompanyEmail", method = RequestMethod.POST)
	public String CompanySettingsEmail(@RequestParam String CompanyEmail) throws SQLException{
		if(company != null) {
			String usr = company.getCompanyID();
			String query = null;
			if(CompanyEmail != null) {
				query = "Update Company Set CompanyEmail='" + CompanyEmail + "'Where CompanyID='" + usr + "';";
				ResultSet set = Service.conn.prepareStatement(query).executeQuery();
			}
			return Service.CompanySettings();
		}else
			return Service.initPage();
	}
	@RequestMapping(value = "/CompanySettingsName", params="CompanyName", method = RequestMethod.POST)
	public String CompanySettingsName(@RequestParam String CompanyName) throws SQLException{
		if(company != null) {
			String usr = company.getCompanyID();
			String query = null;
			if(CompanyName != null) {
				query = "Update Company Set CompanyName='" + CompanyName + "'Where CompanyID='" + usr + "';";
				ResultSet set = Service.conn.prepareStatement(query).executeQuery();
			}
			return Service.CompanySettings();
		}else
			return Service.initPage();
	}
	@RequestMapping(value = "/CompanySettingsAddress", params="CompanyAddress", method = RequestMethod.POST)
	public String CompanySettingsAddress(@RequestParam String CompanyAddress) throws SQLException{
		if(company != null) {
			String usr = company.getCompanyID();
			String query = null;
			if(CompanyAddress != null) {
				query = "Update Company Set CompanyAddress='" + CompanyAddress + "'Where CompanyID='" + usr + "';";
				ResultSet set = Service.conn.prepareStatement(query).executeQuery();
			}
			return Service.CompanySettings();
		}else 
			return Service.initPage();
	}
	@RequestMapping(value = "/CompanySettingsEmail")
	public String CompanySettingsEmail(){
		if(company != null)
			return Service.CompanySettings();
		else
			return Service.initPage();
	}
	@RequestMapping(value = "/CompanySettingsName")
	public String CompanySettingsName(){
		if(company != null)
			return Service.CompanySettings();
		else
			return Service.initPage();
	}
	@RequestMapping(value = "/CompanySettingsAddress")
	public String CompanySettingsAddress(){
		if(company != null)
			return Service.CompanySettings();
		else
			return Service.initPage();
	}
	@RequestMapping(value = "/CompanySettings")
	public String CompanySettings(){
		if(company != null)
			return Service.CompanySettings();
		else
			return Service.initPage();
	}
	@RequestMapping(value = "/Hires")
	public String Hires(Model model) throws SQLException{
		if(company != null) {
			ArrayList<Applicant> list = Service.getHired(company.getCompanyID());
			model.addAttribute("list", list);
			return Service.Hires();
		}
		else
			return Service.initPage();

	}
	@RequestMapping(value = "/SubmitReview")
	public String submitReview(Model model) throws SQLException {
		if(user != null) {
			ArrayList<Company> compList = Service.getCompanies();
			model.addAttribute("cList", compList);
			return Service.SubmitReview();
		}
		else
			return Service.initPage();
	}
	@RequestMapping(value = "/PostReview")
	public String postReview(String CompanyName, Model model) {
		if(user!=null) {
			model.addAttribute("CompanyName",CompanyName);
			return Service.PostReview();
		}
			
		else
			return Service.initPage();
	}
	@RequestMapping(value = "/ViewReview")
	public String viewReview(Model model) throws SQLException{
		if(user != null) {
			ArrayList<EmployeeReview> employeeRev = Service.getEmployeeReview();
			model.addAttribute("erArr", employeeRev);
			return Service.viewReview();
		}
			
		else
			return Service.initPage();
	}
	@RequestMapping(value = "/CompanyViewReview")
	public String viewCompanyReview(Model model) throws SQLException{
		if(company != null) {
			ArrayList<EmployeeReview> employeeRev = Service.getEmployeeReview();
			model.addAttribute("erArr", employeeRev);
			return Service.companyViewReview();
		}
			
		else
			return Service.initPage();
	}
	@RequestMapping(value = "/PostInterviewReviewww", method = RequestMethod.POST)
	public String postInterviewReview(@RequestParam String CompanyName, Model model) {
		if(user!=null) {
			model.addAttribute("CompanyName",CompanyName);
			System.out.println("lalala : " + CompanyName);
			return Service.postInterviewReview();
		}
			
		else
			return Service.initPage();
	}
	@RequestMapping(value = "/ViewInterviewReview")
	public String viewInterviewReview(Model model) throws SQLException{
		if(user != null) {
			ArrayList<InterviewReview> interviews = Service.getInterviewReview();
			model.addAttribute("interviews", interviews);
			return Service.viewInterviewReview();
		}
			
		else
			return Service.initPage();
	}
}
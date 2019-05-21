package vfproject;
import java.sql.*;
@SuppressWarnings("Duplicates")
public class DBConnection {
	static Connection conn;
	static Boolean tablesCreated = false;
	final String serverIP = "remotemysql.com";
	final String username = "Lw7uegZn3J";
	final String password = "Vjeg4tIvf8";
	final String url = "jdbc:mysql://"+serverIP + ":3306" +"/Lw7uegZn3J?user="+username+"&password="+password;
	final String url2 = "jdbc::mysql://localhost:3307/login";
	public DBConnection() throws ClassNotFoundException, SQLException {
		
		System.out.println("This is user name: "+username);
		//Class.forName("org.mariadb.jdbc.Driver");
		Class.forName("org.gjt.mm.mysql.Driver");
		System.out.println("This is user name: "+username);
		//conn = DriverManager.getConnection(url2, "root", "Yd4ever");
		conn = DriverManager.getConnection(url);
		
		//Statement stmt = conn.prepareStatement()
		//createUserTable();
		//createAdminTable();
		createCompanyTable();
		//createOffersTable();
		//createJobTable();
		//createWorksTable();
		//createFollowsTable();
		//createBanTable();
		//createHiresTable();
		//createApplyTable();
		//createEmployeeReviewTable();
		//createInterviewReviewTable();
		//String query = "Drop Table Lw7uegZn3J.User";
		//PreparedStatement stmt = conn.prepareStatement(query);
		//stmt.executeUpdate();
	}
	public Connection getConnection() {
		return conn;
	}
	public Boolean createUserTable() {
		//String dropTable = "Drop Table Lw7uegZn3J.User;";
		
		String query
        = "Create Table Lw7uegZn3J.User("
        + "username varchar(16) Primary Key,"
        + "password varchar(32) Not Null,"
        + "userresume varchar(255) Null,"
        + "email varchar(40),"
        + "address varchar(100) Null,"
        + "age int Null,"
        + "phonenumber varchar(11) Null,"
        + "bantime int Default 0,"
        + "Check (age Between 18 and 100)"
        +   ");";
		try {
			//PreparedStatement stmt2 = conn.prepareStatement(dropTable);
			//stmt2.executeUpdate();
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("User table could not created");
			return false;
		}
	}
public Boolean createAdminTable() {
		
		String query
        = "Create Table Lw7uegZn3J.Admin("
        + "Username varchar(16) Primary Key,"
        + "Password varchar(32) Not Null"
        +   ");";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Admin table could not created");
			return false;
		}
	}
	public Boolean createCompanyTable() {
		
		String query
	    = "Create Table Lw7uegZn3J.Company("
	    + "CompanyID varchar(20) Primary Key ,"
	    + "CompanyName varchar(32) Unique,"		
	    + "CompanyEmail varchar(40),"
	    + "CompanyAddress varchar(80),"
	    + "Index (CompanyName)"
	    +   ");";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Company table could not created");
			return false;
		}
	}
	/*public Boolean createWorksTable() {
		
		String query
	    = "Create Table Lw7uegZn3J.Works("
	    + "Username varchar(16),"
	    + "CompanyID int ,"
	    + "Position varchar(20),"
	    + "Salary int,"
	    + "WorkSince varchar(10),"		
	    + "Foreign Key (username) References User(username) On Delete Cascade,"
	    + "Foreign Key (CompanyID) References Company(CompanyID) On Delete Cascade"
	    +   ");";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Works table could not created");
			return false;
		}
	}*/
	public Boolean createFollowsTable() {
		
		String query
	    = "Create Table Lw7uegZn3J.Follows("
	    + "FollowsID int Auto_Increment,"
	    + "Username varchar(16),"
	    + "CompanyID varchar(20),"
	    + "Primary Key (FollowsID),"		
	    + "Foreign Key (Username) References User(UserName),"
	    + "Foreign Key (CompanyID) References Company(CompanyID)"
	    +   ");";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Admin table could not created");
			return false;
		}
	}
	public Boolean createBanTable() {
		
		String query
	    = "Create Table Lw7uegZn3J.Ban("
	    + "RegularUserName varchar(16),"
	    + "BanTime int,"
	    + "Foreign Key (regularusername) References User(UserName)"
	    + ");";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Ban table could not created");
			return false;
		}
	}
	public Boolean createHiresTable() {
		
		String query
	    = "Create Table Lw7uegZn3J.Hires("
	    + "HiresID int Auto_Increment,"
	    + "username varchar(16),"
	    + "CompanyID varchar(20),"
	    + "JobID int,"
	    + "Foreign Key (JobID) References Job(JobID),"
	    + "Foreign Key (username) References User(username),"
	    + "Foreign Key (CompanyID) References Company(CompanyID),"
	    + "Primary Key (HiresID)"
	    + ");";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Hires table could not created");
			return false;
		}
	}
	public boolean createApplyTable() {
		String query
		= "Create Table Lw7uegZn3J.Apply("
				+ "ApplicationID int Auto_Increment,"
				+ "JobID int,"
				+ "CompanyID varchar(20) references Company(CompanyID) On Delete Cascade,"
				+ "username varchar(16) references User(username),"
				+ "Primary Key (ApplicationID));";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	public boolean createOffersTable() {
		String query
		= "Create Table Lw7uegZn3J.Offers("
		+ "JobID int,"
		+ "JobName varchar(50) References Job(JobName) On Delete Cascade,"
		+ "CompanyID varchar(20) references Company(CompanyID) On Delete Cascade,"
		+ "Primary Key (JobID,CompanyID));";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean createJobTable() {
		String query
		= "Create Table Lw7uegZn3J.Job("
		+ "JobID int Auto_Increment,"
		+ "JobName varchar(50),"
		+ "JobDescription varchar(50),"
		+ "Position varchar(20),"
		+ "Salary int,"
		+ "Primary Key (JobID),"
		+ "Index (JobName)"
		+ " );";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	
	}
	public boolean createReviewTable() {
		String query
		= "Create Table Lw7uegZn3J.Review("
		+ "ReviewID int Auto_Increment,"
		+ "LikeCount int,"
		+ "ViewCount int,"
		+ "CompanyName varchar(20),"
		+ "Primary Key (ReviewID)"
		+ " );";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createInterviewReviewTable() {
		String query
		= "Create Table Lw7uegZn3J.InterviewReview("
		+ "InterviewReviewID int Auto_Increment,"
		+ "CompanyName varchar(32),"
		+ "username varchar(16),"
		+ "InterviewProcess varchar(255),"
		+ "Primary Key (InterviewReviewID),"
		+ "Foreign Key (CompanyName) References Company(CompanyName),"
		+ "Foreign Key (username) References User(username)"
		+ " );";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createEmployeeReviewTable() {
		String query
		= "Create Table Lw7uegZn3J.EmployeeReview("
		+ "ReviewID int Auto_Increment,"
		+ "CompanyName varchar(32),"
		+ "username varchar(16),"
		+ "Pros varchar(255),"
		+ "Cons varchar(255),"
		+ "Position varchar(20),"
		+ "SalaryInfo varchar(30),"
		+ "WorkExp varchar(255),"
		+ "Primary Key (ReviewID),"
		+ "Foreign Key (CompanyName) References Company(CompanyName),"
		+ "Foreign Key (username) References User(username)"
		+ " );";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
}
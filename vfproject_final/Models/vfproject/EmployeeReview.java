package vfproject;

public class EmployeeReview{


	

	private String Pros;
	 private String Cons;
	 private String Position;
	 private String SalaryInfo;
	 private String WorkExp;
	 private String username;
	 private String CompanyName;

	 public EmployeeReview(String pros, String cons, String position, String salaryInfo, String workExp, String username, String companyName) {
			Pros = pros;
			Cons = cons;
			Position = position;
			SalaryInfo = salaryInfo;
			WorkExp = workExp;
			this.username = username;
			CompanyName = companyName;
		}
	 
	 public String getPros() {
			return Pros;
		}

		public void setPros(String pros) {
			Pros = pros;
		}

		public String getCons() {
			return Cons;
		}

		public void setCons(String cons) {
			Cons = cons;
		}

		public String getPosition() {
			return Position;
		}

		public void setPosition(String position) {
			Position = position;
		}

		public String getSalaryInfo() {
			return SalaryInfo;
		}

		public void setSalaryInfo(String salaryInfo) {
			SalaryInfo = salaryInfo;
		}

		public String getWorkExp() {
			return WorkExp;
		}

		public void setWorkExp(String workExp) {
			WorkExp = workExp;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getCompanyName() {
			return CompanyName;
		}

		public void setCompanyName(String companyName) {
			CompanyName = companyName;
		}
}

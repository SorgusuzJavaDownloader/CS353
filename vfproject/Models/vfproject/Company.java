package vfproject;

public class Company {
	String CompanyID;
	String CompanyName;
	String CompanyEmail;
	String CompanyAddress;
	
	public Company() {}
	public Company(String companyID, String companyName, String companyEmail, String companyAddress) {
	
		CompanyID = companyID;
		CompanyName = companyName;
		CompanyEmail = companyEmail;
		CompanyAddress = companyAddress;
	}
	public String getCompanyID() {
		return CompanyID;
	}
	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	public String getCompanyEmail() {
		return CompanyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		CompanyEmail = companyEmail;
	}
	public String getCompanyAddress() {
		return CompanyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		CompanyAddress = companyAddress;
	}

}

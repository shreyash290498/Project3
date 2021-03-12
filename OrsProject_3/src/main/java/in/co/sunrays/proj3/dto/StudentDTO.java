package in.co.sunrays.proj3.dto;

import java.util.Date;

/**
 * Student JavaDto encapsulates Student attributes
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */

public class StudentDTO  extends BaseDTO  {

	  /**
     * First Name of Student
     */
    private String firstName;
    /**
     * Last Name of Student
     */
    private String lastName;
    /**
     * Date of Birth of Student
     */
    private Date dob;
    /**
     * Mobileno of Student
     */
    private String mobileNo;
    /**
     * Email of Student
     */
    private String email;
    /**
     * CollegeId of Student
     */
    private long collegeId;
    /**
     * College name of Student
     */
    private String collegeName;

    /**
     * accessor
     */

    public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}
	public String getValue() {
		// TODO Auto-generated method stub
		return firstName;
	}
	

	
}

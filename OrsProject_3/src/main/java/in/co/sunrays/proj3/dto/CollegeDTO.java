package in.co.sunrays.proj3.dto;

/**
 * Collage DTO classes
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 *
 */
public class CollegeDTO extends BaseDTO {
	/**
	 * Name Of College
	 */
	private String name;

	/**
	 * Address Of College
	 */
	private String address;

	/**
	 * State Of College
	 */
	private String state;

	/**
	 * City Of College
	 */
	private String city;

	/**
	 * PhoneNo of College
	 */
	private String phoneNo;

	/**
	 * 
	 * accessor
	 */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id+ "";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return name;
	}

}

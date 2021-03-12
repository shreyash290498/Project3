package in.co.sunrays.proj3.dto;
/**
 * Course JavaDto encapsulates Course attributes
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */
public class CourseDTO extends BaseDTO {

	/**
	 * Name of Course
	 */
	private String name;

	/**
	 * description of Course
	 */
	private String description;
	/**
	 * duration of Course
	 */
	private String duration;
/**
 * accessor
 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getKey() {
		// TODO Auto-generated method stub
		return id+"" ;
	}
	public String getValue() {
		// TODO Auto-generated method stub
		return name;
	}

}

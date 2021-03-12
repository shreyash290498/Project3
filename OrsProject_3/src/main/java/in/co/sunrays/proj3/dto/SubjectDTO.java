package in.co.sunrays.proj3.dto;

/**
 * Subject JavaDto encapsulates Subject attributes
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */
public class SubjectDTO extends BaseDTO {

	/**
	 * Name of Subject
	 */
	private String name;
	/**
	 * description of Subject
	 */
	private String description;
	/**
	 * courseId of Subject
	 */
	private long courseId;
	/**
	 * courseName of Subject
	 */
	private String courseName;
	/**
	 * semester of Subject
	 */
	private String semester;

	/**
	 * accessor
	 */

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

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

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return name;
	}

}

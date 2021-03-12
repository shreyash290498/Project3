package in.co.sunrays.proj3.model;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Factory of Model classes
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 *
 */

public final class ModelFactory {

	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.sunrays.proj3.bundle.system");

	private static final String DATABASE = rb.getString("DATABASE");
	/**
	 * Cache of Model classes
	 */
	private static HashMap modelCache = new HashMap();
	private static ModelFactory mFactory = null;

	/**
	 * Constructor is private so no other class can create instance of Model
	 * Locator
	 */
	private ModelFactory() {
	}

	/**
	 * Get the instance of Model Locator
	 *
	 * @return mFactory
	 */
	public static ModelFactory getInstance() {
		if (mFactory == null) {
			mFactory = new ModelFactory();
		}
		return mFactory;
	}

	/**
	 * Get instance of Marksheet Model
	 *
	 * @return marksheetModel
	 */
	public MarksheetModelInt getMarksheetModel() {
		MarksheetModelInt marksheetModel = (MarksheetModelInt) modelCache.get("marksheetModel");
		if (marksheetModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				marksheetModel = new MarksheetModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				marksheetModel = new MarksheetModelJDBCImpl();
			}
			modelCache.put("marksheetModel", marksheetModel);
		}
		return marksheetModel;
	}

	/**
	 * Get Instance Of UserModel
	 * 
	 * @return MarkSheetModel
	 */
	public UserModelInt getUserModel() {
		UserModelInt userModel = (UserModelInt) modelCache.get("userModel");
		if (userModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				userModel = new UserModelHibImpl();
				System.out.println("HiberNetImplemente");
			}
			if ("JDBC".equals(DATABASE)) {
				userModel = new UserModelJDBCImpl();
				System.out.println("JDBC implementesion");
			}
			modelCache.put("userModel", userModel);
		}
		return userModel;
	}

	/**
	 * Get Instance Of RoleModel
	 * 
	 * @return RoleModel
	 */
	public RoleModelInt getRoleModel() {
		RoleModelInt roleModel = (RoleModelInt) modelCache.get("roleModel");
		if (roleModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				roleModel = new RoleModleHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				roleModel = new RoleModelJDBCImpl();
			}
			modelCache.put("roleModel", roleModel);
		}
		return roleModel;
	}

	/**
	 * Get Instance Of StudentModel
	 * 
	 * @return StudentModel
	 */
	public StudentModelInt getStudentModel() {
		StudentModelInt studentModel = (StudentModelInt) modelCache.get("studentModel");
		if (studentModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				studentModel = new StudentModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				studentModel = new StudentModelJDCBCImpl();
			}
			modelCache.put("studentModel", studentModel);
		}
		return studentModel;
	}

	/**
	 * Get Instance Of CollegeModel
	 * 
	 * @return CollegeModel
	 */

	public CollegeModelInt getCollegeModel() {
		CollegeModelInt collegeModel = (CollegeModelInt) modelCache.get("collegeModel");
		if (collegeModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				collegeModel = new CollegeModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				collegeModel = new CollegeModelJDBCImpl();
			}
			modelCache.put("collegeModel", collegeModel);
		}
		return collegeModel;
	}

	/**
	 * Get Instance Of SubjectModel
	 * 
	 * @return SubjectModel
	 */
	public SubjectModelInt getSubjectModel() {
		SubjectModelInt subjectModel = (SubjectModelInt) modelCache.get("subjectModel");
		if (subjectModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				subjectModel = new SubjectModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				subjectModel = new SubjectModelJDBCImpl();
			}
			modelCache.put("subjectModel", subjectModel);
		}
		return subjectModel;
	}

	/**
	 * Get Instance Of FacultyModel
	 * 
	 * @return FacultyModel
	 */
	public FacultyModelInt getFacultyModel() {
		FacultyModelInt facultyModel = (FacultyModelInt) modelCache.get("facultyModel");
		if (facultyModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				facultyModel = new FacultyModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				facultyModel = new FacultyModelJDBCImpl();
			}
			modelCache.put("facultyModel", facultyModel);
		}
		return facultyModel;
	}

	/**
	 * Get Instance Of CourseModel
	 * 
	 * @return CourseModel
	 */
	public CourseModelInt getCourseModel() {
		CourseModelInt courseModel = (CourseModelInt) modelCache.get("courseModel");
		if (courseModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				courseModel = new CourseModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				courseModel = new CourseModelJDBCImpl();
			}
			modelCache.put("courseModel", courseModel);
		}
		return courseModel;
	}

	/**
	 * Get Instance Of TimeTableModel
	 * 
	 * @return TimeTableModel
	 */
	public TimeTableModelInt getTimeTableModel() {
		TimeTableModelInt timeTableModel = (TimeTableModelInt) modelCache.get("timeTableModel");
		if (timeTableModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				timeTableModel = new TimeTableModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				timeTableModel = new TimeTableModelJDBCImpl();
			}
			modelCache.put("timeTableModel", timeTableModel);
		}
		return timeTableModel;
	}
}

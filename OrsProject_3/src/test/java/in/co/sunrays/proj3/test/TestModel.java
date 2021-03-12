package in.co.sunrays.proj3.test;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.util.HibDataSource;

public class TestModel {
	public static void main(String[] args) {
		/*String s1=new String("raj");
		System.out.println("s1= "+s1.hashCode());
		
		String s2=new String("raj");
		System.out.println("s2= "+s2.hashCode());
		
		String s3="raj";
		System.out.println("s3= "+s3.hashCode());
		
		String s4="raj";
		System.out.println("s4= "+s4.hashCode());*/
		String s="ravi";
		String s1=new String("ravi");
		
		System.out.println(s.hashCode()+"    "+s1.hashCode());
		/*SessionFactory sf = HibDataSource.getSessionFactory();
		Session session = sf.openSession();
		UserDTO dto = (UserDTO) session.get(UserDTO.class, 4l);
		System.out.println(dto.getFirstName());
		System.out.println(dto.getLastName());
		System.out.println("===========");
		session.close();
		Session session1 = sf.openSession();
		UserDTO dto1 = (UserDTO) session1.get(UserDTO.class, 4l);
		System.out.println(dto1.getFirstName());
		System.out.println(dto1.getLastName());
		session1.close();*/
	}
}

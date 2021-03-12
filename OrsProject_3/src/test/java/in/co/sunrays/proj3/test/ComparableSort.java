package in.co.sunrays.proj3.test;

import java.util.ArrayList;
import java.util.Collections;

import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;

import antlr.collections.List;

public class ComparableSort implements Comparable<ComparableSort>{
private String name;

	public ComparableSort(String name) {
	
	this.name = name;
}
	public int compareTo(ComparableSort o) {
		// TODO Auto-generated method stub
		return getName().compareTo(o.getName());
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
		public String toString() {
			// TODO Auto-generated method stub
			return name;
		}
	public static void main(String[] args) {
		java.util.List l=new ArrayList();
		
		l.add(new ComparableSort("def"));
		l.add(new ComparableSort("def"));
		l.add(new ComparableSort("ghi"));
	
		Iterator it=	(Iterator) l.iterator();
		
	
	
	System.out.println(l);
	}

}

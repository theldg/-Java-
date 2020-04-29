
import javax.script.CompiledScript;
import javax.xml.soap.Node;

import org.omg.CORBA.portable.RemarshalException;

import com.ldg.ArryList;
import com.ldg.IList;
import com.ldg.circle.CircleLinkedList;
import com.ldg.doubles.LinkedList;

public class Main {

	public static void main1(String[] args) {

		IList<Integer> iList = new ArryList<Integer>();
		iList.add(1);
		iList.add(1);
		iList.add(1);
		iList.add(1);
		iList.add(1);
		System.out.println(iList);
	}

	/**
	 * 约瑟夫环问题
	 * 
	 * @param args
	 */

	public static void main2(String[] args) {

		CircleLinkedList<Integer> circleLinkedList = new CircleLinkedList<Integer>();
		circleLinkedList.add(1);
		circleLinkedList.add(2);
		circleLinkedList.add(3);
		circleLinkedList.add(4);
		circleLinkedList.add(5);
		circleLinkedList.add(6);
		circleLinkedList.add(7);
		circleLinkedList.add(8);
		circleLinkedList.reset();
	while(!circleLinkedList.isEmpty())
		{  
			
			circleLinkedList.next();
			circleLinkedList.next();
			System.out.println(circleLinkedList.remove());
			
		}
	
		
		
		
		
		
		
		
	
		
	}
	public static void main(String[] args) {
	
		LinkedList< Integer> linkedList=new  LinkedList<Integer>();
		linkedList.add(null);
		System.out.println(linkedList);
	}

}

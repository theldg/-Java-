
import com.ldg.ArryList;
import com.ldg.Car;

public class Main {

	public static void main(String[] args) {

		ArryList<Integer> arryList=new ArryList<Integer>();
		arryList.add(1);
		arryList.add(1);
		arryList.add(1);
		arryList.add(1);
		System.out.println(arryList);
		arryList.clear();
		System.out.println(arryList);

	}

}

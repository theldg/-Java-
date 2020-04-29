import com.ldg.circleDeque.CircleDeque;
import com.ldg.circleQueue.CircleQueue;
import com.ldg.deque.Deque;
import com.ldg.queue.Queue;

public class Main {

	public static void main(String[] args) {
		CircleDeque<Integer> circleQueue=new CircleDeque<Integer>();
		for(int i=0;i<10;i++)
		{
			circleQueue.enQueueRear(i);
			circleQueue.enQueueFront(i+100);
		}
		//  0 1 2 3 4 104 103 102 101 100
		//  104 103 102 101 100 0 1 2 3 4 5 6 7 8 9 109 108 107 106 105
		System.out.println(circleQueue.front());
		System.out.println(circleQueue.rear());
		System.out.println(circleQueue);
		
		for(int  i=0;i<20;i++)
		{
			System.out.println(circleQueue.deQueueFront());
		}
		
		circleQueue.clear();
		System.out.println(circleQueue);

	}

}

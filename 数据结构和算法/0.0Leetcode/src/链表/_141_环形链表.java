package 链表;

/***
 * https://leetcode-cn.com/problems/linked-list-cycle/submissions/ 
 * @author Administrator
  *  快慢指针  一步一步接近
 */
public class _141_环形链表 {

	 public boolean hasCycle(ListNode head) {
		    if (head == null)
				return false;
			ListNode  slowListNode=head;
			ListNode  fastListNode=head.next;
			while(fastListNode!=null&&fastListNode.next!=null)
			{
				slowListNode=slowListNode.next;
				fastListNode=fastListNode.next.next;
				if(slowListNode==fastListNode)
				{
					return  true;
				}
			}
			return  false;
		    }
}

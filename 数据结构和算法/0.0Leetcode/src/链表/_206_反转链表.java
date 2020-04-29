package 链表;
/***
 * https://leetcode-cn.com/problems/fan-zhuan-lian-biao-lcof/
 * @author Administrator
 *
 */
public class _206_反转链表 {
	// 递归解法
	// 递归就是自顶而下，自底而上，在去的路上解决问题，也能在回来的路上解决问题
	public ListNode reverseList(ListNode head) {
		// 递归出口
		if (head == null || head.next == null)
			return head;
		// 一样的新头节点
		ListNode newhead = reverseList(head.next);
		// 新指向节点
		head.next.next = head;
		head.next = null;
		// 相同头节点
		return newhead;
	}

	// 非递归  迭代
	public ListNode reverseList2(ListNode head) {
		ListNode newhead = null;
		while (head != null) {

			ListNode temp = head.next;
			head.next = newhead;
			newhead= head;
			head=temp;
		}
		return newhead;
	}

}

package 链表;

/**
 * https://leetcode-cn.com/problems/remove-linked-list-elements/solution/yi-chu-lian-biao-yuan-su-by-leetcode/
 * 
 * @author Administrator
 *
 */
//通过声明虚拟节点  前节点  当前节点来完成
public class _203_移除链表元素 {

	public ListNode removeElements(ListNode head, int val) {
		// 声明一个虚拟节点 指向头节点
		ListNode firstListNode = null;
		firstListNode.next = head;
		// 声明前一个节点
		ListNode preListNode = firstListNode;
		// 声明当前节点
		ListNode cuListNode = head;

		while (cuListNode != null) {
			if (cuListNode.val == val) {
                //删除节点 
				preListNode.next = cuListNode.next;
			} else {
                //更改前一个结点
				preListNode = cuListNode;
             }
			//更改当前节点
			cuListNode = cuListNode.next;
		}

		return firstListNode.next;

	}

}

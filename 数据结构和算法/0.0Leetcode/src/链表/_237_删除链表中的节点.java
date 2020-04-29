package 链表;

/**
 * https://leetcode-cn.com/problems/delete-node-in-a-linked-list/
 * 
 * @author Administrator
 *
 */
public class _237_删除链表中的节点 {

    //不能 node=node.next
	//因为这样虽然改变了当前node的值 但改变不了链表中节点指向(除非你改变node内部的属性
	//例如：改变 element next)
	public void deleteNode(ListNode node) {
		node.val = node.next.val;
		node.next = node.next.next;

	}
}

package ds.ch02.linkedlist;

import org.junit.Test;

public class ReverseLinkedList {

    /**
     * 单向链表 翻转
     */
    public static Node reverse(Node head) {
        if (head == null || head.next == null) {
            return head;  // 此段不要，其实效果相同，下面的循环已经覆盖这种情况了，但是还是要比较好
        }

        Node prev = null;
        Node cur = head;
        while (cur != null) {
            Node next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }

        return prev;
    }

    /**
     * 单向链表，每k个翻转。从头开始划分，划分不足k个，则该段不翻转
     */
    public static Node reverseK(Node head, int k) {
        return null;
    }

    @Test
    public void testReverse() {
        Node origin = LinkedListUtil.generateLinkedListFromArray(new int[] {1,2,3,4,5,6,7});
        System.out.println("origin : " + LinkedListUtil.toString(origin));
        Node reversed = reverse(origin);
        System.out.println("reversed : " + LinkedListUtil.toString(reversed));

        System.out.println(LinkedListUtil.toString(LinkedListUtil.generateLinkedListFromArray(null)));
        System.out.println(LinkedListUtil.toString(reverse(LinkedListUtil.generateLinkedListFromArray(null))));
        System.out.println(LinkedListUtil.toString(LinkedListUtil.generateLinkedListFromArray(new int[]{1})));
        System.out.println(LinkedListUtil.toString(reverse(LinkedListUtil.generateLinkedListFromArray(new int[]{1}))));
    }

}
package baseFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import org.junit.jupiter.api.Test;

@Data
public class ListNode {
    public int val;
    public ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    public static ListNode buildListNodeByArray( Integer[] array ){
        if (array.length == 0){
            return new ListNode();
        }
        if (array.length == 1){
            return new ListNode(array[0]);
        }
        ListNode head = new ListNode(-1);
        ListNode node = new ListNode(array[0]);
        head.next = node;
        for (int i = 1; i < array.length; i++) {
            node.next = new ListNode(array[i]);
            node = node.next;
        }
        return head.next;
    }
    public static List<Integer> iteratorList(ListNode listNode){
        List<Integer> retList = new ArrayList<>();
        while ( listNode != null ){
            retList.add(listNode.val);
            listNode = listNode.next;
        }
        return retList;
    }

    public static boolean hasCycle(ListNode head) {
        ListNode point1 = head;
        ListNode point2 = head;
        try{
            while (true){
                point1 = point1.next;
                point2 = point2.next.next;
                if (point1 == point2){
                    return true;
                }
            }

        } catch (Exception e){

        }
        return false;
    }


    public ListNode removeElements(ListNode head, int val) {
        /* 删除掉链表中的所有等于val的元素 */
        /* 链表首 , 链表中 , 链表尾 */
        ListNode preHead = new ListNode(-1);
        preHead.next = head;
        
        ListNode holder = new ListNode(-1);
        holder.next = preHead;

        while ( head != null){
            if (head.val == val){
                /* 当前节点需要删除 */
                /* 前驱节点的下一个指针指向当前节点的下一个(如果有的话)*/
                if (head.next!=null){
                    preHead.next = head.next;
                } else {
                    preHead.next = null;
                }
                /* 当前节点的下一个节点为空 */
                head.next = null;
                /* 当前节点更新为前驱节点的下一个节点 */
                head = preHead.next;
            } else {
                head = head.next;
                preHead = preHead.next;
            }
        }

        return holder.next.next;
    }


    public ListNode partition(ListNode head, int x) {
        /*输入：head = [1,4,3,2,5,2], x = 3 输出：[1,2,2,4,3,5]*/

        return new ListNode(-1);
    }

    /**
     * 获得链表的长度 (无环链表.有环会无限循环.)
     */
    public int getLengthOfListNode(ListNode head ){
        /* 链表长度大于1000自动结束 */
        int length = 0;
        while (head != null && length<1000){
            length++;
            head = head.next;
        }
        return length;
    }

    /**
     * 是否回文链表
     */
    public boolean isPalindrome(ListNode head) {

        List<Integer> list = iteratorList(head);
        ArrayList<Integer> reverseList = new ArrayList<>(list);
        Collections.reverse( reverseList);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i)!=reverseList.get(i)){
                return false;
            }
        }
        return true;
    }


    /** 给你一个链表的头节点 head ，旋转链表，将链表每个节点向右移动 k 个位置。 输入：head = [1,2,3,4,5], k = 2
     输出：[4,5,1,2,3]*/
    public ListNode rotateRight(ListNode head, int k) {
        /* 将末尾的k个元素移到链表的前面 (k的数量如果大于链表的长度,那么对其取余)  */
        int lengthOfListNode = getLengthOfListNode(head);
        if (lengthOfListNode == 0){
            /* 链表长度为0 不处理 */
            return head;
        }
        k = k % lengthOfListNode;
        if ( k== 0){
            /* 不旋转 -> 原封不动 */
            return head;
        }
        /* 得到旧的头 的前驱节点(新建) */
        ListNode oldPreHead = new ListNode(-1);
        oldPreHead.next = head;
        /* 找到操作位置 如果 k=2 l=5 则找到第5-2个 后面的全部作为新的头 */
        int iterNum = lengthOfListNode - k;

        ListNode curr = head;
        ListNode cutPoint = new ListNode(-1);
        for (int i = 1; i <= iterNum-1; i++) {
            /* todo 这个时候head的指向会变吗?*/
            curr = curr.next;
        }
        cutPoint.next = curr;

        ListNode newHead = new ListNode(-1);
        newHead.next = curr.next;

        for (int i = 1; i <= k; i++) {
            curr = curr.next;
        }
        curr.next = oldPreHead.next;
        cutPoint.next.next = null;
        return newHead.next;
    }



    /**
     * 输入：l1 = [2,4,3], l2 = [5,6,4]
     * 输出：[7,0,8]
     * 解释：342 + 465 = 807.
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode preHead = new ListNode();
        ListNode retHead = new ListNode();
        preHead.next  = retHead;
        int carry  = 0;
        while ( l1!=null || l2!=null ){
            int num1 = 0;
            int num2 = 0;
            if (l1 != null){
                num1 = l1.getVal();
            }
            if (l2 !=null){
                num2 = l2.getVal();
            }
            int remain = carry + num1 + num2 % 10;
            carry = carry + num1 + num2 / 10;

            retHead.next = new ListNode( remain );
            retHead = retHead.next;

            if (l1 !=null ){
                l1 = l1.next;
            }
            if (l2!=null){
                l2= l2.next;
            }
        }
        return preHead.next.next;
    }
}

class ListNodeTest{

    @Test
    public void test1(){
        ListNode listNode = new ListNode(1);
        ListNode listNode1 = ListNode.buildListNodeByArray(new Integer[]{-10,-3,0,5,9});
//        System.out.println(listNode1);
//        ListNode listNode2 = listNode.rotateRight(listNode1,3);
        System.out.println(sortedListToBST(listNode1));

        /*如何判断第 k 个节点是否存在呢？如果第 k 个节点位于第 h 层，则 k 的二进制表示包含 h+1 位，其中最高位是 1，其余各位从高到低表示从根节点到第 k 个节点的路径，0 表示移动到左子节点，1 表示移动到右子节点。通过位运算得到第 k 个节点对应的路径，判断该路径对应的节点是否存在，即可判断第 k 个节点是否存在。*/

    }
    /**
     * 给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
     * 你应当 保留 两个分区中每个节点的初始相对位置。
     */
    public ListNode partition(ListNode head, int x) {
        ListNode smaller = new ListNode();
        ListNode equalOrBigger = new ListNode();
        ListNode BiggerHead = equalOrBigger;
        ListNode smallHead = smaller;
        while (head!=null){
            if (head.val < x){
                smaller.next = new ListNode(head.val);
                smaller = smaller.next;
                head = head.next;
            } else {
                equalOrBigger.next = new ListNode(head.val);
                equalOrBigger = equalOrBigger.next;
                head = head.next;
            }
        }
        smaller.next = BiggerHead.next;
        return smallHead.next;
    }


    public TreeNode sortedListToBST(ListNode head) {
        /* grn 链表的中间位置 为根节点 根节点左边的链表为 左子树 , 根节点右边的链表为右子树 */
        /* 找到中位值可以通过快慢指针 slow & fast 来找到 中间指针的后一个为子链表的头 */
        return sortedListToBST(head,null);
    }
    public TreeNode sortedListToBST(ListNode head,ListNode tail){
        /* [head,tail) */
        if (head == null || head == tail){
            return null;
        }
        ListNode midNode = findMidNode(head,tail);
        System.out.println(midNode.val);
        TreeNode root = new TreeNode(midNode.val);
        root.right = sortedListToBST(midNode.next,tail);
        root.left = sortedListToBST(head,midNode) ;
        return root;

    }

    public ListNode findMidNode(ListNode begin,ListNode end){
        ListNode fastPtr = begin;
        ListNode slowPtr = begin;
        while (true){
            /* 快指针的下一个 为 尾巴 那就 break */
            if (fastPtr.next == null || fastPtr.next == end) {
                break;
            }
            fastPtr = fastPtr.next;
            if (fastPtr.next == null || fastPtr.next == end) {
                break;
            }
            fastPtr = fastPtr.next;
            slowPtr = slowPtr.next;
        }
        return slowPtr;
    }


    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        /* 相交链表 */
        /* 你能否设计一个时间复杂度 O(m + n) 、仅用 O(1) 内存的解决方案？*/
        ListNode ret = null;
        ListNode headA_repeat = headA;
        while (headA !=null){
            int val = headA.val;
            if (val >0){
                /* 没有被其他链表染色过 */
                headA.val = -headA.val;
            }
            headA = headA.next;
        }
        while (headB!=null){
            int val = headB.val;
            if (val >0){
                /* 没有被其他链表染色过 */
            } else {
                ret = headB;
                break;
            }
            headB = headB.next;
        }
        while (headA_repeat !=null){
            int val = headA_repeat.val;
            if (val <0){
                /* 没有被其他链表染色过 */
                headA_repeat.val = -headA_repeat.val;
            }
            headA_repeat = headA_repeat.next;
        }

        return null;

    }

}



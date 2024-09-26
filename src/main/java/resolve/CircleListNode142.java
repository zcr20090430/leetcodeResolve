package resolve;

import baseFunction.ListNode;
import java.util.List;
import org.junit.Test;

public class CircleListNode142 {



    public ListNode detectCycle(ListNode head) {
        /* 如果有环 那就返回入环的第一个节点 如果没有环 那就返回-1 */
        /* 环的长度为b+c 环之前的长度为a fast 指针 在慢指针进入环之前可能走了 n(0,1,2,...)圏
        *  slow指针进入环的时候, 快指针进行追击 , slow走了b 这时候 fast也在b点 等式如下:
        * [fast] a + n(b+c) + b  = [slow] 2* ( a+b ) */
        /* b 为相遇点, 整理等式  2a - a = n(b+c) + b  - 2b
        *  a = n(b+c) - b  当前slow在 环+b位置  也就是说在走a = n(b+c) - b的距离 , 这时slow指针正好在起始点 ,
        *  第三个指针走a 跟slow相遇在起点位置 进行返回 */
        /* 快慢指针 */
        if (head == null){
            return null;
        }
        ListNode fastNode = head;
        ListNode slowNode = head;
        while (true){
            fastNode = fastNode.next;
            if (fastNode == null){
                return null;
            }
            fastNode = fastNode.next;
            if (fastNode == null){
                return null;
            }
            slowNode = slowNode.next;
            if (slowNode == fastNode){
                /* 两节点相遇 */
                /* 此时再起一个节点*/
                ListNode thir = head;
                while (true){
                    if (thir == slowNode){
                        return thir;
                    }
                    thir = thir.next;
                    slowNode = slowNode.next;
                }
            }
        }



    }

}

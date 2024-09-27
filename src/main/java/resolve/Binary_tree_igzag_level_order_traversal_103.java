package resolve;

import baseFunction.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Binary_tree_igzag_level_order_traversal_103 {
    //给你二叉树的根节点 root ，返回其节点值的 锯齿形层序遍历 。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root==null){
            return new ArrayList<>();
        }
        // level 偶数 左->右 level 奇数 右->左
        int level = 0;
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queueThisLevel = new LinkedList<>();
        Queue<TreeNode> queueNextLevel = new LinkedList<>();
        queueThisLevel.offer(root);
        while (true){
            List<Integer> thisLevelNums = new ArrayList<>();
            while (!queueThisLevel.isEmpty()){

                TreeNode poll = queueThisLevel.poll();
                thisLevelNums.add(poll.val);
                if (poll.left!=null){
                    queueNextLevel.offer(poll.left);
                }
                if (poll.right != null){
                    queueNextLevel.offer(poll.right);
                }
            }
            if (level%2 == 0){
                res.add(new ArrayList<>(thisLevelNums));
            } else {
                Collections.reverse(thisLevelNums);
                res.add(new ArrayList<>(thisLevelNums));
            }
            if (queueNextLevel.isEmpty()){
                break;
            }
            queueThisLevel = queueNextLevel;
            queueNextLevel = new LinkedList<>();
            level++;


        }
        return res;
    }

}

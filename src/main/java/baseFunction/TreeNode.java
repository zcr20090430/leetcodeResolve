package baseFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

@Data
@NoArgsConstructor

public class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val){
        this.val = val;
    }

    TreeNode(int val,TreeNode left,TreeNode right){
        this.val = val;
        this.left = left;
        this.right = right;
    }

    /* 输入: 前序遍历  preorder = [3,9,20,15,7], 中序遍历 inorder = [9,3,15,20,7]
         输出: [3,9,20,null,null,15,7]
            3
          9  20
            15 7*/



    public TreeNode flattenRecursive(TreeNode root){
        /* 前序遍历 */
        if (root==null){
            return null;
        }
        TreeNode leftNode = null;
        TreeNode rightNode = null;
        if (root.left!=null){
            leftNode = flattenRecursive(root.left);
        }
        if (root.right!=null){
            rightNode = flattenRecursive(root.right);
        }
        if (rightNode !=null && leftNode ==null){
            return root;
        } else if (rightNode !=null && leftNode!=null){
            root.right = leftNode;
            while (leftNode.right != null){
                leftNode = leftNode.right;
            }
            leftNode.right = rightNode;
            root.left =null;
            return root;
        } else {
            /* 右侧为空 左直接接入 */
            root.right = leftNode;
            root.left = null;
            return root;
        }
    }






    public static void LevelTraversalTree( TreeNode treeNode ){
        if (treeNode == null){
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(treeNode);
        while ( !queue.isEmpty() ){
            TreeNode thisNode = queue.poll();
            System.out.println(thisNode.val);
            if (thisNode.left!=null){
                queue.add(thisNode.left);
            }
            if (thisNode.right!=null){
                queue.add(thisNode.right);
            }
        }
    }

    public  static TreeNode getByLevelTraversal( Integer[] array , int index ) {
        if (index >= array.length){
            return null;
        }
        if (array[index] == null){
            return null;
        }
        /* 第0个 找 1,2
        *  1 找 3,4
        *  2 找 5,6
        *  3 找 7,8 ... */
        /* leftNoNode */
        return new TreeNode(
                array[index],
                getByLevelTraversal(array,2*index+1),
                getByLevelTraversal(array,2*index+2)
        );
    }

    public static void preOrderTraversalTree(TreeNode treeNode){
        if (treeNode == null){
            return;
        }
        System.out.println( treeNode.val );
        preOrderTraversalTree(treeNode.left);
        preOrderTraversalTree(treeNode.right);
    }

    public static void midOrderTraversalTree(TreeNode treeNode){
        if (treeNode == null){
            return;
        }
        midOrderTraversalTree(treeNode.left);
        System.out.println( treeNode.val );
        midOrderTraversalTree(treeNode.right);
    }


    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> retList = new ArrayList<>();
        if ( root == null){
            return new ArrayList<>();
        }
        retList.addAll(inorderTraversal(root.left));
        retList.add( root.val);
        retList.addAll(inorderTraversal(root.right));
        return retList;
    }


    public static List<List<Integer>> levelOrder(TreeNode root) {

        List<List<Integer>> ret = new ArrayList<>();

        Queue<TreeNode> queueOfOdd = new LinkedList<>();
        Queue<TreeNode> queueOfEven = new LinkedList<>();
        queueOfOdd.add(root);

        while (!queueOfOdd.isEmpty() || !queueOfEven.isEmpty()){
            /* */
            List<Integer> subRet1 = new ArrayList<>();

            while (!queueOfOdd.isEmpty()){
                TreeNode thisNode = queueOfOdd.poll();
                subRet1.add(thisNode.val);
                if (thisNode.left!=null){
                    queueOfEven.add(thisNode.left);
                }
                if (thisNode.right!=null){
                    queueOfEven.add(thisNode.right);
                }
            }
            if (!subRet1.isEmpty()){
                ret.add(subRet1);
            }
            List<Integer> subRet2 = new ArrayList<>();
            while (!queueOfEven.isEmpty()){
                TreeNode thisNode = queueOfEven.poll();
                subRet2.add(thisNode.val);
                if (thisNode.left!=null){
                    queueOfOdd.add(thisNode.left);
                }
                if (thisNode.right!=null){
                    queueOfOdd.add(thisNode.right);
                }
            }
            if (!subRet2.isEmpty()){
                ret.add(subRet2);
            }
        }
        return ret;
    }


    /* 无重复元素 */
    public static TreeNode buildTreeByPreAndIn(int[] preOrder, int[] inorder){

        /* pre first is root */
        int i = preOrder[0];

        List<Integer> inOrderList = Arrays.stream(inorder).boxed().collect(Collectors.toList());
        List<Integer> preOrderList = Arrays.stream(preOrder).boxed().collect(Collectors.toList());

        int indexOfRoot = inOrderList.indexOf(i);

        /* 中序遍历 [左子树  root 右子树] , 前序遍历 [root , 左子树, 右子树 ] */
        /* 主要找到 前序遍历的 左子树& 右子树的 分界点 */
        /* 中序遍历所有左子树 在 前序遍历的最大index */

        int indexOfSplit  = 0;
        for (int i1 = 0; i1 < indexOfRoot; i1++) {
            indexOfSplit = Math.max(preOrderList.indexOf(i1),indexOfSplit);
        }
        return new TreeNode(
                i,
                buildTreeByPreAndIn(
                        Arrays.copyOfRange(preOrder,1,indexOfSplit+1),
                        Arrays.copyOfRange(preOrder,indexOfSplit+1,preOrder.length)
                ),
                buildTreeByPreAndIn(
                        Arrays.copyOfRange(inorder,0,indexOfRoot),
                        Arrays.copyOfRange(inorder,indexOfRoot+1,inorder.length)
                )
        );
    }


    public static boolean hasPathSum(TreeNode root, int targetSum) {
            if (root == null){
                return false;
            }
        ArrayList<Integer> integers = new ArrayList<>();
        backTracingTreeSum( root ,integers , 0);
        System.out.println(integers);
        return integers.contains(targetSum);
    }

    public static void backTracingTreeSum(TreeNode root,List<Integer> sums , Integer sourceSumValue){
            if ( root.right == null && root.left == null){
                /* 都为空,说明到底了 */
                sums.add( sourceSumValue + root.val);
            }
            if (root.right != null ){
                backTracingTreeSum( root.right , sums , sourceSumValue+root.val);
            }
            if ( root.left != null ){
                backTracingTreeSum(root.left , sums , sourceSumValue+root.val);
            }

    }


    public boolean isBalanced(TreeNode root) {
        /*  给定一个二叉树，判断它是否是高度平衡的二叉树。
            本题中，一棵高度平衡二叉树定义为：
            一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。*/

        if (root == null){
            return true;
        }
        if(Math.abs(getHOfTree(root.left) - getHOfTree(root.right)) >1){
            return false;
        }
        return isBalanced(root.left) && isBalanced(root.right);
    }
    public int getHOfTree(TreeNode node){
        if (node == null){
            return 0;
        }
        return Math.max(getHOfTree(node.left),getHOfTree(node.right)) + 1;
    }


    public int sumNumbers(TreeNode root) {
        /* 深度优先搜索 */
        /*  4
        *  9 0
        * 5 1      ->  495 + 491 + 40 = 1026 */
        if (root == null){
            return 0;
        }
        if (root.left == null && root.right == null){
            return root.val;
        }

        List<Integer> result = new ArrayList<>();
        deepFirstSearchOfSumNumbers( root , new ArrayList<>() , result);
        System.out.println(result);
        return result.stream().reduce(Integer::sum).orElse(0);
    }

    public void deepFirstSearchOfSumNumbers(TreeNode node , List<Integer> path ,List<Integer> result){
        if (node.left == null && node.right == null){
            path.add(node.val);
            int sum = 0;
            for (Integer integer : path) {
                sum = sum*10 + integer;
            }
            result.add(sum);
            path.remove(path.size()-1);
            return;
        }
        path.add(node.val);
        if ( node.left != null ){
            deepFirstSearchOfSumNumbers( node.left ,path,result);
        }
        if (node.right!=null){
            deepFirstSearchOfSumNumbers( node.right , path , result);
        }
        path.remove(path.size()-1);
    }

    public int maxPathSum(TreeNode root) {

        List<Integer> res = new ArrayList<>();
        maxPathSub(root,res);
        return res.stream().max(Integer::compareTo).orElse(0);
    }

    public int maxPathSub( TreeNode node ,List<Integer> everyNodeMaxPathSum){
        /* */
        if (node == null){
            return 0;
        }
        int leftPathMax = Math.max(0,maxPathSub(node.left,everyNodeMaxPathSum));
        int rightPathMax = Math.max(0,maxPathSub(node.right,everyNodeMaxPathSum));
        int thisNodeBeRootMaxPath = leftPathMax + rightPathMax + node.val;
        everyNodeMaxPathSum.add(thisNodeBeRootMaxPath);

        return Math.max( leftPathMax , rightPathMax ) + node.val ;
    }


    public boolean isSymmetric(TreeNode root) {
        return cursive( root.left, root.right );

    }
    public boolean cursive( TreeNode leftNode , TreeNode rightNode ){
        if (leftNode == null && rightNode == null){
            return true;
        }
        if (leftNode != null && rightNode == null){
            return false;
        }
        if (leftNode == null && rightNode != null){
            return false;
        }
        if (leftNode.val != rightNode .val){
            return false;
        }
        return cursive( leftNode.left, rightNode.right )
                && cursive( leftNode.right , rightNode.left );
    }



    public int countNodes(TreeNode root) {
        return 0;

    }

    public TreeNode sortedArrayToBST(int[] nums) {

        if (nums.length == 0){
            return null;
        }
        if (nums.length == 1){
            return new TreeNode(nums[0]);
        }
        if (nums.length == 2){
            TreeNode root = new  TreeNode(nums[0]);
            root.right = new TreeNode(nums[1]);
        }
        return new TreeNode(
                nums[nums.length/2] ,
                sortedArrayToBST(Arrays.copyOfRange(nums,0,nums.length/2-1)),
                sortedArrayToBST(Arrays.copyOfRange(nums,nums.length/2+1,nums.length)));

        /* 数组的中心位置为 root 节点 */
    }

    public int minDepth(TreeNode root) {
        /* 数的最小深度 */

        if (root==null){
            return 0;
        }
        return 1+ Math.min( minDepth(root.right),minDepth(root.left));
    }
//    public Node connect(Node root) {
//
//
//    }

    /**给你二叉树的根结点 root ，请你将它展开为一个单链表：

     展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。
     展开后的单链表应该与二叉树 先序遍历 顺序相同。*/

    public void flatten(TreeNode root) {

    }


    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null){
            return "";
        }
        List<String> rootVals= new ArrayList<>();

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        rootVals.add(String.valueOf(root.val));
        while ( !queue.isEmpty()  ){
            TreeNode thisNode = queue.poll();
            if (thisNode.left!=null){
                queue.add(thisNode.left);
                rootVals.add( String.valueOf(thisNode.left.val) );
            } else {
                rootVals.add("null");
            }
            if (thisNode.right!=null){
                queue.add(thisNode.right);
                rootVals.add( String.valueOf(thisNode.right.val) );
            } else {
                rootVals.add("null");
            }
        }
        return rootVals.stream().collect(Collectors.joining(","));
    }


    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.equals(""))
        {
            return null;
        }
        /* -10,null,20,15,7,null,null,null,null */
        String[] split = data.split(",");
        if (split.length == 0){
            return new TreeNode();
        }
        TreeNode root = new TreeNode(Integer.parseInt(split[0]));

        Queue<TreeNode> levelNodes = new LinkedList<>();
        levelNodes.add(root);
        int thisLevelPtr = 0;
        int nextLevelPtr = 1;
        while (!levelNodes.isEmpty()) {
            TreeNode thisLevelNode = levelNodes.poll();
            /* leftNode */
            if (!split[nextLevelPtr].equals("null")) {
                TreeNode treeNode = new TreeNode(Integer.parseInt(split[nextLevelPtr]));
                thisLevelNode.left = treeNode;
                levelNodes.add(treeNode);
                nextLevelPtr++;
            } else {
                nextLevelPtr++;
            }
            /* rightNode */
            if (!split[nextLevelPtr].equals("null")) {
                TreeNode treeNode = new TreeNode(Integer.parseInt(split[nextLevelPtr]));
                thisLevelNode.right = treeNode;
                levelNodes.add(treeNode);
                nextLevelPtr++;
            } else {
                nextLevelPtr++;
            }

        }
        return root;
    }

    /* 镜像翻转树 */
    public TreeNode invertTree(TreeNode root) {
        if (root == null){
            return root;
        }
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    public int findBottomLeftValue(TreeNode root) {
        /* 层序遍历 */
        Queue<TreeNode> thisLevelQueue = new LinkedList<>();

        thisLevelQueue.add(root);
        while (true){
            Queue<TreeNode> nextLevelQueue = new LinkedList<>();
            System.out.println(thisLevelQueue.stream().map(o->o.val).collect(Collectors.toList()));
            Integer firstVal = thisLevelQueue.peek().val;
            while (!thisLevelQueue.isEmpty()){
                TreeNode poll = thisLevelQueue.poll();
                if (poll.left!=null)  {nextLevelQueue.add(poll.left);}
                if (poll.right!=null) {nextLevelQueue.add(poll.right);}
            }

            System.out.println(nextLevelQueue.stream().map(o->o.val).collect(Collectors.toList()));
            if (nextLevelQueue.isEmpty()){
                return firstVal;
            } else {
                thisLevelQueue = nextLevelQueue;
            }
        }

    }



    public List<Integer> largestValues(TreeNode root) {
        List<Integer> retList = new ArrayList<>();
        Queue<TreeNode> thisLevelTreeNodes = new LinkedList<>();
        thisLevelTreeNodes.offer(root);
        retList.add(root.val);
        while(true){
            Queue<TreeNode> nextLevelTreeNodes = new LinkedList<>();
            while (!thisLevelTreeNodes.isEmpty()){
                TreeNode poll = thisLevelTreeNodes.poll();
                if (poll.left!=null) nextLevelTreeNodes.offer(poll.left);
                if (poll.right!=null) nextLevelTreeNodes.offer(poll.right);
            }
            if (nextLevelTreeNodes.isEmpty()){
                break;
            } else {
                thisLevelTreeNodes = nextLevelTreeNodes;
                retList.add(thisLevelTreeNodes.stream().mapToInt(o -> o.val).max().orElse(0));
            }
        }
        return retList;
    }


    public int[] findFrequentTreeSum(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        statisticAllTreeSum(list,root);
        HashMap<Integer, Long> collect = list.stream()
                .collect(Collectors.groupingBy(o -> o, HashMap::new, Collectors.counting()));
        Long maxAppearTimes = collect.values().stream().max(Long::compareTo).orElse(0L);
        return collect.entrySet().stream().filter(o -> Objects.equals(o.getValue(), maxAppearTimes))
                .mapToInt(o -> o.getKey()).toArray();
    }
    public void statisticAllTreeSum( List<Integer> records , TreeNode root){
        if (root == null){
        } else {
            records.add(calSubTreeSum(root));
            statisticAllTreeSum(records,root.left);
            statisticAllTreeSum(records,root.right);
        }
    }

    public int calSubTreeSum(TreeNode root){
        if (root == null){
            return 0;
         } else {
            return root.val + calSubTreeSum(root.left) + calSubTreeSum(root.right);
        }
    }

    public List<TreeNode> generateTrees(int n){
        /* 生成n个节点, 节点值从1~n互不相同的二叉搜索树 */
        /* n = 3   1-2(r)-3  1(r)-2-3  123(r)*/
        return generateTreesBeginToEnd(1,n);
    }

    private HashMap<Integer, Integer> post_map = new HashMap<>();

    public TreeNode buildTree( int[] inorder , int[] postorder) {
        if (postorder.length == inorder.length){
            /* init */
            for (int i = 0; i < postorder.length; i++) {
                post_map.put(postorder[i],i);
            }
        }
        if (inorder.length == 0){
            return null;
        }
        if (inorder.length ==1){
            return new TreeNode(inorder[0]);
        }
        /* 中序遍历的所有值 在 后续遍历的最后一个 为当前树的根节点 */
        int minest = -1;

        List<Integer> inOderList = Arrays.stream(inorder).boxed().collect(Collectors.toList());
        for (int i : inorder) {
            int index = post_map.get(i);
            /* 得到最小索引 */
            minest = Math.max(minest,index);
        }
        Integer rootVal = postorder[minest];
        TreeNode root = new TreeNode(rootVal);
        int indexInOrder = inOderList.indexOf(rootVal);
        /* [from,to) */
        TreeNode leftNode = buildTree( Arrays.copyOfRange(inorder, 0, indexInOrder),postorder);
        TreeNode rightNode = buildTree( Arrays.copyOfRange(inorder, indexInOrder+1, inorder.length),postorder);
        root.left = leftNode;
        root.right = rightNode;
        return root;

    }

    public List<TreeNode> generateTreesBeginToEnd(int begin,int end){
        /* [begin,end] */
        if (begin > end){
            return null;
        }
        if (begin == end){
            /* 假设从2->2 构造树 构造一个单节点树 */
            TreeNode tree = new TreeNode(begin);
            return Stream.of(tree).collect(Collectors.toList());
        }
        List<TreeNode> genTrees = new ArrayList<>();
        for (int i = begin; i <= end ; i++) {
            /* 1 作为 root 则左边为 [1,0] 2->[1,1] .... 8->[1,7]*/
            List<TreeNode> leftTrees  = generateTreesBeginToEnd(begin, i-1);
            /* 1-> [2,8] ,...7->[8,8],8->[9,8]*/
            List<TreeNode> rightTrees = generateTreesBeginToEnd(i+1,end);
            if (leftTrees == null && rightTrees == null){
                TreeNode tree = new TreeNode(i);
                return Stream.of(tree).collect(Collectors.toList());
            } else if ( leftTrees == null && rightTrees!=null){

                    for (TreeNode rightTree : rightTrees) {
                        TreeNode tree = new TreeNode(i);
                        tree.right = rightTree;
                        genTrees.add(tree);
                    }

            }  else if (rightTrees ==null && leftTrees!=null){
                for (TreeNode leftTree : leftTrees) {
                    TreeNode tree = new TreeNode(i);
                    tree.left = leftTree;
                    genTrees.add(tree);
                }
            } else {
                for (TreeNode leftTree : leftTrees) {
                    for (TreeNode rightTree : rightTrees) {
                        TreeNode tree = new TreeNode(i);
                        tree.left = leftTree;
                        tree.right = rightTree;
                        genTrees.add(tree);
                    }
                }
            }
        }
        return genTrees;
    }











}

class TreeNodeTest{

    @Test
    public void genTreeNodes(){
        TreeNode treeNode = new TreeNode();
        TreeNode byLevelTraversal = TreeNode.getByLevelTraversal(new Integer[]{1,2,3,4,null,5,6,null,null,7},0);
//        TreeNode byLevelTraversal = TreeNode.getByLevelTraversal(new Integer[]{-10}, 0);
        ;
        System.out.println(treeNode.findBottomLeftValue( byLevelTraversal ));
//        TreeNode.hasPathSum(byLevelTraversal,0);
//        System.out.println(treeNode.isBalanced(byLevelTraversal));
//        TreeNode.preOrderTraversalTree(byLevelTraversal);
    }

}




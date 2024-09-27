package resolve;

import org.junit.jupiter.api.Test;

public class MedianOfTwoSortedArrays_4 {

    @Test
    public void test(){
        System.out.println(findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}));
//        System.out.println(findKthMinistNum(new int[]{1, 2}, new int[]{3, 4},4,0,1,0,1));

    }


    /**
     * 输入：nums1 = [1,2], nums2 = [3,4]
     * 输出：2.50000
     * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length1 = nums1.length;
        int length2 = nums2.length;
        // 找到中位数 如果两个数组的长度和为偶数 则找两个数字
        int idx_target = (  length1 + length2) /2 ;
        if ((length1 + length2) %2 == 0){
            return(
                    (double) findKthMinistNum(nums1,nums2,idx_target,0,nums1.length-1,0,nums2.length-1)+
                            (double) findKthMinistNum(nums1,nums2,idx_target+1,0,nums1.length-1,0,nums2.length-1)) /(double) 2
                    ;
        } else {
            return (double) findKthMinistNum(nums1,nums2,idx_target,0,nums1.length-1,0,nums2.length-1);
        }
    }
    // start & end 都是闭闭数组
    public  int findKthMinistNum( int[] nums1, int[] nums2 , int kth , int start1, int end1 , int start2 , int end2){
        //nums1 num2 都是增量数组
        // nums1 的第  k/2 + 1
        // todo 数组越界问题
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;
        // 去掉 k/2 个 如果一个数组的长度为空
        if (len1 <1 || len2<1) {
            // 如果一个数组的长度为空
            return len1>=1? nums1[start1+kth-1]:nums2[start2+kth-1];
        }

        if (kth == 1){
            return Math.min(nums1[start1],nums2[start2]);
        }
        //如果有一个数组的长度不够扣减kth/2 个 那就比较 短数组尾部 和 长数组的中部
        int i1 = nums1[ Math.min(start1 + kth / 2 - 1 , end1) ];
        int i2 = nums2[ Math.min(start2 + kth / 2 - 1 , end2) ];
        if (i1 <= i2){
            //将i1 的前k/2个去掉 如果kth =2 那就去掉1个
            // 如果数组的长度小于k/2个 那就尽可能去
            if (len1 < kth/2 ){
                return findKthMinistNum(nums1,nums2,kth - (len1)  , end1+1 ,end1,start2,end2 );
            } else {
                return findKthMinistNum(nums1,nums2,kth - (kth/2)  , start1 + kth/2 ,end1,start2,end2 );
            }

        } else {
            if (len2<kth/2){
                return findKthMinistNum(nums1,nums2,kth - (len2) , start1,end1,end2+1,end2);
            } else {
                return findKthMinistNum(nums1,nums2,kth - (kth/2) , start1,end1,start2+kth/2,end2);
            }

        }
    }



}

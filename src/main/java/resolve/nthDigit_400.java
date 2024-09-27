package resolve;

import java.util.Arrays;
import java.util.HashMap;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class nthDigit_400 {
    //  [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...]

    @ParameterizedTest
    @ValueSource(ints = {999999999})
    public void test( int nums){
        System.out.println(findNthDigit(nums));
    }

    public int findNthDigit(int n) {
        if ( n < 10){
            return n;
        }

        // 1 * 9 * 10^0
        // 2 * 9 * 10^1  1
        // 100~999 3位数字 (共计 3* 900 = 2700 个数字  )
        // 1000~9999 4位数字 (共计9000*4 = 36000 个数字 )
        // 5 * 9 * 10^4 = 4500000
        // 6 * 9 * 10^5
        //...
        // 8 * 9 * 10^7 7.2亿
        // 9 * 9 * 10^8 81亿
        // 10 * 9 * 10^9 90亿
        // n< 21 * 10^9
        //              1 , 2    , 3     , 4
        int[] count = { 9 , 2*90 , 3*900 , 4*9000,5*90000,6*900000,7*9000000,8*90000000};
        int[] interval = new int[8];

        interval[0] = 9;
        for (int i = 1; i < interval.length; i++) {
            interval[i] = interval[i-1]+ count[i];
        }
        System.out.println(Arrays.toString(interval));
        if (n > interval[7]){
            //如果大于8亿 -> 说明数字是9位的
            int n9 = n - interval[7];
            //这是对应的数字
            String targetNum  = String.valueOf(100000000 +  (n9-1)/ 9);
            return targetNum.charAt((n9-1)%9) - '0';
        } else {
            for (int i = interval.length - 1; i >= 0; i--) {
                if (n>interval[i]){
                    // 在这个区间内
                    int x_num = i+1;
                    int nx = n - interval[i];
                    String targetNum = String.valueOf( Double.valueOf(Math.pow(10,i+1)).intValue() + (nx-1)/(x_num+1));
                    return targetNum.charAt((nx-1)%(x_num+1) ) - '0';

                }
            }
        }
        return 0;
    }

}

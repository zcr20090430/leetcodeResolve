package resolve;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class IntegerToRoman_12 {


//    @Test
    @ParameterizedTest
    @ValueSource(ints = { 3749 ,58,1994})
    public void test(int i){
        System.out.println(intToRoman(i));
    }


    @ParameterizedTest
    @ValueSource(strings = { "MCMXCIV" ,"LVIII","III"})
    public void test2(String s){
        System.out.println(romanToInt(s));
    }

    /**
     * 罗马数字是通过添加从最高到最低的小数位值的转换而形成的。将小数位值转换为罗马数字有以下规则：
     *
     * 如果该值不是以 4 或 9 开头，请选择可以从输入中减去的最大值的符号，将该符号附加到结果，减去其值，然后将其余部分转换为罗马数字。
     * 如果该值以 4 或 9 开头，使用 减法形式，表示从以下符号中减去一个符号，例如 4 是 5 (V) 减 1 (I): IV ，9 是 10 (X) 减 1 (I)：IX。仅使用以下减法形式：4 (IV)，9 (IX)，40 (XL)，90 (XC)，400 (CD) 和 900 (CM)。
     * 只有 10 的次方（I, X, C, M）最多可以连续附加 3 次以代表 10 的倍数。你不能多次附加 5 (V)，50 (L) 或 500 (D)。如果需要将符号附加4次，请使用 减法形式。
     * 输入：num = 1994
     * 输出："MCMXCIV"
     * 输入：num = 58
     * 输出："LVIII"
     */


    public String intToRoman(int num) {
        /* 输入：num = 3749
           输出： "MMMDCCXLIX"
           */
        HashMap<Integer,String> map = new HashMap<>();
        map.put(1,"I");
        map.put(4,"IV");
        map.put(5,"V");
        map.put(9,"IX");
        map.put(10,"X");
        map.put(40,"XL");
        map.put(50,"L");
        map.put(90,"XC");
        map.put(100,"C");
        map.put(400,"CD");
        map.put(500,"D");
        map.put(900,"CM");
        map.put(1000,"M");

        StringBuilder res = new StringBuilder();
        List<Integer> collect = map.keySet().stream().sorted((o1, o2) -> {
            return o2 - o1;
        }).collect(Collectors.toList());
        for (int i = 0; i < collect.size(); i++) {
            while ( num >= collect.get(i) ){
                res.append( map.get(collect.get(i)) );
                num -=  collect.get(i);
            }
        }
        return res.toString();
    }

    public int romanToInt(String s) {

        HashMap<String,Integer> map = new HashMap<>();
        map.put("I",1);
        map.put("IV",4);
        map.put("V",5);
        map.put("IX",9);
        map.put("X",10);
        map.put("XL",40);
        map.put("L",50);
        map.put("XC",90);
        map.put("C",100);
        map.put("CD",400);
        map.put("D",500);
        map.put("CM",900);
        map.put("M",1000);
        char[] chars = s.toCharArray();
        int length = chars.length;
        int idx = 0;
        int res = 0;
        while ( idx <= length -1 ){
            StringBuilder candidate = new StringBuilder();
            if (idx +1 <= length-1){
                String length_2 = candidate.append(chars[idx]).append(chars[idx + 1]).toString();
                if (map.containsKey(length_2)){
                    res+= map.get(length_2);
                    idx++;
                    idx++;
                } else {
                    res+= map.get(String.valueOf(chars[idx]));
                    idx++;
                }
            } else {
                res+= map.get(String.valueOf(chars[idx]));
                idx++;
            }
        }
        return res;
    }

}

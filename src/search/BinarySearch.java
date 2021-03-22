package search;

import linear.Array;
import utils.ArrayUtils;

/**
 * 二分查找法
 * 用于已排序的集合中，查找指定数据，取中间位置元素和数据比较，相等则找到，小则递归右边，大则递归左边；
 * O(logn)
 */
public class BinarySearch {

    public static <E extends Comparable> int solution(Array array, E value){
        return binary(array,value,0,array.getSize()-1);
    }

    private static <E extends Comparable> int binary(Array array, E value, int l, int r) {
        System.out.println( "...l:"+ l + "...r:"+r);
        //递归最小单元
        if(l > r){
            return -1;
        }
        int mid = l+(r-l)/2;
        System.out.println("mid:" + mid);
        if (value.compareTo(array.get(mid)) > 0){
            //mid位置未命中，右半边mid+1处理
            return binary(array,value,mid+1,r);
        }else if(value.compareTo(array.get(mid)) < 0){
            //mid位置未命中，左半边mid-1处理
            return binary(array,value,l,mid-1);
        }else{
            return mid;
        }
    }

    public static void main(String[] args) {
        Array array = ArrayUtils.genOrderIntArray(11);
        System.out.println(array.toString());
        long start = System.currentTimeMillis();
        System.out.println(solution(array,0));
        long end = System.currentTimeMillis();
        System.out.println((end-start));
    }
}

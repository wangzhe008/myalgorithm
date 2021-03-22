package comparesort;

import linear.Array;
import utils.ArrayUtils;

/**
 * 选择排序
 * 每次循环找到最小的元素，若原地排序则每次循环将最小值从头位置开始替换即可，每轮循环次数-1
 * 循环不变量：a[0~i)区间是已排序的且是最终位置，每次循环从a[i~n]区间内查找最小值，需要两重循环
 * 复杂度是O(n^2)
 */
public class SelectSort {

    public static <E extends  Comparable> void solution(Array<E> array){

        int k = 0;
        for (int i = 0; i < array.getSize(); i++) {
            k = i;
            for (int j = i+1; j < array.getSize(); j++) {
                if(array.get(k).compareTo(array.get(j)) > 0){
                    k = j;
                }
            }
            ArrayUtils.swap(array, k, i);
        }
    }

    public static void main(String[] args) {
        Array array = ArrayUtils.genRandomIntArray(10, 50);
        System.out.println(array.toString());
        solution(array);
        System.out.println(array.toString());
    }

}

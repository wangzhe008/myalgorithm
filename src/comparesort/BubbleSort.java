package comparesort;

import linear.Array;
import utils.ArrayUtils;

/**
 * 冒泡排序
 * 每次轮询相邻元素比较，逆序则替换，轮询完后，最后一位是最大，下一次轮询排除最后一位进行从前到后比较
 * 循环不变量:a[l,r-i]乱序，a[r-i+1,r]已排序
 * O(n^2)
 */
public class BubbleSort {

    public static <E extends Comparable> void solution(Array<E> array){

        E value = null;
        boolean isSort = true;//某一次循环没有任何替换，则说明后面全部已排序，则不需要再遍历
        for (int i = 0; i < array.getSize(); i++) {
            isSort = true;
            for (int j = 0; j < array.getSize() - i - 1; j++) {
                //两两比较，逆序则替换，每次轮询a[0,n-i)未排序，a[n-i,n)已排序
                if(array.get(j).compareTo(array.get(j+1)) > 0){
                    value = array.get(j);
                    array.set(j,array.get(j+1));
                    array.set(j+1, value);
                    isSort = false;
                }
            }
            if (isSort){
                break;
            }
        }
    }

    public static void main(String[] args) {
        Array array = ArrayUtils.genOrderIntArray(10);
        System.out.println(array);
        long start = System.currentTimeMillis();
        solution(array);
        long end = System.currentTimeMillis();
        System.out.println(array);
        System.out.println("OrderArray:"+(end-start));

        Array array1 = ArrayUtils.genRandomIntArray(10,20);
        System.out.println(array1);
        start = System.currentTimeMillis();
        solution(array1);
        end = System.currentTimeMillis();
        System.out.println(array1);
        System.out.println("RandomArray:"+(end-start));
    }
}

package comparesort;

import linear.Array;
import utils.ArrayUtils;

/**
 * 希尔排序
 *  *  * 每轮循环将下标相隔n/(2^i)的元素作为一分组，进行插入排序，直到相差为1作为最后一轮
 *  *  * 间隔，可以称为步长，可以自己定义
 *  *  * 属于随机排序，期望时间复杂度：
 */
public class Shellsort {

    public static <E extends Comparable> void solution(Array<E> array){

        int sz = array.getSize()>>1;
        E value = null;
        int i = 0;
        // 步长设计
        while (sz>=1) {
            // 按步长分组
            for (int j = 0; j <= sz; j+=sz) {
                // 对每组数组进行插入排序
                for (int k = j + sz; k+sz <= array.getSize(); k+=sz) {
                    value = array.get(k);
                    //每个元素与前面比较，找到第一个比该元素小的下标，则退出当前元素比较
                    for (i = k; i-sz >= 0 ; i-=sz) {
                        if (array.get(i-sz).compareTo(value) > 0 ){
                            array.set(i, array.get(i-sz));
                        }else {
                            break;
                        }
                    }
                    // 当前面有必当前元素大的数据，需要做互换
                    if(k != i) {
                        array.set(i, value);
                    }
                }
            }
            sz = sz >> 1;
        }
//
//        int h = array.getSize()/ 2;
//        while(h >= 1){
//
//            for(int start = 0; start < h; start ++){
//
//                // 对 data[start, start + h, start + 2h, start + 3h ...], 进行插入排序
//                for(int i = start + h; i < array.getSize(); i += h){
//                    E t = array.get(i);
//                    int j;
//                    for(j = i; j - h >= 0 && t.compareTo(array.get(j - h)) < 0; j -= h)
//                        array.set(j,array.get(j - h));
//                    array.set(j, t);
//                }
//            }
//            h /= 2;
//        }
    }

    public static void main(String[] args) {
        Array array = ArrayUtils.genRandomIntArray(10, 10);
        System.out.println(array);
        long start = System.currentTimeMillis();
        solution(array);
        long end = System.currentTimeMillis();
        System.out.println((end - start));
        System.out.println(array);
        ArrayUtils.checkOrder(array);
    }
}

package comparesort;

import linear.Array;
import utils.ArrayUtils;

/**
 *
 *  类似扑克牌排序，从左到右，将每个元素和前面的元素比较，并插入到第一个比自己小的元素后面，区间元素后移一位(每次检查后直接赋值到后面索引即可)
 *  循环不变量：a[0~i)区间是已排序但不一定是最终位置
 *  复杂度是O(n^2)
 */
public class InsertSort {

    public static <E extends Comparable> void solution(Array<E> array){

        E value = null;
        int k = 0;
        for (int i = 1; i < array.getSize(); i++) {
            k = i;
            for (int j = i-1; j >= 0; j--) {
                //比较自己前面所有元素，依次记录大于自己的元素下标
                if (array.get(i).compareTo(array.get(j)) <= 0){
                    k = j;
                }else {
                    //第一个小于自己的元素则不再处理
                    break;
                }
            }
            if (k!=i){
                //将自己插入到最后一个比自己大的元素前面，需要该元素到自己期间元素后移
                System.out.println(k+"----"+i);
                value = array.get(i);
                for (int j = i; j > k; j--) {
                    array.set(j,array.get(j-1));
                }
                //将自己插入到最后一个比自己大的元素位置
                array.set(k, value);
            }
        }
    }

    public static void main(String[] args) {
        Array array = ArrayUtils.genRandomIntArray(10, 10);
        System.out.println(array);
        solution(array);
        System.out.println(array);
    }
}

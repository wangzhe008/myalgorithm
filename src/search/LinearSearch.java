package search;

import linear.Array;
import utils.ArrayUtils;

/**
 * 线性查找法
 * 数组或链表等线性结构数据
 * O(n)
 */
public class LinearSearch {

    /**
     * 数组
     * @param array
     * @param value
     * @param <E>
     * @return
     */
    public static  <E> int solutionArray(Array array, E value){
        if (array == null || !array.isEmpty()){
            return -1;
        }

        for (int i = 0; i < array.getSize(); i++) {
            if (ArrayUtils.equals(value, array.get(i))){
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Array array = ArrayUtils.genOrderIntArray(10);

        System.out.println(array.toString());

        System.out.println(solutionArray(array, 0));
    }
}

package utils;

import linear.Array;

import java.util.Random;

public final class ArrayUtils {

    private ArrayUtils(){

    }

    static Random random = new Random();

    /**
     * 无序整型数组
     * @param size 数组大小
     * @param bond 数据边界
     * @return
     */
    public static Array<Integer> genRandomIntArray(int size, int bond){

        Array array = new Array<Integer>(size);

        for (int i = 0; i < size; i++) {
            array.insert(random.nextInt(bond));
        }

        return array;
    }

    /**
     * 有序整型数组
     * @param size
     * @return
     */
    public static Array<Integer> genOrderIntArray(int size){
        Array array = new Array<Integer>(size);

        for (int i = 0; i < size; i++) {
            array.insert(i);
        }
        return array;
    }

    /**
     * 比较两个对象是否相等
     * @param o1
     * @param o2
     * @param <E>
     * @return
     */
    public static <E> boolean equals(E o1,E o2){
        if (o1 == null && o2 == null){
            return true;
        }
        if (o1!=null && o1.equals(o2)){
            return true;
        }
        return false;
    }

    /**
     * 两下标值交换
     * @param array
     * @param k
     * @param i
     * @param <E>
     */
    public static <E extends Comparable> void swap(Array<E> array, int k, int i) {
        E value = array.get(k);
        array.set(k, array.get(i));
        array.set(i, value);
    }

    /**
     * 复制数组
     * @param src 源
     * @param srcPos 源起始下标
     * @param dest 目标
     * @param destPos 目标起始下标
     * @param length 复制元素个数
     * @param <E> 泛型，必须支持比较
     */
    public static <E extends Comparable> void copy(Array<E> src,  int  srcPos,
                                                   Array<E> dest, int destPos,
                                                   int length){

        for (int i = 0; i < length; i++) {
//            System.out.println((destPos+i)+"-----"+(srcPos+i));
            dest.set(destPos+i,src.get(srcPos+i));
        }

    }

    public static <E extends Comparable> void copyCompare(Array<E> src,  int  srcPos,
                                                          Comparable[] dest, int destPos,
                                                   int length){
        for (int i = 0; i < length; i++) {
//            System.out.println((destPos+i)+"-----"+(srcPos+i));
            dest[destPos+i] = src.get(srcPos+i);
        }

    }

    /**
     * 验证数组是否已排序
     * @param array
     * @param <E>
     */
    public static <E extends Comparable> void checkOrder(Array<E> array){
        for (int i = 0; i < array.getSize()-1; i++) {
            if (array.get(i).compareTo(array.get(i+1)) > 0){
                throw new RuntimeException("数组未完成排序");
            }
        }
        System.out.println("数组已排序");
    }
}

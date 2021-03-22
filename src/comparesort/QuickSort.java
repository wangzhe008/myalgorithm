package comparesort;

import linear.Array;
import utils.ArrayUtils;

import java.util.Random;

/**
 * 快速排序
 * 随机选择区间内数据，查找区间内其准确位置，保证其左半边比该数据小，右半边比该数据大
 * 递归基本问题：区间不可再分割
 * 循环不变量：a[l+1,i]<a[l]<=a[i+1,r] ----> a[l,i-1]<a[i]<=a[i+1,l]
 * 平均O(nlogn)
 */
public class QuickSort {

    static Random random = new Random();

    public static <E extends Comparable> void solution(Array<E> array){

        quickOne(array,0,array.getSize()-1);
    }

    /**
     * 单路排序
     * @param array
     * @param l
     * @param r
     * @param <E>
     */
    private static <E extends Comparable> void quickOne(Array<E> array, int l, int r) {
        if (l >= r){
            return;
        }
        int index = quick1(array,l,r);
        quickOne(array,l, index-1);
        quickOne(array,index+1, r);
    }

    /**
     * 单路排序逻辑
     * 数据越分散，分片越均匀，递归次数越少，速度越快，相反亦然
     * 使用一个游标k将数组划分为两部分，左边小l+1~k，右边未排序k+1~r
     * @param array
     * @param l
     * @param r
     * @param <E>
     * @return
     */
    private static <E extends Comparable> int quick1(Array<E> array, int l, int r) {
        // 随机获取下标，并与区间其实位置替换
        int index = random.nextInt(r-l) + l;
        ArrayUtils.swap(array, l, index);

        int k = l;
        // 排序过程中，循环不变量：l+1~k 小于，k+1~r乱序
        for (int i = l+1; i <= r; i++) {
            if (array.get(l).compareTo(array.get(i)) > 0){
                k++;
                ArrayUtils.swap(array, k, i);
            }
        }
        // 排序后，l~k-1 小于，k+1~r乱序，需要l和k替换
        ArrayUtils.swap(array, k, l);
        return k;
    }

    public static <E extends Comparable> void solution2(Array<E> array){
        quickTwo(array,0,array.getSize()-1);
    }

    /**
     * 双路排序
     *
     * @param array
     * @param l
     * @param r
     * @param <E>
     */
    private static <E extends Comparable> void quickTwo(Array<E> array, int l, int r) {
        if (l >= r){
            return;
        }
        int index = quick2(array,l,r);
        quickTwo(array,l, index-1);
        quickTwo(array,index+1, r);
    }

    /**
     * 双路排序
     * 支持数据不分散情况下快速排序
     * 使用两个游标k，j将数组划分为三部分，两坐标相向移动，左边小l+1~k-1，中间未排序k~j，右边大j-1~r，量指针碰撞则结束
     * 注，对于相等的情况，需两坐标元素和数据都相等时，同时移动，减少数据倾斜，提高效率
     * @param array
     * @param l
     * @param r
     * @param <E>
     * @return
     */
    private static <E extends Comparable> int quick2(Array<E> array, int l, int r) {
        // 随机获取下标，并与区间其实位置替换
        int index = random.nextInt(r-l+1) + l;
        ArrayUtils.swap(array, l, index);

        int k = l+1;
        int j = r;
        // 指针相碰则结束排序
        while (true) {
            // 前坐标小，则后移，直到大
            while (k<=j && array.get(l).compareTo(array.get(k)) > 0){
                k++;
            }
            // 后坐标大，则迁移，直到小
            while (k <= j && array.get(l).compareTo(array.get(j)) < 0){
                j--;
            }
            //前后坐标碰撞，则结束
            if (k > j) {
                break;
            }
            //前坐标大，后坐标小，则互换，并做移动
            //前后坐标检查时，不能使用等号，在此处处理相等的情况，相等时两坐标都往中间靠拢，降低左右数据倾斜，进而增加递归次数
            ArrayUtils.swap(array, k, j);
            k++;
            j--;
        }
        ArrayUtils.swap(array, l, j);

//        System.out.println("l:"+l+"-------r:"+r + "--------index:" +index+"-------k:" + k + "-------j:"+j);
        return j;
    }

    public static void main(String[] args) {
//
        Array array = ArrayUtils.genRandomIntArray(10000, 1);
//        System.out.println(array);
        long start = System.currentTimeMillis();
        solution(array);
        long end = System.currentTimeMillis();
        System.out.println((end - start));
//        System.out.println(array);
        ArrayUtils.checkOrder(array);

        Array array1 = ArrayUtils.genRandomIntArray(10000, 1);
//        System.out.println(array1);
        long start1 = System.currentTimeMillis();
        solution2(array1);
        long end1 = System.currentTimeMillis();
        System.out.println((end1 - start1));
//        System.out.println(array1);
        ArrayUtils.checkOrder(array1);
    }
}

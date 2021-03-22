package comparesort;

import linear.Array;
import utils.ArrayUtils;

import java.util.ArrayList;

/**
 * 归并排序
 * (1)上往下分解，适用于递归，将数据集合平分为两部分，每部分再平分为两部分，一层层分解，直到不可再分解，再一层层往上归并，归并逻辑里对每层分出的两部分进行排序
 * 复杂度是O(logn)，有序数组会降级为O(n)
 * (2)先分解再归并，适用于循环，每次循环按照2的N次幂将数组分为N分，每份分为两部分进行合并排序，循环不变量a[i~i+sz-1]和a[i+sz~min(i+sz+sz-1,n-1))已排序，并进行合并排序
 */
public class MergeSort {

    public static <E extends Comparable> void solution(Array<E> array){

//        Array<E> temp = new Array(array,array.getSize());
        Comparable[] temp = new Comparable[array.getSize()];
        System.out.println("temp::::" + temp);
        sort(array,0,array.getSize()-1,temp);
    }

    /**
     * 上往下，已一层层分解，再合并
     * @param array
     * @param l
     * @param r
     * @param temp
     * @param <E>
     */
    private static <E extends Comparable> void sort(Array<E> array, int l, int r, Comparable[] temp) {

        //最小递归
        if (l >= r){
            return;
        }

        int mid = l + (r - l) / 2;

        sort(array, l, mid, temp);
        sort(array, mid+1, r, temp);
        merge(array,l, mid, r,temp);
    }

    private static <E extends Comparable> void merge(Array<E> array, int l, int mid, int r,Comparable[] temp) {
        //临时空间存在合并前顺序，用于比较和取值
        ArrayUtils.copyCompare(array, l, temp, l, r-l+1);
        int j = l;
        int k = mid+1;
        for (int i = 0; i <= r-l; i++) {
            if (j>mid){//左边排完，则只用右边
                array.set(l+i, (E)(temp[k]));
                k++;
            }else if (k>r){//右边排完，则只用左边
                array.set(l+i, (E)(temp[j]));
                j++;
            }else //左边大，则用右边，右边进位，必须使用合并前数组中数据进行比较
                if (temp[j].compareTo(temp[k])>=0) {
                    array.set(l + i, (E)(temp[k]));
                    k++;
            }else{//右边大，则用左边，左边进位
                array.set(l+i, (E)(temp[j]));
                j++;
            }
        }

//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append("-----");
//        for (int i = l;i<=r;i++){
//            stringBuffer.append(array.get(i)).append("-");
//        }
//        stringBuffer.append("\n");
//        System.out.println(stringBuffer);
    }

    public static void main (String[]args){
        Array array = ArrayUtils.genRandomIntArray(1000000, 100000);
        System.out.println(array);
        solution(array);
        System.out.println(array);
        ArrayUtils.checkOrder(array);
    }

}

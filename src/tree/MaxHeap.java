package tree;

import linear.Array;
import utils.ArrayUtils;

/**
 * 堆-完全二叉树
 * 最大堆，堆顶最大值，左右都小于根
 * 下标从0开始，第i个节点及其左右子节点下标(i-1)/2、2i+1、2i+2
 * @author
 */
public class MaxHeap<E extends Comparable> {

    private Array<E> array;

    public MaxHeap(){
        array = new Array<>();
    }

    public MaxHeap(int length){
        array = new Array<>(length);
    }

    /**
     * 整个集合转换为堆，从最后一个节点的父节点开始左下沉，直到根，即heapify算法
     * @param arr
     */
    public MaxHeap(Array arr){
        array = new Array(arr,arr.getSize());
        heapify();
    }

    private void heapify() {
        if (array.getSize() <= 1){
            return;
        }
        for (int i = array.getSize()-1; i >= 0 ; i-=2) {
            int p = 0;
            int j = i;
            // 当前节点不是根节点，则查找父节点做下沉
            while ((p = parent(j))>=0 && j > 0){
                down(p);
                j = p;
            }
        }

    }

    public int getSize(){
        return array.getSize();
    }

    public boolean isEmpty(){
        return array.isEmpty();
    }

    /**
     * 添加
     * 从左到右添加元素，在做上浮处理
     * @param value
     */
    public void add(E value){
        int i = array.getSize();
        array.insert(value);
        up(i);
    }

    /**
     * 上浮
     * @param i
     */
    private void up(int i) {
        int p = 0;
        // 从下往上一层层与父节点比较，当前元素大则替换，小则退出
        while ((p = parent(i)) >= 0){
            // 当前节点是根时，退出
            if (i == 0){
                return;
            }
            if (array.get(p).compareTo(array.get(i)) < 0){
                ArrayUtils.swap(array, i, p);
                i = p;
            }else {
                return;
            }
        }
    }

    private int parent(int i){
        return (i-1)/2;
    }

    private int leftChild(int i){
        return 2*i+1;
    }

    private int rightChild(int i){
        return 2*i+2;
    }

    public E findMax(){
        return array.get(0);
    }

    /**
     * 取数据
     * 只能从根开始取数据，使用最后叶子节点替换根，再做下沉处理
     * @return
     */
    public E extractMax(){
        E max = array.get(0);
        ArrayUtils.swap(array, 0, array.getSize()-1);
        array.remove(array.getSize()-1);
        down(0);
        return max;
    }

    /**
     * 下沉
     * @param i
     */
    private void down(int i) {
        while (true){
            int l = leftChild(i);
            int r = rightChild(i);
            System.out.println("down i--:"+i+"--l:"+l+"--r:"+r);
            // 左右子都超限，说明i是叶子，结束
            if (l >= array.getSize() && r >= array.getSize()){
                return;
            }
            // 左子超限，右子大则下沉交换，右子小则结束
            if (l >= array.getSize()){
                if (array.get(r).compareTo(array.get(i)) > 0){
                    ArrayUtils.swap(array, r, i);
                    i = r;
                }else{
                    return;
                }
            }else
            // 右子超限，左子大则下沉交换，左子小则结束
            if (r >= array.getSize()){
                if (array.get(l).compareTo(array.get(i)) > 0){
                    ArrayUtils.swap(array, l, i);
                    i = l;
                }else{
                    return;
                }
            }else
            // 右子比左子大，比较子右，右子大则下沉交换，右子小则结束
            if (array.get(r).compareTo(array.get(l)) > 0){
                if (array.get(r).compareTo(array.get(i)) > 0){
                    ArrayUtils.swap(array, r, i);
                    i = r;
                }else{
                    return;
                }
            }else{// 左子比右子大，与比较左子，左子大则下沉交换，左子小则结束
                if (array.get(l).compareTo(array.get(i)) > 0){
                    ArrayUtils.swap(array, l, i);
                    i = l;
                }else{
                    return;
                }
            }
        }
    }

    /**
     * 替换根，再做下沉
     * @param e
     * @return
     */
    public E replace(E e){
        if (e == null){
            return null;
        }
        E value = array.get(0);
        array.set(0, e);
        down(0);
        return value;
    }

    @Override
    public String toString() {
        return array.toString();
    }

    public static void main(String[] args) {
//        Array<Integer> array = ArrayUtils.genRandomIntArray(10,10);
        Array<Integer> array = ArrayUtils.genOrderIntArray(10);
        System.out.println(array);

//        MaxHeap<Integer> maxHeap = new MaxHeap<>(10);
////        for (int i = 0; i < array.getSize(); i++) {
////            maxHeap.add(array.get(i));
////        }
        MaxHeap<Integer> maxHeap = new MaxHeap<>(array);
        System.out.println(maxHeap);

        System.out.println("------findMax-------"+maxHeap.findMax());
        maxHeap.replace(6);
        System.out.println("------replace-findMax-------"+maxHeap.findMax());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-----extractMax----").append("\n");
        int size = maxHeap.getSize();
        for (int i = 0; i < size; i++) {
            stringBuilder.append("--").append(maxHeap.extractMax());
        }
        System.out.println(stringBuilder.toString());

    }
}

package linear;

import utils.ArrayUtils;

/**
 * 动态数组
 * @param <E>
 */
public class Array<E > {

    private E[] data ;

    private int size = 0;

    public Array(){
        data = (E[])(new Object[10]);
    }

    public Array(int length){
        if (length <= 0){
            throw new IllegalArgumentException("length <= 0");
        }
        data = (E[])(new Object[length]);
    }

    Array(E[] values){
        if (values != null || values.length<= 0){
            throw new IllegalArgumentException("values is null or empty");
        }
        for (int i = 0; i < values.length; i++) {
            data[i] = values[i];
            size ++;
        }
    }

    public Array(Array<E> array, int sz){
        data = (E[])(new Object[sz]);
        for (int i = 0; i < sz; i++) {
            data[i] = array.get(i);
            this.size++;
        }
    }

    /**
     * 获取元素个数
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * 获取数组长度
     * @return
     */
    public int getLength(){
        return data.length;
    }

    /**
     * 更新
     * @param value
     * @param index 更新索引
     */
    public void set(int index,E value){
        check(index);
        data[index] = value;
    }

    /**
     * 末尾插入数据
     * @param value
     */
    public void insert(E value){
        if (size >= data.length){
            resize();
        }
        data[size] = value;
        size++;
    }

    /**
     * 按照2倍扩容
     */
    private void resize() {
        E[] newData = (E[])(new Object[data.length*2]);
        System.arraycopy(data, 0, newData, 0, data.length);
        data = newData;
    }

    /**
     * 是否空数组
     * @return
     */
    public boolean isEmpty(){
        return getSize()>0?false:true;
    }

    /**
     * 读取任意位置数据
     * @param index
     * @return
     */
    public E get(int index){
        check(index);
        return data[index];
    }

    /**
     * 检查索引合法性
     * 索引不能大于等容量或大于当前位置
     */
    private void check(int index){
        if(size <=0 || index > size){
            System.out.println(String.format("index: %s ;data.length %s ;size: %s;",index,data.length,size));
            throw new IllegalArgumentException("Error: index >= length or > size");
        }
    }

    /**
     *
     * @param index
     */
    public void remove(int index){
        check(index);
        System.arraycopy(data, index+1, data, index, size-index-1);
        size --;
    }

    public void reverse(){
        E temp = null;
        for (int i = 0; i < size / 2; i++) {
            System.out.println("i:"+i+"....x:"+(size-i-1));
            temp = data[i];
            data[i] = data[size-i-1];
            data[size-i-1] = temp;
        }
    }

    public boolean contains(E value){
        if (value == null){
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (data[i].equals(value)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("length-").append(data.length).append(":");
        stringBuffer.append("size-").append(size).append("\n");
        for (int i = 0; i < size; i++) {
            stringBuffer.append("(").append(i).append(")").append(data[i]);
            if (i != size-1){
                stringBuffer.append(":");
            }
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        Array array = ArrayUtils.genOrderIntArray(10);
        System.out.println(array.toString());
//        array.remove(10);
//        System.out.println(array.toString());
//        array.set(10, 999);
//        System.out.println(array.toString());
        array.reverse();
        System.out.println(array.toString());
    }


}

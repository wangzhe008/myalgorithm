package tree;

import linear.Array;
import utils.ArrayUtils;
import utils.Merger;
import utils.MinMerger;

/**
 * 线段树，也称区间树
 * 平衡二叉树，叶子节点对应区间的各槽位，非叶子节点一层层往上聚集(最大、最小或和等)，直到根部，代表整个区间聚集(最大、最小或和等)
 * 具体实现，整个区间一层层平分拆分，直到左右区间表示一个槽位(左右区间相等)，再一层层往上聚集，具体使用聚集函数完成
 */
public class SegmentTree<E extends  Comparable> {

    class Node{
        E value;
        Node leftChild;
        Node rightChild;
        int left;
        int right;

        Node(E value,int left,int right){
            this.value = value;
            leftChild = null;
            rightChild = null;
            this.left = left;
            this.right = right;
        }
    }

    Array<E> array;
    Node root;
    Merger<E> merger;// 实现每层聚集逻辑

    public SegmentTree(Array array,Merger merger){
        this.array = array;
        this.root = null;
        this.merger = merger;

        // 生成线段树
        root = buildSegmentTree(root,0,array.getSize()-1);
    }

    private Node buildSegmentTree(Node node,int l,int r) {
        if (array == null || array.isEmpty()){
            throw new IllegalArgumentException("区间不能有空");
        }
        // 创建节点
        if (node == null){
            node = new Node(null, l, r);
        }
        // 递归元逻辑，左右边界相等，说明是叶子节点，存放数组中对应位置数据
        if (l == r){
            node.value = array.get(l);
            return node;
        }
        int mid = l+(r-l)/2;
        // 区间处理
        if (l < r) {
            node.leftChild = buildSegmentTree(node.leftChild, l, mid);
            node.rightChild = buildSegmentTree(node.rightChild, mid + 1, r);
            node.value = merger.merge(node.leftChild.value, node.rightChild.value);
        }else{
            System.out.println("l > r");
            return null;
        }
        return node;
    }

    public int getSize(){
        return array.getSize();
    }

    public E get(int i){
        return array.get(i);
    }

    /**
     * 查询某区间内聚集数据
     * @param l
     * @param r
     * @return
     */
    public E query(int l,int r){
        if (l < 0 || r < 0 || l > r || r >= array.getSize()){
            return null;
        }
        return query(root, 0,array.getSize()-1,l,r);
    }

    private E query(Node node, int l, int r, int indl, int indr) {
        if (l == indl && r == indr){
//            System.out.println("["+ l+","+ r+"]---indl:"+indl + "--indr:"+ indr+"---value:"+node.value);
            return node.value;
        }
        if (node == null){
//            System.out.println("["+ l+","+ r+"]---indl:"+indl + "--indr:"+ indr+"---value:"+ null);
            return null;
        }

        int mid = l + (r-l)/2;
        // 在右半边
        if (indl > mid){
//            System.out.println("["+ l+","+ r+"]---indl:"+indl + "--indr:"+ indr+"---value:"+ node.value);
            return query(node.rightChild, mid + 1, r, indl, indr);
        }
        // 在左半边
        if (indr < mid +1){
//            System.out.println("["+ l+","+ r+"]---indl:"+indl + "--indr:"+ indr+"---value:"+ node.value);
            return query(node.leftChild, l, mid, indl, indr);
        }

        // 左右都有，需要把查询区间按照mid拆开
        E leftValue = query(node.leftChild, l, mid, indl, mid);
        E rightValue = query(node.rightChild, mid+1, r, mid+1, indr);
//        System.out.println("["+ l+","+ r+"]---indl:"+indl + "--indr:"+ indr+"---leftValue:"+ leftValue+"--rightValue:"+rightValue);
        return merger.merge(leftValue, rightValue);
    }

    /**
     * 修改
     * 先修改数组，再修改树
     * @param index
     * @param value
     */
    public void set(int index,E value){
        if (value == null){
            return;
        }
        array.set(index, value);
        root = set(root, 0,array.getSize()-1,index,value);
    }

    private Node set(Node node, int l, int r ,int index,E value) {
        if (l == r){
            node.value = value;
//            System.out.println("["+ l+","+ r+"]---index:"+index+"---value:"+node.value);
            return node;
        }
        int mid = l + (r-l)/2;
        // 索引在左区间
        if (l <= index && index <= mid){
            node.leftChild = set(node.leftChild, l, mid, index, value);
            node.value = merger.merge(node.leftChild.value,node.rightChild.value);
        }else // 索引在右区间
            {
            node.rightChild = set(node.rightChild, mid+1, r, index, value);
            node.value = merger.merge(node.leftChild.value,node.rightChild.value);
        }
//        System.out.println("["+node.leftChild.left+","+node.right+"] left:"+node.leftChild.value+"---right:"+ node.rightChild.value+"----value:" + node.value);
        return node;
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        generateBSTString(root, 0, res);
        return res.toString();
    }

    // 生成以node为根节点，深度为depth的描述二叉树的字符串
    private void generateBSTString(Node node, int depth, StringBuilder res){
        if(node == null){
            res.append(generateDepthString(depth) + "null\n");
            return;
        }

//        System.out.println(depth + "---"+ node.value);
        res.append(generateDepthString(depth) + node.value + "[" + node.left +","+ node.right +"]\n");
        generateBSTString(node.leftChild, depth + 1, res);
        generateBSTString(node.rightChild, depth + 1, res);
    }

    private String generateDepthString(int depth){
        StringBuilder res = new StringBuilder();
        for(int i = 0 ; i < depth ; i ++) {
            res.append("--");
        }
        return res.toString();
    }

    public static void main(String[] args) {
        int size  = 10000000;
        int bond = 100000;
        long start = System.nanoTime();
        Array<Integer> array = ArrayUtils.genRandomIntArray(size, bond);
//        Array<Integer> array = ArrayUtils.genOrderIntArray(size);
        long end = System.nanoTime();
        System.out.println("创建"+size+"数组(纳秒):"+(end - start));
//        System.out.println(array);
        start = System.nanoTime();
        SegmentTree<Integer> segmentTree = new SegmentTree<>(array, new MinMerger());
        end = System.nanoTime();
        System.out.println("生成线段树(纳秒):"+(end - start));
//        System.out.println(segmentTree);

        start = System.nanoTime();
        segmentTree.set(500445, 2);
        end = System.nanoTime();
        System.out.println("修改数据(纳秒):"+(end - start));
//        System.out.println(array);
//        System.out.println(segmentTree);

        start = System.nanoTime();
        System.out.println(segmentTree.query(500000, 509988));
        end = System.nanoTime();
        System.out.println("查询区间数据(纳秒):"+(end - start));

//        System.out.println(array);
        start = System.nanoTime();
        System.out.println(segmentTree.query(500000, 509988));
        end = System.nanoTime();
        System.out.println("查询区间数据(纳秒):"+(end - start));
    }

}

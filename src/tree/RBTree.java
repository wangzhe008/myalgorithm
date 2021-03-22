package tree;

import linear.Array;
import utils.ArrayUtils;

public class RBTree<E extends Comparable<E>> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node{
        public E key;
        public Node left, right;
        public boolean color;

        public Node(E value){
            this.key = value;
            left = null;
            right = null;
            color = RED;
        }
    }

    private Node root;
    private int size;

    public RBTree(){
        root = null;
        size = 0;
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    // 判断节点node的颜色
    private boolean isRed(Node node){
        if(node == null) {
            return BLACK;
        }
        return node.color;
    }

    //   node                     x
    //  /   \     左旋转         /  \
    // T1   x   --------->   node   T3
    //     / \              /   \
    //    T2 T3            T1   T2
    private Node leftRotate(Node node){

        Node x = node.right;

        // 左旋转
        node.right = x.left;
        x.left = node;

        x.color = node.color;
        node.color = RED;

        return x;
    }

    //     node                   x
    //    /   \     右旋转       /  \
    //   x    T2   ------->   y   node
    //  / \                       /  \
    // y  T1                     T1  T2
    private Node rightRotate(Node node){

        Node x = node.left;

        // 右旋转
        node.left = x.right;
        x.right = node;

        x.color = node.color;
        node.color = RED;

        return x;
    }

    // 颜色翻转
    private void flipColors(Node node){

        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    // 向红黑树中添加新的元素(key, value)
    public void add(E value){
        root = add(root, value);
        root.color = BLACK; // 最终根节点为黑色节点
    }

    // 向以node为根的红黑树中插入元素(value)，递归算法
    // 返回插入新节点后红黑树的根
    private Node add(Node node, E value){

        if(node == null){
            size ++;
            return new Node(value); // 默认插入红色节点
        }

        if(value.compareTo(node.key) < 0) {
            node.left = add(node.left, value);
        }
        else if(value.compareTo(node.key) > 0) {
            node.right = add(node.right, value);
        }
        else {// value.compareTo(node.value) == 0
            node.key = value;
        }

        if (isRed(node.right) && !isRed(node.left)) {
            node = leftRotate(node);
        }

        if (isRed(node.left) && isRed(node.left.left)) {
            node = rightRotate(node);
        }

        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        return node;
    }

    // 返回以node为根节点的二分搜索树中，value所在的节点
    private Node getNode(Node node, E value){

        if(node == null) {
            return null;
        }

        if(value.equals(node.key)) {
            return node;
        }
        else if(value.compareTo(node.key) < 0) {
            return getNode(node.left, value);
        }
        else {// if(value.compareTo(node.value) > 0)
            return getNode(node.right, value);
        }
    }

    public boolean contains(E key){
        return getNode(root, key) != null;
    }

    // 返回以node为根的二分搜索树的最小值所在的节点
    private Node minimum(Node node){
        if(node.left == null) {
            return node;
        }
        return minimum(node.left);
    }

    // 删除掉以node为根的二分搜索树中的最小节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMin(Node node){

        if(node.left == null){
            Node rightNode = node.right;
            node.right = null;
            size --;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }

    // 从二分搜索树中删除键为key的节点
    public E remove(E value){

        Node node = getNode(root, value);
        if(node != null){
            root = remove(root, value);
            return node.key;
        }
        return null;
    }

    private Node remove(Node node, E value){

        if( node == null ) {
            return null;
        }

        if( value.compareTo(node.key) < 0 ){
            node.left = remove(node.left , value);
            return node;
        }
        else if(value.compareTo(node.key) > 0 ){
            node.right = remove(node.right, value);
            return node;
        }
        else{   // value.compareTo(node.value) == 0

            // 待删除节点左子树为空的情况
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size --;
                return rightNode;
            }

            // 待删除节点右子树为空的情况
            if(node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size --;
                return leftNode;
            }

            // 待删除节点左右子树均不为空的情况

            // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
            // 用这个节点顶替待删除节点的位置
            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.left;

            node.left = node.right = null;

            return successor;
        }
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
        res.append(generateDepthString(depth) + node.key +"["+ (node.color?"红":"黑") +"]\n");
        generateBSTString(node.left, depth + 1, res);
        generateBSTString(node.right, depth + 1, res);
    }

    private String generateDepthString(int depth){
        StringBuilder res = new StringBuilder();
        for(int i = 0 ; i < depth ; i ++) {
            res.append("--");
        }
        return res.toString();
    }

    public static void main(String[] args){
//        Array<Integer> array = ArrayUtils.genRandomIntArray(20, 5);
        Array<Integer> array  = ArrayUtils.genOrderIntArray(4);
        System.out.println(array);
        RBTree<Integer> redBlackTree = new RBTree<>();
        for (int i = 0; i < array.getSize(); i++) {
            redBlackTree.add(array.get(i));
        }
        System.out.println(redBlackTree);
        System.out.println("contains:" + redBlackTree.contains(10));
    }
}

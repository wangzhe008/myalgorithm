package tree;

import linear.Array;
import utils.ArrayUtils;

/**
 * 红黑树
 * 等价于23树，完全平衡二叉树，一般使用左倾实现，即3子节点中的平行节点在左边
 * 平行节点时红色，根和叶子节点时黑色(空叶子)，任意节点到叶子节点之间的黑色节点的数量时一样的，即黑平衡
 */
public class RedBlackTree<E extends Comparable> {

    class Node{
        E value;
        Node left;
        Node right;
        boolean color;

        Node(E value){
            this.value = value;
            left = null;
            right = null;
            color = true;// 默认是红色，平行节点，根据实际逻辑再做修改
        }
    }
    static boolean RED = true;
    static boolean BLACK = false;

    Node root;
    int size;

    public RedBlackTree(){
        root = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    private boolean isRed(Node node){
        if (node == null){
            return BLACK;
        }
        return node.color;
    }

    public boolean isEmpty(){
        return size <= 0;
    }

    public void add(E value){
        // 已存在则退出，不支持重复数据
        if (contains(value)){
            System.out.println(value+"数据已存在");
            return;
        }

        root = add(root,value);
        //根必须是黑色
        root.color = BLACK;
    }

    /**
     * 添加
     * 1、新增节点是红色，根则转为黑色
     * 2、按照左小右大添加入树
     * 3、左节点是黑，右节点是红，左倾原则，需要左旋转和着色
     *  (1)当前节点挂在右节点的左子节点上，右节点的左子节点挂在当前节点的右节点上，右节点作为新的当前节点
     *  (2)新当前节点和原的颜色不变，原节点为红色(相当于本次旋转着色处理只是选出本子树的根)
     * 4、左节点是红，左节点的左子节点是红，需要右旋和着色
     * (1)当前节点挂在左节点的右子节点上，左节点的右子节点挂在当前节点的左节点上，左节点作为新的当前节点
     * (2)新当前节点和原的颜色不变，原节点为红色(相当于本次旋转着色处理只是选出本子树的根)
     * 5、左右都是红色，需要选出上升节点作为上层节点的平行节点，进行着色处理，当前节点为红，左节点为黑，右节点为黑
     * @param node
     * @param value
     * @return
     */
    private Node add(Node node, E value) {

        if(node == null){
            node = new Node(value);
            size ++;
            return node;
        }

        if (value.compareTo(node.value) > 0){
            // value大则在右半边
            node.right = add(node.right, value);
        }else if(value.compareTo(node.value) < 0){
            // value小则在左半边
            node.left = add(node.left, value);
        }else{
            // 相等则不处理
//            return node;
        }

        // 左节点是黑，右节点是红，左倾原则，需要左旋转和着色
        if (!isRed(node.left) && isRed(node.right)){
            node = leftRotate(node);
        }
        // 左节点是红，左节点的左子节点是红，需要右旋和着色
        if (isRed(node.left) && isRed(node.left.left)){
            node = rightRotate(node);
        }
        // 左右都是红色，需要选出上升节点作为上层节点的平行节点
        if (isRed(node.left) && isRed(node.right)){
            flipColor(node);
        }
        return node;
    }

    /**
     * 翻转颜色
     * 左右都是红色，说明需要选出上升节点作为上层节点的平行节点，进行着色处理
     * 当前节点为红色，左节点为黑，右节点为黑
     * @param node
     */
    private void flipColor(Node node) {
//        System.out.println("flipColor-start:"+node.value+"["+isRed(node)+","+ isRed(node.left)+","+isRed(node.right)+"]");
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;

//        System.out.println("flipColor-end:"+node.value+"["+isRed(node)+","+ isRed(node.left)+","+isRed(node.right)+"]");
    }

    /**
     * 左节点是黑，右节点是红，左倾原则，需要左旋转和着色
     * (1)当前节点挂在右节点的左子节点上，右节点的左子节点挂在当前节点的右节点上，右节点作为新的当前节点
     * (2)新当前节点和原的颜色不变，原节点为红色(相当于本次旋转着色处理只是选出本子树的根)
     * @param node
     * @return
     */
    private Node rightRotate(Node node) {
        System.out.println("rightRotate-start:"+node.value+"["+isRed(node)+","+ isRed(node.left)+","+isRed(node.right)+"]");
        Node leftRight = node.left.right;
        Node newNode = node.left;
        newNode.right = node;
        node.left = leftRight;

        newNode.color = node.color;
        node.color = RED;
        System.out.println("rightRotate-end:"+ newNode.value+"["+isRed(newNode)+","+ isRed(newNode.left)+","+isRed(newNode.right)+"]");
        return newNode;
    }

    /**
     * 左节点是红，左节点的左子节点是红，需要右旋和着色
     * (1)当前节点挂在左节点的右子节点上，左节点的右子节点挂在当前节点的左节点上，左节点作为新的当前节点
     * (2)新的当前节点颜色不变，左节点为黑色，右节点颜色不变
     * @param node
     * @return
     */
    private Node leftRotate(Node node) {
//        System.out.println("leftRotate-start:"+node.value+"["+isRed(node)+","+ isRed(node.left)+","+isRed(node.right)+"]");
        Node rightLeft = node.right.left;
        Node newNode = node.right;
        newNode.left = node;
        node.right = rightLeft;

        newNode.color = node.color;
        node.color = RED;
//        System.out.println("leftRotate-end:"+newNode.value+"["+isRed(newNode)+","+ isRed(newNode.left)+","+isRed(newNode.right)+"]");
        return newNode;
    }

    public boolean contains(E value){
        return getNode(root,value) != null;
    }

    private Node getNode(Node node, E value) {
        if (node == null){
            return null;
        }

        // 大于当前节点，则在左边
        if (node.value.compareTo(value) > 0){
            return getNode(node.left, value);
        }else // 小于当前节点，则在右边
            if (node.value.compareTo(value) < 0){
            return getNode(node.right, value);
        }
        return node;
    }

    public boolean isBalance(){
        return false;
    }

    /**
     * 二分搜索树检查
     * 中序遍历，放入列表，再检查列表即可
     * @return
     */
    public boolean isBST(){
        Array<E> array = new Array();
        inOrder(root,array);
        for (int i = 1; i < array.getSize(); i++) {
            if (array.get(i).compareTo(array.get(i-1)) < 0){
                return false;
            }
        }
        return true;
    }

    private void inOrder(Node node, Array<E> array) {
        if (node == null){
            return;
        }
        inOrder(node.left, array);
        array.insert(node.value);
        inOrder(node.right, array);
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
        if (node.color){
            depth = depth -1;
        }
        res.append(generateDepthString(depth) + node.value +"["+ (node.color?"红":"黑") +"]\n");
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

    public static void main(String[] args) {
//        Array<Integer> array  = ArrayUtils.genOrderIntArray(15);
        Array<Integer> array = ArrayUtils.genRandomIntArray(20, 20);
        System.out.println(array);
        RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();
        for (int i = 0; i < array.getSize(); i++) {
            redBlackTree.add(array.get(i));
        }
        System.out.println(redBlackTree);
        System.out.println("contains:" + redBlackTree.contains(10));
        System.out.println("isBST:" + redBlackTree.isBST());
//        System.out.println("isBalanced:" + redBlackTree.isBalanced());
    }
}

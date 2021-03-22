package tree;

import linear.Array;
import utils.ArrayUtils;

import java.util.ArrayList;

/**
 * AVL平衡树
 * 一种宽松的平衡二叉树，左小右大且任意一个节点的左子树和右子树最大高度差不超过1
 * 父节点高度是左右子节点高度最大值+1
 * 平衡因子，左右子数高度差，若大于1或小于-1说明其左子树或右子树不平衡，需做旋转
 * 注：不允许插入重复数据
 */
public class AvlTree<E extends Comparable> {
    class Node{
        int height;
        E value;
        Node left;
        Node right;

        Node(E value){
            if (value == null){
                throw new IllegalArgumentException("value is null!");
            }
            height = 1;
            this.value = value;
            left = null;
            right = null;
        }

        Node(E value,Node left,Node right){
            if (value == null){
                throw new IllegalArgumentException("value is null!");
            }
            height = 1;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    Node root = null;
    int size = 0;

    public int getSize(){
        return size;
    }
    public boolean isEmpty(){
        return size <= 0;
    }

    /**
     * 二分搜索树，左大右小
     * 中序排序，从小到大
     * @return
     */
    public boolean isBST(){
        Array<E> array = new Array<>(size);
        inOrder(root,array);
        for (int i = 1; i < array.getSize(); i++) {
            if (array.get(i-1).compareTo(array.get(i)) > 0){
                return false;
            }
        }
        return true;
    }

    /**
     * 中序遍历
     * @param root
     * @param array
     */
    private void inOrder(Node root, Array<E> array) {
        if (root == null){
            return;
        }
        inOrder(root.left, array);
        array.insert(root.value);
        inOrder(root.right, array);
    }

    /**
     * 是否平衡二叉树
     * @return
     */
    public boolean isBalanced(){
        return isBalanced(root);
    }

    /**
     * 使用前序遍历所有节点
     * @param root
     * @return
     */
    private boolean isBalanced(Node root) {
        if (root != null) {
            // 当前节点不平衡
            if (balanceFactor(root) > 1 || balanceFactor(root) < -1) {
                return false;
            }
            boolean left = isBalanced(root.left);
            boolean right = isBalanced(root.right);
            // 子树不平衡
            if (!(left && right)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 添加
     * @param value
     */
    public void add(E value){
        // 不允许插入重复数据
        if (getNode(root, value)!=null){
            return ;
        }
        root = add(root,value);
    }

    /**
     * 按左小右大插入
     * 若平衡因子超过1或-1，则旋转
     * @param node
     * @param value
     * @return
     */
    private Node add(Node node, E value) {

        // 递归不变量
        if (node == null){
            size ++;
            return new Node(value);
        }

        if (value.compareTo(node.value) > 0){
            node.right = add(node.right, value);
        }else{
            node.left = add(node.left, value);
        }

        // 树高+1
        setHeight(node);

        // 左边树高
        if (balanceFactor(node) > 1){
            // 新增在左子节点的左边子树,做右旋
            if (balanceFactor(node.left) > 0){
                return rightRotate(node);
            }else // 新增在左子节点的右边子树，先左旋再右旋
                if (balanceFactor(node.left) <= 0){
                    node.left = leftRotate(node.left);
                    return rightRotate(node);
            }
        }else // 右边树高
            if (balanceFactor(node) < -1){
                // 新增在右子节点的左边子树,先做右旋在左旋
                if (balanceFactor(node.right) > 0){
                    node.right = rightRotate(node.right);
                    return leftRotate(node);
                }else // 新增在右子节点的右边子树，左旋
                    if (balanceFactor(node.right) <= 0){
                        return leftRotate(node);
                    }
        }

        return node;
    }

    private void setHeight(Node root) {
        root.height = getHighestChild(root) + 1;
    }

    /**
     * 左旋转
     * 自己挂载到右子节点的左节点，右子节点的原左节点挂在自己的右节点
     * @param root
     * @return
     */
    private Node leftRotate(Node root){
        // 右子树
        Node right = root.right;
        // 右子树的左子树
        Node childLeft = right.left;

        // 自己挂载到右子节点的左节点
        right.left = root;
        root.right = childLeft;
        setHeight(root);
        setHeight(right);
        // 返回右子节点作为根
        return right;
    }

    /**
     * 右旋转
     * 自己挂载到左子节点的右节点，左子节点的右节点挂在到自己的左节点
     * @param root
     * @return
     */
    private Node rightRotate(Node root){
        Node left = root.left;
        Node childRight = left.right;

        left.right = root;
        root.left = childRight;
        setHeight(root);
        setHeight(left);
        // 返回左子节点作为根
        return left;
    }


    /**
     * 获取节点平衡因子
     * @param root
     * @return
     */
    private int balanceFactor(Node root){
        if (root == null){
            return 0;
        }
        return getHeight(root.left) - getHeight(root.right);
    }

    /**
     * 获取子树最高值
     * @param root
     * @return
     */
    private int getHighestChild(Node root){
        if (getHeight(root.left) >= getHeight(root.right)){
            return getHeight(root.left);
        }
        return getHeight(root.right);
    }
    /**
     * 获取节点树高
     * @param root
     * @return
     */
    private int getHeight(Node root){
        if (root == null){
            return 0;
        }
        return root.height;
    }

    /**
     * 检查是否包含value
     * @param value
     * @return
     */
    public boolean contains(E value){
        Node node = getNode(root,value);
        return node != null;
    }

    private Node getNode(Node root, E value) {
        if (root == null){
            return null;
        }
        if (value.compareTo(root.value) > 0){
            return getNode(root.right,value);
        }else if(value.compareTo(root.value) < 0){
            return getNode(root.left, value);
        }else{
            return root;
        }
    }

    /**
     * 删除
     * 先确定元素存在，再删除
     * @param value
     */
    public E remove(E value){
        Node node = getNode(root, value);

        if (node != null){
            root = remove(root,value);
            return node.value;
        }
        return null;
    }

    /**
     * 删除元素
     * 匹配元素，再找到其右节点的最左叶子或左节点的最右叶子代替自己
     * @param node
     * @param value
     * @return
     */
    private Node remove(Node node, E value) {
        if (node == null){
            return null;
        }
        // 删除处理
        Node retNode = node;
        if (node.value.compareTo(value) > 0){
            node.left = remove(node.left, value);
        }else if (node.value.compareTo(value) < 0){
            node.right = remove(node.right, value);
        }else {// 匹配
            // 左右子树为空
            if (node.left == null && node.right == null){
                retNode = null;
                size --;
                return retNode;
            }else if (node.left == null ){
                retNode = node.right;
                node.right = null;
                size --;
            }else if (node.right == node){
                retNode = node.left;
                node.left = null;
                size --;
            }else{//左右子树都存在，找到右子树最左叶子并删除，使用该叶子节点接收当前节点的左右子树
                retNode = minimum(node.right);
//                System.out.println(node.value+"---" + retNode);
                retNode.right = remove(node.right,retNode.value);// 当允许重复数据时，此处会导致实际删除的节点不是最左节点
                retNode.left = node.left;
                node.right = null;
                node.left = null;
                size --;
            }
        }
        if (retNode != null){
            retNode.height = getHeight(retNode);
        }else{
            return null;
        }

        // 平衡处理
        if (balanceFactor(retNode) > 1){// 左边树高
            // 左子节点的左边子树高,做右旋
            if (balanceFactor(retNode.left) > 0){
                return rightRotate(retNode);
            }else // 左子节点的右边子树高，先左旋再右旋
                if (balanceFactor(retNode.left) <= 0){
                    retNode.left = leftRotate(retNode.left);
                    return rightRotate(retNode);
                }
        }else // 右边树高
            if (balanceFactor(retNode) < -1){
                // 右子节点的左边子树高,先做右旋在左旋
                if (balanceFactor(retNode.right) > 0){
                    retNode.right = rightRotate(retNode.right);
                    return leftRotate(retNode);
                }else // 右子节点的右边子树高，左旋
                    if (balanceFactor(retNode.right) <= 0){
                        return leftRotate(retNode);
                    }
            }
        return retNode;
    }

    /**
     * 获取树的最小值
     * 最左叶子节点
     * @param node
     * @return
     */
    private Node minimum(Node node) {
        if (node == null){
            return null;
        }

        if (node.left == null){
            return node;
        }else{
            return minimum(node.left);
        }
    }

//    @Override
//    public String toString() {
//        int size = getSize();
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("size:").append(size).append("\n");
//        Array<E> array = new Array<>(size);
//        inOrder(root, array);
//        for (int i = 0; i < size; i++) {
//            stringBuilder.append("-").append(array.get(i));
//        }
//        return stringBuilder.toString();
//    }

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
        res.append(generateDepthString(depth) + node.value +"\n");
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
//        Array<Integer> array  = ArrayUtils.genOrderIntArray(10);
        Array<Integer> array = ArrayUtils.genRandomIntArray(20, 5);
        System.out.println(array);
        AvlTree<Integer> avlTree = new AvlTree<>();
        for (int i = 0; i < array.getSize(); i++) {
            avlTree.add(array.get(i));
        }
        System.out.println(avlTree);
        System.out.println("contains:" + avlTree.contains(10));
        System.out.println("isBST:" + avlTree.isBST());
        System.out.println("isBalanced:" + avlTree.isBalanced());
//        avlTree.remove(7);
//        System.out.println(avlTree);
//        System.out.println("isBST:" + avlTree.isBST());
//        System.out.println("isBalanced:" + avlTree.isBalanced());
        avlTree.remove(3);
        System.out.println(avlTree);
        System.out.println("isBST:" + avlTree.isBST());
        System.out.println("isBalanced:" + avlTree.isBalanced());
    }
}

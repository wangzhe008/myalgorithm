package tree;

import linear.Array;
import linear.LinkedList;
import utils.ArrayUtils;

import java.util.ArrayList;

/**
 * 二分搜索树
 * 子节点左大右小的二叉树
 */
public class BinarySearchTree<E extends Comparable> {

    class Node{
        E value =  null;
        Node left =  null;
        Node right =  null;

        Node(E value ,Node left ,Node right){
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    private int size = 0;
    private Node root = null;

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return root == null;
    }

    public void add(E value){
        root = add(value, root);
    }

    private Node add(E value, Node root){
        if (root == null){
            size ++;
            root = new Node(value, null, null);
        }else if(root.value.compareTo(value) < 0){//根小，放右边
            root.right = add(value,root.right);
            size ++;
        }else if(root.value.compareTo(value) > 0){//根大，放左边
            root.left = add(value, root.left);
            size ++;
        }

        return root;
    }

    public boolean contains(E value){
        return contains(value,root) != null;
    }

    private Node contains(E value, Node root) {
        if (root == null){
            return null;
        }

        //存在
        if(root.value.compareTo(value) == 0){
            return root;
        }

        //根小，放右边
        if(root.value.compareTo(value) < 0){
            return contains(value,root.right);
        }
        //根大，放左边
        return contains(value, root.left);
    }

    /**
     * 前序遍历
     * 先根后左右
     */
    public void preOrder(){
        preOrder(root);
    }

    private void preOrder(Node root) {
        if (root == null){
            return;
        }
        System.out.println(root.value);
        preOrder(root.left);
        preOrder(root.right);
    }

    /**
     * 左根右，用于排序
     */
    public void inOrder(){
        inOrder(root);
    }

    private void inOrder(Node root) {
        if (root == null){
            return;
        }
        inOrder(root.left);
        System.out.println(root.value);
        inOrder(root.right);
    }

    /**
     * 先左右后跟
     * 可以用于管理引用
     */
    public void postOrder(){
        postOrder(root);
    }

    private void postOrder(Node root) {
        if (root == null){
            return;
        }
        postOrder(root.left);
        postOrder(root.right);
        System.out.println(root.value);
    }
//
//    public void preOrderNR(){//非递归
//
//    }
//
//    public void inOrderNR(){
//
//    }
//
//    public void postOrderNR(){
//
//    }
//

    /**
     * 层序遍历
     * 从上往下，从左往右
     */
    public void levelOrder(){
        if (root == null){
            System.out.println("空树");
            return;
        }
        // 设计队列，先进先出，从上往下处理，一层层放入
        LinkedList<Node> linkedList = new LinkedList();
        linkedList.addFirst(root);
        Node node = null;
        while (!linkedList.isEmpty()){
            node = linkedList.removeTail();
            System.out.println(node.value);

            // 每层子节点放入队列
            if (node.left != null){
                linkedList.addFirst(node.left);
            }
            if (node.right != null){
                linkedList.addFirst(node.right);
            }
        }
    }

    /**
     * 从下往上，从左往右，层序遍历
     */
    public void reverselevelOrder(){
        ArrayList<ArrayList> array = levelList();
        if (array == null){
            return;
        }

        for (int i = array.size()-1; i >= 0; i--) {
            ArrayList array1 = array.get(i);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(i).append("------::");
            for (int j = 0; j < array1.size(); j++) {
                stringBuilder.append(":").append(array1.get(j));
            }
            System.out.println(stringBuilder);
        }
    }

    /**
     * 从上往下，Z字遍历
     */
    public void zlevelOrder(){
        ArrayList<ArrayList> array = levelList();
        if (array == null){
            return;
        }

        for (int i = 0; i < array.size(); i++) {
            ArrayList array1 = array.get(i);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(i).append("------::");
            if (i%2 != 0){// 层次为奇数，反转
                for (int j = array1.size()-1; j >= 0; j--) {
                    stringBuilder.append(":").append(array1.get(j));
                }
            }else {
                for (int j = 0; j < array1.size(); j++) {
                    stringBuilder.append(":").append(array1.get(j));
                }
            }
            System.out.println(stringBuilder);
        }
    }

    private ArrayList<ArrayList> levelList(){
        if (root == null){
            System.out.println("空树");
            return null;
        }

        ArrayList<ArrayList> arrayLists = new ArrayList();// 双层列表，按层次放数据
        LinkedList<Node> linkedList = new LinkedList();// 队列，先进先出，从上往下处理，一层层放入
        linkedList.addFirst(root);
        Node node = null;
        while (!linkedList.isEmpty()){
            int currSize = linkedList.getSize();// 由于队列会持续放入子节点，要提前记录当前层节点个数
            ArrayList arrayList = new ArrayList<>();

            // 处理每层节点
            for (int i = 0; i < currSize; i++) {
                node = linkedList.removeTail();
                arrayList.add(node.value);

                // 每层子节点放入队列
                if (node.left != null){
                    linkedList.addFirst(node.left);
                }
                if (node.right != null){
                    linkedList.addFirst(node.right);
                }
            }
            // 每层数据放入列表
            arrayLists.add(arrayList);
        }

        return arrayLists;
    }

    /**
     * 查询最小值
     * @return
     */
    public E minimum(){
        Node node = root;
        node = minimum(node);
        return node == null?null:node.value;
    }

    private Node minimum(Node node) {
        while(node != null && node.left != null){
            node = node.left;
        }
        return node;
    }

    /**
     * 查询最大值
     * @return
     */
    public E maximum(){
        Node node = root;

        while(node != null && node.right != null){
            node = node.right;
        }
        return node == null?null:node.value;
    }

    /**
     * 删除最小值
     * @return
     */
    public void removeMin(){
        root = removeMin(root);
    }

    private Node removeMin(Node treeRoot) {
        if (treeRoot == null){
            return null;
        }
        Node node = treeRoot;
        Node remove = null;
        while(node != null &&  node.left != null && node.left.left != null){
            node = node.left;
        }

        if (node == treeRoot && node.left == null){
            remove = treeRoot;
            treeRoot = treeRoot.right;
            remove.right = null;
        }else{
            remove = node.left;
            node.left = remove.right;
        }
        size--;
        return treeRoot;
    }

    /**
     * 删除最大值
     * @return
     */
    public E removeMax(){
        if (root == null){
            return null;
        }
        Node node = root;
        Node remove = null;
        // 当前节点的右右子节点没有，说明当前节点的右子节点时删除节点
        while(node != null &&  node.right != null && node.right.right != null){
            node = node.right;
        }

        // 若当前节点时根节点，且没有右节点，说明根节点时删除节点，根做节点作为新的根节点
        if (node == root && node.right == null){
            remove = root;
            root = root.left;
            remove.left = null;
        }else{// 删除当前节点的右节点，删除节点的左节点作为当前节点的右节点
            remove = node.right;
            node.right = remove.left;
        }
        size--;
        return  remove == null?null:remove.value;
    }

    /**
     * 删除匹配节点
     * 找到匹配节点，与最接近的叶子节点替换
     * @param value
     * @return
     */
    public void remove(E value){
        root = remove(value,root);
    }

    private Node remove(E value, Node root) {
        if (root == null){
            System.out.println("未匹配删除节点");
            return null;
        }
        if (root.value.compareTo(value) > 0){
            root.left = remove(value, root.left);
        }else if (root.value.compareTo(value) < 0){
            root.right = remove(value, root.right);
        }else{
            // 左右子节点都为空
            if (root.left == null && root.right == null){
                size--;
                return null;
            }
            //左子节点空
            if (root.left == null){
                Node node = root.right;
                root.right = null;
                size--;
                return node;
            }
            //右子节点空
            if (root.right == null){
                Node node = root.left;
                root.left = null;
                size--;
                return node;
            }
            // 左右子节点都不为空，找到右子树最小节点替换匹配节点作为匹配节点子树新根节点，并迁移原来的左右子树
            Node match = minimum(root.right);
            // 删除右子树最小值
            match.right = removeMin(root.right);
            match.left = root.left;
            size--;
            return match;
        }
        return root;
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
        Array<Integer> array = ArrayUtils.genRandomIntArray(10, 10);
        System.out.println(array);
        BinarySearchTree tree = new BinarySearchTree();
        for (int i = 0; i < array.getSize(); i++) {
            tree.add(array.get(i));
        }
        System.out.println(tree);

        System.out.println(tree.contains(2));

        System.out.println("--------preOrder--------");
        tree.preOrder();

        System.out.println("-------inOrder---------");
        tree.inOrder();

        System.out.println("--------postOrder--------");
        tree.postOrder();

        System.out.println("-------levelOrder---------");
        tree.levelOrder();

        System.out.println("-------zlevelOrder---------");
        tree.zlevelOrder();

        System.out.println("--------reverselevelOrder--------");
        tree.reverselevelOrder();

        System.out.println("-------minimum---------" + tree.minimum());
        tree.removeMin();
        System.out.println("-------removeMin - inOrder---------");
        tree.inOrder();

        System.out.println("-------maximum---------" + tree.maximum());
        System.out.println("-------removeMax---------" + tree.removeMax());

        System.out.println("--------reverselevelOrder--------");
        tree.reverselevelOrder();

        System.out.println("-------inOrder---------");
        tree.inOrder();

        tree.remove(4);
        System.out.println("-------remove - inOrder---------");
        tree.inOrder();
    }
}

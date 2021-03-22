package tree;

import java.util.HashSet;

/**
 * 前缀树
 * 多叉树，字符串从头到尾放入树中，每个字符放一层，直到最后一个放完，该节点打上完整字符串的标识即可
 * 时间复杂度与查询的字符串长度有关，与树中字符串数量无关
 * 主要用于解决字符串查询问题，允许重复字符串
 */
public class Trie {
    class Node{
        Character value ;
        HashSet<Node> childs;
        int count = 0;// 前缀字符串计数
        int wordCount = 0;// 同一字符串计数
        boolean isWord;

        Node(Character value, boolean isWord){
            this.value = value;
            childs = new HashSet<>();
            this.isWord = isWord;
        }
    }

    Node root = new Node(null, false);// 根节点为空节点
    int size = 0;

    /**
     * 字符返回检查
     * @param c
     * @return
     */
    private boolean checkVarilChar(char c){
        if (c >= 'A' && c <= 'z'){
            return true;
        }
        return false;
    }
    public int getSize(){
        return size;
    }

    /**
     * 添加字符串
     * 分解字符串，一个个放入树中
     * @param str
     */
    public void add(String str){
        char[] strChar = str.toCharArray();
        // 检查字符串是否合法
        for (int i = 0; i <strChar.length; i++) {
            if (!checkVarilChar(strChar[i])){
                System.out.println("存在不合法字符："+strChar[i]);
                return;
            }
        }
        add(root.childs, strChar,0);
    }

    private void add(HashSet<Node> nodes, char[] strChar, int i) {
        if (i >= strChar.length){
            return;
        }
        char value = strChar[i];

        // 检查是否存在当前字符
        Node retNode = null;
        for (Node node:nodes) {
            if (node.value.equals(value)){
                retNode = node;
                break;
            }
        }
        // 不存在则创建，并放入上节点的子节点中
        if (retNode == null){
            retNode = new Node(value, false);
            nodes.add(retNode);
        }
        // 更新字符计数
        retNode.count += 1;

        // 继续处理后续字符
        if (i < strChar.length-1){
            add(retNode.childs, strChar, i+1);
        }else // 字符串最后一个字符
            if(i == strChar.length-1){
            retNode.isWord = true;
            retNode.wordCount += 1;
        }
    }

    /**
     * 是否包含字符串
     * @param str
     * @return
     */
    public boolean containsWord(String str){
        char[] strChar = str.toCharArray();
        // 检查字符串是否合法
        for (int i = 0; i <strChar.length; i++) {
            if (!checkVarilChar(strChar[i])){
                System.out.println("存在不合法字符："+strChar[i]);
                return false;
            }
        }
        int wordCount = getWord(root.childs,strChar,0,true);
        return wordCount > 0;
    }

    /**
     * 检查单词或前缀
     * @param nodes
     * @param strChar
     * @param i
     * @param checkWord true-检查单词，false-检查前缀
     * @return
     */
    private int getWord(HashSet<Node> nodes, char[] strChar, int i,boolean checkWord) {
        if (i >= strChar.length){
            return 0;
        }
        char value = strChar[i];
        // 检查是否存在当前字符
        Node retNode = null;
        for (Node node:nodes) {
            if (node.value.equals(value)){
                retNode = node;
                break;
            }
        }
        if (retNode == null){
            return 0;
        }else{
            if (i == strChar.length-1){
                return checkWord?retNode.wordCount:retNode.count;
            }
            return getWord(retNode.childs,strChar,i+1,checkWord);
        }
    }

    /**
     * 包含前缀的字符串数量
     * @param prefix
     * @return
     */
    public boolean containsPrefixCount(String prefix){
        char[] strChar = prefix.toCharArray();
        // 检查字符串是否合法
        for (int i = 0; i <strChar.length; i++) {
            if (!checkVarilChar(strChar[i])){
                System.out.println("存在不合法字符："+strChar[i]);
                return false;
            }
        }
        int prefixCount = getWord(root.childs,strChar,0,false);
        return prefixCount > 0;
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("root:null\n");
        generateBSTString(root.childs, 1, res);
        return res.toString();
    }

    // 生成以node为根节点，深度为depth的描述二叉树的字符串
    private void generateBSTString(HashSet<Node> childs, int depth, StringBuilder res){
        if(childs == null){
            res.append(generateDepthString(depth) + "null\n");
            return;
        }
        for (Node node :childs) {
            res.append(generateDepthString(depth) + node.value + "[count：" + node.count +",wordCount："+ node.wordCount +",isWord:"+node.isWord+"]\n");
            generateBSTString(node.childs, depth + 1, res);
        }
    }

    private String generateDepthString(int depth){
        StringBuilder res = new StringBuilder();
        for(int i = 0 ; i < depth ; i ++) {
            res.append("--");
        }
        return res.toString();
    }

    public static void main(String[] args) {
        String[] strs = {"cseefg","vdfwdh","qwerfa","poldg","kdjicmd","csesfes","csdied","cseefg"};
        Trie trie = new Trie();
        for (int i = 0; i < strs.length; i++) {
            trie.add(strs[i]);
        }
        System.out.println(trie);
        System.out.println("---containsWord---" + trie.containsWord("cseefg"));
        System.out.println("---containsPrefixCount---" + trie.containsPrefixCount("csed"));
    }

}

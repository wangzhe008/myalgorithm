package linear;

/**
 * 双向链表
 * @param <E>
 */
public class LinkedList<E> {

    class Node{
        E value = null;
        Node next = null;
        Node pre = null;

        Node(E value){
            this.value = value;
        }

        Node(E value,Node pre, Node next){
            this.value = value;
            this.pre = pre;
            this.next = next;
        }
    }

    Node head = new Node(null);

    Node tail = null;

    int size = 0;

    public boolean addFirst(E value){
        if (value != null){
            Node node = new Node(value);

            if(size == 0) {
                head.next = node;
                node.pre = head;
                tail = node;
            }else{
                Node next = head.next;
                head.next = node;
                node.pre = head;
                node.next = next;
                next.pre = node;
            }
            size ++;
            return true;
        }
        return false;
    }

    public boolean addTail(E value){
        if (value != null){
            Node node = new Node(value);
            if (tail == null){
                head.next = node;
                node.pre = head;
            }else {
                tail.next = node;
                node.pre = tail;
            }
            tail = node;
            size++;
            return true;
        }
        return false;
    }

    public boolean contains(E value){
        if (size > 0 && value != null){
           Node node = head;
           while(node.next != null) {
               if (value.equals(node.next.value)){
                   return true;
               }
           }
        }
        return false;
    }

    public int getSize(){
        return size;
    }

    public E getFirst(){
       Node node = head.next;
       return node == null? null:node.value;
    }

    public E getTail(){
        return tail == null ? null : tail.value;
    }

    public E removeFirst(){
        if (size > 0){
            Node node = head.next;
            Node next = head.next.next;
            if (next == null){
                head.next = null;
                tail = null;
            }else{
                head.next = next;
                next.pre = head;
            }
            node.pre = null;
            node.next = null;
            size--;
        }
        return null;
    }

    public E removeTail(){
        if (size > 0){
            E value = tail.value;
            if (tail.pre == head){
                head.next = null;
                tail.pre = null;
                tail = null;
            }else{
                Node pre = tail.pre;
                tail.pre = null;
                pre.next = null;
                tail = pre;
            }
            size--;
            return value;
        }
        return null;
    }

    /**
     * 反转
     */
    public void reverse(){
        if(size <= 1){
            return ;
        }
        Node temp = null;

        while(head.next != null){
            Node node = head.next;
            Node next = node.next;

            //头指向下一节点
            head.next = next;
            if (next != null) {
                next.pre = head;
            }

            //当前节点脱离头和下一节点
            node.next = temp;
            node.pre = next;
            if (tail == null){
                tail = node;
            }
            temp = node;
        }
        head.next = temp;
        temp.pre = head;

    }

    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("size-").append(size).append("\n");
        Node node = head;
        int i = 0;
        while (node.next != null){
            node = node.next;
            i++;
            stringBuffer.append("(").append(i).append(")").append(node.value);
            if (i == size ){
                stringBuffer.append(":");
            }
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList<Integer>();
        for (int i = 1; i <= 10; i++) {
            linkedList.addTail(i);
        }
        System.out.println(linkedList.toString());

        LinkedList linkedList1 = new LinkedList<Integer>();
        for (int i = 1; i <= 10; i++) {
            linkedList1.addFirst(i);
        }
        System.out.println(linkedList1.toString());
        linkedList1.removeFirst();
        System.out.println(linkedList1.toString());
        linkedList1.removeTail();
        System.out.println(linkedList1.toString());

        linkedList1.reverse();
        System.out.println(linkedList1.toString());

        LinkedList linkedList2 = new LinkedList<Integer>();
        linkedList2.addFirst(10);
        linkedList2.addFirst(11);
        linkedList2.addFirst(12);
        System.out.println(linkedList2.toString());
        linkedList2.reverse();
        System.out.println(linkedList2.toString());
    }
}

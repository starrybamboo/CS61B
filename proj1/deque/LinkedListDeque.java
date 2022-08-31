package deque;

public class LinkedListDeque<Item> {
    private Node sentinel;
    private int size = 0;
    public class Node{
        public Item value;
        public Node first;
        public Node rest;
        public Node(Item input){
            this.value = input;
            this.first = this;
            this.rest =this;
        }
    }

    public LinkedListDeque(){
        sentinel = new Node(null);
    }

//    public Item getRecursive (int index){
//        if (index == 0){
//            return this.value;
//        }else if (this.next == null){
//            return null;
//        } else{
//            return this.next.getRecursive(index - 1);
//        }
//    }

    public boolean isEmpty(){
        if(size == 0){
            return true;
        }return false;
    }

    public void addFirst(Item value){
        Node x = sentinel.rest;
        sentinel.rest =new Node (value);
        sentinel.rest.first = sentinel;
        sentinel.rest.rest = x;
        x.first = sentinel.rest;
        size = size + 1;

    }
    public void addLast(Item value){
        Node x = sentinel.first;
        sentinel.first = new Node(value);
        sentinel.first.rest = sentinel;
        sentinel.first.first = x;
        x.rest = sentinel.first;
        size += 1;
    }
    public int size(){
        return size;
    }

    public Item removeFirst(){
        if(size != 0) {
            Node x = sentinel.rest;
            sentinel.rest = sentinel.rest.rest;
            size = size -1;
            return x.value;
        }return null;
    }

    public Item removeLast(){
        if(size != 0) {
            Node x = sentinel.first;
            sentinel.first = sentinel.first.first;
            size = size -1;
            return x.value;
        }return null;
    }

    public void printDeque(){
        Node x = sentinel.rest;
        while (x.rest != sentinel){
            System.out.print(x.value+" ");
            x = x.rest;
        }
        System.out.println();
    }


}
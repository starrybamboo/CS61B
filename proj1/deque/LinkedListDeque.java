package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T>{
    private Node sentinel;
    private int size = 0;
    public class Node{
        public T value;
        public Node first;
        public Node rest;
        public Node(T input){
            this.value = input;
            this.first = this;
            this.rest =this;
        }

        public T getRecursive (int index){
            Node o = (Node) this;
            if (index == 0){
                return o.value;
            }else if (o.rest == null){
                return null;
            } else{
                return o.rest.getRecursive(index - 1);
            }
        }
    }

    public LinkedListDeque(){
        sentinel = new Node(null);
    }

    public T getRecursive(int index){
        return this.sentinel.getRecursive(index);
    }

//    public boolean isEmpty(){
//        if(size == 0){
//            return true;
//        }return false;
//    }

    public void addFirst(T value){
        Node x = sentinel.rest;
        sentinel.rest =new Node (value);
        sentinel.rest.first = sentinel;
        sentinel.rest.rest = x;
        x.first = sentinel.rest;
        size = size + 1;

    }
    public void addLast(T value){
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

    public T removeFirst(){
        if(size != 0) {
            Node x = sentinel.rest;
            sentinel.rest = sentinel.rest.rest;
            size = size -1;
            return x.value;
        }return null;
    }


    public T removeLast(){
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

    public T get(int i){
        if (i > size){
            return null;
        }else {
            Node o = sentinel.rest;
            while (i > 0 ){
                o = o.rest;
            }
            return o.value;
        }
    }

    public Iterator<T> iterator(){
        return new LinkedListDequeIterator();
    }

    public class LinkedListDequeIterator implements Iterator{
        int i = 0;

        public boolean hasNext(){
            if (get(i) == null){
                return false;
            }else{
                return true;
            }
        }

        public T next(){
            T tmp = get(i);
            i += 1;
            return tmp;
        }
    }


}
package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    Node sentinel = null;
    int size;

    public BSTMap(){
        this.size = 0;
    }

//    private class Node<K extends Comparable<K>,V>{
//啊这，只要把Node的泛型给去掉就可以了，，，，什么情况，难道是泛型接口只需要一个吗？
    private class Node{
        private Node left, right;
        private K key;
        private V value;

        public Node(K key,V value){
            this.value = value;
            this.key = key;
            this.left = null;
            this.right = null;
        }

//        public boolean containsKey(K key){
//            if (this.key == key){
//                return true;
//            }else {
//                Node tmp = this.getNext(key);
//                if (tmp != null){
//                    return tmp.containsKey(key);
//                }else{
//                    return false;
//                }
//            }
//        }

        public Node locate(K key){
            if (this.key == key){
                return this;
            }else {
                Node tmp = this.getNext(key);
                if (tmp != null){
                    return tmp.locate(key);
                }else{
                    return this;
                }
            }
        }

        public Node getNext(K key){
            if (this.key.compareTo(key) > 0){
                if (this.left != null) {
                    return this.left;
                }return null;
            }else {
                if (this.right != null) {
                    return this.right;
                }return null;
            }
        }

        public void printInOrder(int i){
            String indentation = "";
            for (int j = 0; j < i; j++){
                indentation += " ";
            }
            System.out.println(indentation + this.value);
            if (this.left != null){this.left.printInOrder(i+1);}
            if (this.right != null){this.right.printInOrder(i+1);}
        }

    }
    @Override
    public void clear(){
        sentinel = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key){
        if (sentinel == null){
            return false;
        }else{
            if (sentinel.locate(key).key.equals(key)){
                return true;
            }else {
                return false;
            }
        }
    }

    @Override
    public V get(K key){
        if (sentinel == null){return null;}
        Node tmp =sentinel.locate(key);
        if (tmp.key.equals(key)){
            return tmp.value;
        }else {
            return null;
        }
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void put(K key, V value){
        if (sentinel == null){
            sentinel = new Node(key, value);
            size += 1;
        }else {
            Node tmp = sentinel.locate(key);
            if (tmp.key.equals(key)){
                tmp.value = value;
            } else if (tmp.key.compareTo(key) > 0){
                tmp.left = new Node(key, value);
                this.size += 1;
            }else{
                tmp.right = new Node(key, value);
                this.size += 1;
            }
        }
    }

    public void printInOrder(){
        sentinel.printInOrder(0);
    }


    @Override
    public Set<K> keySet(){
        throw new UnsupportedOperationException("不支持操作！");
    }

    @Override
    public V remove(K key){
        throw new UnsupportedOperationException("不支持操作！");
    }

    public V remove(K key, V value){
        throw new UnsupportedOperationException("不支持操作！");
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("不支持操作！");
    }
}

package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author 降星驰
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int size;
    private int length;
    private double loadFactor;
    private Set<K> keySet;

    /** Constructors */
    public MyHashMap() {
        this.size = 0;
        this.buckets = createTable(16);
        this.loadFactor = 0.75;
        this.length = 16;
        this.keySet = new HashSet<>();
    }

    public MyHashMap(int initialSize) {
        this.size = 0;
        this.buckets = createTable(initialSize);
        this.loadFactor = 0.75;
        this.length = initialSize;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.size = 0;
        this.buckets = createTable(initialSize);
        this.loadFactor = maxLoad;
        this.length = initialSize;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] tmp = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++){
            tmp[i] = createBucket();
        }
        return tmp;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    @Override
    public void clear(){
        this.size = 0;
        this.buckets = createTable(this.length);
        this.keySet = new HashSet<>();
    }

    @Override
    public boolean containsKey(K key){
        Node a = find(key);
        if (a != null) {
            return true;
        }
        return false;
    }

    public Node find(K key){
        int tmp = key.hashCode() % this.length;
        if (tmp < 0){
            tmp = -tmp;
        }
        for (Node x :this.buckets[tmp]){
            if (x.key.equals(key)){
                return x;
            }
        }
        return null;
    }

    private Collection<Node>[] resize(int length){
        Collection<Node>[] a = createTable(this.length * 2);
        for (int i = 0; i < this.length; i++){
            for (Node x : this.buckets[i]){
                int tmp = x.key.hashCode() % (this.length * 2);
                if (tmp < 0){
                    tmp = -tmp;
                }
                a[tmp].add(x);
            }
        }
        this.length *= 2;
        return a;
    }


    @Override
    public V get(K key){
        Node a = find(key);
        if (a != null) {
            return a.value;
        }
        return null;
    }

    @Override
    public int size(){
        return this.size;
    }

    @Override
    public void put(K key, V value){
        Node a = find (key);
        if (a == null){
            int tmp = key.hashCode() % this.length;
            if (tmp < 0){
                tmp = -tmp;
            }
            this.buckets[tmp].add(new Node(key,value));
            this.size += 1;
            this.keySet.add(key);
            if ((double) this.size / this.length > 0.75){
                this.buckets = this.resize(length);
            }
        }else {
            a.value = value;
        }

    }

    public Set<K> keySet(){
        return this.keySet;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }


    @Override
    public V remove(K key){
        return remove(key,get(key));
    }



    @Override
    public V remove(K key, V value){
        Node a = this.find(key);
        if (a != null) {
            int tmp = key.hashCode() % this.length;
            if (tmp < 0) {
                tmp = -tmp;
            }
            buckets[tmp].remove(a);
            return value;
        }
        return null;
    }

}

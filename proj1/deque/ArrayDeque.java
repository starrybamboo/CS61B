package deque;
import java.util.Iterator;


public class ArrayDeque <T> implements Iterable<T>{
    private int size, begin, end;

    private T[] array = (T[]) new Object[8];

    public ArrayDeque(){
        size = 0;
        begin = 0;
        end = array.length - 1;
    }

    public void addFirst(T value){
        if (size == array.length - 2){
            this.resize(size*2);
        }
        array[begin] = value;
        begin += 1;
        size += 1;
        if (begin == array.length){
            begin = 0;
        }
    }

    public void addLast(T value){
        if (size == array.length - 2){
            this.resize(size*2);
        }
        array[end] = value;
        end = end - 1;
        size += 1;
        if (end == -1){
            end = array.length - 1;
        }
    }

    public T removeFirst(){
        if (size > 0) {
            begin -= 1;
            if (begin == -1) {
                begin = array.length - 1;
            }
            T tmp = array[begin];
            size -= 1;
            return tmp;
        }return null;
    }

    public T removeLast(){
        if(size > 0) {
            end += 1;
            if (end == array.length) {
                end = 0;
            }
            T tmp = array[end];
            size = size - 1;
            return tmp;
        }
        return null;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return (size == 0);
    }

    public void resize(int capacity){
        T[] a = (T[]) new Object[capacity];
        if (begin < end){
            for (int i = 0;i < begin; i++){
                a[i] = array[i];
            }
            int m = array.length;
            for(int j = 0;j <= array.length - end -1; j++){
                a[capacity - j - 1] = array[m - j - 1];
            }
            end = capacity - (array.length - end);
        }else{
            for (int i = 0;i < begin - end - 1; i++){
                a[capacity - i - 1] = a[begin - i - 1];
            }
            end = capacity - (begin - end);
            begin = 0;
        }
        array = a;
    }

    public void printDeque(){
        if(begin < end){
            for(int i = begin - 1;i < 0;i--){
                System.out.print(array[i]+" ");
            }
            for (int i = array.length ;i <= end;i--){
                System.out.print(array[i]+" ");
            }
            System.out.println();
        }else {
            for (int i = 0;i < begin - end - 1; i++){
                System.out.print(array[begin - i - 1] + " ");
            }
            System.out.println();
        }
    }

    public T get(int i){
        if (i > size){
            return null;
        }else if (begin < end){
            if (i < begin){
                return array[begin - i - 1];
            }else {
                return array[array.length - (i - begin) - 1];
            }
        }else{
            return array[begin - i - 1];
        }
    }

    public Iterator<T> iterator(){
        return new ArrayDequeIterator();
    }

    public class ArrayDequeIterator implements Iterator{
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
    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }else if (this == o){
            return true;
        }
        ArrayDeque<T> ob = ( ArrayDeque<T>) o;
        if (ob instanceof ArrayDeque){
            if (ob.size != this.size ){
                return false;
            }else{
                for (int i = 0;i < this.size(); i++){
                    if (ob.get(i).equals(this.get(i))){
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}

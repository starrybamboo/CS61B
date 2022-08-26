package deque;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayDeque<Item> {
    private int size;
    private int begin;
    private int end;
    private int beginEnd;
    private int endBegin;
    private Item[] test = (Item[]) new Object[8];

    public ArrayDeque(){
        size = 0;
        begin = 0;
        beginEnd = 0;
        endBegin = test.length -1;
        end = test.length - 1;
    }

    public void addFirst(Item value){
        if (size == test.length){
            resize(size*2);
        }

        if (endBegin <test.length - 1){
            test[endBegin] = value;
            size += 1;
            endBegin += 1;
        }else{
            test[begin] = value;
            size += 1;
            begin += 1;
        }
    }

    public void addLast(Item value){
        if (size == test.length){
            resize(size*2);
        }

        if (beginEnd > 0) {
            test[beginEnd] = value;
            beginEnd = beginEnd - 1;
            size += 1;
        }else {
            test[end] = value;
            end = end - 1;
            size += 1;
        }
    }

    public int judge(){
        return begin - beginEnd + endBegin - end;
    }

    public Item removeFirst(){
        if (size != 0){
            if (begin != 0) {
                Item x = test[begin - 1];
                test[begin - 1] = null;
                begin = begin - 1;
                size = size - 1;
                if (judge() < size / 4) {
                    resize(size / 2);
                }
                return x;
            }else {
                Item x = test[endBegin ];
                test[endBegin] = null;
                endBegin =endBegin - 1;
                size = size - 1;
                if (judge() < size / 4) {
                    resize(size / 2);
                }
                return x;
            }
        }
        return null;
    }

    public int size(){
        return size;
    }

    public Item removeLast(){


        if (size != 0){
            if (end != test.length - 1) {
                Item x = test[end + 1];
                test[end + 1] = null;
                end = end + 1;
                size = size -1;
                if (judge() < size / 4){
                    resize(size/2);
                }
                return x;
            }else{
                Item x = test[beginEnd + 1];
                test[beginEnd + 1] = null;
                beginEnd =beginEnd +1;
                size = size - 1;
                if (judge() < size / 4) {
                    resize(size / 2);
                }
                return x;
            }
        }

        return null;
    }

    public void resize(int capacity){
        Item[] a = (Item[]) new Object[capacity];
        for (int i = 0;i < begin-beginEnd;i++){
            a[i] = test[beginEnd + i ];
        }
        int tmp1 = begin - beginEnd;
        beginEnd = 0;
        begin = beginEnd + tmp1;

        for (int i = 0;i < endBegin - end;i++){
            a[a.length - 1 - i] = test[endBegin - i ];
        }
        int tmp2 =endBegin -end;
        endBegin = a.length-1;
        end = endBegin - tmp2 ;
        test = a;
    }

    public boolean isEmpty(){
        if (size == 0){
            return true;
        }return false;
    }

    public void printDeque(){
        for(int i = begin - 1;i < 0;i--){
            System.out.print(test[i]+" ");
        }
        for (int i = test.length ;i <= end;i--){
            System.out.print(test[i]+" ");
        }
        System.out.println();
    }

    public Item get(int i){
        if (i > test.length){
            return null;
        }
        return test[i];
    }

    public class ArrayDequeTest {
        @Test
        public void AddEmptySizeTest() {
            ArrayDeque<String> lld1 = new ArrayDeque<>();
            assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());

            lld1.addFirst("11");
            assertEquals(1, lld1.size());
            lld1.removeFirst();
            assertEquals(0, lld1.size());
            assertTrue("lld1 should now contain 1 item", lld1.isEmpty());

            lld1.addFirst("11");
            lld1.addLast("middle");
            assertEquals(2, lld1.size());

            lld1.addLast("back");
            assertEquals(3, lld1.size());

            System.out.println("Printing out deque: ");
            lld1.printDeque();
        }
    }
}
package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();

        correct.addLast(5);
        correct.addLast(10);
        correct.addLast(15);

        broken.addLast(5);
        broken.addLast(10);
        broken.addLast(15);

        assertEquals(correct.size(), broken.size());

        assertEquals(correct.removeLast(), broken.removeLast());
        assertEquals(correct.removeLast(), broken.removeLast());
        assertEquals(correct.removeLast(), broken.removeLast());
    }


    @Test
    public void teacherTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();

        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 2);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                System.out.println("size: " + size);
            }
        }
    }
    @Test
    public void ICopyTeachersTest(){
        AListNoResizing<Integer> tmp = new AListNoResizing<>();
        BuggyAList<Integer> tmp2 = new BuggyAList<>();
        for (int i =1 ; i<5000;i++){
            if (tmp.size() == 0) {
                int number = StdRandom.uniform(0, 2);
                if (number == 1) {
                    int randVal = StdRandom.uniform(0, 100);
                    tmp.addLast(randVal);
                    tmp2.addLast(randVal);
                } else {
                    assertEquals(tmp.size(),tmp2.size());
                }
            }else{
                int number = StdRandom.uniform(0, 4);
                if (number == 1) {
                    int randVal = StdRandom.uniform(0, 100);
                    tmp.addLast(randVal);
                    tmp2.addLast(randVal);
                } else if(number==0) {
                    assertEquals(tmp.size(),tmp2.size());
                }else if(number ==3) {
                    assertEquals(tmp.removeLast(),tmp2.removeLast());
                }else {
                    assertEquals(tmp.getLast(),tmp2.getLast());
                }
            }
        }
    }

//  @Test
//  public void testThreeAddThereMove (){
//      AListNoResizing<Integer> ns = new AListNoResizing<>();
//      BuggyAList<Integer> ms = new BuggyAList<>();
//
//      for(int i = 1 ; i<=3;i++){
//          ns.addLast(i);
//          ms.addLast(i);
//          assertEquals(ns,ms);
//          assertEquals
//      }
//      for(int i=1 ; i <=3;i++){
//          ms.removeLast();
//          ns.removeLast();
//          assertEquals(ns,ms);
//      }
//  }
  // 自己还是写不来单元测试!!!
    //自己的单元测试需要接收参数
    //自己的单元测试不会使用assertEquals,根本没有搞清楚别人想用什么啊我日
}


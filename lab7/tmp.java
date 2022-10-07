
import org.checkerframework.checker.units.qual.A;

import java.util.*;
import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.List;

public class tmp {

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    class Solution {
        ListNode[] m;
        ListNode prev;
        public ListNode removeNthFromEnd(ListNode head, int n) {
            m = new ListNode[n];
            if (helpfunction(head, n , 0) == null){
                return head.next;
            }
            return head;
        }

        public ListNode helpfunction(ListNode head, int n, int num){
            if (num == n){
                num = 0;
            }
            if (head != null){
                prev = m[num];
                m[num] = head;
                return helpfunction(head.next, n , num + 1);
            } else {
                if (prev == null){
                    return null;
                }
                prev.next = m[(num + 1) % n];
                return prev;
            }
        }
    }

}

//class Solution 2022 10 06 {(没有优化，应该使用转置链表的)
//    public boolean isPalindrome(tmp.ListNode head) {
//        LinkedList<tmp.ListNode> reversed = new LinkedList<tmp.ListNode>();
//        tmp.ListNode tmp = head;
//        while (tmp != null) {
//            reversed.add(0,tmp);
//            tmp = tmp.next;
//        }
//        while (head != null){
//            if (head.val != reversed.get(0).val) {
//                return false;
//            }
//            head = head.next;
//            reversed.remove(0);
//        }return true;
//    }
//}
//
//    List<Integer>ans=new ArrayList<>(); //记录最终的结果
//    int[]index; //原数组的索引数组，存储着原数组中每个元素对应的下标
//    int[]count; //记录题目中所求的count[i]
//
//    //入口
//    public List<Integer> countSmaller(int[] nums){
//        int len=nums.length;
//        index=new int[len];
//        count=new int[len];
//        for (int i = 0; i <nums.length ; i++) {
//            index[i]=i;
//        }
//        MergeSort(nums,0,nums.length-1);
//        for (int i = 0; i <len ; i++) {
//            ans.add(count[i]);
//        }
//        return ans;
//    }
//
//    /**
//     * 归并排序
//     * @param nums 待排序数组
//     * @param start 数组开始的下标
//     * @param end 数组结束的下标
//     */
//    private void MergeSort(int[] nums,int start,int end){
//        if(start<end){
//            int mid=start+(end-start)/2;
//            MergeSort(nums,start,mid); //将无序数组划分
//            MergeSort(nums,mid+1,end); //将无序数组划分
//            merge(nums,start,mid,end); //再将两个有序数组合并,只不过这里要承接统计count[i]的功能
//        }
//    }
//
//    /**
//     *  双指针合并两个有序数组并统计count[i]
//     * @param nums
//     * @param start
//     * @param mid
//     * @param end
//     */
//    private void merge(int[]nums, int start, int mid, int end){
//        int P1=start;
//        int P2=mid+1;
//        int cur=0;
//        int[]tmp=new int[end-start+1]; //临时数组用于存储一次归并过程中排序好的元素，
//        int[]tmpIndex=new int[end-start+1];//临时数组的索引数组，存储这临时数组中每个元素对应的下标
//
//        while (P1<=mid&&P2<=end){
//            if(nums[P1]>nums[P2]){
//                count[index[P1]]+=end-P2+1; //右半部分小于nums[P1]元素的数目
//                tmpIndex[cur]=index[P1]; //记录元素位置的改变
//                tmp[cur]=nums[P1];
//                P1++;
//            }else {
//                tmp[cur]=nums[P2];
//                tmpIndex[cur]=index[P2];
//                P2++;
//            }
//            cur++;
//        }
//        while (P1<=mid){
//            tmp[cur]=nums[P1];
//            tmpIndex[cur]=index[P1];
//            P1++;
//            cur++;
//        }
//        while (P2<=end){
//            tmp[cur]=nums[P2];
//            tmpIndex[cur]=index[P2];
//            P2++;
//            cur++;
//        }
//        for (int i = 0; i < end-start+1 ; i++) {
//            nums[i+start]=tmp[i];
//            index[i+start]=tmpIndex[i];
//        }
//    }
//
//    public static void main(String[] args) {
//        int[]nums={-1,-1};
//        countSmaller cs=new countSmaller();
//        List<Integer>ans=cs.countSmaller(nums);
//        System.out.println(ans);
//    }

/*
*  class Solution {
        public boolean verifyPostorder(int[] postorder) {
            // 单调栈使用，单调递增的单调栈
            Deque<Integer> stack = new LinkedList<>();
            int pervElem = Integer.MAX_VALUE;
            // 逆向遍历，就是翻转的先序遍历
            for (int i = postorder.length - 1; i >= 0; i--) {
                // 左子树元素必须要小于递增栈被peek访问的元素，否则就不是二叉搜索树
                if (postorder[i] > pervElem) {
                    return false;
                }
                while (!stack.isEmpty() && postorder[i] < stack.peek()) {
                    // 数组元素小于单调栈的元素了，表示往左子树走了，记录下上个根节点
                    // 找到这个左子树对应的根节点，之前右子树全部弹出，不再记录，因为不可能在往根节点的右子树走了
                    pervElem = stack.pop();
                }
                // 这个新元素入栈
                stack.push(postorder[i]);
            }
            return true;
        }
    }
* */

//                    if (nums[i] == 0){
//        tmp = nums[first];
//        nums[first] = nums[i];
//        nums[i] = tmp;
//        first += 1;
//    }
//                if (nums[i] == 2){
//        tmp = nums[back];
//        nums[back] = nums[i];
//        nums[i] = tmp;
//        back -= 1;
//    }

/*
public void sortColors(int[] nums) {
            int tmp;
            int first = 0;
            int back = nums.length - 1;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == 0){
                    first += 1;
                }
                if (nums[i] == 2){
                    back -= 1;
                }
            }
            for (int i = 0; i < first; i++) {
                nums[i] = 0;
            }
            for (int i = first; i <= back; i++) {
                nums[i] = 1;
            }
            for (int i = back + 1; i < nums.length; i++) {
                nums[i] = 2;
            }


        }
 */




/*
* class Solution {

        public int findTargetSumWays(int[] nums, int target) {
            int upBoundary = 0;
            for (int i : nums){
                upBoundary += abs(i);
            }
            if (upBoundary < abs(target)){
                return 0;
            }
            final int ZERO = upBoundary +1 ;
            int[][] helpArray = new int[ZERO + upBoundary + 2][nums.length];
            helpArray[nums[0]+ ZERO][0] = 1;
            helpArray[-nums[0]+ ZERO][0] = 1;
            for (int level = 0; level < nums.length - 1; level++){
                for (int i = 0; i < 2 * upBoundary + 3; i++){
                    if (helpArray[i][level] != 0){
                        helpArray[i + nums[level]][level + 1] += helpArray[i][level];
                        helpArray[i - nums[level]][level + 1] += helpArray[i][level];
                    }
                }
            }
            return  helpArray[target + ZERO][nums.length - 1];
        }
    }
*
* */



    /*
      public class ListNode {
          int val;
          ListNode next;
          ListNode(int x) {
              val = x;
              next = null;
          }
      }

    public class Solution 2022-10-02 {
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
//            ArrayDeque<ListNode> firstBFS = new ArrayDeque<ListNode>();
//            ArrayDeque<ListNode> secondBFS = new ArrayDeque<ListNode>();
            Set<ListNode> tmp = new HashSet<ListNode>();
            while (headA != null){
                tmp.add(headA);
                headA = headA.next;
            }
            while (headB.next != null){
                if (tmp.contains(headB)){
                    return headB;
                }
                headB = headB.next;

            }
            return headB;
        }
    }
     */



/*
* public class TreeNode {
//          int val;
//          TreeNode left;
//          TreeNode right;
//          TreeNode() {}
//          TreeNode(int val) { this.val = val; }
//          TreeNode(int val, TreeNode left, TreeNode right) {
//              this.val = val;
//              this.left = left;
//              this.right = right;
//          }
//      }
//
//    class Solution 2022 -09 - 30 {
//        public List<Integer> rightSideView(TreeNode root) {
//            List<Integer> result = new ArrayList<Integer>();
//            helpFunction(root, result, 0);
//            return result;
//        }
//
//        public void helpFunction(TreeNode root, List<Integer> result, int level) {
//            if (root == null) { return;}
//            if (result.size() <= level){
//                result.add(level, root.val);
//            }else {
//                result.remove(level);
//                result.add(level, root.val);
//            }
//            if (root.left != null) {
//                helpFunction(root.left, result ,level + 1);
//            }
//            if (root.right != null) {
//                helpFunction(root.right, result ,level + 1);
//            }
//        }
//    }
*
*
*
*
* */

/*
*     class Solution 2022-10-1 {
        public int numberOfArithmeticSlices(int[] nums) {
            int numLength = nums.length;
            int sum = 0;
            for (int i = 1; i < numLength - 1; i++){
                int diff = nums[i] - nums[i - 1];
                for (int j = i + 1; j < numLength; j++){
                    if (nums[j] - nums[j - 1] == diff){
                        sum += 1;
                    }else {break;}
                }
            }
            return sum;
        }
    }
*
* */
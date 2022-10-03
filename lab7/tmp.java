
import java.util.*;
import static java.lang.Math.abs;

public class tmp {
    class Solution {
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
    }

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
}



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
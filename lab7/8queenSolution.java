import java.util.ArrayList;
import java.util.List;

class Solution {

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> line = new ArrayList<List<String>>();
        int[] result = new int[n];
        cal8queens(0, n, result, line);
        return line;
    }

    public void cal8queens(int row, int n, int[] result, List<List<String>> line) {
        if (row == n){
            line.add(returnQueenList(n, result));
        }
        for (int column = 0;column < n; column++){
            if (isOk(row, column, n, result)){
                result[row] = column;
                cal8queens(row + 1, n, result,line);
            }
        }
    }

    private boolean isOk(int row, int column, int n, int[] result) {
        int leftup = column - 1;
        int rightup = column + 1;
        for (int i = row-1; i >= 0; --i){
            if (result[i] == column) { return false;}
            if (leftup >= 0) {
                if (result[i] == leftup) { return false;}
            }
            if (rightup < n) {
                if (result[i] == rightup) { return false;}
            }
            leftup--;
            rightup++;
        }
        return true;
    }

    private List<String> returnQueenList(int n, int[] result){
        List<String> line;
        line = new ArrayList<>();
        if (n == 1){
            line.add("Q");
            return line;
        }
        for (int row = 0; row < n; ++row) {
            String tmp = ".".repeat(n);
            line.add(tmp.substring(0,result[row]) + "Q" + tmp.substring(result[row] + 1));
        }
        return line;
    }
}
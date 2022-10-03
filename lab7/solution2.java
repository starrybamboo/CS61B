public class solution2 {
    public int main (){
        int row =6;
        int column = 6;
        int horseRow= 3;
        int horseColumn = 3;
        int[][] tileTable = new int[row + 1][column + 1];

        if (horseRow > row || horseColumn > column){
            return 0;
        }

        if(horseRow<=row&&horseColumn<=column) tileTable[horseRow][horseColumn]=1;
        if(horseRow-1<=row&&horseColumn-2<=column) tileTable[horseRow-1][horseColumn-2]=1;
        if(horseRow+1<=row&&horseColumn-2<=column) tileTable[horseRow+1][horseColumn-2]=1;
        if(horseRow-1<=row&&horseColumn+2<=column) tileTable[horseRow-1][horseColumn+2]=1;
        if(horseRow+1<=row&&horseColumn+2<=column) tileTable[horseRow+1][horseColumn+2]=1;
        if(horseRow-2<=row&&horseColumn-1<=column) tileTable[horseRow-2][horseColumn-1]=1;
        if(horseRow+2<=row&&horseColumn-1<=column) tileTable[horseRow+2][horseColumn-1]=1;
        if(horseRow-2<=row&&horseColumn+1<=column) tileTable[horseRow-2][horseColumn+1]=1;
        if(horseRow+2<=row&&horseColumn+1<=column) tileTable[horseRow+2][horseColumn+1]=1;

        System.out.println(helpFunction(0, 0, row, column));
        return 0;
    }

    public int helpFunction(int row, int column,int positionRow ,int positionColumn){
        if(positionColumn == column && positionRow == row){
            return 1;
        }

        if (row == positionRow){
            return helpFunction(row,column + 1, positionRow, positionColumn);
        }
        if(column == positionColumn){
            return helpFunction(row + 1,column, positionRow, positionColumn);
        }
        return helpFunction(row,column + 1, positionRow, positionColumn)
                + helpFunction(row + 1,column, positionRow, positionColumn);
    }
//
//    public void occupyTile(int[][] tileTable, int horseRow, int horseColumn, int i){
//        tileTable[horseRow + i][horseColumn + 3 - i] = 1;
//        tileTable[horseRow + i][horseColumn - 3 + i] = 1;
//        tileTable[horseRow - i][horseColumn + 3 - i] = 1;
//        tileTable[horseRow - i][horseColumn - 3 + i] = 1;
//    }

}

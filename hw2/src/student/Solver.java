package student;

import java.util.*;

/**
 * This class is used to solve nonogram.
 */
class Solver {
    //array which is used to store current version of the nonogram
    private final char[][] result;
    private final int rowsCount;
    private final int columnsCount;

    //all row constrains
    private final RowConstrains[] rowConstrains;
    //all column constrains
    private final ColumnConstrains[] columnConstrains;

    private final DomainFactory domainFactory;

    //result data
    private final List<ResultData> resultData;

    Solver(Data data) {
        this.rowConstrains = data.getRowsConstrains();
        this.columnConstrains = data.getColumnsConstrains();

        rowsCount = data.getRows();
        columnsCount = data.getColumns();

        domainFactory = new DomainFactory();
        result = new char[data.getRows()][data.getColumns()];

        resultData = new LinkedList<>();
    }

    /**
     * Solves nonogram.
     */
    List<ResultData> solve() {
        placeNext(0, 0);
        return resultData;
    }

    /**
     * Assign variable on [row][col] inde.
     */
    private void placeNext(int row, int col) {
        //create domain for the next variable [row][col]
        Set<Character> domain = createDomain(row, col);

        //if can not assign any value, break this branch
        if (domain.isEmpty()) {
            return;
        }

        for (char toPlace : domain) {
            //assign variable
            result[row][col] = toPlace;

            //move to next variable
            if (columnsCount > col + 1) {
                //if one column is finished and all variables are assigned, check constrain violation
                if (row == rowsCount - 1 && !isColumnCorrect(col)) return;

                placeNext(row, col + 1);
            } else if (rowsCount > row + 1) {
                //if one row is finished and all variables are assigned, check constrain violation
                if (!isRowCorrect(row)) return;

                placeNext(row + 1, 0);
            } else {
                //whole branch is terminated, algorithm assigned all variables, check constrain violation
                if (!isRowCorrect(rowsCount - 1) || !isColumnCorrect(columnsCount - 1)) {
                    return;
                }

                //if there is none constrain violation, we have solution, add it to the result list
                char[][] arrayToAdd = new char[rowsCount][columnsCount];
                for (int i = 0; i < rowsCount; i++) {
                    System.arraycopy(result[i], 0, arrayToAdd[i], 0, columnsCount);
                }

                resultData.add(new ResultData(arrayToAdd));
            }
        }
    }

    /**
     * Checks constrain violation in the column with columnIdx.
     */
    private boolean isColumnCorrect(int columnIdx) {
        char[] data = new char[rowsCount];
        for (int i = 0; i < data.length; i++) {
            data[i] = result[i][columnIdx];
        }
        return domainFactory.createDomain(data, rowsCount, columnConstrains[columnIdx].constrains) == null;
    }

    /**
     * Checks constrain violation in the row with rowIdx.
     */
    private boolean isRowCorrect(int rowIdx) {
        return domainFactory.createDomain(result[rowIdx], columnsCount, rowConstrains[rowIdx].constrains) == null;
    }

    /**
     * Creates domain (all possible values for the variable)
     */
    private Set<Character> createDomain(int row, int col) {
        Set<Character> domain = new HashSet<>();
        //create domain according to the row constrains
        Collection<Character> rowD = createDomainForRow(row, col);
        //create domain according to the column constrains
        Collection<Character> colD = createDomainForColumn(row, col);

        //create intersection -> values in the intersection can be assigned without any constrain violation
        for (Character character : rowD) {
            if (colD.contains(character)) {
                domain.add(character);
            }
        }
        return domain;
    }

    /**
     * Creates domain for particular row.
     */
    private Collection<Character> createDomainForRow(int row, int col) {
        char[] data = result[row];
        List<Tuple> con = rowConstrains[row].constrains;
        return domainFactory.createDomain(data, col, con);
    }

    /**
     * Creates domain for particular column.
     */
    private Collection<Character> createDomainForColumn(int row, int col) {
        char[] data = new char[rowsCount];
        for (int i = 0; i < data.length; i++) {
            data[i] = result[i][col];
        }

        List<Tuple> con = columnConstrains[col].constrains;
        return domainFactory.createDomain(data, row, con);
    }
}

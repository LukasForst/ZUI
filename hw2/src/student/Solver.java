package student;

import java.util.*;

public class Solver {
    private final char[][] result;
    private final int rowsCount;
    private final int columnsCount;

    private final RowConstrains[] rowConstrains;
    private final ColumnConstrains[] columnConstrains;

    private final DomainFactory domainFactory;

    private final List<ResultData> resultData;

    public Solver(Data data) {
        this.rowConstrains = data.getRowsConstrains();
        this.columnConstrains = data.getColumnsConstrains();

        rowsCount = data.getRows();
        columnsCount = data.getColumns();

        domainFactory = new DomainFactory();
        result = new char[data.getRows()][data.getColumns()];

        resultData = new LinkedList<>();
    }

    public List<ResultData> solve() {
        placeNext(0, 0);
        return resultData;
    }

    private void placeNext(int row, int col) {
        Set<Character> domain = createDomain(row, col);
        if (domain.isEmpty()) {
            return;
        }

        for (char toPlace : domain) {
            result[row][col] = toPlace;
            if (columnsCount > col + 1) {
                if (row == rowsCount - 1 && !isColumnCorrect(col)) return;

                placeNext(row, col + 1);
            } else if (rowsCount > row + 1) {
                if (!isRowCorrect(row)) return;

                placeNext(row + 1, 0);
            } else {
                if (!isRowCorrect(rowsCount - 1) || !isColumnCorrect(columnsCount - 1)) {
                    return;
                }

                char[][] arrayToAdd = new char[rowsCount][columnsCount];
                for (int i = 0; i < rowsCount; i++) {
                    System.arraycopy(result[i], 0, arrayToAdd[i], 0, columnsCount);
                }

                resultData.add(new ResultData(arrayToAdd));
            }
        }
    }

    private boolean isColumnCorrect(int columnIdx) {
        char[] data = new char[rowsCount];
        for (int i = 0; i < data.length; i++) {
            data[i] = result[i][columnIdx];
        }
        return domainFactory.createDomain(data, rowsCount, columnConstrains[columnIdx].constrains) == null;
    }

    private boolean isRowCorrect(int rowIdx) {
        return domainFactory.createDomain(result[rowIdx], columnsCount, rowConstrains[rowIdx].constrains) == null;
    }

    private Set<Character> createDomain(int row, int col) {
        Set<Character> domain = new HashSet<>();
        Collection<Character> rowD = createDomainForRow(row, col);
        Collection<Character> colD = createDomainForColumn(row, col);

        for (Character character : rowD) {
            if (colD.contains(character)) {
                domain.add(character);
            }
        }
        return domain;
    }

    private Collection<Character> createDomainForRow(int row, int col) {
        char[] data = result[row];
        List<Tuple> con = rowConstrains[row].constrains;
        return domainFactory.createDomain(data, col, con);
    }

    private Collection<Character> createDomainForColumn(int row, int col) {
        char[] data = new char[rowsCount];
        for (int i = 0; i < data.length; i++) {
            data[i] = result[i][col];
        }

        List<Tuple> con = columnConstrains[col].constrains;
        return domainFactory.createDomain(data, row, con);
    }
}

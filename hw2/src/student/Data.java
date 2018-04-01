package student;

import java.util.List;

class Data {
    private final int rows;
    private final int columns;

    private final RowConstrains[] rowsConstrains;
    private final ColumnConstrains[] columnsConstrains;

    Data(RowConstrains[] rowsConstrains, ColumnConstrains[] columnsConstrains) {
        this.rows = rowsConstrains.length;
        this.columns = columnsConstrains.length;
        this.rowsConstrains = rowsConstrains;
        this.columnsConstrains = columnsConstrains;
    }

    int getRows() {
        return rows;
    }

    int getColumns() {
        return columns;
    }

    RowConstrains[] getRowsConstrains() {
        return rowsConstrains;
    }

    ColumnConstrains[] getColumnsConstrains() {
        return columnsConstrains;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(rows).append(",").append(columns).append('\n');
        for (RowConstrains ro :
                rowsConstrains) {
            sb.append(ro.toString()).append('\n');
        }
        for (ColumnConstrains co :
                columnsConstrains) {
            sb.append(co.toString()).append('\n');
        }

        return sb.toString();
    }
}

class Tuple {
    private char color;
    private int blockSize;

    Tuple(char color, int blockSize) {
        this.color = color;
        this.blockSize = blockSize;
    }

    char getColor() {
        return color;
    }

    int getBlockSize() {
        return blockSize;
    }

    @Override
    public String toString() {
        return "" + color + "," + blockSize;
    }
}

class RowConstrains {
    final List<Tuple> constrains;

    RowConstrains(List<Tuple> constrains) {
        this.constrains = constrains;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Tuple t :
                constrains) {
            sb.append(t.toString()).append(",");
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}

class ColumnConstrains {
    final List<Tuple> constrains;

    ColumnConstrains(List<Tuple> constrains) {
        this.constrains = constrains;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Tuple t : constrains) {
            sb.append(t.toString()).append(",");
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}

class ResultData {
    private char[][] data;

    ResultData(char[][] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] aData : data) {
            for (char anAData : aData) {
                sb.append(anAData);
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}

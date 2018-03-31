package student;

import java.util.List;

class Data {
    private final int rows;
    private final int columns;

    private final RowConstrains[] rowsConstrains;
    private final ColumnConstrains[] columnsConstrains;

    public Data(RowConstrains[] rowsConstrains, ColumnConstrains[] columnsConstrains) {
        this.rows = rowsConstrains.length;
        this.columns = columnsConstrains.length;
        this.rowsConstrains = rowsConstrains;
        this.columnsConstrains = columnsConstrains;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public RowConstrains[] getRowsConstrains() {
        return rowsConstrains;
    }

    public ColumnConstrains[] getColumnsConstrains() {
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

    public Tuple(char color, int blockSize) {
        this.color = color;
        this.blockSize = blockSize;
    }

    public char getColor() {
        return color;
    }

    public int getBlockSize() {
        return blockSize;
    }

    @Override
    public String toString() {
        return "" + color + "," + blockSize;
    }
}

class RowConstrains {
    public final int idx;
    public final List<Tuple> constrains;

    public RowConstrains(int idx, List<Tuple> constrains) {
        this.idx = idx;
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
    public final int idx;
    public final List<Tuple> constrains;

    public ColumnConstrains(int idx, List<Tuple> constrains) {
        this.idx = idx;
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

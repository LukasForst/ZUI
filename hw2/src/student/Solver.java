package student;

import java.util.*;

public class Solver {
    private final Data data;
    private final char[][] result;

    private final RowConstrains[] rowConstrains;
    private final ColumnConstrains[] columnConstrains;


    public Solver(Data data) {
        this.data = data;
        this.result = new char[data.getRows()][data.getColumns()];
        this.rowConstrains = data.getRowsConstrains();
        this.columnConstrains = data.getColumnsConstrains();
    }

    public char[][] solve(Data data) {

        //// TODO: 31-Mar-18 do stuff

        return result;
    }

    private void placeNext(int row, int col, Set<Character> domain) {

    }

    private Set<Character> createDomain(int row, int col) {
        Set<Character> domain = new HashSet<>();
        domain.addAll(createDomainForRow(row, col));
        domain.addAll(createDomainForColumn(row, col));
        return domain;
    }

    private Collection<Character> createDomain(char[] data, int idx, List<Tuple> constrains){
        Collection<Character> rowDomain = new LinkedList<>();

        char[] rowData = data;
        Iterator<Tuple> constrainsIterator = constrains.iterator();

        if (!constrainsIterator.hasNext()) {
            rowDomain.add('_');
            return rowDomain;
        }

        char previousChar = '\n';
        int currentCharCount = 0;
        boolean shouldBeEmpty = false;
        Tuple currentConstrain = null;

        for (int i = 0; i < idx; i++) {
            char currentChar = rowData[i];

            if (currentChar == '_') {
                previousChar = currentChar;
                continue;
            } else if (currentChar == previousChar) {
                currentCharCount++;
                assert currentConstrain != null;
                assert currentConstrain.getBlockSize() >= currentCharCount;
            } else {
                if (!constrainsIterator.hasNext()) {
                    shouldBeEmpty = true;
                    break;
                }
                currentCharCount = 1;
                currentConstrain = constrainsIterator.next();
            }

            previousChar = currentChar;
        }

        if(currentConstrain == null){
            currentConstrain = constrainsIterator.next();
            rowDomain.add('_');
            rowDomain.add(currentConstrain.getColor());
        } else if(shouldBeEmpty){
            rowDomain.add('_');
        } else if(previousChar == currentConstrain.getColor()){
            if(currentCharCount < currentConstrain.getBlockSize()){
                rowDomain.add('_');
                rowDomain.add(currentConstrain.getColor());
            } else if(currentCharCount == currentConstrain.getBlockSize()){
                rowDomain.add('_');
            } else{
                System.err.println("Char: " + previousChar);
                System.err.println("Constrain: " + currentConstrain);
                assert false;
            }
        }

        return rowDomain;
    }

    private Collection<Character> createDomainForRow(int row, int col) {
        Collection<Character> rowDomain = new LinkedList<>();

        char[] rowData = result[row];
        List<Tuple> constrains = rowConstrains[row].constrains;
        Iterator<Tuple> constrainsIterator = constrains.iterator();

        if (!constrainsIterator.hasNext()) {
            rowDomain.add('_');
            return rowDomain;
        }

        char previousChar = '\n';
        int currentCharCount = 0;
        boolean shouldBeEmpty = false;
        Tuple currentConstrain = null;

        for (int i = 0; i < col; i++) {
            char currentChar = rowData[col];

            if (currentChar == '_') {
                previousChar = currentChar;
                continue;
            } else if (currentChar == previousChar) {
                currentCharCount++;
                assert currentConstrain != null;
                assert currentConstrain.getBlockSize() >= currentCharCount;
            } else {
                if (!constrainsIterator.hasNext()) {
                    shouldBeEmpty = true;
                    break;
                }
                currentCharCount = 1;
                currentConstrain = constrainsIterator.next();
            }

            previousChar = currentChar;
        }

        if(currentConstrain == null){
            currentConstrain = constrainsIterator.next();
            rowDomain.add('_');
            rowDomain.add(currentConstrain.getColor());
        } else if(shouldBeEmpty){
            rowDomain.add('_');
        } else if(previousChar == currentConstrain.getColor()){
            if(currentCharCount < currentConstrain.getBlockSize()){
                rowDomain.add('_');
                rowDomain.add(currentConstrain.getColor());
            } else if(currentCharCount == currentConstrain.getBlockSize()){
                rowDomain.add('_');
            } else{
                System.err.println("Char: " + previousChar);
                System.err.println("Constrain: " + currentConstrain);
                assert false;
            }
        }

        return rowDomain;
    }

    private Collection<Character> createDomainForColumn(int row, int col) {
        Collection<Character> columnDomain = new LinkedList<>();

        return columnDomain;
    }
}

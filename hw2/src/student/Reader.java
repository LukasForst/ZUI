package student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Reader {
    public Data read() {
        Data result = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String[] firstLine = reader.readLine().split(",");
            int rowsCount = Integer.parseInt(firstLine[0]);
            int columnsCount = Integer.parseInt(firstLine[1]);

            RowConstrains[] rowsConstrains = new RowConstrains[rowsCount];
            ColumnConstrains[] columnConstrains = new ColumnConstrains[columnsCount];

            for (int i = 0; i < rowsCount; i++) {
                String[] line = reader.readLine().split(",");
                rowsConstrains[i] = new RowConstrains(i, getOneLineData(line));
            }

            for (int i = 0; i < columnsCount; i++) {
                String[] line = reader.readLine().split(",");
                columnConstrains[i] = new ColumnConstrains(i, getOneLineData(line));
            }

            result = new Data(rowsConstrains, columnConstrains);
        } catch (IOException e) {
            System.out.println(e);
        }

        return result;
    }

    private List<Tuple> getOneLineData(String[] line) {
        char color = ' ';
        int blockSize;
        List<Tuple> constrains = new LinkedList<>();

        for (int j = 0; j < line.length; j++) {
            if (j % 2 == 1) {
                blockSize = Integer.parseInt(line[j]);
                constrains.add(new Tuple(color, blockSize));
            } else {
                color = line[j].charAt(0);
            }
        }

        return constrains;
    }
}

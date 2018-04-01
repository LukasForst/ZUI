package student;

import java.util.List;

/**
 * <b>Variable</b> - every single box in the resulting 2d array -> variable on position char[x][y]
 *
 * <p>
 * <b>Constrain</b>
 * <ol>
 * <li>there is always at least one empty box between two blocks of the boxes filled with the same color</li>
 * <li>there does not have to be an empty box between the boxes filled with different color</li>
 * <li>the order of the numbers in the legend corresponds to the order of the blocks of the boxes (from left to right, from top to bottom)</li>
 * <li>when there is block which is not same sized as block in the input, and this block is currently being placed, next color has to be same color as the color of the unfinished box</li>
 * </ol>
 * </p>
 *
 * <p>
 * <b>Domain</b>
 * <ul>
 * <li>domain is every possible color (from the input) or '_' char</li>
 * <li>each variable has same start domain -> all colors and '_'</li>
 * <li>class DomainFactory will create already reduced domain for one row or column</li>
 * <li>afterwards intersection of domain for row and domain for column will be created</li>
 * <li>thanks to this intersection we don't have to check constrain violation after every variable assign</li>
 * <li>we only have to check constrain violation for every row and every column in the end of placing that particular row/column</li>
 * </ul>
 * </p>
 * I implemented slightly modified AC-3 algorithm which is creating domain (not only reducing) for next variable according to the constrain.
 */
public class CSPMain {
    public static void main(String[] args) {
        //read data
        Reader reader = new Reader();
        Data data = reader.read();
        Solver solver = new Solver(data);

        //solve nonograms
        List<ResultData> result = solver.solve();

        if (result.size() == 0) System.out.println("null");
        else {
            System.out.println(result.get(0));
            for (int i = 1; i < result.size(); i++) {
                System.out.println();
                System.out.println(result.get(i));
            }
        }
    }
}

package student;

import java.util.List;

public class CSPMain {
    public static void main(String[] args) {
        Reader reader = new Reader();
        Data data = reader.read();
        Solver solver = new Solver(data);
        List<ResultData> result = solver.solve();

        if(result.size() == 0) System.out.println("null");
        else{
            System.out.println(result.get(0));
            for(int i = 1; i < result.size(); i++){
                System.out.println();
                System.out.println(result.get(i));
            }
        }
    }
}

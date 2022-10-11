import com.company.Graph;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static int litteralToVertex(int l){
        if(l >= 0) return 2*(l-1);
        return -2*l - 1;
    }

    public static int VertexToLitteral(int v){
        if(v % 2 == 0) return v/2 + 1;
        return (v - 1)/2;
    }

    public static boolean isValide(ArrayList<LinkedList<Integer>> components){
        for (int i = 0; i < components.size(); i++) {
            for (Integer e : components.get(i)) {
                if(components.contains(-VertexToLitteral(e))) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {

        // read file && count size
        File file = new File("C:\\Users\\Dang Dinh NGUYEN\\Documents\\L3_INFO\\Algo\\2-SAT\\src\\formule-2-sat.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st = br.readLine() ;
        st = br.readLine();
        String[] contenu = st.split(" ");
        int size = Integer.parseInt(contenu[2]) * 2;

        //create graph G and G'
        com.company.Graph<Integer> graph= new Graph(size);

        int label = 0;
        while ((st = br.readLine()) != null) {
                String[] line = st.split(" ");
                int source1 = - Integer.parseInt(line[0]);
                int destination1 = Integer.parseInt(line[1]);
                graph.addArc(litteralToVertex(source1) ,litteralToVertex(destination1),label++);

                int source2 = - Integer.parseInt(line[1]);
                int destination2 = Integer.parseInt(line[0]);
                graph.addArc(litteralToVertex(source2),litteralToVertex(destination2),label++);
        }
        Graph graphTrans = graph.graphTranspose();
        System.out.println("Graph G: ");
        System.out.println(graph.toString());
        System.out.println("Graph G'': ");
        System.out.println(graphTrans.toString());
        System.out.println(isValide(graphTrans.DFS(graph.DFS())));
    }
}

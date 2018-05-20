package helper;

import edge.Edge;
import edge.EdgeFactory;
import graph.ConcreteGraph;
import graph.Graph;
import graph.GraphFactory;
import vertex.Vertex;
import vertex.VertexFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Bridge Pattern, download the factory/ builder pattern
read files, regex, create Graph using the factory: graph,vertex,edgeFactory.
 */

/*
*Class ParseInputHelper:
While you input an file name, the file name will be firstly checked by ParseInputHelper,
and for each line of file input, the line will be sent to function: WarpVertex and
WarpEdge(both Edge and HyperEdge), and the Function Batch Analysis in Parse Input Helper
class is the General Pattern Compiler.

Another condition is use this class as a command middle contact, The class: ParseCommandHelper
 will send them to Parse Input Helper to make them added to the graph built by ParseInputHelper.
 In a word, the parse input helper is use for one graph for the add/delete a(n) vertex/edge,
 and used for one graph only, for the operation on graph.

Interface:
establishGraph(Filename): @return: Graph. input the file name and this function will execute the adder and constructor method.
LabeltoVertex(String label): @return :Vertex .get the store information about label-vertex

getAns():@return Graph.

judgeExist(String label):@return: Boolean, if there exists vertex owning the label return true

ParseInput(String str):private, execute each line

Cmd(Vertex/Edge)(Adder/Deleter): @return: Graph. cmd input interface, public

WrapVertex/Edge(String str):private

*/

public class ParserInputHelper
{
    private String typeName;
    private String []typeVertex;
    private String []typeEdge;
    private String label;
    private String graphType;
    //private Class ObjectClass; //reflection

    private static final String []preCondition = {"GraphType", "GraphName", "VertexType", "Vertex", "EdgeType", "Edge", "HyperEdge"};
    private static final String []graphTypeSet = {"GraphPoet", "SocialNetwork", "NetworkTopology", "MovieGraph"};
    private static final String []edgeTypeSet = {"WordNeighborhood", "FriendTie", "CommentTie", "ForwardTie", "NetworkConnection", "MovieActorRelation", "MovieDirectorRelation", "SameMovieHyperEdge"};
    private static final String []vertexTypeSet = {"Word", "Person", "Computer", "Server", "Router", "Movie", "Actor", "Director"};

    private Map<String , Vertex> stov = new HashMap<>();

    private Graph ans = new ConcreteGraph();
    private GraphFactory gf;

    public Graph establishGraph(String fileSrc) throws Exception
    {
        Scanner sc = new Scanner(new File(fileSrc));
        while(sc.hasNext())
        {
            String nextLine = sc.nextLine();
            ParserInput(nextLine);
        }
        this.ans  = gf.exportGraph();
        return this.ans;
    }

    public void Clear()
    {
        gf = null;
        ans = null;
        stov = new HashMap<>();
    }

    public Vertex LabeltoVertex(String label)
    {
        if(judgeExist(label))
            return this.stov.get(label);
        else
        {
            System.out.println("[E] Vertex does not exist.");
            return null;
        }
    }

    public Graph getAns() {
        return ans;
    }

    public Boolean judgeExist(String label)
    {
        //System.out.println(this.stov.get("Computer1"));
        return this.stov.containsKey(label);
    }

    private void ParserInput(String str) throws Exception {
        String [] spl = str.split("\\s\\=\\s");
        //check rep
        assert spl.length == 2;
        //System.out.println(spl[0]+"   0<- SPL:->1:  "+spl[1]);
        //analysis condition: pre: type
        for(String s: preCondition)
        {
            if(s.equals(spl[0]))
            {
                this.typeName = spl[0];
            }
        }
        //check rep
        assert this.typeName != null;

        if(this.typeName.contains("Graph"))
        {
            if(this.typeName.equals(preCondition[0]))
            {
                graphType = spl[1];
            }
            else
            {
                label = spl[1];
                if(graphType!=null)
                {
                    this.gf = new GraphFactory();
                    this.ans = gf.createGraph(this.label, this.graphType);
                }
            }
            //ObjectClass = Graph.class;
        }
        else
        {
            assert this.ans != null;
            if(this.typeName.equals(preCondition[2]) || this.typeName.equals(preCondition[4]))
            {
                typeVertex = spl[1].split(",");
            }
            else
            {
                if(this.typeName.equals(preCondition[3]))
                {
                    Vertex v=  WrapVertex(spl[1]);
                    if(v!=null) this.ans = gf.addVertex(v);
                }
                else if(this.typeName.equals(preCondition[5]) || this.typeName.equals(preCondition[6]))
                {
                    Edge e = WrapEdge(spl[1]);
                    if(e!=null) this.ans = gf.addEdge(e, true);
                }
            }
        }
    }
    public Graph cmdLabelModify(String typeName, String preLabel, String modifiedLabel) throws Exception {
        if(typeName.equals("vertex"))
        {
            if(this.stov.containsKey(preLabel))
            {
                Vertex tmp = this.stov.get(preLabel);
                this.ans.removeVertex(tmp);
                VertexFactory vf = new VertexFactory();
                Vertex v = vf.createVertexFromPreventVertex(tmp, modifiedLabel);
                this.ans = gf.addVertex(v);
                this.stov.remove(preLabel);
                this.stov.put(modifiedLabel, v);
            }
            else
            {
                System.out.println("[E] the old Label not exists. Error on Modifying Vertex");
            }
        }
        else if(typeName.equals("edge"))
        {
            Set<Edge> ver = (Set<Edge>)this.ans.edges();
            Edge ee = null;
            for(Edge e:ver)
            {
                if(e.getLabel().equals(preLabel))
                {
                    ee = e;
                    break;
                }
            }
            if(ee==null)
            {
                System.out.println("[E] The label of Edge not exists.");
            }
            else
            {
                EdgeFactory ef = new EdgeFactory();
                Edge eee = ef.createEdgeFromPreviousEdge(ee, modifiedLabel);
                this.ans.removeEdge(ee);
                this.ans = gf.addEdge(eee, false);
            }
        }
        else
        {
            System.out.println("[E] Inner Error. On Label Modify.");
        }
        return ans;
    }
    public Graph cmdVertexAdder(String label,String typename,  String typeClass, String[] res) throws Exception {
        if(typeClass.equals("Vertex"))
        {
            VertexFactory vf = new VertexFactory();
            Vertex v = vf.createVertexOfCertainType(typename, label, res);
            this.ans = gf.addVertex(v);
            this.stov.put(label, v);
        }
        return this.ans;
    }
    public Graph cmdVertexDeleter(String regex)
    {
        Pattern p = Pattern.compile(regex);
        for(String s:this.stov.keySet())
        {
            Matcher m = p.matcher(s);
            if(m.matches())
            {
                Vertex v = stov.get(s);
                stov.keySet().removeIf(o -> o.equals(v));
                this.ans.removeVertex(v);
            }
        }
        return this.ans;
    }
    public Graph cmdEdgeAdder(String type, String label, double weight, String[] res) throws Exception {
        EdgeFactory ef = new EdgeFactory();
        Edge anss =  ef.createEdgeOfCertainType(type, label, res, this.stov);
        if(anss!=null) this.ans = gf.addEdge(anss, false);
        return  this.ans;
    }
    public Graph cmdEdgeDeleter(String regex)
    {
        Pattern p = Pattern.compile(regex);
        Set<Edge> suppressed = ans.edges();
        for(Edge e:suppressed)
        {
            if(p.matcher(e.getLabel()).find())
            {
                this.ans.removeEdge(e);
            }
        }
        return ans;
    }
    private String[] BatchAnalysis(String regex)
    {
        String Pat = "[,^(\\s<>{}\")]+";
        Pattern p = Pattern.compile(Pat);
        return p.split(regex);
    }
    private Vertex WrapVertex(String regex) throws Exception
    {
        String []res = BatchAnalysis(regex);
        String []ress = new String[res.length-1];
        assert res.length >= 1;

        for(int i=0;i<res.length;i++)
        {
            //System.out.println(res[i]);
            if(i>=1) ress[i-1] = res[i];
        }

        this.label = res[1];
        this.typeName = res[2];
        VertexFactory vf = new VertexFactory();
        Vertex anss = vf.createVertexOfCertainType(this.typeName, this.label, ress);
        if(ans!=null) this.stov.put(anss.getLabel(), anss);
        return anss;
    }
    private Edge WrapEdge(String regex) throws Exception
    {
        String [] res = BatchAnalysis(regex);
        String []ress = new String[res.length-1];
        assert res.length >= 1;
        for(int i=0;i<res.length;i++)
        {
            //System.out.println(res[i]);
            if(i>=1) ress[i-1] = res[i];
        }
        //assert ress.length == 6;
        //Edge = < Label, type, Weight, StartVertex:label , EndVertex:label , Yes |No>    yes/no:directed
        // eg: Edge = <“C1S1”, “NetworkConnection”, “10”, Computer1”, “Server1", No>
        //{"WordNeighborhood"0, "FriendTie"1, "CommentTie"2, "ForwardTie"3, "NetworkConnection"4, "MovieActorRelation"5, "MovieDirectorRelation"6, "SameMovieHyperEdge"7};
        this.label = ress[0];
        this.typeName = ress[1];
        EdgeFactory ef = new EdgeFactory();
        Edge ans = ef.createEdgeOfCertainType(this.typeName, this.label, ress, this.stov);
        return ans;
    }
}

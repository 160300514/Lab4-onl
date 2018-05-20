package graph;

import edge.Edge;
import vertex.Vertex;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConcreteGraph<L extends Vertex, E extends Edge> implements Graph<L,E>
{

    private Set<L> vertices;
    //private Set<E> edges;
    private Map<L, Set<E>> map;
    private String label;
    private int state;

    public ConcreteGraph()
    {
        this.vertices = new HashSet<>();
        state = -1;
        //this.edges = new HashSet<>();
    }

    @Override
    public Graph clone(){
        ConcreteGraph tmp = new ConcreteGraph();
        tmp.setGraphName(this.label);
        //tmp.setEdges(new HashSet<E>(edges));
        tmp.setVertices(new HashSet<L>(vertices));
        return tmp;
    }

    public void setVertices(Set<L> vertices) {
        this.vertices = vertices;
    }

    public void setMap(Map<L, Set<E>> map) {
        this.map = map;
    }

    public void setGraphName(String graphName)
    {
        this.label = graphName;
    }

    @Override
    public String getLabel() {
        //System.out.println("Super Class");
        return this.label;
    }

    @Override
    public boolean addVertex(L v) {
        return this.vertices.add(v);
    }

    @Override
    public boolean removeVertex(L v) {
        this.vertices.remove(v);
        this.map.remove(v);
        for(L key:this.map.keySet())
        {
            for(Edge e: this.map.get(key))
            {
                if(e.containVertex(v)) {
                    this.map.get(key).remove(e);
                }
            }
        }
        return true;
    }

    @Override
    public Set<L> vertices() {
        return this.vertices;
    }

    @Override
    public Map<L, List<Double>> sources(L target) {
        return null;
    }

    @Override
    public Map<L, List<Double>> targets(L source) {
        return null;
    }

    @Override
    public boolean addEdge(E edge, Boolean filein) throws CloneNotSupportedException {

        return false;
    }

    @Override
    public boolean removeEdge(E edge) {
        return false;
    }

    @Override
    public Set<E> edges() {
        return null;
    }
}

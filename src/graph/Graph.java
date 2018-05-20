package graph;

import edge.Edge;
import vertex.Vertex;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Graph<L,E>
{
    public String getLabel();

    public Graph clone();

    public static <L extends Vertex,E extends Edge> Graph<L,E> empty()
    {
        return new ConcreteGraph<>();
    }

    public boolean addVertex(L v);

    public boolean removeVertex(L v);

    public Set<L> vertices();

    public Map<L, List<Double>> sources(L target);

    public Map<L, List<Double>> targets(L source);

    public boolean addEdge(E edge, Boolean filein) throws CloneNotSupportedException;

    public boolean removeEdge(E edge);

    public Set<E> edges();

    @Override
    public String toString();
}

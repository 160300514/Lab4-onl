package edge;

import vertex.Vertex;

import java.util.Collection;
import java.util.List;
import java.util.Set;

abstract public class Edge
{
    private Collection vertices;
    private String label;
    private double weight; // for weighted graph , non-weighted graph:-1;

    public Edge(String label, double weight)
    {
        this.label = label;
        this.weight = weight;
    }

    public String getLabel()
    {
        return this.label;
    }

    public void weightConfig(double configValue)
    {
        this.weight = configValue;
    }

    abstract public void checkRep();

    abstract public boolean addVertices(List<Vertex> vertices);

    abstract public boolean containVertex(Vertex v);

    abstract public Set<Vertex> vertices();

    abstract public Set<Vertex> sourceVertices();

    abstract public Set<Vertex> targetVertices();

    public double getWeight()
    {
        return this.weight;
    }

    @Override
    abstract public String toString();

    @Override
    abstract public boolean equals(Object obj);

    @Override
    abstract public int hashCode();
}

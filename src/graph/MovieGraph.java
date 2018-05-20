package graph;

import edge.Edge;
import edge.MovieActorRelation;
import edge.MovieDirectorRelation;
import edge.SameMovieHyperEdge;
import vertex.Vertex;

import java.io.StringWriter;
import java.util.*;

public class MovieGraph extends ConcreteGraph<Vertex,Edge> {
    private String label;
    private Map<Vertex, Set<Edge>> map;
    private Set<Vertex> vertices;

    MovieGraph(String label)
    {
        this.label = label;
        this.map = new HashMap<>();
        this.vertices = new HashSet<>();
        super.setGraphName(label);
        super.setVertices(vertices);
        super.setMap(map);
    }

    MovieGraph(String label, Set<Vertex> s, Map<Vertex, Set<Edge>> maps)
    {
        this.label = label;
        this.vertices = new HashSet<>(s);
        this.map = new HashMap<>(maps);
    }

    public MovieGraph clone()
    {
        return new MovieGraph(label, vertices, map);
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean addVertex(Vertex v) {
        return this.vertices.add(v);
    }

    @Override
    public boolean removeVertex(Vertex v) {
        if(v==null||!this.vertices.contains(v)) return false;
        this.vertices.remove(v);
        this.map.remove(v);
        for(Vertex vet: this.map.keySet())
        {
            Iterator it = this.map.get(vet).iterator();
            while(it.hasNext())
            {
                Edge e = (Edge) it.next();
                if(e.containVertex(v))
                    it.remove();
            }
        }
        return true;
    }

    @Override
    public Set<Vertex> vertices() {
        return this.vertices;
    }

    @Override
    public Map<Vertex, List<Double>> sources(Vertex target) {
        Map<Vertex, List<Double>> ans = new HashMap<>();
        for(Edge e: this.map.get(target))
        {
            if(e.getClass().equals(SameMovieHyperEdge.class))
                continue;
            Set<Vertex> vs = e.sourceVertices();
            Double wei = e.getWeight();
            List<Double> tmp = new ArrayList<>();
            tmp.add(wei);
            for (Vertex v : vs)
            {
                if(ans.containsKey(v))
                    ans.get(v).add(wei);
                else
                    ans.put(v, tmp);
            }
        }
        return ans;
    }

    @Override
    public Map<Vertex, List<Double>> targets(Vertex source) {
        return sources(source);
    }


    //protect edge input: clone : only hyper is needed.
    @Override
    public boolean addEdge(Edge edge, Boolean filein) throws CloneNotSupportedException {
        if((edge instanceof SameMovieHyperEdge || edge instanceof MovieDirectorRelation || edge instanceof MovieActorRelation))
        {
            if(edge instanceof SameMovieHyperEdge) {
                edge = (SameMovieHyperEdge)((SameMovieHyperEdge) edge).clone();
            }
            Set<Vertex> v =  edge.sourceVertices();
            for(Vertex ve: v)
            {
                if(this.map.containsKey(ve))
                    this.map.get(ve).add(edge);
                else
                {
                    this.vertices.add(ve);
                    Set<Edge> tmp = new HashSet<>();
                    tmp.add(edge);
                    this.map.put(ve, tmp);
                }
            }
        }
        return true;
    }

    @Override
    public boolean removeEdge(Edge edge) {
        boolean removed = false;
        for(Vertex v: this.map.keySet())
        {
            Iterator it = this.map.get(v).iterator();
            while(it.hasNext())
            {
                Edge e = (Edge) it.next();
                if(edge.equals(e))
                {
                    removed = true;
                    it.remove();
                }
            }
            /*
            for(Edge e: this.map.get(v))
            {
                if(e.equals(edge))
                {
                    this.map.get(v).remove(edge);
                }
            }*/
        }
        return true;
    }

    @Override
    public Set<Edge> edges() {
        Set<Edge> ans=  new HashSet<>();
        this.map.keySet().forEach(vertex -> ans.addAll(this.map.get(vertex)));
        return ans;
    }

    @Override
    public String toString()
    {
        StringWriter swt = new StringWriter();
        swt.write("Graph: MovieGrapph, with vertices:\n");
        for(Vertex v: this.vertices) swt.write("\t"+v.toString()+"\n");
        swt.write("Edges:\n");
        edges().forEach(edge -> swt.write("\t"+edge.toString()+"\n"));
        return swt.toString();
    }
}

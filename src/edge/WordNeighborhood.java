package edge;

import exception.RepFieldException;
import helper.HashEncoderHelper;
import vertex.Vertex;
import vertex.Word;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordNeighborhood extends Edge
{
    private Word source;
    private Word target;

    public WordNeighborhood(String label, double weight, Word src, Word tar) throws RepFieldException {
        super(label, weight);
        if(weight < -0.0)
            throw new RepFieldException("[E] In Class: WordNeighborhood weight cannot be less than 0.");

        assert weight >= 0 && src != null && tar != null;
        this.source = src;
        this.target = tar;
        checkRep();
    }

    public Word getSource()
    {
        return this.source;
    }

    public Word getTarget()
    {
        return this.target;
    }

    public void setWeight(double newWeight)
    {
        super.weightConfig(newWeight);
    }

    @Override
    public void checkRep() {
        assert this.target!=null && this.source!=null && super.getLabel()!=null && super.getWeight()>=0;
    }

    @Override
    public boolean addVertices(List<Vertex> vertices){
        throw new NoSuchMethodError();
    }

    @Override
    public boolean containVertex(Vertex v) {
        if(v instanceof Word)
        {
            Word p = (Word)v;
            return this.source.equals(p) || this.target.equals(p);
        }
        else return false;
    }

    @Override
    public Set<Vertex> vertices() {
        Set<Vertex> ans = new HashSet<>();
        ans.add(this.source);
        ans.add(this.target);
        return ans;
    }

    @Override
    public Set<Vertex> sourceVertices() {
        Set<Vertex> ans = new HashSet<>();
        ans.add(this.source);
        return ans;
    }

    @Override
    public Set<Vertex> targetVertices() {
        Set<Vertex> ans = new HashSet<>();
        ans.add(this.target);
        return ans;
    }

    @Override
    public String toString() {
        StringWriter swt = new StringWriter();
        swt.write("Edge: WordEdge is from:   "+this.source.toString()+"\tto:   "+this.target.toString()+"\tweight is: "+super.getWeight()+"\tis labeled: "+this.getLabel());
        return swt.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof WordNeighborhood)
        {
            WordNeighborhood wn = (WordNeighborhood)obj;
            return wn.getSource().equals(this.source) && wn.getTarget().equals(this.target);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (new HashEncoderHelper()).hash(this.toString());
    }
}

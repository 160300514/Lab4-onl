package edge;

import exception.RepFieldException;
import helper.HashEncoderHelper;
import vertex.Person;
import vertex.Vertex;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ForwardTie extends Edge//put forward
{

    private Person src;
    private Person tar;

    public ForwardTie(String label, double weight, Person src, Person tar) throws RepFieldException {
        super(label, weight);
        if(weight < -0.0)
            throw new RepFieldException("[E] In Class: ForwardTie weight cannot be less than 0.");
        this.src=src;
        this.tar = tar;
        checkRep();
    }

    public Person getTar() {
        return tar;
    }

    public Person getSrc() {
        return src;
    }

    @Override
    public void checkRep() {
        assert this.src!=null &&this.tar!=null && super.getWeight()>=0 && super.getLabel()!=null;
    }

    @Override
    public boolean addVertices(List<Vertex> vertices) {
        throw new NoSuchMethodError();
    }

    @Override
    public boolean containVertex(Vertex v) {
        if(v instanceof Person)
        {
            Person p = (Person) v;
            return p.equals(this.src) || p.equals(this.tar);
        }
        return false;
    }

    @Override
    public Set<Vertex> vertices() {
        Set<Vertex> ans = new HashSet<>();
        ans.add(this.src);
        ans.add(this.tar);
        return ans;
    }

    @Override
    public Set<Vertex> sourceVertices() {
        Set<Vertex> ans = new HashSet<>();
        ans.add(this.src);
        return ans;
    }

    @Override
    public Set<Vertex> targetVertices() {
        Set<Vertex> ans = new HashSet<>();
        ans.add(this.tar);
        return ans;
    }

    @Override
    public String toString() {
        StringWriter swt = new StringWriter();
        swt.write("Edge: ForwardTie is from:   "+this.src.toString()+"\tto:   "+this.tar.toString()+"\tweight is: "+super.getWeight()+"\tis labeled: "+this.getLabel());
        return swt.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ForwardTie)
        {
            ForwardTie ft = (ForwardTie) obj;
            return ft.getLabel().equals(this.getLabel()) && ft.getSrc().equals(this.src) && ft.getTar().equals(this.tar) && ft.getWeight()==this.getWeight();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (new HashEncoderHelper()).hash(this.toString());
    }
}

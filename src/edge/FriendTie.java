package edge;

import exception.RepFieldException;
import helper.HashEncoderHelper;
import vertex.Person;
import vertex.Vertex;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FriendTie extends Edge
{

    private Person src;
    private Person tar;

    public FriendTie(String label, double weight, Person src, Person tar) throws RepFieldException {
        super(label, weight);
        if(weight < -0.0)
            throw new RepFieldException("[E] In Class: FriendTie weight cannot be less than 0.");
        this.src=src;
        this.tar = tar;
        checkRep();
    }

    public Person getSrc() {
        return src;
    }

    public Person getTar() {
        return tar;
    }

    @Override
    public void checkRep() {
        assert this.src!=null && this.tar!= null && this.getLabel()!=null && this.getWeight()>=0;
    }

    @Override
    public boolean addVertices(List<Vertex> vertices) {
        throw new NoSuchMethodError();
    }

    @Override
    public boolean containVertex(Vertex v) {
        if(v instanceof Person)
        {
            Person p = (Person)v;
            return this.src.equals(p) || this.tar.equals(p);
        }
        else return false;
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
        swt.write("Edge: FriendTie is from:   "+this.src.toString()+"\tto:   "+this.tar.toString()+"\tweight is: "+super.getWeight()+"\tis labeled: "+this.getLabel());
        return swt.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof FriendTie)
        {
            FriendTie ft = (FriendTie) obj;
            return ft.getWeight()==this.getWeight() && ft.getLabel().equals(this.getLabel()) && ft.getSrc().equals(this.getSrc()) && ft.getTar().equals(this.tar);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (new HashEncoderHelper()).hash(this.toString());
    }
}

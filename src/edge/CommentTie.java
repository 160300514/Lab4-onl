package edge;

import exception.RepFieldException;
import helper.HashEncoderHelper;
import vertex.Person;
import vertex.Vertex;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommentTie extends Edge implements Cloneable
{

    private Person src;
    private Person tar;

    public CommentTie(String label, double weight, Person src, Person tar) throws RepFieldException {
        super(label, weight);
        if(weight < -0.0)
            throw new RepFieldException("[E] In Class: CommentTie weight cannot be less than 0.");
        this.src=src;
        this.tar = tar;
        checkRep();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Person getSrc() {
        return src;
    }

    public Person getTar() {
        return tar;
    }

    @Override
    public void checkRep()
    {
        assert this.src!=null&&this.tar!=null&&super.getWeight()>=0;
    }

    @Override
    public boolean addVertices(List<Vertex> vertices)
    {
        throw new NoSuchMethodError();
    }

    @Override
    public boolean containVertex(Vertex v)
    {
        if(v instanceof Person)
        {
            Person p = (Person)v;
            //System.out.println(p.toString());
            //System.out.println(this.src.equals(p));
            //System.out.println(this.tar.equals(p));
            return (this.src.equals(p) || this.tar.equals(p));
        }
        else return false;
    }

    @Override
    public Set<Vertex> vertices() {
        Set<Vertex> s = new HashSet<>();
        s.add(this.src);
        s.add(this.tar);
        return s;
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
        swt.write("Edge: CommentTie is from:   "+this.src.toString()+"\tto:   "+this.tar.toString()+"\tweight is: "+super.getWeight()+"\tis labeled: "+this.getLabel());
        return swt.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CommentTie)
        {
            CommentTie ct = (CommentTie) obj;
            return ct.getLabel().equals(this.getLabel()) && ct.getWeight()==this.getWeight() && ct.getSrc().equals(this.src) && ct.getTar().equals(this.tar);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (new HashEncoderHelper()).hash(this.toString());
    }
}

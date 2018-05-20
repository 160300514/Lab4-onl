package edge;

import exception.RepFieldException;
import helper.HashEncoderHelper;
import vertex.Actor;
import vertex.Movie;
import vertex.Vertex;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieActorRelation extends Edge
{
    private Movie m;
    private Actor act;

    public MovieActorRelation(String label, double weight, Movie mov, Actor act) throws RepFieldException {
        super(label, weight);
        if(weight < -0.0)
            throw new RepFieldException("[E] In Class: MovieActorRelation weight cannot be less than 0.");
        this.act = act;
        this.m  =mov;
    }

    public Actor getAct() {
        return act;
    }

    public Movie getMovie() {
        return m;
    }

    @Override
    public void checkRep()
    {
        assert this.act != null && this.m != null && super.getWeight()>=0;
    }

    @Override
    public boolean addVertices(List<Vertex> vertices) {
        throw new NoSuchMethodError();
    }

    @Override
    public boolean containVertex(Vertex v) {
        if(v instanceof Movie)
        {
            Movie p = (Movie) v;
            return this.m.equals(p);
        }
        else if(v instanceof Actor)
        {
            Actor p = (Actor) v;
            return this.act.equals(p);
        }
        else return false;
    }

    @Override
    public Set<Vertex> vertices() {
        Set<Vertex> ans = new HashSet<>();
        ans.add(this.act);
        ans.add(this.m);
        return ans;
    }

    @Override
    public Set<Vertex> sourceVertices() {
        Set<Vertex> ans = new HashSet<>();
        ans.add(this.act);
        ans.add(this.m);
        return ans;
    }

    @Override
    public Set<Vertex> targetVertices() {
        Set<Vertex> ans = new HashSet<>();
        ans.add(this.act);
        ans.add(this.m);
        return ans;
    }

    @Override
    public String toString() {
        StringWriter swt = new StringWriter();
        swt.write("Edge: MovieActorRelation is of the movie:    "+this.m.toString()+"\tActor is:   "+this.act.toString()+"\tweight is: "+super.getWeight()+"\tis labeled: "+this.getLabel());
        return swt.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MovieActorRelation)
        {
            MovieActorRelation ma = (MovieActorRelation) obj;
            return ma.getAct().equals(this.act) && ma.getMovie().equals(this.m) && ma.getLabel().equals(this.getLabel()) && ma.getWeight()==this.getWeight();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (new HashEncoderHelper()).hash(this.toString());
    }
}

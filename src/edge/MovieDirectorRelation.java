package edge;

import helper.HashEncoderHelper;
import vertex.Director;
import vertex.Movie;
import vertex.Vertex;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieDirectorRelation extends Edge
{
    private Movie m;
    private Director d;

    public MovieDirectorRelation(String label, Movie mov, Director dir)
    {
        super(label, -1);
        System.out.println(mov+"\t"+dir);
        this.d = dir;
        this.m = mov;
        checkRep();
    }

    public Director getDirector() {
        return d;
    }

    public Movie getMovie() {
        return m;
    }

    @Override
    public void checkRep()
    {
        double esp  =1e-6;
        assert this.m!=null && this.d !=null && super.getLabel()!=null;// && super.getWeight()>=-1-esp && super.getWeight()<=-1+esp;
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
        else if(v instanceof Director)
        {
            Director p = (Director) v;
            return this.d.equals(p);
        }
        else return false;
    }

    @Override
    public Set<Vertex> vertices() {
        Set<Vertex> ans = new HashSet<>();
        ans.add(this.m);
        ans.add(this.d);
        return ans;
    }

    @Override
    public Set<Vertex> sourceVertices() {
        Set<Vertex> ans = new HashSet<>();
        ans.add(this.m);
        ans.add(this.d);
        return ans;
    }

    @Override
    public Set<Vertex> targetVertices() {
        Set<Vertex> ans = new HashSet<>();
        ans.add(this.m);
        ans.add(this.d);
        return ans;
    }

    @Override
    public String toString() {
        StringWriter swt = new StringWriter();
        swt.write("Edge: MovieDirectorRelation: UnweightedGraph is of the movie:   "+this.m.toString()+"\tDirector:   "+this.d.toString()+"\tis labeled: "+this.getLabel());
        return swt.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MovieDirectorRelation)
        {
            MovieDirectorRelation md = (MovieDirectorRelation) obj;
            return md.getDirector().equals(this.d) && md.getMovie().equals(this.m) && md.getLabel().equals(this.getLabel());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (new HashEncoderHelper()).hash(this.toString());
    }
}

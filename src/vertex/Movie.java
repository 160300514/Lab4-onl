package vertex;

import exception.RepFieldException;
import helper.HashEncoderHelper;

import java.io.StringWriter;

public class Movie extends Vertex
{

    private int yrOnline;// range: 1900,2018
    private String country;
    private double imdbScore;//0.0~10.0

    public Movie(String mvname, int yrOnline, String country, double imdbScore) throws RepFieldException {
        super(mvname);
        double esp = 1e-6;
        if(!(yrOnline >=1900 && yrOnline<= 2018 && imdbScore >=0-esp && imdbScore <=10+esp))
            throw new RepFieldException("[E] year or score field wrong. In Class: Movie");
        this.yrOnline = yrOnline;
        this.country = country;
        this.imdbScore = imdbScore;
    }

    @Override
    public String getLabel() {
        return super.getLabel();
    }

    public int getYrOnline()
    {
        return this.yrOnline;
    }

    public double getImdbScore() {
        return imdbScore;
    }

    public String getCountry() {
        return country;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    void fillVertexInfo(String[] args) {

    }

    @Override
    public String toString() {
        StringWriter swt = new StringWriter();
        swt.write("Movie: Name:    "+super.label+"online years: "+this.yrOnline+"  made in : "+ country+"is scored as :"+imdbScore);
        return swt.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Movie)
        {
            Movie tmp = (Movie) obj;
            return tmp.getCountry().equals(this.country) && tmp.getImdbScore()== this.imdbScore && tmp.getLabel().equals(this.label) && tmp.getYrOnline()==this.yrOnline;
        }
        else return false;
    }

    @Override
    public int hashCode() {
        return (new HashEncoderHelper()).hash("Movie"+this.label+this.yrOnline+this.imdbScore+this.country);
    }
}

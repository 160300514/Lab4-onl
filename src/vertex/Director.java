package vertex;

import exception.RepFieldException;
import helper.HashEncoderHelper;

import java.io.StringWriter;

public class Director extends Vertex
{
    private int age;
    private String Gender;

    public Director(String Name, int age, String Gender) throws RepFieldException {
        super(Name);
        this.age = age;
        if(!(Gender.equals("M")||Gender.equals("F")))
            throw new RepFieldException("[E] Gender choice should be M or F.In Class Director."+Gender+" "+Name);
        this.Gender = Gender;
    }

    @Override
    public void setLabel(String label) {
        super.setLabel(label);
    }

    public String getGender()
    {
        return this.Gender;
    }

    public int getAge()
    {
        return this.age;
    }

    @Override
    void fillVertexInfo(String[] args) {

    }

    @Override
    public String toString() {
        StringWriter swt = new StringWriter();
        swt.write("Director: Name:   "+super.label+", whose age is "+age+", and"+this.Gender);
        return swt.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Director)
        {
            Director tmp = (Director) obj;
            return this.getGender().equals(tmp.Gender) && this.age == tmp.getAge() && tmp.getLabel().equals(super.label);
        }
        else return false;
    }

    @Override
    public int hashCode() {
        return (new HashEncoderHelper()).hash("Director"+age+Gender);
    }
}

package vertex;

import exception.RepFieldException;
import helper.HashEncoderHelper;

import java.io.StringWriter;

public class Actor extends Vertex implements Cloneable
{
    private int age;
    private String Gender;

    public Actor(String Name, int age, String Gender) throws RepFieldException {
        super(Name);
        this.age = age;
        if(!(Gender.equals("M")||Gender.equals("F")))
            throw new RepFieldException("[E] Gender choice should be M or F.In Class Actor.");
        this.Gender = Gender;
    }

    @Override
    public void setLabel(String label) {
        super.setLabel(label);
    }

    @Override
    public String getLabel() {
        return super.getLabel();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Actor act = (Actor) super.clone();
        return act;
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
    void fillVertexInfo(String[] args)
    {

    }

    @Override
    public String toString() {
        StringWriter swt = new StringWriter();
        swt.write("Actor: Name:   "+super.label+", whose age is "+age+", and"+this.Gender);
        return swt.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Actor)
        {
            Actor tmp = (Actor) obj;
            return this.getGender().equals(tmp.Gender) && this.age == tmp.getAge() && tmp.getLabel().equals(super.label);
        }
        else return false;
    }

    @Override
    public int hashCode() {
        return (new HashEncoderHelper()).hash("Actor"+age+Gender);
    }
}

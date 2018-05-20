package vertex;

import helper.HashEncoderHelper;

import java.io.StringWriter;

public class Word extends Vertex
{

    public Word(String label)
    {
        super(label);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    void fillVertexInfo(String[] args)
    {
        throw new UnsupportedOperationException();
    }


    @Override
    public String toString()
    {
        StringWriter swt = new StringWriter();
        swt.write("Word Vertex: label=   "+super.label);
        return swt.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Word)
        {
            Word tmp = (Word) obj;
            return tmp.getLabel().equals(super.label);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return (new HashEncoderHelper()).hash(super.label);
    }
}

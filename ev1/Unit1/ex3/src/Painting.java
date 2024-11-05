public class Painting extends Piece
{
    private String format;
    private PaintType type;

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public PaintType getType()
    {
        return type;
    }

    public void setType(PaintType type)
    {
        this.type = type;
    }
}

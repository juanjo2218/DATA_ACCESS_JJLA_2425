public class Sculpture extends Piece
{
    private Materials materials;
    private Style place;

    public Materials getMaterials()
    {
        return materials;
    }

    public void setMaterials(Materials materials)
    {
        this.materials = materials;
    }

    public Style getPlace()
    {
        return place;
    }

    public void setPlace(Style place)
    {
        this.place = place;
    }
}

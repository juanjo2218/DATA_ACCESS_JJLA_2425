import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Hall
{
    private String name;
    private Set<Piece> collection = new HashSet<>();
    private Museum ubication;
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public void setCollection(HashSet<Piece> collection)
    {
        this.collection = collection;
    }
    public void addPiece(Piece piece)
    {
        piece.setUbication(this);
        collection.add(piece);
    }
    public void removePiece(Piece piece)
    {
        piece.setUbication(null);
        collection.remove(piece);
    }

    public void SetUbication(Museum museum)
    {
        if (ubication != null)
            ubication.removeHall(this);
        ubication = museum;
    }
}

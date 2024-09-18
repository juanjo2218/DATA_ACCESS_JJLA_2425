import java.util.HashSet;
import java.util.Set;

public class Autor
{
    private String name;
    private String nacionality;
    private Set<Piece> pieces = new HashSet<>();

    public void setPieces(Set<Piece> pieces)
    {
        this.pieces = pieces;
    }
    public void addPiece(Piece piece)
    {
        piece.SetAutor(this);
        pieces.add(piece);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNacionality()
    {
        return nacionality;
    }

    public void setNacionality(String nacionality)
    {
        this.nacionality = nacionality;
    }

    public void RemovePiece(Piece piece)
    {
        piece.SetAutor(null);
        pieces.remove(piece);
    }
}

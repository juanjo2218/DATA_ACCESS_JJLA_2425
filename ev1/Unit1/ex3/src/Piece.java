public class Piece
{
    private String name;
    private Hall ubication;
    private Autor autor;

    public Hall getUbication()
    {
        return ubication;
    }

    public void setUbication(Hall ubication)
    {
        if (this.ubication != null)
            this.ubication.removePiece(this);
        this.ubication = ubication;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void SetAutor(Autor autor)
    {
        if (this.autor != null)
            this.autor.RemovePiece(this);
        this.autor = autor;
    }
    public  Autor getAutor()
    {
        return autor;
    }
}

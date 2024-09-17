public class Student
{
    private String name;
    private int mark;

    public String  getName()
    {
        return name;
    }
    public void setName(String newName)
    {
        if (newName == null)
            return;
        name = newName;
    }
    public Integer getMark()
    {
        return mark;
    }
    public void setMark(int newMark)
    {
        mark = newMark;
    }
    public Boolean pass()
    {
        return mark >= 5;
    }
}

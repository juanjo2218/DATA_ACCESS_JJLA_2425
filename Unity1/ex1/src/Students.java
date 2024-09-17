import java.util.ArrayList;

public class Students
{
    private final ArrayList<Student> studentslist = new ArrayList<>();

    // Agrega un nuevo alumno a la lista
    //
    public void addStudent(Student alu)
    {
        if (alu == null)
            return;
        studentslist.add(alu);
    }
    public Student getStudentAt(Integer index)
    {
        return (index >= 0 && index < studentslist.size()) ? studentslist.get(index) : null;
    }

    // Devuelve la nota media de los alumnos
    //
    public float average()
    {
        if (studentslist.isEmpty())
            return 0;
        float average = 0;
        for (Student student : studentslist)
        {
            average += student.getMark();
        }
        return (average / studentslist.size());
    }
}

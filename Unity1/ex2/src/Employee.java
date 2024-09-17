public class Employee extends Person
{
    private float salary;

    public float getSalary()
    {
        return salary;
    }

    public void setSalary(float newSalary) throws Exception
    {
        if (newSalary < 0)
            throw new Exception("INVALID_SALARY");
        salary = newSalary;
    }
}

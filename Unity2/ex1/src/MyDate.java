public class MyDate
{
    private int month = 1;
    private int day = 1;
    private int year = 1;

    public int getMonth()
    {
        return month;
    }

    public void setMonth(int month) throws Exception
    {
        if (month < 0 || month > 12)
            throw new Exception("INVALID_MONTH");
        this.month = month;
    }

    public int getDay()
    {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

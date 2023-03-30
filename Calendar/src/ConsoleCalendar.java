import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;

public class ConsoleCalendar {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter month and year: MM yyyy ");
        int month = in.nextInt();
        int year = in.nextInt();
        in.close();
        // checks valid month
        try {

            if (month < 1 || month > 12)
                throw new Exception("Invalid index for month: " + month);
            printCalendarMonthYear(month, year);}

        catch (Exception e) {
            System.err.println(e.getMessage());
        }


    }
    private static void printCalendarMonthYear(int month, int year) {
        Calendar cal = new GregorianCalendar();
        cal.clear();
        cal.set(year, month -1, 1);

        System.out.println("Calendar for "+ cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.FRANCE) + " " + cal.get(Calendar.YEAR));//to print Calendar for month and year

        int firstWeekdayOfMonth = (cal.get(Calendar.DAY_OF_WEEK)) -1;//which weekday was the first in month
        int numberOfMonthDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //lengh of days in a month
        System.out.println(" voici le nuber ofMonthDays :"+ numberOfMonthDays);
        System.out.println(" voici le firstWeekdayof month :+"+ firstWeekdayOfMonth);
        printCalendar(numberOfMonthDays, firstWeekdayOfMonth);
    }
    private static void printCalendar (int numberOfMonthDays, int firstWeekdayOfMonth) {
        int weekDayIndex = 0;
        System.out.println("Mo  Tu  We  Th  Fr  Sa  Su");

        for (int day = 1; day < firstWeekdayOfMonth; day++) {
            System.out.print("    "); //this loop to print the first day in his correct place
            weekDayIndex++;
        }
        for (int day = 1; day <= numberOfMonthDays; day++) {

            if (day<10) // permet de mieux visualiser le truc;
                System.out.print(day+" ");
            else
                System.out.print(day);
            weekDayIndex++;
            if (weekDayIndex == 7) {
                weekDayIndex = 0;
                System.out.println();
            } else {
                System.out.print("  ");
            }}}
    }

import java.util.*;
import java.lang.IllegalArgumentException;
public class Main {

    public static String getDayOfWeekName(int number) {
        if (number < 1 || number > 7) {
            throw new IllegalArgumentException();
        }
        List<String> dayOfTheWeek = List.of(
                "Mon",
                "Tue",
                "Wed",
                "Thu",
                "Fri",
                "Sat",
                "Sun"
        );
        return dayOfTheWeek.get(--number);
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dayNumber = scanner.nextInt();
        try {
            System.out.println(getDayOfWeekName(dayNumber));
        } catch (Exception e) {
            System.out.println(e.getClass().getName());
        }
    }
}
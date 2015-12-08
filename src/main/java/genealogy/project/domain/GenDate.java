package genealogy.project.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by TValentine on 12/8/15.
 */
@Entity
public class GenDate {
    @Id
    @GeneratedValue
    private long id;
    private int second;
    private int minute;
    private int hour;
    private int day;
    private int month;
    private int year;
    private String timezone;

    public GenDate(int year, int month, int day, int hour, int minute,
                   int second, String timezone) {
        super();
        this.second = second;
        this.minute = minute;
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.year = year;
        this.timezone = timezone;
    }

    public GenDate() {
        // default constructor
    }

    @Override
    public String toString() {

        StringBuffer buffer = new StringBuffer();
        if (year != 0 && month != 0 && day != 0) {
            Calendar calendar = Calendar.getInstance();
            String dayOfWeek = null;
            try {
                calendar.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(""
                        + storableConverter(day) + "/"
                        + storableConverter(month) + "/"
                        + storableConverter(year)));
                dayOfWeek = getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
            } catch (ParseException e) {
                System.out.println("ParseException, day of week could not be parsed for date " + storableConverter(day) + "/"
                        + storableConverter(month) + "/"
                        + storableConverter(year) + ", no day of week will be listed: " + e);
            }

            buffer.append((dayOfWeek == null) ? "" : dayOfWeek + ", ");
            buffer.append(month == 0 ? "" : storableConverter(month));
            buffer.append(day == 0 ? "" : storableConverter(day) + ", ");
            buffer.append(year == 0 ? "" : year);
            buffer.append(hour == 0 ? "" : ", " + storableConverter(hour));
            buffer.append(minute == 0 ? "" : ":" + storableConverter(minute));
            buffer.append(second == 0 ? "" : ":" + storableConverter(second));
            buffer.append(timezone == null ? "" : timezone);
        }
        return super.toString();
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    private String getDayOfWeek(int dayOfWeek) {
        if (dayOfWeek == 1) {
            return "Sunday";
        } else if (dayOfWeek == 2) {
            return "Monday";
        } else if (dayOfWeek == 3) {
            return "Tuesday";
        } else if (dayOfWeek == 4) {
            return "Wednesday";
        } else if (dayOfWeek == 5) {
            return "Thursday";
        } else if (dayOfWeek == 6) {
            return "Friday";
        } else if (dayOfWeek == 7) {
            return "Saturday";
        } else {
            return null;
        }
    }

    public String toStorableString() {
        String storableDay = storableConverter(day);
        StringBuffer buffer = new StringBuffer();

        buffer.append(year == 0 ? "----" : year);
        buffer.append(month == 0 ? "--" : storableConverter(month));
        buffer.append(day == 0 ? "--" : storableConverter(day));
        buffer.append(hour == 0 ? "--" : storableConverter(hour));
        buffer.append(minute == 0 ? "--" : storableConverter(minute));
        buffer.append(second == 0 ? "--" : storableConverter(second));
        buffer.append(timezone == null || "".equals(timezone) ? "---" : timezone);
        return buffer.toString();
    }

    private String storableConverter(int number) {
        return (number < 10) ? "0" + number : "" + number;
    }
}

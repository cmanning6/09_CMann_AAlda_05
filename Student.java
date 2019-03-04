/**
 * @Author Ian Thomas , Chad Manning
 * Created on : February 4th, 2019
 * Instructor : Dr. Wang
 */

import java.util.Random;

public class Student extends Member {
    public Student() {
        super();
        rnd = new Random();
        major = Names.department[rnd.nextInt(Names.department.length)];
        gpa = rnd.nextFloat() * 3 + 1;
        this.generate();
    }

    public String toString() {
        return (String.format("%s %s %.2f", super.toString(), major, gpa));
    }
    
    public String toString(boolean lab) {
        return lab ? "STU " + toString() : toString();
    }

    public String htmlRow() {
        return String.format("               <tr>%s" +
                                "               </tr>", htmlColumns());
    }
    public String htmlColumns() {
        return String.format("%s                  <td>%s</td>" +
                             "\n                  <td>%.2f</td>\n", super.htmlColumns(),
                                                                major, gpa);
    }

    protected String major; float gpa;
}


/**
 * @Author Ian Thomas , Chad Manning
 * Created on : February 4th, 2019
 * Instructor : Dr. Wang
 */
import java.util.Random;

public class Faculty extends Employee {
    public Faculty() {
        super();
        rnd = new Random();
        generate();
        degreeHeld = Names.degree[rnd.nextInt(Names.degree.length)];
    }

    public String toString() {
        return (String.format("%s %s", super.toString(), degreeHeld));
    }

    public String toString(boolean lab) {
        return lab ? "FAC " + toString() : toString();
    }
    
    public String htmlRow() {
        return (String.format("               <tr>%s" +
                                "               </tr>", htmlColumns()));
    }

    public String htmlColumns() {
        return (String.format("%s                  <td></td>\n" +
                        "                  <td>%s</td>\n", super.htmlColumns(),
                    degreeHeld));
    }
    
    protected String degreeHeld;
}


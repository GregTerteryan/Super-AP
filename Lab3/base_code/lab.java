import pkg.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class lab {
    //psvm
    public static void main(String args[]) {
        Path path = Paths.get("births.csv");
        try {
            Scanner sc = new Scanner(new File(path.toAbsolutePath().toString()));
            sc.useDelimiter(",|\n");
            sc.nextLine();
            ArrayList<DayBirth> male = new ArrayList<DayBirth>();
            ArrayList<DayBirth> female = new ArrayList<DayBirth>();
            
            while (sc.hasNext()) {
                int year = sc.nextInt();
                int month = sc.nextInt();
                Integer day;
                try {
                    day = sc.nextInt();
                }
                catch (InputMismatchException bruh) {
                    day = null;
                    sc.next();
                }
                char gender = sc.next().charAt(0);
                int births = sc.nextInt();
                if (gender == (char)(77)) {
                    male.add(new DayBirth(year, month, day, gender, births));
                }
                else {
                    female.add(new DayBirth(year, month, day, gender, births));
                }
            }
            
            BaseClass.mergeSortLab(male);
            BaseClass.mergeSortLab(female);
            try {
                FileWriter fw = new FileWriter("male.csv");
                while (male.size() > 0) {
                    fw.append("" + male.get(0).getYear() + "," + male.get(0).getMonth() + "," + male.get(0).getDay() + "," + male.get(0).getGender() + "," + male.get(0).getBirths() + "\n");
                    male.remove(0);
                }
            }
            catch (IOException l) {
                l.printStackTrace();
            }
            try {
                FileWriter fw = new FileWriter("female.csv");
                while (female.size() > 0) {
                    fw.append("" + female.get(0).getYear() + "," + female.get(0).getMonth() + "," + female.get(0).getDay() + "," + female.get(0).getGender() + "," + female.get(0).getBirths() + "\n");
                    female.remove(0);
                }
            }
            catch (IOException l) {
                l.printStackTrace();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
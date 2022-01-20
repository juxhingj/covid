import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Controller {

    // Function which splits given String into tokens based on the delimiter
    public static String[] mySplit(String st, Character delim) {
        ArrayList<String> ret = new ArrayList<>();
        int i = 0;
        String temp = "";
        while (i < st.length()) {
            temp= "";
            if (st.charAt(i) != delim) {
                while (i<st.length() && st.charAt(i) != delim) {
                    temp += st.charAt(i);
                    i += 1;
                }
                ret.add(temp);
            }
            else {
                ret.add(temp);
            }
            i+=1;
        }
        ret.add(temp);
        String a[] = new String[ret.size()];
        for(i=0; i < ret.size(); i++)
            a[i] = ret.get(i);
        return a;
    }

    // function which returns updated values based on the value of "by"
    public static double getNewValue(double oldVal, Row row, String key, String by) {
        if (by.equals("NC"))
            return oldVal + row.NC;
        else if (by.equals("TC"))
            return oldVal + row.TC;
        else if (by.equals("NCS"))
            return oldVal + row.NCS;
        else if (by.equals("TD"))
            return oldVal + row.TD;
        else if (by.equals("ND"))
            return oldVal + row.ND;
        else if (by.equals("NDS"))
            return oldVal + row.NDS;
        else if (by.equals("RR"))
            return oldVal + row.RR;
        else if (by.equals("NT"))
            return oldVal + row.NT;
        else if (by.equals("TT"))
            return oldVal + row.TT;
        else if (by.equals("SI"))
            return oldVal + row.SI;
        else if (by.equals("POP"))
            return oldVal + row.POP;
        else if (by.equals("MA"))
            return oldVal + row.MA;
        return 0.0;
    }

    public static void main(String[] args) throws IOException {

        // cli contains command line arguments
        Map cli = new HashMap();
        if (args.length != 10) {
            System.out.println("Insufficient Command Line Arguments");
            return;
        }
        for (int i = 0; i < args.length; i+=2) {
            cli.put(args[i].substring(1), args[i+1]);
        }
        cli.replace("limit",  Integer.parseInt((String)cli.get("limit")));
        System.out.println("CLI: " + cli);

        ArrayList<Row> rows = new ArrayList<>();

        // stream the dataset
        Files.lines(new File((String) cli.get("file")).toPath())
                .map(line -> mySplit(line, ','))
                .skip(1)
                .forEach(a -> rows.add(new Row(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7],
                        a[8], a[9], a[16], a[25], a[26], a[47], a[48], a[50])));

        // create the map for storing the aggregates
        Map resMap = new HashMap();
        rows.forEach( row -> {

            // segragete the process by the value of "display"
            if (cli.get("display").equals("CONTINENT")) {
                if (resMap.containsKey(row.CNT)) {
                   double oldVal = (double) resMap.get(row.CNT);
                   double newVal = getNewValue(oldVal, row, row.CNT, (String) cli.get("by"));
                   resMap.replace(row.CNT, newVal);
                }
                else{
                    double newVal = getNewValue(0.0, row, row.CNT, (String) cli.get("by"));
                    resMap.put(row.CNT, newVal);
                }

            }
            else if (cli.get("display").equals("DATE")) {
                if (resMap.containsKey(row.DT)) {
                    double oldVal = (double) resMap.get(row.DT);
                    double newVal = getNewValue(oldVal, row, row.DT, (String) cli.get("by"));
                    resMap.replace(row.DT, newVal);
                }
                else{
                    double newVal = getNewValue(0.0, row, row.DT, (String) cli.get("by"));
                    resMap.put(row.DT, newVal);
                }
            }
            else if (cli.get("display").equals("COUNTRY")) {
                if (resMap.containsKey(row.LOC)) {
                    double oldVal = (double) resMap.get(row.LOC);
                    double newVal = getNewValue(oldVal, row, row.LOC, (String) cli.get("by"));
                    resMap.replace(row.LOC, newVal);
                }
                else{
                    double newVal = getNewValue(0.0, row, row.LOC, (String) cli.get("by"));
                    resMap.put(row.LOC, newVal);
                }
            }

        } );

        // create the Heap to store the result in the intended manner (ASC, DESC) efficiently
        PriorityQueue<Pair> pq;
        if (cli.get("stat").equals("min")) {
            System.out.println("Bottom " + cli.get("limit") + " " + cli.get("display") + " by " + cli.get("by"));
            // create a min Heap
            pq = new PriorityQueue<Pair>((a, b) -> {
                if (a.value > b.value)
                    return 1;
                else if (a.value < b.value)
                    return -1;
                return 0;
            });
        } else {
            System.out.println("Top " + cli.get("limit") + " " + cli.get("display") + " by " + cli.get("by"));
            // create a Max Heap
            pq = new PriorityQueue<Pair>((a, b) -> {
                if (a.value > b.value)
                    return -1;
                else if (a.value < b.value)
                    return 1;
                return 0;
            });
        }

        // add Pairs from map to heap
        resMap.forEach((key, val) -> pq.add(new Pair((String) key,(double) val)));

        // print the result - top/bottom limit values
        int count = 0;
        while (!pq.isEmpty() && count < (Integer)cli.get("limit")) {
            Pair top = pq.poll();
            System.out.println(top);
            count += 1;
        }
    }
}

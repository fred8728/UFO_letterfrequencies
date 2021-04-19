import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.stream.Collectors.toMap;

/**
 * Frequency analysis Inspired by
 * https://en.wikipedia.org/wiki/Frequency_analysis
 *
 * @author kasper
 */
public class OptimizedVersion {

    // Start nanoseconds: 189, 154 ns
    // End nanaseconds: 106, 99 ns


    public static void main(String[] args) throws FileNotFoundException, IOException {
        long t0 = System.nanoTime();
        final int N = 1000;
        String fileName = "C:/Users/fskn/Documents/Softwareudvikling/1 semester/Ufo/PerformanceAndOptimization_week14/letterfrequencies/data/FoundationSeries.txt";
        Reader reader = new BufferedReader(new FileReader(fileName));

        //Optimized
        long freq[] = new long[128];
        optimizedTallyChars(reader, freq);
        optimizedPrintTally(freq);

        long t = System.nanoTime() - t0;
        System.out.println("Time: " + t / 1_000_000 + "ns"); // nanoseconds per kørsels
    }

    private static void optimizedTallyChars(Reader reader, long[] freq) throws IOException {
        int b;
        while ((b = reader.read()) != -1) {
            freq[b]++;
        }
    }

    private static void optimizedPrintTally(long[] freq) { // current score
        int dist = 'a' - 'A'; // 'a' - 'A'
        Map<Character, Long> upperAndlower = new LinkedHashMap();
        for (Character c = 'A'; c <= 'Z'; c++) { // for (Character c = 'A'; c <= 'Z'; c++)
            upperAndlower.put(c, freq[c] + freq[c + dist]);
        }
        Map<Character, Long> sorted = upperAndlower
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        for (Character c : sorted.keySet()) {
            System.out.println("" + c + ": " + upperAndlower.get(c));
            ;
        }
    }
}
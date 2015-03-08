import skalmadka.countMinSketch.CountMinSketch;
import skalmadka.countMinSketch.RedisCountMinSketch;

/**
 * Created by Sunil Kalmadka
 */
public class CountMinSketchTest {

    public static void main(String[] args) {
        CountMinSketch<String> cm = new RedisCountMinSketch<String>(1000, 0.01, "localhost", (short) 6379, "redisCountMinTest");

        cm.updateCount("a", 1);
        cm.updateCount("abc", 1);
        cm.updateCount("a", 1);
        cm.updateCount("abc", 1);
        cm.updateCount("qwerty", 1);
        cm.updateCount("123", 1);

        System.out.println("a:"+cm.getFrequency("a"));
        System.out.println("abc:"+cm.getFrequency("abc"));
        System.out.println("qwerty:"+cm.getFrequency("qwerty"));
        System.out.println("123:"+cm.getFrequency("123"));
        System.out.println("aqwe:"+cm.getFrequency("aqwe"));

        cm.clear();
    }
}


import skalmadka.heavyHitter.CountMinArrayHH;
import skalmadka.heavyHitter.HeavyHitter;

/**
 * Created by Sunil Kalmadka
 */
public class HeavyHitterTest {
    public static void main(String[] args) {
        HeavyHitter<String> hh = new CountMinArrayHH<String>(50, 0.01, 0.4);

        hh.updateCount("a", 1);
        System.out.println("HeavyHitters:" + hh.getHeavyHitters());
        hh.updateCount("abc", 1);
        System.out.println("HeavyHitters:" + hh.getHeavyHitters());
        hh.updateCount("a", 1);
        System.out.println("HeavyHitters:" + hh.getHeavyHitters());
        hh.updateCount("abc", 1);
        System.out.println("HeavyHitters:" + hh.getHeavyHitters());
        hh.updateCount("qwerty", 1);
        System.out.println("HeavyHitters:" + hh.getHeavyHitters());
        hh.updateCount("a", 1);
        System.out.println("HeavyHitters:" + hh.getHeavyHitters());

        hh.clear();
    }

}

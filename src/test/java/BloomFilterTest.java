import skalmadka.bloomfilter.BitSetBloomFilter;
import skalmadka.bloomfilter.BloomFilter;
import skalmadka.bloomfilter.RedisBloomFilter;

/**
 * Created by sunil on 3/3/15.
 */
public class BloomFilterTest {
    public static void main(String[] args) {
        //BloomFilter<String> bf = new BitSetBloomFilter<String>(100, 0.01);
        BloomFilter<String> bf = new RedisBloomFilter<String>(100, 0.01, "localhost", (short) 6379, "redisBloomTest");

        bf.add("sunil");
        System.out.println("sunil : " +  bf.exists("sunil"));
        System.out.println("kalmadka : " +  bf.exists("kalmadka"));
        System.out.println("a : " +  bf.exists("a"));
        System.out.println("A : " +  bf.exists("A"));
        System.out.println("----");

        bf.add("kalmadka");
        System.out.println("sunil : " +  bf.exists("sunil"));
        System.out.println("kalmadka : " +  bf.exists("kalmadka"));
        System.out.println("a : " +  bf.exists("a"));
        System.out.println("A : " +  bf.exists("A"));
        System.out.println("----");

        bf.add("a");
        System.out.println("sunil : " +  bf.exists("sunil"));
        System.out.println("kalmadka : " +  bf.exists("kalmadka"));
        System.out.println("a : " +  bf.exists("a"));
        System.out.println("A : " +  bf.exists("A"));
        System.out.println("----");

        bf.add("A");
        System.out.println("sunil : " +  bf.exists("sunil"));
        System.out.println("kalmadka : " +  bf.exists("kalmadka"));
        System.out.println("a : " +  bf.exists("a"));
        System.out.println("A : " +  bf.exists("A"));
        System.out.println("----");

        bf.clearFilter();
        System.out.println("sunil : " +  bf.exists("sunil"));
        System.out.println("kalmadka : " +  bf.exists("kalmadka"));
        System.out.println("a : " +  bf.exists("a"));
        System.out.println("A : " +  bf.exists("A"));
        System.out.println("----");

        bf.clearFilter();

    }
}

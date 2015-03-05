package skalmadka.bloomfilter;

/**
 * Created by Sunil Kalmadka
 */
public interface BloomFilter<T> {
    public void add(final T obj);
    public boolean exists(final T obj);
    public void clearFilter();
}

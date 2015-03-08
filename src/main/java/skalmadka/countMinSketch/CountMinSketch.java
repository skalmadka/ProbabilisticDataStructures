package skalmadka.countMinSketch;

/**
 * Created by Sunil Kalmadka
 */
public interface CountMinSketch<T> {
    public int updateCount(final T obj,final int count);
    public int getFrequency(final T obj);
    public void clear();
}

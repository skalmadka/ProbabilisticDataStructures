package skalmadka.heavyHitter;

import java.util.Map;

/**
 * Created by Sunil Kalmadka
 */
public interface HeavyHitter<T> {
    public int updateCount(final T obj, int count);
    public int getFrequency(final T obj);
    public Map<T,Integer> getHeavyHitters();
    public void clear();
}

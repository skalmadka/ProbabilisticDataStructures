package skalmadka.heavyHitter;

import skalmadka.countMinSketch.RedisCountMinSketch;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Sunil Kalmadka
 */
public class CountMinRedisHH<T> extends RedisCountMinSketch<T> implements HeavyHitter<T>{

    protected final double fThreshold;
    protected Map<T,Integer> heavyHitterMap;
    protected long N;

    public CountMinRedisHH(final int width,final int hashCount,
                           final String redisHost, final short redisPort,
                           final String countSketchName, double fThreshold) {
        super(width, hashCount, redisHost, redisPort, countSketchName);
        this.fThreshold = fThreshold;
        heavyHitterMap = new HashMap<T, Integer>();
    }

    public CountMinRedisHH(final int expectedUniqueObj,final double desiredFalseProbability,
                           final String redisHost, final short redisPort,
                           final String countSketchName, double fThreshold){
        super(expectedUniqueObj, desiredFalseProbability, redisHost, redisPort, countSketchName);
        this.fThreshold = fThreshold;
        heavyHitterMap = new HashMap<T, Integer>();
    }

    public int updateCount(T obj, int count) {
        int updatedCount = super.updateCount(obj, count);
        N++;

        Iterator hhIterator =  heavyHitterMap.entrySet().iterator();
        while (hhIterator.hasNext()){
            Map.Entry cur = (Map.Entry) hhIterator.next();
            Integer freq = (Integer) cur.getValue();
            if((double)freq/N  < fThreshold){
                hhIterator.remove();
            }
        }

        // System.out.println("Updated Count = "+ updatedCount/N);
        if((double)updatedCount/N >= fThreshold){
            heavyHitterMap.put(obj, updatedCount);
        }

        return updatedCount;
    }

    public int getFrequency(T obj) {
        return super.getFrequency(obj);
    }

    @Override
    public Map<T, Integer> getHeavyHitters() {
        return heavyHitterMap;
    }

    @Override
    public void clear() {
        super.clear();
    }


}

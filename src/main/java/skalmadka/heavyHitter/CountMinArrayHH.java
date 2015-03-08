package skalmadka.heavyHitter;

import skalmadka.countMinSketch.ArrayCountMinSketch;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Sunil Kalmadka
 */
public class CountMinArrayHH<T> extends ArrayCountMinSketch<T> implements HeavyHitter<T>{

    protected final double fThreshold;
    protected Map<T,Integer> heavyHitterMap;
    protected long N;

    public CountMinArrayHH(int width, int hashCount, double fThreshold) {
        super(width, hashCount);
        this.fThreshold = fThreshold;
        heavyHitterMap = new HashMap<T, Integer>();
    }

    public CountMinArrayHH(final int expectedUniqueObj,final double desiredFalseProbability, double fThreshold){
        super(expectedUniqueObj, desiredFalseProbability);
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

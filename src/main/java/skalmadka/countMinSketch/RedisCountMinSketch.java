package skalmadka.countMinSketch;

import redis.clients.jedis.Jedis;
import skalmadka.hash.MurmurHash;

/**
 * Created by Sunil Kalmadka
 */
public class RedisCountMinSketch<T> implements CountMinSketch<T> {
    private final int w;
    private final int d;
    private Jedis jedis;
    private final String countSketchName;

    public RedisCountMinSketch(final int width,final int hashCount,
                               final String redisHost, final short redisPort,
                               final String countSketchName){
        this.w = width;
        this.d = hashCount;

        //Connect Redis
        jedis = new Jedis(redisHost, redisPort);
        this.countSketchName = countSketchName;

        //System.out.println("w=" +w + "\td="+d+"\tfp="+Math.pow(0.5, d) + " for N="+w/2);
    }

    public RedisCountMinSketch(final int expectedUniqueObj,final double desiredFalseProbability,
                               final String redisHost, final short redisPort,
                               final String countSketchName){
        this.w = expectedUniqueObj*2;
        this.d = (int) Math.round(Math.log(desiredFalseProbability) / Math.log(0.5));

        //Connect Redis
        jedis = new Jedis(redisHost, redisPort);
        this.countSketchName = countSketchName;

        //System.out.println("w=" +w + "\td="+d+"\tfp="+Math.pow(0.5, d) + " for N="+w/2);
    }

    @Override
    public void updateCount(T obj, int count) {
        final byte[] bytes = obj.toString().getBytes();
        final int hash1 = MurmurHash.hash32(bytes, bytes.length, 0);
        final int hash2 = MurmurHash.hash32(bytes, bytes.length, hash1);

        int hashBit;
        for(int i=0;i<d;i++){
            hashBit = Math.abs((hash1 + i*hash2)%d);
            jedis.hincrBy(String.format("%s%d", countSketchName, i), Integer.toString(hashBit), (long) count);
        }
    }

    @Override
    public int getFrequency(T obj) {
        int returnFrequency = Integer.MAX_VALUE;

        final byte[] bytes = obj.toString().getBytes();
        final int hash1 = MurmurHash.hash32(bytes, bytes.length, 0);
        final int hash2 = MurmurHash.hash32(bytes, bytes.length, hash1);

        int hashBit;
        for(int i=0;i<d;i++){
            hashBit = Math.abs((hash1 + i*hash2)%d);
            final String curValString = jedis.hget(String.format("%s%d", countSketchName, i), Integer.toString(hashBit));
            final int curValue = curValString==null ? 0 : Integer.parseInt(curValString);

            if(returnFrequency > curValue){
                returnFrequency = curValue;
            }
        }
        return returnFrequency;
    }

    @Override
    public void clearFilter() {
        for(int i=0; i<d; i++) {
            jedis.del(String.format("%s%d", countSketchName, i));
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if(jedis != null) {
            jedis.close();
        }
    }
}

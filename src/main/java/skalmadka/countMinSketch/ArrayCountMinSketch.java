package skalmadka.countMinSketch;

import skalmadka.hash.MurmurHash;

/**
 * Created by Sunil Kalmadka
 */
public class ArrayCountMinSketch<T> implements CountMinSketch<T> {
    protected final int w;
    protected final int d;
    protected int[][] countSketch;

    public ArrayCountMinSketch(final int width,final int hashCount){
        this.w = width;
        this.d = hashCount;
        countSketch = new int[this.d][this.w];

       // System.out.println("w=" +w + "\td="+d+"\tfp="+Math.pow(0.5, d) + " for N="+w/2);
    }

    public ArrayCountMinSketch(final int expectedUniqueObj,final double desiredFalseProbability){
        this.w = expectedUniqueObj*2;
        this.d = (int) Math.round(Math.log(desiredFalseProbability) / Math.log(0.5));
        countSketch = new int[this.d][this.w];

        //System.out.println("w=" +w + "\td="+d+"\tfp="+Math.pow(0.5, d) + " for N="+w/2);
    }

    @Override
    public int updateCount(final T obj, final int count) {
        int updatedFrequency = Integer.MAX_VALUE;

        final byte[] bytes = obj.toString().getBytes();
        final int hash1 = MurmurHash.hash32(bytes, bytes.length, 0);
        final int hash2 = MurmurHash.hash32(bytes, bytes.length, hash1);

        int hashBit;
        for(int i=0;i<d;i++){
            hashBit = Math.abs((hash1 + i*hash2)%d);
            countSketch[i][hashBit] += count;

            if(updatedFrequency > countSketch[i][hashBit]){
                updatedFrequency = countSketch[i][hashBit];
            }
        }

        return updatedFrequency;
    }

    @Override
    public int getFrequency(final T obj) {
        int returnFrequency = Integer.MAX_VALUE;

        final byte[] bytes = obj.toString().getBytes();
        final int hash1 = MurmurHash.hash32(bytes, bytes.length, 0);
        final int hash2 = MurmurHash.hash32(bytes, bytes.length, hash1);

        int hashBit;
        for(int i=0;i<d;i++){
            hashBit = Math.abs((hash1 + i*hash2)%d);
            if(returnFrequency > countSketch[i][hashBit]){
                returnFrequency = countSketch[i][hashBit];
            }
        }
        return returnFrequency;
    }

    @Override
    public void clear() {
        countSketch = new int[this.d][this.w];
    }
}

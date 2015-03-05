package skalmadka.bloomfilter;

import skalmadka.hash.MurmurHash;

import java.util.BitSet;

/**
 * Created by Sunil Kalmadka
 */
public class BitSetBloomFilter<T> implements BloomFilter<T>{
    private final int m;     //Number of bits in BloomFilter
    private final short k;    //Number of hash functions
    private BitSet bitSet;

    public BitSetBloomFilter(int expectedCount, double desiredFalsePositiveRate){
        final Double estimatedBitArraySize = estimateOptimalBitArraySize(expectedCount, desiredFalsePositiveRate);

        if(estimatedBitArraySize > Integer.MAX_VALUE){
            throw new ArrayStoreException("Number of bits required is too large");
        }

        m = estimatedBitArraySize.intValue();
        k = estimateOptimalHashCount(expectedCount, m);

        bitSet = new BitSet(m);
    }

    @Override
    public void add(final T obj){
        final byte[] bytes = obj.toString().getBytes();
        final int hash1 = MurmurHash.hash32(bytes, bytes.length, 0);
        final int hash2 = MurmurHash.hash32(bytes, bytes.length, hash1);

        int hashBit;
        for(int i=0;i<k;i++){
            hashBit = Math.abs((hash1 + i*hash2)%m);
            bitSet.set(hashBit);
        }
    }

    @Override
    public boolean exists(final T obj){
        final byte[] bytes = obj.toString().getBytes();
        final int hash1 = MurmurHash.hash32(bytes, bytes.length, 0);
        final int hash2 = MurmurHash.hash32(bytes, bytes.length, hash1);

        int hashBit;
        for(int i=0;i<k;i++){
            hashBit = Math.abs((hash1 + i*hash2)%m);
            if(!bitSet.get(hashBit)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void clearFilter() {
        bitSet.clear();
    }


    private Double estimateOptimalBitArraySize(int expectedCount, double desiredFalsePositiveRate){
        return Math.ceil( -1 * expectedCount * Math.log(desiredFalsePositiveRate)
                            /((Math.log(2)) * (Math.log(2)))
                        );
    }

    private short estimateOptimalHashCount(int expectedCount, int bitArraySize){
        return  (short) ((bitArraySize/expectedCount) * Math.log(2));
    }

}

# Probabilistic Data Structures
Probabilistic Data Structures for Java with Redis store.

The goal is to make probabilistic data structures easy to use, rely on the basics for implementation and support future optimization.

Redis backing helps in implementing the data structures over parallel/distributed environments. 

Through testing and evaluation are immediate goals.

#### Currently Supported Data Structures
- Bloom Filter
  - BitSet implementation
  - Redis implementation for distributed applications.
- Count-Min-Sketch
  - Array implementation
  - Redis implementation for distributed applications.
- Heavy Hitters using Count-Min-Sketch
  - /*TODO*/
  
  
#### References
- Tarkoma, S.; Rothenberg, C.E.; Lagerspetz, E., "Theory and Practice of Bloom Filters for Distributed Systems," Communications Surveys & Tutorials, IEEE , vol.14, no.1, pp.131,155, First Quarter 2012
- Cormode, G.; Muthukrishnan, S., "Approximating Data with the Count-Min Sketch," Software, IEEE , vol.29, no.1, pp.64,69, Jan.-Feb. 2012
- Murmur Hash implementation : https://github.com/tnm/murmurhash-java/blob/master/src/main/java/ie/ucd/murmur/MurmurHash.java
- Redis : http://redis.io
- Jedis : https://github.com/xetorthio/jedis

Concurrent Implementation utilitising LongAdder and Concurrent Hashmap:
com.demo.model.WordCounterLongAdder

Single Thread Implementation using Trie DataStructure:
com.demo.model.WordCountTrie

HashMap is O(1) best case + 0(m) time to evaluate the key. 
Looking up data in a trie is faster in the worst case, compared to an  hashmap. (No key collisions).
Size of this structure will be alot small as words such as car , carpet and pet  will  only store chars (c,a,r,p,e,t) rather than 3 Strings.
The worst-case lookup speed in an imperfect hash table is O(N) time. (Java 8 is now 0 (log N) as buckets > 8  will turn into a Red Black Tree.)

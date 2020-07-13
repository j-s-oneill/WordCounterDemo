package com.demo.model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.demo.lang.Translator;


public class WordCountLongAdder implements WordCounter {

		private final Translator translator;
	    private final ReentrantReadWriteLock RWLock = new ReentrantReadWriteLock();
	    private final Lock readLock = RWLock.readLock();
	    private final Lock writeLock = RWLock.writeLock();

	public WordCountLongAdder(Translator translator) {
		this.translator = translator;
	}
	
    private final ConcurrentMap<String, LongAdder> map = new ConcurrentHashMap<>();
    
    
   
	public void add(String word) {
		writeLock.lock();
		try {
		
		word = translator.translate(word);
		word = validate(word);	
		map.computeIfAbsent(word,k-> new LongAdder()).increment();
		}
		finally {
			writeLock.unlock();
		}
	
		
	}

    public long count(String word){
    	readLock.lock();
		try {
    	word = translator.translate(word);
    	word = validate(word);	
    	LongAdder adder = map.get(word.toLowerCase());
        if(adder != null){
        	
            return adder.sum();
        }
        return 0;
    } finally {
    	readLock.unlock();
	}
    
    }
}
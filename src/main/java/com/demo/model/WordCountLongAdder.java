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

	// Future Enhancement: I would prefer to call: translate() and validate()		 
	// outside of this client code,  I can then remove the RWlock and let the HashMap and LongAdder handle concurrent increments only
	// as long as I provide a valid/translated word
	// the spec stated I can only have one "add" and  one "count" method so unfortunately I have to wrap with a rwlock.
	// I have commented this out for demo purposes. RWLock will only allow one thread to add() at a time and will be slow.
	
	public WordCountLongAdder(Translator translator) {
		this.translator = translator;
	}

	private final ConcurrentMap<String, LongAdder> map = new ConcurrentHashMap<>();

	@Override
	public void add(String word) {		
		//writeLock.lock();
		try {

			word = translator.translate(word);		 
			word = validate(word);
			map.computeIfAbsent(word, k -> new LongAdder()).increment();
		} finally {
	//		writeLock.unlock();
		}

	}

	@Override
	public long count(String word) {
//		readLock.lock();
		try {
			word = translator.translate(word);
			word = validate(word);
			LongAdder adder = map.get(word.toLowerCase());
			if (adder != null) {

				return adder.sum();
			}
			return 0;
		} finally {
	//		readLock.unlock();
		}

	}
}
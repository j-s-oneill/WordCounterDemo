package com.demo.model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;

import com.demo.lang.Translator;


public class WordCountLongAdder implements WordCounter {

	private final Translator translator;

	public WordCountLongAdder(Translator translator) {
		this.translator = translator;
	}
	
    private ConcurrentMap<String, LongAdder> map = new ConcurrentHashMap<>();
   
	public void add(String word) {
		word = translator.translate(word);
		word = validate(word);		
		map.computeIfAbsent(word.toLowerCase(),k-> new LongAdder()).increment();
	}

    public long count(String word){
    	word = translator.translate(word);
    	word = validate(word);	
    	LongAdder adder = map.get(word.toLowerCase());
        if(adder != null){
            return adder.longValue();
        }
        return 0;
    }
}
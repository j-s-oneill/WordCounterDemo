package com.demo.basic.tdd;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.demo.lang.Translator;
import com.demo.model.WordCountLongAdder;
import com.demo.model.WordCounter;

public class WordCounterLongAdderTest {

    WordCounter wc  =null;
    
    @Before
    public void init() {
        wc = new WordCountLongAdder(new Translator());
    }
    
    @Test
    public void testTranslatorService() {
    	
    	Translator test = Mockito.mock(Translator.class);
        when(test.translate("flower")).thenReturn("flower");
        when(test.translate("blume")).thenReturn("flower");
        when(test.translate("flor")).thenReturn("flower");
        WordCountLongAdder wordCounter = new WordCountLongAdder(test);
        
        wordCounter.add("flower");
        wordCounter.add("blume");
        wordCounter.add("flor");
	   
	   long count = wordCounter.count("flower");
        assertEquals("verify count", 3, count);
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyWord() {
         wc.add("");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testWhitespace() {
         wc.add(" ");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void wordIsNull() {
    wc.add(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void addInvalidWord() {
    	 wc.add("!!ABC");
    }
    
    
    @Test
	public void testSummationWithConcurrency() throws InterruptedException {
	    int numberOfThreads = 100;
	    ExecutorService service = Executors.newFixedThreadPool(16);
	    CountDownLatch latch = new CountDownLatch(numberOfThreads);
	    String word = "Concurrent";
	    
	    Runnable r1= () -> {
	    	wc.add(word);
	    	   latch.countDown();
	    };
	    for (int i = 0; i < numberOfThreads; i++) {
	        service.submit(r1);
	    }
	    latch.await();
	 
		assertEquals(numberOfThreads, wc.count(word));
	}
    
    @Test
    public void variousWords() {
    
    //dog = 4, cat = 1 , apple =2 , badger =0
       wc.add("dog");
	   wc.add("apple");
	   wc.add("dog");
	   wc.add("dog");
	   wc.add("dog");
	   wc.add("cat");
	   wc.add("apple");
	
	   long dog = wc.count("dog");
	   long cat = wc.count("cat");
	   long apple = wc.count("apple");
	   long badger = wc.count("badger");
	   
        assertEquals("verify dog", 4, dog);
        assertEquals("verify cat", 1, cat);
        assertEquals("verify apple", 2, apple);
        assertEquals("verify badger", 0, badger);
    
    }
    

    @Test
    public void singleWord() {
    	wc.add("Bacon");
        long count = wc.count("Bacon");
        assertEquals("verify count", count, 1);
    }
 

    @Test
    public void duplicateWords() {
 
     wc.add("Apple");
     wc.add("Apple");
     wc.add("Apple");
       long count = wc.count("Apple");
        assertEquals("verify count ", count, 3);
    }

}
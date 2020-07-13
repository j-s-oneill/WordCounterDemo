package com.demo.basic.tdd;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.demo.lang.Translator;
import com.demo.model.WordCountTrie;
import com.demo.model.WordCounter;

public class WordCounterTrieTest {

    WordCounter wc  =null;
    
    @Before
    public void init() {
        wc = new WordCountTrie(new Translator());
    }
    
    @Test
    public void testTranslatorService() {
    	
    	Translator test = Mockito.mock(Translator.class);
        when(test.translate("flower")).thenReturn("flower");
        when(test.translate("blume")).thenReturn("flower");
        when(test.translate("flor")).thenReturn("flower");
        WordCountTrie wordCounter = new WordCountTrie(test);
        
        wordCounter.add("flower");
        wordCounter.add("blume");
        wordCounter.add("flor");
	   
	   long count = wordCounter.count("flower");
       assertEquals("verify count", 3, count);
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
    public void singleWordCaseInsensitive() {
    	wc.add("Bacon");
    	wc.add("bacon");
        long count = wc.count("Bacon");
       assertEquals("verify count", count, 2);
    }
    
    @Test
    public void singleWordCaseUpperLower() {
    	wc.add("ORANGES");
    	wc.add("oranges");
        long count = wc.count("oranges");
       assertEquals("verify count", count, 2);
        count = wc.count("ORANGES");
       assertEquals("verify count", count, 2);
    }

    @Test
    public void duplicateWords() {
 
     wc.add("Apple");
     wc.add("Apple");
     wc.add("Apple");
       long count = wc.count("Apple");
       assertEquals("verify count ", count, 3);
    }
    @Test
    public void ranges() {
    	wc.add("aa");
    	wc.add("AA");
    	wc.add("zz");
    	wc.add("ZZ");
        long count1 = wc.count("aa");
       assertEquals("verify count", count1, 2);
       long count2 = wc.count("zz");
       assertEquals("verify count", count2, 2);
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyWord() {
         wc.add("");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCountEmptyWord() {
         wc.count("");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testWhitespace() {
         wc.add(" ");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void countWhitespace() {
         wc.count(" ");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void addWordIsNull() {
    wc.add(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void countWordIsNull() {
    	wc.count(null);
    }    
    
    @Test(expected = IllegalArgumentException.class)
    public void addInvalidWord() {
    	 wc.add("!!ABC");
    }
    @Test(expected = IllegalArgumentException.class)
    public void countInvalidWord() {
    	 wc.count("!!ABC");
    }
    @Test(expected = IllegalArgumentException.class)
    public void addANumber() {
    	 wc.add("98");
    }
    @Test(expected = IllegalArgumentException.class)
    public void countANumber() {
    	 wc.count("100");
    }

}
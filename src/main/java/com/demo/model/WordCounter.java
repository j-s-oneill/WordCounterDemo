package com.demo.model;


import java.util.regex.Pattern;

import com.demo.lang.Dictonary;

public interface WordCounter {

	void add(String word);
	long count(String word);

	default void validate(CharSequence word) {
		if (word == null)
			throw new IllegalArgumentException("Word is null");

		int keyLength = word.length();

		if (keyLength < 0 || keyLength > Dictonary.LONGEST_WORD)
			throw new IllegalArgumentException("Invalid word length, must be between 1 and " + Dictonary.LONGEST_WORD);

		if(!word.chars().allMatch(Character::isLetter)) //short circuit operation
		{
			throw new IllegalArgumentException("Not a valid word , please use letters only ");
		}
		
	
	}
	default void validate(String word) {
		if (word == null || word.isEmpty() )
			throw new IllegalArgumentException("Word is null");

		int keyLength = word.length();

		if (keyLength <= 0 || keyLength > Dictonary.LONGEST_WORD)
			throw new IllegalArgumentException("Invalid word length, must be between 1 and " + Dictonary.LONGEST_WORD);

		if(!word.chars().allMatch(Character::isLetter)) //short circuit operation
		{
			throw new IllegalArgumentException("Not a valid word , please use letters only ");
		}
		
	
	}
	
	default void  validatePattern(String word) {
		if (word == null)
			throw new IllegalArgumentException("Word is null");

		int keyLength = word.length();

		if (keyLength < 0 || keyLength > Dictonary.LONGEST_WORD)
			throw new IllegalArgumentException("Invalid word length, must be between 1 and " + Dictonary.LONGEST_WORD);
		
		if (!Pattern.matches("[a-zA-Z]+", word)) {
			throw new IllegalArgumentException("Not a valid word , please use letters only");
		}
	
	}
	
	
	

}
package com.demo.model;


import static com.demo.lang.Dictionary.LONGEST_WORD;

public interface WordCounter {

	void add(String word);
	long count(String word);

	default String validate(String word) {
		if (word == null || word.isEmpty() )
			throw new IllegalArgumentException("Word is null");

		int keyLength = word.length();

		if (keyLength <= 0 || keyLength > LONGEST_WORD)
			throw new IllegalArgumentException("Invalid word length, must be between 1 and " + LONGEST_WORD);

		if(!word.chars().allMatch(Character::isLetter)) //short circuit operation
		{
			throw new IllegalArgumentException("Not a valid word , please use letters only ");
		}
		return word.toLowerCase();
	}
	
}
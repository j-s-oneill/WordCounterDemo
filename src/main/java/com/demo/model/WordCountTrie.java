package com.demo.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.demo.lang.Dictionary;
import com.demo.lang.Translator;

public class WordCountTrie implements WordCounter {

	private final Translator translator;
	private Node root; // root of trie
	private final ReentrantReadWriteLock RWLock = new ReentrantReadWriteLock();
	private final Lock readLock = RWLock.readLock();
	private final Lock writeLock = RWLock.writeLock();

	// Future Enhancement: I could put out the Trie code and implement the java collection, 
	//allowing for datastructures to easily swapped 
	
	public WordCountTrie(Translator translator) {
		this.translator = translator;
	}

	@Override
	public void add(String word) {
		writeLock.lock();
		try {
		word = translator.translate(word);
		word = validate(word);
		put(word, get(word) + 1);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public long count(String word) {
		readLock.lock();
		try {
		word = validate(word);
		return get(word);
		} finally {
			readLock.unlock();
		}
	}

	private long get(String key) {
		if (root == null)
			return 0;
		Node node = get(root, key, 0); // find the key from the root node and 1st character.
		if (node == null)
			return 0;
		return node.val;
	}

	private Node get(Node node, String key, int index) {
		if (node == null)
			return null;
		if (index == key.length())
			return node; // when we have the char_index= length of the key/word we return

		char c = key.charAt(index);
		return get(node.next[getIndex(c)], key, index + 1); // move to the next node i.e. charater 2
	}

	private void put(String key, long val) {
		root = put(root, key, val, 0);
	}

	private Node put(Node node, String key, long val, int index) {
		if (node == null)
			node = new Node();

		if (index == key.length()) {
			node.val = val;
			return node;
		}
		char c = key.charAt(index);
		node.next[getIndex(c)] = put(node.next[getIndex(c)], key, val, index + 1);

		return node;
	}

	private int getIndex(char character) {
		return character - 'a'; // return character - 97 , we subtract 97 to start at 0 in ASCII we can then
								// have 26 letters
	}

	private static class Node {
		private long val;
		private Node[] next = new Node[Dictionary.ALPHABET_SIZE];
	}
}
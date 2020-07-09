package com.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.demo.lang.Translator;

public class WordCountTrie implements WordCounter {

        private final TrieNode root;
        private final Translator translator;

        public WordCountTrie(Translator translator) {
        	this.translator = translator;
            this.root = new TrieNode();
        }

        public void add(String word) {
        	validate(word);
        	word = translator.translate(word);
          
        	char[] chars = word.toCharArray();

            TrieNode temp = root;

            for(int i = 0; i < chars.length; i++) {

            	char c = chars[i];
            	                        		
                TrieNode x = temp.childsContains(c);

                if(x == null) {
                    TrieNode newNode = new TrieNode();
                    newNode.content = c;
                    temp.childs.add(newNode);
                    temp = newNode;
                } else {
                    x.count++;
                    temp = x;
                }
            }
        }

        public long count(String in) {
        	long count = 0;
            char[] chars = in.toCharArray();

            TrieNode temp = root;

            for(int i = 0; i < chars.length; i++) {

                TrieNode x = temp.childsContains(chars[i]);

                if(x == null) {
                    return 0;
                } else {
                    temp = x;
                }
            }

            if(!temp.equals(root)) {
                count = temp.count;
            }

            return count;
        }
    }

     class TrieNode {

        char content;
        int count = 1;
        List<TrieNode> childs;

        TrieNode() {
            childs = new ArrayList<>();
        }

        TrieNode childsContains(char c) {
            for(TrieNode n : childs) {
                if(n.content == c) {
                    return n;
                }
            }

            return null;
        }
    }

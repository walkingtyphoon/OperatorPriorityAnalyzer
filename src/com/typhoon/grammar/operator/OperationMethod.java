package com.typhoon.grammar.operator;

import com.typhoon.grammar.Grammar;

import java.util.Set;

/**
 * @author walki
 */
public class OperationMethod {

    /**
     * .
     * 获取需要规约的产生式开始文法;
     *
     * @param src        需要规约的产生式;
     * @return 产生式对应的文法开始部分;
     */
    protected String getGrammarStart(String src) {
        // 遍历文法集合;
        Set<Character> characters = Grammar.RESULT_MAP.keySet();
        // 遍历所有的key
        for (Character character : characters) {
            // 通过key获取所有产生式
            for (String s : Grammar.RESULT_MAP.get(character)) {
                // 判断的当前的产生式是否包含在产生式中
                if (s.contains(src)) {
                    String start = String.valueOf(character);
                    return start + s.length();
                }
            }
        }
        return "这是空的";
    }
}

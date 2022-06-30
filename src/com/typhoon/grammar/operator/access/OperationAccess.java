package com.typhoon.grammar.operator.access;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author walki
 */
public interface OperationAccess {

    /**
     * .
     * 获取当前FIRST VT集合;
     *
     * @return 当前文法的所有的firstVT元素;
     */
    HashMap<Character, HashSet<String>> getFistVt();

    /**
     * .
     * 获取当前LAST VT集合;
     * @return 当前文法的所有的LAST VT元素;
     */
    HashMap<Character, HashSet<String>> getLastVt();


    /**.
     * 获取当前文法的优先关系
     * @return 存储优先关系的Map集合;
     */
    HashMap<String,String> getGrammarPriorityRelationships();

    /**.
     * 展示算赋优先关系表
     * @return 二维数组的优先关系表格
     */
    String [][] ShowPriorityRelationshipTable();

}

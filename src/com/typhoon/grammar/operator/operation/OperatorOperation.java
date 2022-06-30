package com.typhoon.grammar.operator.operation;

import java.util.HashMap;
import java.util.HashSet;

/**
 * .
 * 算赋相关操作的接口
 *
 * @author typhoon
 */
public interface OperatorOperation {

    /**
     * .
     * 获取文法的访问集合
     *
     * @param grammarMap     文法集合
     * @param noEndCharacter 非终结符
     * @param accessSet      访问集合
     * @param type           获取的集合类型
     */
    void getAccessMap(HashMap<Character, HashSet<String>> grammarMap, Character noEndCharacter, HashSet<String> accessSet, int type);

    /**.
     * 获取用于符号之间的优先关系;
     * @param type 判断的符号类型，优先级高于低于或者相等;
     * @param src 当前符号;
     * @param next 下一个符号;
     * @param priorityRelationshipTable 存储优先符关系的二维数组;
     * @return 是否符合预期的关系;
     */
    boolean getPriorityRelationship(String type, String src, String next, String[][] priorityRelationshipTable);
}

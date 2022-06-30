package com.typhoon.grammar.operator.operation;

import com.typhoon.grammar.Grammar;

import java.util.HashMap;
import java.util.HashSet;

/**.
 * 算符操作接口的实现类
 * @author typhoon
 */
public class OperatorOperationImpl implements OperatorOperation {


    /**
     * .
     * 获取文法的访问集合
     *
     * @param grammarMap     文法集合
     * @param noEndCharacter 非终结符
     * @param accessSet      访问集合
     * @param type           获取的集合类型
     */
    @Override
    public void getAccessMap(HashMap<Character, HashSet<String>> grammarMap, Character noEndCharacter, HashSet<String> accessSet, int type) {
        // 获取当前非终结符的产生式集合;
        HashSet<String> hashSet = grammarMap.get(noEndCharacter);
        // 声明当前访问到的第一个字符变量;
        char currentCharacter;
        // 声明当前的非终结符;
        char currentNoEndCharacter;
        // 遍历当前的产生式
        for (String s : hashSet) {
            // 判断当前的访问类型是否为FIRST VT集合
            if (type == 0) {
                // 初始化第一个访问到的字符
                currentCharacter = s.charAt(0);
                // 如果当前产生式的第一个元素为终结符
                if (Grammar.isVT(currentCharacter)) {
                    // 那就将他加入访问集合
                    accessSet.add(String.valueOf(currentCharacter));
                    // 直接跳过当前产生式;
                    continue;
                } else if (Grammar.isVN(currentCharacter)) {
                    // 初始化当前非终结符
                    currentNoEndCharacter = currentCharacter;
                    // 遍历当前产生式的所有元素
                    for (int i = 1; i < s.length(); i++) {
                        // 获取当前的产生式的元素;
                        currentCharacter = s.charAt(i);
                        // 如果当前元素为终结符
                        if (Grammar.isVT(currentCharacter)) {
                            // 那就将他加入访问集合元素
                            accessSet.add(String.valueOf(currentCharacter));
                            // 然后结束当前循环
                            break;
                        }
                    }
                    if (currentNoEndCharacter == noEndCharacter) {
                        continue;
                    }
                    // 然后传递当前获取到的非终结符;
                    getAccessMap(grammarMap, currentNoEndCharacter, accessSet, type);
                }
            }
            // 获取LAST VT 集合
            if (type == -1) {
                // 初始化第一个访问到的字符
                currentCharacter = s.charAt(s.length() - 1);
                // 如果当前产生式的第一个元素为终结符
                if (Grammar.isVT(currentCharacter)) {
                    // 那就将他加入访问集合
                    accessSet.add(String.valueOf(currentCharacter));
                    // 判断当前元素是否为非终结符
                } else if (Grammar.isVN(currentCharacter)) {
                    // 初始化当前非终结符
                    currentNoEndCharacter = currentCharacter;
                    // 遍历当前产生式的所有元素
                    for (int i = s.length() - 2; i >= 0; i--) {
                        // 获取当前的产生式的元素;
                        currentCharacter = s.charAt(i);
                        // 如果当前元素为终结符
                        if (Grammar.isVT(currentCharacter)) {
                            // 那就将他加入访问集合元素
                            accessSet.add(String.valueOf(currentCharacter));
                            // 然后结束当前循环
                            break;
                        }
                    }
                    // 如果当前非终结符是开始非终结符
                    if (currentNoEndCharacter == noEndCharacter) {
                        // 那就直接跳过
                        continue;
                    }
                    // 然后传递当前获取到的非终结符;
                    getAccessMap(grammarMap, currentNoEndCharacter, accessSet, type);
                }
            }
        }
    }

    /**
     * .
     * 获取用于符号之间的优先关系;
     *
     * @param type                      判断的符号类型，优先级高于低于或者相等;
     * @param src                       当前符号;
     * @param next                      下一个符号;
     * @param priorityRelationshipTable 存储优先符关系的二维数组;
     * @return 是否符合预期的关系;
     */
    @Override
    public boolean getPriorityRelationship(String type, String src, String next, String[][] priorityRelationshipTable) {
        // 设置一个参考数组用来获取元素对应的列坐标
        String[] flag = priorityRelationshipTable[0];
        // 遍历所有的数组;
        for (String[] string : priorityRelationshipTable) {
            if (src.equals(string[0])) {
                // 遍历对应数组的所有元素
                for (int j = 1; j < string.length; j++) {
                    // 如果当前数组的元素等于下一个元素
                    if (next.equals(flag[j])) {
                        // 判断当前元素是否为
                        if (type.equals(string[j])) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}

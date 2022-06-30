package com.typhoon.grammar.operator.access;

import com.typhoon.grammar.Grammar;
import com.typhoon.grammar.operation.GrammarOperation;
import com.typhoon.grammar.operation.GrammarOperationImpl;
import com.typhoon.grammar.operator.OperationMethod;
import com.typhoon.grammar.operator.operation.OperatorOperation;
import com.typhoon.grammar.operator.operation.OperatorOperationImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author walki
 */
public class OperationAccessImpl extends OperationMethod implements OperationAccess {
    /**
     * .
     * 声明一个文法优先关系集合;
     */
    public static HashMap<String, String> GRAMMAR_PRIORITY_RELATION_SHIPS = new HashMap<>();

    public static String[][] PRIORITY_RELATION_SHIPS_TABLE;

    /**
     * .
     * 创建文法相关操作对象;
     */
    private final GrammarOperation GRAMMAR_OPERATION = new GrammarOperationImpl();

    /**
     * .
     * 创建算符操作对象
     */
    private final OperatorOperation operatorOperation = new OperatorOperationImpl();


    /**
     * .
     * 获取当前FIRST VT集合;
     *
     * @return 当前文法的所有的firstVT元素;
     */
    @Override
    public HashMap<Character, HashSet<String>> getFistVt() {
        // 初始化访问结果的FIRST VT 集合
        HashMap<Character, HashSet<String>> firstVTSet = new HashMap<>();
        // 设置当前非终结符的首先访问集合;
        HashSet<String> resultSet = new HashSet<>();
        // 遍历文法的开始符号;
        for (Character character : Grammar.RESULT_MAP.keySet()) {
            // 调用方法，获取访问集合;
            operatorOperation.getAccessMap(Grammar.RESULT_MAP, character, resultSet, 0);
            // 获取后，将的当前文法的首先访问集合加入结果集合
            firstVTSet.put(character, resultSet);
            // 上传后重新清空列表
            resultSet = new HashSet<>();
        }
        // 返回当前文法的FIRST VT 集合;
        return firstVTSet;
    }

    /**
     * .
     * 获取当前LAST VT集合;
     *
     * @return 当前文法的所有的LAST VT元素;
     */
    @Override
    public HashMap<Character, HashSet<String>> getLastVt() {
        // 设置当前的LAST VT集合;
        HashMap<Character, HashSet<String>> lastVTSet = new HashMap<>();
        // 设置当前非终结符的首先访问集合;
        HashSet<String> resultSet = new HashSet<>();
        // 遍历文法的开始符号;
        for (Character character : Grammar.RESULT_MAP.keySet()) {
            // 调用方法，获取访问集合;
            operatorOperation.getAccessMap(Grammar.RESULT_MAP, character, resultSet, -1);
            // 获取后，将的当前文法的首先访问集合加入结果集合
            lastVTSet.put(character, resultSet);
            // 上传后重新清空列表
            resultSet = new HashSet<>();
        }
        // 返回当前文法的LAST VT 集合
        return lastVTSet;
    }

    /**
     * .
     * 获取当前文法的优先关系
     *
     * @return 存储优先关系的Map集合;
     */
    @Override
    public HashMap<String, String> getGrammarPriorityRelationships() {
        // 创建一个获取访问操作的集合
        OperationAccess operationAccess = new OperationAccessImpl();
        // 创建一个获取第一个文法的开始符号的集合
        HashSet<String> endSet = new HashSet<>();
        endSet.add("#" + Grammar.GRAMMAR_START + "#");
        Grammar.RESULT_MAP.put('#', endSet);
        // 声明当前的产生式元素变量
        char currentElement;
        // 声明下一个产生式元素变量;
        char nextElement;
        // 遍历所有文法的开始符号
        for (Character character : Grammar.RESULT_MAP.keySet()) {
            // 获取对应的产生式
            for (String s : Grammar.RESULT_MAP.get(character)) {

                // 判断当前产生式是否需要比较P->AC
                if (s.length() >= 2) {
                    // 遍历当前字符串的字符元素
                    for (int i = 0; i < s.length() - 1; i++) {
                        // 初始化当前产生式元素
                        currentElement = s.charAt(i);
                        // 初始化下一个产生式元素
                        nextElement = s.charAt(i + 1);
                        // 判断当前产生式的优先级是否大于下一个
                        if (GRAMMAR_OPERATION.isGreater(currentElement, nextElement)) {
                            // 那就将当前比较的结果加入到结果集合中;
                            HashSet<String> hashSet = operationAccess.getLastVt().get(currentElement);
                            // 格式化文法LASTVT产生式列表字符串转化为连续字符串
                            String s1 = hashSet.toString().replaceAll("\\[", "")
                                    .replaceAll("]", "")
                                    .replaceAll(",", "");
                            // 判别后，上传比较左部元素的LAST VT集合以及右部元素，以及专用符号
                            GRAMMAR_PRIORITY_RELATION_SHIPS.put(s1 + "%" + nextElement, "⋗");
                            continue;
                        }

                        // 判断当前产生式的优先级是否小于下一个
                        if (GRAMMAR_OPERATION.isLess(currentElement, nextElement)) {
                            // 将集合中元素替换为字符串
                            HashSet<String> hashSet = operationAccess.getFistVt().get(nextElement);
                            // 格式化文法FIRSTVT产生式列表字符串转化为连续字符串
                            String s1 = hashSet.toString().replaceAll("\\[", "")
                                    .replaceAll("]", "")
                                    .replaceAll(",", "");
                            // 判别后，上传比较左部元素以及右部元素的LAST VT集合，以及专用符号
                            GRAMMAR_PRIORITY_RELATION_SHIPS.put(currentElement + "%" + s1, "⋖");
                        }
                        // 判断当前产生式的优先级是否为普通的相等
                        if (GRAMMAR_OPERATION.isEquals(currentElement, nextElement)) {
                            GRAMMAR_PRIORITY_RELATION_SHIPS.put(currentElement + "%" + nextElement, "≑");
                        }
                    }
                }
                // 判断当前产生式是否大于2
                if (s.length() > 2) {
                    // 重新遍历当前产生式的元素，用于判断是否想等
                    for (int i = 0; i < s.length() - 2; i++) {
                        // 初始化当前元素;
                        currentElement = s.charAt(i);
                        // 初始化下一个元素
                        nextElement = s.charAt(i + 2);
                        // 判断元素是否相等,如果产生式元素的优先级相同
                        if (GRAMMAR_OPERATION.isEquals(currentElement, s.charAt(i + 1), nextElement)) {
                            // 直接上传元素到集合中
                            GRAMMAR_PRIORITY_RELATION_SHIPS.put(currentElement + "  %  " + nextElement, "≑");
                        }
                    }
                }
            }
        }
        // 返回装有终结符关系的集合
        return GRAMMAR_PRIORITY_RELATION_SHIPS;
    }

    /**
     * .
     * 展示算赋优先关系表
     *
     * @return 二维数组的优先关系表格
     */
    @Override
    public String[][] ShowPriorityRelationshipTable() {
        // 获取文法的所有开始符号
        Set<Character> characters = Grammar.RESULT_MAP.keySet();
        // 声明产生式变量
        HashSet<String> hashSet;
        // 设置交换集合
        HashSet<String> tempSet = new HashSet<>();
        // 遍历所有产生式左部元素
        for (Character character : characters) {
            // 初始化产生式集合
            hashSet = Grammar.RESULT_MAP.get(character);
            // 遍历产生式元素
            for (String s : hashSet) {
                // 遍历产生式所有字符元素
                for (int i = 0; i < s.length(); i++) {
                    // 判断当前元素是否为终结符
                    if (Grammar.isVT(s.charAt(i))) {
                        // 将当前元素加入列表中
                        tempSet.add(String.valueOf(s.charAt(i)));
                    }
                }
            }
        }
        // 声明结果数组
        PRIORITY_RELATION_SHIPS_TABLE = new String[tempSet.size() + 1][tempSet.size() + 1];
        // 设置临时变量用于累加元素
        int index = 1;
        // 将所有的终结符都加入到数组中
        for (String s : tempSet) {
            // 将当前所有的终结符加入到第一行，从0开始
            PRIORITY_RELATION_SHIPS_TABLE[0][index] = s;
            // 上传以后数组索引前移一位
            index++;
        }
        // 上传完列以后，重新初始化索引
        index = 1;
        // 将终结符追加到列
        for (String s : tempSet) {
            // 将每一个终结符元素追加到每一个行的第一个元素从第一个数组开始，注意数组是从第0个开始
            PRIORITY_RELATION_SHIPS_TABLE[index][0] = s;
            // 上传完了以后索引加一
            index++;
        }
        // 获取优先关系中的所有key
        Set<String> keySet = GRAMMAR_PRIORITY_RELATION_SHIPS.keySet();
        // 声明一个行元素
        String row;
        // 声明一个列元素
        String colum;
        // 声明当前的优先符关系变量
        String value;
        // 设置一个参考数组用来获取元素对应的列坐标
        String[] flag = PRIORITY_RELATION_SHIPS_TABLE[0];
        // 遍历所有的终结符关系
        for (String s : keySet) {
            // 获取行元素 .trim()为String的方法，去除空格
            row = s.split("%")[0].trim();
            // 获取列元素
            colum = s.split("%")[1].trim();
            // 获取当前非终结符关系对应的优先符关系
            value = GRAMMAR_PRIORITY_RELATION_SHIPS.get(s);
            // 遍历分析表
            for (String[] resultArray : PRIORITY_RELATION_SHIPS_TABLE) {
                // 获取当前元素在第一个数组中的元素位置
                for (int i = 0; i < row.length(); i++) {
                    // 判断当前的元素对应数组位置;
                    if (String.valueOf(row.charAt(i)).equals(resultArray[0])) {
                        // 遍历对应元素
                        for (int j = 0; j < colum.length(); j++) {
                            // 遍历参考数组
                            for (int k = 0; k < flag.length; k++) {
                                // 如果当前元素等于参考数组中的元素
                                if (String.valueOf(colum.charAt(j)).equals(flag[k])) {
                                    // 那么就上传运算符到对应的元素位置;
                                    resultArray[k] = value;
                                }
                            }
                        }
                    }
                }
            }
        }
        // 遍历所有的数组
        for (String[] resultArray : PRIORITY_RELATION_SHIPS_TABLE) {
            // 遍历数组中的元素
            for (int i = 0; i < resultArray.length; i++) {
                // 如果当前的元素为空
                if (resultArray[i] == null) {
                    // 那么就将其替换为空格
                    resultArray[i] = " ";
                }
            }
        }
        // 替换后返回优先关系表
        return PRIORITY_RELATION_SHIPS_TABLE;
    }
}

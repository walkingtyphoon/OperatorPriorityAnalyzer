package com.typhoon.grammar.operator.analyse;

import com.typhoon.grammar.Grammar;
import com.typhoon.grammar.operator.OperationMethod;
import com.typhoon.grammar.operator.access.OperationAccessImpl;
import com.typhoon.grammar.operator.operation.PriorityRelationship.OperatorOperationPriorityRelationship;
import com.typhoon.grammar.operator.operation.PriorityRelationship.OperatorOperationPriorityRelationshipImpl;

import java.util.*;

/**
 * @author typhoon
 */
public class OperationAnalyseImpl extends OperationMethod implements OperationAnalyse {

    private final OperatorOperationPriorityRelationship oopr = new OperatorOperationPriorityRelationshipImpl();

    /**
     * .分析输入串
     *
     * @param inputString 输入的需要分析的文法串
     */
    @Override
    public void analyseSentential(String inputString) {
        // 设置一个符号栈;
        ArrayList<Character> stack = new ArrayList<>();
        // 将#号读入栈中
        stack.add('#');
        // 设置当前栈的深度;
        int stackSize = 0;
        // 当前栈顶元素
        Character topStackElement;
        // 设置一个读取字符数组的索引
        int index = 0;
        // 设置一个当前字符元素;
        Character currentCharacter;
        // 设置一个下一个字符元素;
        char nextCharacter = 0;
        // 查找子串的索引;
        int stackIndex = 0;




        // 设置循环解析输入串
        do {
            // 读取有效化
            if (index != inputString.length()) {
                // 读取下一个字符
                nextCharacter = inputString.charAt(index);
            }
            // 获取当前的栈顶元素;
            topStackElement = stack.get(stackSize);
            // 判断栈顶元素是否为终结符
            if (Grammar.isVT(topStackElement)) {
                stackIndex = stackSize;
            } else {
                stackIndex = stackSize - 1;
            }



            // 查找最作素短语
            while (oopr.isPriorityGreater(""+stack.get(stackIndex), ""+nextCharacter, OperationAccessImpl.PRIORITY_RELATION_SHIPS_TABLE)) {
                System.out.format("%15s", stack);
                System.out.format("%15s", inputString.substring(index));
                // 寻找最左子串
                do {
                    // 获取当前的栈顶元素
                    currentCharacter = stack.get(stackIndex);
                    // 判断下一个元素是否为终结符
                    if (Grammar.isVT(stack.get(stackIndex - 1))) {
                        // 那么就将索引减一；
                        stackIndex -= 1;
                    } else {
                        // 因为不会存在两个相邻的非终结符;
                        stackIndex -= 2;
                    }
                } while (oopr.isPriorityEquals(""+stack.get(stackIndex), ""+currentCharacter, OperationAccessImpl.PRIORITY_RELATION_SHIPS_TABLE));





                // 那么就把S[j + 1] .... S[k]归约为某个N；
                // 1、设置一个文法的开始部分
                String grammarStart;
                // 2、遍历所有的文法产生式;
                for (int i = stackSize; i >= stackIndex + 1; i--) {
                    // 判断当前元素是否为终结符
                    if (Grammar.isVT(stack.get(i))) {
                        // 初始化文法开始部分
                        grammarStart = getGrammarStart(String.valueOf(stack.get(i)));
                        // 如果文法的开始部分不为空
                        if (grammarStart != null) {
                            // 获取栈中元素的深度
                            int grammarLength = Integer.parseInt(grammarStart.substring(1));
                            int deep = stack.size() - grammarLength;
                            int stackLength = stack.size();
                            // 那就弹出对应的元素
                            if (stackLength > deep) {
                                stack.subList(deep, stackLength).clear();
                            }
                            stackSize = stackIndex + 1;
                            System.out.format("%15s %n", "使用" + grammarStart.charAt(0) + "进行规约");
                            // 设置对应索引处的位置
                            stack.add(grammarStart.charAt(0));
                        }
                    }
                }
            }




            if (oopr.isPriorityLess(""+stack.get(stackIndex), ""+nextCharacter, OperationAccessImpl.PRIORITY_RELATION_SHIPS_TABLE)
                    ||
                    oopr.isPriorityEquals(""+stack.get(stackIndex), ""+nextCharacter, OperationAccessImpl.PRIORITY_RELATION_SHIPS_TABLE)) {
                System.out.format("%15s", stack);
                System.out.format("%15s", inputString.substring(index));
                stackSize = stackSize + 1;

                if (nextCharacter != '#') {
                    System.out.format("%15s %n", "移进下一个元素" + nextCharacter);
                    stack.add(nextCharacter);
                } else {
                    stack.add(nextCharacter);
                    System.out.format("%18s %n", stack + "结束");
                }
                index++;
            }
        } while (nextCharacter != '#');
    }
}

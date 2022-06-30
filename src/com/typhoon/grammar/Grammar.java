package com.typhoon.grammar;

import com.typhoon.exception.GrammarTextException;
import com.typhoon.exception.GrammarTextLengthException;
import com.typhoon.exception.OperatorGrammarTextException;
import com.typhoon.grammar.operator.OperationMethod;

import java.util.*;

/**
 * 实现文法的判断；
 * 1、判断是否为文法;
 * 2、判断是否为算符优先文法
 *
 * @author typhoon
 * @Date 2022年5月18日14:44:00
 */
public class Grammar extends OperationMethod {

    /**
     * 文法文本；
     */
    private final String GRAMMAR_TEXT;

    /**
     * .
     * 文法的开始部分
     */
    public static String GRAMMAR_START;

    /**
     * .
     * 声明一个共享的文法集合
     */
    public static HashMap<Character, HashSet<String>> RESULT_MAP;

    /**
     * 创建一个新的语法；
     *
     * @param grammarText 文法文本；
     */
    public Grammar(String grammarText) {
        this.GRAMMAR_TEXT = grammarText;

        GRAMMAR_START = String.valueOf(grammarText.charAt(6));
        // 调用方法判断是否为算符优先文法
    }

    @Override
    public String toString() {
        return "Grammar{" + "GRAMMARTEXT='" + GRAMMAR_TEXT + '\'' + '}';
    }

    /**
     * .
     * 用于识别输入法的文法；
     */
    public boolean initGrammar() {
        // 获取当前待识别的语法的文本
        String waitRecognizingGrammarTexT = GRAMMAR_TEXT;
        // 判断文法的组成是否满足四元祖（VT，VN，S，ε）

        // 0、判断输入的文法长度是否符合规范
        try {
            // 判断当前文法的长度是不是小于4,则就是不是为不和规范的文法 正确的文法至少是G->S
            if (waitRecognizingGrammarTexT.length() < 4) {
                // 如果不是直接抛出文法文本长度异常
                throw new GrammarTextLengthException("文法的输入长度有误，请重新尝试");
            }
            // 捕获异常信息
        } catch (GrammarTextLengthException e) {
            // 打印内存信息
            e.printStackTrace();
        }

        // 1、判断输入的文法是否符合终结符和非终结符的规则
        // 1、1 获取需要识别的文法文本的长度
        int grammarTextLength = waitRecognizingGrammarTexT.length();
        // 设置文法读取指针
        int index;
        // 遍历所有的文法内容字符串
        for (int i = 0; i < grammarTextLength; i++) {
            // 获取当前读取到的字符串元素ascii值
            index = waitRecognizingGrammarTexT.charAt(i);
            if (i > 1) {
                // 如果存在非法的推导符号
                if (index == '>' && waitRecognizingGrammarTexT.charAt(i - 1) != '-') {
                    try {
                        throw new GrammarTextException(waitRecognizingGrammarTexT, waitRecognizingGrammarTexT.charAt(i - 1), i - 1);
                    } catch (GrammarTextException e) {
                        e.printStackTrace();
                    }
                }
                // 判断推导符号左部，是否为非终结符
                if (!(isVN(waitRecognizingGrammarTexT.charAt(i - 1))) && index == '-' && waitRecognizingGrammarTexT.charAt(i + 1) == '>') {
                    try {
                        throw new GrammarTextException(waitRecognizingGrammarTexT, waitRecognizingGrammarTexT.charAt(i - 1), i - 1);
                    } catch (GrammarTextException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 判断该字符是否为空格
            if (isSpace(index)) {
                continue;
            }

            try {
                // 判断当前字符是否不为终结符
                if (!(isVT(index))) {
                    // 判断该字符是否不为非终结符
                    if (!isVN(index)) {
                        // 判断该字符是否为终结符,否则默认这是违规字符
                        throw new GrammarTextException(waitRecognizingGrammarTexT, waitRecognizingGrammarTexT.charAt(index), i);
                    }
                }
            } catch (GrammarTextException e) {
                e.printStackTrace();
            }
        }

        // 2、判断文法的开始符号
        try {
            // 判断第一个字符是否为非终结符
            if (isVN(waitRecognizingGrammarTexT.charAt(0))) {
                // 判断第二个字符是否为英文的小左括号
                if (waitRecognizingGrammarTexT.charAt(1) == 91) {
                    // 判断输入的第三个字符是否非终结符
                    if (isVN(waitRecognizingGrammarTexT.charAt(2))) {
                        // 判断第四个字符是否为英文的小右括号
                        if (waitRecognizingGrammarTexT.charAt(3) == 93) {
                            return true;
                        }
                        throw new GrammarTextException(waitRecognizingGrammarTexT, waitRecognizingGrammarTexT.charAt(3));
                    }
                    throw new GrammarTextException(waitRecognizingGrammarTexT, waitRecognizingGrammarTexT.charAt(2));
                }
                throw new GrammarTextException(waitRecognizingGrammarTexT, waitRecognizingGrammarTexT.charAt(1));
            } else {
                throw new GrammarTextException(waitRecognizingGrammarTexT, waitRecognizingGrammarTexT.charAt(0));
            }
        } catch (GrammarTextException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 切割文法
     *
     * @return 存储切割出来的文法对 例如G->E;G-E|E;
     */
    public HashMap<Character, HashSet<String>> splitGrammar() {
        try {
            // 判断待切割文法的长度是否合法
            if (GRAMMAR_TEXT.length() < 4) {
                throw new GrammarTextLengthException("待拆分文法存在长度问题，请重试" + GRAMMAR_TEXT.length());
            }
        } catch (GrammarTextLengthException e) {
            e.printStackTrace();
        }
        // 使用空格拆分待识别文法
        List<String> resultList = new ArrayList<>(Arrays.asList(GRAMMAR_TEXT.split(" ")));
        // 创建结果数组
        RESULT_MAP = new HashMap<>();
        // 创建中间交换的集合
        HashSet<String> hashSet = new HashSet<>();
        // 当前文法开始部分
        String grammarStart;
        // 获取当前的字符串
        String currentString;
        // 声明文法文本部分
        String grammarText;
        // 获取开始符号的位置坐标
        int startIndex;
        // 遍历所有需要拆分的文法对
        for (int j = 1; j < resultList.size(); j++) {
            // 获取当前文法的字符串形式
            currentString = resultList.get(j);
            // 获取开始符号的位置
            startIndex = currentString.indexOf('>') + 1;
            // 获取当前文法的开始部分
            grammarStart = currentString.substring(0, 1);
            // 初始化文法文本部分
            grammarText = currentString.substring(startIndex);
            // 上传当前文法的开始部分
            // 判断文法文本部分是否包含 "|" 符号
            if (grammarText.contains("|")) {
                // 存在则拆分便利所有的文法对
                String[] splitArrays = grammarText.split("\\|");
                // 将文法对追加到新的字符串中
                hashSet.addAll(Arrays.asList(splitArrays));
            } else {
                // 单独元素直接添加到元素列表
                hashSet.add(grammarText);
            }
            // 文法拆分添加后我们加入文法集合中
            addElement(RESULT_MAP, hashSet, grammarStart);
            // 添加后清空文法列表
            hashSet = new HashSet<>();
        }
        // 返回拆分后的文法集合
        return RESULT_MAP;
    }

    private void addElement(HashMap<Character, HashSet<String>> resultMap, HashSet<String> hashSet, String grammarStart) {

        if (resultMap.get(grammarStart.charAt(0)) != null || hashSet.isEmpty()) {

            hashSet.addAll(resultMap.get(grammarStart.charAt(0)));
        }
        resultMap.put(grammarStart.charAt(0), hashSet);
    }

    /**
     * .
     * 判断当前的文法是否为算符优先文法
     */
    public boolean isOperatorGrammar() {
        // 获取文法字符串的长度信息;
        int length = GRAMMAR_TEXT.length();
        // 声明当前元素的变量
        char currentElement;
        // 声明下一个元素的变量
        char nextElement;
        try {
            // 遍历文法文本的所有字符数据;
            for (int i = 0; i < length - 1; i++) {
                // 初始化当前元素;
                currentElement = GRAMMAR_TEXT.charAt(i);
                // 初始化下一个元素;
                nextElement = GRAMMAR_TEXT.charAt(i + 1);
                // 判断前后两个元素是否均为终结符，如果是的话
                if (Grammar.isVN(currentElement) && Grammar.isVN(nextElement)) {
                    throw new OperatorGrammarTextException("当前文法存在两个相临的非终结符{" + currentElement + "},{" + nextElement + "}不符合算符优先文法的定义");
                }
                // 判断当前文法是否存在空串
                if ('ε' == currentElement || 'ε' == nextElement) {
                    // 提示当前文法存在空串，不符合文法的定义
                    throw new OperatorGrammarTextException("当前文法存在{ε},不符合算符优先文法的定义");
                }
            }
        } catch (OperatorGrammarTextException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 判断当前输入的字符是否为终结符；
     *
     * @param waitRecognizingChar 待识别的终结符；
     * @return true or false
     */
    public static boolean isVT(int waitRecognizingChar) {
        // 终结符第一部分的开始ascii值；
        int V_T_ONE_START = 33;
        // 终结符第一部分的结束ascii值；
        int V_T_ONE_END = 64;
        // 终结符的第二部分的开始ascii值；
        int V_T_TWO_START = 91;
        // 终结符的第二部分的结束ascii值；
        int V_T_TWO_END = 126;
        // 判断当前字符是否为第一部分的终结符；
        if (waitRecognizingChar >= V_T_TWO_START && waitRecognizingChar <= V_T_TWO_END) {
            return true;
            // 判断当前字符是否为第二部分终结符；
        } else {
            return waitRecognizingChar >= V_T_ONE_START && waitRecognizingChar <= V_T_ONE_END;
        }
    }

    /**
     * 判断当前字符是否为非终结符；
     *
     * @param waitRecognizingChar 待识别的字符；
     * @return true or false；
     */
    public static boolean isVN(int waitRecognizingChar) {
        // 非终结符的起始ascii值；
        int V_N_START = 65;
        // 非终结符的结束ascii值；
        int V_N_END = 90;
        // 返回判断是否为非终结符的结果
        return waitRecognizingChar >= V_N_START && waitRecognizingChar <= V_N_END;
    }

    /**
     * 判断是否为空格；
     *
     * @param waitRecognizingChar 待识别的字符
     * @return true or false
     */
    public static boolean isSpace(int waitRecognizingChar) {
        // 返回判断结果
        return waitRecognizingChar == 32;
    }
}

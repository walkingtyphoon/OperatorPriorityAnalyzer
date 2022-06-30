package com.typhoon.grammar.operation;


/**
 * @author typhoon
 */
public interface GrammarOperation {

    /**
     * .
     * 判断两个元素之间的是否相等
     *
     * @param src 需要判断的第一个元素 通常为 i - 1
     * @param in  中间元素通常为非终结符
     * @param tag 需要判断的第三个元素 通常为 i + 1
     * @return 是否相等
     */
    boolean isEquals(char src, char in, char tag);

    /**
     * .
     * 判断两个元素之间的关系是否相等
     *
     * @param src 需要判断的第一个元素 通常为 i - 1
     * @param tag 需要判断的第三个元素 通常为 i + 1
     * @return 是否相等
     */
    boolean isEquals(char src, char tag);

    /**
     * .
     * 判断当前所给第一个元素是否为小于第二个元素
     *
     * @param src 第一个元素终结符
     * @param tag 第二个元素非终结符
     * @return 是否小于
     */
    boolean isLess(char src, char tag);

    /**
     * .
     * 判断当前所给第一个元素是否为大于第二个元素
     *
     * @param src 第一个元素
     * @param tag 第二个元素
     * @return 是否大于
     */
    boolean isGreater(char src, char tag);

}

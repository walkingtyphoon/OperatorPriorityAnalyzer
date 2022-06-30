package com.typhoon.exception;

/**
 * 文法文本内容异常
 *
 * @author walki
 * @Date 2022年5月18日14:41:02
 */
public class GrammarTextException extends GrammarException {

    /**.
     * 文法内容异常
     * @param text 需要输出文法内容部分
     * @param var 出错的文法元素
     */
    public GrammarTextException(String text, char var) {
        super("当前文法开始符号存在违规文发元素，请参见" + text + "中的{" + var + "}元素");
    }

    /**.
     * 文法内容异常，打印索引
     * @param text 需要输出的文法内容部分
     * @param var  出错的文法元素
     * @param index 元素索引位置
     */
    public GrammarTextException(String text, char var, int index) {
        super("当前文法开始符号存在违规文法元素，请参见" + text + "中的{" + var + "}元素,位于第{" + index + "}个元素");
    }
}

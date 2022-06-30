package com.typhoon.grammar.operation;

import com.typhoon.grammar.Grammar;
import com.typhoon.grammar.operator.access.OperationAccess;
import com.typhoon.grammar.operator.access.OperationAccessImpl;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 文法操作接口实现类
 *
 * @author typhoon
 */
public class GrammarOperationImpl implements GrammarOperation {


    private static final OperationAccess OPERATION_ACCESS = new OperationAccessImpl();

    /**
     * .
     * 判断两个文法元素元素之间优先关系是否相等
     * 对于形如 R->aQb,则a ≑ b
     * @param src 文法元素
     * @param in  中间元素通常为非终结符
     * @param tag 文法元素
     * @return 是否相等
     */
    @Override
    public boolean isEquals(char src, char in, char tag) {
        // 判断当前元素是否为终结符
        if (Grammar.isVT(src) && Grammar.isVT(tag)) {
            // 判断中间元素是否为非终结符
            return Grammar.isVN(in);
        }
        // 反之为False
        return false;
    }

    /**
     * .
     * 判断两个文法元素元素之间优先关系是否相等
     * 对于形如 R->ab,则a ≑ b
     * @param src 文法元素
     * @param tag 文法元素
     * @return 是否相等
     */
    @Override
    public boolean isEquals(char src, char tag) {
        // 判断当前元素是否为终结符
        return Grammar.isVT(src) && Grammar.isVT(tag);
    }

    /**
     * .
     * 判断当前所给第一个元素是否为小于第二个元素
     * 对形如 R-> aP的产生式，若有b ∈ FIRSTVT(P),则a⋖b，a⋖P
     *
     * @param src 第一个元素终结符
     * @param tag 第二个元素非终结符
     * @return 是否小于
     */
    @Override
    public boolean isLess(char src, char tag) {
        // 获取到文法的FIRST VT集合
        HashMap<Character, HashSet<String>> fistVt = OPERATION_ACCESS.getFistVt();
        // 判断当前非终结符的FIST VT集合是否存在
        if (fistVt.containsKey(tag)) {
            // 获取的非终结符的集合是否为空
            if (fistVt.get(tag).isEmpty()) {
                // 如果为空则直接返回false
                return false;
            }
            // 不存在集合则直接返回false
        } else {
            return false;
        }
        // 如果集合非空，则我们判断第一个符号是否为终结符，如果是的话则返回true，反之亦然
        return Grammar.isVT(src);
    }

    /**
     * .
     * 判断当前的非终结符和终结符之间的关系是否为高于
     * R->Pb 若有a ∈ LASTVT(P) ,则a⋗b，则P⋗b
     *
     * @param src 非终结符
     * @param tag 终结符
     * @return 是否大于
     */
    @Override
    public boolean isGreater(char src, char tag) {
        // 获取所有终结符的LAST VT集合;
        HashMap<Character, HashSet<String>> lastVT = OPERATION_ACCESS.getLastVt();
        // 判断当前非终结符是否存在LAST VT 集合;
        if (lastVT.containsKey(src)) {
            // 如果存在则判断当前非终结符的LAST VT 集合是否为空;
            if (lastVT.get(src).isEmpty()) {
                // 如果为空，则表示优先级不高于
                return false;
            }
            // 否则的直接返回false
        } else {
            return false;
        }
        // 当传递的非终结符的集合不为空的时候，我们就需要判断第二个元素是否为非终结符号，如果是的话，就返回true，其他的默认返回false;
        return Grammar.isVT(tag);
    }
}

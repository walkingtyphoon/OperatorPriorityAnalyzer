package com.typhoon.grammar.operator.operation.PriorityRelationship;

import com.typhoon.grammar.operator.operation.OperatorOperation;
import com.typhoon.grammar.operator.operation.OperatorOperationImpl;

/**
 * 算符优先关系的实现类
 * @author typhoon
 */
public class OperatorOperationPriorityRelationshipImpl implements OperatorOperationPriorityRelationship{

    private final OperatorOperation operatorOperation = new OperatorOperationImpl();

    /**
     * 判断优先级是否大于
     *
     * @param src 第一个元素
     * @param next 第二个元素
     * @param priorityRelationshipTable 优先关系表
     * @return 是否符合预期关系
     */
    @Override
    public boolean isPriorityGreater(String src, String next, String[][] priorityRelationshipTable) {
        return operatorOperation.getPriorityRelationship("⋗",src,next,priorityRelationshipTable);
    }

    /**
     * 判断优先级是否小于
     *
     * @param src 第一个元素
     * @param next 第二个元素
     * @param priorityRelationshipTable 优先关系表
     * @return 是否符合预期关系
     */
    @Override
    public boolean isPriorityLess(String src, String next, String[][] priorityRelationshipTable) {
        return operatorOperation.getPriorityRelationship("⋖", src, next, priorityRelationshipTable);
    }

    /**
     * 判断优先级是否相等
     *
     * @param src 第一个元素
     * @param next 第二个元素
     * @param priorityRelationshipTable 优先关系表
     * @return 是否符合预期关系
     */
    @Override
    public boolean isPriorityEquals(String src, String next, String[][] priorityRelationshipTable) {
        return operatorOperation.getPriorityRelationship("≑", src, next, priorityRelationshipTable);
    }
}

package com.typhoon.grammar.operator.operation.PriorityRelationship;

/**
 * @author typhoon
 */
public interface OperatorOperationPriorityRelationship{


    /**
     * 判断优先级是否大于
     *
     * @param src
     * @param next
     * @param priorityRelationshipTable
     * @return
     */
    boolean isPriorityGreater(String src, String next, String[][] priorityRelationshipTable);

    /**
     * 判断优先级是否小于
     *
     * @param src
     * @param next
     * @param priorityRelationshipTable
     * @return
     */
    boolean isPriorityLess(String src, String next, String[][] priorityRelationshipTable);

    /**
     * 判断优先级是否相等
     *
     * @param src
     * @param next
     * @param priorityRelationshipTable
     * @return
     */
    boolean isPriorityEquals(String src, String next, String[][] priorityRelationshipTable);

}

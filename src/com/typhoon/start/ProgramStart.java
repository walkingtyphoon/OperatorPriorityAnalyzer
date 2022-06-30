package com.typhoon.start;

import com.typhoon.grammar.Grammar;
import com.typhoon.grammar.operator.access.OperationAccess;
import com.typhoon.grammar.operator.access.OperationAccessImpl;
import com.typhoon.grammar.operator.analyse.OperationAnalyse;
import com.typhoon.grammar.operator.analyse.OperationAnalyseImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author typhoon
 */
public class ProgramStart {

    /**
     * .
     * 启动算符优先分析器;
     */
    public static void start() {
        // 创建键盘输入对象;
        Scanner scanner = new Scanner(System.in);
        // 提示用户输入文法串，文法开始类似G[S] S->e A->x
        System.out.println("请输入您所使用的算符优先文法,请参考:G[S] S->e A->x 换行后结束");
        // 创建算符优先文法字符串;
        String grammarText = scanner.nextLine();
        // 创建文法对象，并初始化文法;
        Grammar grammar = new Grammar(grammarText);
        // 开始初始化文法内容;
        // 判断当前内容是否为文法;
        if (!grammar.initGrammar()) {
            // 不是则直接返回，结束方法;
            return;
        }
        // 判断当前产生式是否为算符优先文法;
        if (!grammar.isOperatorGrammar()) {
            // 不是则直接结束方法;
            return;
        }
        // 初始化并拆分文法;
        grammar.splitGrammar();
        System.out.println("文法初始化完成");

        showCaiDan();

    }

    /**
     * .
     * 获取菜单的方法
     */
    protected static void showCaiDan() {
        // 功能选项;
        int option;
        // 循环录入选项
        while (true) {
            try {
                // 否则的话展示功能页面;
                System.out.println("欢迎使用算符优先分析器,请选择您需要使用的功能,输入数字回车即可");
                System.out.println("    1、获取FIRST VT 访问集合");
                System.out.println("    2、获取LAST VT 访问集合");
                System.out.println("    3、获取算符优先关系表");
                System.out.println("    4、分析句型");
                System.out.println("    5、退出");
                // 初始化选项数字;
                option = new Scanner(System.in).nextInt(6);
                // 那就结束当前的循环;
                break;
            } catch (InputMismatchException e) {
                System.out.println("输入选项有误，请重新尝试输入");
            }
        }
        // 创建算符访问对象;
        OperationAccess operationAccess = new OperationAccessImpl();
        // 设置菜单对应选项;
        switch (option) {
            // 设置第一个选项;
            case 1 -> {
                // 输出获取到FIRST VT集合;
                System.out.println(operationAccess.getFistVt());
                // 输出后重新调用菜单;
                showCaiDan();
            }
            case 2 -> {
                // 输出LAST VT集合;
                System.out.println(operationAccess.getLastVt());
                // 再次调用菜单;
                showCaiDan();
            }
            case 3 -> {
                // 获取优先分析关系;
                operationAccess.getGrammarPriorityRelationships();
                // 获取优先关系表;
                String[][] strings = operationAccess.ShowPriorityRelationshipTable();
                // 遍历文法优先关系矩阵;
                for (String[] string : strings) {
                    // 输出每一行;
                    System.out.println(Arrays.toString(string));
                }
                // 重新调用菜单;
                showCaiDan();
            }
            case 4 -> {
                // 获取优先分析关系;
                operationAccess.getGrammarPriorityRelationships();
                // 获取优先关系表;
                operationAccess.ShowPriorityRelationshipTable();
                // 创建算符分析对象;
                OperationAnalyse operationAnalyse = new OperationAnalyseImpl();
                // 创建键盘录入对象;
                Scanner scanner = new Scanner(System.in);
                // 输出提示信息
                System.out.println("请输入您需要分析的输入串，并以井号结束");
                // 创建并输入对象;
                String inputString = scanner.next();
                // 分析录入的语句;
                operationAnalyse.analyseSentential(inputString);
                // 重新调用菜单;
                showCaiDan();
            }
            default -> {
                System.out.println("感谢您的使用，欢迎下次使用");
                break;
            }
        }
    }
}

import com.typhoon.grammar.Grammar;
import com.typhoon.grammar.operator.OperationMethod;
import com.typhoon.grammar.operator.access.OperationAccess;
import com.typhoon.grammar.operator.access.OperationAccessImpl;
import com.typhoon.grammar.operator.analyse.OperationAnalyse;
import com.typhoon.grammar.operator.analyse.OperationAnalyseImpl;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class TestDemo1 extends OperationMethod {

    @Test
    public void test01() {

        // 创建需要识别的文法

//        String grammarText = "G[E]: E->E+T|T T->T*F|F F->(E)|i";

        /*
         *
         *   E,+,*,(,i
         *
         *
         *
         * */
//        String grammarText = "G[S]: S->aAbB A->c|Ac B->d";
//          String grammarText = "G[S]: S->iBtS|iBtAeS|a A->iBtAeS|a B->b";
        String grammarText = "G[E]: E->E+E|E*E|(E)|i";
        // 创建一个新的待识别的文法对象
        Grammar grammar = new Grammar(grammarText);
        // 分割复杂文法
        HashMap<Character, HashSet<String>> hashMap = grammar.splitGrammar();

//        // 遍历分割后的文法
//        for (Character character : hashMap.keySet()) {
//            System.out.println("key" + character + ":" + hashMap.get(character));
//        }

        OperationAccess operationAccess = new OperationAccessImpl();

        HashMap<Character, HashSet<String>> fistVt = operationAccess.getFistVt();

        Set<Character> characters = fistVt.keySet();
//
//        for (Character character : characters) {
//            System.out.println("FIRSTVT(" + character + ")={" + fistVt.get(character) + "}");
//        }

        HashMap<Character, HashSet<String>> lastVt = operationAccess.getLastVt();

        Set<Character> characters1 = lastVt.keySet();

        for (Character character : characters1) {

            System.out.println("LASTVT(" + character + ")={" + lastVt.get(character) + "}");

        }

//        HashMap<String, String> grammarPriorityRelationships = operationAccess.getGrammarPriorityRelationships();

//        for (String s : grammarPriorityRelationships.keySet()) {
//            System.out.println(s + " : " + grammarPriorityRelationships.get(s));
//        }


//        String[][] strings = operationAccess.ShowPriorityRelationshipTable();
//
//        for (String[] string : strings) {
//            System.out.println(Arrays.toString(string));
//        }

//        OperationAnalyse operationAnalyse = new OperationAnalyseImpl();
//
//        operationAnalyse.analyseSentential("i+i*i#");

//        boolean e = isPriorityGreater("#", "E", "#", strings);


    }
}

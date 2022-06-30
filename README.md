1. # 开发环境(development environment)

   1. ## 操作系统(operating system)：Fedora 36

   2. ## Java: JDK 11

2. # 简介(Introduction)

   ## 编译原理自底向上算符优先分析器，编译原理自底向上分析，其核心主要包括求解FIRST VT集合和LAST VT集合，以及获取算符号优先关系，打印算符优先关系表，分析输入串。

   ## Compilation principle bottom-up operator priority analyzer, compiling principle bottom-up analysis, its core mainly includes solving FIRST VT set and LAST VT set, as well as obtaining operator priority relationship, printing operator priority relationship table, and analyzing input string.

   ## 该程序设计主要是基于控制台

   ## The program design is mainly based on the console

3. # 核心功能(Core functions)

   1. ## FIRST VT

   2. ## LAST VT

   3. ## 打印算符优先关系表(print operator precedence table)

   4. ## 分析输入串(Parse the input string)

4. ## 程序分布结构(Program distribution structure)

   1. ## 目录结构(Directory Structure)

      | 文件夹名称(Folder name) | 解释(explain)                                                |
      | ----------------------- | ------------------------------------------------------------ |
      | .idea                   | idea配置信息(idea configuration information)                 |
      | lib                     | 依赖库 [主要为单元测试JAR文件，只做测试使用，可选] (Dependent library [mainly JAR files for unit testing, only for testing, optional]) |
      | logFiles                | 存储调试日志的文件夹(Folder where debug logs are stored)     |
      | src                     | 源代码文件(source code file)                                 |
      | Test                    | 测试类文件(test class file)                                  |

   2. ## 代码结构(code structure)

      ```markdown
      .
      └── com
        └── typhoon
           ├── Boot.java                   主程序的启动类(Startup class of the main program)
           ├── exception                   异常包(exception package)
           │   ├── GrammarException.java                   文法异常的父类(Grammar exception parent class)
           │   ├── GrammarTextException.java                   文法文本异常(grammatical text exception)
           │   ├── GrammarTextLengthException.java                   文法文本长度异常(Grammar text length is abnormal)
           │   ├── OperatorGrammarException.java                   算符文法异常父类(Operator syntax exception parent class)
           │   └── OperatorGrammarTextException.java                   算赋文法文本异常(Arithmetic grammar text exception)
           ├── grammar                   文法包(grammar pack)
           │   ├── Grammar.java                   文法相关的类(Grammar-related classes)
           │   ├── operation                   文法操作的包(package for grammar operations)
           │   │   ├── GrammarOperationImpl.java                   文法操作的实现类(Implementation class for grammar operations)
           │   │   └── GrammarOperation.java                   文法操作接口(Grammar Operation Interface)
           │   └── operator                   算符包(operator package)
           │       ├── access                   访问包(access package)
           │       │   ├── OperationAccessImpl.java                   算符访问实现类(Operator access implementation class)
           │       │   └── OperationAccess.java                   算符访问接口(operator access interface)
           │       ├── analyse                   分析包(Analysis package)
           │       │   ├── OperationAnalyseImpl.java                   算符分析实现类(Operator Analysis Implementation Class)
           │       │   └── OperationAnalyse.java                   算符分析接口(Operator Analysis Interface)
           │       ├── operation                   操作(operate)
           │       │   ├── OperatorOperationImpl.java                   算符操作实现类(Operator operation implementation class)
           │       │   ├── OperatorOperation.java                   算符操作实现接口(Operator operation implements interface)
           │       │   └── PriorityRelationship                   优先关系(priority relationship)
           │       │       ├── OperatorOperationPriorityRelationshipImpl.java                   算符优先关系实现类(Operator precedence relationship implementation class)
           │       │       └── OperatorOperationPriorityRelationship.java                   算符优先关系接口(Operator precedence relation interface)
           │       └── OperationMethod.java                   算符方法的父类(superclass of operator methods)
           └── start                    启动包(starter pack)
                 └── ProgramStart.java                    主启动程序的具体实现(The specific implementation of the main startup program)
      ```

  

# &nbsp; &nbsp; &nbsp; &nbsp; 关于本算符优先分析器我们实现了对于获取访问集合，求解优先关系矩阵，分析输入串的功能，不过对于异常处理并不完善，代码尚存在很大的优化空间，对于代码的结构以及重构亦是如此，欢迎各位大佬参加到我们开发，完善，更新过程中，帮助更多有需要的人，以及热爱学习的朋友，谢谢！

# &nbsp; &nbsp; &nbsp; &nbsp; Regarding this operator priority analyzer, we have realized the functions of obtaining access sets, solving priority relationship matrices, and analyzing input strings. However, exception handling is not perfect, and the code still has a lot of room for optimization. For code structure and refactoring The same is true, welcome everyone to participate in our development, improvement, and update process to help more people in need and friends who love learning, thank you!

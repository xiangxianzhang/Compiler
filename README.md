基于java的C语言编译器
================
采用LL(1)文法进行分析，文法是C语言文法的子集，支持报错，以及错误原因
--------------------------------------------------------------------
  以前只用编译器编译程序，现在学完编译原理这门课以后，通过编译大作业，我对编译器的工作原理有了比较清晰的认识<br>
编译器的工作原理 <br>
      编译器 (Compiler) 是一种将由一种语言编写的程序转换为另一种编程语言的可执行程序. 现代软件对于编译器的需求远甚从前, 究其原因很简单: 作为中间层, 编译器是构建更高层抽象的基础设施. 编译器意欲将人类可阅读的高阶代码, 翻译为机器能运行的低阶代码. <br>
     现代编译器的主要工作流程为: 源代码（source code）→ 预处理器（preprocessor）→ 编译器（compiler）→ 汇编程序（assembler）→ 目标代码（object code）→ 链接器（Linker）→ 可执行文件（executables）1.

其中, 编译器位于一个最重要的位置: 将源码转为汇编(上文提到的css, js也可认为是一种汇编).<br>

<big>词法分析<big/>：<br>
    词法分析器根据词法规则识别出源程序中的各个记号（token），每个记号代表一类单词（lexeme）。源程序中常见的记号可以归为几大类：关键字、标识符、字面量和特殊符号。词法分析器的输入是源程序，输出是识别的记号流。词法分析器的任务是把源文件的字符流转换成记号流。本质上它查看连续的字符然后把它们识别为“单词”。<br>

<big>语法分析<big/>：<br>
          语法分析器根据语法规则识别出记号流中的结构（短语、句子），并构造一棵能够正确反映该结构的语法树。<br>

<big>语义分析<big/>:<br>
    语义分析器根据语义规则对语法树中的语法单元进行静态语义检查，如果类型检查和转换等，其目的在于保证语法正确的结构在语义上也是合法的。<br>

<big>中间代码生成<big/>:<br>
    中间代码生成器根据语义分析器的输出生成中间代码。中间代码可以有若干种形式，它们的共同特征是与具体机器无关。最常用的一种中间代码是三地址码，它的一种实现方式是四元式。三地址码的优点是便于阅读、便于优化。<br>

<big>中间代码优化<big/>:<br>
    优化是编译器的一个重要组成部分，由于编译器将源程序翻译成中间代码的工作是机械的、按固定模式进行的，因此，生成的中间代码往往在时间和空间上有很大浪费。当需要生成高效目标代码时，就必须进行优化。<br>

<big>目标代码生成<big/>:<br>
    目标代码生成是编译器的最后一个阶段。在生成目标代码时要考虑以下几个问题：计算机的系统结构、指令系统、寄存器的分配以及内存的组织等。编译器生成的目标程序代码可以有多种形式：汇编语言、可重定位二进制代码、内存形式。<br>

<big>符号表管理<big/>:<br>
    符号表的作用是记录源程序中符号的必要信息，并加以合理组织，从而在编译器的各个阶段能对它们进行快速、准确的查找和操作。符号表中的某些内容甚至要保留到程序的运行阶段。<br>

<big>出错处理<big/>:<br>
     用户编写的源程序中往往会有一些错误，可分为静态错误和动态错误两类。所谓动态错误，是指源程序中的逻辑错误，它们发生在程序运行的时候，也被称作动态语义错误，如变量取值为零时作为除数，数组元素引用时下标出界等。静态错误又可分为语法错误和静态语义错误。语法错误是指有关语言结构上的错误，如单词拼写错、表达式中缺少操作数、begin和end不匹配等。静态语义错误是指分析源程序时可以发现的语言意义上的错误，如加法的两个操作数中一个是整型变量名，而另一个是数组名等。<br>

<big>编译大作业<big/>: <br>
LL1文法: <br>
1、 文法开始： <br>
S->void main(){A} <br>
2、 声明： <br>
X->YZ; <br>
Y->int|char|bool <br>
Z->UZ’ <br>
Z’->,Z|U−>idU′U′−>=L| <br>
3、 赋值：<br> 
R->id=L; <br>
4、 算术运算： <br>
L->TL’ <br>
L’->+L|-L|T−>FT′T′−>∗T|/T| <br>
F->(L) <br>
F->id|num <br>
O->++|–|Q−>idO| <br>
5、 布尔运算 <br>
E->HE’ <br>
E’->&&E|H−>GH′H′−>||HH′−> <br>
G->FDF <br>
D-><|>|==|!= <br>
G->(E) <br>
G->!E <br>
5、控制语句 <br>
B->if (E){A}else{A} <br>
B->while(E){A} <br>
B->for(YZ;G;Q){A} <br>
6、功能函数 <br>
B->printf(P); <br>
B->scanf(id); <br>
P->id|ch|num<br>

7、复合语句 <br>
A->CA <br>
C->X|B|R <br>
A->$<br>

1、构造LL1属性翻译文法 <br>
构造LL1属性翻译文法即在原有LL1文法基础上加上动作符号，并给非终结符和终结符加上一定属性，给动作符号加上语义子程序。对原有LL1文法改进的地方如下： <br>
1、 赋值： <br>
产生式 语义子程序<br>

R->@ASS_R id =L@ EQ; @ASS{R.VAL=id并压入语义栈} <br>
@EQ{RES=R.VAL,OP=’=’,ARG1=L.VAL, <br>
new fourElement(OP,ARG1,/, RES)} <br>
U->@ASS_UidU’ {U.VAL=id并压入语义栈} <br>
U’->=L|$@EQ_U’ {RES=U.VAL,OP=’=’,ARG1=L.VAL,new fourElement(OP,ARG1,/, RES)}<br>

2、 算术运算： <br>
产生式 语义子程序<br>

L->TL’@ADD_SUB {If(OP!=null) RES= NEWTEMP; L.VAL=RES,并压入语义栈;New fourElement(OP, T.VAL;,L’VAL, RES)， <br>
} <br>
L’->+L@ADD {OP=+,ARG2=L.VAL} <br>
L’->-L@SUB {OP=-,ARG2=L.VAL} <br>
L’->$ <br>
T->FT’@DIV_MUL { if (OP !=null) RES= NEWTEMP;T.VAL=RES; <br>
new FourElement(OP,F.VAL,ARG2, RES) <br>
else ARG1=F.VAL; } <br>
T’->/T@DIV {OP=/,ARG2=T.VAL} <br>
T’->T@MUL {OP=,ARG2=T.VAL} <br>
T’->F−>(L)@VOLF.VAL−>L.VALF−>@ASSFnum|idF.VAL=num|idQ−>idO| <br>
O->@SINGLE_OP++|– {OP=++|–}<br>

3、 布尔运算 <br>
产生式 语义子程序<br>

G->FDF@COMPARE{OP=D.VAL;ARG1=F(1).VAL;ARG2=F(2).VAL,RES=NEWTEMP; <br>
New fourElement(OP,F.VAL,ARG2, RES );G.VAL=RES并压入语义栈} <br>
D->@COMPARE_OP<|>|==|!={D.VAL=<|>|==|!=,并压入语栈}<br>

4、 控制语句 <br>
产生式 语义子程序 <br>
B->if (G)@IF_FJ{A}@IF_BACKPATCH_FJ @IF_RJ else{A}@IF_BACKPATCH_RJ <br>
@IF_FJ{OP=”FJ”;ARG1=G.VAL;RES=if_fj, New fourElement(OP,ARG1,/, RES ),将其插入到四元式列表中第i个} <br>
@IF_BACKPATCH_FJ{回填前面假出口跳转四元式的跳转序号, BACKPATCH (i,if_fj)} <br>
B->while(G)@WHILE_FJ{A}@WHILE_RJ@WHILE_BACKPATCH_FJ {参照if else} <br>
B->for(YZ;G@FOR_FJ;Q){A@SINGLE}@FOR_RJ@FOR_BACKPATCH_FJ {参照if else } <br>
@SINGLE {ARG1=id;RES=NEWTEMP;New fourElement(OP,ARG1,/,RES)}<br>

说明： <br>
（1）、R.VAL表示符号R的值，VAL是R的一个属性，其它类似。 <br>
（2）、NEWTEMP()函数：每调用一次生成一个临时变量，依次为T1，T2，…Tn。 <br><br>
（3）、BACKPATCH (int i,int res):回填函数，用res回填第i个四元式的跳转地址。 
（4）、new fourElement（String OP,String ARG1,String ARG2,String RES ）:生成一个四元式 <br>
（OP,ARG1,ARG2,RES）

工程列表 <br>
![](http://img.blog.csdn.net/20150628153430389) 

主界面 <br>
![](http://img.blog.csdn.net/20150628153513997) <br>
主界面代码<br>
~~~~~~~~~~~~~java
package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.*;
import compiler.*;

public class MainFrame extends JFrame {

    TextArea sourseFile;//用来显示源文件的文本框
    String soursePath;// 源文件路径
    String LL1Path;
    String wordListPath;
    String fourElementPath;
    LexAnalyse lexAnalyse;

    Parser parser;
    public MainFrame() {
        this.init();
    }

    public void init() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screen = toolkit.getScreenSize();
        setTitle("向贤章C语言小型编译器");
        setSize(750, 480);
        super.setResizable(false);
        super.setLocation(screen.width / 2 - this.getWidth() / 2, screen.height
                / 2 - this.getHeight() / 2);
        this.setContentPane(this.createContentPane());
    }

    private JPanel createContentPane() {
        JPanel p = new JPanel(new BorderLayout());
        p.add(BorderLayout.NORTH, createUpPane());
        p.add(BorderLayout.CENTER, createcCenterPane());
        p.add(BorderLayout.SOUTH, creatBottomPane());
        // p.setBorder(new EmptyBorder(8,8,8,8));
        return p;
    }

    private Component createUpPane() {
        JPanel p = new JPanel(new FlowLayout());
        final FilePanel fp = new FilePanel("选择待分析文件");
        //final FilePanel fp1 = new FilePanel("xiang");
        JButton button = new JButton("确定");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String text;
                try {
                    soursePath = fp.getFileName();
                    text = readFile(soursePath);
                    sourseFile.setText(text);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }            

            }
        });
        p.add(fp);
        //p.add(fp1);
        p.add(button);
        return p;
    }

    private Component createcCenterPane() {
        JPanel p = new JPanel(new BorderLayout());
        JLabel label = new JLabel("源文件如下：");
        sourseFile = new TextArea();
        sourseFile.setText("");
        p.add(BorderLayout.NORTH, label);
        p.add(BorderLayout.CENTER, sourseFile);
        return p;
    }

    private Component creatBottomPane() {
        JPanel p = new JPanel(new FlowLayout());
        JButton bt1 = new JButton("词法分析");
        JButton bt2 = new JButton("语法分析");
        JButton bt4 = new JButton("中间代码生成");
        JButton bt5 = new JButton("目标代码生成");
        bt1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    lexAnalyse=new LexAnalyse(sourseFile.getText());
                    wordListPath = lexAnalyse.outputWordList();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                InfoFrame inf = new InfoFrame("词法分析", wordListPath);

                inf.setVisible(true);
            }
        });
        bt2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            lexAnalyse=new LexAnalyse(sourseFile.getText());
            parser=new Parser(lexAnalyse);
                try {
                    parser.grammerAnalyse();
                    LL1Path= parser.outputLL1();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                InfoFrame inf = new InfoFrame("语法分析", LL1Path);
                inf.setVisible(true);
            }
        });

        bt4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    lexAnalyse=new LexAnalyse(sourseFile.getText());
                    parser=new Parser(lexAnalyse);
                    parser.grammerAnalyse();
                    fourElementPath=parser.outputFourElem();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                InfoFrame inf = new InfoFrame("中间代码生成", fourElementPath);
                inf.setVisible(true);
            }
        });
        bt5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    lexAnalyse=new LexAnalyse(sourseFile.getText());
                    parser=new Parser(lexAnalyse);
                    parser.grammerAnalyse();
                    fourElementPath=parser.outputFourElem();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                huibian inf = new huibian("目标代码生成", fourElementPath);
                inf.setVisible(true);
            }
        });

        p.add(bt1);
        p.add(bt2);
        //p.add(bt3);
        p.add(bt4);
        p.add(bt5);
        return p;
    }

    public static String readFile(String fileName) throws IOException {
        StringBuilder sbr = new StringBuilder();
        String str;
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        InputStreamReader isr = new InputStreamReader(bis, "UTF-8");
        BufferedReader in = new BufferedReader(isr);
        while ((str = in.readLine()) != null) {
            sbr.append(str).append('\n');
        }
        in.close();
        return sbr.toString();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        MainFrame mf = new MainFrame();
        //TinyCompiler tinyCompiler = new TinyCompiler();
        //mf.tinyCompiler = tinyCompiler;
        mf.setVisible(true);
    }
}

class FilePanel extends JPanel {
    FilePanel(String str) {
        JLabel label = new JLabel(str);
        JTextField fileText = new JTextField(35);
        JButton chooseButton = new JButton("浏览...");
        this.add(label);
        this.add(fileText);
        this.add(chooseButton);
        clickAction ca = new clickAction(this);
        chooseButton.addActionListener(ca);
    }

    public String getFileName() {
        JTextField jtf = (JTextField) this.getComponent(1);
        return jtf.getText();    
    }

    // 按钮响应函数
    private class clickAction implements ActionListener {
        private Component cmpt;

        clickAction(Component c) {
            cmpt = c;
        }

        public void actionPerformed(ActionEvent event) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            int ret = chooser.showOpenDialog(cmpt);
            if (ret == JFileChooser.APPROVE_OPTION) {
                JPanel jp = (JPanel) cmpt;
                JTextField jtf = (JTextField) jp.getComponent(1);//获取zujian
                jtf.setText(chooser.getSelectedFile().getPath());
            }
        }
    }
}
~~~~~~~~~~~~~~~~~~~~~~~~
词法分析:<br>
![](http://img.blog.csdn.net/20150628153601335)<br>

语法分析：<br>
![](http://img.blog.csdn.net/20150628153632496)<br>

中间代码生成:<br>
![](http://img.blog.csdn.net/20150628153649472)<br>

目标代码生成:<br>
![](http://img.blog.csdn.net/20150628153707573)<br>

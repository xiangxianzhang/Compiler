package backup;

import java.util.ArrayList;


/**
 * 语法树节点
 */
public class Node {
public final static String NONTERMINAL="非终结符";
public final static String TERMINAL="终结符";
Word word=null;//树的值
String type;//节点类型
String nonterminal=null; 
ArrayList<Node> child=new ArrayList<Node>();//孩子集合
public void addChild(Node node){
	child.add(node);
}
public Node(){
	
}
public Node(Word word,String type){
	this.type=type;
	this.word=word;
}
public Node(String nonterminal,String type){
	this.type=type;
	this.nonterminal=nonterminal;
}
public boolean isTerminal(){
	return this.type.equals(Node.NONTERMINAL);
}
public boolean isArOP(){//判断节点值是否为算术运算符
if((this.word!=null)&&(word.value.equals("+")||word.value.equals("-")||word.value.equals("*")
		||word.value.equals("/")))return true;
else return false;
}
public boolean isBoolOP(){//判断节点值是否为布尔运算符
	if((this.word!=null)&&(word.value.equals(">")||word.value.equals("<")||word.value.equals("==")
			||word.value.equals("!=")||word.value.equals("!")||
			word.value.equals("&&")||word.value.equals("||")))return true;
	else return false;
	}
}

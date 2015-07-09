package test;

/**
 * 栈，属性: 1.序号; 2.内容; 3.下一个结点
 */
class Stack{
	int num;
	char name;
	Stack next;
	public Stack(){
		this.next=null;
	}
}
/**
 * 规则集
    属性: 1.序号; 2.规则长度; 3.符号 4.连接下一结点的对象引用
 * @author Administrator
 *
 */
class Guiyue{
	int num;
	int count;
	char name;
	Guiyue next;	
	public Guiyue(){
		this.next=null;
	}
}
/**
 *  符号表
属性: 1.自变量名; 2.标识类型; 3.连接下一结点的对象引用
 * @author Administrator
 *
 */
class Sign{
char [] name=new char[20];	
char kind;
Sign next;
public Sign(){
	this.next=null;
}
}
/**
 * 单词表 class word
  属性: 1.单词名字; 2.标识类型; 3.状态 4.序号; 5.行号; 6.连接符号表的引用; 7.连接下一结点的对象引用
 * @author Administrator
 *
 */
class Word{
	char []name=new char[20];
	char mark_name;
	int state;
	int num;
	int line;
	Word link;
	Word next;
	public Word(){
		this.link=null;
		this.next=null;
	}
}

public class Bean {

}

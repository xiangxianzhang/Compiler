package backup;
/**
 * 四元式节点
 * @author Administrator
 *
 */
public class FourElement {
int id;//四元式序号，为编程方便
String op;
String arg1;//
String arg2;
Object result;
public FourElement(){
	
}
public FourElement(int id,String op,String arg1,String arg2,String result){
	this.id=id;
	this.op=op;
	this.arg1=arg1;
	this.arg2=arg2;
	this.result=result;	
}
}

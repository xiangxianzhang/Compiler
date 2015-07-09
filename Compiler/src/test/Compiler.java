package test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class Compiler {

	static int row=1;//字符行变量
	static int []line=new int[10000];//字符行
	static int []wordLine=new int[300];//单词所在行
	static int wordCount;//单词数
	static char []buffer=new char [10000];//字符缓冲区
	/**
	 * 入栈
	 * @param ip
	 * @param name
	 * @param num
	 * @return
	 */
	Stack markPush(Stack ip,char name,int num){
		Stack s=new Stack();
		s.name=name;
		s.num=num;
		s.next=ip;
		ip=s;
		return ip;
	}
	/**
	 * 出栈
	 * @param ip
	 */
	void MarkPop(Stack ip){
		Stack s;
		char name;
		name=ip.name;
		s=ip.next;
		if(ip.next!=null){
			ip.name=ip.next.name;
			ip.num=ip.next.num;
			ip.next=ip.next.next;
		}
		
	}
/**
 * 判断字符类别函数: int judge(char ch)
   数据传递: 形参ch接收待判断字符；变量flag返回字符类别。
 */
	int judge(char ch){
		int flag;
		if(ch=='!'||ch=='$'||ch=='&'||ch=='*'||ch=='('||ch==')'||ch=='-'||ch=='_'||
				   ch=='+'||ch=='='||ch=='|'||ch=='{'||ch=='}'||ch=='['||ch==']'||ch==';'||
				   ch==':'||ch=='"'||ch=='<'||ch==','||ch=='>'||ch=='.'||ch=='/'||ch=='\'')     
																	{flag=1;}///特殊字符
				else if('0'<=ch&&ch<='9')                          {flag=2;}//数字
				else if(('a'<=ch&&ch<='z')||('A'<=ch&&ch<='Z'))    {flag=3;}//字母
				else if(ch==' ')                                   {flag=4;}//空格
				else if(ch=='\n')                                  {flag=5;}//换行
				else if(ch=='?')                                   {flag=6;} 
				else                                               {flag=0;}      //非法字符
		return flag;
	}
	/**
	 * 词法分析函数: void scan(String fileName)
	数据传递: 形参fileName接收文件路径；
    buffer与line对应保存源文件字符及其行号，char_num保存字符总数。
	 * @throws IOException 
	 */
	void scan(String fileName) throws IOException{
		FileInputStream fis=new FileInputStream(fileName);
		BufferedInputStream bis=new BufferedInputStream(fis);
		InputStreamReader isr=new InputStreamReader(bis,"utf-8");
		BufferedReader inbr=new BufferedReader(isr);	
		char ch;
		int flag,j=0,i=-1;
		while((ch=(char) inbr.read())!=-1){
			flag=judge(ch);
			System.out.print(ch);
			if(flag==1||flag==2||flag==3){
				i++;
				buffer[i]=ch;
				line[i]=row;
			}else if (flag==4){
				i++;
				buffer[i]='?';
				line[i]=row;
			}else if(flag==5){
				i++;
				buffer[i]='~';
				row++;
			}else{
				System.out.println("\n请注意，第"+row+"行的"+ch+"是非法字符");
			}
		}
		wordCount=i;
		/* *****************确定单词所在的行******************/
		int one,two,k=0;
		for(i=0;i<wordCount;i++){
			one=judge(buffer[i]);
			two=judge(buffer[i+1]);
			if((one!=two&&buffer[i]!='?'&&buffer[i]!='~')||one==1){
				wordLine[k]=line[i];
				k++;
			}
		}
	}
	
	/*
//======================================================================================================
//  初始化单词表函数: struct Word *InitWord()
//          数据传递: head返回单词表的头指针
//              备注: 初始化单词表函数包括分割单词、标识单词、生成变量符号表、完善单词属性表四个功能
struct Word *InitWord(){
	struct Word *head,*ft,*news,*p;
	struct Sign *s_first,*s_look;
	s_first=s_look=(struct Sign *)malloc(sizeof(struct Sign));
	s_first->kind='\0';
	s_first->name[0]='\0';
	news=head=ft=(struct Word *)malloc(sizeof(struct Word));
	ft->link=s_first;
	ft->next=NULL;
//====================================分割单词功能==========================================================
	int i=0,k,flag,jud=0;
	for(k=0;k<w_num;k++)
	{
		flag=judge(buffer[k]);
		if(jud==0){//1~
           if(flag==2||flag==3) {
			        news->name[i]=buffer[k];
			        news->name[++i]='\0';
		   }
		   else {//2~
                  i=0;
                  ft=news;
		          if(news->name[0]>=33&&news->name[0]<=125){
				               news=(struct Word *)malloc(sizeof(struct Word));
                               ft->next=news;
                               news->next=NULL;
				  }
		          if(flag==1){//3~
					     if(buffer[k]=='/'&&buffer[k+1]=='/') jud=1;
						 else if(buffer[k]=='/'&&buffer[k+1]=='*') jud=2;
						 else{//4~
			                news->name[i]=buffer[k];
			                if((buffer[k]=='='&&buffer[k+1]=='=')||(buffer[k]=='&'&&buffer[k+1]=='&')||
						  	   (buffer[k]=='|'&&buffer[k+1]=='|')||(buffer[k]=='>'&&buffer[k+1]=='=')||
				               (buffer[k]=='<'&&buffer[k+1]=='=')||(buffer[k]=='!'&&buffer[k+1]=='=')){
								               k=k+1;
											   i=i+1;
											   printf("%d",i);
								               news->name[i]=buffer[k];
							}
			                news->name[1+i]='\0';
			                ft=news;
			                news=(struct Word *)malloc(sizeof(struct Word));
			                ft->next=news;
			                news->next=NULL;
						 }//4~
				  }//3~
				}//2~
		}//1~
		else if(jud==1)
			if(buffer[k]=='~') jud=0;
			   else ;
		else if(jud==2)
			if(buffer[k]=='*'&&buffer[k+1]=='/') {  jud=0;  k=k+1;}
			   else ;
	}
     if(news->name[0]<33||news->name[0]>125) ft->next=NULL;

	/*******************单词转换成标识符******************
	ft=head;
	while(ft){
		if(strcmp(ft->name,"main")==0){ft->mark_name='m';}
		else if(strcmp(ft->name,"void")==0){ft->mark_name='v';}
		else if(strcmp(ft->name,"while")==0){ft->mark_name='w';}
		else if(strcmp(ft->name,"if")==0){ft->mark_name='f';}
		else if(strcmp(ft->name,"else")==0){ft->mark_name='e';}
		else if(strcmp(ft->name,"int")==0){ft->mark_name='a';}
		else if(strcmp(ft->name,"float")==0){ft->mark_name='b';}
		else if(strcmp(ft->name,"double")==0){ft->mark_name='d';}
		else if(strcmp(ft->name,"char")==0){ft->mark_name='c';}
		else if(ft->name[0]>='0'&&ft->name[0]<='9'){ft->mark_name='n';}
		else if(ft->name[0]=='+'||ft->name[0]=='-'||ft->name[0]=='*'||ft->name[0]=='/'
			  ||ft->name[0]=='='||ft->name[0]=='<'||ft->name[0]=='>'
			  ||ft->name[0]==','||ft->name[0]==';'||ft->name[0]=='('||ft->name[0]==')'
			  ||ft->name[0]=='{'||ft->name[0]=='}'){ft->mark_name=ft->name[0];}
		else if(strcmp(ft->name,"&&")==0){ft->mark_name='&';}
		else if(strcmp(ft->name,"||")==0){ft->mark_name='|';}
		else if(strcmp(ft->name,"!=")==0){ft->mark_name='@';}
		else if(strcmp(ft->name,"==")==0){ft->mark_name='#';}
		else {ft->mark_name='i';}
		ft=ft->next;
	}
	/********************初始化单词表的序号和行号*******************
	i=0;
	ft=head;
	while(ft){
		ft->num=i;
		ft->line=Lin[i];
		i++;
		ft=ft->next;
	}
	/*************************初始化符号表************************
	ft=head;
	char word_type;
	while(ft){//1~
		if(ft->mark_name=='a'||ft->mark_name=='b'||ft->mark_name=='c'||ft->mark_name=='d'){//2~
			p=ft->next;
			word_type=ft->mark_name;
			while(p->mark_name!=';'){
				if(p->mark_name!=','){
					s_look=(struct Sign *)malloc(sizeof(struct Sign));
					s_look->kind=word_type;
					strcpy(s_look->name,p->name);
					s_first->next=s_look;
					s_first=s_look;
					s_look->next=NULL;
				}
				p=p->next;
			}
			ft=p;
		}//2~
		ft=ft->next;
	}//1~
	return(head);
}

	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

package compiler;

import java.util.ArrayList;

public class Table {
	//N:S,B,A,C,,X,R,Z,Z’,U,U’,E,E’,H,H’,G,M,D,L,L’,T,T’,F,O,P,Q
	/**
	 * 非终结符：N:S,B,A,C,,X,R,Z,Z’,U,U’,E,E’,H,H’,G,M,D,L,L’,T,T’,F,O,P,Q
	 */
	public final static int S=0;
	public final static int A=1;
	public final static int B=2;
	public final static int C=3;
	public final static int X=4;
	public final static int Y=5;
	public final static int Z=6;
	public final static int Z1=7;
	public final static int U=8;
	public final static int U1=9;
	public final static int R=10;
	public final static int E=11;
	public final static int E1=12;
	public final static int H=13;
	public final static int G=14;
	public final static int H1=15;
	public final static int D=16;
	public final static int L=17;
	public final static int L1=18;
	public final static int T=19;
	public final static int T1=20;
	public final static int F=21;
	public final static int O=22;
	public final static int Q=23;
	 /**
	  * 终结符：main	printf	scanf	void	int 	char	bool	id(自定义变量)	num（int常量）	ch(char常量)
	  * if	else	while	for	;	,(	)	{	}	=	== !=	>	<	+	-	*	/	&&	||	!	++	--	#
	  */
	public final static int MAIN=0;
	public final static int PRINTF=1;
	public final static int SCANF=2;
	public final static int VOID=3;
	public final static int INT=4;
	public final static int CHAR=5;
	public final static int BOOL=6;
	public final static int ID=7;
	public final static int NUM=8;
	public final static int CH=9;
	public final static int IF=10;
	public final static int ELSE=11;
	public final static int WHILE=12;
	public final static int FOR=13;
	public final static int SEMI=14;
	public final static int COMMA=15;
	public final static int LBRA=16;
	public final static int RBRA=17;
	public final static int LBIGBRA=18;
	public final static int RBIGBRA=19;
	public final static int ASS=20;
	public final static int EQ=21;
	public final static int UNEQ=22;
	public final static int BIG=23;
	public final static int LESS=24;
	public final static int ADD=25;
	public final static int SUB=26;
	public final static int MUL=27;
	public final static int AND=28;
	public final static int OR=29;
	public final static int NON=30;
	public final static int DADD=31;
	public final static int DSUB=32;
	public final static int END=33;
	
	/**
	 * 产生式PRO:S,B,A,C,,X,R,Z,Z’,U,U’,E,E’,H,H’,G,M,D,L,L’,T,T’,F,O,P,Q
	 */
String [] PRO_S={"void","main","(",")","{","A","}"};
String [] PRO_X={"Y","Z"};
String [] PRO_Y_int={"int"};
String [] PRO_Y_char={"char"};
String [] PRO_Z={"U","Z1"};
String [] PRO_Z1={",","Z"};
String [] PRO_Z1_$={"$"};
String [] PRO_U={"id","U1"};
String [] PRO_U1={"=","L"};
String [] PRO_U1_$={"$"};
String [] PRO_R={"id","=","L"};
String [] PRO_L={"T","L1"};
String [] PRO_L1_add={"+","L"};
String [] PRO_L1_sub={"-","L"};
String [] PRO_L1_$={"$"};
String [] PRO_T={"F","T"};
String [] PRO_T1_mul={"*","T"};
String [] PRO_T1_div={"/","T"};
String [] PRO_T1_$={"$"};
String [] PRO_F={"(","L",")"};
String [] PRO_O_dadd={"++"};
String [] PRO_O_dsub={"--"};
String [] PRO_O_$={"$"};
String [] PRO_Q_forop={"id","O"};
String [] PRO_E={"H","E1"};
String [] PRO_E1_and={"&&","E"};
String [] PRO_E1_$={"$"};
String [] PRO_H={"G","H1"};
String [] PRO_H1_or={"||","H"};
String [] PRO_H1_$={"$"};
String [] PRO_G={"F","D","F"};
String [] PRO_G_lbra={"(","E",")"};
String [] PRO_G_un={"!","E"};
String [] PRO_D_big={">"};
String [] PRO_D_less={"F<"};
String [] PRO_D_eq={"=="};
String [] PRO_D_ueq={"!="};
String [] PRO_B={"if","(","E",")","{","A","}"};

String [] PRO_P={"void","main","(",")","{","A","}"};



	
}

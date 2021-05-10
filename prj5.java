package prj5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*; 
import java.util.HashMap;

class Token
{
	String stvalue;
	String type;
	
	public Token(String stvalue, String type)
	{
		this.stvalue = stvalue;
		this.type = type;
	}
}

class Attributes
{
	String type;
	int parNum;
	
	public Attributes(String type, int parNum)
	{
		this.type = type;
		this.parNum = parNum;
	}
}

public class prj5 

{	
	String makest;
	String stvalue;
	String type;
	String funkey;
	String varkey;
	String varkeyhf;
	String loptype, roptype;
	String lopType, ropType;
	String vardType, funType, tspecType, retType, expType, varType, simType, parsType, parListType, parType;
	String addType, termType, facType, callType, JType, LType, afterType, arrid, lsType, lcoptype, rcoptype;
	String facop, varop, mlop, mrop, op, temp, addop, termop, addexpop, mulop, nlop, nrop, simop, Llop, Lrop, relop, nop, mop;
	String assign, expop, paramType, param, backpatch, elsebackpatch, callop, whilebackpatch, numop,idop;
	char bpcnt = 'a';
	int count = 0;
	int codecnt = 0;
	int haselse = 0;
	int inelse = 0;
	int inwhile = 0;
	int whilecnt = 0;
	int alloc;
	int paramCnt, paramNum, argCnt;
	
	Attributes funVal;

	boolean wrong = false;
	int index = 0;
	int i;
	Token tkn;
	ArrayList<Token> tokensList = new ArrayList<Token>();
	ArrayList<String> parList = new ArrayList<String>();
	HashMap<String, String> hmap = new HashMap<String, String>();
	HashMap<String, String> hmapPar = new HashMap<String, String>();
	HashMap<String, Attributes> hmapFun = new HashMap<String, Attributes>();
	
	public Token getToken(ArrayList<Token> tknlist)
	{
		tkn = tknlist.get(index);
		return tkn;
	}
		
	public void program()
	{	
		for(int i=0;i<tokensList.size();i++)  
        {  
			//System.out.printf("%d %s %s\n", i, tokensList.get(i).stvalue, tokensList.get(i).type);     
        }
		decList();
		if (getToken(tokensList).stvalue == "$" && wrong == false)
		{
			//System.out.println();
			//System.out.println("ACCEPT");
		}
		else
		{
			//System.out.println();
			//System.out.println("REJECT");
		}
	}
	public void decList()
	{
		dec();
		A();
	}
	public void A()
	{
		//if (getToken(tokensList)!= null && getToken(tokensList)!= "$") {
		if (getToken(tokensList).stvalue.equals("int") || getToken(tokensList).stvalue.equals("void"))
		{
			dec();
			A();
		}
		else
		{
			//System.out.println("Accept @ on A");
		}
	}
	public void dec()
	{
		index++; index++;
		if (getToken(tokensList).stvalue.equals(";") || getToken(tokensList).stvalue.equals("[")) 
		{
			--index; --index;
			varDec();
		}
		else if (getToken(tokensList).stvalue.equals("("))
		{
			--index; --index;
			funDec();
		}
		else
		{
			//System.out.println("REJECT grammar on dec");
			--index; --index;
			wrong = true;
		}
	}
	public void varDec()
	{
		typeSpec();
		vardType = tspecType;
		B();
	}
	public void typeSpec()
	{
		//System.out.println(getToken(tokensList));
		if (getToken(tokensList).stvalue.equals("int"))
		{
			tspecType = "int";
			//System.out.printf("%d ",index);
			//System.out.println("Accept int on typeSpec");
			index++;
		}
		else if (getToken(tokensList).stvalue.equals("void"))
		{
			tspecType = "void";
			//System.out.printf("%d ",index);
			//System.out.println("Accept void on typeSpec");
			index++;
		}
		else
		{
			tspecType = "";
			//System.out.printf("%d ",index);
			//System.out.printf("Reject grammar %s on typeSpec\n", getToken(tokensList).stvalue);
			index++;
			wrong = true;
		}
	}
	public void B()
	{
		if (getToken(tokensList).type.equals("ID"))
		{
			idop = getToken(tokensList).stvalue;
			hmap.put(getToken(tokensList).stvalue, vardType);
			//System.out.printf("Inserted key:%s value:%s\n",getToken(tokensList).stvalue, vardType);
			
			//System.out.printf("%d ",index);
			//System.out.println("Accept ID on B");
			index++;
			//System.out.printf("vardType is %s\n", vardType);
			C();
		}
	}
	public void C()
	{
		if (getToken(tokensList).stvalue.equals(";"))
		{
			if (hmap.get(idop).equals("int"))
			{
				codecnt +=1;
				System.out.printf("%d	alloc	4		%s\n", codecnt, idop);
			}
			//System.out.printf("%d ",index);
			//System.out.println("Accept ; on C");
			index++;
		}
		else if (getToken(tokensList).stvalue.equals("["))
		{			
			//System.out.printf("vardType is %s\n", vardType);
			if (vardType.equals("int"))
			{
				varType = "intarr";
				index--;
				//System.out.printf("Replaced var '%s' type:%s for newtype:%s\n", getToken(tokensList).stvalue, hmap.get(getToken(tokensList).stvalue), varType);
				hmap.replace(getToken(tokensList).stvalue, varType);
				index++;
			}
			else if (vardType.equals("intarr"))
			{
				//System.out.printf("Vardec %s is already type:%s.on varDec()\n", getToken(tokensList).stvalue, varType);
			}
			else
			{
				//System.out.println("ERROR! varType must be int to have intarr on C()");
				//wrong = true;
			}
			
			//System.out.printf("%d ",index);
			//System.out.println("Accept [ on C");
			index++;
			if (getToken(tokensList).type.equals("NUM"))
			{
				numop = getToken(tokensList).stvalue;
				//System.out.printf("%d ",index);
				//System.out.println("Accept NUM on C");
				index++;
			}
			else
			{
				//System.out.printf("%d ",index);
				//System.out.println("REJECT NUM on C");
				index++;
				wrong = true;
			}
			if (getToken(tokensList).stvalue.equals("]"))
			{
				//System.out.printf("%d ",index);
				//System.out.println("Accept ] on C");
				index++;
			}
			else 
			{
				//System.out.printf("%d ",index);
				//System.out.println("REJECT ] on C");
				index++;
				wrong = true;
			}
			if (getToken(tokensList).stvalue.equals(";"))
			{
				//System.out.printf("%d ",index);
				//System.out.println("Accept ; on C");
				index++;
			}
			else
			{
				//System.out.printf("%d ",index);
				//System.out.println("REJECT ; on C");
				index++;
				wrong = true;
			}
			//System.out.print(hmap.get(idop));
			if (hmap.get(idop).equals("int") || hmap.get(idop).equals("intarr"))
			{
				codecnt +=1;
				alloc = 4 * Integer.parseInt(numop);
				System.out.printf("%d	alloc	%d		%s\n", codecnt, alloc, idop);
			}
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("REJECT on C");
			index++;
			wrong = true;
		}
	}
	public void funDec()
	{
		//System.out.println("Hmap:");
		//System.out.print(hmap);
//		if(!(hmap.isEmpty()))
//		{
//			hmapCol.putAll(hmap);
			hmap.clear();
//			//System.out.println("\nHmap:");
//			//System.out.print(hmap);
//			//System.out.println();
			paramCnt = 0;
//		}
		typeSpec();
		funType = tspecType;
		if (getToken(tokensList).type.equals("ID"))
		{
			funkey = getToken(tokensList).stvalue;
			//System.out.printf("%d ",index);
			//System.out.println("Accept ID on funDec");
			index++;
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("REJECT ID on funDec");
			index++;
			wrong = true;
		}
		if (getToken(tokensList).stvalue.equals("("))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept ( on funDec");
			index++;
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("REJECT( on funDec");
			index++;
			wrong = true;
		}
		Attributes att = new Attributes("", 0);
		params();
		//System.out.printf("Parameter type is %s\n", parsType);
		//System.out.printf("%s %s\n", funkey, funType);
		att.type = funType;
		att.parNum = paramCnt;
		hmapFun.put(funkey, att);
		//System.out.printf("Inserted function key:%s value:(%s,%d)\n", funkey, att.type, att.parNum);
		codecnt +=1;
		System.out.printf("%d	func   %s	%s	%s\n", codecnt, funkey, att.type, att.parNum);
		if (hmapFun.get(funkey).parNum > 0)
		{
			for(int i = (parList.size() - paramCnt) ; i < parList.size(); i++)  
	        { 
				param = parList.get(i);
				paramType = hmapPar.get(param);			
				codecnt +=1;
				System.out.printf("%d	param			%s\n", codecnt, param);	
				if (paramType.equals("int"))
				{
					codecnt +=1;
					System.out.printf("%d	alloc	4		%s\n", codecnt, param);	
				}
	        }
		}
			
		if (getToken(tokensList).stvalue.equals(")"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept ) on funDec");
			index++;
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("REJECT ) on funDec");
			index++;
			wrong = true;
		}
		compoundStmt();	
		codecnt +=1;
		if (backpatch != null)
		{
			if (haselse ==1)
			{
				System.out.printf("%d	end	func	%s		val for %s\n", codecnt, funkey, elsebackpatch);
			}
			else
			{
				System.out.printf("%d	end	func	%s		val for %s\n", codecnt, funkey, backpatch);
			}
		}
		else
		{
			System.out.printf("%d	end	func	%s	\n", codecnt, funkey);
		}
		backpatch = null;
		haselse = 0;
	}
	public void params()
	{
		if (getToken(tokensList).stvalue.equals("void"))
		{
			index++;
			if (getToken(tokensList).type.equals("ID"))
			{
				index--;
				paramList();
				parsType = parListType;
			}
			else
			{
				index--;
				//System.out.printf("%d ",index);
				//System.out.println("Accept void on params");
				parsType = "void";
				index++;
			}	
		}
		else if (getToken(tokensList).stvalue.equals("int"))
		{
			paramList();
			parsType = parListType;
		}
		else 
		{
			//System.out.printf("%d ",index);
			//System.out.println("REJECT grammar on params");
			wrong = true;
		}
	}
	public void paramList()
	{
		param();
		parListType = parType;
		if (getToken(tokensList)!= null && getToken(tokensList).stvalue!= "$")
		D();
	}
	public void D()
	{
		if (getToken(tokensList).stvalue.equals(","))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept , on D");
			index++;
			param(); 
			parListType = parType;
			D();
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept @ on D");
		}
	}
	public void param()
	{
		typeSpec();
		parType = tspecType;
		E();
	}
	public void E()
	{
		if (getToken(tokensList).type.equals("ID"))
		{
			paramCnt += 1;
			hmap.put(getToken(tokensList).stvalue, parType);
			hmapPar.put(getToken(tokensList).stvalue, parType);
			parList.add(getToken(tokensList).stvalue);
		    //System.out.printf("Inserted param key:%s value:%s\n ",getToken(tokensList).stvalue, parType);
			//System.out.printf("%d ",index);
			//System.out.println("Accept ID on E");
			index++;
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("REJECT ID on E");
			index++;
			wrong = true;
		}
		F();
	}
	public void F()
	{
		if (getToken(tokensList).stvalue.equals("["))
		{
			if (parType.equals("int"))
			{
				parType = "intarr";
				index--;
				hmap.put(getToken(tokensList).stvalue, parType);
			//	//System.out.printf("Inserted param key:%s value:%s", getToken(tokensList).stvalue, parType);
				index++;			
			}
			else
			{
				//System.out.println("ERROR! ID [] must be of type int array");
				//wrong = true;
			}
			//System.out.printf("%d ",index);
			//System.out.println("Accept [ on F");
			index++;
			if (getToken(tokensList).stvalue.equals("]"))
			{
				//System.out.printf("%d ",index);
				//System.out.println("Accept ] on F");
				index++;
			}
			else
			{
				//System.out.printf("%d ",index);
				//System.out.println("REJECT ] on F");
				index++;
				wrong = true;
			}
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept @ on F");
		}
	}
	public void compoundStmt()
	{
		if (getToken(tokensList).stvalue.equals("{"))
		{			
			//System.out.printf("%d ",index);
			//System.out.println("Accept { on compoundStmt");
			index++;
			localDeclarations();
			statementList();
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("REJECT { on compoundStmt");
			index++;
			wrong = true;
		}
		if (getToken(tokensList).stvalue.equals("}"))
		{			
			//System.out.printf("%d ",index);
			//System.out.println("Accept } on compoundStmt");
			index++;
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.printf("on compound statment token is %s\n", getToken(tokensList));
			//System.out.println("REJECT } on compoundStmt");
			index++;
			wrong = true;
		}
	}
	public void localDeclarations()
	{
		G();
	}
	public void G()
	{
		if (getToken(tokensList).stvalue.equals("int") || getToken(tokensList).stvalue.equals("void"))
		{
			varDec();
			G();
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept @ on G");
		}
	}
	public void statementList()
	{
		H();
	}
	public void H()
	{
		if (getToken(tokensList).stvalue.equals(";") || getToken(tokensList).type.equals("ID") || getToken(tokensList).stvalue.equals("(") || getToken(tokensList).type.equals("NUM") || getToken(tokensList).stvalue.equals("{") || getToken(tokensList).stvalue.equals("if") || getToken(tokensList).stvalue.equals("while") || getToken(tokensList).stvalue.equals("return"))
		{
			statement();
			H();
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept @ on H");
		}
	}
	public void statement()
	{
		if (getToken(tokensList).stvalue.equals(";") || getToken(tokensList).type.equals("ID") || getToken(tokensList).stvalue.equals("(") || getToken(tokensList).type.equals("NUM"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept go to expressionStmt() on statement");
			expressionStmt();
		}
		else if (getToken(tokensList).stvalue.equals("{"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept go to compoundStmt() on statement");
			compoundStmt();
		}
		else if (getToken(tokensList).stvalue.equals("if"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept go to selectionStmt() on statement");
			selectionStmt();			
		}
		else if (getToken(tokensList).stvalue.equals("while"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept go to iterationStmt() on statement");			
			iterationStmt();
			codecnt +=1;
			System.out.printf("%d	end	block		\n", codecnt);
			codecnt +=1;
			System.out.printf("%d	BR			%d	val for %s\n", codecnt, whilecnt, whilebackpatch);
		}
		else if (getToken(tokensList).stvalue.equals("return"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept go to returnStmt() on statement");
			returnStmt();
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("Reject grammar on statement");
			wrong = true;
		}
	}
	public void expressionStmt()
	{
		if (getToken(tokensList).stvalue.equals(";"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept ; on expressionStmt");
			index++;
		}
		else
		{
			expression();
			if (getToken(tokensList).stvalue.equals(";"))
			{
				//System.out.printf("%d ",index);
				//System.out.println("Accept ; on expressionStmt");
				index++;
			}
			else
			{
				//System.out.printf("%d ",index);
				//System.out.println(getToken(tokensList));
				//System.out.println("REJECT ; on expressionStmt");
				index++;
				wrong = true;
			}
		}		
	}
	public void selectionStmt()
	{
		if (getToken(tokensList).stvalue.equals("if"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept if on selectionStmt");
			index++;
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("REJECT if on selectionStmt");
			index++;
			wrong = true;
		}
		if (getToken(tokensList).stvalue.equals("("))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept ( on selectionStmt");
			index++;
			expression();
			if (getToken(tokensList).stvalue.equals(")"))
			{
				//System.out.printf("%d ",index);
				//System.out.println("Accept ) on selectionStmt");
				index++;
			}
			else
			{
				//System.out.printf("%d ",index);
				//System.out.println("REJECT ) on selectionStmt");
				index++;
				wrong = true;
			}
			nop = null;
			mop = null;
			op = null;
			statement();
			I();		
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("REJECT ( on selectionStmt");
			index++;
			wrong = true;
		}		
	}
	public void I()
	{
		if (getToken(tokensList).stvalue.equals("else"))
		{
			inelse = 1;
			elsebackpatch = "bp" + bpcnt;
			bpcnt += 1;		
			codecnt +=1;
			System.out.printf("%d	BR			???	%s = %d\n", codecnt, elsebackpatch, codecnt);
			//System.out.printf("%d ",index);
			//System.out.println("Accept else on I");
			index++;
			statement();
			haselse = 1;	
			inelse = 0;
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept @ on I");
		}
	}
	
	public void iterationStmt()
	{
		if (getToken(tokensList).stvalue.equals("while"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept while on iterationStmt");
			index++;
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("REJECT while on iterationStmt");
			index++;
			wrong = true;
		}
		if (getToken(tokensList).stvalue.equals("("))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept ( on iterationStmt");
			index++;
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("REJECT ( on iterationStmt");
			index++;
			wrong = true;
		}
		inwhile = 1;
		expression();
		inwhile = 0;
		if (getToken(tokensList).stvalue.equals(")"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept ) on iterationStmt");
			index++;
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("REJECT ) on iterationStmt");
			index++;
			wrong = true;
		}
		codecnt +=1;
		System.out.printf("%d	block			\n", codecnt);
		statement();
		nop = null;
		mop = null;
		op = null;
	}
	public void returnStmt()
	{
		if (getToken(tokensList).stvalue.equals("return"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept return on returnStmt");
			index++;
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("REJECT return on returnStmt");
			index++;
			wrong = true;
		}
		J();
		retType = JType;
		//System.out.printf("Comparing retType:%s funType:%s\n",retType, funType);

	}
	public void J()
	{
		if (getToken(tokensList).stvalue.equals(";"))
		{
			JType = "void";
			//System.out.printf("%d ",index);
			//System.out.println("Accept ; on J");
			index++;
		}
		else
		{
			expression();
			JType = expType;
			if (getToken(tokensList).stvalue.equals(";"))
			{
				//System.out.printf("%d ",index);
				//System.out.println("Accept ; on J");
				index++;
			}
			else
			{
				//System.out.printf("%d ",index);
				//System.out.println("REJECT ; on J");
				index++;
				wrong = true;
			}
			codecnt += 1;
			System.out.printf("%d	return			%s\n", codecnt, temp);
		}		
	}
	public void expression()
	{
		if (getToken(tokensList).type.equals("ID"))
		{			
			index++;
			if (getToken(tokensList).stvalue.equals("="))
			{
				index--;
				var();
				index--;
				assign = getToken(tokensList).stvalue;
				index++;
				expType = varType;
				lsType = expType;
				//System.out.printf("Left side type '%s'\n", lsType);
				if (getToken(tokensList).stvalue.equals("="))
				{
					//System.out.printf("%d ",index);
					//System.out.println("Accept = on expression");
					index++;					
				}
				else
				{
					//System.out.printf("%d ",index);
					//System.out.println("REJECT = on expression");
					index++;
					wrong = true;
				}
				expression();
				expop = simop;
				codecnt +=1;
				if (inelse == 1)
				{
					System.out.printf("%d	assign	%s		%s	val for %s\n", codecnt, expop, assign, backpatch); 
				}
				else
				{
					System.out.printf("%d	assign	%s		%s\n", codecnt, expop, assign);
				}
				afterType = expType;
				//System.out.printf("Right side type after = is '%s'\n", afterType);
				//System.out.printf("Checking left type '%s' vs right type '%s'.\n", lsType, afterType);
				if(lsType == afterType && !lsType.equals("intarr"))
				{
					//System.out.println("The assignment exp matches types in both sides");
				}
				else
				{
					//System.out.println("ERROR! left and right side type of assig. exp must be equal and cannot be intarr.");
				//	wrong = true;
				}
				//if (lsType.equals("intarr") && expType.equals("int"))
			}
			else if (getToken(tokensList).stvalue.equals("["))
			{
				index++;
				index++;
				index++;
				if (getToken(tokensList).stvalue.equals("="))
				{
					index--;
					index--;
					index--;
					index--;
					var();
					expType = varType;
					lsType = expType;
					//System.out.printf("left side type '%s'\n", lsType);	
					if (getToken(tokensList).stvalue.equals("="))
					{
						//System.out.printf("%d ",index);
						//System.out.println("Accept = on expression");
						index++;
					}
					else
					{
						//System.out.printf("%d ",index);
						//System.out.println("REJECT = on expression");
						index++;
						wrong = true;
					}
					expression();
					afterType = expType;
					//System.out.printf("Expression type after = is '%s'\n", afterType);
					//System.out.printf("Checking left type '%s' vs right type '%s'.\n", lsType, afterType);
					if(lsType == afterType && !lsType.equals("intarr"))
					{
						//System.out.println("The assignment exp matches types in both sides");
					}
					else
					{
						//System.out.println("ERROR! left and right side type of assig. exp must be equal and cannot be intarr");
						wrong = true;
					}
				}
				else
				{
					index--;
					index--;
					index--;
					index--;
					simpleExpression();
					expType = simType;
				}
			}
			else
			{
				index--;;
				simpleExpression();
				expType = simType;
				//System.out.printf("itwashere '%s'\n", simType);
			}
		}	
		else if (getToken(tokensList).stvalue.equals("(") || getToken(tokensList).type.equals("NUM"))
		{
			simpleExpression();
			expType = simType;
		}
	}
	public void var()
	{
		if (getToken(tokensList).type.equals("ID"))
		{
			varop = getToken(tokensList).stvalue;
			//System.out.printf("Searching key:%s value:%s on var()\n", getToken(tokensList).stvalue, hmap.get(getToken(tokensList).stvalue));
			if (hmap.get(getToken(tokensList).stvalue) != null)
			{
				varType = hmap.get(getToken(tokensList).stvalue);
				//System.out.printf("Var '%s' is of type: %s.\n", getToken(tokensList).stvalue, varType);
				if(varType.equals("void"))
				{
					//System.out.println("ERROR! Variable can not be of type void.");
					//wrong = true;
				}				
			}
			else 
			{
				//System.out.printf("ERROR! undeclared var '%s' on var()\n", getToken(tokensList).stvalue);
				//varType = "undeclared";
				//wrong = true;
			}
			//System.out.printf("%d ",index);
			//System.out.println("Accept ID on var");
			index++;
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("REJECT ID on var");
			index++;
			wrong = true;
		}
		K();
	}
	public void K()
	{
		if (getToken(tokensList).stvalue.equals("["))
		{			
			if (varType.equals("intarr"))
			{
				index--;
				arrid = getToken(tokensList).stvalue;
				//System.out.printf("Variable '%s' is of type intarr on K()\n", arrid);
				index++;
			}
			else
			{
				//System.out.println("ERROR! varType must be intarr on K()");
				//wrong = true;
			}
			
			//System.out.printf("%d ",index);
			//System.out.println("Accept [ on K");
			index++;
			if (getToken(tokensList).stvalue.equals("]"))
			{
				//System.out.println("ERROR! array must become int");
				//wrong = true;
			}
			else
			{
				varType = "int";
				//System.out.printf("Replaced varType to '%s'\n", varType);
			}
			expression();
			//System.out.printf("Expression Type inside ID[] is '%s'.\n", expType);
			if (numop != "")
			{
				temp = "_t" + count;
				count += 1;
				codecnt +=1;
				alloc = 4 * Integer.parseInt(numop);
				System.out.printf("%d	disp	%s	%d	%s\n", codecnt, varop, alloc, temp);
				varop = temp;
			}
			
			if (expType.equals("int"))
			{
				//System.out.println("ID[] has int inside the bracket");
			}
			else 
			{
				//System.out.println("ERROR! ID[] can only have int inside the brackets");
				//wrong = true;
			}
			
			if (getToken(tokensList).stvalue.equals("]"))
			{
				//System.out.printf("%d ",index);
				//System.out.println("Accept ] on K");
				index++;
			}
			else
			{
				//System.out.printf("%d ",index);
				//System.out.println("REJECT ] on K");
			}
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept @ on K");
		}
	}
	public void simpleExpression()
	{
		additiveExpression(); 
		simop = addexpop;
		simType = addType;
		lcoptype = simType;
		nop = null;
		mop = null;
		L();
	}
	public void L()
	{
		if (getToken(tokensList).stvalue.equals("<=") || getToken(tokensList).stvalue.equals("<") || getToken(tokensList).stvalue.equals(">") || getToken(tokensList).stvalue.equals(">=") || getToken(tokensList).stvalue.equals("==") || getToken(tokensList).stvalue.equals("!="))
		{
			relop();
			additiveExpression();
			rcoptype = addType;
			Llop = simop;
			op = "comp";
			Lrop = addexpop;
			//last = temp;
			//System.out.printf("Llop is '%s' and lrop is '%s' and op is '%s'\n",Llop,Lrop,op);
			if (Llop != null && Lrop != null && op != null)
			{						
				temp = "_t" + count; 
				count += 1;
				codecnt +=1;
				System.out.printf("%d	%s	%s	%s	%s\n", codecnt, op, Llop, Lrop, temp);
				codecnt +=1;				
				backpatch = "bp" + bpcnt;
				bpcnt += 1;				
				System.out.printf("%d	%s	%s		???	%s = %d\n", codecnt, relop, temp, backpatch, codecnt);							
			}
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept @ on L");
		}
	}
	public void relop()
	{
		//System.out.println("Checking that relop is only used when appropiate");
		if (getToken(tokensList).stvalue.equals("<=") || getToken(tokensList).stvalue.equals("<") || getToken(tokensList).stvalue.equals(">") || getToken(tokensList).stvalue.equals(">=") || getToken(tokensList).stvalue.equals("==") || getToken(tokensList).stvalue.equals("!="))
		{
			if (getToken(tokensList).stvalue.equals("<="))
			{
				relop = "BRGT";
			}
			else if (getToken(tokensList).stvalue.equals(">="))
			{
				relop = "BRLT";
			}
			else if (getToken(tokensList).stvalue.equals(">"))
			{
				relop = "BRLE";
			}
			else if (getToken(tokensList).stvalue.equals("<"))
			{
				relop = "BRGE";
			}
			else if (getToken(tokensList).stvalue.equals("=="))
			{
				relop = "BRNE";
			}
			else if (getToken(tokensList).stvalue.equals("!="))
			{
				relop = "BREQ";
			}
			//System.out.printf("%d ",index);
		//	//System.out.printf("Accept %s on relop\n", getToken(tokensList).stvalue);
			index++;
		}
	}
	public void additiveExpression()
	{
		term();
	//	if (temp != null)
	//	{
	//		addexpop = temp;
	//	}
		//else
	//	{
			addexpop = termop;
		//}
		addType = termType;
		M();
		if (mop != null)
		{
			addexpop = mop;
		}
	}
	public void M()
	{
		if (getToken(tokensList).stvalue.equals("+") || getToken(tokensList).stvalue.equals("-"))
		{
			//System.out.println("Checking operand types on addop():"); 
			index --;
			//System.out.printf("The left operand is %s\n", getToken(tokensList).stvalue);
			index ++;
			lopType = addType;
			addop(); 
			term(); 
			index --;
			//System.out.printf("The right operand is %s\n", getToken(tokensList).stvalue);
			index ++;
			addType = termType;
			ropType = addType;
			if (mop != null)
			{
				mlop = mop;
			}	
			else
			{
				mlop = addexpop;
			}
			op = addop;
			mrop = termop;
			//System.out.printf("mlop is '%s' and mrop is '%s' and op is '%s'\n",mlop,mrop,op);
			if (mlop != null && mrop != null && op != null)
			{				
				temp = "_t" + count;  
				count += 1;
				codecnt += 1;
				if (inwhile == 1)
				{
					backpatch = "bp" + bpcnt;
					bpcnt += 1;
					whilecnt = codecnt;
					whilebackpatch =  backpatch;
					System.out.printf("%d	%s	%s	%s	%s	%s = %d\n", codecnt, op, mlop, mrop, temp, backpatch, codecnt);
				}
				else
				{
					System.out.printf("%d	%s	%s	%s	%s\n", codecnt, op, mlop, mrop, temp);
				}
				hmap.put(temp, hmap.get(mlop));
				//System.out.printf("Inserted key:%s value:%s\n", temp, hmap.get(temp));
				mop = temp;
			}
			M();
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept @ on M");		
		}
	}
	public void addop()
	{
		if (getToken(tokensList).stvalue.equals("+"))
		{
			addop = "add";
			//System.out.printf("%d ",index);
			//System.out.println("Accept + on addop");
			index ++;
		}
		else if (getToken(tokensList).stvalue.equals("-"))
		{
			addop = "sub";
			//System.out.printf("%d ",index);
			//System.out.println("Accept - on addop");
			index++;
		}
		else 
		{
			//System.out.printf("%d ",index);
			//System.out.println("REJECT addop on addopp");
			index++;
			wrong = true;
		}
	}
	public void term()
	{
		factor();
	//	if (temp != null)
	//	{
	//		termop = temp;
	//	}
	//	else
	//	{
			termop = facop;;
	//	}		
		termType = facType;
		N();
		if (nop != null)
		{
			termop = nop;
		}	
	}
	public void N()
	{
		if (getToken(tokensList).stvalue.equals("*") || getToken(tokensList).stvalue.equals("/"))
		{
			//System.out.println("Checking operand types on mulop():"); 
			index --;
			//System.out.printf("The left operand is %s\n", getToken(tokensList).stvalue);
			index ++;
			lopType = termType;
			op = "";
			mulop();			
			factor();
			index --;
			//System.out.printf("The right operand is %s\n", getToken(tokensList).stvalue);
			index ++;
			termType = facType;
			ropType = termType;
			if (nop != null)
			{
				nlop = nop;
			}	
			else
			{
				nlop = termop;
			}
			op = mulop;
			nrop = facop;
			if (nlop != null && nrop != null && op != null)
			{				 
				temp = "_t" + count;
				count += 1;
				codecnt +=1;
				if (inwhile == 1)
				{
					backpatch = "bp" + bpcnt;
					bpcnt += 1;
					whilecnt = codecnt;
					whilebackpatch =  backpatch;
					System.out.printf("%d	%s	%s	%s	%s	%s = %d\n", codecnt, op, nlop, nrop, temp, backpatch, codecnt);
					inwhile = 0;
				}
				else
				{
				System.out.printf("%d	%s	%s	%s	%s \n", codecnt, op, nlop, nrop, temp);
				}
				hmap.put(temp, hmap.get(nlop));
			//	//System.out.printf("Inserted key:%s value:%s\n", temp, hmap.get(temp));
				nop = temp;
			}
			N();
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept @ on N");
		}
	}
	public void mulop()
	{
		if (getToken(tokensList).stvalue.equals("*"))
		{
			mulop = "mult";
			//System.out.printf("%d ",index);
			//System.out.println("Accept * on mulop");
			index++;
		}
		else if (getToken(tokensList).stvalue.equals("/"))
		{
			mulop = "div";
			//System.out.printf("%d ",index);
			//System.out.println("Accept / on mulop");
			index++;
		}
		else 
		{
			//System.out.printf("%d ",index);
			//System.out.println("REJECT mulop on mulop");
			index++;
			wrong = true;
		}
	}
	public void factor()
	{
		if (getToken(tokensList).stvalue.equals("("))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept ( on factor");
			index ++;
			expression();
			facop = temp;
			facType = expType;
			if (getToken(tokensList).stvalue.equals(")"))
			{
				//System.out.printf("%d ",index);
				//System.out.println("Accept ) on factor");
				index++;
			}
			else
			{
				//System.out.printf("%d ",index);
				//System.out.println("REJECT ) on factor");
				index++;
				wrong = true;
			}
			
		}
		else if (getToken(tokensList).type.equals("NUM"))
		{
			numop = getToken(tokensList).stvalue;
			facop = getToken(tokensList).stvalue;
			facType = "int";
			hmap.put(getToken(tokensList).stvalue, facType);
			//System.out.printf("Inserted key:%s value:%s\n", getToken(tokensList).stvalue, facType);
			//System.out.printf("%d ",index);
			//System.out.println("Accept NUM on factor");
			index++;
		}
		else if (getToken(tokensList).type.equals("ID"))
		{
			index++;
			if (getToken(tokensList).stvalue.equals("("))
			{
				index--;
				call();
				facop = callop;
				facType = callType;
			}
			else 
			{
				index --;
				var();
				facop = varop;
				facType = varType;
			}
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("REJECT grammar on factor");
			wrong = true;
		}

	}
	public void call()
	{
		argCnt = 0;
		if (getToken(tokensList).type.equals("ID"))
		{
			callop = getToken(tokensList).stvalue;
			index++; index++; index++;
			//System.out.printf("Three tokens ahead: %s\n",getToken(tokensList).stvalue);
			if (getToken(tokensList).stvalue.equals("("))
			{
				index--; index--; index--;
				varkeyhf = getToken(tokensList).stvalue;
				//System.out.printf("varkeyhf: %s\n", varkeyhf);
			}
			else
			{
				index--; index--; index--;
				varkey = getToken(tokensList).stvalue;	
			}
			varkey = getToken(tokensList).stvalue;
			//System.out.println(varkey);
			//System.out.printf("Searching key:%s value:%s on Call()\n", getToken(tokensList).stvalue, hmap.get(getToken(tokensList).stvalue));
			//System.out.println("HmapFun:");
			for (String key: hmapFun.keySet())
			{
				funVal = hmapFun.get(key);
				callType = funVal.type;
				paramNum = funVal.parNum;
				//System.out.printf("Contents of function hmap key:%s value:(%s,%s) on Call()\n", key, callType, paramNum);
			}
			if (hmapFun.get(varkey) != null)
			{
				funkey = getToken(tokensList).stvalue;
				funVal = hmapFun.get(funkey);
				callType = funVal.type;
				paramNum = funVal.parNum;
				//System.out.printf("Searching function key:%s value:(%s,%s) on call()\n", funkey, callType, paramNum);
			}
			else 
			{
				//System.out.printf("ERROR! Undeclared variable %s on call()\n", varkey);
				//wrong = true;
			}
			//System.out.printf("%d ",index);
			//System.out.println("Accept ID on call");
			index++;
			if (getToken(tokensList).stvalue.equals("("))
			{
				//System.out.printf("%d ",index);
				//System.out.println("Accept ( on call");
				index++;
				args();
				if (getToken(tokensList).stvalue.equals(")"))
				{
					//System.out.printf("%d ",index);
					//System.out.println("Accept ) on call");
					index++;
					index--; index--;
					//System.out.println(getToken(tokensList).stvalue);
					if (getToken(tokensList).stvalue.equals(")"))
					{
						//System.out.print("inside dthing\n");
						varkey = varkeyhf;
						//System.out.printf("varkeyhf value: %s\n" ,varkeyhf);
					}
					index++; index++;
					if (hmapFun.get(varkey) != null)
					{	
						//System.out.println(varkey);
						paramCnt = hmapFun.get(varkey).parNum; 
					}
					//System.out.printf("Compare number for fun %s Params:%d Args:%d\n",varkey, paramCnt, argCnt);
					if (paramCnt != argCnt)
					{
						//System.out.println("ERROR! The number of params and args does not match.");
						//wrong = true;
					}					
				}
				else
				{
					//System.out.printf("%d ",index);
					//System.out.println(getToken(tokensList).stvalue);
					//System.out.println("REJECT ) on call");
					index++;
					wrong = true;
				}
			}
			else
			{
				//System.out.printf("%d ",index);
				//System.out.println("REJECT ( on call");
				index++;
				wrong = true;
			}
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("REJECT ID on call");
			index++;
			wrong = true;
		}
		temp = "_t" + count; 
		count += 1;
		codecnt += 1;
		System.out.printf("%d	call	%s	%d	%s\n", codecnt, callop, argCnt, temp);
		callop = temp;
		argCnt = 0;
	}
	public void args()
	{
		//if (getToken(tokensList).equals("ID") || getToken(tokensList).equals("("))
		if (getToken(tokensList).stvalue.equals(")"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept @ on args");
		}
		else 
			argsList();
	}
	public void argsList()
	{
		expression();
		codecnt += 1;
		System.out.printf("%d	arg			%s\n", codecnt, varop);
		argCnt += 1;
		//System.out.printf("Arg count at this point is %d in arglist()\n", argCnt);
		O();
	}
	public void O()
	{
		if (getToken(tokensList).stvalue.equals(","))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept , on O");
			index++;
			expression();
			codecnt += 1;
			System.out.printf("%d	arg			%s\n", codecnt, varop);
			argCnt += 1;
			//System.out.printf("Arg count at this point is %d in O()\n", argCnt);
			O();
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept @ on O");
		}
	}
	
	public void fillup(BufferedReader bufr) throws IOException
	{
		String line;
		Character current;		
		String buffer = "";
		String bufferVal = "";
		Token tokenInd = new Token(stvalue, type);
		while ((line = bufr.readLine()) != null)
		{			
			Token token = new Token(stvalue, type);
			int i = 0;
			boolean valid = true;					
			//current = line.charAt(i);
			//buffer = Character.toString(current);			

				for(i=0;i<line.length();i++)
				{	
					current = line.charAt(i);	
					if (Character.isLetter(current))
					{
						while (Character.isLetter(current) && i < line.length())
						{
							current = line.charAt(i);
							buffer += Character.toString(current);
							i++;
						}

						if (buffer.equals("INPUT:"))
						{
							valid = false;
							token.type = "INPUT";
							//buffer = "INPUT";
							//break;
						}
						else if (buffer.equals("ID:"))
						{
							//buffer = "ID";
							token.type = "ID";
							//break;
						}
						else if (buffer.equals("NUM:"))
						{
							//buffer = "NUM";
							token.type = "NUM";
							//break;
						}
						else if (buffer.equals("KW:"))
						{
							token.type = "KW";
							//break;
						}
						//System.out.printf("%d and line.lenght is %d\n", i, line.length());
						if (i < line.length()-1)
						{
						++i;
						current = line.charAt(i);
						}
						while (Character.isLetter(current) && i < line.length())
						{
							current = line.charAt(i);
							bufferVal += Character.toString(current);
							i++;
						}
						while (Character.isDigit(current) && i < line.length())
						{
							current = line.charAt(i);
							bufferVal += Character.toString(current);
							i++;
						}
						//buffer = bufferKW;
						token.stvalue = bufferVal;
						//break;
					 }
					else if (isSpecialSymbol(current))
					{
						while(isSpecialSymbol(current) && i < line.length())
						{
							current = line.charAt(i);
							buffer += Character.toString(current);
							i++;
						}
						token.stvalue = buffer;
						token.type = "operand";
					}
				}
				if (valid && token.stvalue != null)
				{
					tokensList.add(token);
				}
				buffer = "";
				bufferVal = "";
				//token.stvalue = "";
				//token.type = "";
		}
		tokenInd.stvalue = "$";
		tokenInd.type = "indicator";
		tokensList.add(tokenInd);
	}
	
	static boolean isSpecialSymbol (char c)
    {
        int i;
        char symbols[]= {'+','-','*','/','<','>','=',';',',','(',')','[',']','{','}','!'};
        for(i=0;i<symbols.length;i++)
        {
            if (Character.compare(c, symbols[i]) == 0)
            return true;
        }
        return false;
    }
	public static void main(String[] args) throws Exception 
	{	
		//File smplInput = new File (args[0]);		
		File smplInput = new File ("file_path");				
		BufferedReader bufr = new BufferedReader(new FileReader(smplInput));	
	//	try {
		prj5 classins = new prj5();
		classins.fillup(bufr);
		classins.program();
	//	}catch(IndexOutOfBoundsException e)
	//	{
	//		//System.out.println("IndexOutOfBoundsException"); 
	//	}		
	}
}




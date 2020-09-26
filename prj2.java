package prj2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*; 

public class prj2 
{
	String token;
	String result;
	boolean wrong = false;
	int index = 0;
	int i;
	String stringtkn;
	ArrayList<String> tokensList = new ArrayList<String>();
	
	public String getNextToken(ArrayList<String> tokens)
	{	
		index++;
		stringtkn= tokens.get(index);
		return stringtkn;
	}
	
	public String getToken(ArrayList<String> tokens)
	{
		stringtkn= tokens.get(index);
		return stringtkn;
	}
	
	public void program()
	{	
		for(int i=0;i<tokensList.size();i++)  
        {  
			//System.out.printf("%d %s\n", i, tokensList.get(i));     
        }
		decList();
		if (getToken(tokensList) == "$" && wrong == false)
		{
			//System.out.println("ACCEPT");
		}
		else
		{
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
		if (getToken(tokensList).equals("int") || getToken(tokensList).equals("void"))
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
		if (getToken(tokensList).equals(";") || getToken(tokensList).equals("[")) 
		{
			--index; --index;
			varDec();
		}
		else if (getToken(tokensList).equals("("))
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
		B();
	}
	public void typeSpec()
	{
		//System.out.println(getToken(tokensList));
		if (getToken(tokensList).equals("int"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept int on typeSpec");
			index++;
		}
		else if (getToken(tokensList).equals("void"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept void on typeSpec");
			index++;
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("Reject grammar on typeSpec");
			index++;
			wrong = true;
		}
	}
	public void B()
	{
		if (getToken(tokensList).equals("ID"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept ID on B");
			index++;
				C();
		}
	}
	public void C()
	{
		if (getToken(tokensList).equals(";"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept ; on C");
			index++;
		}
		else if (getToken(tokensList).equals("["))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept [ on C");
			index++;
			if (getToken(tokensList).equals("NUM"))
			{
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
			if (getToken(tokensList).equals("]"))
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
			if (getToken(tokensList).equals(";"))
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
				//System.exit(0);
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
		typeSpec();
		if (getToken(tokensList).equals("ID"))
		{
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
		if (getToken(tokensList).equals("("))
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
		params();
		if (getToken(tokensList).equals(")"))
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
	}
	public void params()
	{
		if (getToken(tokensList).equals("void"))
		{
			index++;
			if (getToken(tokensList).equals("ID"))
			{
				index--;
				paramList();
			}
			else
			{
				index--;
				//System.out.printf("%d ",index);
				//System.out.println("Accept void on params");
				index++;
			}	
		}
		else if (getToken(tokensList).equals("int"))
		{
			paramList();
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
		if (getToken(tokensList)!= null && getToken(tokensList)!= "$")
		D();
	}
	public void D()
	{
		if (getToken(tokensList).equals(","))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept , on D");
			index++;
			param();  
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
		E();
	}
	public void E()
	{
		if (getToken(tokensList).equals("ID"))
		{
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
		if (getToken(tokensList).equals("["))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept [ on F");
			index++;
			if (getToken(tokensList).equals("]"))
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
		if (getToken(tokensList).equals("{"))
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
		if (getToken(tokensList).equals("}"))
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
		if (getToken(tokensList).equals("int") || getToken(tokensList).equals("void"))
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
		if (getToken(tokensList).equals(";") || getToken(tokensList).equals("ID") || getToken(tokensList).equals("(") || getToken(tokensList).equals("NUM") || getToken(tokensList).equals("{") || getToken(tokensList).equals("if") || getToken(tokensList).equals("while") || getToken(tokensList).equals("return"))
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
		if (getToken(tokensList).equals(";") || getToken(tokensList).equals("ID") || getToken(tokensList).equals("(") || getToken(tokensList).equals("NUM"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept go to expressionStmt() on statement");
			expressionStmt();
		}
		else if (getToken(tokensList).equals("{"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept go to compoundStmt() on statement");
			compoundStmt();
		}
		else if (getToken(tokensList).equals("if"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept go to selectionStmt() on statement");
			selectionStmt();
		}
		else if (getToken(tokensList).equals("while"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept go to iterationStmt() on statement");
			iterationStmt();
		}
		else if (getToken(tokensList).equals("return"))
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
		if (getToken(tokensList).equals(";"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept ; on expressionStmt");
			index++;
		}
		else
		{
			expression();
			if (getToken(tokensList).equals(";"))
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
		if (getToken(tokensList).equals("if"))
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
		if (getToken(tokensList).equals("("))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept ( on selectionStmt");
			index++;
			expression();
			if (getToken(tokensList).equals(")"))
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
		if (getToken(tokensList).equals("else"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept else on I");
			index++;
			statement();
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept @ on I");
		}
	}
	
	public void iterationStmt()
	{
		if (getToken(tokensList).equals("while"))
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
		if (getToken(tokensList).equals("("))
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
		expression();
		if (getToken(tokensList).equals(")"))
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
		statement();
	}
	public void returnStmt()
	{
		if (getToken(tokensList).equals("return"))
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
	}
	public void J()
	{
		if (getToken(tokensList).equals(";"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept ; on J");
			index++;
		}
		else
		{
			expression();
			if (getToken(tokensList).equals(";"))
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
		}		
	}
	public void expression()
	{
		if (getToken(tokensList).equals("ID"))
		{
			index++;
			if (getToken(tokensList).equals("="))
			{
				index--;
				var();
				if (getToken(tokensList).equals("="))
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
			}
			else if (getToken(tokensList).equals("["))
			{
				index++;
				index++;
				index++;
				if (getToken(tokensList).equals("="))
				{
					index--;
					index--;
					index--;
					index--;
					var();
					if (getToken(tokensList).equals("="))
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
				}
				else
				{
					index--;
					index--;
					index--;
					index--;
					simpleExpression();
				}
			}
			else
			{
				index--;;
				simpleExpression();
			}
		}	
		else if (getToken(tokensList).equals("(") || getToken(tokensList).equals("NUM"))
		{
			simpleExpression();
		}
	}
	public void var()
	{
		if (getToken(tokensList).equals("ID"))
		{
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
		if (getToken(tokensList).equals("["))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept [ on K");
			index++;
			expression();
			if (getToken(tokensList).equals("]"))
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
		L();
	}
	public void L()
	{
		if (getToken(tokensList).equals("<=") || getToken(tokensList).equals("<") || getToken(tokensList).equals(">") || getToken(tokensList).equals(">=") || getToken(tokensList).equals("==") || getToken(tokensList).equals("!="))
		{
		relop();
		additiveExpression();
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept @ on L");
		}
	}
	public void relop()
	{
		if (getToken(tokensList).equals("<=") || getToken(tokensList).equals("<") || getToken(tokensList).equals(">") || getToken(tokensList).equals(">=") || getToken(tokensList).equals("==") || getToken(tokensList).equals("!="))
		{
			//System.out.printf("%d ",index);
			//System.out.printf("Accept %s on relop", getToken(tokensList));
			index++;
		}
	}
	public void additiveExpression()
	{
		term();
		M();
	}
	public void M()
	{
		if (getToken(tokensList).equals("+") || getToken(tokensList).equals("-"))
		{
		addop(); 
		term(); 
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
		if (getToken(tokensList).equals("+"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept + on addop");
			index ++;
		}
		else if (getToken(tokensList).equals("-"))
		{
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
		N();
	}
	public void N()
	{
		if (getToken(tokensList).equals("*") || getToken(tokensList).equals("/"))
		{
		mulop(); factor(); N();
		}
		else
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept @ on N");
		}
	}
	public void mulop()
	{
		if (getToken(tokensList).equals("*"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept * on mulop");
			index ++;
		}
		else if (getToken(tokensList).equals("/"))
		{
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
		if (getToken(tokensList).equals("("))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept ( on factor");
			index ++;
			expression();
			if (getToken(tokensList).equals(")"))
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
		else if (getToken(tokensList).equals("NUM"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept NUM on factor");
			index++;
		}
		else if (getToken(tokensList).equals("ID"))
		{
			index++;
			if (getToken(tokensList).equals("("))
			{
				index--;
				call();
			}
			else 
			{
				index --;
				var();
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
		if (getToken(tokensList).equals("ID"))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept ID on call");
			index++;
			if (getToken(tokensList).equals("("))
			{
				//System.out.printf("%d ",index);
				//System.out.println("Accept ( on call");
				index++;
				args();
				if (getToken(tokensList).equals(")"))
				{
					//System.out.printf("%d ",index);
					//System.out.println("Accept ) on call");
					index++;
					args();
				}
				else
				{
					//System.out.printf("%d ",index);
					//System.out.println(getToken(tokensList));
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
	}
	public void args()
	{
		//if (getToken(tokensList).equals("ID") || getToken(tokensList).equals("("))
		if (getToken(tokensList).equals(")"))
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
		O();
	}
	public void O()
	{
		if (getToken(tokensList).equals(","))
		{
			//System.out.printf("%d ",index);
			//System.out.println("Accept , on O");
			index++;
			expression();
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
		String bufferKW = "";
		while ((line = bufr.readLine()) != null)
		{			
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
							buffer = "INPUT";
							break;
						}
						else if (buffer.equals("ID:"))
						{
							buffer = "ID";
							break;
						}
						else if (buffer.equals("NUM:"))
						{
							buffer = "NUM";
							break;
						}
						else if (buffer.equals("KW:"))
						{
							++i;
							current = line.charAt(i);
							while (Character.isLetter(current) && i < line.length())
							{
								current = line.charAt(i);
								bufferKW += Character.toString(current);
								i++;
							}
							buffer = bufferKW;
							break;
						 }
					 }
					else if (isSpecialSymbol(current))
					{
						while(isSpecialSymbol(current) && i < line.length())
						{
							current = line.charAt(i);
							buffer += Character.toString(current);
							i++;
						}
					}
				}
				if (valid && buffer != "")
				{
					tokensList.add(buffer);
				}
				buffer = "";
				bufferKW = "";
			}
		tokensList.add("$");
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
		File smplInput = new File ("C:\\Users\\Angel Hierrezuelo\\Desktop\\Spring 2020\\Compilers (COP 4620)\\Projects\\Project2\\terminal\\testOutput1.txt");				
		BufferedReader bufr = new BufferedReader(new FileReader(smplInput));	
		try {
		prj2 classins = new prj2();
		classins.fillup(bufr);
		classins.program();
		}catch(IndexOutOfBoundsException e)
		{
			//System.out.println("IndexOutOfBoundsException"); 
		}
		}
	}




package Project1;

import java.io.*;
public class prj1 {
	
	static boolean isSpecialSymbol (char c)
		    {
		        int i;
		        char symbols[]= {'+','-','*','/','<','>','=',';',',','(',')','[',']','{','}'};
		        for(i=0;i<symbols.length;i++)
		        {
		            if (Character.compare(c, symbols[i]) == 0)
		            return true;
		        }
		        return false;
		    }
	static boolean isKeyword (String st)
    {
        int i;
        String kw[]= {"else","if","int","return","void","while"};
        for(i=0;i<kw.length;i++)
        {
            if (st.equals(kw[i]))
            return true;
        }
        return false;
    }

	public static void main(String[] args) throws Exception 
	{		
		//File smplInput = new File (args[0]);		
		File smplInput = new File ("C:\\Users\\Angel Hierrezuelo\\Desktop\\Spring 2020\\Compilers (COP 4620)\\Projects\\Project5\\input2.txt");	
		BufferedReader bufr = new BufferedReader(new FileReader(smplInput));		
		String line;
		char current;		
		String buffer = "";
		int lnempty;
		int bcomment = 0;
		int last;
		
		while ((line = bufr.readLine()) != null)
		{			
			int i = 0;
			int loop = 0;
			lnempty = 0;	
				
			for(i=0;i<line.length();i++)
			{				
				if (line.charAt(i) == ' ')
					{continue;}
				else
				{
					lnempty = 1;
					break;
				}
			}					
		    
		    if(lnempty == 1)
		    {
		    System.out.printf("INPUT: %s\n", line);
		    }
			
			for(i=0;i<line.length();i++)
			{				
				last = 0;
				current = line.charAt(i);
				//checks if character is a letter----
				if (Character.isLetter(current) && bcomment == 0)					
				{					
					buffer = Character.toString(current);
					if ((i+1) < line.length())
					{
						i++;
						current = line.charAt(i);									
						while ((Character.isLetter(current) || Character.isDigit(current)) && (last != 1))
						{
							buffer += current;
							if ((i+1) >= line.length())
							{
								last = 1;
							}
							else
							{
								i++;
								current = line.charAt(i);
							}
						}
						if (last != 1)
						{
							i--;
							current = line.charAt(i);	
						}
					}
					if (isKeyword(buffer))
					{				
						System.out.printf("KW: %s\n", buffer);						
					}
					else
					{
						System.out.printf("ID: %s\n", buffer);
					}
					
					buffer = null;
				}
				//checks if character is a digit--------
				else if (Character.isDigit(current) && bcomment == 0)					
				{					
					buffer = Character.toString(current);
					i++;
					current = line.charAt(i);				
					while (Character.isDigit(current))
					{
						buffer += current;
						i++;
						current = line.charAt(i);
					}
					System.out.printf("NUM: %s\n", buffer);					
					i--;	
					current = line.charAt(i);
					buffer = null;
				} 
				//checks if character is a special symbol-
				else if (isSpecialSymbol(current))
				{
					buffer = Character.toString(current);
					if ((i+1) < line.length())
					{
						if(isSpecialSymbol(line.charAt(i+1)))
						{
							buffer += line.charAt(i+1);
													
						    if(buffer.equals("/*"))
							{
								bcomment = 1;	
								i++;
								current = line.charAt(i);
								buffer = null;
							}
							else if(buffer.equals("*/"))
							{
								if (bcomment == 0)
								{
									System.out.println(current);
								}
								else
								{
								bcomment = 0;
								i++;
								current = line.charAt(i);
								buffer = null;
								}
							}
							else if(buffer.equals("//"))
							{
								break;
							}
							else if((buffer.equals("<=") || buffer.equals(">=") || buffer.equals("==") || buffer.equals("!=")) && bcomment == 0)
							{
								System.out.printf("%s\n", buffer);
								i++;
								current = line.charAt(i);
								buffer = null;
							}						
							else if (bcomment == 0)
							{									
							System.out.println(current);
							}
							else
							{
								continue;
							}
						}	
						else if ( bcomment == 0)
						{									
						System.out.println(current);
						}		
						else 
						{
							continue;
						}
					}
					else if (bcomment == 0)
					{
						System.out.println(current);
					}
					else
					{
						continue;
					}
				}				
				else if (current == '!' && bcomment == 0)
				{
					buffer = Character.toString(current);
					if ((i+1) < line.length())
						{
							i++;
							current = line.charAt(i);
							if(current == '=')
							{
								buffer += current;
								System.out.printf("%s\n", buffer);;
								buffer = null;
							}
							else if (Character.isDigit(current) || Character.isLetter(current))
							{
								buffer += current;
								if ((i+1) < line.length())
								{
									i++;
									current = line.charAt(i);	
									while (Character.isDigit(current) || Character.isLetter(current))
									{
										buffer += current;
										i++;
										current = line.charAt(i);
										loop = 1;								
									}  	
									if (loop == 1)
									{
										System.out.printf("Error: %s\n", buffer);
										i--;	
										current = line.charAt(i);
										buffer = null;
									}
									else
									{
										System.out.printf("Error: %s\n", buffer);
									}
								}
								System.out.printf("Error: %s\n", buffer);
								buffer = null;
							}
							else
							{
								System.out.printf("Error:: %s\n", buffer);
							}
						}
					else
					{
						System.out.printf("Error: %s\n", buffer);
					}
				}
				else if (current != ' ' && bcomment == 0 && current != '\t')
				{
					buffer = Character.toString(current);
					if ((i+1) < line.length())
					{
					i++;
					current = line.charAt(i);
					
				
					while (Character.isDigit(current) || Character.isLetter(current))
					{
						buffer += current;
						i++;
						current = line.charAt(i);
					}				
					i--;	
					current = line.charAt(i);
					}
						System.out.printf("Error: %s\n", buffer);
					buffer = null;
				}				
			buffer = null;
			}
			if (lnempty == 1)
			{
			System.out.println("");
			}
		}
		bufr.close();
	}
}

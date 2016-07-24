import java.util.Scanner; 

public class RNC 
{
 
	private static String input;

	//  m=1000, d=500, c=100, l=50, x=10, v=5, i=1

	final static String[] base =  {"m",  "cm", "d", "cd", "c", "xc", "l", "xl", "x", "ix", "v", "iv", "i"}; 
	final static int[] baseVals = {1000, 900,  500, 400,  100, 90,   50,  40,   10,  9,    5,   4,    1}; 

	public static void main(String[] args)
 
	{ 
		Scanner sc = new Scanner(System.in); 
		String method = "y";

		while(method.compareTo("y") == 0) 
		{ 
			System.out.printf("1. Arabic to Roman \n" + "2. Roman to Arabic \n"); 
			while(method.compareTo("1") != 0 && method.compareTo("2") != 0)  
			{ 
				System.out.printf("Please enter your conversion choice (\"1\" or \"2\"): ");
				method = sc.nextLine();
			} 

			System.out.printf("Enter numerals: ");
			input = sc.nextLine();

			switch(method) 
			{ 
				case("2"): 
				{

					romanToArabic(); 
					break;
				} 

				case("1"): 

				{ 
					int inpt; 
					try 
					{ 
						inpt = Integer.parseInt(input); 
						if(inpt < 0 || inpt > 3999) 
						{ 
							System.out.printf("Only Arabic numerals greater " + 
								"zero and less than 3,999 can be converted. Sorry. \n");
						} 
						else 
						{ 
							System.out.printf("%s in Roman numerals is %s \n", input, 
								arabicToRoman(inpt)); 
						} 
					} 
					catch(Exception e) 
					{ 
						System.out.printf("Something messed up! \n"); 
						break; 
					} 

					break; 

				} 

				default: 

					System.out.printf("Something messed up! \n"); 

			} 

			while(method.compareTo("y") != 0 && method.compareTo("n") != 0) 
			{ 
				System.out.printf("More conversions (\"y\" or \"n\")? "); 
				method = sc.nextLine(); 
			} 
		} 

	} 


	// converts properly formatted Roman numerals to Arabic numerals, according 
	// to the rules given for Roman numerals on 
	// http://www.free-online-calculator-use.com/roman-numeral-converter.html 


	// the Roman numerals are read from left to right and must be in decreasing 

	// order. subtraction is done before addition. 

	private static void romanToArabic() 

 	{ 

		// arrayIndex indicates where the last Roman numeral was found in the 
		// "base" array 

		int timesRepeated = 0, arrayIndex = 0, inputIndex = 0, sum = 0; 


		while(inputIndex < input.length()) 

		{ 

			String numeral = input.substring(inputIndex,inputIndex+1); 

			int index = getIndex(numeral); 



			if(index == -1) 

			{ 

				System.out.printf("Invalid numeral at position %d \n", inputIndex+1); 

				return; 

			} 

			else 

			{ 

				// consider what might happen if the numeral is a power-of-10 
				if(numeral.compareTo("m") == 0 || numeral.compareTo("c") == 0 || 
					numeral.compareTo("x") == 0 || numeral.compareTo("i") == 0 ) 
				{ 
					// check for a subtractive value 
					if(inputIndex < input.length()-1 ? 
						input.substring(inputIndex,inputIndex+2).compareTo("cm") 
							== 0 || 
						input.substring(inputIndex,inputIndex+2).compareTo("cd") 
							== 0 ||
 
						input.substring(inputIndex,inputIndex+2).compareTo("xc") 
							== 0 ||
 
						input.substring(inputIndex,inputIndex+2).compareTo("xl") 
							== 0 ||
 
						input.substring(inputIndex,inputIndex+2).compareTo("ix") 
							== 0 ||
 
						input.substring(inputIndex,inputIndex+2).compareTo("iv") 
							== 0 :
 
						false)
 
					{
 
						numeral = input.substring(inputIndex,inputIndex+2);
 
						++inputIndex;
 
					}
 

					// in case this has changed
 
					index = getIndex(numeral);
 

					// check for non-decreasing ordering of Roman numerals
 
					if(index < arrayIndex)
 
					{
 
						System.out.printf("The Roman numerals are not decreasing" 
						+ " in value! \n");
 
						return;
 
					}
 

					// check for powers of 10 being repeated more than 3 times,
 
					// or non-powers being repeated (for example, 90 or 40)
 
					if(index == arrayIndex)
 
					{
 
						if(numeral.compareTo("m") == 0 || numeral.compareTo("c") 
							== 0 || 
							numeral.compareTo("x") == 0 || numeral.compareTo("i") 
							== 0)
 
						{
 
							if(++timesRepeated == 4)
 
							{
 
								System.out.printf("A Roman numeral which is a " +
 
									"power of ten has been repeated more than 3 " + 
									"times! \n");
 
								return;
 
							}
 
						}
 
						else 
						{
 
							System.out.printf("A Roman numeral which is not a " + 
								"power of ten has been repeated! \n");
 
							return;
 
						} 
					}
 
					// index > arrayIndex 
					else 
					{
 
						timesRepeated = 1; 
					} 

					arrayIndex = index;
 
				}
 
				// the numeral is not a power of 10
 
				else
 
				{
 
					// check for non-decreasing ordering of Roman numerals
 
					if(index < arrayIndex)
 
					{ 
						System.out.printf("The Roman numerals are not decreasing" 
							+ "in value! \n"); 
						return; 
					} 

					// check for non-powers being repeated 
					if(index == arrayIndex) 
					{ 
						System.out.printf("A Roman numeral which is not a power " + 
							"of ten has been repeated! \n"); 
						return; 
					} 

					arrayIndex = index; 
					timesRepeated = 0; 
				} 
			} 

			sum += baseVals[index]; 
			++inputIndex; 
		} 

		System.out.printf("%s in Arabic numerals is %d", input.toUpperCase(), sum); 

		String standardNumerals = arabicToRoman(sum); 
		if(input.toUpperCase().compareTo(standardNumerals) != 0) 
		{ 
			System.out.printf(". But the standard Roman numerals for %d is %s. \n", 
				sum, standardNumerals); 
		} 
		else 
		{ 
			System.out.printf("\n"); 
		} 
	} 

	//  converts numbers up to 3999 to Roman numerals 
	private static String arabicToRoman(int inpt) 
	{ 
		StringBuilder sb = new StringBuilder(); 
		int i = 0, inp = inpt; 

		while( inp > 0 ) 
		{ 
			if(inp >= baseVals[i]) 
			{ 
				sb.append(base[i]); 
				inp -= baseVals[i]; 
			} 
			else 
			{ 
				++i; 
			} 
		} 

		return sb.toString().toUpperCase(); 
	} 

	private static int getIndex(String numeral) 
	{ 
		for(int i=0; i<13; ++i) 
		{ 
			if(numeral.compareTo(base[i]) == 0) 
				return i; 
		} 

		return -1; 
	}

}
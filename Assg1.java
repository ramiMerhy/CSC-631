import java.util.ArrayList;

public class Assg1 {
	// data path
	private static ArrayList<Integer> ImmSelect = new ArrayList<Integer>();
	private static ArrayList<Integer> PCArray = new ArrayList<Integer>();
	private static ArrayList<Integer> ALU = new ArrayList<Integer>();
	private static ArrayList<Integer> LoadA = new ArrayList<Integer>();
	private static ArrayList<Integer> LoadB = new ArrayList<Integer>();
	private static ArrayList<Integer> ReadRegister = new ArrayList<Integer>();
	private static ArrayList<Integer> WriteRegister = new ArrayList<Integer>();
	private static ArrayList<Integer> ReadMemory = new ArrayList<Integer>();
	private static ArrayList<Integer> WriteMemory = new ArrayList<Integer>();
	//
	private static ArrayList<Integer> nextuInstruction = new ArrayList<Integer>();
	//
	private static ArrayList<Integer> C_DataPath = new ArrayList<Integer>();

	// immediate
	private static int[] target;
	private static long ImmediateVal = 0;
	private static long A = 0;
	private static long B = 0;

	// register
	private static int[][] Registers;
	private static int PC = 0;
	private static int effectiveAddress = 0;

	// memory
	private static int[][] Memory;

	// Micro Instruction
	private static ArrayList<Integer> uInstruction;

	//
	private static int[] Instruction = new int[31];

	public static void main(String[] arg) {
		// add the arrays to the data path
		for (int i = 0; i < ImmSelect.size(); i++) {
			C_DataPath.add(i);
		}
		for (int i = 0; i < PCArray.size(); i++) {
			C_DataPath.add(i);
		}
		for (int i = 0; i < ALU.size(); i++) {
			C_DataPath.add(i);
		}
		for (int i = 0; i < LoadA.size(); i++) {
			C_DataPath.add(i);
		}
		for (int i = 0; i < LoadB.size(); i++) {
			C_DataPath.add(i);
		}
		for (int i = 0; i < ReadRegister.size(); i++) {
			C_DataPath.add(i);
		}
		for (int i = 0; i < WriteRegister.size(); i++) {
			C_DataPath.add(i);
		}
		for (int i = 0; i < ReadMemory.size(); i++) {
			C_DataPath.add(i);
		}
		for (int i = 0; i < WriteMemory.size(); i++) {
			C_DataPath.add(i);
		}

		// Create 32 Registers with empty
		Registers = new int[32][6];

		// Create 32 Registers with empty
		Memory = new int[32][6];
		//
		Registers[0] = intToArray(Integer.parseInt(decimalToBinary(3)));
		Registers[1] = intToArray(Integer.parseInt(decimalToBinary(1)));

		String InstructionStr = "00000000000" + decimalToBinary(2) + decimalToBinary(1) + decimalToBinary(0) + "100000";
		Instruction = stringToArray(InstructionStr);
		//
		dispatch();

		System.out.println();
		// Assume reference for the base address
		System.out.println("before memory");
		for (int i = 0; i < 6; i++) {
			System.out.print(Memory[0][i]);
			System.out.print("\t");
		}
		System.out.println();
		for (int i = 0; i < 6; i++) {
			System.out.print(Memory[1][i]);
			System.out.print("\t");
		}
		System.out.println();
		for (int i = 0; i < 6; i++) {
			System.out.print(Memory[2][i]);
			System.out.print("\t");
		}
		System.out.println();
		for (int i = 0; i < 6; i++) {
			System.out.print(Memory[3][i]);
			System.out.print("\t");
		}
		System.out.println();
		//
		InstructionStr = decimalToBinary(2) + decimalToBinary(0) + decimalToBinary(15) + "011001";
		Instruction = stringToArray(InstructionStr);
		//
		dispatch();

		System.out.println("after");
		for (int i = 0; i < 6; i++) {
			System.out.print(Memory[0][i]);
			System.out.print("\t");
		}
		System.out.println();
		for (int i = 0; i < 6; i++) {
			System.out.print(Memory[1][i]);
			System.out.print("\t");
		}
		System.out.println();
		for (int i = 0; i < 6; i++) {
			System.out.print(Memory[2][i]);
			System.out.print("\t");
		}
		System.out.println();
		for (int i = 0; i < 6; i++) {
			System.out.print(Memory[3][i]);
			System.out.print("\t");
		}
	}

	/*
	 * immediate functions
	 */
	public static void resetImmediates() {
		System.out.println("immediate Resest");
		ImmediateVal = 0;
		A = 0;
		B = 0;
	}

	public static void loadIntoA(ArrayList<Integer> param) {
		if (param != null && param.size() > 0 && param.get(0) == 1) {
			A = loadImmediateValue();
		}
	}

	public static void loadIntoB(ArrayList<Integer> param) {
		if (param != null && param.size() > 0 && param.get(0) == 1) {
			B = loadImmediateValue();
		}
	}

	public static long loadFromA() {
		return A;
	}

	public static long loadFromB() {
		return B;
	}

	public static void setImmediateValue(long param) {
		ImmediateVal = param;
	}

	public static long loadImmediateValue() {
		return ImmediateVal;
	}

	/*
	 * Register
	 */
	// Assume the format is the same as ReadRegister array
	public static void readFromRegister(ArrayList<Integer> readArray) {
		// 1 represents to activate this component
		if (readArray != null && readArray.size() > 0 && readArray.get(0) == 1) {
			for (int i = 2; i < readArray.size(); i++) {
				effectiveAddress = binaryToDecimal(readArray.get(i));
				setImmediateValue(Registers[effectiveAddress][i]);
			}
		}
	}

	// Assume the format is the same as WriteRegister array
	public static void writeToRegister(ArrayList<Integer> writeArray) {
		// 1 represents to activate this component
		if (writeArray != null && writeArray.size() > 0 && writeArray.get(0) == 1) {
			for (int i = 2; i < writeArray.size(); i++) {
				effectiveAddress = binaryToDecimal(writeArray.get(i));
				// TODO: Enforce 6 bits
				Registers[effectiveAddress] = intToArray(loadImmediateValue());
			}
		}
	}

	/*
	 * MicroControler
	 */
	public static ArrayList<Integer> createMicroInstruction(int[] target, String code, String uCounter) {
		ArrayList<Integer> globalReturnValue = new ArrayList<Integer>();
		if (code.equals("100000")) {
			if (uCounter.equals("00")) {
				int returnValueFrontArray[] = { 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1 };
				int returnValueEndArray[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };

				for (int i = 0; i < returnValueFrontArray.length; i++) {
					globalReturnValue.add(i);
				}
				for (int i = 0; i < target.length; i++) {
					globalReturnValue.add(i);
				}
				for (int i = 0; i < returnValueEndArray.length; i++) {
					globalReturnValue.add(i);
				}
			}
			if (uCounter.equals("01")) {
				int returnValueFrontArray[] = { 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1 };
				int returnValueEndArray[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 };

				for (int i = 0; i < returnValueFrontArray.length; i++) {
					globalReturnValue.add(i);
				}
				for (int i = 0; i < target.length; i++) {
					globalReturnValue.add(i);
				}
				for (int i = 0; i < returnValueEndArray.length; i++) {
					globalReturnValue.add(i);
				}
			}
			if (uCounter.equals("10")) {
				int returnValueFrontArray[] = { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
				int returnValueEndArray[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

				for (int i = 0; i < returnValueFrontArray.length; i++) {
					globalReturnValue.add(i);
				}
				for (int i = 0; i < target.length; i++) {
					globalReturnValue.add(i);
				}
				for (int i = 0; i < returnValueEndArray.length; i++) {
					globalReturnValue.add(i);
				}
			}
		}

		if (code.equals("ld")) {
			if (uCounter.equals("00")) {
				int returnValueFrontArray[] = { 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1 };
				int returnValueEndArray[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };

				for (int i = 0; i < returnValueFrontArray.length; i++) {
					globalReturnValue.add(i);
				}
				for (int i = 0; i < target.length; i++) {
					globalReturnValue.add(i);
				}
				for (int i = 0; i < returnValueEndArray.length; i++) {
					globalReturnValue.add(i);
				}
			}
			if (uCounter.equals("01")) {
				int returnValueFrontArray[] = { 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 };
				int returnValueEndArray[] = { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 };

				for (int i = 0; i < returnValueFrontArray.length; i++) {
					globalReturnValue.add(i);
				}
				for (int i = 0; i < target.length; i++) {
					globalReturnValue.add(i);
				}
				for (int i = 0; i < returnValueEndArray.length; i++) {
					globalReturnValue.add(i);
				}
			}
		}
		if (code.equals("011001")) {
			if (uCounter.equals("00")) {
				int returnValueFrontArray[] = { 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1 };
				int returnValueEndArray[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };

				for (int i = 0; i < returnValueFrontArray.length; i++) {
					globalReturnValue.add(i);
				}
				for (int i = 0; i < target.length; i++) {
					globalReturnValue.add(i);
				}
				for (int i = 0; i < returnValueEndArray.length; i++) {
					globalReturnValue.add(i);
				}
			}
			if (uCounter.equals("01")) {
				int[] bitArray = intToArray(loadImmediateValue());
				int returnValueFrontArray[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
				int returnValueMidArray[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
				int returnValueEndArray[] = { 0, 0 };

				for (int i = 0; i < returnValueFrontArray.length; i++) {
					globalReturnValue.add(i);
				}
				for (int i = 0; i < target.length; i++) {
					globalReturnValue.add(i);
				}
				for (int i = 0; i < returnValueMidArray.length; i++) {
					globalReturnValue.add(i);
				}
				for (int i = 0; i < bitArray.length; i++) {
					globalReturnValue.add(i);
				}
				for (int i = 0; i < returnValueEndArray.length; i++) {
					globalReturnValue.add(i);
				}
			}
		}
		return globalReturnValue;

	}

	public static ArrayList<Integer> getuInstruction(String code, String uCounter, int[] target) {
		uCounter = "00";
		// TODO: Move target to Immediates
		return createMicroInstruction(target, code, uCounter);
	}

	/*
	 * Memory
	 */
	// Assume the format is the same as ReadRegister array
	public static void readFromMemory(ArrayList<Integer> readArray) {
		// 1 represents to activate this component
		if (readArray != null && readArray.size() > 0 && readArray.get(0) == 1) {
			for (int i = 2; i < readArray.size(); i++) {
				effectiveAddress = binaryToDecimal(readArray.get(i));
				ImmediateVal = Memory[effectiveAddress][i];
			}
		}
	}

	// Assume the format is the same as WriteRegister array
	public static void writeToMemory(ArrayList<Integer> writeArray) {
		// 1 represents to activate this component
		if (writeArray != null && writeArray.size() > 0 && writeArray.get(0) == 1) {
			for (int i = 2; i < writeArray.size(); i++) {
				System.out.println("Writing to " + writeArray.get(i));
				effectiveAddress = binaryToDecimal(writeArray.get(i));
				Memory[effectiveAddress] = intToArray(loadImmediateValue());
			}
		}
	}

	/*
	 * uInstruction
	 */

	// Provides access to a uInstruction
	public static ArrayList<Integer> MicroInstruction(String opCode, int[] target, String counter) {
		if (counter.isEmpty())
			counter = "00";
		uInstruction = getuInstruction(opCode, counter, target);
		//
		return uInstruction;
	}

	public static ArrayList<Integer> getImmSelectInstruction() {
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(uInstruction.get(0));
		//
		return array;
	}

	public static ArrayList<Integer> getPCInstruction() {
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(uInstruction.get(1));
		//
		return array;
	}

	public static ArrayList<Integer> getALUInstruction() {
		ArrayList<Integer> array = new ArrayList<Integer>();

		for (int i = 2; i < 9; i++) {
			array.add(uInstruction.get(i));
		}
		//
		return array;
	}

	public static ArrayList<Integer> getLoadAInstruction() {
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(uInstruction.get(9));
		//
		return array;
	}

	public static ArrayList<Integer> getLoadBInstruction() {
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(uInstruction.get(10));
		//
		return array;
	}

	public static ArrayList<Integer> getReadRegister() {
		ArrayList<Integer> array = new ArrayList<Integer>();

		for (int i = 11; i < 17; i++) {
			array.add(uInstruction.get(i));
		}
		//
		return array;
	}

	public static ArrayList<Integer> getWriteRegister() {
		ArrayList<Integer> array = new ArrayList<Integer>();

		for (int i = 17; i < 23; i++) {
			array.add(uInstruction.get(i));
		}
		//
		return array;
	}

	public static ArrayList<Integer> getReadMemory() {
		ArrayList<Integer> array = new ArrayList<Integer>();

		for (int i = 23; i < 29; i++) {
			array.add(uInstruction.get(i));
		}
		//
		return array;
	}

	public static ArrayList<Integer> getWriteMemory() {
		ArrayList<Integer> array = new ArrayList<Integer>();

		for (int i = 29; i < 35; i++) {
			array.add(uInstruction.get(i));
		}
		//
		return array;
	}

	public static ArrayList<Integer> getNextuInstruction() {
		ArrayList<Integer> array = new ArrayList<Integer>();

		for (int i = 35; i < 37; i++) {
			array.add(uInstruction.get(i));
		}
		//
		return array;
	}

	/*
	 * ALU
	 */
	public static void performALU(ArrayList<Integer> aluArray) {
		String operation = "";
		int result = 0;
		ArrayList<Integer> arr = new ArrayList<Integer>();

		// enable
		if (aluArray != null && aluArray.size() > 0 && aluArray.get(0) == 1) {
			result = 0;
		}

		// Change to String to be translated
		for (int i = 1; i < aluArray.size(); i++) {
			arr.add(aluArray.get(i));
		}
		operation = arrayToString(arr);
		// "ADD": "100000"
		// "SUB": "100001"
		// "CMPEQ": "100100"
		// "AND": "101000"
		// "OR": "101001"
		// "XOR": "101010"
		// "SHL": "101100"
		// "SHR": "101101"
		// "SRA": "101110"
		// "ADDC": "110000"
		// "SUBC": "110001"
		// "SHLC": "111100"
		// "SHRC": "111101"
		// "SRAC": "111110"

		if (operation.equals("100000")) {
			result = binaryToDecimal(loadFromA()) + binaryToDecimal(loadFromB());
		}

		// print("Result of ALU Operation", result)
		setImmediateValue(Integer.parseInt(decimalToBinary(result)));
	}

	/*
	 * Instruction
	 */
	// Return Type
	public static int getType() {
		int type = 0;
		//
		if (getUnused().size() == 11) {
			for (int i = 0; i < getUnused().size(); i++) {
				if (getUnused().get(i) != 0) {
					type = 1;
					break;
				} else
					type = 0;
			}
		} else
			type = 1;
		//
		return type;
	}

	public static ArrayList<Integer> getOpCode() {
		ArrayList<Integer> array = new ArrayList<Integer>();

		for (int i = 26; i < 32; i++) {
			if (i > Instruction.length - 1)
				break;
			array.add(Instruction[i]);
		}
		//
		return array;
	}

	public static ArrayList<Integer> getRegA() {
		ArrayList<Integer> array = new ArrayList<Integer>();

		for (int i = 21; i < 26; i++) {
			if (i > Instruction.length - 1)
				break;
			array.add(Instruction[i]);
		}
		//
		return array;
	}

	public static ArrayList<Integer> getRegB() {
		ArrayList<Integer> array = new ArrayList<Integer>();

		for (int i = 16; i < 21; i++) {
			if (i > Instruction.length - 1)
				break;
			array.add(Instruction[i]);
		}
		//
		return array;
	}

	public static ArrayList<Integer> getRegC() {
		ArrayList<Integer> array = new ArrayList<Integer>();

		for (int i = 11; i < 16; i++) {
			if (i > Instruction.length - 1)
				break;
			array.add(Instruction[i]);
		}
		//
		return array;
	}

	public static ArrayList<Integer> getLiteral() {
		ArrayList<Integer> array = new ArrayList<Integer>();

		for (int i = 0; i < 16; i++) {
			if (i > Instruction.length - 1)
				break;
			array.add(Instruction[i]);
		}
		//
		return array;
	}

	public static ArrayList<Integer> getUnused() {
		ArrayList<Integer> array = new ArrayList<Integer>();

		for (int i = 0; i < 11; i++) {
			if (i > Instruction.length - 1)
				break;
			array.add(Instruction[i]);
		}
		//
		return array;
	}

	/*
	 * Dispatcher
	 */
	public static void dispatch() {
		String opCode = "";
		opCode = arrayToString(getOpCode());

		ArrayList<Integer> literal = new ArrayList<Integer>();
		ArrayList<Integer> targetRegistries = new ArrayList<Integer>();
		targetRegistries.addAll(getRegA());
		targetRegistries.addAll(getRegB());

		if (getType() == 0) {
			// First type with Reg1, Reg2, and Reg3
			targetRegistries.addAll(getRegC());
		} else {
			// Second type with Reg1, Reg2, and Literal
			literal = getLiteral();
		}
		System.out.println("Target REG " + targetRegistries);
		if (targetRegistries != null && targetRegistries.size() > 0)
			target = intToArray(targetRegistries.get(0));
		uInstruction = MicroInstruction(opCode, target, "");
		execute(uInstruction, opCode, targetRegistries, literal);
	}

	public static void execute(ArrayList<Integer> uI, String originalOpCode, ArrayList<Integer> targetRegistries,
			ArrayList<Integer> literal) {
		System.out.println("New Execution");
		// print("PArameters", uI, originalOpCode, targetRegistries, literal)
		// [1] [1] [7] [1] [1] [6] [6] [6] [6]
		// ImmSelect + PC + ALU + LoadA + LoadB + ReadRegister + WriteRegister +
		// ReadMemory + WriteMemory
		if (uInstruction != null && uInstruction.size() > 0) {
			ImmSelect = getImmSelectInstruction();
			PCArray = getPCInstruction();
			ALU = getALUInstruction();
			LoadA = getLoadAInstruction();
			LoadB = getLoadBInstruction();
			ReadRegister = getReadRegister();
			WriteRegister = getWriteRegister();
			ReadMemory = getReadMemory();
			WriteMemory = getWriteMemory();
			nextuInstruction = getNextuInstruction();
			incrementPC(PCArray);
		}

		// print(ImmSelect, PC, ALU, LoadA, LoadB, ReadRegister, WriteRegister,
		// readMemory, writeMemory, nextuInstruction)

		readFromRegister(ReadRegister);
		readFromMemory(ReadMemory);
		loadIntoA(LoadA);

		// Change immediate value to literal to store it in B
		if (literal != null && literal.size() > 0) {
			System.out.println("Literal set as immediate");
			setImmediateValue(Long.parseLong(arrayToString(literal)));
		}
		loadIntoB(LoadB);
		performALU(ALU);
		writeToRegister(WriteRegister);
		writeToMemory(WriteMemory);

		// Get next command and execute if different then fetch
		// Assume 00 means fetch
		if (nextuInstruction != null && nextuInstruction.size() > 0) {
			if (targetRegistries.size() > 0) {
				target = intToArray(targetRegistries.get(0));
			} else {
				System.out.println("No more targets");
			}
			ArrayList<Integer> nextuI = MicroInstruction(originalOpCode, target, arrayToString(nextuInstruction));
			execute(nextuI, originalOpCode, targetRegistries, literal);
		} else {
			resetImmediates();
		}
	}

	/*
	 * Conversions
	 */
	public static int binaryToDecimal(long param) {
		String binaryString = String.valueOf(param);
		//
		return Integer.parseInt(binaryString, 2);
	}

	public static String decimalToBinary(int param) {
		//
		return Integer.toBinaryString(param);
	}

	// convert from array to string
	public static String arrayToString(ArrayList<Integer> arr) {
		String str = "";

		for (int i = 0; i < arr.size(); i++) {
			str += arr.get(i);
		}
		return str;
	}

	// convert from int to int array
	public static int[] intToArray(long param) {
		String temp = Long.toString(param);
		int[] newGuess = new int[temp.length()];
		for (int i = 0; i < temp.length(); i++) {
			newGuess[i] = temp.charAt(i) - '0';
		}
		//
		return newGuess;
	}

	public static int[] stringToArray(String param) {
		int[] newGuess = new int[param.length()];
		for (int i = 0; i < param.length(); i++) {
			newGuess[i] = param.charAt(i) - '0';
		}
		//
		return newGuess;
	}

	/*
	 * increment PC
	 */
	public static void incrementPC(ArrayList<Integer> param) {
		if (param.get(0) == 1)
			PC = PC + 4;
	}
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Assg2 {
	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader("C:/Users/user/workspace/AssignementTwo/src/branch_history.txt"));
			//
			PrintWriter writer = new PrintWriter("C:/Users/user/workspace/AssignementTwo/src/the_buffer_64.txt",
					"UTF-8");
			// read the first line
			String line = reader.readLine();

			//
			String outputLine = "";
			//
			String[] splitedOutputLine;
			String[] splitedLine;
			String branchInstructionAdd = "";
			String branchTargetAdd = "";
			Boolean isTakenBranch = false;
			//
			String preLine = "";
			String[] preLineSplitedLine;
			Boolean preLineIsTakenBranch = false;

			String before2Lines = "";
			String[] before2LinesSplitedLine;
			Boolean before2LinesIsTakenBranch = false;

			String before3Lines = "";
			String[] before3LinesSplitedLine;
			Boolean before3LinesIsTakenBranch = false;

			// read from the written array "the_buffer_64.txt"
			ArrayList<String> bufferTakenStatusArray = new ArrayList<String>();
			Boolean readFromWriterPreLineIsTakenBranch = false;

			Boolean readFromWriterBefore2LinesIsTakenBranch = false;
			//
			int counter = 1;

			// if same status there is a hit
			int hitCounter = 0;

			// if different status there is a miss
			int missCounter = 0;
			//
			Boolean takenStatusChange = false;
			//
			while (line != null) {
				isTakenBranch = false;
				preLineIsTakenBranch = false;
				before2LinesIsTakenBranch = false;
				before3LinesIsTakenBranch = false;
				readFromWriterPreLineIsTakenBranch = false;
				readFromWriterBefore2LinesIsTakenBranch = false;
				takenStatusChange = false;

				splitedLine = line.split("\\s+");
				//
				branchInstructionAdd = splitedLine[0];
				branchTargetAdd = splitedLine[1];
				if (splitedLine[2].equals("1"))
					isTakenBranch = true;
				else
					isTakenBranch = false;

				if (counter > 0) {
					// read the line before one line of the current one and read
					// the
					// taken status
					preLine = Files
							.readAllLines(Paths.get("C:/Users/user/workspace/AssignementTwo/src/branch_history.txt"))
							.get(counter - 1);
					preLineSplitedLine = preLine.split("\\s+");
					if (preLineSplitedLine[2].equals("1"))
						preLineIsTakenBranch = true;
					else
						preLineIsTakenBranch = false;
				}

				if (counter > 1) {
					// read the line before two lines of the current one and
					// read
					// the taken status
					before2Lines = Files
							.readAllLines(Paths.get("C:/Users/user/workspace/AssignementTwo/src/branch_history.txt"))
							.get(counter - 2);
					before2LinesSplitedLine = before2Lines.split("\\s+");
					if (before2LinesSplitedLine[2].equals("1"))
						before2LinesIsTakenBranch = true;
					else
						before2LinesIsTakenBranch = false;
				}

				if (counter > 2) {
					// read the line before two lines of the current one and
					// read
					// the taken status
					before3Lines = Files
							.readAllLines(Paths.get("C:/Users/user/workspace/AssignementTwo/src/branch_history.txt"))
							.get(counter - 3);
					before3LinesSplitedLine = before3Lines.split("\\s+");
					if (before3LinesSplitedLine[2].equals("1"))
						before3LinesIsTakenBranch = true;
					else
						before3LinesIsTakenBranch = false;
				}

				// if the last 3 lines are equal
				if (((preLineIsTakenBranch && before2LinesIsTakenBranch && before3LinesIsTakenBranch)
						|| (!preLineIsTakenBranch && !before2LinesIsTakenBranch && !before3LinesIsTakenBranch))
						&& counter != 1) {
					// read from the written temporary array
					if (counter > 1) {
						if (bufferTakenStatusArray.get(counter - 2).equals("true")
								|| bufferTakenStatusArray.get(counter - 2).equals("1"))
							readFromWriterPreLineIsTakenBranch = true;
						else
							readFromWriterPreLineIsTakenBranch = false;
					}

					if (counter > 2) {
						if (bufferTakenStatusArray.get(counter - 3).equals("true")
								|| bufferTakenStatusArray.get(counter - 3).equals("1"))
							readFromWriterBefore2LinesIsTakenBranch = true;
						else
							readFromWriterBefore2LinesIsTakenBranch = false;
					}
					//
					if ((((readFromWriterPreLineIsTakenBranch && readFromWriterBefore2LinesIsTakenBranch)
							|| (!readFromWriterPreLineIsTakenBranch && !readFromWriterBefore2LinesIsTakenBranch))
							&& (!line.equals(readFromWriterPreLineIsTakenBranch))) || counter == 1) {

						if (bufferTakenStatusArray.get(bufferTakenStatusArray.size() - 1).equals(true)
								|| bufferTakenStatusArray.get(bufferTakenStatusArray.size() - 1).equals("1"))
							isTakenBranch = true;
						else
							isTakenBranch = false;
						//
						if (isTakenBranch && counter != 1)
							outputLine = branchInstructionAdd + "   " + branchTargetAdd + " " + 0;
						//
						if (!isTakenBranch && counter != 1)
							outputLine = branchInstructionAdd + "   " + branchTargetAdd + " " + 1;

						takenStatusChange = true;
					}
					//
				} else {
					if ((((preLineIsTakenBranch && before2LinesIsTakenBranch)
							|| (!preLineIsTakenBranch && !before2LinesIsTakenBranch))
							&& (!line.equals(preLineIsTakenBranch))) || counter == 1) {

						if (isTakenBranch && counter != 1)
							outputLine = branchInstructionAdd + "   " + branchTargetAdd + " " + 0;
						//
						if (!isTakenBranch && counter != 1)
							outputLine = branchInstructionAdd + "   " + branchTargetAdd + " " + 1;

						takenStatusChange = true;
						//
						missCounter++;
					} else {
						hitCounter++;
					}
				}

				// write the line into the buffer file
				if (counter == 1 || !takenStatusChange) {
					writer.println(line);
					//
					bufferTakenStatusArray.add(String.valueOf(isTakenBranch));
				} else {
					writer.println(outputLine);
					//
					splitedOutputLine = outputLine.split("\\s+");
					if (splitedOutputLine[2].equals("1") || splitedOutputLine[2].equals("true"))
						bufferTakenStatusArray.add("true");
					else
						bufferTakenStatusArray.add("false");
				}
				/*
				 * System.out.println("line " + counter + " >>> " + line);
				 * System.out.println( "branchInstructionAdd >>> " +
				 * Integer.toBinaryString(hexToDecimal(branchInstructionAdd) ));
				 * System.out.println("branchTargetAdd >>> " +
				 * Integer.toBinaryString(hexToDecimal(branchTargetAdd)));
				 * System.out.println("isTakenBranch >>> " + isTakenBranch);
				 */

				// read next line
				line = reader.readLine();
				//
				counter++;
			}
			reader.close();
			//
			writer.close();
			//
			System.out.println("missCounter >>> " + missCounter);
			System.out.println("hitCounter >>> " + hitCounter);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int hexToDecimal(String hex) {
		String digits = "0123456789ABCDEF";
		hex = hex.toUpperCase();
		int val = 0;
		for (int i = 0; i < hex.length(); i++) {
			char c = hex.charAt(i);
			int d = digits.indexOf(c);
			val = 16 * val + d;
		}
		return val;
	}
}

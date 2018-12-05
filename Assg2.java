import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Assg2 {
	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader("C:/Users/user/workspace/AssignementTwo/src/branch_history.txt"));
			// read the first line
			String line = reader.readLine();
			//
			String[] splitedLine;
			String branchInstructionAdd = "";
			String branchTargetAdd = "";
			Boolean isTakenBranch = false;
			int counter = 1;
			//
			while (line != null) {
				splitedLine = line.split("\\s+");
				//
				branchInstructionAdd = splitedLine[0];
				branchTargetAdd = splitedLine[1];
				if (splitedLine[2].equals("1"))
					isTakenBranch = true;
				else
					isTakenBranch = false;
				//
				System.out.println("line " + counter + " >>> " + line);
				System.out.println("branchInstructionAdd >>> " + branchInstructionAdd);
				System.out.println("branchTargetAdd >>> " + branchTargetAdd);
				System.out.println("isTakenBranch >>> " + isTakenBranch);

				// read next line
				line = reader.readLine();
				//
				counter++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

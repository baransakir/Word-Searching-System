import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WordSearchingSystem {
	public static void addSentence(String input, AVLTree tree) {
		String[] wordArray = input.replaceAll("\\p{Punct}", " ").replaceAll("  ", " ").toLowerCase().split(" ");
		// removing all punctuations
		// fixing possible bugs (double spaces)
		// decapitalise all letters
		// splitting text into words
		for (int i = 0; i < wordArray.length; i++)
			tree.setRoot(tree.insert(tree.getRoot(), wordArray[i]));
	}

	public static void clearAll(AVLTree tree) {
		if (tree.getRoot() == null) {
			System.out.println("List is already empty.");
			return;
		}
		tree.deleteTree();
		System.out.println("All texts removed from the list.");
	}

	public static void contains(String word, AVLTree tree) {
		System.out.println("Does this text contain \"" + word + "\"?");
		if (tree.search(word))
			System.out.println("Yes, amount of word \"" + word + "\" in the paragraph is "
					+ tree.returnNode(word).getCount() + ".");
		else
			System.out.println("No, there is not such a word like \"" + word + "\".");
	}

	public static void main(String[] args) {
		String menu = "1) Add new text\n2) Display word list\n3) Search keyword\n4) Clear list\n5) EXIT";
		String formatMenu = "1) .txt file\n2) Manually typing\n3) CANCEL";

		AVLTree tree = new AVLTree();
		Scanner sc = new Scanner(System.in);
		System.out.println("---WORD SEARCHING SYSTEM---\nSELECT OPERATOR\n" + menu);
		String choice = sc.next();

		while (true) {
			if (choice.equals("1")) {
				System.out.println("Which format do you want to use?\n" + formatMenu);
				String format = sc.next();
				while (true) {
					if (format.equals("1")) {
						String txt = "";
						System.out.println("Enter text file path:");
						String filePath = sc.next();
						if (!filePath.endsWith(".txt")) // fixing file path if necessary
							filePath += ".txt";
						File file = new File(filePath);
						try {
							Scanner readFile = new Scanner(file);
							while (readFile.hasNext()) {
								txt += readFile.nextLine();
							}
							readFile.close();
							addSentence(txt, tree);
						} catch (FileNotFoundException e) {
							System.out.println("INVALID TEXT PATH! Returning to main menu...");
						}
					} else if (format.equals("2")) {
						System.out.println("Enter text:");
						sc = new Scanner(System.in);
						String manualText = sc.nextLine();
						addSentence(manualText, tree);
					} else if (format.equals("3")) {
						System.out.println("Returning to main menu...");
					} else {
						System.out.println(
								"INVALID OPERATOR!\n\nPlease try again and select a valid operator:\n" + formatMenu);
						format = sc.next();
						continue;
					}
					break;
				}

			} else if (choice.equals("2")) {
				System.out.println("---WORD LIST & COUNTS---");
				tree.inOrder(tree.getRoot());
			} else if (choice.equals("3")) {
				System.out.print("Enter the word you want to search for: ");
				String word = sc.next().toLowerCase();
				contains(word, tree);
			} else if (choice.equals("4")) {
				clearAll(tree);
			} else if (choice.equals("5")) {
				System.out.println("Program terminated.");
				break;
			} else {
				System.out.println("INVALID OPERATOR! Please try again and select a valid operator:\n" + menu);
				choice = sc.next();
				continue;
			}
			System.out.println("\nSELECT NEXT OPERATOR:\n" + menu);
			choice = sc.next();
		}
		sc.close();
	}
}
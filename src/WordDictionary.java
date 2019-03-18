import java.io.*;
import java.util.ArrayList;

class WordDictionary {
    private int wordLength;
    private ArrayList<String> wordDictionary = new ArrayList<>(); // the word from the dictionary

    final int getWordLength() {
        return wordLength;
    }

    final ArrayList<String> getWordDictionary() {
        return wordDictionary;
    }

    WordDictionary(final int wordLength)
    {
        this.wordLength = wordLength;
    }

    /**
     * Reads built in dictionary to compile a list of words that are equal to word length supplied
     */
    void AddWords()
    {
        BufferedReader reader;
        try {
            InputStream in = getClass().getResourceAsStream("/dictionary.txt");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();

            while (line != null) {
                line = line.trim();
                if(line.length() == wordLength)
                {
                    if (line.matches("^[a-zA-Z]+$")) {
                        wordDictionary.add(line);
                    }
                }

                line = reader.readLine();
            }

            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

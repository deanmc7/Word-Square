import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class LettersStore {
    private Map<Character, Integer> letterCount = new HashMap<>();

    void setLetterCount(final char[] letters){
        for(char letter : letters){
            Integer freq = this.letterCount.get(letter);
            letterCount.put(letter, (freq == null) ? 1 : freq + 1);
        }
    }

    int GetLetterFreq(char letter){
        Integer freq = this.letterCount.get(letter);

        return Objects.requireNonNullElse(freq, 0);
    }

    void IncreaseLetterFreq(final char letter, final int freqIncrease){
        int freq = this.GetLetterFreq(letter);
        this.letterCount.put(letter, freq + freqIncrease);
    }

    void DecreaseLetterFreq(final char letter, final int freqDecrease){
        int freq = this.GetLetterFreq(letter);
        this.letterCount.put(letter, freq - freqDecrease);
    }

    void IncreaseLetterFreqFromWord(final String word) {
        for(char letter : word.toCharArray()) {
            this.IncreaseLetterFreq(letter, 1);
        }
    }

    void DecreaseLetterFreqFromWord(final String word) {
        for(char letter : word.toCharArray()) {
            this.DecreaseLetterFreq(letter, 1);
        }
    }

    boolean IsWordValid(final String word){
        for(char letter : word.toCharArray()){
            if(this.GetLetterFreq(letter) < 0){
                return false;
            }
        }
        return true;
    }
}

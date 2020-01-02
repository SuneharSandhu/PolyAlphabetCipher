
public class PolyAlphabet {

    int[] cipher = {5,19,19,5,19};
    
    public String encrypt(String text) {
        
        String result = "";

        for (int i = 0; i < text.length(); i++) {

            if (Character.isUpperCase(text.charAt(i))) {
                char ch = (char) (((int)text.charAt(i) + cipher[i] - 65) % 26 + 65);
                result += ch;
            } 
            else {
                char ch = (char) (((int)text.charAt(i) + cipher[i] - 97) % 26 + 97);
                result += ch;
            }
        }
        return result;
    }
}
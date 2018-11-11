import java.util.ArrayList;
import java.util.HashSet;

public class PasswordCracker {
    private static String[] passwords;
    private static HashSet<String> tried;

    public static void main(String[] args) {
        String[] pass = {"because", "can", "do", "must", "we", "what"};
        String attempt = "wedowhatwemustbecausewecan";
        System.out.println(passwordCracker(pass, attempt));
    }

    static String passwordCracker(String[] pass, String attempt) {
        passwords = pass;
        tried = new HashSet();
        ArrayList<Integer> result = getPasswordsCombinations(new ArrayList<Integer>(), new StringBuilder(), attempt);
        if (result == null) {
            return "WRONG PASSWORD";
        }
        return getStringResult(result);
    }

    private static ArrayList<Integer> getPasswordsCombinations(ArrayList<Integer> currentResult, StringBuilder currentAttempt, String attempt) {
        ArrayList<Integer> result;
        for (int i = 0; i < passwords.length; i++) {
            if ( passwords[i].length() + currentAttempt.length() <= attempt.length() &&
                    passwords[i].equals(attempt.substring(currentAttempt.length(), currentAttempt.length() + passwords[i].length()))) {
                currentAttempt.append(passwords[i]);
                currentResult.add(i);
                if (currentAttempt.length() == attempt.length()) {
                    return currentResult;
                }

                if (!tried.contains(currentAttempt.toString())) {
                    tried.add(currentAttempt.toString());
                    result = getPasswordsCombinations(currentResult, currentAttempt, attempt);
                    if (result != null) {
                        return result;
                    }
                }
                currentResult.remove(currentResult.size() - 1);
                currentAttempt.delete(currentAttempt.length() - passwords[i].length(), currentAttempt.length());
            }
        }
        return null;
    }

    private static String getStringResult(ArrayList<Integer> result) {
        StringBuilder stringResult = new StringBuilder();
        stringResult.append(passwords[result.get(0)]);
        for (int i = 1; i < result.size(); i++) {
            stringResult.append(" ");
            stringResult.append(passwords[result.get(i)]);
        }
        return stringResult.toString();
    }
}
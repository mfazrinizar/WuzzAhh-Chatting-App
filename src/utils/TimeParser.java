package utils;

public class TimeParser {

    // Message (message) (YYYY:MM:DD HH:MM:SS) -> YYYY:MM:DD HH:MM:SS
    public static String parseTime(String message) {
        int startIndex = message.lastIndexOf("(") + 1;
        int endIndex = message.lastIndexOf(")");
        if (startIndex > 0 && endIndex > startIndex) {
            return message.substring(startIndex, endIndex);
        }
        return ""; 
    }

    // Message (message) (YYYY:MM:DD HH:MM:SS) -> YYYY:MM:DD
    public static String parseDate(String message) {
        String time = parseTime(message);
        return time.substring(0, time.indexOf(" "));
    }

    // Message (message) (YYYY:MM:DD HH:MM:SS) -> HH:MM
    public static String parseClockOnly(String message) {
        String time = parseTime(message);
        return time.substring(time.indexOf(" ") + 1, time.lastIndexOf(":"));
    }

    // [Message (message)] (YYYY:MM:DD HH:MM:SS) -> [Message (message)]
    public static String parseMessage(String message) {
        int endIndex = message.lastIndexOf("(") - 1;
        if (endIndex > 0) {
            return message.substring(0, endIndex).trim();
        }
        return message; 
    }

    // public static void main(String[] args) {
    //     String message = "Hello, world! (I'm good) (2024-12-31 23:59:59.0)";
    //     System.out.println("Parsed Time: " + parseTime(message));
    //     System.out.println("Parsed Date: " + parseDate(message));
    //     System.out.println("Parsed Clock Only: " + parseClockOnly(message));
    //     System.out.println("Parsed Message: " + parseMessage(message));
    // }
}
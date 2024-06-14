package az.test.util;

public class RandomHelper {

    public static int generateInt(int rangeStart, int rangeEnd) {
        return (int) (Math.random() * (double) (rangeEnd - rangeStart + 1)) + rangeStart;
    }

    public static void main(String[] args) {
        int a = 10;
        int b = 3;
        System.out.println(a / b);
    }

}

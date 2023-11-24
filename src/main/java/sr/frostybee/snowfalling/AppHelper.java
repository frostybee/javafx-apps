package sr.frostybee.snowfalling;

/**
 *
 * @author frostybee
 */
public class AppHelper {

    public static int getRandomInt(int lower, int upper) {
        return (int) (Math.floor(Math.random() * (upper - lower + 1)) + lower);
    }

    public static double getRandomDouble(double lower, double upper) {
        return Math.random() * (upper - lower) + lower;
    }
}

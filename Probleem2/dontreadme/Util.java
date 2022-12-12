package lab4.dontreadme;

import java.util.Random;

public class Util
{
    public static Random rng = new Random();

    
    public static int boundedNextInt(int min, int max)
    {
        return min + rng.nextInt(max - min);
    }
}

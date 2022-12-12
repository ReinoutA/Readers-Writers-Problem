package lab4.dontreadme;


import lab4.RWConfig;


public class RWLogger
{

    public static void debugf(String message, Object... values)
    {
        if (!RWConfig.PERFORMANCE)
            System.out.format("[DEBUG]   > %s\n" , String.format(message, values));
    }

    public static void warnf(String message, Object... values)
    {
        System.out.format("[WARNING] > %s\n" , String.format(message, values));
    }

    public static void infof(String message, Object... values)
    {
        System.out.format("[INFO]    > %s\n" , String.format(message, values));
    }
}

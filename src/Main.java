import ru.dkuleshov.Hello3dWorld;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            Hello3dWorld test = new Hello3dWorld();
            test.loop();
        } catch (Exception ex)
        {
            System.out.println(ex);
        };
    }
}

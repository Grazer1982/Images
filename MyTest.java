import java.io.IOException;

public class MyTest
{

   public static void main(String[] args)
   {
      ImageExtractor test = new ImageExtractor();
      try
      {
         System.out.println(test.extractImageUrl(args[0]));
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

}

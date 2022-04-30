import java.io.*;
public class HelloWorld
{
    
    public static void main(String[] args) throws IOException
    {
        Writer w = new PrintWriter(System.out);
        w.write(new char[]{'H','e','l','l','o',' ','W','o','r','l','d','!'});
        w.flush();
    }

}

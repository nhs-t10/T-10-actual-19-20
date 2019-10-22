package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import java.io.*;

@Autonomous(name = "MimingReader")
public class MimingReader extends Library
{
    public void init()
    {
        hardwareInit();
    }

    /* The following loop reads MimingFile.txt line by line,
    accessing the "linear," "side," and "rotation" values and
    using them as parameters for the "omni" function. */
    public void loop()
    {
        float linear = 0;
        float side = 0;
        float rotation = 0;

        try
        {
        /* The object, "reader," is a FileReader that accesses
        MimingFile.txt, which contains instructions for this autonomous'
        actions. The object, "bufferReader," is a BufferedReader that
        will be used to read the aforementioned txt file. */
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("Environment.getExternalStorageDirectory.getPath()/FIRST/Miming/MimingFile.txt")));
        String line = bufferedReader.readLine();

            while(line != null)
            {
                int first = line.indexOf(" ");
                int second = line.indexOf(" ", first);

                linear = Float.parseFloat(line.substring(0, first));
                side = Float.parseFloat(line.substring(first + 1, second));
                rotation = Float.parseFloat(line.substring(second + 1));

                omni(linear, side, rotation);
                line = bufferedReader.readLine();
            }
        }

        catch (Exception ioe)
        {
            ioe.printStackTrace();
        }
    }
}
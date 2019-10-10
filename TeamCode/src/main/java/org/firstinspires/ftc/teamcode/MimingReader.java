package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Autonomous(name = "MimingReader")
public class MimingReader extends Library
{
    /* The object, "reader," is a FileReader that accesses
    MimingFile.txt, which contains instructions for this autonomous'
    actions. The object, "bufferReader," is a BufferedReader that
    will be used to read the aforementioned txt file. */
    File file = new File("MimingFile.txt");
    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

    public void init()
    {
        hardwareInit();
    }

    /* The following loop reads MimingFile.txt line by line,
    accessing the "linear," "side," and "rotation" values and
    using them as parameters for the "omni" function. */
    public void loop()
    {
        float linear, side, rotation;

        try
        {
            String line = bufferedReader.nextLine();

            if(line != null)
            {
                int first = line.indexOf(" ");
                int second = line.indexOf(" ", first);

                linear = Float.parseFloat(line.substring(0, first));
                side = Float.parseFloat(line.substring(first + 1, second));
                rotation = Float.parseFloat(line.substring(second + 1));
            }

            else
            {
                reader.close();
            }
        }

        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

        omni(linear, side, rotation);
    }
}*/

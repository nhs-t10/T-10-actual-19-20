package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import java.io.*;

@Autonomous(name = "MimingReader")
public class MimingReader extends Library
{
    //Declare bufferedReader
    BufferedReader bufferedReader = null;

    public void init()
    {
        hardwareInit();

        //Initialize bufferReader and catch the error that occurs if the MimingFile is not found
        try
        {
            File MimingFile = new File("/storage/emulated/0/FIRST/MimingFile.txt");
            bufferedReader = new BufferedReader(new FileReader(MimingFile));
        }

        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    /* The following loop reads MimingFile.txt line by line,
    accessing the "linear," "side," and "rotation" values and
    using them as parameters for the "drive" function. */
    public void loop()
    {
        try
        {
            String line = null;
            float linear, side, rotation;

            try
            {
                line = bufferedReader.readLine();
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }
            int cur = 0;
            boolean[] parsedValues = new boolean[6];

            for (int i = 0; i < 6; i++)
            {
                parsedValues[i] = Boolean.parseBoolean((line.substring(cur, line.indexOf(" ", cur))));
                cur = line.indexOf(" ", cur) + 1;
            }

            int next = line.indexOf(" ", cur) + 1;
            int after = line.indexOf(" ", next) + 1;

            linear = Float.parseFloat(line.substring(cur, next - 1));
            side = Float.parseFloat(line.substring(next, after - 1));
            rotation = Float.parseFloat(line.substring(after));

            intake(parsedValues[0], parsedValues[1]);
            grip(parsedValues[2]);
            platform(parsedValues[3]);
            lift(parsedValues[4], parsedValues[5]);
            drive(linear, side, rotation);
        }

        catch (NullPointerException npe)
        {
            npe.printStackTrace();
        }
    }
}
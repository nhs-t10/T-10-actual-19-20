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
            float[] parsedFloat = new float[4];
            boolean[] parsedBoolean = new boolean[6];

            for (int i = 0; i < parsedBoolean.length; i++)
            {
                parsedBoolean[i] = Boolean.parseBoolean((line.substring(cur, line.indexOf(" ", cur))));
                cur = line.indexOf(" ", cur) + 1;
            }

            for (int i = 0; i < parsedFloat.length - 1; i++)
            {
                parsedFloat[i] = Float.parseFloat(line.substring(cur, line.indexOf(" ", cur)));
                cur = line.indexOf(" ", cur) + 1;
            }

            float multiply = Float.parseFloat(line.substring(cur)) / getVoltage();

            intake(parsedBoolean[0], parsedBoolean[1]);
            gripSkystone(parsedBoolean[2]);
            gripFoundation(parsedBoolean[3]);
            liftGivenControllerValues(parsedBoolean[4], parsedBoolean[5]);
            drive(parsedFloat[0] * multiply, parsedFloat[1] * multiply, parsedFloat[2] * multiply);
        }

        catch (NullPointerException npe)
        {
            npe.printStackTrace();
        }
    }
}
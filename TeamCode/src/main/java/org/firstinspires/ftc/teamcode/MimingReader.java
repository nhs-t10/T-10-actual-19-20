package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import java.io.*;

@Autonomous(name = "MimingReader")
public class MimingReader extends Library
{
    BufferedReader bufferedReader = null;

    public void init()
    {
        hardwareInit();

        try
        {
            File MimingFile = new File("/storage/emulated/0/FIRST/MimingFile.txt");
            bufferedReader = new BufferedReader(new FileReader(MimingFile));
        }

        catch (Exception ioe)
        {
            ioe.printStackTrace();
        }
    }

    /* The following loop reads MimingFile.txt line by line,
    accessing the "linear," "side," and "rotation" values and
    using them as parameters for the "drive" function. */

    public void loop()
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

        /* The object, "reader," is a FileReader that accesses
        MimingFile.txt, which contains instructions for this autonomous'
        actions. The object, "bufferReader," is a BufferedReader that
        will be used to read the aforementioned txt file. */

        int first = line.indexOf(" ");
        int second = line.indexOf(" ", first + 1);

        linear = Float.parseFloat(line.substring(0, first));
        side = Float.parseFloat(line.substring(first + 1, second));
        rotation = Float.parseFloat(line.substring(second + 1));

        drive(linear, side, rotation, 0);
    }
}
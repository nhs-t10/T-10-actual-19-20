/*package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
@Autonomous(name = "MimingReader")
public class MimingReader extends Library
{
    public void init()
    {
        FileReader reader = new FileReader("MimingFile.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
    }

    public void loop()
    {
        float[] values = new float[3];

        try
        {
            String line = bufferedReader.nextLine();

            if(line != null)
            {
                int first = IndexOf(" ");
                int second = line.indexOf(" ", first);

                values[0] = Float.parseFloat(line.substring(0, first));
                values[0] = Float.parseFloat(line.substring(first + 1, second));
                values[0] = Float.parseFloat(line.substring(second + 1));
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

        omni(values[0], values[1], values[2]);
    }
}*/
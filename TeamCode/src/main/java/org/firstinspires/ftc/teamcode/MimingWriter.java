package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.File;

@TeleOp(name = "MimingWriter")
public class MimingWriter extends Library
{
    BufferedWriter writer = null;

    public void init()
    {
        hardwareInit();

        try
        {
            File MimingFile = new File("/storage/emulated/0/FIRST/MimingFile.txt");
            FileOutputStream MimingOutput = new FileOutputStream(MimingFile);
            writer = new BufferedWriter(new OutputStreamWriter(MimingOutput));
        }

        catch(java.io.IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    /* The following loop accesses the "linear," "side," and
    "rotation" values from gamepad1, uses them as paramaters
    for the drive function (curlimit ensures that the values
    gradually increase), and records them in MimingFile.txt
    (to be later accessed by "MimingReader.java"). */

    public void loop()
    {
        float linear = gamepad1.left_stick_y;
        float side = gamepad1.left_stick_x;
        float rotation = gamepad1.right_stick_x;

        drive(linear, side, rotation, 0);
        telemetry.addData("Values: ", linear + "\n " + rotation + "\n " + side);

        try
        {
            writer.write(linear + " " + rotation + " " + side);
            writer.newLine();
        }

        catch(java.io.IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
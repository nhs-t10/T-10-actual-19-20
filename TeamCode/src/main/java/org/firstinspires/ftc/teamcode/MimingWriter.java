package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;

@TeleOp(name = "MimingWriter")
public class MimingWriter extends Library
{
    public void init()
    {
        hardwareInit();
    }

    /* The following loop accesses the "linear," "side," and
    "rotation" values from gamepad1, uses them as paramaters
    for the omni function (curlimit ensures that the values
    gradually increase), and records them in MimingFile.txt
    (to be later accessed by "MimingReader.java"). */
    public void loop()
    {
        float curLimit = 2;
        float linear = gamepad1.left_stick_y;
        float side = gamepad1.left_stick_x;
        float rotation = gamepad1.right_stick_x;

        if(gamepad1.right_stick_button)
        {
            curLimit -= .5;
            if (curLimit < 1)
                curLimit = 2;
        }

        omni(linear/curLimit, side/curLimit, rotation/curLimit, 0);
        telemetry.addData("Values:", linear + "\n " + rotation + "\n " + side);

        try
        {
            Writer writer = new BufferedWriter(new FileWriter("/storage/emulated/0/FIRST/MimingFile.txt", true));
            writer.append(linear + " " + rotation + " " + side);
            writer.close();
        }

        catch (java.io.IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
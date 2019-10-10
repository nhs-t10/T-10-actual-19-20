package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.io.FileWriter;
import java.io.IOException;

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

        omni(linear/curLimit, side/curLimit, rotation/curLimit);
        telemetry.addData("Values:", String.valueOf(linear) + "\n " +String.valueOf(rotation) + "\n " + String.valueOf(side));

        try
        {
            FileWriter writer = new FileWriter("MimingFile.txt", true);
            writer.write(linear/curLimit + " " + side/curLimit + " " + rotation/curLimit);
            writer.close();
        }

        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
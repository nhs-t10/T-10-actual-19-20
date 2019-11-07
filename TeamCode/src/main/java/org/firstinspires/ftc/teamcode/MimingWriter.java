package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.File;

@TeleOp(name = "MimingWriter")
public class MimingWriter extends Library {
	public void init() {
		hardwareInit();
	}

    /* The following loop accesses the "linear," "side," and
    "rotation" values from gamepad1, uses them as paramaters
    for the drive function (curlimit ensures that the values
    gradually increase), and records them in MimingFile.txt
    (to be later accessed by "MimingReader.java"). */

    public void loop()
    {
        float linear, side, rotation;
        BufferedWriter writer = null;

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

        for (int i = 0; i < 10000; i++)
        {
            linear = gamepad1.left_stick_y;
            side = gamepad1.left_stick_x;
            rotation = gamepad1.right_stick_x;

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

            try { Thread.sleep(10); }
            catch (InterruptedException ie) { ie.printStackTrace(); }
        }
    }
}
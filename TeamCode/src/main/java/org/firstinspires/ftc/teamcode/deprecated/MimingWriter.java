package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.File;

@TeleOp(name = "MimingWriter")
public class MimingWriter extends Library
{
    //Declare writer
    BufferedWriter writer = null;

    public void init()
    {
        hardwareInit();

        //Initialize writer, "flush" (clear) MimingFile, and catch the error that occurs if
        //the MimingFile is not found
        try
        {
            File MimingFile = new File("/storage/emulated/0/FIRST/MimingFile.txt");
            FileOutputStream MimingOutput = new FileOutputStream(MimingFile);
            writer = new BufferedWriter(new OutputStreamWriter(MimingOutput));
            writer.flush();
        }

        catch(java.io.IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    //The following loop accesses various user inputs, uses them as parameters for the robot's
    //functions, and writes them to MimingFile
    public void loop()
    {
        float linear = gamepad1.left_stick_y;
        float side = gamepad1.left_stick_x;
        float rotation = gamepad1.right_stick_x;


        boolean a = gamepad1.a;
        boolean b = gamepad1.b;
        boolean x = gamepad1.x;
        boolean y = gamepad1.y;
		boolean liftUp = gamepad1.right_bumper;
		boolean liftDown = gamepad1.left_bumper;

		intake(a, b);
		gripSkystone(x);
		gripFoundation(y);
        liftGivenControllerValues(liftUp, liftDown);
        drive(linear, rotation, side);
        telemetry.addData("Values: ", linear + "\n " + rotation + "\n " + side);

        try
        {
            writer.write(a + " " + b + " " + x + " " + y + " " + liftUp + " " + liftDown + " " + linear + " " + rotation + " " + side + " " + getVoltage());
            writer.newLine();
        }

        catch(java.io.IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
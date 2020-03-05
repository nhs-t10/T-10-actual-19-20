package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "VuforiaSkystoneDetector")
public class VuforiaSkystoneDetector extends Library
{
    public void init()
    {
        hardwareInit();
        vuforiaInit();
    }

    public void loop()
    {
        //Movement inputs
        float linear = gamepad1.left_stick_y; //Forward and back
        float side = gamepad1.left_stick_x; //Right and left
        float rotation = gamepad1.right_stick_x; //Rotating in place

        if( gamepad1.right_stick_button )
        {
            mode = mode.getNext();
        }

        if( mode == DRIVING.Slow )
        {
            drive(linear / 2, rotation / 2, side / 2); // slow driving
        }

        else if( mode == DRIVING.Fast )
        {
            drive(linear, rotation, side); // fast driving
        }

        telemetry.addData("SkystoneIsVisible", isSkystoneVisible());
    }
}
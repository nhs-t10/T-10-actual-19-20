package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "SkystoneDetectorTestClass")
public class SkystoneDetectorTestClass extends Library
{
    public void init()
    {
        initSkysone();
    }

    public void loop()
    {
        if (isSkystoneVisible())
            telemetry.addLine("Skystone is visible");

        else
            telemetry.addLine("Skystone is not visible");
    }
}
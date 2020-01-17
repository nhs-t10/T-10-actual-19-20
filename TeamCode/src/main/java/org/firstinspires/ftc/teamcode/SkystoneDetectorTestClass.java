package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "SkystoneDetectorTestClass")
public class SkystoneDetectorTestClass extends Library
{
    float startEncoderValue;
    private static final float QUARRYGOAL = 10f;

    public void init()
    {
        startEncoderValue = getEncoderValue();
        initSkysone();
    }

    public void loop()
    {
        if (QUARRYGOAL != 0 && getEncoderValue() - QUARRYGOAL < startEncoderValue)
            drive((getEncoderValue() - startEncoderValue) / QUARRYGOAL * .6f + .3f, 0 , 0);


        if (isSkystoneVisible())
            telemetry.addLine("Skystone is visible");

        else
            telemetry.addLine("Skystone is not visible");
    }
}
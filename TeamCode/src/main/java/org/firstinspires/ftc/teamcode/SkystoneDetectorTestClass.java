package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "SkystoneDetectorTestClass")
public class SkystoneDetectorTestClass extends Library
{
    float startEncoderValue;
    private static float QUARRYGOAL = 10f;
    private static float SIDEWALL = 20f;

    public void init()
    {
        startEncoderValue = getEncoderValue();
        initSkysone();
    }

    public void loop()
    {
        QUARRYGOAL = drive(QUARRYGOAL, false);

        if (QUARRYGOAL == 0)
            SIDEWALL = drive(SIDEWALL, true);

        if (isSkystoneVisible())
            telemetry.addLine("Skystone is visible");

        else
            telemetry.addLine("Skystone is not visible");
    }

    public float drive(float goal, boolean strafe)
    {
        if (getEncoderValue() - QUARRYGOAL < startEncoderValue)
        {
            if( strafe )
                drive(0, ( getEncoderValue() - startEncoderValue ) / SIDEWALL * .6f + .3f, 0);
            else
                drive(( getEncoderValue() - startEncoderValue ) / QUARRYGOAL * .6f + .3f, 0, 0);

            return goal;
        }

        startEncoderValue = getEncoderValue();
        drive(0, 0, 0);
        return 0;
    }

}
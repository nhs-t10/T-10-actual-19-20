package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "SkystoneDetectorTestClass")
public class SkystoneDetectorTestClass extends Library
{
    float startEncoderValue;
    private float QUARRYGOAL = 10f;
    private float SIDEWALL = 10f;

    public void init()
    {
        startEncoderValue = getEncoderValue();
        initSkysone();
//      hardwareInit();

    }

    public void loop()
    {
//        QUARRYGOAL = driveFor(QUARRYGOAL, false);
//
//        if (QUARRYGOAL == 0)
//            SIDEWALL = driveFor(SIDEWALL, true);

        if (isSkystoneVisible())
            telemetry.addLine("Skystone is visible");

        else
            telemetry.addLine("Skystone is not visible");
    }

//    public float driveFor(float goal, boolean strafe)
//    {
//        if (getEncoderValue() - QUARRYGOAL < startEncoderValue)
//        {
//            if( strafe )
//                drive(0, ( getEncoderValue() - startEncoderValue ) / SIDEWALL * .6f + .3f, 0);
//            else
//                drive(( getEncoderValue() - startEncoderValue ) / QUARRYGOAL * .6f + .3f, 0, 0);
//
//            return goal;
//        }
//
//        startEncoderValue = getEncoderValue();
//        drive(0, 0, 0);
//        return 0;
//    }
}
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Backup Auto")
public class BackupAuto extends Library
{
    State curState;
    float startEncoderValue = getEncoderValue();

    private static final double ticksPerCM = 194.13;

    public void init()
    {
        hardwareInit();
    }

    public void loop()
    {
        switch (curState)
        {
            case STRAFE_TO_FOUNDATION:
                if (startEncoderValue + ticksPerCM * 50 > getEncoderValue())
                    drive(0, .5f, 0);

                else
                {
                    drive(0, 0, 0);
                    startEncoderValue = getEncoderValue();

                    curState = State.MOVE_TO_FOUNDATION;
                }

            case MOVE_TO_FOUNDATION:
                if (startEncoderValue + ticksPerCM * 25 > getEncoderValue())
                    drive(.5f, 0, 0);

                else
                {
                    drive(0, 0, 0);
                    gripFoundation(false);
                    startEncoderValue = getEncoderValue();

                    curState = State.DRAG_FOUNDATION;
                }

            case DRAG_FOUNDATION:
                if (startEncoderValue - ticksPerCM * 25 < getEncoderValue())
                    drive(-.5f, 0, 0);

                else
                {
                    drive(0, 0, 0);
                    gripFoundation(false);
                    startEncoderValue = getEncoderValue();

                    curState = State.DRAG_FOUNDATION;
                }

            case STRAFE_UNDER_BRIDGE:
                if (startEncoderValue - ticksPerCM * 50 < getEncoderValue())
                    drive(0, -.5f, 0);

                else
                    drive(0, 0, 0);

            default:
                telemetry.addLine("Routine is completed");
        }

    }

    enum State
    {
        STRAFE_TO_FOUNDATION, MOVE_TO_FOUNDATION, DRAG_FOUNDATION, STRAFE_UNDER_BRIDGE
    }
}

package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Backup Auto")
public class BackupAuto extends Library
{
    State curState;
    Turning turner;
    ElapsedTime clock;

    float startEncoderValue = getEncoderValue();
    private static final double ticksPerCM = 194.13;

    public void init()
    {
        curState = State.STRAFE_TO_FOUNDATION;

        turner = new Turning();
        turner.initImu(hardwareMap);

        clock = new ElapsedTime();
        hardwareInit();
    }

    public void loop()
    {
        switch (curState)
        {
            case STRAFE_TO_FOUNDATION:
                turner.turnDegrees(90);

                if (startEncoderValue + ticksPerCM * 50 > getEncoderValue() && clock.seconds() > 5)
                    drive(0, .5f, 0);

                else if (clock.seconds() > 5)
                {
                    drive(0, 0, 0);
                    startEncoderValue = getEncoderValue();

                    clock.reset();
                    curState = State.MOVE_TO_FOUNDATION;
                }

            case MOVE_TO_FOUNDATION:
                turner.turnDegrees(-90);

                if (startEncoderValue + ticksPerCM * 25 > getEncoderValue() && clock.seconds() > 5)
                    drive(.5f, 0, 0);

                else if (clock.seconds() > 5)
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

                    clock.reset();
                    curState = State.DRAG_FOUNDATION;
                }

            case STRAFE_UNDER_BRIDGE:
                turner.turnDegrees(-90);

                if (startEncoderValue - ticksPerCM * 50 < getEncoderValue() && clock.seconds() > 5)
                    drive(0, -.5f, 0);

                else if (clock.seconds() > 5)
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

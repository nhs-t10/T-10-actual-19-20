package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class GripFoundation
{
    boolean started;
    ElapsedTime clock;

    public GripFoundation()
    {
        started = false;
        clock = new ElapsedTime();
    }

    public void driveToQuarry(boolean isOnBlueSide)
    {
        if (!started)
        {
            started = true;
            clock.reset();
        }

        if (started && clock.seconds() < 5)
        {
            Library.drive(.5f, 0, 0);
        }
    }
}
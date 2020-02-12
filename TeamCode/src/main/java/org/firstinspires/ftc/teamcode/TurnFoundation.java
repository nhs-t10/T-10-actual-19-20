package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class TurnFoundation
{
    boolean started;
    ElapsedTime clock;
    Turning turner;

    public TurnFoundation()
    {
        started = false;
        clock = new ElapsedTime();
        turner = new Turning();
    }

    public void turnFoundation(boolean isOnBlueSide)
    {
        if (isOnBlueSide) {
            turner.turnDegrees(90);
        }
        else {
            turner.turnDegrees(-90);
        }
    }
}
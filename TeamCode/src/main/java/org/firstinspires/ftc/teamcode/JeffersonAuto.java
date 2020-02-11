package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="JeffersonAuto")
public class JeffersonAuto extends Library
{
    Method method;
    boolean isOnBlueSide;

    public void init()
    {
        hardwareInit();

        method = new Method();
        isOnBlueSide = true;
    }

    public void loop()
    {
        method.individualMethod(isOnBlueSide);
    }
}

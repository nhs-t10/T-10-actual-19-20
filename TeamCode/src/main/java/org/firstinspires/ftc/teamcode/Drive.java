package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Drive {
    private ElapsedTime clock;
    private boolean moving;

    public void Drive()
    {
        clock = new ElapsedTime();
        moving = false;
    }

    private void DriveToFoundation(boolean isBlue)
    {
        if (!moving)
        {
            clock.reset();
            moving = true;
        }

        else if (clock.seconds() < .75)
        {
            if (isBlue)
                Library.drive(0, -.5f, 0);
            else
                Library.drive(0, .5f, 0);
        }
    }

//    else if(Library.distanceLeft.getDistance(DistanceUnit.CM)<=80||distanceRight.getDistance(DistanceUnit.CM)<=80)
//
//    {
//        Library.drive(-.75f, 0, 0);
//    }
//
//    else
//    {
//        moving = false;
//        Library.drive(0, 0, 0);
//    }
}
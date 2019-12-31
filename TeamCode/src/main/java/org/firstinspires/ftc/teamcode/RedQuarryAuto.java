package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "RedQuarryAuto")
public class RedQuarryAuto extends Library
{
    //Constants that will be used to determine how far the robot should travel at certain intervals
    int lengthOfStone = 0;
    int distanceUntilCameraSeesOneStone = 0;
    int distanceFromQuarrySideToFoundation = 0;

    public void init()
    {
        /* quarryConfiguration = 0: 00X00X
           quarryConfiguration = 1: 0X00X0
           quarryConfiguration = 2: 00X00X
           quarryConfiguration = 3: undetected */

        driveFor(distanceUntilCameraSeesOneStone);
        int quarryConfiguration = getQuarryConfiguration();

        /* Pick up the skystone, skid until a wall is detected, move forward (overshoot)
           to push the foundation, use intake to drag the foundation into the building site,
           place the skystone */

        pickUpSkystone();

        while (!isRightSideTouching())
            drive(0, .5f, 0);

        driveFor(distanceFromQuarrySideToFoundation - distanceUntilCameraSeesOneStone);
        platform(true);

        while (!isBackSideTouching())
            drive(-.5f, 0, 0);

        placeSkystone();
    }

    //Skids until the robot is under the bridge
    public void loop()
    {
        if (!isUnderBridge())
            drive(0, .5f, 0);

        else
            drive(0, 0, 0);
    }

    //Determines the configuration of stones, returning 3 if no skystone is detected
    private int getQuarryConfiguration()
    {
        for (int stone = 0; stone < 3; stone++)
        {
            if(isSkystoneVisible())
                return stone;

            skidFor(lengthOfStone);
        }

        return 3;
    }
}
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "RedQuarryAuto")
public class RedQuarryAuto extends Library
{
    /* Constants that will be used to determine how far the robot should travel at certain intervals
       All distances are in mm */
    private static final float LENGTH_OF_STONE = 203.2f;
    private static final float DISTANCE_SKYSTONE_SHOULD_BE_LIFTED = 0;
    private static final float DISTANCE_FROM_QUARRY_TO_FOUNDATION = 1340;
    private static final float DISTANCE_UNTIL_CAMERA_SEES_ONE_STONE = 0;

    boolean isFoundationInBuildingSite = false;

    enum AutonomousStates
    {
        REACH_STARTING_LOCATION, GET_QUARRY_CONFIGURATION, PICKUP_STONE, DRIVE_TO_WALL, 
        DRIVE_TO_FOUNDATION, GRIP_FOUNDATION, DRIVE_TO_BUILDING_SITE, PLACE_STONE, MOVE_UNDER_BRIDGE;
    }

    public States(AutonomousStates autonomousStates) 
    { 
        this.autonomousStates = autonomousStates; 
    }  

    public void init()
    {
        int quarryConfiguration;
        String curState = "REACH_STARTING_LOCATION";
        States curAutonomousStates = new States(curState);
    }

    /* Pick up the skystone
       Skid until a wall is detected
       Move forward (overshoot) to push the foundation
       Drag the foundation into the building site
       Place the skystone 
       Skid until under the bridge */

    public void loop()
    {
        switch(curAutonomousStates)
            case REACH_STARTING_LOCATION:
                driveFor(DISTANCE_UNTIL_CAMERA_SEES_ONE_STONE);
                curState = "GET_QUARRY_CONFIGURATION"
                break;

            case GET_QUARRY_CONFIGURATION:
                quarryConfiguration = getQuarryConfiguration();
                curState = "PICKUP_STONE"
                break;

            case PICKUP_STONE:
                // rotate robot so that lift is facing forwards
                // drive remaining distance to quarry so that gripper is against stones
                grip(true);
                liftDistance(DISTANCE_SKYSTONE_SHOULD_BE_LIFTED);
                curState = "DRIVE_TO_TOP"
                break;

            case DRIVE_TO_WALL:
                // Make seperate function in library so that touch sensors aren't relied on (overshoot just in case)
                // Make sure overshoot gradually slows down
    
                if (!touchingSide())
                    drive(0, .5, 0);

                else
                {
                    drive(0, 0, 0);
                    curState = "DRIVE_TO_FOUNDATION"     
                }

                break;

            case DRIVE_TO_FOUNDATION:
            // TODO: make sure that the robot does not collide with the alliance partner's robot if it is parked under the skybridge, near the wall
                driveForEncoders(DISTANCE_FROM_QUARRY_TO_FOUNDATION);
                curState = "GRIP_FOUNDATION"
                break;

            case GRIP_FOUNDATION:
                gripFoundation(true);
                curState = "DRIVE_TO_BUILDING_SITE"
                break;
                
            case DRIVE_TO_BUILDING_SITE:
                if(!touchingBack())
                    drive(-.5, 0, 0);

                else
                {
                    curState = "PLACE_STONE";
                    drive(0, 0, 0);
                }    

                break;    

            case PLACE_STONE:
                // not final sitance lifted, think about placing multiple stones
                liftDistance(-DISTANCE_SKYSTONE_SHOULD_BE_LIFTED);
                grip(false);
                curState = "MOVE_UNDER_BRIDGE";
                break;

            case MOVE_UNDER_BRIDGE:
                //Use budget PID
                if(!isUnderBridge)
                    drive(0, .5f, 0);
                else
                    drive(0, 0, 0);

                break;

            default:
                telemetry.add("Autonomous Compelted with Quarry Configuration " + quarryConfiguration);
                break; 
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
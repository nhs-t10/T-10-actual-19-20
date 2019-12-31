package org.firstinspires.ftc.teamcode;

enum States
{
    DRIVE_TO_STARTING_POS, GET_QUARRY_CONFIGURATION, DRIVE_TO_QUARRY, PICKUP_STONE, DRIVE_TO_WALL,
    DRIVE_TO_FOUNDATION, GRIP_FOUNDATION, DRIVE_TO_BUILDING_SITE, PLACE_STONE, MOVE_UNDER_BRIDGE;
}

public class AutonomousStates extends Library
{
    String curState;
    AutonomousStates process;

    States states;
    int configuration;
    int numStonesPlaced;

    private static final float STONE_HEIGHT = 127;
    private static final float STONE_LENGTH = 203.2f;
    private static final float FOUNDATION_HEIGHT = 57.2f;
    private static final float DISTANCE_FROM_SIDE_WALL_TO_QUARRY = 600;
    private static final float DISTANCE_FROM_QUARRY_TO_FOUNDATION = 1340;
    private static final float DISTANCE_UNTIL_CAMERA_SEES_ONE_STONE = 300;
    private static final float DISTANCE_FROM_STARTING_POSITION_TO_TOP_WALL = 300;

    public AutonomousStates(States states)
    {
        this.states = states;
        this.configuration = 0;
        this.numStonesPlaced = 0;
    }

    public String basicQuarryAuto()
    {
        switch(states)
        {
            case DRIVE_TO_STARTING_POS:
                driveFor(DISTANCE_UNTIL_CAMERA_SEES_ONE_STONE);
                return "GET_QUARRY_CONFIGURATION";

            case GET_QUARRY_CONFIGURATION:
                configuration = getQuarryConfiguration();
                return "DRIVE_TO_QUARRY";

            case DRIVE_TO_QUARRY:
                driveFor(DISTANCE_FROM_SIDE_WALL_TO_QUARRY - DISTANCE_UNTIL_CAMERA_SEES_ONE_STONE);
                return "PICKUP_STONE";

            case PICKUP_STONE:
                rotateFor(90);
                gripStone(true);

                liftFor(10);
                return "DRIVE_TO_TOP";

            case DRIVE_TO_WALL:
                strafeFor(DISTANCE_FROM_STARTING_POSITION_TO_TOP_WALL);
                return "DRIVE_TO_FOUNDATION";

            case DRIVE_TO_FOUNDATION:
                driveFor(DISTANCE_FROM_QUARRY_TO_FOUNDATION);
                return "GRIP_FOUNDATION";

            case GRIP_FOUNDATION:
                gripFoundation(true);
                return "DRIVE_TO_BUILDING_SITE";

            case DRIVE_TO_BUILDING_SITE:
                driveFor(-(DISTANCE_FROM_SIDE_WALL_TO_QUARRY + DISTANCE_FROM_QUARRY_TO_FOUNDATION));
                return "PLACE_STONE";

            case PLACE_STONE:
                rotateMotorToPosition(STONE_HEIGHT * numStonesPlaced);

                driveFor(10);
                gripStone(false);

                strafeFor(100);
                liftDistance(-(STONE_HEIGHT * numStonesPlaced));

                return "MOVE_UNDER_BRIDGE";

            case MOVE_UNDER_BRIDGE:
                if (!isUnderBridge())
                {
                    drive(0, .5f, 0);
                    return "MOVE_UNDER_BRIDGE";
                }

                drive(0, 0, 0);

            default:
                return "COMPLETED";
        }
    }

    private int getQuarryConfiguration()
    {
        for (int stone = 0; stone < 3; stone++)
        {
            if(isSkystoneVisible())
                return stone;

            strafeFor(STONE_LENGTH);
        }

        return 3;
    }

    public void init()
    {
        curState = "REACH_STARTING_LOCATION";
        process = new AutonomousStates(States.valueOf(curState));
    }

    public void loop()
    {
        while (!curState.equals("COMPLETED"))
        {
            curState = process.basicQuarryAuto();
            process = new AutonomousStates(States.valueOf(curState));
        }
    }
}
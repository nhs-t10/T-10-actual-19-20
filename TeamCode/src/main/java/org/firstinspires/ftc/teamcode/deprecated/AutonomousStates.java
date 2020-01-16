//package org.firstinspires.ftc.teamcode;
//
//public class AutonomousStates extends Library
//{
//    enum States
//    {
//        DRIVE_TO_STARTING_POS, GET_QUARRY_CONFIGURATION, DRIVE_TO_QUARRY, PICKUP_STONE, DRIVE_TO_WALL,
//        DRIVE_TO_FOUNDATION, GRIP_FOUNDATION, DRIVE_TO_BUILDING_SITE, PLACE_STONE, MOVE_UNDER_BRIDGE;
//    }
//
//    String curState;
//    AutonomousStates process;
//
//    States states;
//    int configuration;
//    int numStonesPlaced;
//
//    private static final float STONE_HEIGHT_WITHOUT_NUBS = 101.6f;
//    private static final float STONE_NUB_HEIGHT = 25.4f;
//    private static final float STONE_LENGTH = 203.2f;
//    private static final float STONE_WIDTH = 101.6f;
//    private static final float FOUNDATION_HEIGHT = 57.2f;
//    private static final float DISTANCE_FROM_SIDE_WALL_TO_QUARRY = 600f;
//    private static final float DISTANCE_FROM_QUARRY_TO_FOUNDATION = 1340f;
//    private static final float DISTANCE_UNTIL_CAMERA_SEES_ONE_STONE = 300f;
//    private static final float DISTANCE_FROM_STARTING_POSITION_TO_TOP_WALL = 300f;
//
//    private static final float STONE_CLEARANCE_HEIGHT = 4f;
//
//    public AutonomousStates(States states)
//    {
//        this.states = states;
//        this.configuration = 0;
//        this.numStonesPlaced = 0;
//    }
//
//    public String basicQuarryAuto()
//    {
//        switch(states)
//        {
//            case DRIVE_TO_STARTING_POS:
//                driveForEncoders(getEncoderValue(), getEncoderValue(), DISTANCE_UNTIL_CAMERA_SEES_ONE_STONE);
//                return "GET_QUARRY_CONFIGURATION";
//
//            case GET_QUARRY_CONFIGURATION:
//                configuration = getQuarryConfiguration();
//                return "DRIVE_TO_QUARRY";
//
//            case DRIVE_TO_QUARRY:
//                driveForEncoders(getEncoderValue(), getEncoderValue(), DISTANCE_FROM_SIDE_WALL_TO_QUARRY - DISTANCE_UNTIL_CAMERA_SEES_ONE_STONE);
//                return "PICKUP_STONE";
//
//            case PICKUP_STONE:
//                //rotateFor(90);
//                gripStone(true);
//
//                moveLiftToPosition(STONE_CLEARANCE_HEIGHT);
//                return "DRIVE_TO_TOP";
//
//            case DRIVE_TO_WALL:
//                strafeForEncoders(DISTANCE_FROM_STARTING_POSITION_TO_TOP_WALL, true);
//                return "DRIVE_TO_FOUNDATION";
//
//            case DRIVE_TO_FOUNDATION:
//                driveForEncoders(getEncoderValue(), getEncoderValue(), DISTANCE_FROM_QUARRY_TO_FOUNDATION);
//                return "GRIP_FOUNDATION";
//
//            case GRIP_FOUNDATION:
//                gripFoundation(true);
//                return "DRIVE_TO_BUILDING_SITE";
//
//            case DRIVE_TO_BUILDING_SITE:
//                driveForEncoders(getEncoderValue(), getEncoderValue(), -(DISTANCE_FROM_SIDE_WALL_TO_QUARRY + DISTANCE_FROM_QUARRY_TO_FOUNDATION));
//                return "PLACE_STONE";
//
//            case PLACE_STONE:
//                moveLiftToPosition(FOUNDATION_HEIGHT + (numStonesPlaced * STONE_HEIGHT_WITHOUT_NUBS) + STONE_NUB_HEIGHT + STONE_CLEARANCE_HEIGHT);
//
//                driveForEncoders(getEncoderValue(), getEncoderValue(),10);
//                gripStone(false);
//
//                strafeForEncoders(100, true);
//                moveLiftToPosition(0);
//
//                return "MOVE_UNDER_BRIDGE";
//
//            case MOVE_UNDER_BRIDGE:
//                //Simon please fix this, the isUnderBridge() isn't used correctly
//                /*if (!isUnderBridge())
//                {
//                    drive(0, .5f, 0);
//                    return "MOVE_UNDER_BRIDGE";
//                }*/
//
//                drive(0, 0, 0);
//
//            default:
//                return "COMPLETED";
//        }
//    }
//
//    private int getQuarryConfiguration()
//    {
//        for (int stone = 0; stone < 3; stone++)
//        {
//            //if(isSkystoneVisible())
//                //return stone;
//
//            strafeForEncoders(STONE_LENGTH, true);
//        }
//
//        return 3;
//    }
//
//    public void init()
//    {
//        curState = "REACH_STARTING_LOCATION";
//        process = new AutonomousStates(States.valueOf(curState));
//    }
//
//    public void loop()
//    {
//        while (!curState.equals("COMPLETED"))
//        {
//            curState = process.basicQuarryAuto();
//            process = new AutonomousStates(States.valueOf(curState));
//        }
//    }
//}
//
////package org.firstinspires.ftc.teamcode;
////
////enum States
////{
////    DRIVE_TO_STARTING_POS, GET_QUARRY_CONFIGURATION, DRIVE_TO_QUARRY, PICKUP_STONE, DRIVE_TO_WALL,
////    DRIVE_TO_FOUNDATION, GRIP_FOUNDATION, DRIVE_TO_BUILDING_SITE, PLACE_STONE, MOVE_UNDER_BRIDGE;
////}
////
////public class AutonomousStates extends Library
////{
////    String curState;
////    AutonomousStates process;
////
////    States states;
////    int configuration;
////    int numStonesPlaced;
////
////    private static final float STONE_HEIGHT_WITHOUT_NUBS = 101.6f;
////    private static final float STONE_NUB_HEIGHT = 25.4f;
////    private static final float STONE_LENGTH = 203.2f;
////    private static final float STONE_WIDTH = 101.6f;
////    private static final float FOUNDATION_HEIGHT = 57.2f;
////    private static final float DISTANCE_FROM_SIDE_WALL_TO_QUARRY = 600;
////    private static final float DISTANCE_FROM_QUARRY_TO_FOUNDATION = 1340;
////    private static final float DISTANCE_UNTIL_CAMERA_SEES_ONE_STONE = 300;
////    private static final float DISTANCE_FROM_STARTING_POSITION_TO_TOP_WALL = 300;
////
////    private static final float STONE_CLEARANCE_HEIGHT = 4;
////
////    public AutonomousStates(States states)
////    {
////        this.states = states;
////        this.configuration = 0;
////        this.numStonesPlaced = 0;
////    }
////
////    public String basicQuarryAuto()
////    {
////        switch(states)
////        {
////            case DRIVE_TO_STARTING_POS:
////                driveForEncoders(DISTANCE_UNTIL_CAMERA_SEES_ONE_STONE, true);
////                return "GET_QUARRY_CONFIGURATION";
////
////            case GET_QUARRY_CONFIGURATION:
////                configuration = getQuarryConfiguration();
////                return "DRIVE_TO_QUARRY";
////
////            case DRIVE_TO_QUARRY:
////                driveForEncoders(DISTANCE_FROM_SIDE_WALL_TO_QUARRY - DISTANCE_UNTIL_CAMERA_SEES_ONE_STONE, true);
////                return "PICKUP_STONE";
////
////            case PICKUP_STONE:
////                rotateFor(90);
////                gripStone(true);
////
////                moveLiftToPosition(STONE_CLEARANCE_HEIGHT);
////                return "DRIVE_TO_TOP";
////
////            case DRIVE_TO_WALL:
////                strafeForEncoders(DISTANCE_FROM_STARTING_POSITION_TO_TOP_WALL);
////                return "DRIVE_TO_FOUNDATION";
////
////            case DRIVE_TO_FOUNDATION:
////                driveForEncoders(DISTANCE_FROM_QUARRY_TO_FOUNDATION);
////                return "GRIP_FOUNDATION";
////
////            case GRIP_FOUNDATION:
////                gripFoundation(true);
////                return "DRIVE_TO_BUILDING_SITE";
////
////            case DRIVE_TO_BUILDING_SITE:
////                driveForEncoders(-(DISTANCE_FROM_SIDE_WALL_TO_QUARRY + DISTANCE_FROM_QUARRY_TO_FOUNDATION), true);
////                return "PLACE_STONE";
////
////            case PLACE_STONE:
////                moveLiftToPosition(FOUNDATION_HEIGHT + (numStonesPlaced * STONE_HEIGHT_WITHOUT_NUBS) + STONE_NUB_HEIGHT + STONE_CLEARANCE_HEIGHT);
////
////                driveForEncoders(10, true);
////                gripStone(false);
////
////                strafeForEncoders(100, true);
////                moveLiftToPosition(0);
////
////                return "MOVE_UNDER_BRIDGE";
////
////            case MOVE_UNDER_BRIDGE:
////                if (!isUnderBridge())
////                {
////                    drive(0, .5f, 0);
////                    return "MOVE_UNDER_BRIDGE";
////                }
////
////                drive(0, 0, 0);
////
////            default:
////                return "COMPLETED";
////        }
////    }
////
////    private int getQuarryConfiguration()
////    {
////        for (int stone = 0; stone < 3; stone++)
////        {
////            if(isSkystoneVisible())
////                return stone;
////
////            strafeForEncoders(STONE_LENGTH, true);
////        }
////
////        return 3;
////    }
////
////    public void init()
////    {
////        curState = "REACH_STARTING_LOCATION";
////        process = new AutonomousStates(States.valueOf(curState));
////    }
////
////    public void loop()
////    {
////        while (!curState.equals("COMPLETED"))
////        {
////            curState = process.basicQuarryAuto();
////            process = new AutonomousStates(States.valueOf(curState));
////        }
////    }
////}

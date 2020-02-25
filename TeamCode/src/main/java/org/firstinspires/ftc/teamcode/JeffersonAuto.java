package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "JeffersonAuto")
public class JeffersonAuto extends Library{
    enum States{
        BACK_UP_FOUNDATION, COLOR_PARKING, DRIVE_BACK_TO_QUARRY, DRIVE_TO_BUILDING_ZONE, DRIVE_TO_FOUNDATION,
        DRIVE_TO_QUARRY, EXTEND_TAPE_MEASURE, GET_QUARRY_CONFIGURATION, GRIP_FOUNDATION, MOVE_TO_PARKING_POSITION_FOUNDATION,
        PICK_UP_BLOCK, PLACE_BLOCK, PUSH_FOUNDATION, TURN_FOUNDATION, TURN_TO_PARKING_LINE_BLOCK, UNGRIP_FOUNDATION
    }

    Enum foundation[] = new Enum[]{ States.DRIVE_TO_FOUNDATION, States.GRIP_FOUNDATION, States.BACK_UP_FOUNDATION, States.TURN_FOUNDATION,
                                    States.PUSH_FOUNDATION.UNGRIP_FOUNDATION, States.MOVE_TO_PARKING_POSITION_FOUNDATION, States.EXTEND_TAPE_MEASURE};
    Enum block[] = new Enum[]{ States.DRIVE_TO_QUARRY, States.GET_QUARRY_CONFIGURATION, States.PICK_UP_BLOCK, States.DRIVE_TO_BUILDING_ZONE,
                                States.PLACE_BLOCK, States.TURN_TO_PARKING_LINE_BLOCK, States.EXTEND_TAPE_MEASURE};


    JeffAutoMethods method;
    int index = 0;
    boolean isOnBlueSide;
    boolean finished;
    boolean isOnFoundationSide;

    public void init(){
        hardwareInit();

        isOnBlueSide = true;
        finished = false;
        isOnFoundationSide = true;


        method = new JeffAutoMethods(isOnBlueSide);

    }

    public void loop(){
        if(isOnFoundationSide){
            if( foundation[index] == States.DRIVE_TO_FOUNDATION ){
                finished = method.driveToFoundation();
            }else if( foundation[index] == States.GRIP_FOUNDATION ){
                finished = method.gripFoundation();
            }else if( foundation[index] == States.BACK_UP_FOUNDATION ){
                finished = method.backUpFoundation();
            }else if( foundation[index] == States.TURN_FOUNDATION ){
                finished = method.turnFoundation();
            }else if( foundation[index] == States.PUSH_FOUNDATION ){
                finished = method.pushFoundation();
            }else if( foundation[index] == States.UNGRIP_FOUNDATION ){
                finished = method.ungripFoundation();
            }else if( foundation[index] == States.MOVE_TO_PARKING_POSITION_FOUNDATION ){
                finished = method.moveToParkingPositionFoundation();
            }else if( foundation[index] == States.EXTEND_TAPE_MEASURE ){
                finished = method.extendTapeMeasure(5);
            }
        }else{
            if( block[index] == States.DRIVE_TO_QUARRY){
                finished = method.driveToQuarry();
            }else if(block[index] == States.GET_QUARRY_CONFIGURATION){
                finished = method.getQuarryConfiguration();
            }else if(block[index] == States.PICK_UP_BLOCK){
                finished = method.pickUpBlock();
            }else if(block[index] == States.DRIVE_TO_BUILDING_ZONE){
                finished = method.driveToBuildingZone();
            }else if(block[index] == States.PLACE_BLOCK){
                finished = method.placeBlock();
            }else if(block[index] == States.TURN_TO_PARKING_LINE_BLOCK){
                finished = method.turnToParkingLineBlock();
            }else if(block[index] == States.EXTEND_TAPE_MEASURE){
                finished = method.extendTapeMeasure(5);
            }
        }
        if( finished ){
            finished = false;
            index++;
        }
        telemetry.addData("State:", foundation[index]);
    }
}

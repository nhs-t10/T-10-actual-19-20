package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "JeffersonAuto")
public class JeffersonAuto extends Library{
    enum States{
        BACK_UP_FOUNDATION, COLOR_PARKING, DRIVE_BACK_TO_QUARRY, DRIVE_TO_BUILDING_ZONE, DRIVE_TO_FOUNDATION, DRIVE_TO_QUARRY,
        EXTEND_TAPE_MEASURE, GET_QUARRY_CONFIGURATION, GRIP_FOUNDATION, MOVE_TO_PARKING_POSITION_FOUNDATION, PICK_UP_BLOCK,
        PLACE_BLOCK, PUSH_FOUNDATION, TURN_FOUNDATION, TURN_TO_PARKING_LINE,UNGRIP_FOUNDATION
    }

    Enum foundation[] = new Enum[] {States.DRIVE_TO_FOUNDATION, States.BACK_UP_FOUNDATION};


    JeffAutoMethods method;
    int index = 0;
    boolean isOnBlueSide;
    boolean finished;

    public void init(){
        hardwareInit();

        isOnBlueSide = true;
        finished = false;

        method = new JeffAutoMethods(isOnBlueSide);

    }

    public void loop(){
        if(foundation[index] == States.DRIVE_TO_FOUNDATION){
            finished = method.driveToFoundation();
        }else if(foundation[index] == States.GRIP_FOUNDATION){
            finished = method.gripFoundation();
        }
        if(finished){
            finished = false;
            index++;
        }
        telemetry.addData("State:", foundation[index]);
    }
}

package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name= "PlatfromAuto")
public class PlatformAuto extends Library {
    enum state {
        MOVING, PUSHING;
    }

    state currentState = null;

    public void init() {
        hardwareInit();
        currentState = state.MOVING;
    }

    public void loop() {
        //Loop constantly checks state, and then executes a command based on this.
        if (currentState == state.MOVING) {
            MoveToPlatform();
        }
        if (currentState == state.PUSHING) {
            PushPlatform();
        }
        telemetry.addData("Current State: ", currentState);
    }

    public void MoveToPlatform() {
        //if aligned with a skystone, drive foreward and get it to ghe other side
        //if not aligned, use CV to align <-- likely a seperate method
        currentState = state.PUSHING;
    }

    public void PushPlatform() {
        
    }
}
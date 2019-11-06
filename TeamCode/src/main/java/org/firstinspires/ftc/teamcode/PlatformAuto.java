package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name= "PlatfromAuto")
public class PlatformAuto extends Library {
    enum state {
        PULLING, MOVING, SCANNING;
    }

    state currentState = null;

    public void init() {
        hardwareInit();
        currentState = state.PULLING;
    }

    public void loop() {
        //Loop constantly checks state, and then executes a command based on this.
        if (currentState == state.PULLING) {
            MoveToPlatform();
        }
        if (currentState == state.MOVING) {
            MoveToBlocks();
        }
        if (currentState == state.MOVING) {
            ScanBlocks();
        }
        telemetry.addData("Current State: ", currentState);
    }

    public void MoveToPlatform() {//this is code that moves the platform
        platform(false);//sets platform grabber up
        driveFor(75, .75f, 0, 0);//moves to platform
        platform(true);//sets platform grabber down
        driveForNeg(-75, -.75f, 0, 0);//moves back
        platform(false);//sets platform grabber up
        currentState = state.MOVING;
    }

    public void MoveToBlocks() {//this code moves from triangle to blocks.
        //turn 90 degrees and move forward then turn back to original direction
        //or just move sideways until clear of platform
        //then drive until center
        //then turn and drive over bride, arrived
        currentState = state.SCANNING;
    }

    public void ScanBlocks() {
        //Scan for skystones
    }
}
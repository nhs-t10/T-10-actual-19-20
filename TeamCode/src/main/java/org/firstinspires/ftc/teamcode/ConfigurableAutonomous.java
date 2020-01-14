package org.firstinspires.ftc.teamcode;

import java.util.Queue;
import java.awt.Point;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="configurable autonomous")
public class ConfigurableAutonomous extends Library {

    //constants for keeping track of red side vs blue side
    enum Side {
        RED, BLUE;
    }

    enum State {
        STOPPED, NAVIGATING; //obv add more
    }

    //assumed position of the robot at any given time
    //measured where the bottom left of the field (0, 0) is the red depot
    //TODO: attach picture to better explain
    private float robotPosX;
    private float robotPosY;

    //important locations for the autonomous, will be set by buildStatesQueue()
    private float startingPosition;

    State currentState;
    //sequence of actions to be performed in autonomous
    private Queue<State> statesQueue;


    @Override
    public void init() {
        //initialize hardware
        hardwareInit();
        //init encoders
        //init color sensor stuff
        //possibly init time

        //might need to become its own state in loop()
        statesQueue = buildStatesQueue();
        currentState = statesQueue.poll();

        //init imu?
        //set 0 position for angle of robot
    }

    @Override
    public void init_loop() {
        //this code is looped after init is pressed but before play is pressed
        //set up of statesQueue with a controller could go here
    }
    
    @Override
    public void start() {
        //code here executes ONCE after start is pressed
    }

    @Override
    public void loop() {
        switch(currentState) {
            case STOP:
                stop();
            
            case null:
                telemetry.addData("currentState is null. This likely means that the stop() function was not properly called");

            default:
                telemetry.addData("currentState: " + currentState + " does not have a case in loop(). This means there is nothing to execute.");
        }
    }

    @Override
    public void stop() {

    }


    //TODO: add parkingLocation and other details
    private Queue<State> buildStatesQueue(Side color, int skystonesToPickup, int skystonesToPlace, boolean shouldMoveFoundation) {
        //check for mistakes with inputted parameters
        if(skystonesToPickup > 2) {
            telemetry.addData("There are only 2 skystones to pick up. Setting skystonesToPickup to 2");
            skystonesToPickup = 2;
        }
        if(skystonesToPlace > skystonesToPickup) {
            telemetry.addData("Can't place more skystones than you pick up. Placing max possible number of skystones: " + skystonesToPickup);
            skystonesToPlace = skystonesToPickup;
        }


        Queue<State> statesQueue = new Queue<State>();


        statesQueue.offer(State.STOPPED);
        return(statesQueue);
    }
}

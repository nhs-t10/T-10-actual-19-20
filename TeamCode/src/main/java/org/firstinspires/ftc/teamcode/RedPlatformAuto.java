package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Red Platform")//do not delete this test class used by sasha
public class RedPlatformAuto extends Library {
    public final int platformDistance = 60;
    public final float driveSpeed = .75f;

    enum State{
        TO_PLATFORM, FROM_PLATFORM, PARKING, END
    }
    State currentstate;
    int i = 0;
    long startTime, startTime2, startTime3, duration, duration2, duration3;
    ElapsedTime clock = new ElapsedTime();
    boolean moving = false;

    @Override public void init() {
        hardwareInit();
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);// we may use more motor encoders but some of the encoders have weird values
        currentstate = State.TO_PLATFORM;
    }
    public void loop()
    {
        if(currentstate == State.TO_PLATFORM){
            ToPlatform();
        }
        if(currentstate == State.FROM_PLATFORM){
            FromPlatform();
        }
        if(currentstate == State.PARKING){
            Parking();
        }

        telemetry.addData("Red color: ", color.red());
        telemetry.addData("Millis since run: ", clock.seconds());
        telemetry.addData("State: ", currentstate);
    }

    public void ToPlatform() {
        if (!moving) {
            clock.reset();
            moving = true;
            slideForEncoders(60, -1);
        } else if (clock.seconds() < 2) {

        } else if (clock.seconds() > 2 && color.red()<20) {
            drive(1f,0,0);
        }
        else{
            moving = false;
            drive(0,0,0);
            currentstate = State.FROM_PLATFORM;
        }
    }

    public void FromPlatform() {
        if (!moving) {
            clock.reset();
            moving = true;
            grabber.setPosition(1);
        } else if (clock.seconds() < 2) {

        } else if (clock.seconds() > 2 && (!front1.isPressed()||!front2.isPressed())) {
            drive(-1f,0,0);//drives until touching wall
        }
        else{
            moving = false;
            drive(0,0,0);
            currentstate = State.PARKING;
        }
    }

    public void Parking() {
        if (!moving) {
            clock.reset();
            moving = true;
            slideForEncoders(60, 100);
        } else if (clock.seconds() < 2) {

        } else if (clock.seconds() > 2 && color.red()<20) {
            drive(0,0,1);
        }
        else{
            moving = false;
            drive(0,0,0);
            currentstate = State.END;
        }
    }
}


//drive with encoders distance to foundation + overshoot
//move foundation gripper servo(s)
//drive back until touch sensor is pressed
//if it hasn't been pressed by distance to wall + overshoot
//backtrack overshoot
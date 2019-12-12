package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name="foundation sasha")//do not delete this test class used by sasha
public class RedPlatformAuto extends Library {
    public final int DRIVE_TO_PLATFORM = 100;
    public final float DRIVE_SPEED = .8f;

    enum State{
        Pulling, Parking, Waiting
    }
    State stat = State.Pulling;

    @Override public void init() {
        hardwareInit();
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);// we may use more motor encoders but some of the encoders have weird values

    }
    public void loop()
    {
        if(stat==State.Pulling)
        {
            driveForEncoders(DRIVE_TO_PLATFORM,-DRIVE_SPEED);

            driveForEncoders(DRIVE_TO_PLATFORM,DRIVE_SPEED);
            stat=State.Parking;
        }

        if(stat==State.Parking)
        {
            //driveUntil(ColorSensor,0,0,DRIVE_SPEED);  //commented because I need to define color sensor
            drive(0,0,0);
            stat=State.Waiting;
        }
    }
}


//drive with encoders distance to foundation + overshoot
//move foundation gripper servo(s)
//drive back until touch sensor is pressed
//if it hasn't been pressed by distance to wall + overshoot
//backtrack overshoot
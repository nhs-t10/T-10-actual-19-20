package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="encoders testing")//do not delete this test class used by sasha
public class AutoTest extends Library {
    public final int DRIVE_TO_PLATFORM = 100;
    public final float DRIVE_SPEED = .8f;

    enum State
    {
        Pulling, Parking, Waiting
    }

    State stat = State.Pulling;

    @Override public void init() {
        hardwareInit();
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);// we may use more motor encoders but some of the encoders have weird values

    }
    public void loop()
    {

        float linear = gamepad1.left_stick_y;
        float side = gamepad1.left_stick_x;
        float rotation = gamepad1.right_stick_x;
        boolean a = gamepad1.a;
        boolean b = gamepad1.b;
        boolean liftUp = gamepad1.right_bumper;
        boolean liftDown = gamepad1.left_bumper;
        //float sums[] = drive(linear,rotation,side); //THIS ERRORS

        if(a)
        {
            telemetry.addData("button getting pressed", "a is pressed");
            driveForEncoders(100,1);
        }
        if (b)
        {
            telemetry.addData("button getting pressed", "b is pressed");

            driveForEncoders(150,.5f);
        }
        if(liftUp)
        {
            telemetry.addData("button getting pressed", "r1 is pressed");

            slideForEncoders(100,1);
        }
        if(liftDown)
        {
            telemetry.addData("button getting pressed", "a is pressed");

            slideForEncoders(150,.5f);
        }


//        if(stat==State.Pulling)
//        {
//            driveForEncoders(DRIVE_TO_PLATFORM,-DRIVE_SPEED);
//
//            driveForEncoders(DRIVE_TO_PLATFORM,DRIVE_SPEED);
//            stat=State.Parking;
//        }
//
//        if(stat==State.Parking)
//        {
//            //driveUntil(ColorSensor,0,0,DRIVE_SPEED);  //commented because I need to define color sensor
//            drive(0,0,0);
//            stat=State.Waiting;
//        }
    }
}
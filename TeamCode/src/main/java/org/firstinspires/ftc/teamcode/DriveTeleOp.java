package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TeleOp")
public class DriveTeleOp extends Library{
    boolean subroutine;

    public void init()
    {
        hardwareInit();
    }
    private float[] sums;
    public void loop(){


        //Intake for blocks | gamepad 1
        boolean a = gamepad1.a;
        boolean c = gamepad1.right_bumper;
        boolean v = gamepad1.left_bumper;
        //Output for blocks | gamepad 1
        boolean b = gamepad1.b;
        //Intake for blocks | gamepad 2
        boolean a2 = gamepad2.a;
        //Output for blocks | gamepad 2
        boolean b2 = gamepad2.b;

        //Stone gripping | both gamepads
        boolean x = gamepad1.x;
        boolean x2 = gamepad2.x;

        //Hook control to grab foundation | both gamepads
        boolean y = gamepad1.y;
        boolean y2 = gamepad2.y;

        //Lift controls | Both gamepads
        boolean liftUp = gamepad1.right_bumper;
        boolean liftDown = gamepad1.left_bumper;
        boolean liftUp2 = gamepad2.right_bumper;
        boolean liftDown2 = gamepad2.left_bumper;
        //boolean skystone = gamepad1.dpad_up;

        //Movement inputs
        float linear = gamepad1.left_stick_y; //Forward and back
        float side = gamepad1.left_stick_x; //Right and left
        float rotation = gamepad1.right_stick_x; //Rotating in place


        //If controller two gives any commands (true) than the robot will use those inputs
        //Otherwise, it will use the inputs of controller one

        if( a2 || b2 ){
            intake(a2, b2);
        }else{
            intake(a, b);
        }

//        lowerIntake(c);

        if( x2 ){
//            gripStone(true);
        }else{
//            gripStone(x);
        }
        if( x2 ){
            //            gripStone(true);
        }else{
            //            gripStone(x);
        }

        if( y2 ){
//            gripFoundation(true);
        }else{
//            gripFoundation(y);
        }

        if( liftUp2 || liftDown2 ){
//            liftGivenControllerValues(liftUp2, liftDown2);
        }else{
//            liftGivenControllerValues(liftUp, liftDown);
        }

        /*(if (grabberRight2 != 0 || grabberLeft2 != 0)
        	gRotate(grabberLeft2, grabberRight2);

           else
        	gRotate(grabberLeft, grabberRight);*/

        if( gamepad1.right_stick_button ){
            mode = mode.getNext();
        }

        if( mode == DRIVING.Slow ){
            sums = drive(linear / 2, rotation / 2, side / 2); // slow driving
        }

        if( mode == DRIVING.Medium ){
            sums = drive(linear / 1.5f, rotation / 1.5f, side / 1.5f); // medium driving
        }

        if( mode == DRIVING.Fast ){
            sums = drive(linear, rotation, side); // fast driving
        }

        telemetry.addData("Front Left", sums[0]);
        telemetry.addData("Front Right", sums[1]);
        telemetry.addData("Back Left", sums[2]);
        telemetry.addData("Back Right", sums[3]);

        telemetry.addData("Values: ", linear + "\n " + rotation + "\n " + side);
    }
}
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Test Turning")
public class TestTurning extends Library{
    private boolean subroutine;
    private boolean intakeUp = true;
    Turning turner;

    public void init(){
        hardwareInit();
        driveInit();
        turner = new Turning();
        turner.initImu(hardwareMap);
    }

    private float[] sums = new float[4];

    public void loop(){
        //tape measure code
        boolean a = gamepad1.a;
        boolean a2 = gamepad2.a;
        boolean b = gamepad1.b;
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

        //Intake controls | Both gamepads
        float intakeIn = gamepad1.right_trigger;
        float intakeOut = gamepad1.left_trigger;
        float intakeIn2 = gamepad2.right_trigger;
        float intakeOut2 = gamepad2.left_trigger;

        //Movement inputs
        float linear = gamepad1.left_stick_y; //Forward and back
        float side = gamepad1.left_stick_x; //Right and left
        float rotation = gamepad1.right_stick_x * .5f; //Rotating in place

        //If controller two gives any commands (true) than the robot will use those inputs
        //Otherwise, it will use the inputs of controller one

        if( a || a2 ){
            tapeMeasure.setPower(1);
        }else if( b || b2 ){
            tapeMeasure.setPower(-1);
        }else{
            tapeMeasure.setPower(0);
        }

        if( y2 ){
            gripFoundation(y2);
        }else{
            gripFoundation(y);
        }

        if( liftUp2 || liftDown2 ){
            liftGivenControllerValues(liftUp2, liftDown2);
        }else{
            liftGivenControllerValues(liftUp, liftDown);
        }


        if( gamepad1.right_stick_button ){
            mode = mode.getNext();
        }

        if( mode == DRIVING.Slow ){
            sums = drive(linear / 2, rotation / 2, side / 2); // slow driving
        }else if( mode == DRIVING.Fast ){
            sums = drive(linear, rotation, side); // fast driving
        }

        double angle = turner.getCurrentAngle();

        telemetry.addData("Angle: ", angle);
    }
}

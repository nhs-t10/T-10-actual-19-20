package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TeleOp")
public class DriveTeleOp extends Library{
    private boolean subroutine;
    private boolean intakeUp = true;

    public void init(){
        hardwareInit();
        driveInit();
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

        gripStone(x2);

        if(x && intakeUp){
            lowerIntake(false);
            intakeUp = false;
        }else if(x){
            lowerIntake(true);
            intakeUp = true;
        }

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

        if( intakeIn2 !=0  || intakeOut2 != 0){
            intake(intakeIn2, intakeOut2);
        }else{
            intake(intakeIn, intakeOut);
        }

        if( gamepad1.right_stick_button ){
            mode = mode.getNext();
        }

        if( mode == DRIVING.Slow ){
            sums = drive(linear / 2, rotation / 2, side / 2); // slow driving
        }else if( mode == DRIVING.Fast ){
            sums = drive(linear, rotation, side); // fast driving
        }

        telemetry.addData("Drive mode: ", mode);
        telemetry.addData("Front Left: ", sums[0]);
        telemetry.addData("Front Right: ", sums[1]);
        telemetry.addData("Back Left: ", sums[2]);
        telemetry.addData("Back Right: ", sums[3]);
        telemetry.addData("Values: ", linear + "\n " + rotation + "\n " + side);
    }
}
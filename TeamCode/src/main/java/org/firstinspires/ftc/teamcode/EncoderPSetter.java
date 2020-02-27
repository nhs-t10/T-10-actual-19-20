package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Encoder Setter")
public class EncoderPSetter extends Library{
    private boolean subroutine;
    private boolean intakeUp = true;
    EncoderDriving encoderDrive;
    float error;

    public void init(){
        hardwareInit();
        driveInit();
        encoderDrive = new EncoderDriving();
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
        boolean right_bumper = gamepad1.right_bumper;
        boolean left_bumper = gamepad1.left_bumper;
        boolean liftUp2 = gamepad2.right_bumper;
        boolean liftDown2 = gamepad2.left_bumper;

        //Intake controls | Both gamepads
        float right_trigger = gamepad1.right_trigger;
        float left_trigger = gamepad1.left_trigger;
        float intakeIn2 = gamepad2.right_trigger;
        float intakeOut2 = gamepad2.left_trigger;

        //Movement inputs
        float linear = gamepad1.left_stick_y; //Forward and back
        float side = gamepad1.left_stick_x; //Right and left
        float rotation = gamepad1.right_stick_x * .5f; //Rotating in place

        //If controller two gives any commands (true) than the robot will use those inputs
        //Otherwise, it will use the inputs of controller one

        if( x ){
            encoderDrive.setP(encoderDrive.getP() + .001f);
        }

        if( y ){
            encoderDrive.setP(encoderDrive.getP() - .001f);
        }

        if( a ){
            error = encoderDrive.encoderDrive(5);
        }else{
            error = 13131313;
        }

        if( left_bumper ){
            encoderDrive.setRPCM(encoderDrive.getRPCM() + 10);
        }

        if( right_bumper ){
            encoderDrive.setRPCM(encoderDrive.getRPCM() - 10);
        }

        sums = drive(linear / 2, rotation / 2, side / 2);

        telemetry.addData("Error: ", error);
        telemetry.addData("P: ", encoderDrive.getP());
        telemetry.addData("RPCM: ", encoderDrive.getRPCM());
        telemetry.addData("Started: ", encoderDrive.getStarted());
    }
}
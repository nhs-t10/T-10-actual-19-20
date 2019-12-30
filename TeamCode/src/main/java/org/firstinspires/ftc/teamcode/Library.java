package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;

public abstract class Library extends OpMode {
    // Declare hardware devices
    public static DcMotor frontLeft, frontRight, backLeft, backRight, intakeOne, intakeTwo, lift;
    public static Servo platform, grabber;
    public static CRServo rotateGrabber;
    public static VoltageSensor voltageSensor;
    public static TouchSensor front1, front2;
    public static ColorSensor color;
    private static final int TRACTION_SCALER = 1;//temp value will be changed // Used in driveForEncoders/slideForEncoders
    // Initialize hardware devices and their zero behavior

    public DRIVING mode;

    public enum DRIVING { Slow, Medium, Fast;
        public DRIVING getNext() {
            return values()[(ordinal() + 1) % values().length];
        } // change driving mode
    }

    public void hardwareInit() {
        frontLeft = hardwareMap.dcMotor.get("m0");
        frontRight = hardwareMap.dcMotor.get("m1");
        backLeft = hardwareMap.dcMotor.get("m2");
        backRight = hardwareMap.dcMotor.get("m3");

        lift = hardwareMap.dcMotor.get("l0");
        intakeOne = hardwareMap.dcMotor.get("l1");
        intakeTwo = hardwareMap.dcMotor.get("l2");

        platform = hardwareMap.servo.get("s0");
        grabber = hardwareMap.servo.get("s1");
        rotateGrabber = hardwareMap.crservo.get("s2");

        color = hardwareMap.colorSensor.get("color1");
        front1 = hardwareMap.touchSensor.get("touch1");
        front2 = hardwareMap.touchSensor.get("touch2");

        //voltageSensor = hardwareMap.voltageSensor.get("vs1");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        mode = DRIVING.Fast;
    }

    public static float getVoltage() {
        //return (float) voltageSensor.getVoltage();
        return 1;
    }

    public static void driveUntil(boolean sensor, int l, int r, int s) {
        if (!sensor){
            drive(l, r, s);
        }

        else{
            drive(0, 0, 0);
        }
    }

    //Each method below uses inputs to dictate the robot's actions
    //(i.e grip, which determines weather the robot should grab or not)
    public static void intake(boolean a, boolean b) {
        double num = 0.0;

        if (a) {
            num = .5;
        }

        if (b){
            num = -.5;
        }

        intakeOne.setPower(num);
        intakeTwo.setPower(num);
    }

    public static void grip(boolean x) {
        if(x){
            grabber.setPosition(1);
        } else{
            grabber.setPosition(0);
        }
    }

    public static void platform(boolean y) {
        if (y){
            platform.setPosition(1);
        } else{
            platform.setPosition(0);
        }
    }

    public static void lift(boolean up, boolean down) {
        if (up){
            lift.setPower(.5);
        }

        if (down){
            lift.setPower(-.5);
        }

        if (!up && !down){
            lift.setPower(0);
        }
    }

    /*public static void gRotate(float left, float right) {
        if(right > left){
            rotateGrabber.setPower(right);
        } else if(left > right){
            rotateGrabber.setPower(-left);
        } else {
            rotateGrabber.setPower(0);
        }
    }*/

    //Drive is the central movement and robot handling method of the code
    //Its parameters are l (forward component), r (rotational component), and s (skating component)
    //The method creates a list of the sums of each parameter multiplied by the i index of the
    //forward, rotational and horizontal multiplier arrays
    //Any resulting values above .9 are rounded down to .9 (any higher value might cause the robot
    //to crash) and used to set the power of each of the motors
    public static void drive(float l, float r, float s) {
        float[] sums = new float[4];
        float[] forwardMultiplier = {-1f, 1f, -1f, 1f};
        float[] rotationalMultiplier = {1f, 1f, 1f, 1f};
        float[] horizontalMultiplier = {1f, 1f, -1f, -1f};

        for (int i = 0; i < 4; i++){
            sums[i] += forwardMultiplier[i] * l + rotationalMultiplier[i] * r + horizontalMultiplier[i] * s;
        }

        for (int i = 0; i < 4; i++){
            if (sums[i] > .9){
                sums[i] = .9f;
            }
        }

        frontLeft.setPower(sums[0]);
        frontRight.setPower(sums[1]);
        backLeft.setPower(sums[2]);
        backRight.setPower(sums[3]);
        /*telemetry.addData("Front Left", sums[0]);
        telemetry.addData("Front Right", sums[1]);
        telemetry.addData("Back Left", sums[2]);
        telemetry.addData("Back Right", sums[3]);
        */
    }

    //drive method for auto using encoders
    //float scalar to chose direction+power
    //float distance in CM is the magnitude of the distance traveled forwards or backwards
    //use this method if and only if no other sensors can be used to complete the motion
    public static void driveForEncoders(float distanceInCM, float scalar) {
        float startPosition = backLeft.getCurrentPosition();
        while (Math.abs(backLeft.getCurrentPosition()) < (distanceInCM / 31.9f) * 1120f + startPosition) {//31.9 is scuffed
            drive(scalar, 0, 0);
        }
        drive(0, 0, 0);
    }
    //drive method for auto using encoders
    //float scalar to chose direction+power
    //float distance in CM is the magnitude of the distance traveled left or right
    //use this method if and only if no other sensors can be used to complete the motion
    public static void slideForEncoders(float distanceInCM, float scalar) {
        float startPosition = backLeft.getCurrentPosition();
        while (Math.abs(startPosition - backLeft.getCurrentPosition()) < (distanceInCM / 31.9f) * 1120f * TRACTION_SCALER + startPosition){
            drive(0, 0, scalar);
        }

        drive(0, 0, 0);
    }

}
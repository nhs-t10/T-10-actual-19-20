package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

public abstract class Library extends OpMode{
    // mm the lift moves for each rotation of the lift motor
    // TODO: measured as the diameter of the spool
    final static int MM_PER_LIFT_ROTATION = 1;
    private static final int TRACTION_SCALER = 1; //temp value will be changed // Used in driveForEncoders/slideForEncoders
    // Declare hardware devices
    public static DcMotor frontLeft, frontRight, backLeft, backRight, intakeOne, intakeTwo, lift;
    public static CRServo rotateGrabber;
    public static Servo platform,grabber;
    public static VoltageSensor voltageSensor;
    // Initialize hardware devices and their zero behavior
    public static TouchSensor front1, front2;
    public static ColorSensor color;
    public static DistanceSensor distance;
    public DRIVING mode;

    // the rotation of the encoders is measured in steps
    final static int ENCODER_STEPS_PER_ROTATION = 1120;

    // mm the lift moves for each rotation of the lift motor
    // TODO: measured as the diameter of the spool

    public enum DRIVING{
        Slow, Medium, Fast;

        public DRIVING getNext(){
            return values()[( ordinal() + 1 ) % values().length];
        } // change driving mode
    }

    public void hardwareInit(){
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
        distance = hardwareMap.get(DistanceSensor.class, "distance1");
        front1 = hardwareMap.touchSensor.get("touch1");
        front2 = hardwareMap.touchSensor.get("touch2");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //liftGivenControllerValues.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //liftGivenControllerValues.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        mode = DRIVING.Fast;
    }

    public static void driveUntil( boolean sensor, int l, int r, int s ){
        if( !sensor ){
            drive(l, r, s);
        }else{
            drive(0, 0, 0);
        }
    }

    //Each method below uses inputs to dictate the robot's actions
    //(i.e gripSkystone, which determines weather the robot should grab or not)
    /*public static void intake( boolean a, boolean b ){
        double num = 0.0;

        if( a ){
            num = .5;
        }
        if( b ){
            num = -.5;
        }

        intakeOne.setPower(num);
        intakeTwo.setPower(num);
    }*/

    public static void gripStone( boolean x ){
        if( x ){
            grabber.setPosition(1);
        }else{
            grabber.setPosition(0);
        }
    }

    public static void gripFoundation( boolean y ){
        if( y ){
            platform.setPosition(1);
        }else{
            platform.setPosition(0);
        }
    }

    public static void liftGivenControllerValues( boolean up, boolean down ){
        if( up ){
            lift.setPower(.5);
        }
        if( down ){
            lift.setPower(-.5);
        }
        if( !up && !down ){
            lift.setPower(0);
        }
    }

    /*public static void gripRotate( float left, float right ){
        if( right > left ){
            rotateGrabber.setPower(right);
        }else if( left > right ){
            rotateGrabber.setPower(-left);
        }else{
            rotateGrabber.setPower(0);
        }
    }*/

    //Drive is the central movement and robot handling method of the code
    //Its parameters are l (forward component), r (rotational component), and s (skating component)
    //The method creates a list of the sums of each parameter multiplied by the i index of the
    //forward, rotational and horizontal multiplier arrays
    //Any resulting values above .9 are rounded down to .9 (any higher value might cause the robot
    //to crash) and used to set the power of each of the motors
    public static void drive( float l, float r, float s ){
        float[] sums = new float[4];
        float[] forwardMultiplier = { -1f, 1f, -1f, 1f };
        float[] rotationalMultiplier = { 1f, 1f, 1f, 1f };
        float[] horizontalMultiplier = { 1f, 1f, -1f, -1f };

        for( int i = 0; i < 4; i++ ){
            sums[i] += forwardMultiplier[i] * l + rotationalMultiplier[i] * r + horizontalMultiplier[i] * s;
        }

        for( int i = 0; i < 4; i++ ){
            if( sums[i] > .9 ){
                sums[i] = .9f;
            }
        }

        frontLeft.setPower(sums[0]);
        frontRight.setPower(sums[1]);
        backLeft.setPower(sums[2]);
        backRight.setPower(sums[3]);
        /* telemetry.addData("Front Left", sums[0]);
        telemetry.addData("Front Right", sums[1]);
        telemetry.addData("Back Left", sums[2]);
        telemetry.addData("Back Right", sums[3]); */
    }

    public static void encodersInit()//slap this in the init of classes that wanna use encoders
    {
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public static Float getEncoderValue()
    {
        return ( backLeft.getCurrentPosition() + frontLeft.getCurrentPosition() + frontRight.getCurrentPosition() + backRight.getCurrentPosition() ) / 4f;
    }

    /*public static void strafeForEncoders( float distanceInMM, boolean sensor ){

        float startPosition = backLeft.getCurrentPosition();
        float num = distanceInMM;

        while( ( Math.abs(startPosition - ( backLeft.getCurrentPosition() + backRight.getCurrentPosition() - frontRight.getCurrentPosition() - frontLeft.getCurrentPosition() ) / 4f) < ( ( distanceInMM / 31.9f ) * 10 ) * 1120f * TRACTION_SCALER + startPosition ) && !sensor ){
            drive(0, 0, -.5f * Math.abs(num) / distanceInMM);
            if( startPosition - backLeft.getCurrentPosition() < ( ( distanceInMM / 31.9f ) * 10 ) * 1120f * TRACTION_SCALER + startPosition - ( distanceInMM * .20 ) ){
                num *= .95;
            }
        }

        drive(0, 0, 0);
    }*/


    //it does what you think it does
    //returns the percentage of the destination its at
    //75 100 85 .6 95 .3
    //how to use:
    /*
    int SCALAR = 1;
     */
    public static float encodersDriveForButNoLoops(float startPos, float goalPos, float scalar){
        if(goalPos == 0)
            return 1f;

        drive(scalar, scalar, scalar);
        return (getEncoderValue() - startPos) / goalPos;
    }

    //tells if under bridge or not (basic version)
    public static boolean isUnderBridge(int gray, boolean blue)
    {
        int minColor = (int)(gray*1.3);
        return (color.blue() > minColor && blue) || (color.red() > minColor && !blue);
    }


    // TODO: make this use a PID controller

    /**
     * needs to be called every time through loop
     *
     * param motor    the target motor that will be rotating
     * param finalPos desired final rotation of the motor in encoder steps, can be positive or negative
     **/

    /*public static void rotateMotorToPosition(DcMotor motor, float finalPos){

        // TODO: may cause overshooting, should probably be changed
        if( finalPos > 0 && motor.getCurrentPosition() < finalPos ){
            motor.setPower(0.9);
        }else if( finalPos < 0 && motor.getCurrentPosition() > finalPos ){
            motor.setPower(-0.9);
        }
    }*/

    public static void rotateFor(int degreesInRadians)
    {
        float start = getEncoderValue();
        if(degreesInRadians>getEncoderValue()-getEncoderValue())
        {
            drive(0,(Math.abs(degreesInRadians))/(degreesInRadians),0);
        }
    }

    /**
     * moves the stone lift to a target position
     *
     * @param finalPos target lift position in mm
     */
}
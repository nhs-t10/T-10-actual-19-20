package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;

import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

public abstract class Library extends OpMode{
    // Orient phone matrix
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false;
    private static final String VUFORIA_KEY = "AUlk7Uf/////AAABmQgYG9UqckAptIBX1t3NyKw2UKIX1soSTNHPNtD0M7fh+tziRX+LBq3QsczDz4ZOIPVhTBSHiN2wfr7iJnQgMHgs4JyyxcrfeMfVUY5QB5JvwovcRntoojMFvLXX4SCRPTeA6rIADVqyJSBEqjFiy8CoU2cdxBZUvSDue69pgWdd5wvD07Ezt1NJ7OHHa8qCZdF/4f9I5wljgAGS2CmXg8IV6bgrC7K1cFDzGlPLH4Zmhlv0fkoC58wD82dEPxkLCVE2098RGFZiM36yb8IgSniPiaHwl1XH7Swnpzd9086Y+vs+ak1JMhgg3UUQqc1pQFt5xCCXV3FmNZ1Vn2knbcPtTo8IWRP3ZjFa5IGqoft4";

    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // We will define some constants and conversions here
    private static final float mmPerInch        = 25.4f;
    private static final float mmTargetHeight   = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Constant for Stone Target
    private static final float stoneZ = 2.00f * mmPerInch;

    // Constants for the center support targets
    private static final float bridgeZ = 6.42f * mmPerInch;
    private static final float bridgeY = 23 * mmPerInch;
    private static final float bridgeX = 5.18f * mmPerInch;
    private static final float bridgeRotY = 59;                                 // Units are degrees
    private static final float bridgeRotZ = 180;

    // Constants for perimeter targets
    private static final float halfField = 72 * mmPerInch;
    private static final float quadField  = 36 * mmPerInch;

    // Class Members
    private static List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
    private static OpenGLMatrix lastLocation = null;
    private static VuforiaLocalizer vuforia = null;
    private static boolean targetVisible = false;
    private float phoneXRotate    = 0;
    private float phoneYRotate    = 0;
    private float phoneZRotate    = 0;
    
    // mm the lift moves for each rotation of the lift motor
    // TODO: measured as the diameter of the spool
    final static int MM_PER_LIFT_ROTATION = 1;
    private static final int TRACTION_SCALER = 1; //temp value will be changed // Used in driveForEncoders/slideForEncoders
    // Declare hardware devices
    public static DcMotor frontLeft, frontRight, backLeft, backRight, intakeOne, intakeTwo, lift;
    public static CRServo rotateGrabber;
    public static Servo platform,grabber, intake1, intake2;
    public static VoltageSensor voltageSensor;
    // Initialize hardware devices and their zero behavior
    public static TouchSensor front1, front2;
    public static ColorSensor color;
    public static DistanceSensor distance;
    public DRIVING mode;

    //TEST
    imuData imu;
    Turning turner;


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

//        lift = hardwareMap.dcMotor.get("l0");
//        intakeOne = hardwareMap.dcMotor.get("l1");
//        intakeTwo = hardwareMap.dcMotor.get("l2");

//        platform = hardwareMap.servo.get("s0");
//        grabber = hardwareMap.servo.get("s1");
//        rotateGrabber = hardwareMap.crservo.get("s2");

//        color = hardwareMap.colorSensor.get("color1");
        distance = hardwareMap.get(DistanceSensor.class, "distance1");

        front1 = hardwareMap.touchSensor.get("touch1");
        front2 = hardwareMap.touchSensor.get("touch2");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //liftGivenControllerValues.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //liftGivenControllerValues.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //intakeOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //intakeTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        mode = DRIVING.Fast;


        //TEST
        //imu = new imuData(hardwareMap);
        //turner = new Turning();
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
    public static void intake( boolean a, boolean b ){
        double num = 0.0;

        if( a ){
            num = .5;
        }
        else if( b ){
            num = -.5;
        }
        else{
            num = 0;
        }

        intakeOne.setPower(num);
        intakeTwo.setPower(num);
    }

    public static void lowerIntake( boolean x ){
        if( x ){
            intake1.setPosition(1);
            intake2.setPosition(1);
        }else{
            intake1.setPosition(0);
            intake2.setPosition(0);
        }
    }

//    public static void gripStone( boolean x ){
//        if( x ){
//            grabber.setPosition(1);
//        }else{
//            grabber.setPosition(0);
//        }
//    }

//    public static void gripFoundation( boolean y ){
//        if( y ){
//            platform.setPosition(1);
//        }else{
//            platform.setPosition(0);
//        }
//    }

//    public static void liftGivenControllerValues( boolean up, boolean down ){
//        if( up ){
//            lift.setPower(.5);
//        }
//        if( down ){
//            lift.setPower(-.5);
//        }
//        if( !up && !down ){
//            lift.setPower(0);
//        }
//    }

    /*public static void gripRotate( float left, float right ){
        if( right > left ){
            rotateGrabber.setPower(right);
        }else if( left > right ){
            rotateGrabber.setPower(-left);
        }else{
            rotateGrabber.setPower(0);
        }
}*/

    public static void driveInit(){
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    //Drive is the central movement and robot handling method of the code
    //Its parameters are l (forward component), r (rotational component), and s (skating component)
    //The method creates a list of the sums of each parameter multiplied by the i index of the
    //forward, rotational and horizontal multiplier arrays
    //Any resulting values above .9 are rounded down to .9 (any higher value might cause the robot
    //to crash) and used to set the power of each of the motors
    public static float[] drive( float l, float r, float s ){
        s = -s; //sideways is inversed
        float[] sums = new float[4];
        float[] forwardMultiplier = { -1f, 1f, -1f, 1f };
        float[] rotationalMultiplier = { 1f, 1f, 1f, 1f };
        float[] horizontalMultiplier = { 1f, 1f, -1f, -1f };

        for( int i = 0; i < 4; i++ ){
            sums[i] += forwardMultiplier[i] * l + rotationalMultiplier[i] * r + horizontalMultiplier[i] * s;
        }

        for( int i = 0; i < 4; i++ ){
            if( sums[i] > 1 ){
                sums[i] = 1f;
            }else if( sums[i] < -1 ){
                sums[i] = -1f;
            }
        }

        frontLeft.setPower(sums[0]);
        frontRight.setPower(sums[1]);
        backLeft.setPower(sums[2]);
        backRight.setPower(sums[3]);
        return sums;
    }

//    public static void encodersInit()//slap this in the init of classes that wanna use encoders
//    {
//        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//    }

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
package org.firstinspires.ftc.teamcode;
import java.util.ArrayList;
import java.util.List;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
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
    private static final boolean PHONE_IS_PORTRAIT = true;
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
    public static DcMotor frontLeft, frontRight, backLeft, backRight, liftLeft, liftRight;
    public static CRServo tapeMeasure;
    public static Servo foundationLeft, foundationRight, grabber1, grabber2;
    public static VoltageSensor voltageSensor;
    // Initialize hardware devices and their zero behavior
    public static ColorSensor color;
    public static DistanceSensor distanceLeft, distanceRight;
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
        driveInit();

        liftLeft = hardwareMap.dcMotor.get("l0");
        liftRight = hardwareMap.dcMotor.get("l1");

        grabber1 = hardwareMap.servo.get("s0");
        grabber2 = hardwareMap.servo.get("s1");
        foundationRight = hardwareMap.servo.get("s2");
        foundationLeft = hardwareMap.servo.get("s3");
        tapeMeasure = hardwareMap.crservo.get("s4");

        color = hardwareMap.get(ColorSensor.class, "color0");
        distanceLeft = hardwareMap.get(DistanceSensor.class, "distance0");
        distanceRight = hardwareMap.get(DistanceSensor.class, "distance1");
        //front1 = hardwareMap.touchSensor.get("touch1");
        //front2 = hardwareMap.touchSensor.get("touch2");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        mode = DRIVING.Fast;
    }

    public void vuforiaInit(){
        //TEST
        //imu = new imuData(hardwareMap);
        //turner = new Turning();

        /* Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below). */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection   = CAMERA_CHOICE;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the data sets for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        VuforiaTrackables targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

        VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");
        VuforiaTrackable blueRearBridge = targetsSkyStone.get(1);
        blueRearBridge.setName("Blue Rear Bridge");
        VuforiaTrackable redRearBridge = targetsSkyStone.get(2);
        redRearBridge.setName("Red Rear Bridge");
        VuforiaTrackable redFrontBridge = targetsSkyStone.get(3);
        redFrontBridge.setName("Red Front Bridge");
        VuforiaTrackable blueFrontBridge = targetsSkyStone.get(4);
        blueFrontBridge.setName("Blue Front Bridge");
        VuforiaTrackable red1 = targetsSkyStone.get(5);
        red1.setName("Red Perimeter 1");
        VuforiaTrackable red2 = targetsSkyStone.get(6);
        red2.setName("Red Perimeter 2");
        VuforiaTrackable front1 = targetsSkyStone.get(7);
        front1.setName("Front Perimeter 1");
        VuforiaTrackable front2 = targetsSkyStone.get(8);
        front2.setName("Front Perimeter 2");
        VuforiaTrackable blue1 = targetsSkyStone.get(9);
        blue1.setName("Blue Perimeter 1");
        VuforiaTrackable blue2 = targetsSkyStone.get(10);
        blue2.setName("Blue Perimeter 2");
        VuforiaTrackable rear1 = targetsSkyStone.get(11);
        rear1.setName("Rear Perimeter 1");
        VuforiaTrackable rear2 = targetsSkyStone.get(12);
        rear2.setName("Rear Perimeter 2");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        allTrackables.addAll(targetsSkyStone);

        /* In order for localization to work, we need to tell the system where each target is on the field, and
           where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
           Transformation matrices are a central, important concept in the math here involved in localization.
           See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
           for detailed information. Commonly, you'll encounter transformation matrices as instances
           of the {@link OpenGLMatrix} class.

           If you are standing in the Red Alliance Station looking towards the center of the field,
               - The X axis runs from your left to the right. (positive from the center to the right)
               - The Y axis runs from the Red Alliance Station towards the other side of the field
                 where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
               - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)

           Before being transformed, each target image is conceptually located at the origin of the field's
           coordinate system (the center of the field), facing up */

        /* Set the position of the Stone Target.  Since it's not fixed in position, assume it's at the field origin.
           Rotated it to to face forward, and raised it to sit on the ground correctly.
           This can be used for generic target-centric approach algorithms */

        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //Set the position of the bridge support targets with relation to origin (center of field)
        blueFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, bridgeRotZ)));

        blueRearBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, bridgeRotZ)));

        redFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, 0)));

        redRearBridge.setLocation(OpenGLMatrix
                .translation(bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, 0)));

        //Set the position of the perimeter targets with relation to origin (center of field)
        red1.setLocation(OpenGLMatrix
                .translation(quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        red2.setLocation(OpenGLMatrix
                .translation(-quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        front1.setLocation(OpenGLMatrix
                .translation(-halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90)));

        front2.setLocation(OpenGLMatrix
                .translation(-halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

        blue1.setLocation(OpenGLMatrix
                .translation(-quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        blue2.setLocation(OpenGLMatrix
                .translation(quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        rear1.setLocation(OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , -90)));

        rear2.setLocation(OpenGLMatrix
                .translation(halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        // Create a transformation matrix describing where the phone is on the robot.
        if (CAMERA_CHOICE == BACK)
            phoneYRotate = -90;
        else
            phoneYRotate = 90;

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT)
            phoneXRotate = 90;

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        final float CAMERA_FORWARD_DISPLACEMENT  = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot center
        final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables)
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);

        targetsSkyStone.activate();
    }

    public static void driveUntil( boolean sensor, int l, int r, int s ){
        if( !sensor ){
            drive(l, r, s);
        }else{
            drive(0, 0, 0);
        }
    }

    public static void gripStone( boolean x ){
        if( x ){
            grabber1.setPosition(1);
            grabber2.setPosition(1);
        }else{
            grabber1.setPosition(0);
            grabber2.setPosition(0);
        }
    }

        public static void gripFoundation( boolean y ){
            if( y ){
                foundationRight.setPosition(1);
                foundationLeft.setPosition(1);
            }else{
                foundationRight.setPosition(0);
                foundationLeft.setPosition(0);
            }
        }

    public static void liftGivenControllerValues( boolean up, boolean down ){
        if( up ){
            liftLeft.setPower(.5);
            liftRight.setPower(-.5);
        }
        if( down ){
            liftLeft.setPower(-.5);
            liftRight.setPower(.5);
        }
        if( !up && !down ){
            liftLeft.setPower(0);
            liftRight.setPower(0);
        }
    }

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
    public static float[] drive(float l, float r, float s){
        s = -s; //sideways is inverted
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

    public static void rotateFor(float degreesInRadians)
    {
        float start = getEncoderValue();
        if(degreesInRadians>getEncoderValue() - start)
        {
            drive(0,(Math.abs(degreesInRadians))/(degreesInRadians),0);
        }
    }

    public static boolean isSkystoneVisible()
    {
        targetVisible = false;
        for (VuforiaTrackable trackable : allTrackables)
        {
            if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible())
            {
                targetVisible = true;

                // getUpdatedRobotLocation() will return null if no new information is available since
                // the last time that call was made, or if the trackable is not currently visible.
                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null)
                    lastLocation = robotLocationTransform;

                break;
            }

            // Provide feedback as to where the robot is located (if we know).
            if (targetVisible)
            {
                // express position (translation) of robot in inches.
                VectorF translation = lastLocation.getTranslation();

                // express the rotation of the robot in degrees.
                Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
            }
        }

        return ((VuforiaTrackableDefaultListener) allTrackables.get(0).getListener()).isVisible();
    }

    /**
     * moves the stone lift to a target position
     *
     * @param finalPos target lift position in mm
     */
}
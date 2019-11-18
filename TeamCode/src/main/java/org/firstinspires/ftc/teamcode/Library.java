package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;



import java.util.*;

public abstract class Library extends OpMode {
    // Declare Hardware Devices
    public static DcMotor frontLeft, frontRight, backLeft, backRight, intakeOne, intakeTwo, lift;
    public static VoltageSensor voltSensor;
    //Blinkin needs to be defined as a servo to read data
    public static Servo platform, grabber;
    public static CRServo blinkin, rotateGrabber;
    //public static LED blinkin;
    //Blinkin needs to be defined as a servo to read data
    //public static SonarSensor sonarSensor;

    // Declare initializing method
    public void hardwareInit() {
        frontLeft = hardwareMap.dcMotor.get("m0");
        frontRight = hardwareMap.dcMotor.get("m1");
        backLeft = hardwareMap.dcMotor.get("m2");
        backRight = hardwareMap.dcMotor.get("m3");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift = hardwareMap.dcMotor.get("l0");
        intakeOne = hardwareMap.dcMotor.get("l1");
        intakeTwo = hardwareMap.dcMotor.get("l2");


        platform = hardwareMap.servo.get("s0");
        grabber = hardwareMap.servo.get("s1");
        //rotateGrabber = hardwareMap.crservo.get("s2");
        //blinkin = hardwareMap.crservo.get("s2");

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        //sonar = hardwareMap.sonarSensor.get("s1");
        //stoneLift = hardwareMap.dcMotor.get("sL");


        //Safety Check: run through the list of voltage sensors; if any of them are below the minimum voltage, exit.
		for (VoltageSensor voltageSensor : hardwareMap.voltageSensor) {
			voltSensor = voltageSensor;
			if (voltageSensor.getVoltage() < WARNING_BATTERY_VOLTAGE) telemetry.addData("WARNING: ", "BATTERY LOW");
			if (voltageSensor.getVoltage() < REPLACE_BATTERY_VOLTAGE) telemetry.addData("WARNING: ", "BATTERY VERY LOW; REPLACE IMMEDIATELY");
		}
    }
    // Declare other helper methods

    // Constants
    static float attenuationfactor;
    static double initial_position = 0;
    static double moveRate = .005;
    static boolean servosMoving = false;
    static int SAMPLES_PER_SECOND = 10;
    static double REPLACE_BATTERY_VOLTAGE = 10;
    static double WARNING_BATTERY_VOLTAGE = 11;

//    public DRIVING mode;
//    public enum DRIVING { Slow, Medium, Fast;
//        public DRIVING getNext() {
//            return values()[(ordinal() + 1) % values().length];
//        } // change driving mode
//    }

    public static float maxValue(float array[]) {
        float max = 0f;
        for (float i : array) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }
	/*public void battery(){
		voltSensor.getVoltage();
		telemetry.addData("Initial Battery: ", voltSensor);
		try {
			if (voltSensor != voltSensor){
				telemetry.addData("Battery Level: ", voltSensor);
			}
		} catch(Exception ex) {}
	}*/

    /*drive is the central movement and robot handling method of the code
    It takes in the linear (forward) component, the rotational (turning) component, and
    the side(skating) component.
    It creates arrays for each of the components, and then adds them all together.
    After finding the highest value, it makes sure than everything is below 1.
    If any values are above 1, it divides all the sums by 1, else it divides by the highest value.
    It then sends the values from the modified sums array to the actual motors, with the code
    having numbers attached to them to account for proper rotation.
    */
    public static void drive(float l, float r, float s, float intake) {
        float[] sums = new float[4];
        if (l > -0.1 && l < 0.1 && r > -0.1 && r < 0.1 && s > -0.1 && s < 0.1) {
            sums[0] = 0;
            sums[1] = 0;
            sums[2] = 0;
            sums[3] = 0;
        }
        /*
        drive-driving function.
        @param: l, linear component, r, rotational component, and s, horizontal component
         */
        float[] forwardMultiplier = {-1f, 1f, -1f, 1f};
        float[] rotationalMultiplier = {1f, 1f, 1f, 1f};
        float[] horizontalMultiplier = {1f, 1f, -1f, -1f};

        for (int i = 0; i < 4; i++) {
            sums[i] += forwardMultiplier[i] * l + rotationalMultiplier[i] * r + horizontalMultiplier[i] * s;
        }

        float highest = maxValue(sums);

        if (Math.abs(highest) > 1) {
            attenuationfactor = highest;
        } else {
            attenuationfactor = 1f;
        }

        for (int i = 0; i < 4; i++) {
            sums[i] = sums[i] / attenuationfactor;
        }
        intakeOne.setPower(intake);
        intakeTwo.setPower(intake);
        float speed = 0.9f; //set speed of driving, speed of 1 was tested and would occasionally crash robot while turning
        frontLeft.setPower(speed * sums[0]);
        frontRight.setPower(speed * sums[1]);
        backLeft.setPower(speed * sums[2]);
        backRight.setPower(speed * sums[3]);
    }

    //takes in distance in centimeters, drives until it hits that distance
    //this method is wack rn idk if it works lol
    /*
     *Parameter(s): float cM, String mode "turn", float degree(optional), float directionSideways (optional)
     *cM: distance you want it to travel in centimeters
     *mode: either forwards, sideways, or turning (this will control what the thing does)
     *degree: if you are turning fill this in, otherwise it will go straight, this will determine what angle you want the robot to turn
     *directionSideways: also a degree, no radians PAUL, it determines which diagonal path the robot takes if we want to be extra like that
     */
    public static void driveFor(float distanceInCM, float l, float r, float s) {
        float startPosition = backLeft.getCurrentPosition();
        float rotations = (distanceInCM / 31.9f) * 1120f;
        //According to website, 1120 ticks per revolution
        while (backLeft.getCurrentPosition() < rotations + startPosition) {
            drive(l, r, s, 0);
        }

        drive(0, 0, 0, 0);
    }

    //this is the second attempt
    //improved drive for
    public static void driveForReformed(float distanceInCM, float l, float r, float s, float degreesToTurn)
    {
        if(s!=0)
        {
            float startPosition = backLeft.getCurrentPosition();
            float rotations = (distanceInCM / 31.9f) * 1120f;
            //According to website, 1120 ticks per revolution
            while(true) {
                drive(l, r, s, 0);
            }
        }else if(r!=0)
        {
            //matt do your thing
        }else
        {
            float startPosition = backLeft.getCurrentPosition();
            float rotations = (distanceInCM / 31.9f) * 1120f;
            //According to website, 1120 ticks per revolution
            while (dealWithNeg(backLeft.getCurrentPosition(), rotations, startPosition)) {
                drive(l, r, s, 0);
            }


        }
        drive(0, 0, 0, 0);
    }

    public static boolean dealWithNeg(float currentValue, float rots, float start)
    {
        if(rots==0)
        {
            return false;
        }else if(rots>0)
        {

            return (currentValue<rots+start);
        }else
        {
            return(-currentValue>rots-start);
        }
    }

    public static float cmIntoRots(float cmImput)
    {
        return cmImput * (1120f/31.9f);
    }

    public static void driveForNeg(float distanceInCM, float l, float r, float s) {
        float startPosition = backLeft.getCurrentPosition();
        float rotations = (distanceInCM / 25.5f) * 1120;
        //According to website, 1120 ticks per revolution
        while (backLeft.getCurrentPosition() > rotations + startPosition) {
            drive(l, r, s, 0);
        }

        drive(0, 0, 0, 0);
    }

    //Param: degrees --> Degrees the robot will turn
    //Robot turns (degrees) degrees
    //Degrees can be pos or neg (pos --> right, neg --> left)
    public static void turnDegrees(int degrees){  
        float wheelToWheelWidth = 0f;  //Length between the two front wheels
        float wheelToWheelLength = 0f;  //Length betweeen front and back wheels

        //It calculates a "circle" that starts in the center of the robot and hits all 4 wheels
        //The arc length for the given degree is the CM the robot turnFor()
        float radius = (float)Math.sqrt((wheelToWheelWidth / 2.0f) * (wheelToWheelWidth / 2.0f) + (wheelToWheelLength / 2.0f) * (wheelToWheelLength / 2.0f));
        float circumference = 2 * (float)Math.PI * radius;
        float turnCM = circumference * ((float)degrees / 360) ;  //arc length of "circle"

        if(degrees < 0){
            driveForNeg(turnCM, 0, .75f, 0);
        }
        else{
            driveFor(turnCM, 0, .75f, 0);
        }
    }

    public static void encodersInit() {//DO NOT TOUCH MY METHODS
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public static int encodersLagQuestionMark(DcMotor one, DcMotor two, DcMotor three, DcMotor four) {
        return (one.getCurrentPosition() + two.getCurrentPosition() + three.getCurrentPosition() + four.getCurrentPosition()) / 4;
    }

    /*
        hardware notes:
        2 motors for lift - need to be in sync, have intervals that it can go to manually (button press, may require encoders)
        1 servo for block grabber - likely continuous (button press)
        */


    public static void platform(boolean down) {
        if (down) {
            platform.setPosition(1);
        } else {
            platform.setPosition(0);
        }
    }

    /**
     * Sets the pattern/color of the Rev Blinkin LED strip based on a pattern number
     * Rev's documentation: http://www.revrobotics.com/content/docs/REV-11-1105-UM.pdf
     * patternNumber Integer [1,100] that represents desired LED pattern or color
     */
    /*public static void setBlinkinPattern(int patternNumber) {
        //map patternNumber value from [1,100] => [0.2525, 0.7475]
        //formula: output = output_start + ((output_end - output_start) / (input_end - input_start)) * (input - input_start)
        double output = 0.2525 + ((0.7475 - 0.2525) / (100 - 1)) * (patternNumber - 1);

        //send mapped value to "servo" (Blinkin)
        blinkin.setPower(output);
    }*/



    public static void gRotate(float left, float right){
        if(right > left){
            rotateGrabber.setPower(right);
       }
        else if(left > right){
            rotateGrabber.setPower(-left);
        }else{
            rotateGrabber.setPower(0);
        }
    }
    public static void lift(float num){
        lift.setPower(num);
    }
    public static void grip(boolean x){
        if(x){
            grabber.setPosition(1);
    }else{
            grabber.setPosition(0);
        }
    }
}
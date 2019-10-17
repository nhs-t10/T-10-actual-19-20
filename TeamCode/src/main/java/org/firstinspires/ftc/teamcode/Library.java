package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.LED;
//import com.qualcomm.robotcore.hardware.SonarSensor;


import java.util.*;

public abstract class Library extends OpMode {
    // Declare Hardware Devices
    public static DcMotor frontLeft, frontRight, backLeft, backRight, liftOne, liftTwo;
    public static VoltageSensor voltSensor;
    //Blinkin needs to be defined as a servo to read data
    public static Servo grabberServo;
    public static CRServo blinkin;
    //public static LED blinkin;
    //Blinkin needs to be defined as a servo to read data
    //public static SonarSensor sonarSensor;

    // Declare initializing method
    public void hardwareInit() {
        frontLeft = hardwareMap.dcMotor.get("m0");
        frontRight = hardwareMap.dcMotor.get("m1");
        backLeft = hardwareMap.dcMotor.get("m2");
        backRight = hardwareMap.dcMotor.get("m3");

        grabberServo = hardwareMap.servo.get("s0");
        blinkin = hardwareMap.crservo.get("s1");


        //sonar = hardwareMap.sonarSensor.get("s1");
        //stoneLift = hardwareMap.dcMotor.get("sL");


        //Safety Check: run through the list of voltage sensors; if any of them are below the minimum voltage, exit.
		/*for (VoltageSensor voltageSensor : hardwareMap.voltageSensor) {
			voltSensor = voltageSensor;
			if (voltageSensor.getVoltage() < WARNING_BATTERY_VOLTAGE) telemetry.addData("WARNING: ", "BATTERY LOW");
			if (voltageSensor.getVoltage() < REPLACE_BATTERY_VOLTAGE) telemetry.addData("WARNING: ", "BATTERY VERY LOW; REPLACE IMMEDIATELY");
		}*/
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
    
    public DRIVING mode;
    public enum DRIVING { Slow, Medium, Fast;
        public DRIVING getNext() {
            return values()[(ordinal() + 1) % values().length];
        } // change driving mode
    }

    public static float maxValue(float array[]) {
        float max = 0f;
        for (float i : array){
            if(i>max){ max = i; }
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

    public static void omni(float l, float r, float s) {
        /*
        Omni-driving function.
        @param: l, linear component, r, rotational component, and s, horizontal component
         */
        float[] forwardMultiplier = {-1f, 1f, -1f, 1f};
        float[] rotationalMultiplier = {1f, 1f, 1f, 1f};
        float[] horizontalMultiplier = {1f, 1f, -1f, -1f};

        //float[] forwardMultiplier = {-1f, 1f, -1f, 1f};
        //float[] rotationalMultiplier = {1f, 1f, 1f, 1f};
        //float[] horizontalMultiplier = {1f, 1f, -1f, -1f};
        float[] forwardComponent = new float[4];
        float[] rotationalComponent = new float[4];
        float[] eastwestComponent = new float[4];

        for (int i = 0; i < 4; i++) {
            forwardComponent[i] = forwardMultiplier[i] * l;
            rotationalComponent[i] = rotationalMultiplier[i] * r;
            eastwestComponent[i] = horizontalMultiplier[i] * s;
        }

        float[] sums = new float[4];
        for(int i=0;i<4;i++){
            sums[i]+=forwardComponent[i]+rotationalComponent[i]+eastwestComponent[i];
        }

        float highest = maxValue(sums);

        if (Math.abs(highest) > 1) { attenuationfactor = highest;
        } else { attenuationfactor = 1f; }

        for (int i = 0; i < 4; i++) {
            sums[i] = sums[i] / attenuationfactor;
        }

        frontLeft.setPower(sums[0]);
        frontRight.setPower(sums[1]);
        backLeft.setPower(sums[2]);
        backRight.setPower(sums[3]);
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
        float startPosition = frontLeft.getCurrentPosition();
		float rotations = distanceInCM / 25.5f;
		while (frontLeft.getCurrentPosition() < rotations + startPosition) {
            omni(l, r, s);
        }
        
        omni(0, 0, 0);
	}

    public static void encodersInit(){//DO NOT TOUCH MY METHODS
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public static int encodersLagQuestionMark(DcMotor one, DcMotor two, DcMotor three, DcMotor four){
        return(one.getCurrentPosition() + two.getCurrentPosition() + three.getCurrentPosition() + four.getCurrentPosition()) / 4;
    }

    //This method allows the robot to turn a certain number of degrees using encoders
   public void turnDegrees(int degrees){  //Degrees can be pos or neg (pos --> right, neg --> left)
        float radius = 30.54607421584f;
        float circumference = 2 * (float)Math.PI * radius;
        float turnCM = circumference * ((float)degrees / 360) ;  //arc length in circle

        if(degrees < 0){
            driveFor(turnCM, 0, -.75f, 0);
        }
        else{
            driveFor(turnCM, 0, .75f, 0);
        }
    }
    

    /*
        hardware notes:
        2 motors for lift - need to be in sync, have intervals that it can go to manually (button press, may require encoders)
        1 servo for block grabber - likely continuous (button press)
        */

//    public static void lift(float zoom){
//        liftOne.setPower(zoom);
//        liftTwo.setPower(zoom);
//    }
    public static void platform(boolean Grab){
        if(Grab){
            grabberServo.setPosition(1);
        }
        else{
            grabberServo.setPosition(0);
        }
    }

    /**
     * Sets the pattern/color of the Rev Blinkin LED strip based on a pattern number
     * Rev's documentation: http://www.revrobotics.com/content/docs/REV-11-1105-UM.pdf
     * @param patternNumber Integer [1,100] that represents desired LED pattern or color
     */
    public static void setBlinkinPattern(int patternNumber) {
        //map patternNumber value from [1,100] => [0.2525, 0.7475]
        //formula: output = output_start + ((output_end - output_start) / (input_end - input_start)) * (input - input_start)
        double output = 0.2525 + ((0.7475 - 0.2525) / (100 - 1)) * (patternNumber - 1);

        //send mapped value to "servo" (Blinkin)
        blinkin.setPower(output);
    }
}

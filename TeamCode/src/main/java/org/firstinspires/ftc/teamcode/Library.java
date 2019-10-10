package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.hardware.SonarSensor;


import java.util.*;

public abstract class Library extends OpMode {
    // Declare Hardware Devices
    public static DcMotor frontLeft, frontRight, backLeft, backRight, liftOne, liftTwo;
    public static VoltageSensor voltSensor;
    public static Servo grabberServo, unknown;
    //public static SonarSensor sonarSensor;

    // Declare initializing method
    public void hardwareInit() {
        frontLeft = hardwareMap.dcMotor.get("m0");
        frontRight = hardwareMap.dcMotor.get("m1");
        backLeft = hardwareMap.dcMotor.get("m2");
        backRight = hardwareMap.dcMotor.get("m3");
        grabberServo = hardwareMap.servo.get("s1");
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


    /*public static void drive(float lf, float rf, float lb, float rb) {
        frontLeft.setPower(-lf);
        frontRight.setPower(rf);
        backLeft.setPower(-lb);
        backRight.setPower(rb);
    }*/
    
    // public static void driveUntil(float distanceInCM) {
    //     float startPosition = frontLeft.getCurrentPosition();
    //     float rotations = distanceInCM / 31.4f * 360;  //360 if its in degrees
    //     while (frontLeft.getCurrentPosition() < rotations + startPosition) {
    //         omni(1, 0, 0);
    //     }
    // }
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
        float[] forwardMultiplier = {1f, 1f, 1f, 1f};
        float[] rotationalMultiplier = {-1f, 1f, -1f, 1f};
        float[] horizontalMultiplier = {-1f, 1f, 1f, -1f};

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

    public static void driveUntil(float cM) {//DO NOT TOUCH MY METHODS
        float startPosition = frontLeft.getCurrentPosition();
        float rotations = (cM/25.5f); //537.6 is because the motor we use has that many ticks per revolutoin shut the hell up matt
        while (frontLeft.getCurrentPosition() < rotations + startPosition) {
            omni(1, 0, 0);
        }        int coastDistance = frontLeft.getCurrentPosition();
        long theTime = System.currentTimeMillis();
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public static void encodersInit(){//DO NOT TOUCH MY METHODS
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /*
        hardware notes:
        2 motors for lift - need to be in sync, have intervals that it can go to manually (button press, may require encoders)
        1 servo for block grabber - likely continuous (button press)
        */
        
//    public static void lift(float zoom){
//        liftOne.setPower(zoom);
//        lifeTwo.setPower(zoom);
//    }
    public static void grab(boolean Grab){
        if(Grab){
            grabberServo.setPosition(1);
        }
        else{
            grabberServo.setPosition(0);
        }
    }
}
/*
// ---------------------------------------------------------
// Class for Proportional Integral Derivative controler
// Goal: reduce jerk in the SBotNick ( ͡~ ͜ʖ ͡° )
// ---------------------------------------------------------

package org.firstinspires.ftc.teamcode;

import android.hardware.SensorEventListener;

public class PID{
    double prevError = 0.0;
    double sumError = 0.0;
    double prevTime = 0.0;
    final double P = 0.3;
    final double D = 0.0;
    final double I = 0.0;
    double dComponent;
    double savedTime;
    double error2;
    double time = getCurrTime();


    enum state {
        IDLE,TURNING,TRAVELING_IN_A_LINEAR_FASHION;
    }

    public double getError(){
        return currentAngle-destination ;
    }

    // method to find the

    public double getCurrTime() {
        return System.currentTimeMillis();
    }

    public void updateTurning(imuData sean) {
        currentAngle = sean.getAngle();
        double error = getError();
        prevTime = getCurrTime();
        pComponent = error * P;
        // Code to make slight delay, in order to avoid dividing by zero error

        dComponent = ((error2 - error) / (time - prevTime)) * D;

        if (currentEvent==state.TURNING) {
            if (getCurrTime()-savedTime>2000) {
                stopTurning();
            }else {
                T10_Library.omni(0f, (float) (pComponent), 0f);
            }
        } else if(currentEvent==state.TRAVELING_IN_A_LINEAR_FASHION){
            T10_Library.omni(0.5f,(float) (pComponent), 0f);
        }
    }

    public void updateMoving(   ){
        currentAngle = sean.getAngle();
        double error = getError();
        prevTime = getCurrTime();
        pComponent = error * P;
        // Code to make slight delay, in order to avoid dividing by zero error
        double error2 = getError();
        double time = getCurrTime();
        dComponent = ((error2 - error) / (time - prevTime)) * D;

        // code to do control movemment of the robot, in order to get to an "ideal state"
    }

    // use getError() to get current angle, and use getCurrTime to get currTime


    // Misc. code from FTC PID forum page:

    // --------------------------------------------------------------
    // "You have two components when driving forward: 1) Overall forward power;
    // 2) Differential power between left and right. You calculate the overall forward
    // power by checking your encoder against its target. So if the target is far away,
    // the overall forward power is large. If you are almost there, the overall forward power
    // would be small. Then the differential power is controlled by the gyro. If the robot is
    // deviated from the center line in a big way (i.e. the error is large), the differential
    // power is big. If you are right on straight, the differential power is zero. With these
    // two components, the wheel powers are:""
    // *mikets - Senior Member
    // --------------------------------------------------------------


    // adjust code to fit distinct variable- also, put this into a methos
    /*
    driveError = distanceTarget - currentEncoder;
    turnError = angleTarget - currentGyroAngle;
    drivePower = driveKp * driveError;
    turnPower = turnKp*turnError;
    leftPower = drivePower + turnPower;
    rightPower = driverPower - turnPower;
*/
//}
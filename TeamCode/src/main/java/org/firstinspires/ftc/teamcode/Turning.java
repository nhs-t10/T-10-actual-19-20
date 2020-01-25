package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Turning{
    Telemetry telemetry;
    double currentAngle;
    double destinationAngle;
    double pComponent;
    state currentEvent;

    //double prevError = 0.0;
    double sumError = 0.0;
    final double P = 0.03;
    double savedTime;

    //Different possible states during turning
    enum state{
        IDLE, TURNING, TRAVELING_IN_A_LINEAR_FASHION
    }

    //Turning object: Has a destination angle and a current event (state)
    public Turning(){
        destinationAngle = 0;
        currentEvent = state.IDLE;
    }

    //Setting the destination in degrees
    public void setDestination( float degrees ){
        savedTime = getCurrTime();

        if( degrees > 180 ){
            destinationAngle = degrees - 360;
        }

        //Otherwise, the destination just becomes the entered degrees
        destinationAngle = degrees;
        currentEvent = state.TURNING;
    }

    /*public void startSkewing()
    {
        destinationAngle = currentAngle;
        currentEvent = state.TRAVELING_IN_A_LINEAR_FASHION;
    }*/


    public double[] updateDrive( imuData imu ){
        //Setting the current angle
        currentAngle = imu.getAngle();

        //Finding the error
        double error = getError();
        pComponent = error * P;
        if( pComponent > .5f ){
            pComponent = .5f;
        }
        if( pComponent < -.5f ){
            pComponent = -.5f;
        }

        double stateTest = 0.0;

        if( currentEvent == state.TURNING ){
            if( getError() < 1 ){
                stopTurning();
            }else{
                Library.drive(0f, (float) pComponent, 0f);
            }

            /*if (error < 1.0)
                stopTurning();
            else
                Library.drive(0f, (float) pComponent, 0f);*/
        }else if( currentEvent == state.TRAVELING_IN_A_LINEAR_FASHION ){
            stateTest = 1.0;
        }

        double[] array = { destinationAngle, stateTest, currentAngle, error };
        return array;
    }

    public void stopTurning(){
        currentEvent = state.IDLE;
        sumError = 0.0;
        Library.drive(0f, 0f, 0f);
    }

    /*public void stopSkewing()
    {
        currentEvent = state.IDLE;
        Library.drive(0,0,0);
    }*/

    public double getError(){
        return currentAngle - destinationAngle;
    }

    public double getCurrTime(){
        return System.currentTimeMillis();
    }
}
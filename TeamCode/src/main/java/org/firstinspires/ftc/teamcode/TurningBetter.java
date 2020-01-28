package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class TurningBetter {
    double currentAngle;
    double destinationAngle;
    double pComponent;
    double angleTurned = 0;
    boolean started = false;

    //double prevError = 0.0;
    double sumError = 0.0;
    final double P = 0.02;
    //double savedTime;

    ElapsedTime clock = new ElapsedTime();

    //Turning object: Has a destination angle
    public TurningBetter(){
        destinationAngle = 0;
    }

    //Setting the destination in degrees
    public void setDestination(imuData imu, float degrees){
        destinationAngle = imu.getAngle() + degrees;

        if( destinationAngle > 180 ){
            destinationAngle -= 360;
        }
        else if (destinationAngle < -180){
            destinationAngle += 360;
        }
    }

    public void updateAndDrive( imuData imu ){
        //Setting the current angle
        currentAngle = imu.getAngle();

        //Finding the error
        double error = currentAngle - destinationAngle;

        /*This turnAngle is so that the robot turns the right direction and considers the
         *rotation system of IMU (-180 to 180)*/
        double turnAngle = - error;
        if( turnAngle > 180 ){
            turnAngle -= 360;
        }
        else if (turnAngle < -180){
            turnAngle += 360;
        }

        //Figuring out the P component
        pComponent = turnAngle * P;
        if( pComponent > .5f ){
            pComponent = .5f;
        }
        if( pComponent < -.5f ){
            pComponent = -.5f;
        }

        //Actual turning
        /*if( Math.abs(error) > 3 ) { //Possibly change to 2
            Library.drive(0f, (float) pComponent, 0f);
        }*/

        if( Math.abs(error) < 3 ) {  //Possibly change to 2
            stopTurning();
        }
        else {
            Library.drive(0f, (float) pComponent, 0f);
        }
    }

    public double turnBetter(imuData imu, int degrees){
        angleTurned = imu.getAngle();
        if(!started){
            started = true;
            clock.reset();
        }

        if( started && clock.seconds() < 1 ){
            setDestination(imu, degrees);
        }
        if( started && clock.seconds() > 1 && clock.seconds() < 10 ){
           updateAndDrive(imu);
        }

        angleTurned = imu.getAngle();
        return clock.seconds();
    }

    public void stopTurning(){
        sumError = 0.0;
        Library.drive(0f, 0f, 0f);
    }
}
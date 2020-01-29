package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Turning {
    double currentAngle;
    double destinationAngle;
    double pComponent;
    double angleTurned = 0;
    final double P = 0.02;

    boolean started = false;
    ElapsedTime clock = new ElapsedTime();

    //imu and Turning objects
    imuData imu;
    Turning turner;

    //Turning object: Has a destination angle
    public Turning(){
        destinationAngle = 0;
    }

    //This method allows turnDegrees() to be called from anywhere without having to
    //define new imuData and Turning objects
    public void initImuAndTurning(HardwareMap hardwareMap) {
        imu = new imuData(hardwareMap);
        imu.initImu();
        turner = new Turning();
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

    public void updateAndDrive() {
        //Setting the current angle
        currentAngle = imu.getAngle();

        //Finding the error
        double error = currentAngle - destinationAngle;

        /*This turnAngle is so that the robot turns the right direction and considers the
         *rotation system of IMU (-180 to 180)*/
        double turnAngle = -error;
        if (turnAngle > 180) {
            turnAngle -= 360;
        } else if (turnAngle < -180) {
            turnAngle += 360;
        }

        //Figuring out the P component
        pComponent = turnAngle * P;
        if (pComponent > .5f) {
            pComponent = .5f;
        }
        if (pComponent < -.5f) {
            pComponent = -.5f;
        }

        //Actual turning
        if (Math.abs(error) > 2) {
            Library.drive(0f, (float) pComponent, 0f);
        }
    }

    public void turnDegrees (int degrees) {
        angleTurned = imu.getAngle();
        if (!started) {
            started = true;
            clock.reset();
        }

        if (started && clock.seconds() < 1) {
            setDestination(imu, degrees);
        }
        else if (started && clock.seconds() < 7) {
            updateAndDrive();
        }

        angleTurned = imu.getAngle();
    }
}
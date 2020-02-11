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

    //imu object
    imuData imu;

    //Turning object: Has a destination angle
    public Turning(){
        destinationAngle = 0;
    }

    public void initImu(HardwareMap hardwareMap) {
        imu = new imuData(hardwareMap);
        imu.initImu();
    }

    //Setting the destination in degrees
    public void setDestination(float degrees){
        destinationAngle = imu.getAngle() + degrees;

        if( destinationAngle > 180 ){
            destinationAngle -= 360;
        }
        else if (destinationAngle < -180){
            destinationAngle += 360;
        }
    }

    public double updateAndDrive() {
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
        return error;
    }

    public double[] turnDegrees (int degrees) {
        angleTurned = imu.getAngle();
        double error = 0.0;
        if (!started) {
            started = true;
            clock.reset();
        }

        if (started && clock.seconds() < 1) {
            setDestination(degrees);
        }
        else if (started && clock.seconds() < 4) {
            error = updateAndDrive();
        }

        angleTurned = imu.getAngle();
        double[] arr = {destinationAngle, clock.seconds(), currentAngle, error};
        return arr;
    }
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
//import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.bosch.MyNaiveAccelerationIntegrator;
import com.qualcomm.hardware.bosch.NaiveAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
//import org.firstinspires.ftc.robotcore.external.navigation.Position;
//import org.firstinspires.ftc.robotcore.external.navigation.Velocity;


public class imuData
{
    static BNO055IMU imu;
    Orientation angle = new Orientation();
    BNO055IMU.Parameters parameters;

    public imuData (HardwareMap hardwareMap) {
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        initImu();
    }

    public void initImu(){
        parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;

        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new MyNaiveAccelerationIntegrator();

        /*Position position = new Position();
        Velocity velocity= new Velocity();
        imu.startAccelerationIntegration(position, velocity, 1000 );*/

        imu.initialize(parameters);
    }

    public float getAngle() {
        return imu.getAngularOrientation().firstAngle;
    }

    public double getXVelocity() {
        return imu.getVelocity().xVeloc;
    }

    public double getYVelocity() {
        return imu.getVelocity().yVeloc;
    }

    public double getZVelocity() {
        return imu.getVelocity().zVeloc;
    }

    public double getXAcceleration() {
        return imu.getAcceleration().xAccel;
    }

    public double getYAcceleration() {
        return imu.getAcceleration().yAccel;
    }

    public double getZAcceleration() {
        return imu.getAcceleration().zAccel;
    }
}
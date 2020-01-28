package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "Leroy smoothing test")
public class SmoothingTest extends Library
{
    imuData imu;
    OldTurning turner;
    double angleTurned = 0;
    ExponentialSmoothing test;
    //double timeCheck = 0;
    double goalAccel = 1.5;
    double checkAccel = 0.008;
    double accel;
    double velocity;

    public void init()
    {
        hardwareInit();
        imu = new imuData(hardwareMap);
        imu.initImu();
        turner = new OldTurning();
        test = new ExponentialSmoothing();
    }

    public void loop() {
        if (test.getClockTime() < 3000) {

            test.smallAcceleration(goalAccel, imu);
            goalAccel += checkAccel;
//            timeCheck += 10;
            //test.updateClock();
            accel = imu.getXAcceleration();
            velocity = imu.getXVelocity();
            telemetry.addData("Starting Accelerations:", accel);
            telemetry.addData("Current Velocity:", velocity);
        } else if (test.getClockTime() < 6000) {
            test.smoothing(1.6, 1.6, imu);
//            timeCheck += 10;
            //test.updateClock();
            accel = imu.getYAcceleration();
            velocity = imu.getYVelocity();
            telemetry.addData("Smoothed Acceleration:", accel);
            telemetry.addData("Current Velocity:", velocity);
        } else if (test.getClockTime() < 9000) {
            test.decelerateToValue(imu, 0);
//            timeCheck += 100;
            //test.updateClock();
            accel = imu.getZAcceleration();
            velocity = imu.getZVelocity();
            telemetry.addData("End Accelerations:", accel);
            telemetry.addData("Current Velocity:", velocity);
            telemetry.addData("I'm in here!", imu.getZAcceleration());
        }
        telemetry.addData("Current time (millis):", test.getClockTime());
    }

}

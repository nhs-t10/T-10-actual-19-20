package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "Leroy smoothing test")
public class SmoothingTest extends Library
{
    imuData imu;
    Turning turner;
    double angleTurned = 0;
    ExponentialSmoothing test;
    //double timeCheck = 0;
    double goalAccel = 1.5;
    double checkAccel = 0.000;

    public void init()
    {
        hardwareInit();
        imu = new imuData(hardwareMap);
        imu.initImu();
        turner = new Turning();
        test = new ExponentialSmoothing();
    }

    public void loop() {
        if (test.getClockTime() < 3000) {

            test.smallAcceleration(goalAccel, imu);
            goalAccel += checkAccel;
//            timeCheck += 10;
            //test.updateClock();
            telemetry.addData("Starting Accelerations X:", imu.getXAcceleration());
            telemetry.addData("Current Velocity X:", imu.getXVelocity());
            telemetry.addData("Starting Accelerations Y:", imu.getYAcceleration());
            telemetry.addData("Current Velocity Y:", imu.getYVelocity());
            telemetry.addData("Starting Accelerations Z:", imu.getZAcceleration());
            telemetry.addData("Current Velocity Z:", imu.getZVelocity());
        } else if (test.getClockTime() < 6000) {
            test.smoothing(1.6, 1.6, imu);
//            timeCheck += 10;
            //test.updateClock();
            telemetry.addData("Smoothed Acceleration X:", imu.getXAcceleration());
            telemetry.addData("Current Velocity X:", imu.getXVelocity());
            telemetry.addData("Smoothed Acceleration Y:", imu.getYAcceleration());
            telemetry.addData("Current Velocity Y:", imu.getYVelocity());
            telemetry.addData("Smoothed Acceleration Z:", imu.getZAcceleration());
            telemetry.addData("Current Velocity Z:", imu.getZVelocity());
        } else if (test.getClockTime() < 9000) {
            test.decelerateToValue(imu, 0);
//            timeCheck += 100;
            //test.updateClock();
            telemetry.addData("End Accelerations X:", imu.getXAcceleration());
            telemetry.addData("Current Velocity X:", imu.getXVelocity());
            telemetry.addData("End Accelerations Y:", imu.getYAcceleration());
            telemetry.addData("Current Velocity Y:", imu.getYVelocity());
            telemetry.addData("End Accelerations Z:", imu.getZAcceleration());
            telemetry.addData("Current Velocity Z:", imu.getZVelocity());
            telemetry.addData("I'm in here!", imu.getZAcceleration());
        }
        telemetry.addData("Current time (millis):", test.getClockTime());
    }

}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;



@Autonomous(name = "Leroy smoothing test")
public class SmoothingTest extends Library
{
    imuData imu;
    Turning turner;
    double angleTurned = 0;
    ExponentialSmoothing test;
    double timeCheck = 0;
    double goalAccel = 1.0;
    double checkAccel = 0.008;

    public void init()
    {
        hardwareInit();
        imu = new imuData(hardwareMap);
        turner = new Turning();
        test = new ExponentialSmoothing();

    }

    public void loop() {
        if (test.getClockTime() < 3000 && test.getClockTime() >= timeCheck) {
            test.smallAcceleration(goalAccel, imu);
            goalAccel += checkAccel;
            timeCheck += 10;
            //test.updateClock();
            telemetry.addData("Starting Accelerations:", imu.getZAcceleration());
            telemetry.addData("Current Velocity:", imu.getZVelocity());
        } else if (test.getClockTime() < 6000 && test.getClockTime() >= timeCheck) {
            test.smoothing(1.4, 1.4, imu);
            timeCheck += 10;
            //test.updateClock();
            telemetry.addData("Smoothed Acceleration:", imu.getZAcceleration());
            telemetry.addData("Current Velocity:", imu.getZVelocity());
        } else if (test.getClockTime() < 9000 && test.getClockTime() >= timeCheck) {
            test.decelerate(imu);
            timeCheck += 10;
            //test.updateClock();
            telemetry.addData("End Accelerations:", imu.getZAcceleration());
            telemetry.addData("Current Velocity:", imu.getZVelocity());
        }
        telemetry.addData("Current time (millis):", test.getClockTime());
    }

}

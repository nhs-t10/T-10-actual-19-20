package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "imu test")
public class TestIMU extends Library
{
    double angleTurned = 0;

    public void init()
    {
        hardwareInit();
    }

    public void loop()
    {
        if (gamepad1.a)
        {
            turner.setDestination(90);
            turner.updateDrive(imu);
            angleTurned = imu.getAngle();

            telemetry.addData("Current Angle: ", angleTurned);
        }

    }
}
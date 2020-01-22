package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;



@TeleOp(name = "Cheifetz imu test")
public class TestIMU extends Library
{
    imuData imu;
    Turning turner;
    double angleTurned = 0;

    public void init()
    {
        hardwareInit();
        imu = new imuData(hardwareMap);
        turner = new Turning();
    }

    public void loop()
    {
        angleTurned = imu.getAngle();
        double[] array = new double[4];

        if (gamepad1.a)
        {
            while (System.currentTimeMillis() < 5000)
            {
                turner.setDestination(90);
                turner.updateDrive(imu);
                array = turner.updateDrive(imu);
                telemetry.addData("Destination Angle: ", array[0]);
                telemetry.addData("Current State (0.0 good, 1.0 bad): ", array[1]);
                telemetry.addData("Current Angle: ", array[2]);
                telemetry.addData("Error: ", array[3]);
            }
        }

        angleTurned = imu.getAngle();
        telemetry.addData("Current Angle YES: ", angleTurned);

        if (gamepad1.b)
        {
            turner.setDestination(-90);
            turner.updateDrive(imu);
            //angleTurned = imu.getAngle();

            //telemetry.addData("Current Angle: ", angleTurned);
        }

    }
}
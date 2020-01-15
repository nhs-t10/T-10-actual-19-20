package org.firstinspires.ftc.teamcode;

public class IMUTest extends Library{
    double xAccel = 0;
    public void init()
    {
        hardwareInit();
    }


    public void loop() {
        //stuff lol
        //imuData imu = new imuData(hardwareMap);
        if (gamepad1.a) {
            turner.setDestination(90);
            turner.updateDrive(imu);
            xAccel = imu.getXAcceleration();
            telemetry.addData("Current Horizontal Acceleration: ", xAccel);
        }
    }
}

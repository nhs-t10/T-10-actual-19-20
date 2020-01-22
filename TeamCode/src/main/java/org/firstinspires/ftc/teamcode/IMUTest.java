package org.firstinspires.ftc.teamcode;

public class IMUTest extends Library{
    double xAccel = 0;
    imuData imu;
    Turning turner;
    public void init()
    {
        imu = new imuData(hardwareMap);
        turner = new Turning();
        hardwareInit();
    }


    public void loop() {
        //stuff lol
        //imuData imu = new imuData(hardwareMap);
        if (gamepad1.a) {
            turner.setDestination(90);
            // issue with .updateDrive(imu)
            turner.updateDrive(imu);
            xAccel = imu.getXAcceleration();
            telemetry.addData("Current Horizontal Acceleration: ", xAccel);
        }
    }
}

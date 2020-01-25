package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Cheifetz imu test")
public class TestIMU extends Library{
    imuData imu;
    Turning turner;
    double angleTurned = 0;
    double[] array;
    state curState;

    enum state{
        PlEASE_WORK
    }

    public void init(){
        hardwareInit();
        imu = new imuData(hardwareMap);
        turner = new Turning();
        curState = state.PlEASE_WORK;
        clock.reset();
    }

    ElapsedTime clock = new ElapsedTime();

    public void loop(){
        if( curState == state.PlEASE_WORK ){
            turn();
        }
    }

    public void turn(){
        angleTurned = imu.getAngle();
        array = new double[4];

        if( clock.seconds() < 1 ){
            turner.setDestination(90);
        }
        if( clock.seconds() > 1 && clock.seconds() < 10 ){
            array = turner.updateDrive(imu);
            telemetry.addData("Destination Angle: ", array[0]);
            telemetry.addData("Current State (0.0 good, 1.0 bad): ", array[1]);
            telemetry.addData("Current Angle: ", array[2]);
            telemetry.addData("Error: ", array[3]);
        }

        angleTurned = imu.getAngle();
        telemetry.addData("Current Angle YES: ", angleTurned);
        telemetry.addData("Clock:", clock.seconds());
    }
}
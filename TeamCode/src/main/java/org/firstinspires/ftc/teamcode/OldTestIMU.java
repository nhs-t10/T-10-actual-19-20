package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Cheifetz imu test")
public class OldTestIMU extends Library{
    imuData imu;
    OldTurning turner;
    double angleTurned = 0;
    double[] array;
    state curState;
    boolean started = false;

    enum state{
        PlEASE_WORK
    }

    public void init(){
        hardwareInit();
        imu = new imuData(hardwareMap);
        imu.initImu();
        turner = new OldTurning();
        curState = state.PlEASE_WORK;
    }

    ElapsedTime clock = new ElapsedTime();

    public void loop(){
        if( curState == state.PlEASE_WORK ){
            turn(180);
        }
    }

    public void turn(int degrees){
        angleTurned = imu.getAngle();
        array = new double[4];
        if(!started){
            started = true;
            clock.reset();
        }
        if( started && clock.seconds() < 1 ){
            turner.setDestination(imu, degrees);
        }

        if( started && clock.seconds() > 1 && clock.seconds() < 10 ){
            array = turner.updateAndDrive(imu);
            telemetry.addData("Destination Angle: ", array[0]);
            telemetry.addData("Current Turn State (0.0 good, 1.0 bad): ", array[1]);
            telemetry.addData("Current Angle: ", array[2]);
            telemetry.addData("Error: ", array[3]);
        }

        angleTurned = imu.getAngle();
        telemetry.addData("Current Angle YES: ", angleTurned);
        telemetry.addData("Clock:", clock.seconds());
    }
}
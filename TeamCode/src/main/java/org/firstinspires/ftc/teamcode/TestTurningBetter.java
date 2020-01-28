package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Turning 2.0")
public class TestTurningBetter extends Library{
    imuData imu;
    TurningBetter turner;
    state curState;

    enum state{
        PlEASE_WORK
    }

    public void init(){
        hardwareInit();
        imu = new imuData(hardwareMap);
        imu.initImu();
        turner = new TurningBetter();
        curState = state.PlEASE_WORK;
    }

    ElapsedTime clock = new ElapsedTime();
    double curClock;

    public void loop(){
        if (curState == state.PlEASE_WORK) {
            curClock = turner.turnBetter(imu, 90);
            telemetry.addData("Destination Angle: ", curClock);   
        }
    }
}
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Turning Test All Angles")
public class TestTurningVerticalRev extends Library{
    TurningVerticalRev turner;

    public void init(){
        hardwareInit();
        turner = new TurningVerticalRev();
        turner.initImu(hardwareMap);
    }

    public void loop(){
        double[] array = turner.turnDegrees(0);
        telemetry.addData("DEST ANGLE: ", array[0]);
        telemetry.addData("CLOCK: ", array[1]);
        telemetry.addData("CURRENT ANGLE (1st): ", array[2]);
        telemetry.addData("CURRENT ANGLE (2nd): ", array[3]);
        telemetry.addData("CURRENT ANGLE (3rd): ", array[4]);
        telemetry.addData("ERROR: ", array[5]);
    }
}
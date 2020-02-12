package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Turning Test")
public class TestTurningVerticalRev extends Library {
    TurningVerticalRev turner;

    public void init(){
        hardwareInit();
        turner = new TurningVerticalRev();
        turner.initImu(hardwareMap);
    }

    public void loop(){
        double[] array = turner.turnDegrees(0);
        telemetry.addData("DEST ANGLE: ", array[0]);
        telemetry.addData("CURRENT ANGLE (2nd): ", array[2]);
        telemetry.addData("ERROR: ", array[3]);
    }
}
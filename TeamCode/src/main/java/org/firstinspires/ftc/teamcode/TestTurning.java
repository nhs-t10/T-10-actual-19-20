package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Turning Test")
public class TestTurning extends Library {
    Turning turner;

    public void init(){
        hardwareInit();
        turner = new Turning();
        turner.initImu(hardwareMap);
    }

    public void loop(){
        double[] array;
        array = turner.turnDegrees(90);
        telemetry.addData("CLOCK: ", array[0]);
        telemetry.addData("DESTINATION ANGLE: ", array[1]);
        telemetry.addData("ANGLE TURNED: ", array[2]);
        telemetry.addData("ERROR: ", array[3]);
    }
}
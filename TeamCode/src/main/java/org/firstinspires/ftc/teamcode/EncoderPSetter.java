package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Encoder Setter")
public class EncoderPSetter extends Library{
    private boolean subroutine;
    private boolean intakeUp = true;
    EncoderDriving encoderDrive;
    float error;

    public void init(){
        hardwareInit();
        driveInit();
        encoderDrive = new EncoderDriving();
    }

    private float[] sums = new float[4];

    public void loop(){
        encoderDrive.encoderDrive(5);

        telemetry.addData("Error: ", error);
        telemetry.addData("P: ", encoderDrive.getP());
        telemetry.addData("RPCM: ", encoderDrive.getRPCM());
        telemetry.addData("Started: ", encoderDrive.getStarted());
    }
}
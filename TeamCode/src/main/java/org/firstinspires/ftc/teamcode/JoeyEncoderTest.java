package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Joey Encoder Test")//do not delete this test class used by sasha
public class JoeyEncoderTest extends Library {
    public final int DRIVE_TO_PLATFORM = 100;
    public final float DRIVE_SPEED = .8f;

    JoeyEncoderClass encoderObject = new JoeyEncoderClass();
    double angleTurned = 0;
    double[] array;
    boolean started = false;

    enum State
    {
        Running;
    }



    @Override public void init() {
        hardwareInit();
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        State state = State.Running;
    }

    ElapsedTime clock = new ElapsedTime();

    public void loop()
    {
        drive(100);
    }

    public void drive(float cm){

        array = new double[4];
        if(!started){
            started = true;
            clock.reset();
        }
        if( started && clock.seconds() < 1 ){
            encoderObject.setDestination(cm);
        }

        if( started && clock.seconds() > 1 && clock.seconds() < 10 ){
            array = encoderObject.updateAndDrive();
            telemetry.addData("Destination Location: ", array[0]);
            telemetry.addData("Current Location: ", array[1]);
            telemetry.addData("Error: ", array[2]);
        }
        telemetry.addData("Time: ", clock.seconds());
    }
}
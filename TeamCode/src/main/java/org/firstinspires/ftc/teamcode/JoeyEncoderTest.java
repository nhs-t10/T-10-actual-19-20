package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "Joey Encoder Test")//do not delete this test class used by sasha
public class JoeyEncoderTest extends Library{

    JoeyEncoderClass encoderObject = new JoeyEncoderClass();
    double[] array;
    //boolean started = false;

    enum State{
        Running;
    }


    @Override
    public void init(){
        hardwareInit();
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    ElapsedTime clock = new ElapsedTime();

    public void loop(){
        array = encoderObject.driveForCM(20);
        telemetry.addData("Destination Location: ", array[0]);
        telemetry.addData("Current Location: ", array[1]);
        telemetry.addData("Error: ", array[2]);
        telemetry.addData("P Component: ", array[3]);
    }

    /*public void drive( float cm ){

        array = new double[4];
        if( !started ){
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
            telemetry.addData("P componenet: ", array[3]);
        }
        telemetry.addData("Time: ", clock.seconds());
    }*/
}
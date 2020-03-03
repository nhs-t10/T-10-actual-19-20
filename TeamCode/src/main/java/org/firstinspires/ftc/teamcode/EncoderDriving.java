package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Rotation;

import com.qualcomm.robotcore.util.ElapsedTime;

public class EncoderDriving{
    float error;
    float[] meow;
    float startEncoderValue;
    float destinationEncoderValue;
    boolean started;
    boolean strafingRight;
    boolean strafingLeft;
    float P = 0.01f;
    float RotationPerCM = (float) 10.13;
    ElapsedTime clock;

    public EncoderDriving(){
        started = false;
        clock = new ElapsedTime();
        destinationEncoderValue = 0;
        meow = new float[3];
    }

    public void setP(float newP){
        P = newP;
    }

    public void setRPCM(float RPCM){
        RotationPerCM = RPCM;
    }

    public void setStarted(boolean start){
        started = start;
    }

    public float getP(){
        return P;
    }

    public float getRPCM(){
        return RotationPerCM;
    }

    public boolean getStarted(){
        return started;
    }

    public void setDistance(float distCM){
        destinationEncoderValue = distCM * RotationPerCM + Library.getEncoderValue();
    }

    private void drive(){
        error = destinationEncoderValue - Library.getEncoderValue();
        if(error > 1){
            Library.drive(P * -error, 0, 0);
        }else{
            Library.drive(0,0,0);
        }
    }

    public float encoderDrive(float distCM){
        if( !started ){
            started = true;
            clock.reset();
            setDistance(distCM);
        }else{
            drive();
        }
        return error;
    }

    public void setStrafeDistance(float distCM){
        destinationEncoderValue = distCM * RotationPerCM * 10 + Library.getBackLeftEncoderValue();
    }

    private float[] strafe(){
        float curencval = Library.getBackLeftEncoderValue();
        error = destinationEncoderValue - curencval;
        if (Math.abs(error) > 1){
            Library.drive(0, 0, -P * error);
        }else{
            Library.drive(0,0,0);
        }
        meow[0] = error;
        meow[1] = destinationEncoderValue;
        meow[2] = curencval;
        return meow;
    }

    public float[] encoderStrafe(float distCM){
        if( !started){
            started = true;
            clock.reset();
            setStrafeDistance(distCM);
        }else if (clock.seconds() < 6){
            strafe();
        }
        return meow;
    }

}
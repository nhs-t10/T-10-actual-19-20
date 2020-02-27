package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Rotation;

import com.qualcomm.robotcore.util.ElapsedTime;

public class EncoderDriving{
    float error;
    float startEncoderValue;
    float destinationEncoderValue;
    boolean started;
    float P = 0.001f;
    float RotationPerCM = (float) 194.13;
    ElapsedTime clock;

    public EncoderDriving(){
        started = false;
        clock = new ElapsedTime();
        destinationEncoderValue = 0;
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
            Library.drive(P * error, 0, 0);
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
}
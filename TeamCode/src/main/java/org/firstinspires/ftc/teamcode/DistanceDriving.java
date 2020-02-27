package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class DistanceDriving {
        double currentDistance;
        double destinationDistance;
        double pComponent;
        final double P = 0.01;
        double error;

        boolean started = false;
        ElapsedTime clock = new ElapsedTime();

        //Turning object: Has a destination angle
        public DistanceDriving(){
            destinationDistance = 0;
        }

        //Setting the destination in degrees
        public void setDestination( float cm ){
            destinationDistance= Library.distanceLeft.getDistance(DistanceUnit.CM) + cm;

        }

        public double updateAndDrive(){
            //Setting the current angle
            if(Library.distanceLeft.getDistance(DistanceUnit.CM) >= 30) {
                //stuff
            }

            //Finding the error
            double error = currentDistance - destinationDistance;

            /*This turnAngle is so that the robot turns the right direction and considers the
             *rotation system of IMU (-180 to 180)*/
            double turnAngle = -error;
            if( turnAngle > 180 ){
                turnAngle -= 360;
            }else if( turnAngle < -180 ){
                turnAngle += 360;
            }

            //Figuring out the P component
            pComponent = turnAngle * P;
            if( pComponent > .5f ){
                pComponent = .5f;
            }
            if( pComponent < -.5f ){
                pComponent = -.5f;
            }

            //Actual turning
            if( Math.abs(error) > 2 ){
                Library.drive(0f, (float) pComponent, 0f);
            }

            return error;
        }

        // stuff from EncoderDriving

        public void setStarted(boolean start){
            started = start;
        }

        public double getP(){
            return P;
        }

        public boolean getStarted(){
            return started;
        }

        /*public void setDistance(float distCM){
            destinationEncoderValue = distCM * RotationPerCM + Library.getEncoderValue();
        }
       */

        private void drive(){
            error = destinationDistance - Library.distanceLeft.getDistance(DistanceUnit.CM);
            if(error > 1){
                Library.drive((float) (P * error), 0, 0);
            }else{
                Library.drive(0,0,0);
            }
        }

//        public double[] turnDegrees( int degrees ){
//            angleTurned = imu.getAngle();
//            double error = 0.0;
//            if( !started ){
//                started = true;
//                clock.reset();
//            }
//
//            if( started && clock.seconds() < 1 ){
//                setDestination(degrees);
//            }else if( started && clock.seconds() < 4 ){
//                error = updateAndDrive();
//            }
//
//            angleTurned = imu.getAngle();
//
//            double[] array = { clock.seconds(), destinationAngle, angleTurned, error };
//            return array;
//        }
}

    //distanceLeft.getDistance(DistanceUnit.CM) >= 30 and distanceRight.getDistance(DistanceUnit.CM) >= 30
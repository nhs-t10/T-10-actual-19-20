private void scan(){
        //is Sky stone Visible, then either slide left or move forward
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 1 ){
            if( isSkystoneVisible() ){
                currentState = State.MOVE;
            }else{
                currentState = State.SLIDE;
            }
        }

    }

    private void slide(){
        //only do if skystone is not immediately visible
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.milliseconds() < 50 ){
            drive(0, 0, -.5f);
        }else{
            drive(0, 0, 0);
            moving = false;
            currentState = State.SCAN;
        }
    }

    private void move(){
        //move forward to skystone (will need tweaking to make sure that skystone is always visible)
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( distanceLeft.getDistance(DistanceUnit.INCH) <= 18 ){
            drive(.5f, 0, 0);
        }else{
            turner.turnDegrees(180);
            drive(0, 0, 0);
            gripStone(true);
            liftRight.setPower(0.0001);
            liftLeft.setPower(0.0001);
            drive(1, 0, 0);
            drive(0, 0, 0);
            moving = false;
            currentState = State.PARK;
        }
    }

    //    private void travel(){
    //        //back up a small amount, then slide left to cross the barrier
    //        if( !moving ){
    //            clock.reset();
    //            moving = true;
    //        }else if( clock.seconds() < 2 ){
    //            drive(0, 0, .5f);
    //        }else{
    //            drive(0, 0, 0);
    //            //            lift.setPower(0);
    //            //            gripStone(false);
    //            moving = false;
    //            currentState = State.PARK;
    //        }
    //    }

    private void park(){
        //slide right and use color sensor to stop on blue line
        Color.RGBToHSV((int) ( color.red() * SCALE_FACTOR ), (int) ( color.green() * SCALE_FACTOR ), (int) ( color.blue() * SCALE_FACTOR ), hsvValues);
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( hsvValues[0] >= 180 || clock.seconds() >= 6 ){
            moving = false;
            drive(0, 0, 0);
            liftRight.setPower(0);
            liftLeft.setPower(0);
            gripStone(false);
            currentState = State.END;
        }else if( clock.seconds() >= 5 ){
            drive(0, 0, .3f);
        }else{
            drive(0, 0, -.4f);
        }

        if( distanceLeft.getDistance(DistanceUnit.CM) > 8 || distanceRight.getDistance(DistanceUnit.CM) > 8 ){
            drive(.3f, 0, 0);
        }
    }
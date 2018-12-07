package edu.ufl.cise.cs1.robots;

import robocode.TeamRobot;
import robocode.ScannedRobotEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.DeathEvent;

import java.awt.*;
import robocode.*;

public class YourRobotName extends TeamRobot
{

    boolean n;
    int topWall = 0;
    int leftWall = 20;
    int rightWall = 0;
    int bottomWall = 20;
    int currentCorner = 0;

    int distanceRight = 0;
    int distanceUp = 0;
    int distanceDown = 0;
    int distanceLeft = 0;

    int cornerUpRightDistance = 0;
    int cornerUpLeftDistance = 0;
    int cornerDownRightDistance= 0;
    int cornerDownLeftDistance = 0;

    public YourRobotName(){

    }

    @Override
    public void run(){
        boolean x = true;


        setAllColors(Color.magenta);

        distanceRight = (int) getBattleFieldWidth() - (int) getX();
        distanceUp = (int) getBattleFieldHeight() - (int) getY();
        distanceDown = (int) getY();
        distanceLeft = (int) getX();
        topWall = (int) getBattleFieldHeight() -20;
        rightWall = (int) getBattleFieldWidth() -20;

        cornerUpRightDistance = (int) Math.sqrt(Math.pow(distanceRight,2) +
                Math.pow(distanceUp,2));
        cornerUpLeftDistance = (int) Math.sqrt(Math.pow(distanceLeft,2) +
                Math.pow(distanceUp,2));
        cornerDownRightDistance= (int) Math.sqrt(Math.pow(distanceRight,2) +
                Math.pow(distanceDown,2));
        cornerDownLeftDistance = (int) Math.sqrt(Math.pow(distanceLeft,2) +
                Math.pow(distanceDown,2));



        setAdjustGunForRobotTurn(true);
        setAdjustRadarForRobotTurn(true);// allows gun and radar to move while turning

        goCorner(); // tank immediately moves to the closest corner


        counterClockwise(); // tank then moves around the whole map in a counterclockwise direction


        while(x){

            distanceRight = (int) getBattleFieldWidth() - (int) getX();
            distanceUp = (int) getBattleFieldHeight() - (int) getY();
            distanceDown = (int) getY();
            distanceLeft = (int) getX();

            updateCornerDistance();
            turnGunLeft(60);
            turnGunRight(60);

        }

    }


    private void updateCornerDistance() {
        cornerUpRightDistance = (int) Math.sqrt(Math.pow(distanceRight,2) +
                Math.pow(distanceUp,2));
        cornerUpLeftDistance = (int) Math.sqrt(Math.pow(distanceLeft,2) +
                Math.pow(distanceUp,2));
        cornerDownRightDistance= (int) Math.sqrt(Math.pow(distanceRight,2) +
                Math.pow(distanceDown,2));
        cornerDownLeftDistance = (int) Math.sqrt(Math.pow(distanceLeft,2) +
                Math.pow(distanceDown,2));
    }



    public void standardLeft(){

        updateCornerDistance();
        double currentHeading = getHeading();

        double certainDegree =  270.0;
        certainDegree = currentHeading - certainDegree;
        if (certainDegree > 0)
            turnLeft(certainDegree);
        else
            turnRight(Math.abs(certainDegree));

        execute();
        //updateCornerDistance();
        ahead(rightWall);
        standardUp();

    }
    public void standardDown(){

        updateCornerDistance();
        double currentHeading = getHeading();

        double certainDegree =  180.0;
        certainDegree = currentHeading - certainDegree;
        if (certainDegree > 0)
            turnLeft(certainDegree);
        else
            turnRight(Math.abs(certainDegree));

        execute();
        //updateCornerDistance();
        ahead(topWall);
        standardLeft();
    }
    public void standardRight(){

        updateCornerDistance();
        double currentHeading = getHeading();

        double certainDegree =  90.0;
        certainDegree = currentHeading - certainDegree;
        if (certainDegree > 0)
            turnLeft(certainDegree);
        else
            turnRight(Math.abs(certainDegree));

        execute();
        //updateCornerDistance();
        ahead(rightWall);
        standardDown();


    }
    public void standardUp(){

        updateCornerDistance();
        double currentHeading = getHeading();

        double certainDegree =  360.0;
        certainDegree = currentHeading - certainDegree;
        if (certainDegree > 0)
            turnLeft(certainDegree);
        else
            turnRight(Math.abs(certainDegree));

        execute();
        //updateCornerDistance();
        ahead(topWall);
        standardRight();


    }




    public void goCorner(){ //use euclidean distance to find the nearest corner, go there and that's the start with corner labeled

        double currentHeading = getHeading();
        if ((cornerUpRightDistance < cornerUpLeftDistance) && (cornerUpRightDistance < cornerDownRightDistance)
                && (cornerUpRightDistance < cornerDownLeftDistance)){
            double distance1 = distanceUp;
            double distance2 = distanceRight;
            double certainDegree = (double) 90.0 - Math.toDegrees(Math.atan2(distance1, distance2));
            certainDegree = currentHeading - certainDegree;
            if (certainDegree > 0)
                turnLeft(certainDegree);
            else
                turnRight(Math.abs(certainDegree));
            execute();
            updateCornerDistance();
            ahead(cornerUpRightDistance -100);
            currentCorner = 1;
        } else if ((cornerDownRightDistance < cornerUpLeftDistance) && (cornerDownRightDistance < cornerUpRightDistance)
                && (cornerDownRightDistance < cornerDownLeftDistance)){
            double distance1 = distanceDown;
            double distance2 = distanceRight;
            double certainDegree = (double) 90.0 + Math.toDegrees(Math.atan2(distance1, distance2));
            certainDegree = currentHeading - certainDegree;
            if (certainDegree > 0)
                turnLeft(certainDegree);
            else
                turnRight(Math.abs(certainDegree));
            execute();
            updateCornerDistance();
            ahead(cornerUpRightDistance -100);
            currentCorner = 2;
        } else if ((cornerUpLeftDistance < cornerUpRightDistance) && (cornerUpLeftDistance < cornerDownRightDistance)
                && (cornerUpLeftDistance < cornerDownLeftDistance)){
            double distance1 = distanceUp;
            double distance2 = distanceLeft;
            double certainDegree = (double) 270.0 + Math.toDegrees(Math.atan2(distance1, distance2));
            certainDegree = currentHeading - certainDegree;
            if (certainDegree > 0)
                turnLeft(certainDegree);
            else
                turnRight(Math.abs(certainDegree));
            execute();
            updateCornerDistance();
            ahead(cornerUpRightDistance -100);
            currentCorner = 4;
        } else if (cornerDownLeftDistance < cornerUpLeftDistance && cornerDownLeftDistance < cornerDownRightDistance
                && cornerDownLeftDistance < cornerUpRightDistance){
            double distance1 = distanceDown;
            double distance2 = distanceLeft;
            double certainDegree = (double) 270.0 - Math.toDegrees(Math.atan2(distance1, distance2));
            certainDegree = currentHeading - certainDegree;
            if (certainDegree > 0)
                turnLeft(certainDegree);
            else
                turnRight(Math.abs(certainDegree));
            execute();
            updateCornerDistance();
            ahead(cornerUpRightDistance -100);
            currentCorner = 3;
        }

        // turn to the corner and move towards it
    }


       public void counterClockwise(){ // if you want the tank to stay in the corner, just create a new method where it moves ahead 0 distance.

        //might change the code a little with a while loop, and ahead in between each standard Turn instead so i can code gun

        updateCornerDistance();
           double currentHeading = getHeading();

                if (cornerUpLeftDistance < 1000){


                        standardRight();
                        standardDown();
                        standardLeft();
                        standardUp();


                } else if(cornerUpRightDistance < 1000){



                        standardDown();
                        standardLeft();
                        standardUp();
                        standardRight();



                } else if(cornerDownRightDistance < 1000){



                        standardLeft();
                        standardUp();
                        standardRight();
                        standardDown();




                } else  if(cornerDownLeftDistance < 1000){


                        standardUp();
                        standardRight();
                        standardDown();
                        standardLeft();


                }
       }

       public void onHitWall(HitWallEvent e){

                if((int) getY() == (topWall - 100) || (int) getY() == bottomWall + 100){
                   //measure closes corner (topleft or topright)
                    if(distanceRight < distanceLeft){
                        standardRight();
                        ahead(distanceRight);

                    } else{
                        standardLeft();
                        ahead(distanceLeft);

                    }
                    //turn to nearest corner

                }

                if((int) getX() == leftWall + 100 || (int) getX() == rightWall - 100){

                    if(distanceUp < distanceDown){
                        standardDown();
                        ahead(distanceDown);
                    } else{
                        standardUp();
                        ahead(distanceUp);
                    }
                    // turn to nearest corner
                }

       }

       @Override
        public void onScannedRobot(ScannedRobotEvent e){
                fire(1);
        }

}

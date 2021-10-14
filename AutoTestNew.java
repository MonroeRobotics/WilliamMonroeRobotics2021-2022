package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous

public class AutoTest extends LinearOpMode{
    
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;
    
    public void moveForTime(float time, float deg, double magnitude){
        
        double fRight;
        double bRight;
        double bLeft;
        double fLeft;
        
        double direction = deg * (Math.PI / 180);
        
        double checkTime =  System.currentTimeMillis();
        double checkTimeEnd = checkTime + time;
        
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        
        while (checkTimeEnd > System.currentTimeMillis() && opModeIsActive()) {
            
            
            fRight = (Math.sin(direction - 1.0/4.0 * Math.PI) * magnitude);
            bLeft = (-Math.sin(direction - 1.0/4.0 * Math.PI) * magnitude);
            bRight = (Math.sin(direction + 1.0/4.0 * Math.PI) * magnitude);
            fLeft = (-Math.sin(direction + 1.0/4.0 * Math.PI) * magnitude);
            
            if (fRight > 1 || fRight < -1){
                fLeft = (fLeft / Math.abs(fRight));
                fRight = (fRight / Math.abs(fRight));
                bRight = (bRight / Math.abs(fRight));
                bLeft = (bLeft / Math.abs(fRight));
            }
        
            if (fLeft > 1 || fLeft < -1){
                fLeft = (fLeft / Math.abs(fLeft));
                fRight = (fRight / Math.abs(fLeft));
                bLeft = (bLeft / Math.abs(fLeft));
                bRight = (bRight / Math.abs(fLeft));
            }
                
            backLeft.setPower(bLeft);
            frontRight.setPower(fRight);
            backRight.setPower(bRight);
            frontLeft.setPower(fLeft);
            
            
        }
        
        
    }
    
    @Override
    public void runOpMode() {
        
        
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        // run until the end of the match (driver presses STOP)
        
        moveForTime(1000, 20, 1);
        
        moveForTime(500, 200, 1);
        
        moveForTime(500, 180, 1);
        
        
        
        
    }
}

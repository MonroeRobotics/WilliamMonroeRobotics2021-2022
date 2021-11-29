
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(group = "test")
@Disabled

public class IntakeTest extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor intakeMotor = null;

    @Override
    public void runOpMode() {
        double motorPower = .8;
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        intakeMotor  = hardwareMap.get(DcMotor.class, "intakeMotor");
        
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            
            
            intakeMotor.setPower(0);
            
            if (this.gamepad1.x){
                
                intakeMotor.setPower(motorPower);
            }
            if (this.gamepad1.y){
                intakeMotor.setPower(-motorPower);
            }

            telemetry.update();
        }
    }
}

package roboticArm;

import java.util.Arrays;

import static roboticArm.ArmTrigger.*;

public class RoboticArm {

	static int currentState;

	public RoboticArm() {
		currentState = READY.ordinal();
	}

	public void fireTrigger(ArmTrigger trigger) {

		switch (trigger) {
		case MOVE_IN:
			currentState = MOVE_IN.ordinal();
			moveIn();
			break;
		case GRAB:
			currentState = GRAB.ordinal();
			grab();
			break;
		case MOVE_OUT:
			currentState = MOVE_OUT.ordinal();
			moveOut();
			break;
		case RELEASE:
			currentState = RELEASE.ordinal();
			release();
			break;
		case READY:
			currentState = READY.ordinal();
			ready();
			break;
		default:
			// DONE needs to be called from inside this class
			// Invalid input should not be handled
			break;
		}
	}

	public ArmTrigger[] getAllTriggers() {
		return Arrays.stream(ArmTrigger.values()).filter(t -> !t.equals(READY)).toArray(ArmTrigger[]::new);
	}

	public final int getCurrentState() {
		return currentState;
	}

	public void moveIn() {

	}

	public void moveOut() {

	}

	public void grab() {

	}

	public void release() {

	}

	public void ready() {

	}

}

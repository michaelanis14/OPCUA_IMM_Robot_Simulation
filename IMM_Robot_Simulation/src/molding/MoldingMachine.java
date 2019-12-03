package molding;

import java.util.Arrays;

import static molding.MoldingTrigger.*;

public class MoldingMachine {

	static int currentState;

	public MoldingMachine() {
		currentState = CLOSING.ordinal();
	}

	public void fireTrigger(MoldingTrigger trigger) {

		switch (trigger) {
		case CLOSING:
			currentState = CLOSING.ordinal();
			closeMold();
			break;
		case MOLDING:
			currentState = MOLDING.ordinal();
			injectMold();
			break;
		case OPENING:
			currentState = OPENING.ordinal();
			openMold();
			break;
		case ROBOT:
			currentState = ROBOT.ordinal();
			waitForRobot();
			break;
		case STOP:
			currentState = STOP.ordinal();
			break;
		case SUSPEND:
			currentState = SUSPEND.ordinal();
			break;
		case RESUME:
			currentState = RESUME.ordinal();
			break;
		default:
			break;
		}
	}

	public MoldingTrigger[] getAllTriggers() {
		return Arrays.stream(MoldingTrigger.values()).filter(t -> !t.equals(DONE)).toArray(MoldingTrigger[]::new);
	}

	public int getCurrentState() {
		return currentState;
	}

	public String injectMold() {
		return "injectMold";

	}

	public String openMold() {
		return "openMold";

	}

	public String closeMold() {
		return "closeMold";

	}

	public String waitForRobot() {
		return "waitForRobot";

	}

}

package roboticArm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static roboticArm.ArmTrigger.*;

/**
 * State machine model for the Robotic Arm.
 * Manages state transitions and lifecycle of robot operations.
 *
 * @author Michael Bishara
 */
public class RoboticArm {

	private static final Logger logger = LoggerFactory.getLogger(RoboticArm.class);

	static int currentState;

	public RoboticArm() {
		currentState = READY.ordinal();
		logger.info("RoboticArm initialized in READY state");
	}

	/**
	 * Fires a state transition trigger.
	 *
	 * @param trigger the trigger to fire
	 */
	public void fireTrigger(ArmTrigger trigger) {
		String previousState = getCurrentStateName();
		logger.debug("Firing trigger: {} from state: {}", trigger, previousState);

		switch (trigger) {
		case MOVE_IN:
			currentState = MOVE_IN.ordinal();
			logger.info("State transition: {} -> MOVE_IN", previousState);
			moveIn();
			break;
		case GRAB:
			currentState = GRAB.ordinal();
			logger.info("State transition: {} -> GRAB", previousState);
			grab();
			break;
		case MOVE_OUT:
			currentState = MOVE_OUT.ordinal();
			logger.info("State transition: {} -> MOVE_OUT", previousState);
			moveOut();
			break;
		case RELEASE:
			currentState = RELEASE.ordinal();
			logger.info("State transition: {} -> RELEASE", previousState);
			release();
			break;
		case READY:
			currentState = READY.ordinal();
			logger.info("State transition: {} -> READY", previousState);
			ready();
			break;
		default:
			logger.warn("Invalid or unsupported trigger: {}", trigger);
			break;
		}
	}

	public ArmTrigger[] getAllTriggers() {
		return Arrays.stream(ArmTrigger.values()).filter(t -> !t.equals(READY)).toArray(ArmTrigger[]::new);
	}

	public final int getCurrentState() {
		return currentState;
	}

	/**
	 * Gets the current state name as a string.
	 *
	 * @return current state name
	 */
	public String getCurrentStateName() {
		try {
			return ArmTrigger.values()[currentState].name();
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.error("Invalid state index: {}", currentState);
			return "UNKNOWN";
		}
	}

	public void moveIn() {
		logger.debug("Executing moveIn operation - moving into mold");
	}

	public void moveOut() {
		logger.debug("Executing moveOut operation - moving out of mold");
	}

	public void grab() {
		logger.debug("Executing grab operation - grabbing part");
	}

	public void release() {
		logger.debug("Executing release operation - releasing part");
	}

	public void ready() {
		logger.debug("Executing ready operation - robot ready for next cycle");
	}

}

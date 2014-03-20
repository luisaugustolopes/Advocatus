package br.com.lopes.listener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.log4j.Logger;

public class LifeCycleListener implements PhaseListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger(getClass());
	
	public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    public void beforePhase(PhaseEvent event) {
    	log.info("START PHASE "+ event.getPhaseId());
    }

    public void afterPhase(PhaseEvent event) {
    	log.info("END PHASE "+ event.getPhaseId());
    }

}
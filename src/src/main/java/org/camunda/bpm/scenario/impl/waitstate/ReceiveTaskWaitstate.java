package org.camunda.bpm.scenario.impl.waitstate;


import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.camunda.bpm.scenario.Scenario;
import org.camunda.bpm.scenario.action.ScenarioAction;
import org.camunda.bpm.scenario.impl.ProcessRunnerImpl;

import java.util.Date;
import java.util.Map;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ReceiveTaskWaitstate extends MessageIntermediateCatchEventWaitstate {

  public ReceiveTaskWaitstate(ProcessRunnerImpl runner, HistoricActivityInstance instance) {
    super(runner, instance);
  }

  @Override
  protected ScenarioAction action(Scenario.Process scenario) {
    return scenario.actsOnReceiveTask(getActivityId());
  }

  @Override
  protected EventSubscription getDelegate() {
    return getRuntimeService().createEventSubscriptionQuery().eventType("message").executionId(getExecutionId()).singleResult();
  }

  @Override
  public void receive() {
    EventSubscription eventSubscription = getDelegate();
    if (eventSubscription != null) {
      super.receive();
    } else {
      getRuntimeService().signal(getExecutionId());
    }
  }

  @Override
  public void receive(Map<String, Object> variables) {
    EventSubscription eventSubscription = getDelegate();
    if (eventSubscription != null) {
      super.receive(variables);
    } else {
      getRuntimeService().signal(getExecutionId(), variables);
    }
  }

  @Override
  public String getId() {
    return super.getId();
  }

  @Override
  public String getEventType() {
    if (delegate == null)
      throw new UnsupportedOperationException("Not supported for Receive Tasks " +
          "used without a message event subscription.");
      return super.getEventType();
  }

  @Override
  public String getEventName() {
    if (delegate == null)
      throw new UnsupportedOperationException("Not supported for Receive Tasks " +
          "used without a message event subscription.");
    return super.getEventName();
  }

  @Override
  public String getProcessInstanceId() {
    if (delegate == null)
      throw new UnsupportedOperationException("Not supported for Receive Tasks " +
          "used without a message event subscription.");
    return super.getProcessInstanceId();
  }

  @Override
  public String getTenantId() {
    if (delegate == null)
      throw new UnsupportedOperationException("Not supported for Receive Tasks " +
          "used without a message event subscription.");
    return super.getTenantId();
  }

  @Override
  public Date getCreated() {
    if (delegate == null)
      throw new UnsupportedOperationException("Not supported for Receive Tasks " +
          "used without a message event subscription.");
    return super.getCreated();
  }

}

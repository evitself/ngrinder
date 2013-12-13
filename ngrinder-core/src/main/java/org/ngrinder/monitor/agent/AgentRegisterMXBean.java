/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package org.ngrinder.monitor.agent;

import org.ngrinder.monitor.MonitorConstants;
import org.ngrinder.monitor.MonitorContext;
import org.ngrinder.monitor.agent.mxbean.SystemMonitoringData;
import org.ngrinder.monitor.agent.mxbean.core.MXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.util.Set;

import static org.ngrinder.common.util.ExceptionUtils.processException;

/**
 * 
 * AgentRegisterMXBean used to register MBeanServer.
 * 
 * @author Mavlarn
 * @since 2.0
 */
public final class AgentRegisterMXBean {
	private static final Logger LOG = LoggerFactory.getLogger(AgentRegisterMXBean.class);
	private static final AgentRegisterMXBean INSTANCE = new AgentRegisterMXBean();

	private AgentRegisterMXBean() {
	}

	/**
	 * Add MBeanServer to AgentRegisterMXBean, add collector based on current setting.
	 * 
	 * @param mbeanServer	JMX MBean server for registration
	 */
	public void addDefaultMXBean(MBeanServer mbeanServer) {
		Set<String> dataCollectors = MonitorContext.getInstance().getDataCollectors();

		if (dataCollectors.contains(MonitorConstants.SYSTEM)) {
			addMXBean(mbeanServer, MonitorConstants.SYSTEM, new SystemMonitoringData());
		}

		LOG.info("Default MXBean Initialized.");
	}

	void addMXBean(MBeanServer mbeanServer, String subDomainName, MXBean bean) {
		ObjectName objectName;
		try {
			objectName = new ObjectName(MonitorConstants.DEFAULT_MONITOR_DOMAIN + ":" + subDomainName);
			mbeanServer.registerMBean(bean, objectName);
		} catch (Exception e) {
			// Not to check these exception, if there is any JMX related exception, just make it
			// error.
			throw processException(e.getMessage(), e);
		}

		AgentMXBeanStorage.getInstance().addMXBean(subDomainName, bean);
		LOG.info("Added MXBean:{}.", objectName);
	}

	public static AgentRegisterMXBean getInstance() {
		return INSTANCE;
	}
}

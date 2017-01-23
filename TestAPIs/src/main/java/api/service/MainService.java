package api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.external.ExternalSim;
import api.hystrix.HystrixWrapper;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;


@Service
public class MainService {
	
	@Autowired
	ExternalSim external;
	
	static volatile int number  = 0;

	public String getCall() {
		
		
		external.setWait(1000);
		String s  = "This is get call : ";
		String s2 = null;
		HystrixCommand.Setter config = getConfig(0);
		HystrixWrapper wrapper = new HystrixWrapper(config, external);
		try {
			s2 = wrapper.execute();
		} catch (Exception e) {
		}
		
		this.increment();
		return s+s2;
	}
	
	
	
	
	public String anotherCall() {
		
		
		external.setWait(0);
		String s  = "This is get call : ";
		String s2 = null;
		HystrixCommand.Setter config = getConfig(1);
		HystrixWrapper wrapper = new HystrixWrapper(config, external);
		try {
			s2 = wrapper.execute();
		} catch (Exception e) {
		}
		
		/*this.increment();*/
		return s+s2;
	}

	
	private HystrixCommand.Setter getConfig(int a)
	{
		HystrixCommand.Setter config = null;
		HystrixCommandProperties.Setter properties = HystrixCommandProperties.Setter();
		if(a == 0)
		{
			config = HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("MyKeyForAParticularCall")).andCommandKey(HystrixCommandKey.Factory.asKey("a"));
			ConfigurationManager.getConfigInstance().setProperty("hystrix.command.a.execution.isolation.thread.timeoutInMilliseconds", 500);
			if(number%2  == 0)
			{
				ConfigurationManager.getConfigInstance().setProperty("hystrix.command.a.execution.isolation.thread.timeoutInMilliseconds", 2000);
			}
			/*properties.withExecutionTimeoutInMilliseconds(4000);*/
			properties.withExecutionTimeoutEnabled(true);
		    properties.withCircuitBreakerSleepWindowInMilliseconds(30000);
		   // properties.withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD);
		    properties.withCircuitBreakerRequestVolumeThreshold(10);
		    properties.withCircuitBreakerErrorThresholdPercentage(80);
		    properties.withMetricsRollingStatisticalWindowInMilliseconds(60000);
		    properties.withMetricsRollingStatisticalWindowBuckets(100);
		    properties.withMetricsRollingStatisticalWindowInMilliseconds(60000);
		    properties.withMetricsRollingStatisticalWindowBuckets(100);
		    properties.withCircuitBreakerEnabled(false);  
		    config.andCommandPropertiesDefaults(properties);
		}
		else 
		{
			config = HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("AnotherKey")).andCommandKey(HystrixCommandKey.Factory.asKey("b"));
			ConfigurationManager.getConfigInstance().setProperty("hystrix.command.a.execution.isolation.thread.timeoutInMilliseconds", 1000);
			properties.withExecutionTimeoutEnabled(true);
		    properties.withCircuitBreakerSleepWindowInMilliseconds(30000);
		   // properties.withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD);
		    properties.withCircuitBreakerRequestVolumeThreshold(10);
		    properties.withCircuitBreakerErrorThresholdPercentage(90);
		    properties.withMetricsRollingStatisticalWindowInMilliseconds(60000);
		    properties.withMetricsRollingStatisticalWindowBuckets(100);
		    properties.withMetricsRollingStatisticalWindowInMilliseconds(60000);
		    properties.withMetricsRollingStatisticalWindowBuckets(100);
		    properties.withCircuitBreakerEnabled(true);  
		    config.andCommandPropertiesDefaults(properties);
		}
		return config;
	}


	public String resetHystrix() {
		Hystrix.reset();
		return "Reset";
	}
	
	private void increment() {
		// These two statements perform read and write operations
		// on a variable that is commonly accessed by multiple threads.
		// So, acquire a lock before processing this "critical section"
		synchronized(this) {
			MainService.number++;
			/*System.out.println(number);*/
			}
		}
}

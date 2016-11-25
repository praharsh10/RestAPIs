package api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.external.ExternalSim;
import api.hystrix.HystrixWrapper;

import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;


@Service
public class MainService {
	
	@Autowired
	ExternalSim external;

	public String getCall() {
		
		external.setWait(3000);
		String s  = "This is get call : ";
		String s2 = null;
		HystrixCommand.Setter config = getConfig(0);
		HystrixWrapper wrapper = new HystrixWrapper(config, external);
		try {
			s2 = wrapper.execute();
		} catch (Exception e) {
		}
		return s+s2;
	}

	
	private HystrixCommand.Setter getConfig(int a)
	{
		HystrixCommand.Setter config = null;
		HystrixCommandProperties.Setter properties = HystrixCommandProperties.Setter();
		
		config = HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("MyKeyForAParticularCall")).andCommandKey(HystrixCommandKey.Factory.asKey("a"));
		properties.withExecutionTimeoutInMilliseconds(2000);
	    properties.withExecutionTimeoutEnabled(true);
	    
	    
	    properties.withCircuitBreakerSleepWindowInMilliseconds(30000);
	   // properties.withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD);
	    
	    properties.withCircuitBreakerRequestVolumeThreshold(10);
	    properties.withCircuitBreakerErrorThresholdPercentage(80);
	    properties.withMetricsRollingStatisticalWindowInMilliseconds(60000);
	    properties.withMetricsRollingStatisticalWindowBuckets(100);
	    properties.withMetricsRollingStatisticalWindowInMilliseconds(60000);
	    properties.withMetricsRollingStatisticalWindowBuckets(100);
	    properties.withCircuitBreakerEnabled(true);
	    config.andCommandPropertiesDefaults(properties);
	    return config;
	}


	public String resetHystrix() {
		Hystrix.reset();
		return "Reset";
	}
}

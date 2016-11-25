package api.hystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import api.external.ExternalSim;

import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.HystrixCommand;

public class HystrixWrapper extends HystrixCommand<String>{
	

	@Autowired
	ExternalSim externalSimulator;
	
	public HystrixWrapper(Setter config, ExternalSim externalService)
	{
		super(config);
		this.externalSimulator = externalService;
		
	}

	@Override
	protected String run() throws Exception {
		/*System.out.println("circuit breaker state :"+super.circuitBreaker.isOpen());
		System.out.println(super.metrics.getHealthCounts());
		System.out.println("Running run");*/
		return externalSimulator.getExternalData();
	}
	
    @Override
    protected String getFallback() {
    	/*System.out.println(super.metrics.getHealthCounts());
    	System.out.println("Fail");
    	System.out.println("circuit breaker state :"+super.circuitBreaker.isOpen());*/
        return "Failure !";
    }
    
    public void reset()
    {
    	Hystrix.reset();
    }
	


}

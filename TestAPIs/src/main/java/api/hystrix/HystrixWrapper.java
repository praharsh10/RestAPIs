package api.hystrix;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import api.external.ExternalSim;

import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.HystrixCommand;

public class HystrixWrapper extends HystrixCommand<String>{
	

	@Autowired
	ExternalSim externalSimulator;
	
	public HystrixWrapper(HystrixCommand.Setter config, ExternalSim externalService)
	{
		super(config);
        this.externalSimulator = externalService;	
	}

	@Override
	protected String run() throws Exception {
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime());
		
/*		System.out.println(timeStamp+" : circuit breaker state :"+super.circuitBreaker.isOpen());
		System.out.println(super.metrics.getHealthCounts());*/
		/*System.out.println("Running run");*/
		/*System.out.println("");*/
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

package api.external;

import org.springframework.stereotype.Component;

@Component
public class ExternalSim {
	

	private long wait;
	
	public ExternalSim() {
		super();
	}
	
	public ExternalSim(long wait) {
		this.wait = wait;
	}
	
	public void setWait(int n)
	{
		wait = n;
	}
	
	public String getExternalData() throws InterruptedException
	{
		/*System.out.println("Called external");*/
		Thread.sleep(wait);
		/*System.out.println("returned Response");*/
        return "Success";
	}


}

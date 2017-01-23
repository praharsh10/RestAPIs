import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class TestingThreads extends Thread{
	
	  static int counter=0;
	
	public TestingThreads(String s) {
		this.setName(s);
	}
	
		public void run() {
			
			  try {

					URL url = new URL("http://localhost:8080/TestAPIs/anotherCall");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Accept", "application/json");

					BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));
					
					String output;
					//System.out.println("Output from Server .... \n");
					while ((output = br.readLine()) != null) {
						System.out.println(output);
					}
					
					this.increment();
					
					conn.disconnect();

				  } catch (MalformedURLException e) {

					e.printStackTrace();

				  } catch (IOException e) {

					e.printStackTrace();

				  }
			
		}
		
		private void increment() {
			// These two statements perform read and write operations
			// on a variable that is commonly accessed by multiple threads.
			// So, acquire a lock before processing this "critical section"
			synchronized(this) {
				TestingThreads.counter++;
				}
			}
}

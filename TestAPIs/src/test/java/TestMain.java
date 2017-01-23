import java.util.ArrayList;
import java.util.List;



public class TestMain {

	public static void main(String[] args) {
		
		/*TestingThreads t1 = new TestingThreads();
		t1.start();*/
		
		List<Thread> l = new ArrayList<Thread>();
		for (int i = 0; i < 1000; i++) {
			String s = Integer.toString(i);
			   l.add(new TestingThreads(s));
			}
		
		for (Thread t : l) {
			t.start();
		}
		for (Thread t : l) {
			try {
				t.join();
			} catch (InterruptedException e) {
				System.out.println("somethings wrong here");
			}
		}
		
		System.out.println(">>>>>>>"+TestingThreads.counter);
	}
}

public class HelloWorldApp {
	
	public static void main(String[] args)
	{
		System.out.print("Hello World");
		
		try {
            // sleep for 5 mins
            Thread.sleep(1000*60*5);
        }catch(InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
	}
	
}
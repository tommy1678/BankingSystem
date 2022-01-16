public class NewLauncher {

	public static void main(String argv[]) {
		System.out.println(":: PROGRAM START");
		
		if (argv.length < 1) {
			System.out.println("Need database properties filename");
		} else {
			BankingSystem.init(argv[0]);
			BankingSystem.testConnection();
			System.out.println();
			BatchInputProcessor.run(argv[0]);
		}
		//p1.startMenu();
		System.out.println(":: PROGRAM END");
	}
}
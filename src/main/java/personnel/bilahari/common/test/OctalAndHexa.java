package personnel.bilahari.common.test;

/**
 * @author bilahari.th
 *
 */
public class OctalAndHexa {

	public static void main(String[] args) {

		OctalAndHexa oh = new OctalAndHexa();

		oh.doTest3();
	}	

	private void doTest3(){

		//	octal
		int a = 012;
		System.out.println(a);

		//	hexa
		int h = 0xF;
		System.out.println(h);
	}    
}

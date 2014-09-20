package personnel.bilahari.common.test;

/**
 * @author bilahari.th
 *
 */
public class ShiftOperators {

	int x = 1,x1;
	int y = 2,y1;
	int z = 3,z1;

	public static void main(String[] args) {

		ShiftOperators so = new ShiftOperators();
		
		//so.doTest1();
		so.doTest2();
	}	

	private void doTest3(){
		
	}
	
	private void doTest2(){
		int x=1;
		for(int i=0;i<65;i++){
			System.out.println("<< Signed Left shift "+x+" by "+i+" : "+(x<<i));
		}
		x=1;
		for(int i=0;i<65;i++){
			System.out.println(">> Signed Right shift "+x+" by "+i+" : "+(x>>i));
		}
		x=1;
		for(int i=0;i<65;i++){
			System.out.println(">>> Unsigned Right shift "+x+" by "+i+" : "+(x>>>i));
		}
	}
	
	private void doTest1(){
		
		System.out.println(""+(1<<3));

		x1 = x<<1;
		y1 = y<<1;
		z1 = z<<1;
		printAllShiftedValues("<< Signed Left shift by 1");

		x1 = x<<2;
		y1 = y<<2;
		z1 = z<<2;
		printAllShiftedValues("<< Signed Left shift by 2");

		x1 = x<<3;
		y1 = y<<3;
		z1 = z<<3;
		printAllShiftedValues("<< Signed Left shift by 3");

		x1 = x>>1;
		y1 = y>>1;
		z1 = z>>1;
		printAllShiftedValues(">> Signed Right shift by 1");

		x1 = x>>2;
		y1 = y>>2;
		z1 = z>>2;
		printAllShiftedValues(">> Signed Right shift by 2");

		x1 = x>>3;
		y1 = y>>3;
		z1 = z>>3;
		printAllShiftedValues(">> Signed Right shift by 3");

		x1 = x>>>1;
		y1 = y>>>1;
		z1 = z>>>1;
		printAllShiftedValues(">>> Unsigned Right shift by 1");

		x1 = x>>>2;
		y1 = y>>>2;
		z1 = z>>>2;
		printAllShiftedValues(">>> Unsigned Right shift by 2");

		x1 = x>>>3;
		y1 = y>>>3;
		z1 = z>>>3;
		printAllShiftedValues(">>> Unsigned Right shift by 3");
	}

	private void printAllShiftedValues(String header){
		System.out.println(header+"\n");
		System.out.println("x = "+x+"\tx1 = "+x1);
		System.out.println("y = "+y+"\ty1 = "+y1);
		System.out.println("z = "+z+"\tz1 = "+z1+"\n----------------------");
	}
}

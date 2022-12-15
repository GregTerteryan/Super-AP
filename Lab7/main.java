import pkg.*;
import java.util.*;
import java.time.*;
import java.lang.*;

class main {
	public static void main(String args[]) {
		String infix = "6*9+6+9";
		System.out.println(BaseClass.toPostFix(infix));
		System.out.println("fun fact, that equals 69");
		String actualTest = "1+((3+4)*2+2)";
		System.out.println(BaseClass.toPostFix(actualTest));
	}
}

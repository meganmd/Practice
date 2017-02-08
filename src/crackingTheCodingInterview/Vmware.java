package crackingTheCodingInterview;

public class Vmware {

	public static void main(String[] args) {
		//StairCase(10);
		//System.out.println();
		//StairCase();
		//System.out.println(sum(new int[]{2,3,4}));
		Point2D p = new Point2D(1,3);
		Point2D p2 = new Point2D(4,5);
		System.out.println(p.dist2D(p2));
	}
	static void StairCase(int n) {
		for(int i = 1; i<= n; i++) {
			for(int j = 0; j < n-i; j++) {
				System.out.print(" ");
			}
			for(int j = 0; j < i; j++ ) {
				System.out.print("#");
			}
			System.out.println();
		}
	}
	static int sum(int[] numbers) {
		int sum = 0;
		for(int n : numbers) {
			sum += n;
		}
		return sum;
    }
	
	static class Point2D {
	    int x;
	    int y;
	    public Point2D(int x, int y) {
	        this.x = x;
	        this.y = y;
	    }
	    double dist2D(Point2D p) {
	        return Math.sqrt(Math.pow((double)(p.x-x), 2) + Math.pow((double)(p.y-y), 2));
	    }
	    void printDistance(double d) {
	        
	    }
	}

}

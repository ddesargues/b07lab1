public class Polynomial{

	double [] x_arr;

	public Polynomial(){
		double [] neww = {0};
		x_arr = neww;
	}

	public Polynomial(double [] arr){
		x_arr = arr;
	}
	public Polynomial add(Polynomial arr){
		Polynomial poly;
		int check = 0;
		if(arr.x_arr.length >= x_arr.length){
			check = 0;
		}
		else{
			check = 1;
		}
		if(check == 0){
			double [] neww = new double[arr.x_arr.length];
			int i = 0;
			while(i < x_arr.length){
				neww[i] = arr.x_arr[i] + x_arr[i];
				i++;
			}
			for(int j = i; j < arr.x_arr.length; j++){
				neww[j] = arr.x_arr[j];
			}
			poly = new Polynomial(neww);
		}
		else{
			double [] neww = new double[x_arr.length];
			int i = 0; 
			while(i < arr.x_arr.length){
				neww[i] = arr.x_arr[i] + x_arr[i];
				i++;
			}
			for(int j = i; j < x_arr.length; j++){
				neww[j] = x_arr[j];
			}
			poly = new Polynomial(neww);
		}
		return poly;
	}

	public double evaluate(double x){
		double total = 0;
		for(int i = 0; i<x_arr.length; i++){
			double start = x_arr[i];
			for(int j = 0; j<i;j++){
				start *= x;
			}
			total += start;
		}
		return total;
	}
	public boolean hasRoot(double val){
		return evaluate(val) == 0;
	}
}
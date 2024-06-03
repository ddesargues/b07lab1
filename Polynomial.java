import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.util.Scanner; 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

public class Polynomial{

	double[] coeff;
	int[] exps;

	public Polynomial(){
		coeff = null;
		exps = null;
	}

	public Polynomial(double [] ccoeff, int [] eexps){
		coeff = ccoeff;
		exps = eexps;
	}

	public Polynomial(File file){
		try{	
			if(file.length() == 0){
				int [] nexp = {};
				double [] ncop ={};
				coeff = ncop;
				exps = nexp;
			}
			else{
			Scanner obj = new Scanner(file);
			String play = obj.nextLine();
			int len = play.length();
			int ind = 0;
			int co = 0;
			int lco = 0;
			int lexp = 0;
			String coef = "";
			String expo = "";
			
			while(ind < len){
				if(play.charAt(ind) == '+' || play.charAt(ind) == '-'){
					coef += '#';
					if(co == 1){
						expo += Integer.toString(1);
						expo += '#';
						lexp += 1;
					}
					else if(co == 3){
						expo += Integer.toString(0);
						expo += '#';
						lexp += 1;
					}
					if(play.charAt(ind) == '-'){
						coef += '-';
					}
					ind += 1;
					co = 0;
				}	
				else if(play.charAt(ind) == 'x'){
					if(co == 0){
						coef += Double.toString(1);
						lco += 1;
					}
					co = 1;
					ind += 1;
				}	
				else if(co == 0){
					while(ind <len && play.charAt(ind) != 'x' && play.charAt(ind) != '+' && play.charAt(ind) != '-'){	
						coef += play.charAt(ind);
						ind += 1;
					}
					co = 3;
					lco += 1;
				}
				else{
					while(ind < len && play.charAt(ind)!= '+' && play.charAt(ind) != '-'){
						expo += play.charAt(ind);
						ind += 1;
					}
					lexp += 1;
					co = 2;
					expo += '#';
				}
			}
			if(co == 3){
				expo += Integer.toString(0);
				lexp += 1;
			}
			else if(co == 1){
				expo += Integer.toString(1);
				lexp += 1;
			}
			int [] nexp = new int[lexp];
			double [] ncof = new double[lco];
			int i = 0;
			int index = 0;
			int len1 = coef.length();
			while(i < len1){
				String start = "";
				if(coef.charAt(i) == '#'){
					i += 1;
				}
				while(i < len1 && (coef.charAt(i)!= '#')){
					start += coef.charAt(i);
					i += 1;
				}
				if(index < lco){
					double d = Double.parseDouble(start);
					if(d == 0){
						double[] cof1 = {};
						int[] exp1 = {};
						exps = exp1;
						coeff = cof1;
						return;
					}
					else{
						ncof[index] = d;
						index += 1;
					}
				}
			}
			int j = 0;
			int index2 = 0;
			int len2 = expo.length();
			while(j < len2){
				String start = "";
				if(expo.charAt(j) == '#'){
					j += 1;
				}
				while(j < len2 && expo.charAt(j) != '#'){
					start += expo.charAt(j);
					j += 1;
				}
				if(index2 < lexp){
					nexp[index2] = Integer.parseInt(start);
					index2+=1;
				}
			}
			coeff = ncof;
			exps = nexp;
			obj.close();
			} 
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}

	}
	public void check(){
		System.out.println("Coefficients : ");
		if(coeff == null){
			System.out.println("null");
		}
		else{
			for(int i =0; i < coeff.length; i++){
				System.out.println(coeff[i]);
			}
		}
		System.out.println("Exponents : ");
		if(exps == null){
			System.out.println("null");
		}
		else{
			for(int i =0; i < exps.length; i++){
				System.out.println(exps[i]);
			}
		}
	}

	public Polynomial add(Polynomial arr){
		if(arr.exps == null && exps == null){
			Polynomial nnew = new Polynomial();
			return nnew;
		}
		else if(arr.exps == null && exps != null){
			int [] e1 = exps;
			double [] c1 = coeff;
			Polynomial p1 = new Polynomial(c1,e1);
			return p1;
		}
		else if(arr.exps != null && exps == null){
			int [] e2 = arr.exps;
			double [] c2 = arr.coeff;
			Polynomial p2 = new Polynomial(c2,e2);
			return p2;
		}
		else{
			HashMap<Integer, Double> npol = new HashMap<>();
			for(int i =0; i < arr.exps.length; i++){
				npol.put(arr.exps[i], arr.coeff[i]);
			}
			for(int j =0; j< exps.length;j++){
				if(npol.containsKey(exps[j])){
					double temp = npol.get(exps[j]);
					double he = temp+coeff[j];
					if(he != 0){
						npol.put(exps[j], temp+coeff[j]);
					}
					else{
						npol.remove(exps[j]);
					}
				}
				else{
					npol.put(exps[j],coeff[j]);
				}
			}
			int len = npol.size();
			Polynomial Poly;
			if(len != 0){
				int[] nexp = new int[len];
				double[] ncof = new double[len];
				int ind =0;
				for (int tkey : npol.keySet()){
					if(npol.get(tkey) != 0){
						nexp[ind] = tkey;
						ncof[ind] = npol.get(tkey);
					}
					ind += 1;
				}
				Poly = new Polynomial(ncof, nexp);
			}
			else{
				Poly = new Polynomial();
			}
			return Poly; 
		}
	}

	public boolean isContain(Polynomial pp){
		if(exps == null){
			return true;
		}
		else if(pp.exps == null){
			return false;
		}
		HashMap<Integer, Double> npol = new HashMap<>();
		for(int i = 0; i < exps.length; i++){
			npol.put(exps[i], coeff[i]);
		}
		int j = 0;
		while(j < pp.coeff.length){
			if(npol.containsKey(pp.exps[j]) && (npol.get(pp.exps[j]) == pp.coeff[j])){
				j += 1;
			}
			else{
				return false;
			}
		}
		return true;
	}

	public void saveToFile(String nname){
		try{
			FileWriter output = new FileWriter(nname, false);
			int [] expo = this.exps;
			double [] coef = this.coeff;
			String start = "";
			for(int i = 0; i < expo.length; i++){
				if(i != 0 && coef[i] > 0){
					start += '+';				
				}
				start += Double.toString(coef[i]);
				if(expo[i] > 0){
					start += 'x';
				} 
				if(expo[i] > 1){
					start += expo[i];
				}
			}
			System.out.println(start);
			output.write(start);
			output.close();
		}
		catch (Exception e){
			e.getStackTrace();
		}
	}

	public double evaluate(double x){
		double total = 0;
		if(exps != null){
			for(int i = 0; i<coeff.length; i++){
				double start = coeff[i];
				for(int j = 0; j<exps[i];j++){
					start *= x;
				}
				total += start;
			}
		}
		return total;
	}
	
	public Polynomial multiply(Polynomial pol){
		if(exps == null || pol.exps == null){
			Polynomial px = new Polynomial();
			return px;
		}
		Polynomial np = new Polynomial();
		for(int i = 0; i < pol.exps.length; i++){
			int [] nexp = new int [exps.length];
			double[] ncof = new double [coeff.length];
			for(int j = 0; j < exps.length; j++){
				nexp[j] = exps[j]+pol.exps[i];
				ncof[j] = coeff[j]*(pol.coeff[i]); 
			} 
			Polynomial p = new Polynomial(ncof, nexp);
			np = np.add(p);
		}
		return np;
	}
	public boolean hasRoot(double val){
		return evaluate(val) == 0;
	}
}
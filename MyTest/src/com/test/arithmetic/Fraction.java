package com.test.arithmetic;

public class Fraction {
	private int a;
    private int b;
    public Fraction(int x, int y){
        this.a = x;
        this.b = y;
    }
     
    public Fraction(){
         
    }
    public String toString(){
    	int c = Simple(a,b);
        return this.a/c+"/"+this.b/c;
    }
     
    public int Simple(int a,int b){
    	 if(a<b){
	             int temp;
	             temp=a;
	             a=b;
	             b=temp;
	     }
	     if(0==b){
	             return a;
	     }
	     return Simple(a-b,b);
    }
 
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Fraction f = new Fraction(868424680, 246804680);
        System.out.println("result:"+f.toString());
    }
}

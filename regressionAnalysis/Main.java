
package pkg191213046_ıst_ve_ıht_odev2;

import java.io.BufferedReader;
import java.io.FileReader;


public class Main {

    public static void main(String[] args) {
        int n=0;
        try{
            FileReader fr=new FileReader("machine.txt");
            BufferedReader br=new BufferedReader(fr);
            String line=br.readLine();
            while(line!=null){
                n++;
                line=br.readLine();
            }
            br.close();
            
        }catch(Exception e){
            System.out.println("Bağlantı Hatası");
        }
        
        double[] cycleTime=new double[n];
        double[] cache=new double[n];
        double[] performance=new double[n];
        
        
        try{
          int x=0;
     
          FileReader fr=new FileReader("machine.txt");
            BufferedReader br=new BufferedReader(fr);
            String line=br.readLine();
            while (line!=null) {
                String[] parcala=line.split(",");
                for(int i=0;i<parcala.length;i++){
                    if(i==2){
                        cycleTime[x]=Integer.valueOf(parcala[i]);
                    }
                    if(i==5){
                        cache[x]=Integer.valueOf(parcala[i]);
                    }
                    if(i==8){
                        performance[x]=Integer.valueOf(parcala[i]);
                    }
                }
                x++;
                line=br.readLine();
            
            }
            br.close();
        }catch(Exception e){
            System.out.println("Okuma Hatası");
            System.out.println(e.getMessage());
        }
        int a=(int)n*7/10;
        
        int b=n-a;
        
        double[] egitimCycleTime=new double[a];
        double[] egitimCache=new double[a];
        double[] egitimPerf=new double[a];
        double[] testCycleTime=new double[b];
        double[] testCache=new double[b];
        double[] testPerf=new double[b];
        int[] index=new int[n];

        index[0]=0;
    for(int i=1;i<n;i++){
        int sayi=(int)(Math.random()*n);
        for(int j=0;j<=i;j++){
            if(sayi==index[j]){
                sayi=(int)(Math.random()*n);
                j=0;
            }
        }
        index[i]=sayi;
 
    }
    int c=-1;
    for(int i=0;i<n;i++){
        if(i<a){
            egitimCycleTime[i]=cycleTime[index[i]];
            egitimCache[i]=cache[index[i]];
            egitimPerf[i]=performance[index[i]];
        }else{
            c++;
            testCycleTime[c]=cycleTime[index[i]];
            testCache[c]=cache[index[i]];
            testPerf[c]=performance[index[i]];  
        }
    }
  
    Hesaplamalar h=new Hesaplamalar(egitimCycleTime,egitimPerf);
    double r1=h.KorelasyonKatsayisi();
   
    Hesaplamalar h2=new Hesaplamalar(egitimCache, egitimPerf);
    double r2=h2.KorelasyonKatsayisi();
    System.out.println("r1:"+r1+" r2:"+r2);
    double sse=0.0;
    if(r1>r2){
        h.BKatsayisi();
        h.AKatsayisi();
        System.out.println("Tahmini y değerleri");
        sse=h.Sonuclar(egitimCycleTime,egitimPerf);
        System.out.println("\nEğitim verileri için SSE:"+sse);
        System.out.println("Test verileri için");
        System.out.println("Tahmini y değerleri:");
        sse=h.Sonuclar(testCycleTime,testPerf);
        System.out.println("\nSSE:"+sse);
        
    }
    else{
        h2.BKatsayisi();
        h2.AKatsayisi();
        System.out.println("Tahmini y değerleri");
        sse=h2.Sonuclar(egitimCache,egitimPerf);
        System.out.println("\nEğitim verileri için SSE:"+sse);
        System.out.println("Test verileri için");
        System.out.println("Tahmini y değerleri:");
        sse= h2.Sonuclar(testCache,testPerf);
        System.out.println("\nSSE:"+sse);
    }
    

    
 }
        
}
class Hesaplamalar {
    int n=0,xToplam=0,yToplam=0;
        double r,xOrt,yOrt=0.0,a,b,sse;
       long X2,XY,Y2;

    public Hesaplamalar(double[] x,double[] y){
         
        for(int i=0;i<x.length;i++){
            n++;
            xToplam+=x[i];
            yToplam+=y[i]; 
            X2+= x[i]*x[i]; 
            Y2+= y[i]*y[i]; 
            XY+= x[i]*y[i];   
 
        }
        xOrt=(double)xToplam/n; 
        yOrt=(double)yToplam/n; 
        
    }

    public double KorelasyonKatsayisi(){
 
        r=(double)(XY-n*xOrt*yOrt)/Math.sqrt((X2-n*(xOrt*xOrt))*(Y2-n*(yOrt*yOrt)));
        return r;
        
    }
    public void BKatsayisi(){
        b=(double)(n*XY-xToplam*yToplam)/(n*X2-(xToplam*xToplam));

    }
    public void AKatsayisi(){
        a=yOrt-b*xOrt;
        System.out.println("Basit dogrusal regresyon modeli:y="+a+"+"+b+"x");
        

    }
    public double Sonuclar(double[] x,double[] y){
        double[] tahminY=new double[x.length];
        
        for(int i=0;i<x.length;i++){
            tahminY[i]=a+b*x[i];
            System.out.print(tahminY[i]+" ");
            double c=y[i]-tahminY[i];
            sse+=c*c;
        }
        return sse;
    
    }
  
    
    
    
}

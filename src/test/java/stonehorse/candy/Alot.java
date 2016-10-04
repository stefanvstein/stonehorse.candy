package stonehorse.candy;

import java.io.IOException;
import java.util.stream.Stream;

import static stonehorse.candy.Iterables.*;

public class Alot {
	public static void main(String[] args) throws IOException {
		System.in.read();
		System.out.println("Start!!");
		for(int y=0;y<10;y++){
			System.out.println("");

		int x = 300000
				;
		
		long time = System.currentTimeMillis();
		Iterable<Integer> i = iterate(v -> v + 1, 0);
	
		Iterable<Integer> ii = map(v -> v + 1, i);
		Iterable<Integer> iii = filter(v -> v % 2 == 0, ii);
		int r= fold((a, v) -> a + v, 0, take(x, iii));
		System.out.println(System.currentTimeMillis() - time);

		time = System.currentTimeMillis();
		int s = 0;
		int cnt=0;
		for (int j = 0; true; j++) {
			
			int g = j + 1;
			if (g % 2 == 0){
				cnt++;
				s = s + g;
				if(cnt==x)
					break;
			}
			

		}
		System.out.println(System.currentTimeMillis() - time);
		time=System.currentTimeMillis();
		Stream<Integer> e = Stream.iterate(0,v->v+1);
		Stream<Integer> ee = e.map(v->v+1);
		Stream<Integer> eee = ee.filter(v->v%2==0);
		int w=eee.limit(x).reduce(0, (a,v)-> a+v);
		//collect(Collectors.summingInt(v->v));
		//System.out.println(w + " "+s);
		
		System.out.println(System.currentTimeMillis() - time);
		if(s!=r)
			System.out.println(s+" "+r);
	}
		
		
	}
	
	
}

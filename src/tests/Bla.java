package tests;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Bla {

	public static void main(String[] args) {
		List<String> dicionario = Arrays.asList("Comparecer", 
				"na", "Faria", "Lima", "Rua", "Berrini",
				"Jaceru", "Carlos", "225", "no", "dia", 
				"19 de dezembro", "10 de novembro", 
				"Paulista", "às", "9h"
				);
		
		System.out.println(dicionario.stream().filter(
				p -> isInsideOfRules(dicionario.indexOf(p))).
				collect(Collectors.joining(" ")));
	}
	
	private static boolean isInsideOfRules(int index) {
		if(index <= 1) return true;
		if(index <= 3) return false;
		if(index % 2 == 0 || index % 3 == 0) return true;
		
		for(int i=5; i*i <= index; i=i+6) {
			if(index % i == 0 || index % (i+2) == 0) {
				return true;
			}
		}
		
		return false;
	}
}

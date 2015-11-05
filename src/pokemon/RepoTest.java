package pokemon;

import model.SimplePokemon;

public class RepoTest {
    
    public static void main (String[] args) {
        
        SimplePokemon sp = new SimplePokemon("Pikachu","region_type",0.50,0.50);
        
        System.out.println("Pokemon: "+sp);
    }

}
